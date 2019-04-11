/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.jsmartgenerator.util;

/**
 *
 * @author Mohammed Almutairi
 */
public class JavaTypeHelper {
    
    public static String convertPrimitiveToWrapper(String type) {
        String tempType = null;
        switch (type) {
            case "int":
                tempType = "java.lang.Integer";
                break;
            case "short":
                tempType = "java.lang.Short";
                break;
            case "long":
                tempType = "java.lang.Short";
                break;

        }
        return tempType;
    }
    
    public String textJAva()
    {
        String ret ;
        
        if (true)
        {
          ret = "1";  
        }
        else
        {
            ret = "2";
        }
        
        return ret;
    }
    
}
