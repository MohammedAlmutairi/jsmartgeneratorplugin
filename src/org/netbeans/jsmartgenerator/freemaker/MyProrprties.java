/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.netbeans.jsmartgenerator.freemaker;

/**
 *
 * @author Mohammed Almutairi
 */
public class MyProrprties {
    
    public static String convertrPackage = "src\\main\\java\\converter";
     public static String entityPackage = "src\\main\\java\\entities";
    public static String facadePackage = "src\\main\\java\\facade";
     public static String outputDirctory = "D:\\NetBeans 8 Workspace\\MyWeb";
     public static String primefacesForm  = "src\\main\\webapp\\form";
      public static String ManageBean  = "src\\main\\java\\mb";
     public static String subDirctory = null;
     
     
     public static String removeSrcMainJava(String str)
     {
         String ret = str.replace("src\\main\\java\\", "");
         ret = ret.replace("src/main/java/", "");
         ret = ret.replace("src//main//java//", "");
         ret = ret.replace("\\", ".");
         return ret;
     }

    static Object removeSrcMainWebApp(String str) {
                String ret = str.replace("src\\main\\webapp\\", "");
         ret = ret.replace("src/main/webapp/", "");
         ret = ret.replace("src//main//webapp//", "");
//         ret = ret.replace("\\", ".");
         return ret;
    }
 
   
    
    
}
