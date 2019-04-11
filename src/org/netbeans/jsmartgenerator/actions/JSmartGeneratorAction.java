/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.netbeans.jsmartgenerator.actions;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import javax.lang.model.element.TypeElement;
import javax.swing.JOptionPane;
import org.jsmartgenerator.Application;
import org.netbeans.api.project.FileOwnerQuery;
import org.netbeans.api.project.Project;
import org.netbeans.jsmartgenerator.compiler.CompileJavaPackage;
import org.netbeans.jsmartgenerator.compiler.CompilePackageFeedBack;
import org.netbeans.jsmartgenerator.freemaker.DataFolderContainer;
import org.netbeans.jsmartgenerator.util.PackageUtil;
import org.openide.awt.ActionID;
import org.openide.awt.ActionReference;
import org.openide.awt.ActionRegistration;
import org.openide.filesystems.FileObject;
import org.openide.loaders.DataFolder;

import org.openide.util.NbBundle.Messages;

@ActionID(
        category = "Build",
        id = "org.netbeans.jsmartgenerator.actions.JSmartGeneratorAction"
)
@ActionRegistration(
        displayName = "JSmartGenerator Tools"
)
@ActionReference(path = "Projects/package/Actions", position = 0)
@Messages("CTL_JSmartGeneratorAction=JSmartGeneratorAction")
public final class JSmartGeneratorAction implements ActionListener, CompilePackageFeedBack {

    DataFolder folder;
    FileObject fo;
    DataFolder rootPackage;
    DataFolder webapp;

    public JSmartGeneratorAction(DataFolder folder) {
        this.folder = folder;
    }

    @Override
    public void actionPerformed(ActionEvent e) {

     
        
        fo = folder.getPrimaryFile();
 
        Project p = FileOwnerQuery.getOwner(fo);
 

        webapp = PackageUtil.findWebAppFolder(p);
   
        rootPackage = PackageUtil.findRootPackge(p);
       
  

        CompileJavaPackage compileJavaPackage = new CompileJavaPackage(this);
         
        compileJavaPackage.compileJavaPackage(folder); //error
        

    }

    @Override
    public void compileComplete(List<TypeElement> elements) {
 
          
        DataFolderContainer container = new DataFolderContainer(rootPackage, folder, webapp);
       
        Application application = new Application(elements, container);
    
        application.generate();

    }

}
