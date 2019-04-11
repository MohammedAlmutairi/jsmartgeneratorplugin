package org.netbeans.jsmartgenerator.freemaker;

import javax.swing.JOptionPane;
import org.openide.filesystems.FileUtil;
import org.openide.loaders.DataObject;
import org.openide.loaders.DataObjectNotFoundException;
import org.openide.util.Exceptions;

/**
 * Mohammed
 *
 * @author Mohammed
 */
public class Configuration {

    private String folderName = "mytemplates/";

    public DataObject getTemplate(String name) {
        try {
            DataObject ret = DataObject.find(FileUtil.getConfigFile(folderName + name));
            return ret;
        } catch (DataObjectNotFoundException ex) {
          
       
            return null;
        }

    }

}
