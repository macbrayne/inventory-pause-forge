// SPDX-License-Identifier: EUPL-1.2

package de.macbrayne.forge.inventorypause.gui.screens;

import com.google.common.collect.ImmutableList;
import de.macbrayne.forge.inventorypause.InventoryPause;
import de.macbrayne.forge.inventorypause.gui.components.HoverButton;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.*;
import net.minecraft.client.gui.components.events.GuiEventListener;
import net.minecraft.client.gui.narration.NarratableEntry;
import net.minecraft.network.chat.Component;
import org.apache.commons.lang3.math.NumberUtils;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.function.Function;
import java.util.function.IntConsumer;
import java.util.function.IntSupplier;
import java.util.function.Supplier;

public class ModCompatList extends ContainerObjectSelectionList<ModCompatList.Entry> {
    private static final String newEntry = Component.translatable("menu.inventorypause.settings.modCompat.new").getString();
    private final ModCompatScreen modCompatScreen;
    private final Supplier<List<String>> modCompatSupplier;
    private final Supplier<List<String>> modCustomSupplier;
    private final List<ItemEntry> removedEntries = new ArrayList<>();
    public ModCompatList(ModCompatScreen parent, Minecraft minecraft) {
        super(minecraft, parent.width, parent.height - 52, 20, 25);
        this.modCompatScreen = parent;
        modCompatSupplier = () -> InventoryPause.MOD_CONFIG.modCompat.compatScreens;
        modCustomSupplier = () -> InventoryPause.MOD_CONFIG.modCompat.customScreens;

        initEntries();
    }

    private void initEntries() {
        // Custom Screens
        this.addEntry(new ModCompatList.SectionEntry(Component.translatable("menu.inventorypause.settings.modCompat.customScreens"),
                Component.translatable("menu.inventorypause.settings.modCompat.customScreens.tooltip")));
        ArrayList<String> modCustomClasses = new ArrayList<>(modCustomSupplier.get());
        for(int i = 0; i < modCustomClasses.size(); i++) {
            String aClass = modCustomClasses.get(i);
            this.addEntry(new ModCompatList.CustomEntry(i, aClass));
        }
        this.addEntry(new AddEntry(Component.translatable("menu.inventorypause.settings.modCompat.customScreens.add"), addEntry -> button -> {
            int i = children().indexOf(addEntry);
            children().add(i, new CustomEntry(modCustomSupplier.get().size(), newEntry));
            modCustomSupplier.get().add("New Entry");
        }));

        // Time Between Compat Ticks
        this.addEntry(new ModCompatList.SectionEntry(Component.translatable("menu.inventorypause.settings.modCompat.timeBetweenCompatTicks"),
                Component.translatable("menu.inventorypause.settings.modCompat.timeBetweenCompatTicks.tooltip")));
        this.addEntry(new ModCompatList.NumEntry(() -> InventoryPause.MOD_CONFIG.modCompat.timeBetweenCompatTicks,
                value -> InventoryPause.MOD_CONFIG.modCompat.timeBetweenCompatTicks = value, 20));

        this.addEntry(new ModCompatList.SectionEntry(Component.translatable("menu.inventorypause.settings.modCompat.compatScreens"),
                Component.translatable("menu.inventorypause.settings.modCompat.compatScreens.tooltip")));
        ArrayList<String> modCompatClasses = new ArrayList<>(modCompatSupplier.get());
        for(int i = 0; i < modCompatClasses.size(); i++) {
            String aClass = modCompatClasses.get(i);
            this.addEntry(new ModCompatList.CompatEntry(i, aClass));
        }
        this.addEntry(new AddEntry(Component.translatable("menu.inventorypause.settings.modCompat.compatScreens.add"), addEntry -> (button) -> {
            int i = children().indexOf(addEntry);
            children().add(i, new CompatEntry(modCompatSupplier.get().size(), newEntry));
            modCustomSupplier.get().add("New Entry");
        }));
    }

    @Override
    public void setFocused(@Nullable GuiEventListener guiEventListener) {
        if(getFocused() != null) {
            getFocused().setFocused(false);
        }
        super.setFocused(guiEventListener);
    }

