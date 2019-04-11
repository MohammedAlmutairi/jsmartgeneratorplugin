/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.jsmartgenerator.gui;

/**
 *
 * @author Mohammed Almutairi
 */
public class ProjectData {

    private String convertrPackage = "src\\main\\java\\converter";
    private String entityPackage = "src\\main\\java\\entities";
    private String facadePackage = "src\\main\\java\\facade";
    private String outputDirctory = "C:\\Users\\Mohammed\\Documents\\NetBeansProjects\\TestDemo\\TestDemo";
    private String primefacesForm = "src\\main\\webapp\\form";
    private String ManageBean = "src\\main\\java\\mb";
    private String subDirctory = null;

    public String getConvertrPackage() {
        return getEntityPackage() + ".converter";
        
        
        
//        return convertrPackage;
    }

    public void setConvertrPackage(String convertrPackage) {
        this.convertrPackage = convertrPackage;
    }

    public String getEntityPackage() {
        return entityPackage;
    }

    public void setEntityPackage(String entityPackage) {
        this.entityPackage = entityPackage;
    }

    public String getFacadePackage() {
        return facadePackage;
    }

    public void setFacadePackage(String facadePackage) {
        this.facadePackage = facadePackage;
    }

    public String getOutputDirctory() {
        return outputDirctory;
    }

    public void setOutputDirctory(String outputDirctory) {
        this.outputDirctory = outputDirctory;
    }

    public String getPrimefacesForm() {
        return primefacesForm;
    }

    public void setPrimefacesForm(String primefacesForm) {
        this.primefacesForm = primefacesForm;
    }

    public String getManageBean() {
        return getEntityPackage() + ".mb";
//        return ManageBean;
    }

    public void setManageBean(String ManageBean) {
        this.ManageBean = ManageBean;
    }

    public String getSubDirctory() {
        return subDirctory;
    }

    public void setSubDirctory(String subDirctory) {
        this.subDirctory = subDirctory;
    }

}
