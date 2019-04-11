/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.jsmartgenerator.datastruture;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import static java.util.stream.Collectors.toList;
import javax.lang.model.element.AnnotationMirror;
import javax.lang.model.element.VariableElement;
import javax.lang.model.util.ElementFilter;
import javax.persistence.Embeddable;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.swing.JOptionPane;
import jdk.internal.dynalink.support.NameCodec;

/**
 *
 * @author Mohammed Almutairi
 */
public class Column {
    
    private String name;
    private String type;
    private String relation;
    
    private String simpleType;
    private String packageName;
    
    
    private String name2;
    
    private String firstStringOfManyToOne = "";

    public String getFirstStringOfManyToOne() {
        return firstStringOfManyToOne;
    }

    public void setFirstStringOfManyToOne(String firstStringOfManyToOne) {
        this.firstStringOfManyToOne = firstStringOfManyToOne;
    }
    
   

    
    
    
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getRelation() {
        return relation;
    }

    public void setRelation(String relation) {
        this.relation = relation;
    }

    public Column(String name, String type, String relation) {
        this.name = name;
        this.type = type;
        this.relation = relation;
    }
    
    
    
    
    public static void fillCols(EntityInfo entityInfo)
    {
       Class<?> myClass = entityInfo.getJavaClass();
       Field[] fields = myClass.getDeclaredFields();
      
       List<Column> cols = Arrays.stream(fields)
                              .filter(f -> f.getName().equals("serialVersionUID") == false)
                              .filter(Column::notPrimaryKey)
                              .map(f -> new Column(f.getName(), f.getType().toString().replace("class ", ""), getRelationName(f)))
                                .collect(Collectors.toList());
       
       entityInfo.setCols(cols);
       
    }
 
    public static void fillCols2(EntityInfo entityInfo)
    {
          
        List<VariableElement> fields = ElementFilter.fieldsIn(entityInfo.getElement().getEnclosedElements());
        
       
       
       List<Column> cols = fields.stream().filter(f -> f.getSimpleName().equals("serialVersionUID") == false)
                      .filter(Column::notPrimaryKey)
                      .map(f -> new Column(f.getSimpleName().toString(), f.asType().toString(), getRelationName(f)))
                     .collect(Collectors.toList())
                      ;
       
      
       
       
       entityInfo.setCols(cols);
       
    }
 
    
    public static boolean notPrimaryKey(Field field)
    {
        Annotation[] annotations = field.getAnnotations();
        for (Annotation a:annotations)
        {
            if (a.annotationType() == Id.class || a.annotationType() == Embeddable.class)
            {
                return false;
            }
        }
        return true;
    }
    
     
    public static boolean notPrimaryKey(VariableElement element)
    {
      
        for (AnnotationMirror a:element.getAnnotationMirrors())
        {
            if ( a.getAnnotationType().toString().equals("javax.persistence.Id")|| a.getAnnotationType().toString().equals("javax.persistence.EmbeddedId"))
            {
                return false;
            }
        }
        return true;
    }
    
    
    public static String getRelationName(Field f) {
//        String ret = "None";
        Annotation[] list = f.getAnnotations();

        for (Annotation a : list) {
            
           
            if (a.annotationType() == ManyToOne.class) {
           
                return "ManyToOne";
            } else if (a.annotationType() == ManyToMany.class) {
                return "ManyToMany";
            }
             else if (a.annotationType() == OneToMany.class) {
                return "OneToMany";
            }
         
        }
                   
        
        return "None";


    }

    public static String getRelationName(VariableElement f) {
//        String ret = "None";
        
        for (AnnotationMirror a : f.getAnnotationMirrors()) {
            String name = a.getAnnotationType().toString();
           
            if (name.contains("ManyToOne")) {

                return "ManyToOne";
            } else if (name.contains("ManyToMany")) {
                return "ManyToMany";
                
            } else if (name.contains("OneToMany")) {
                return "OneToMany";
            }

        }

        return "None";
    }
    public String getSimpleType() {
        if (simpleType == null)
        {
            int lastdot = type.lastIndexOf(".");
            lastdot++;
            simpleType = type.substring(lastdot);
        }
        return simpleType;
    }

    public void setSimpleType(String simpleType) {
        this.simpleType = simpleType;
    }

    public String getPackageName() {
        return packageName;
    }

    public void setPackageName(String packageName) {
        if (packageName == null)
        {
          String extract = "." + getSimpleType();
          packageName = type.replace(extract, "");
        }
        this.packageName = packageName;
    }

    public String getName2() {
        if (name2 == null)
        {
            name2 = name.substring(0,1).toUpperCase();
           
            if (name.length() > 1)
            {
                name2 = name2 + name.substring(1);
            }
            
           
            
        }
        return name2;
    }

    public void setName2(String name2) {
        this.name2 = name2;
    }
    
    public String getFacade()
    {
        StringBuilder sb = new StringBuilder();
         sb.append(getSimpleType().substring(0, 1).toLowerCase());
        if (getType().length() > 1)
        {
            sb.append(getSimpleType().substring(1));
        }
        sb.append("Facade.all");
        return sb.toString();
    }
    
    public boolean isPreview()
    {
        return !name.equals("serialVersionUID") && !relation.equals("OneToMany");
    }
    
    public static void fillFirsStringOfManyToOne(EntityInfo entityInfo,AllEntityies list)
    {
        entityInfo.getCols()
                .stream()
                .filter(col -> col.getRelation().endsWith("ManyToOne"))
                .forEach(column -> column.setFirstStringOfManyToOne(list.findFirstStringFor(column.getType())));
                ;
                
                
    }
    
    
 
    
    
    
   
    
    
    
    
}
