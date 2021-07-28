package de.macbrayne.forge.inventorypause.processor;

import com.squareup.javapoet.*;
import de.macbrayne.forge.inventorypause.annotation.RegisterCompat;

import javax.annotation.processing.Filer;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.Name;
import javax.lang.model.element.TypeElement;
import javax.lang.model.element.VariableElement;
import javax.lang.model.type.TypeMirror;
import java.io.IOException;
import java.lang.reflect.Type;
import java.sql.Types;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class CodeGenerator {
    public void generateCode(Set<TypeElement> parentClasses, Map<VariableElement, TypeMirror> mirrorMap, Filer filer) throws IOException {
        /*Name[] distinctClassNames = mirrorMap.keySet().stream()
                .map(variableElement -> variableElement.getEnclosingElement().getSimpleName()).distinct().toArray(Name[]::new);*/
        // Name[] distinctClassNames = parentClasses.stream().map(TypeElement::getSimpleName).toArray(Name[]::new);
        for(TypeElement parentClass : parentClasses) {
            Map<VariableElement, TypeMirror> filteredMap = mirrorMap.entrySet().stream()
                    .filter(variableElementTypeMirrorEntry -> variableElementTypeMirrorEntry.getKey()
                            .getEnclosingElement()
                            .getSimpleName()
                            .equals(parentClass.getSimpleName()))
                    .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));

            if(filteredMap.size() == 0) {
                continue;
            }

            ClassName reference = ClassName.get("de.macbrayne.forge.inventorypause.utils", "Reference");
            ClassName modConfig = ClassName.get("de.macbrayne.forge.inventorypause.common", "ModConfig");
            ClassName autoConfig = ClassName.get("me.shedaniel.autoconfig", "AutoConfig");

            TypeMirror enclosingClassType = filteredMap.keySet().iterator().next()
                    .getEnclosingElement().getEnclosingElement().asType();

            MethodSpec getConfigKey = MethodSpec.methodBuilder("getConfigKey")
                    .returns(boolean.class)
                    .addStatement("return config.modCompat.$L", parentClass.getAnnotation(RegisterCompat.class).configKey())
                    .build();

            MethodSpec.Builder registerBuilder = MethodSpec.methodBuilder("register")
                    .addModifiers(Modifier.PUBLIC)
                    .returns(void.class);
            filteredMap.forEach((key, value) -> registerBuilder.addStatement("$T.getModScreenDictionary().register($T.class, () -> getConfigKey() && config.modCompat.fineTuning.$L.$L)",
                    reference, value, key.getEnclosingElement().getSimpleName().toString().substring(0, 1).toLowerCase(Locale.ROOT) + key.getEnclosingElement().getSimpleName().toString().substring(1), key.getSimpleName()));


            TypeSpec registration = TypeSpec.classBuilder(parentClass.getSimpleName().toString() + "Gen")
                    .addModifiers(Modifier.PUBLIC, Modifier.FINAL)
                    .addField(modConfig, "config", Modifier.FINAL)
                    .addField(TypeName.get(enclosingClassType), "registration", Modifier.FINAL)
                    .addMethod(MethodSpec.constructorBuilder()
                            .addModifiers(Modifier.PUBLIC)
                            .addStatement("config = $T.getConfigHolder($T.class).getConfig()", autoConfig, modConfig)
                            .addStatement("registration = new $T()", enclosingClassType)
                            .build())
                    .addMethod(registerBuilder.build())
                    .addMethod(getConfigKey)
                    .build();

            JavaFile javaFile = JavaFile.builder("de.macbrayne.forge.inventorypause.compat.mod", registration)
                    .indent("    ")
                    .build();

            javaFile.writeTo(filer);
        }
    }
}