    public abstract static class Entry extends ContainerObjectSelectionList.Entry<ModCompatList.Entry> {
    }
    public class SectionEntry extends ModCompatList.Entry {
        final StringWidget title;
        final Component name;

        public SectionEntry(Component name, Component tooltip) {
            this.name = name;
            title = new StringWidget(ModCompatList.this.width, ModCompatList.this.height, name, minecraft.font).alignCenter();
            title.setTooltip(Tooltip.create(tooltip));
        }

        @Override
        public List<? extends NarratableEntry> narratables() {
            return ImmutableList.of(title);
        }

        @Override
        public void render(GuiGraphics guiGraphics, int index, int y, int x, int entryWidth, int entryHeight, int mouseX, int mouseY, boolean hovered, float tickDelta) {
            title.setX(x);
            title.setY(y);
            title.setWidth(entryWidth);
            title.setHeight(entryHeight);
            title.render(guiGraphics, mouseX, mouseY, tickDelta);
        }

        @Override
        public List<? extends GuiEventListener> children() {
            return ImmutableList.of(title);
        }
    }

    public static class AddEntry extends ModCompatList.Entry {
        final Button button;

        public AddEntry(Component text, Function<AddEntry, Button.OnPress> onPress) {
            this.button = Button.builder(text, onPress.apply(this)).size(180, 20).build();
        }

        @Override
        public List<? extends NarratableEntry> narratables() {
            return ImmutableList.of(button);
        }

        @Override
        public void render(GuiGraphics guiGraphics, int index, int y, int x, int entryWidth, int entryHeight, int mouseX, int mouseY, boolean hovered, float tickDelta) {
            this.button.setX(x);
            this.button.setY(y);
            this.button.setWidth(entryWidth);
            this.button.render(guiGraphics, mouseX, mouseY, tickDelta);
        }

        @Override
        public void setFocused(boolean state) {
            super.setFocused(state);
            button.setFocused(state);
        }

        @Override
        public List<? extends GuiEventListener> children() {
            return ImmutableList.of(button);
        }
    }

    public class ItemEntry extends ModCompatList.Entry implements Saveable {
        private final int key;
        private final String content;
        private final EditBox editBox;
        private final Button removeButton;
        private final Supplier<List<String>> supplier;

        public ItemEntry(int key, String content, Supplier<List<String>> supplier) {
            this.key = key;
            this.content = content;
            this.supplier = supplier;
            this.removeButton = new HoverButton(new Button.Builder(Component.translatable("menu.inventorypause.settings.modCompat.delete"), (button) -> {
                ModCompatList.this.removeEntry(this);
                removedEntries.add(this);
                unfocusEntry();
            }).size(20, 20).createNarration(p_253695_ -> Component.translatable("narrator.controls.reset", content)));
            this.editBox = new EditBox(ModCompatList.this.minecraft.font, 0, 0, 180, 20, Component.empty());
            editBox.setMaxLength(128);
            editBox.setValue(this.content);
            editBox.setFilter(s -> !s.contains("-"));
        }

        @Override
        public List<? extends NarratableEntry> narratables() {
            return ImmutableList.of(this.editBox, this.removeButton);
        }

        @Override
        public void setFocused(boolean state) {
            super.setFocused(state);
            if(getFocused() == editBox) {
                editBox.setFocused(state);
            }
        }



        public void render(GuiGraphics guiGraphics, int index, int y, int x, int entryWidth, int entryHeight, int mouseX, int mouseY, boolean hovered, float tickDelta) {
            this.removeButton.setX(x + 190 + 10);
            this.removeButton.setY(y);
            this.removeButton.setWidth(20);
            this.removeButton.setHeight(20);
            this.removeButton.render(guiGraphics, mouseX, mouseY, tickDelta);
            this.editBox.setX(x);
            this.editBox.setY(y);
            this.editBox.setWidth(195);
            this.editBox.render(guiGraphics, mouseX, mouseY, tickDelta);
        }

        public List<? extends GuiEventListener> children() {
            return ImmutableList.of(this.editBox, this.removeButton);
        }

        public void save() {
            if(!content.equals(this.editBox.getValue())) {
                supplier.get().set(key, this.editBox.getValue());
            }
        }

    }

