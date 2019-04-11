/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ${converterPackage};

import ${entity.element.qualifiedName};
import ${facadePackage}.${entity.className}Facade;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import javax.naming.InitialContext;
import javax.naming.NamingException;
 
/**
 *
 * @author JSmartGenerator Tools
 */
@FacesConverter(forClass = ${entity.className}.class)
public class ${entity.className}Converter implements  Converter {
    
    
    public ${entity.className}Facade findEJB()
    {
        try {
            InitialContext  conttext = new InitialContext();
            ${entity.className}Facade ret = (${entity.className}Facade) conttext.lookup("java:module/${entity.className}Facade");
            return ret;
                    
        } catch (NamingException ex) {
            return null;
        }
    }

    @Override
    public Object getAsObject(FacesContext context, UIComponent component, String value) 
    {
        if (value == null || value.trim().equals(""))
        {
            return null;
        }
                
        ${entity.className}Facade ejb = findEJB();
        if (ejb == null)
        {
            return null;
        }
      
      <#if entity.annotationMirror.toString() = '@javax.persistence.EmbeddedId'>
          String[] s = value.split("_");
          ${entity.pk.type} pk = new ${entity.pk.type}();
          <#list entity.embeddedId as a>
          <#assign abcd = '${a_index}'>
          ${a.findSetAsString('pk',abcd)};
              </#list>    
          return   ejb.find(pk);
       <#else>
        return ejb.find(new ${entity.pk.type}(value));
     </#if>
         

    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, Object value) {
        
        if (value == null)
        {
            return  null;
        }
        <#if entity.annotationMirror.toString() = '@javax.persistence.EmbeddedId()'>
              ${entity.javaClass.name} var = (${entity.javaClass.name}) value;
              ${entity.pk.type} pk = var.get${entity.pk.name2}();
              StringBuilder sb = new StringBuilder();
              <#list entity.embeddedId as a>
                <#if a_index &gt; 0>
              sb.append("_");
                </#if>
              sb.append(${a.findGetAsString('pk')});
              </#list>
              return sb.toString();
        <#else>
                ${entity.className} v = (${entity.className}) value;
               return v.get${entity.pk.name2}().toString();
        </#if>

        
    }

 
}
