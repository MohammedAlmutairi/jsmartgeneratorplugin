/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.netbeans.jsmartgenerator.compiler;

import java.util.List;
import javax.lang.model.element.TypeElement;

/**
 *
 * @author Mohammed
 */
@FunctionalInterface
public interface CompilePackageFeedBack {
    
    public void compileComplete(List<TypeElement> elements);
}
