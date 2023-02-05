// SPDX-License-Identifier: EUPL-1.2

package de.macbrayne.forge.inventorypause.gui.screens;

import com.google.common.collect.ImmutableList;
import com.mojang.blaze3d.vertex.PoseStack;
import de.macbrayne.forge.inventorypause.InventoryPause;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.ContainerObjectSelectionList;
import net.minecraft.client.gui.components.EditBox;
import net.minecraft.client.gui.components.events.GuiEventListener;
import net.minecraft.client.gui.narration.NarratableEntry;
import net.minecraft.client.gui.narration.NarratedElementType;
import net.minecraft.client.gui.narration.NarrationElementOutput;
import net.minecraft.network.chat.Component;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.function.Function;
import java.util.function.Supplier;

public class ModCompatList extends ContainerObjectSelectionList<ModCompatList.Entry> {
    private final ModCompatScreen modCompatScreen;
    private final Supplier<List<String>> modCompatSupplier;
    private final Supplier<List<String>> modCustomSupplier;
    private final List<ItemEntry> removedEntries = new ArrayList<>();
    public ModCompatList(ModCompatScreen parent, Minecraft minecraft) {
        super(minecraft, parent.width, parent.height, 20, parent.height - 32, 25);
        this.modCompatScreen = parent;
        modCompatSupplier = () -> InventoryPause.MOD_CONFIG.modCompat.compatScreens;
        modCustomSupplier = () -> InventoryPause.MOD_CONFIG.modCompat.customScreens;

        initEntries();
    }

    private void initEntries() {
        this.addEntry(new ModCompatList.SectionEntry(Component.literal("Compat")));
        ArrayList<String> modCompatClasses = new ArrayList<>(modCompatSupplier.get());
        for(int i = 0; i < modCompatClasses.size(); i++) {
            String aClass = modCompatClasses.get(i);
            this.addEntry(new ModCompatList.CompatEntry(i, aClass));
        }
        this.addEntry(new AddEntry(Component.literal("Add Compat Entry"), addEntry -> {
            return (button) -> {
                int i = children().indexOf(addEntry);
                children().add(i, new CompatEntry(modCompatSupplier.get().size(), "New Entry"));
                modCustomSupplier.get().add("New Entry");
            };
        }));
        this.addEntry(new ModCompatList.SectionEntry(Component.literal("Custom")));
        ArrayList<String> modCustomClasses = new ArrayList<>(modCustomSupplier.get());
        for(int i = 0; i < modCustomClasses.size(); i++) {
            String aClass = modCustomClasses.get(i);
            this.addEntry(new ModCompatList.CustomEntry(i, aClass));
        }
        this.addEntry(new AddEntry(Component.literal("Add Custom Entry"), addEntry -> {
            return (button) -> {
                int i = children().indexOf(addEntry);
                children().add(i, new CustomEntry(modCustomSupplier.get().size(), "New Entry"));
                modCustomSupplier.get().add("New Entry");
            };
        }));
    }



    public abstract static class Entry extends ContainerObjectSelectionList.Entry<ModCompatList.Entry> {
    }
    public class SectionEntry extends ModCompatList.Entry {
        final Component name;
        private final int width;

        public SectionEntry(Component name) {
            this.name = name;
            this.width = ModCompatList.this.minecraft.font.width(this.name);
        }

        @Override
        public List<? extends NarratableEntry> narratables() {
            return ImmutableList.of(new NarratableEntry() {
                public NarratableEntry.NarrationPriority narrationPriority() {
                    return NarratableEntry.NarrationPriority.HOVERED;
                }

                public void updateNarration(NarrationElementOutput p_193906_) {
                    p_193906_.add(NarratedElementType.TITLE, name);
                }
            });
        }
        /*public boolean changeFocus(boolean goForward) {
            return false;
        }*/

        @Override
        public void render(PoseStack poseStack, int index, int y, int x, int entryWidth, int entryHeight, int mouseX, int mouseY, boolean hovered, float tickDelta) {
            ModCompatList.this.minecraft.font.draw(poseStack, this.name, (float)(ModCompatList.this.minecraft.screen.width / 2 - this.width / 2), (float)(y + entryHeight - 9 - 1), 16777215);
        }

        @Override
        public List<? extends GuiEventListener> children() {
            return Collections.emptyList();
        }
    }

    public class AddEntry extends ModCompatList.Entry {
        final Button button;

        public AddEntry(Component text, Function<AddEntry, Button.OnPress> onPress) {
            this.button = Button.builder(text, onPress.apply(this)).size(180, 20).build();
        }

        @Override
        public List<? extends NarratableEntry> narratables() {
            return ImmutableList.of(button);
        }

        @Override
        public void render(PoseStack poseStack, int index, int y, int x, int entryWidth, int entryHeight, int mouseX, int mouseY, boolean hovered, float tickDelta) {
            this.button.setX(width / 4);
            this.button.setY(y);
            this.button.render(poseStack, mouseX, mouseY, tickDelta);
        }

        @Override
        public List<? extends GuiEventListener> children() {
            return ImmutableList.of(button);
        }
    }

    public class ItemEntry extends ModCompatList.Entry {
        private final int key;
        private final String content;
        private final EditBox editBox;
        private final Button removeButton;
        private final Supplier<List<String>> supplier;

        public ItemEntry(int key, String content, Supplier<List<String>> supplier) {
            this.key = key;
            this.content = content;
            this.supplier = supplier;
            this.removeButton = new Button.Builder(Component.literal("X"), (button) -> {
                // remove
                System.out.println(this.key);
                ModCompatList.this.removeEntry(this);
                removedEntries.add(this);
                unfocusEntry();
            }).size(20, 20).createNarration(p_253695_ -> Component.translatable("narrator.controls.reset", content)).build();
            this.editBox = new EditBox(ModCompatList.this.minecraft.font, 0, 0, 180, 20, Component.literal("Test"));
            editBox.setMaxLength(128);
            editBox.setValue(this.content);
            editBox.setFilter(s -> !s.contains("-"));
        }

        @Override
        public List<? extends NarratableEntry> narratables() {
            return ImmutableList.of(this.editBox, this.removeButton);
        }


        public void render(PoseStack poseStack, int index, int y, int x, int entryWidth, int entryHeight, int mouseX, int mouseY, boolean hovered, float tickDelta) {
            this.removeButton.setX(x + 190 + 10);
            this.removeButton.setY(y);
            this.removeButton.setWidth(20);
            this.removeButton.setWidth(20);
            this.removeButton.render(poseStack, mouseX, mouseY, tickDelta);
            this.editBox.setX(width / 4);
            this.editBox.setY(y);
            this.editBox.setWidth(entryWidth - 40);
            this.editBox.render(poseStack, mouseX, mouseY, tickDelta);
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
            getFocused().changeFocus(false);
            setFocused(null);
        }
    }

    public void saveChanges() {
        for(Entry item : children()) {
            if(item instanceof ItemEntry itemEntry) {
                itemEntry.save();
            }
        }
        for(ItemEntry item : removedEntries) {
            item.supplier.get().remove(item.key);
        }
    }
}
