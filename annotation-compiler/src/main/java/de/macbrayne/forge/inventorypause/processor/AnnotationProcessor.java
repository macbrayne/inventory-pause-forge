package de.macbrayne.forge.inventorypause.processor;

import de.macbrayne.forge.inventorypause.annotation.RegisterClass;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.annotation.processing.SupportedSourceVersion;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;
import javax.lang.model.element.VariableElement;
import javax.lang.model.type.PrimitiveType;
import javax.lang.model.type.TypeKind;
import javax.lang.model.type.TypeMirror;
import javax.lang.model.util.ElementFilter;
import javax.tools.Diagnostic;
import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@SupportedAnnotationTypes({
        "de.macbrayne.forge.inventorypause.annotation.RegisterClass",
        "de.macbrayne.forge.inventorypause.annotation.RegisterCompat"
})
@SupportedSourceVersion(SourceVersion.RELEASE_8)
public class AnnotationProcessor extends AbstractProcessor {
    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment env) {
        Set<TypeElement> rawTypes = new HashSet<>();
        Set<TypeElement> processedTypes = new HashSet<>();
        Map<VariableElement, TypeMirror> processedAnnotations = new HashMap<>();

        annotations.forEach(annotation -> {
            Set<? extends Element> elements = env.getElementsAnnotatedWith(annotation);

            Set<TypeElement> types = ElementFilter.typesIn(elements);
            rawTypes.addAll(types);
        });

        annotations.forEach(annotation -> {
            Set<? extends Element> elements = env.getElementsAnnotatedWith(annotation);

            Set<TypeMirror> typeMirrors = rawTypes.stream().map(Element::asType).collect(Collectors.toSet());

            Set<VariableElement> fields = ElementFilter.fieldsIn(elements);
            for (VariableElement field : fields) {
                TypeMirror fieldType = field.asType();

                if(!typeMirrors.contains(field.getEnclosingElement().asType())) {
                    processingEnv.getMessager().printMessage(Diagnostic.Kind.ERROR, "Parent class must be annotated with RegisterCompat", field);
                    continue;
                }
                TypeElement parentType = rawTypes.stream().filter(typeMirror -> typeMirror.equals(field.getEnclosingElement())).findAny().orElseThrow(() -> new RuntimeException("WHAT"));
                processedTypes.add(parentType);

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
                codeGenerator.generateCode(processedTypes, processedAnnotations, processingEnv.getFiler());
                codeGenerator.generateClientSetup(processedTypes, processingEnv.getFiler());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return true;
    }

}
