package org.netbeans.jsmartgenerator.freemaker;

import java.awt.EventQueue;
import javax.swing.JOptionPane;
import org.netbeans.jsmartgenerator.util.PackageUtil;
import org.openide.loaders.DataFolder;

/**
 * Mohammed
 *
 * @author Mohammed
 */
public final class DataFolderContainer {

    DataFolder facadeFolder;
    DataFolder converterFolder;
    DataFolder mbLazyFolder;
    DataFolder mainFolder; 
    DataFolder entityFolder;
    DataFolder webApp;
    DataFolder pageDirectory;
    DataFolder compsiteDirectory;
    String pageDirecotryAsString;
    String entityDirectoryName;

    public DataFolderContainer(DataFolder mainPackage,DataFolder entityFolder,DataFolder webAppDir) {
        
 
        this.mainFolder = mainPackage;
       
        setFacadeFolder(PackageUtil.findOrCreateNestingDataFolder(mainFolder, "infastrcuture.facade"));
       
        setConverterFolder(PackageUtil.findOrCreateNestingDataFolder(mainFolder, "web.converter"));
         
        setMbLazyFolder(PackageUtil.findOrCreateNestingDataFolder(mainFolder, "web.mb.lazy." + entityFolder.getName()));
        
        this.entityFolder = entityFolder;
        this.webApp = webAppDir;
        setPageDirectory(PackageUtil.findOrCreateNestingDataFolder(webApp, "crudpage."+entityFolder.getName()));
        setCompsiteDirectory(PackageUtil.findOrCreateNestingDataFolder(webApp, "resources.form."+entityFolder.getName()));
        setEntityDirectoryName(entityFolder.getName());
        setPageDirecotryAsString("crudpage/"+entityFolder.getName());
        

    }

    public DataFolder getFacadeFolder() {
        return facadeFolder;
    }

    public void setFacadeFolder(DataFolder facadeFolder) {
        this.facadeFolder = facadeFolder;
    }

    public DataFolder getConverterFolder() {
        return converterFolder;
    }

    public void setConverterFolder(DataFolder converterFolder) {
        this.converterFolder = converterFolder;
    }

    public DataFolder getMbLazyFolder() {
        return mbLazyFolder;
    }

    public void setMbLazyFolder(DataFolder mbLazyFolder) {
        this.mbLazyFolder = mbLazyFolder;
    }

    public DataFolder getMainFolder() {
        return mainFolder;
    }

    public void setMainFolder(DataFolder mainFolder) {
        this.mainFolder = mainFolder;
    }

    public DataFolder getEntityFolder() {
        return entityFolder;
    }

    public void setEntityFolder(DataFolder entityFolder) {
        this.entityFolder = entityFolder;
    }
    
    
    
    public String getConverterPackqge() { return PackageUtil.findPackageName(getConverterFolder()); }
    public String getEntityPackage() { return PackageUtil.findPackageName(getEntityFolder()); }
    public String getFacadePackage() { return PackageUtil.findPackageName(getFacadeFolder()); }
    public String getmbPackage() { return PackageUtil.findPackageName(getMbLazyFolder()); }

    public DataFolder getWebApp() {
        return webApp;
    }

    public void setWebApp(DataFolder webApp) {
        this.webApp = webApp;
    }

    public DataFolder getPageDirectory() {
        return pageDirectory;
    }

    public DataFolder getCompsiteDirectory() {
        return compsiteDirectory;
    }

    public String getPageDirecotryAsString() {
        return pageDirecotryAsString;
    }

    public void setPageDirecotryAsString(String pageDirecotryAsString) {
        this.pageDirecotryAsString = pageDirecotryAsString;
    }

    public void setPageDirectory(DataFolder pageDirectory) {
        this.pageDirectory = pageDirectory;
    }

    public void setCompsiteDirectory(DataFolder compsiteDirectory) {
        this.compsiteDirectory = compsiteDirectory;
    }

    public String getEntityDirectoryName() {
        return entityDirectoryName;
    }

    public void setEntityDirectoryName(String entityDirectoryName) {
        this.entityDirectoryName = entityDirectoryName;
    }

    
    
   
    
    
}
