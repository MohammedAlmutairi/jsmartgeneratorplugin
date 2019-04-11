/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.jsmartgenerator.datastruture;

import org.jsmartgenerator.util.JavaTypeHelper;

/**
 *
 * @author Mohammed Almutairi
 */
public class PrimaryKeyInfo {
    
    private String name;
    private String type;

    public String getName() {
        return name;
    }
    
    
    public String getName2()
    {
        String result = name.substring(0, 1).toUpperCase() + name.substring(1);
        return result;
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

    public PrimaryKeyInfo(String name, String type) {
        this.name = name;
        this.type = type;
    }
    
    
    
    
    public String findGetAsString(String var)
    {
          String ret;
         if (type.startsWith("java.lang") || type.startsWith("java.math")) {
            ret = String.format("%s.get%s().toString();", var, getName2());
        }
         else
         {
              ret = String.format("%s.get%s()+\"\"", var, getName2());
         }
        return ret;
    }
    public String findSetAsString(String var,String value) {
        
        String ret;
         if (type.startsWith("java.lang") || type.startsWith("java.math")) 
         {    
            ret = String.format("%s.set%s(new %s(s[%s]))", var, getName2(),type,value) ;
        }
          
         else
         {
             String tempType = JavaTypeHelper.convertPrimitiveToWrapper(type);
              ret = String.format("%s.set%s(new %s(s[%s]))", var, getName2(),tempType,value) ;
         }
        return ret;
    }
    
    
    
    
    
    
}
