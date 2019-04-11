/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.jsmartgenerator.converter;

 ;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
 

import static java.util.stream.Collectors.toList;
import javax.persistence.EmbeddedId;
 
import javax.persistence.Id;
import org.jsmartgenerator.datastruture.Column;

import org.jsmartgenerator.datastruture.EntityInfo;

import org.jsmartgenerator.interfaces.TriFunction;

/**
 *
 * @author Mohammed Almutairi
 */
 
public class Converter {
    
    List<Class<?>> classes;

    public Converter(List<Class<?>> classes) {
        this.classes = classes;
    }
    public List<EntityInfo> convertToEntityInfo()
    {

        TriFunction<Class,Field,Annotation,EntityInfo> function = (className,field,anntoation) -> {
            EntityInfo entityInfo = new EntityInfo();
            entityInfo.setJavaClass(className);
            entityInfo.setFieldName(field);
            entityInfo.setAnnotationName(anntoation);
            entityInfo.findePK();
            if (anntoation.annotationType() == EmbeddedId.class)
            {
                Optional<Class<?>> abc = classes.stream().filter(s -> s.equals(field.getType())).findFirst();  
                abc.ifPresent(z -> entityInfo.findEmbeddedId(z));
                
        
            }
            return entityInfo;  
        };
             
        List<EntityInfo> list = classes.stream()
                .filter(className -> !className.getName().endsWith("PK"))
                .flatMap(className
                        -> Arrays.stream(className.getDeclaredFields())
                        .flatMap(field -> Arrays.stream(field.getAnnotations())
                                .filter(annotation -> annotation.annotationType() == Id.class || annotation.annotationType() == EmbeddedId.class)
                                .map(a -> function.apply(className, field, a))
                        )
                ).collect(toList());
                        
        
               
        
        list.stream().forEach(Column::fillCols);
        
        
        return list;
    }
    

}
