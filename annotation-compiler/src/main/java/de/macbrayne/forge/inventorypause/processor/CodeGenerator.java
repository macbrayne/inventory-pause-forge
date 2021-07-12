package de.macbrayne.forge.inventorypause.processor;

import com.squareup.javapoet.*;

import javax.annotation.processing.Filer;
import javax.lang.model.element.Modifier;
import javax.lang.model.type.TypeMirror;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

public class CodeGenerator {
    public void generateCode(List<TypeMirror> mirrorList, Filer filer) throws IOException {
        ClassName reference = ClassName.get("de.macbrayne.forge.inventorypause.utils", "Reference");

        List<TypeName> typeNames = mirrorList.stream().map(TypeName::get).collect(Collectors.toList());

        MethodSpec.Builder registerBuilder = MethodSpec.methodBuilder("register")
                .addModifiers(Modifier.PUBLIC)
                .returns(void.class);
        typeNames.forEach(typeName -> registerBuilder.addStatement("$T.getModScreenDictionary().register($T.class, () -> true)", reference, typeName));

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
