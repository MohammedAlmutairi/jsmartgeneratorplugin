/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ${manageBeanPackage};
import javax.ejb.EJBException;
import  ${facadePackage}.${entity.className}Facade;
import ${entity.element.qualifiedName};
import java.util.List;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import java.io.Serializable;
import java.util.Map;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;


           <#assign jsf='#{'/>
/**
 *
 * @author JSmartGenerator Tools
 */
@Named(value = "${entity.manageBean}")
@ViewScoped
public class ${entity.className}MB extends LazyDataModel<${entity.className}>  implements Serializable   {

 
    ${entity.className} ${entity.objectName} = new ${entity.className}();
    @EJB ${entity.className}Facade ${entity.objectName}Facade;

    
    

    
    public ${entity.className}MB() {
    }

    public ${entity.className}  get${entity.className}() {
        return ${entity.objectName};
    }

    public void set${entity.className}(${entity.className} v) {
        this.${entity.objectName} = v;
    }
     public void addMsg(String msg) {
        FacesContext context = FacesContext.getCurrentInstance();
        context.addMessage(null, new FacesMessage(msg));
    }

   public void remove(${entity.className} v)
   {
      ${entity.objectName}Facade.remove(v);
   }
   public void new${entity.className}()
   {
      ${entity.objectName} = new ${entity.className}(); 
   }



   public void save() {
        try {
            if (${entity.objectName}.get${entity.pk.name2}() == null) {
                ${entity.objectName}Facade.create(${entity.objectName});
               ${entity.objectName} = new ${entity.className}();
            } else {
                 ${entity.objectName}Facade.edit(${entity.objectName});
            }
            addMsg("Saved is OK");
        } catch (EJBException exception) {
            addMsg("Problem in Save");
        }

    }
    @Override
    public List<${entity.className}> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String,Object> filters) 
    {
    
        setRowCount(${entity.objectName}Facade.count());
        return ${entity.objectName}Facade.find(first, pageSize);
    }
    

}
