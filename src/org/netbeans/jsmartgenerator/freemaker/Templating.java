/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.netbeans.jsmartgenerator.freemaker;

import java.awt.EventQueue;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.swing.JOptionPane;
import org.jsmartgenerator.datastruture.AllEntityies;
import org.jsmartgenerator.datastruture.Column;
import org.jsmartgenerator.datastruture.EntityInfo;
import org.jsmartgenerator.gui.ProjectData;
import org.openide.loaders.DataFolder;
import org.openide.loaders.DataObject;

/**
 *
 * @author Mohammed Almutairi
 */
public class Templating {

    Configuration cfg;
    DataObject template;
    Map<String, Object> data;
    ProjectData projectData;

    public Templating(ProjectData projectData) {
        this.projectData = projectData;

    }

    public Templating(ProjectData projectData, String DirName) {
        this.projectData = projectData;
    }

    public Templating() {
        cfg = new Configuration();
    }

    public void jsfConvertersTemplate(List<EntityInfo> list) {

        AllEntityies all = new AllEntityies(list);

        for (EntityInfo a : list) {
            if (a.getClassName().endsWith("PK")) {
                continue;
            }
            Column.fillFirsStringOfManyToOne(a, all);

            jsfConverterTemplate(null, a);
            primeFacesForm(null, a);
            manageBean(null, a);
            compsite(null, a);
            facadeTemplate(null, a);
        }
        indexPage(null, list);
//        abstractFacadeTemplate(null);

    }

    public void generateTheCode(DataFolderContainer folderContainer, List<EntityInfo> list) {

        AllEntityies all = new AllEntityies(list);

        abstractFacadeTemplate(folderContainer);

        for (EntityInfo a : list) {
            if (a.getClassName().endsWith("PK")) {
                continue;
            }
            Column.fillFirsStringOfManyToOne(a, all);
            jsfConverterTemplate(folderContainer, a);
            primeFacesForm(folderContainer, a);
            manageBean(folderContainer, a);
            facadeTemplate(folderContainer, a);
            compsite(folderContainer, a);

        }

        indexPage(folderContainer, list);

    }

    private void jsfConverterTemplate(DataFolderContainer folder, EntityInfo entityInfo) {

        DataObject template = cfg.getTemplate("converter");

        Map<String, Object> data = new HashMap<>();

        data.put("converterPackage", folder.getConverterPackqge());

        data.put("entityPackage", folder.getEntityPackage());
        data.put("facadePackage", folder.getFacadePackage());
        data.put("entity", entityInfo);
        //             WriteToFile(String.format("%sConverter.java", entityInfo.getClassName()), projectData.getConvertrPackage());

        WriteToFile(template, folder.getConverterFolder(), String.format("%sConverter.java", entityInfo.getClassName()), data);
    }

//    private void abstractFacadeTemplate(DataFolder folder) {
//
//        template = cfg.getTemplate("AbstractFacade");
//        data = new HashMap<>();
//        String p = MyProrprties.removeSrcMainJava(projectData.getEntityPackage()) + ".facade";
//        data.put("packageName", p);
//
////             WriteToFile("AbstractFacade.java", projectData.getEntityPackage()+".facade");
//        WriteToFile(folder, "AbstractFacade.java");
//    }
    private void abstractFacadeTemplate(DataFolderContainer folder) {

        DataObject template = cfg.getTemplate("AbstractFacade");
        Map<String, Object> data = new HashMap<>();

        data.put("packageName", folder.getFacadePackage());

        WriteToFile(template, folder.getFacadeFolder(), "AbstractFacade.java", data);
    }

    private void facadeTemplate(DataFolderContainer folder, EntityInfo entityInfo) {

        DataObject template = cfg.getTemplate("facade");

        Map<String, Object> data = new HashMap<>();
//        String p = MyProrprties.removeSrcMainJava(projectData.getEntityPackage()) + ".facade";
        data.put("packageName", folder.getFacadePackage());
        data.put("entity", entityInfo);
        //             WriteToFile(entityInfo.getClassName()+"Facade.java", projectData.getEntityPackage()+".facade");

        WriteToFile(template, folder.getFacadeFolder(), entityInfo.getClassName() + "Facade.java", data);
    }

