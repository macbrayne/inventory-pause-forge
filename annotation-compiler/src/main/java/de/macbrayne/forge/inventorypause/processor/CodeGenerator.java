package de.macbrayne.forge.inventorypause.processor;

import com.squareup.javapoet.*;
import de.macbrayne.forge.inventorypause.annotation.RegisterCompat;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

import javax.annotation.processing.Filer;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.TypeElement;
import javax.lang.model.element.VariableElement;
import javax.lang.model.type.TypeMirror;
import java.io.IOException;
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

            TypeSpec registration = TypeSpec.classBuilder(getGeneratedClassName(parentClass))
                    .addSuperinterface(genericModCompat)
                    .addModifiers(Modifier.PUBLIC, Modifier.FINAL)
                    .addMethod(registerBuilder.build())
                    .addMethod(getConfigKey)
                    .build();

            JavaFile javaFile = JavaFile.builder("de.macbrayne.forge.inventorypause.compat.mod", registration)
                    .indent("    ")
                    .build();

            javaFile.writeTo(filer);
        }
    }

    public void generateClientSetup(Set<TypeElement> parentClasses, Filer filer) throws IOException {
        MethodSpec.Builder clientSetup = MethodSpec.methodBuilder("clientSetup")
                .addModifiers(Modifier.FINAL)
                .addModifiers(Modifier.STATIC)
                .addModifiers(Modifier.PUBLIC)
                .addParameter(FMLClientSetupEvent.class, "event");

        ClassName modList = ClassName.get("net.minecraftforge.fml", "ModList");
        for (TypeElement parentClass : parentClasses) {
            RegisterCompat annotation = parentClass.getAnnotation(RegisterCompat.class);
            ClassName parentName = ClassName.get("de.macbrayne.forge.inventorypause.compat.mod", getGeneratedClassName(parentClass));
            CodeBlock codeBlock = CodeBlock.builder()
                    .beginControlFlow("if($T.get().isLoaded(\"$L\"))", modList, annotation.modId())
                    .addStatement("new $T().register()", parentName)
                    .endControlFlow()
                    .build();

            clientSetup.addCode(codeBlock);
        }

        TypeSpec registration = TypeSpec.classBuilder("ForgeClientSetupGen")
                .addModifiers(Modifier.PUBLIC, Modifier.FINAL)
                .addMethod(clientSetup.build())
                .build();

        JavaFile javaFile = JavaFile.builder("de.macbrayne.forge.inventorypause.utils", registration)
                .indent("    ")
                .build();

        javaFile.writeTo(filer);
    }

    private String getGeneratedClassName(TypeElement parent) {
        String configKey = parent.getAnnotation(RegisterCompat.class).configKey();
        return configKey.substring(0, 1).toUpperCase(Locale.ROOT) + configKey.substring(1) + "Gen";
    }
}
