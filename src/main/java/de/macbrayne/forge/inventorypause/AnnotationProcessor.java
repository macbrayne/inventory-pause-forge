// SPDX-License-Identifier: EUPL-1.2

package de.macbrayne.forge.inventorypause;

import de.macbrayne.forge.inventorypause.annotations.ConfigClass;
import de.macbrayne.forge.inventorypause.annotations.ConfigField;
import de.macbrayne.forge.inventorypause.gui.components.ButtonInfo;
import it.unimi.dsi.fastutil.booleans.BooleanConsumer;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;

import java.lang.reflect.Field;
import java.util.*;
import java.util.function.BooleanSupplier;
import java.util.function.Consumer;
import java.util.function.Supplier;

public class AnnotationProcessor {

    public List<ButtonInfo> run() throws IllegalAccessException, NoSuchFieldException {
        List<ButtonInfo> buttonInfos = new ArrayList<>();
        HashMap<AnnotatedObject, Set<AnnotatedObject>> data = new HashMap<>();
        data.putAll(getAnnotatedConfigClasses(InventoryPause.class.getField("MOD_CONFIG"), InventoryPause.MOD_CONFIG));
        for(var classEntry : data.entrySet()) {
            for(var field : classEntry.getValue()) {
                ConfigField configField = field.field().getAnnotation(ConfigField.class);
                ResourceLocation iconLocation = new ResourceLocation(configField.iconLocation());
                Supplier<Boolean> supplier = () -> (Boolean) field.object();
                Consumer<Boolean> consumer = t -> {
                    try {
                        field.field().set(field.object(), t ? Boolean.TRUE : Boolean.FALSE);
                    } catch (IllegalAccessException e) {
                        throw new RuntimeException(e);
                    }
                };
                String ownerName = classEntry.getKey().field().getName();
                ButtonInfo buttonInfo = new ButtonInfo(iconLocation, Component.translatable("text.autoconfig.inventorypause.option.abilities" + (ownerName.equals("abilities") ? "" : "." + ownerName) + "." + field.field().getName()), supplier, consumer);
                buttonInfos.add(buttonInfo);
            }
        }
        return buttonInfos;
    }

    HashMap<AnnotatedObject, Set<AnnotatedObject>> getAnnotatedConfigClasses(Field aField, Object object) throws IllegalAccessException {
        HashMap<AnnotatedObject, Set<AnnotatedObject>> configFields = new HashMap<>();
        for(var field : aField.getType().getFields()) {
            var annotation = field.getAnnotation(ConfigClass.class);
            if(annotation != null) {
                AnnotatedObject annotatedObject = new AnnotatedObject(field, field.get(object));
                configFields.put(annotatedObject, getAnnotatedConfigFields(annotatedObject));
                configFields.putAll(getAnnotatedConfigClasses(annotatedObject.field(), annotatedObject.object()));
            }
        }
        return configFields;
    }

    Set<AnnotatedObject> getAnnotatedConfigFields(AnnotatedObject aField) throws IllegalAccessException {
        HashSet<AnnotatedObject> configFields = new HashSet<>();
        for(var field : aField.object().getClass().getFields()) {
            var annotation = field.getAnnotation(ConfigField.class);
            if(annotation != null) {
                configFields.add(new AnnotatedObject(field, field.get(aField.object())));
            }
        }
        return configFields;
    }

    record AnnotatedObject(Field field, Object object) {

    }
}