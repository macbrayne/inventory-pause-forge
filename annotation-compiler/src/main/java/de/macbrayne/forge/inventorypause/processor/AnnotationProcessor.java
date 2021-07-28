package de.macbrayne.forge.inventorypause.processor;

import de.macbrayne.forge.inventorypause.annotation.RegisterClass;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.annotation.processing.SupportedSourceVersion;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.TypeElement;
import javax.lang.model.element.VariableElement;
import javax.lang.model.type.PrimitiveType;
import javax.lang.model.type.TypeKind;
import javax.lang.model.type.TypeMirror;
import javax.lang.model.util.ElementFilter;
import javax.tools.Diagnostic;
import java.io.IOException;
import java.util.*;

@SupportedAnnotationTypes("de.macbrayne.forge.inventorypause.annotation.*")
@SupportedSourceVersion(SourceVersion.RELEASE_8)
public class AnnotationProcessor extends AbstractProcessor {
    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment env) {
        Map<VariableElement, TypeMirror> processedAnnotations = new HashMap<>();

        annotations.forEach(annotation -> {
            Set<? extends Element> elements = env.getElementsAnnotatedWith(annotation);
            Set<VariableElement> fields = ElementFilter.fieldsIn(elements);
            for (VariableElement field : fields) {
                TypeMirror fieldType = field.asType();

                if(!(fieldType instanceof PrimitiveType) || fieldType.getKind() != TypeKind.BOOLEAN) {
                    processingEnv.getMessager().printMessage(Diagnostic.Kind.ERROR, "Field type must be boolean", field);
                    continue;
                }
                String className = RegisterClass.class.getName();

                field.getAnnotationMirrors().stream()
                        .filter(annotationMirror -> annotationMirror.getAnnotationType().toString().equals(className))
                        .map(annotationMirror -> annotationMirror.getElementValues().entrySet().stream())
                        .forEach(entryStream -> entryStream.filter(entry -> entry.getKey().toString().equals("value()"))
                                .forEach(entry -> processedAnnotations.put(field, (TypeMirror) entry.getValue().getValue())));
            }
        });

        if(!processedAnnotations.isEmpty()) {
            try {
                CodeGenerator codeGenerator = new CodeGenerator();
                codeGenerator.generateCode(processedAnnotations, processingEnv.getFiler());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return true;
    }

}
