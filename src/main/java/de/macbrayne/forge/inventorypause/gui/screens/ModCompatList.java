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
    private final Comparator<String> caseInsensitive = (str1, str2) -> {
        int res = String.CASE_INSENSITIVE_ORDER.compare(str1, str2);
        if (res == 0) {
            res = str1.compareTo(str2);
        }
        return res;
    };
    private final ModCompatScreen modCompatScreen;
    private final Supplier<List<String>> modCompatSupplier;
    private final Supplier<List<String>> modCustomSupplier;
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
        modCompatClasses.sort(caseInsensitive);
        for(String aClass : modCompatClasses) {
            Component component = Component.literal(aClass);
            this.addEntry(new ModCompatList.CompatEntry(aClass, component));
        }
        this.addEntry(new AddEntry(Component.literal("Add Compat Entry"), addEntry -> {
            return (button) -> {
                children().add(children().indexOf(addEntry), new CompatEntry(Long.toString(System.nanoTime()), Component.literal("New Entry")));
            };
        }));
        this.addEntry(new ModCompatList.SectionEntry(Component.literal("Custom")));
        ArrayList<String> modCustomClasses = new ArrayList<>(modCustomSupplier.get());
        modCustomClasses.sort(caseInsensitive);
        for(String aClass : modCustomClasses) {
            Component component = Component.literal(aClass);
            this.addEntry(new ModCompatList.CustomEntry(aClass, component));
        }
        this.addEntry(new AddEntry(Component.literal("Add Custom Entry"), addEntry -> {
            return (button) -> {
                children().add(children().indexOf(addEntry), new CustomEntry(Long.toString(System.nanoTime()), Component.literal("New Entry")));
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
        private final String item;
        private final Component name;
        private final EditBox editBox;
        private final Button removeButton;

        public ItemEntry(String item, Component name, Supplier<List<String>> supplier) {
            this.item = item;
            this.name = name;
            this.removeButton = new Button.Builder(Component.literal("X"), (button) -> {
                // remove
                System.out.println(this.item);
                ModCompatList.this.removeEntry(this);
                supplier.get().remove(ItemEntry.this.item);
            }).size(20, 20).createNarration(p_253695_ -> Component.translatable("narrator.controls.reset", name)).build();
            this.editBox = new EditBox(ModCompatList.this.minecraft.font, 0, 0, 180, 20, this.name);
            editBox.setMaxLength(128);
            editBox.setValue(this.item);
        }

        @Override
        public List<? extends NarratableEntry> narratables() {
            return ImmutableList.of(this.editBox, this.removeButton);
        }


        public void render(PoseStack poseStack, int index, int y, int x, int entryWidth, int entryHeight, int mouseX, int mouseY, boolean hovered, float tickDelta) {
            this.removeButton.setX(x + 190 + 20);
            this.removeButton.setY(y);
            this.removeButton.render(poseStack, mouseX, mouseY, tickDelta);
            this.editBox.setX(width / 4);
            this.editBox.setY(y);
            this.editBox.render(poseStack, mouseX, mouseY, tickDelta);
        }

        public List<? extends GuiEventListener> children() {
            return ImmutableList.of(this.editBox, this.removeButton);
        }
    }

    public class CompatEntry extends ItemEntry {

        public CompatEntry(String item, Component name) {
            super(item, name, modCompatSupplier);
        }
    }

    public class CustomEntry extends ItemEntry {

        public CustomEntry(String item, Component name) {
            super(item, name, modCompatSupplier);
        }
    }

    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        if (!this.isMouseOver(mouseX, mouseY)) {
            unfocusEntry();
            return false;
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
}
