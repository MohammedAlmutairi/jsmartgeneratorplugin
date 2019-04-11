/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.jsmartgenerator.datastruture;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.stream.Stream;

 

/**
 *
 * @author Mohammed
 */
public class AllEntityies extends ArrayList<EntityInfo> {
    
    public AllEntityies(List<EntityInfo> data)
    {
        super(data);
    }
    public String findFirstStringFor(String type)
    {
      
       Optional<Column> opt =  this.stream()
            .filter(e -> e.getElement().getQualifiedName().toString().equals(type))
            .flatMap(i -> i.getCols().stream() )
            .filter(col -> col.getType().contains("String") )
            .findFirst()
            
            ;
       
       return opt.map(p -> "."+p.getName()).orElse("");
            
    }
    
    
}
