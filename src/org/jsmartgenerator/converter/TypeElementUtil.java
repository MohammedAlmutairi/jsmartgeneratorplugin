/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.jsmartgenerator.converter;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;
import javax.lang.model.element.AnnotationMirror;
import javax.lang.model.element.TypeElement;
import javax.lang.model.element.VariableElement;
import javax.lang.model.util.ElementFilter;
import javax.swing.JOptionPane;
import org.jsmartgenerator.datastruture.Column;
import org.jsmartgenerator.datastruture.EntityInfo;

/**
 *
 * @author Mohammed
 */
public class TypeElementUtil {

    List<TypeElement> elements;

    public TypeElementUtil(List<TypeElement> elements) {
        this.elements = elements;
    }

    public List<EntityInfo> toEntityInfo() {
        Function<TypeElement, EntityInfo> mapper = element -> find(element);
        List<EntityInfo> ret = elements
                .stream()
                .map(mapper)
                .collect(Collectors.toList());

        ret.forEach(entityInfo -> Column.fillCols2(entityInfo));
        return ret;
    }

    public EntityInfo find(TypeElement element) {

        EntityInfo entityInfo = new EntityInfo();
        entityInfo.setElement(element);
        List<VariableElement> fields = ElementFilter.fieldsIn(element.getEnclosedElements());
        for (VariableElement f : fields) {

            List<AnnotationMirror> ams = (List<AnnotationMirror>) f.getAnnotationMirrors();
            for (AnnotationMirror single : ams) {

                String a = single.getAnnotationType().toString();
                boolean isId = isId(single);
                boolean isEmbeddedId = isEmbeddedId(single);
                if (isId || isEmbeddedId) {

                    entityInfo.setVariableElement(f);
                    entityInfo.setAnnotationMirror(single);
                }

                entityInfo.findePK();

                if (isEmbeddedId) {
                    TypeElement te = findTypeElementByName(f.asType().toString());
                    entityInfo.findEmbeddedId(te);
                }

            }

        }
        return entityInfo;
    }

    public boolean isId(AnnotationMirror annotationMirror) {
//        return annotationMirror.getAnnotationType().toString().equals("javax.persistence.Id");
        return annotationMirror.getAnnotationType().toString().equals("Id");
    }

    public boolean isEmbeddedId(AnnotationMirror annotationMirror) {
//        return annotationMirror.getAnnotationType().toString().equals("javax.persistence.EmbeddedId");
        return annotationMirror.getAnnotationType().toString().equals("EmbeddedId");
    }

    public TypeElement findTypeElementByName(String name) {
        return elements.stream().filter(e -> e.asType().toString().equals(name)).findFirst().get();
    }

}
