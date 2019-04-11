/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ${packageName};

import ${entity.element.qualifiedName};
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.inject.Named;
import java.util.List;
/**
 *
 * @author Mohammed
 */
@Stateless
@Named
public class ${entity.className}Facade extends AbstractFacade<${entity.className}> {

    @PersistenceContext(unitName = "PU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public ${entity.className}Facade() {
        super(${entity.className}.class);
    }

    public List< ${entity.className}> getAll() {
        javax.persistence.criteria.CriteriaQuery cq = getEntityManager().getCriteriaBuilder().createQuery();
        cq.select(cq.from(${entity.className}.class));
        return getEntityManager().createQuery(cq).getResultList();
    } 
    
}
