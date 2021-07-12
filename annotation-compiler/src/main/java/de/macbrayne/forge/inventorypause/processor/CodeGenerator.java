package de.macbrayne.forge.inventorypause.processor;

import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.TypeSpec;

import javax.annotation.processing.Filer;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.VariableElement;
import javax.lang.model.type.TypeMirror;
import java.io.IOException;
import java.util.Map;

public class CodeGenerator {
    public void generateCode(Map<VariableElement, TypeMirror> mirrorMap, Filer filer) throws IOException {
        ClassName reference = ClassName.get("de.macbrayne.forge.inventorypause.utils", "Reference");

        MethodSpec.Builder registerBuilder = MethodSpec.methodBuilder("register")
                .addModifiers(Modifier.PUBLIC)
                .returns(void.class);
        mirrorMap.forEach((key, value) -> registerBuilder.addStatement("$T.getModScreenDictionary().register($T.class, () -> $T.$L)",
                reference, value, key.getEnclosingElement().asType(), key.getSimpleName()));

        TypeSpec registration = TypeSpec.classBuilder("Registration")
                .addModifiers(Modifier.PUBLIC, Modifier.FINAL)
                .addMethod(registerBuilder.build())
                .build();

        JavaFile javaFile = JavaFile.builder("de.macbrayne.forge.inventorypause.compat.mod", registration)
                .indent("    ")
                .build();

        javaFile.writeTo(filer);
    }
}