    private void primeFacesForm(DataFolderContainer folder, EntityInfo entityInfo) {

        DataObject template = cfg.getTemplate("form");
        Map<String, Object> data = new HashMap<>();
        data.put("object", entityInfo.getObjectName());
        data.put("mb", entityInfo.getManageBean());
        data.put("entity", entityInfo);
        data.put("entityDirectory", folder.getEntityDirectoryName());
        WriteToFile(template, folder.getPageDirectory(), String.format("%sForm.xhtml", entityInfo.getClassName()), data);
    }

    private void indexPage(DataFolderContainer folder, List<EntityInfo> entityInfos) {

        DataObject template = cfg.getTemplate("index");
        Map<String, Object> data = new HashMap<>();
        data.put("entities", entityInfos);

        data.put("primefacesForm", folder.getPageDirecotryAsString());

//       data.put("entityDirectory", folder.getEntityDirectoryName());
        WriteToFile(template, folder.getWebApp(), "index.xhtml", data);
    }

    private void compsite(DataFolderContainer folder, EntityInfo entityInfo) {

        DataObject template = cfg.getTemplate("compsite");
        Map<String, Object> data = new HashMap<>();
        data.put("entity", entityInfo);
        data.put("mb", entityInfo.getManageBean());

//            WriteToFile(String.format("%s.xhtml", entityInfo.getClassName()), "src//main//webapp//resources//form");
        WriteToFile(template, folder.getCompsiteDirectory(), String.format("%s.xhtml", entityInfo.getSimpleName2()), data);
    }

    private void manageBean(DataFolderContainer folder, EntityInfo entityInfo) {

        DataObject template = cfg.getTemplate("mb");
        Map<String, Object> data = new HashMap<>();
        data.put("entity", entityInfo);
        data.put("facadePackage", folder.getFacadePackage());
        data.put("entityPackage", folder.getEntityPackage());
        data.put("manageBeanPackage", folder.getmbPackage());

        //            WriteToFile(String.format("%sMB.java", entityInfo.getClassName()), projectData.getManageBean());
        WriteToFile(template, folder.getMbLazyFolder(), String.format("%sMB.java", entityInfo.getClassName()), data);

    }

//    public void WriteToFile(String name, String subDirectory) {
//
//        try {
//            String dir = projectData.getOutputDirctory();
//            if (subDirectory != null) {
//                if (subDirectory.contains(".")) {
//                    subDirectory = subDirectory.replace(".", "\\");
//                }
//                dir = dir + "\\" + subDirectory;
//                File f = new File(dir);
//                if (!f.exists()) {
//                    f.mkdirs();
//                }
//
//            }
//            String filename = dir + "\\" + name;
//            try (Writer file = new FileWriter(new File(filename))) {
//                template.process(data, file);
//                file.flush();
//            }
//        } catch (IOException | TemplateException ex) {
//            ex.printStackTrace();
//        }
//
//    }
    public void WriteToFile(DataFolder containFolder, String name) {
        EventQueue.invokeLater(
                () -> {
                    try {
                        DataObject o = template.createFromTemplate(containFolder, name, data);
                    } catch (IOException ex) {
                        JOptionPane.showConfirmDialog(null, "Erroe no templatews");
                    }
                }
        );
    }

    public void WriteToFile(DataObject template, DataFolder containFolder, String name, Map<String, Object> data) {
        EventQueue.invokeLater(
                () -> {
                    try {
                        for (DataObject c : containFolder.getChildren()) {
                            if (c.getName().equals(name)) {
                                return;
                            }
                        }
                        DataObject o = template.createFromTemplate(containFolder, name, data);
                    } catch (IOException ex) {
                        JOptionPane.showConfirmDialog(null, "Erroe no templatews");
                    }
                }
        );
    }

}