    public class CompatEntry extends ItemEntry {

        public CompatEntry(int key, String content) {
            super(key, content, modCompatSupplier);
        }
    }

    public class CustomEntry extends ItemEntry {

        public CustomEntry(int key, String content) {
            super(key, content, modCustomSupplier);
        }
    }

    public class NumEntry extends Entry implements Saveable {
        private final IntSupplier valueSupplier;
        private final IntConsumer valueConsumer;
        private final EditBox numBox;
        private final Button resetButton;


        public NumEntry(IntSupplier valueSupplier, IntConsumer valueConsumer, int defaultValue) {
            this.valueSupplier = valueSupplier;
            this.valueConsumer = valueConsumer;
            this.numBox = new EditBox(ModCompatList.this.minecraft.font, 0, 0, 180, 20, Component.empty());
            this.numBox.setMaxLength(3);
            this.numBox.setValue(String.valueOf(valueSupplier.getAsInt()));
            this.numBox.setFilter(s -> s.isEmpty() || (NumberUtils.isParsable(s) && !s.contains("-")));
            this.numBox.setResponder(this::onEdit);

            this.resetButton = new HoverButton(new Button.Builder(Component.translatable("menu.inventorypause.settings.modCompat.reset"), (button) -> {
                this.numBox.setValue(String.valueOf(defaultValue));
                this.onEdit(String.valueOf(defaultValue));
            })
                    .size(40, 20).createNarration(p_253695_ -> Component.translatable("narrator.controls.reset", defaultValue))
                    .tooltip(Tooltip.create(Component.translatable("menu.inventorypause.settings.modCompat.reset.tooltip"))));
            onEdit(this.numBox.getValue());
        }

        private void onEdit(String currentValue) {
            if(currentValue.equals("20")) {
                this.resetButton.active = false;
            } else {
                this.resetButton.active = true;
            }
        }

        @Override
        public List<? extends NarratableEntry> narratables() {
            return ImmutableList.of(numBox, resetButton);
        }

        @Override
        public void render(GuiGraphics guiGraphics, int index, int y, int x, int entryWidth, int entryHeight, int mouseX, int mouseY, boolean hovered, float tickDelta) {
            this.numBox.render(guiGraphics, mouseX, mouseY, tickDelta);
            this.resetButton.render(guiGraphics, mouseX, mouseY, tickDelta);
            this.resetButton.setX(x + 190 - 10);
            this.resetButton.setY(y);
            this.resetButton.render(guiGraphics, mouseX, mouseY, tickDelta);
            this.numBox.setX(x);
            this.numBox.setY(y);
            this.numBox.setWidth(190 - 15);
            this.numBox.render(guiGraphics, mouseX, mouseY, tickDelta);
        }

        @Override
        public void setFocused(boolean state) {
            super.setFocused(state);
            if(getFocused() == numBox) {
                numBox.setFocused(state);
            }
        }

        @Override
        public List<? extends GuiEventListener> children() {
            return ImmutableList.of(numBox, resetButton);
        }

        @Override
        public void save() {
            if(numBox.getValue().isEmpty()) {
                return;
            }
            int value = Math.abs(Integer.parseInt(this.numBox.getValue()));
            if(value > 0) {
                valueConsumer.accept(value);
            }
        }
    }

    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        if (!this.isMouseOver(mouseX, mouseY)) {
            unfocusEntry();
        }
        if (this.getEntryAtPosition(mouseX, mouseY) != getFocused()) {
            unfocusEntry();
        }
        return super.mouseClicked(mouseX, mouseY, button);
    }

    private void unfocusEntry() {
        if(getFocused() != null) {
            // getFocused().setFocused(false);
            getFocused().setFocused(false);
            setFocused(null);
        }
    }

    public void saveChanges() {
        for(Entry item : children()) {
            if(item instanceof Saveable saveable) {
                saveable.save();
            }
        }
        removedEntries.sort(Comparator.comparingInt((ItemEntry o) -> o.key).reversed());
        for(ItemEntry item : removedEntries) {
            item.supplier.get().remove(item.key);
        }
    }

    interface Saveable {
        void save();
    }
}
