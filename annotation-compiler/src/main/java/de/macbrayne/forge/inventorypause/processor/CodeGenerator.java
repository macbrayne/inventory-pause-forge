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
        for(TypeElement parentClass : parentClasses) {
            Map<VariableElement, TypeMirror> filteredMap = mirrorMap.entrySet().stream()
                    .filter(variableElementTypeMirrorEntry -> variableElementTypeMirrorEntry.getKey()
                            .getEnclosingElement()
                            .getSimpleName()
                            .equals(parentClass.getSimpleName()))
                    .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
            String configKey = parentClass.getAnnotation(RegisterCompat.class).configKey();

            if(filteredMap.size() == 0) {
                continue;
            }

            ClassName genericModCompat = ClassName.get("de.macbrayne.forge.inventorypause.compat.mod", "GenericModCompat");
            ClassName reference = ClassName.get("de.macbrayne.forge.inventorypause.utils", "Reference");
            ClassName modConfig = ClassName.get("de.macbrayne.forge.inventorypause.common", "ModConfig");
            ClassName autoConfig = ClassName.get("me.shedaniel.autoconfig", "AutoConfig");

            MethodSpec getConfigKey = MethodSpec.methodBuilder("getConfigKey")
                    .returns(boolean.class)
                    .addAnnotation(Override.class)
                    .addModifiers(Modifier.PUBLIC)
                    .addStatement("return config.modCompat.$L", configKey)
                    .build();

            MethodSpec.Builder registerBuilder = MethodSpec.methodBuilder("register")
                    .addModifiers(Modifier.PUBLIC)
                    .returns(void.class);
            String parentSimple = parentClass.getSimpleName().toString();
            String parentLowerCamelCase = parentSimple.substring(0, 1).toLowerCase(Locale.ROOT) + parentSimple.substring(1);
            filteredMap.forEach((key, value) -> registerBuilder.addStatement("$T.getModScreenDictionary().register($T.class, () -> getConfigKey() && config.modCompat.fineTuning.$L.$L)",
                    reference, value, parentLowerCamelCase, key.getSimpleName()));

            String outputNameCamelCase = configKey.substring(0, 1).toUpperCase(Locale.ROOT) + configKey.substring(1);

            TypeSpec registration = TypeSpec.classBuilder(outputNameCamelCase + "Gen")
                    .addSuperinterface(genericModCompat)
                    .addModifiers(Modifier.PUBLIC, Modifier.FINAL)
                    .addField(modConfig, "config", Modifier.FINAL)
                    .addMethod(MethodSpec.constructorBuilder()
                            .addModifiers(Modifier.PUBLIC)
                            .addStatement("config = $T.getConfigHolder($T.class).getConfig()", autoConfig, modConfig)
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
