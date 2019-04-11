package org.netbeans.jsmartgenerator.util;

import java.io.File;
import java.io.IOException;
import javax.swing.JOptionPane;
import org.netbeans.api.java.project.JavaProjectConstants;
import org.netbeans.api.project.Project;
import org.netbeans.api.project.ProjectUtils;
import org.netbeans.api.project.SourceGroup;
import org.netbeans.api.project.Sources;
import org.openide.filesystems.FileObject;
import org.openide.filesystems.FileUtil;
import org.openide.loaders.DataFolder;
import org.openide.loaders.DataObject;
import org.openide.loaders.DataObjectNotFoundException;
import org.openide.util.Exceptions;

/**
 * Mohammed
 *
 * @author Mohammed
 */
public class PackageUtil {
    
    private static DataFolder findMainPackageBySrcFolder(FileObject javaSrcFolder) {
      
        DataFolder df = DataFolder.findFolder(javaSrcFolder);
        
        DataFolder mainPacksgeFolder = null;
        
        if (df.getChildren().length > 1)
        {
            JOptionPane.showMessageDialog(null, "on main/java more than one directory so this will cause problem");
            return df;
        }
      
        while (df.getChildren().length == 1) {

           
            
            if (df.getChildren()[0].getName().equals("domain")) {
                break;
            } else if (df.getChildren()[0] instanceof DataFolder) {
                mainPacksgeFolder = (DataFolder) df.getChildren()[0];
                df = mainPacksgeFolder;
            } else {
                break;
            }
        }
        
        
        
        return mainPacksgeFolder;
    }

    public static DataFolder findRootPackge(Project p)
    {
        
        FileObject o = findSrc(p);
   
          
        return findMainPackageBySrcFolder(o);
    
    }

    public static DataFolder findMainPackage(FileObject anyJavaPackage) {
        File parent = FileUtil.toFile(anyJavaPackage);
        File mainPackage = parent;

        while (!parent.getName().equals("java")) {
            
            parent = parent.getParentFile();

            if (parent.list().length != 1) {
                mainPackage = parent;
            }
        }

        FileObject main = FileUtil.toFileObject(mainPackage);
        DataFolder mainfolder = DataFolder.findFolder(main);
        
 
        

        return mainfolder;

    }
    
    public  static FileObject findSrc(Project project) {
        Sources sources = ProjectUtils.getSources(project);
        if (sources == null) {
          
            return null;
        }
        SourceGroup[] groups = sources.getSourceGroups(JavaProjectConstants.SOURCES_TYPE_JAVA);
        for (SourceGroup group : groups) {
            FileObject fo = group.getRootFolder();
            

            if (fo.getPath().endsWith("\\src\\main\\java") || fo.getPath().endsWith("\\src\\java")
                    || fo.getPath().endsWith("/src/main/java") || fo.getPath().endsWith("/src/java")) {
                return fo;
            }
        }
        
        
        return null;
    }

    public static DataFolder findOrCreateDataFolder(DataFolder main, String name) {
       
        
        DataFolder folder = findDataFolder(main, name);
        if (folder != null) {
            
            return folder;
        }
        FileObject newFolder = null;
        try {
            newFolder = FileUtil.createFolder(main.getPrimaryFile(), name);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "Error of creating new Folder");
        }

        return DataFolder.findFolder(newFolder);

    }
    public static DataFolder findOrCreateNestingDataFolder(DataFolder main, String name) {
         
       String[] sub = name.split("\\.");
       DataFolder rec= main;
       for (String s:sub)
       {
            
           DataFolder folder = findOrCreateDataFolder(rec, s);
           rec = folder;
       }
       return rec;
    }
    
    
    
    

    private static DataFolder findDataFolder(DataFolder folder, String name) {
        DataObject[] dataObjects = folder.getChildren();
        for (DataObject object : dataObjects) {

            if (name.equals(object.getName())) {
                return (DataFolder) object;
            }
        }
        return null;
    }

    public static String findPackageName(DataFolder folder) {
        File parent = FileUtil.toFile(folder.getPrimaryFile());
        StringBuilder sb = new StringBuilder();

        while (!parent.getName().equals("java")) {

            if (sb.toString().isEmpty()) {
                sb.append(parent.getName());
//                sb.insert(0, parent.getName());
            } else {
                sb.insert(0, ".");
                 sb.insert(0, parent.getName());
//                sb.append(".").append(parent.getName());
            }

            parent = parent.getParentFile();

        }

        if (sb.toString().isEmpty()) {
            return "";
        }
        return sb.toString();
    }

    
    public static DataFolder findWebAppFolder(Project p)
    { 
        FileObject javaSrc = PackageUtil.findSrc(p);
        
        try {
            DataFolder mainDirecory = DataObject.find(javaSrc).getFolder();
            return PackageUtil.findOrCreateDataFolder(mainDirecory, "webapp");
        } catch (DataObjectNotFoundException ex) {
            return null;
        }
    }
}
