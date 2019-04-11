package org.jsmartgenerator;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.List;
import javax.lang.model.element.TypeElement;
import org.jsmartgenerator.converter.TypeElementUtil;
import org.jsmartgenerator.datastruture.EntityInfo;
import org.netbeans.jsmartgenerator.freemaker.DataFolderContainer;
import org.netbeans.jsmartgenerator.freemaker.Templating;
import org.openide.util.Exceptions;

/**
 * Mohammed
 *
 * @author Mohammed
 */


public class Application {

    List<TypeElement> elements;
    DataFolderContainer container;

    public Application(List<TypeElement> elements, DataFolderContainer container) {
        this.elements = elements;
        this.container = container;
    }
    
    
    
    public void generate()
    {
                
        TypeElementUtil util = new TypeElementUtil(elements);
        List<EntityInfo> entityInfos = util.toEntityInfo();
        Templating templating = new Templating();
        try
        {
          templating.generateTheCode(container, entityInfos);  
        }
        catch (Exception e)
        {
           StringWriter sw = new StringWriter();
           e.printStackTrace(new PrintWriter(sw));
           String path = "C://netbeans//log.txt";
         try {
                Files.write( Paths.get(path), sw.toString().getBytes(), StandardOpenOption.CREATE);
            } catch (IOException ex) {
                Exceptions.printStackTrace(ex);
            }
           
           
           
        }
        
    
       
    }
    

    
}
