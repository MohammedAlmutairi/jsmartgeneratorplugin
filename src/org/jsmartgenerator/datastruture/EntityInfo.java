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
import javax.lang.model.element.TypeElement;
import javax.lang.model.element.VariableElement;
import javax.lang.model.util.ElementFilter;
 
 

/**
 *
 * @author Mohammed Almutairi
 */
public class EntityInfo {
    
    private Class javaClass;
    
    private TypeElement  element;
    
    private Field fieldName;
    private VariableElement variableElement;
    
    private Annotation annotationName;
    private AnnotationMirror annotationMirror;
    
    private PrimaryKeyInfo pk;
    private List<PrimaryKeyInfo> embeddedId;
    private String className;
    
    private List<Column> cols;

 

    public Field getFieldName() {
        return fieldName;
    }

    public void setFieldName(Field fieldName) {
        this.fieldName = fieldName;
    }

    public Annotation getAnnotationName() {
        return annotationName;
    }

    public void setAnnotationName(Annotation annotationName) {
        this.annotationName = annotationName;
        

    }
    
    
    public String getSimpleName2()
    {
        String simple = "";
        if (getJavaClass() != null)
        {
           simple = getJavaClass().getSimpleName();  
        }
        if (element != null)
        {
            simple = element.getSimpleName().toString();
        }
        
        String ret = simple.substring(0, 1).toLowerCase();
        if (simple.length() > 1)
        {
            ret = ret + simple.substring(1);
        }
        
        return ret;
    }

    public PrimaryKeyInfo getPk() {
        return pk;
    }

    public void setPk(PrimaryKeyInfo pk) {
        this.pk = pk;
    }

    public List<PrimaryKeyInfo> getEmbeddedId() {
        return embeddedId;
    }

    public void setEmbeddedId(List<PrimaryKeyInfo> embeddedId) {
        this.embeddedId = embeddedId;
    }

 
    
    public void findePK()
    {
        if (fieldName != null)
        {
            pk = new PrimaryKeyInfo(fieldName.getName(),fieldName.getType().getName());
        }
        
        if (variableElement != null)
        {
            pk = new PrimaryKeyInfo(variableElement.getSimpleName().toString(),variableElement.asType().toString());
 
        }
        
    }
    
    
    
    public void findEmbeddedId(Class className)
    {
        embeddedId = Arrays.stream(className.getDeclaredFields())
                .map(field -> {
                    PrimaryKeyInfo pk = new PrimaryKeyInfo(field.getName(),field.getType().getName());
 
                    return pk;
                })
                .collect(toList())
                ;
               
     }
    
    public void findEmbeddedId(TypeElement element) {
          
        List<VariableElement> fields = ElementFilter.fieldsIn(element.getEnclosedElements());
        
        embeddedId = fields.stream().map(f -> new PrimaryKeyInfo(f.getSimpleName().toString(), f.asType().toString())).collect(toList());
        
        
          
    }


    public Class getJavaClass() {
        return javaClass;
    }

    public void setJavaClass(Class javaClass) {
        this.javaClass = javaClass;
        findClassName();
        
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    private void findClassName() {
        if (javaClass != null)
        {
          setClassName(javaClass.getSimpleName());  
        }
        
        if (element != null)
        {
            setClassName(element.getSimpleName().toString());
        }
        
        
    }

    public List<Column> getCols() {
        return cols;
    }

    public void setCols(List<Column> cols) {
        this.cols = cols;
    }
    
    public String getManageBean()
    {
        StringBuilder sb = new StringBuilder();
        sb.append(getSimpleName2().substring(0,1).toLowerCase());
        sb.append(getSimpleName2().substring(1));
        sb.append("MB");
        return sb.toString();
    }
    
    public String getObjectName()
    {
        StringBuilder sb = new StringBuilder();
//        sb.append(getMB());
//        sb.append(".");

       
       
                  
//        sb.append(getJavaClass().getSimpleName().substring(0, 1).toLowerCase());
  //      sb.append(getJavaClass().getSimpleName().substring(1));

               
        sb.append(getSimpleName2().substring(0, 1).toLowerCase());
        sb.append(getSimpleName2().substring(1)); 
  
        return sb.toString();
    }

    public TypeElement getElement() {
        return element;
    }

    public void setElement(TypeElement element) {
        this.element = element;
        findClassName();
    }

    public VariableElement getVariableElement() {
        return variableElement;
    }

    public void setVariableElement(VariableElement variableElement) {
        this.variableElement = variableElement;
    }

    public AnnotationMirror getAnnotationMirror() {
        return annotationMirror;
    }

    public void setAnnotationMirror(AnnotationMirror annotationMirror) {
        this.annotationMirror = annotationMirror;
    }
    
    
    
    
}
