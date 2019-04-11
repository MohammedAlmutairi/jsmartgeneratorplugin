/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.netbeans.jsmartgenerator.compiler;

import com.sun.source.tree.ClassTree;
import com.sun.source.util.TreePathScanner;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;
import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;
import javax.swing.JOptionPane;
import org.netbeans.api.java.classpath.ClassPath;
import org.netbeans.api.java.source.ClasspathInfo;
import org.netbeans.api.java.source.CompilationController;
import org.netbeans.api.java.source.CompilationInfo;
import org.netbeans.api.java.source.JavaSource;
import org.netbeans.api.java.source.Task;
import org.netbeans.api.project.FileOwnerQuery;
import org.netbeans.api.project.Project;
import org.netbeans.api.project.ProjectUtils;
import org.netbeans.api.project.SourceGroup;
import org.netbeans.api.project.Sources;
import org.openide.loaders.DataObject;
import org.openide.filesystems.FileObject;
import org.openide.loaders.DataFolder;
import org.netbeans.api.java.project.JavaProjectConstants;

/**
 *
 * @author Mohammed
 */
public class CompileJavaPackage {

    int index, size;
  
    List<TypeElement> typeElements;

    
    CompilePackageFeedBack compilePackageFeedBack;

    public CompileJavaPackage(CompilePackageFeedBack compilePackageFeedBack) {
        this.compilePackageFeedBack = compilePackageFeedBack;
    }
    
    
    
    
    public void reset() {
        index = 0;
        size = 0;
        typeElements = new ArrayList<>();
    }

    public void compileJavaPackage(DataFolder folder) {

        
        reset();
         
        List<FileObject> allClasses = new ArrayList<>();
        for (DataObject do2 : folder.getChildren()) {
            FileObject o = do2.getPrimaryFile();
            JavaSource src = JavaSource.forFileObject(o);
            if (src != null) {
                allClasses.add(o);
                size++;
            }
        }
         
        Project p = FileOwnerQuery.getOwner(folder.getPrimaryFile());
        
        SourceGroup[] groups = getJavaSourceGroups(p);
         
        SourceGroup g = groups[0];
       
         
        ClasspathInfo ci = ClasspathInfo.create(ClassPath.EMPTY,
                ClassPath.getClassPath(g.getRootFolder(), ClassPath.COMPILE), ClassPath.getClassPath(g.getRootFolder(), ClassPath.SOURCE));
         
        JavaSource sources = JavaSource.create(ci, allClasses);
      
        compileMultipleClasses(sources); //error
        
    }

    public static SourceGroup[] getJavaSourceGroups(Project project) {
        Sources s = ProjectUtils.getSources(project);
        return s.getSourceGroups(JavaProjectConstants.SOURCES_TYPE_JAVA);
    }

    private void compileMultipleClasses(JavaSource sources) {

        try {
            sources.runUserActionTask(new Task<CompilationController>() {
                @Override
                public void run(CompilationController controller) throws IOException {
                      
                    controller.toPhase(JavaSource.Phase.ELEMENTS_RESOLVED);
                     
                    new MemberVisitor(controller).scan(controller.getCompilationUnit(), null);
                     
                }
            }, true);
        } catch (IOException ex) {
            Msg("Error");
        }

    }

    private  class MemberVisitor extends TreePathScanner<Void, Void> {

        private CompilationInfo info;

        public MemberVisitor(CompilationInfo info) {
            this.info = info;
        }

        @Override
        public Void visitClass(ClassTree t, Void v) {
             
            Element el = info.getTrees().getElement(getCurrentPath());
            
            if (el != null) {
               
                TypeElement te = (TypeElement) el;
                  
                typeElements.add(te);
                  
                index++;
                
                if (index == size) {
                    
                    if (compilePackageFeedBack != null)
                    {
                         
                            compilePackageFeedBack.compileComplete(typeElements);
                         
                        
                        
                    }
                    
                }
            }
            return null;
        }

    }

    public static void Msg(Object o) {
        JOptionPane.showMessageDialog(null, o);

    }

}
