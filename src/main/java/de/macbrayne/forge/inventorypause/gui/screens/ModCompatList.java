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
import net.minecraft.network.chat.Component;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

public class ModCompatList extends ContainerObjectSelectionList<ModCompatList.Entry> {
    private final ModCompatScreen modCompatScreen;
    private int maxNameWidth;
    private Entry focused;
    private Supplier<List<String>> modCompatSupplier;
    public ModCompatList(ModCompatScreen parent, Minecraft minecraft) {
        super(minecraft, parent.width, parent.height, 20, parent.height - 32, 20);
        this.modCompatScreen = parent;
        modCompatSupplier = () -> InventoryPause.MOD_CONFIG.modCompat.customScreens;
        ArrayList<String> modCompatClasses = new ArrayList<>(modCompatSupplier.get());
        modCompatClasses.sort((str1, str2) -> {
            int res = String.CASE_INSENSITIVE_ORDER.compare(str1, str2);
            if (res == 0) {
                res = str1.compareTo(str2);
            }
            return res;
        });
        for(String aClass : modCompatClasses) {
            Component component = Component.literal(aClass);
            int i = minecraft.font.width(component);
            if (i > this.maxNameWidth) {
                this.maxNameWidth = i;
            }

            this.addEntry(new ModCompatList.CompatEntry(aClass, component));
        }
    }



    public abstract static class Entry extends ContainerObjectSelectionList.Entry<ModCompatList.Entry> {
        @Override
        public void setFocused(@Nullable GuiEventListener listener) {
            super.setFocused(listener);
        }
    }
    public class CompatEntry extends ModCompatList.Entry {
        private final String item;
        private final Component name;
        private final EditBox editBox;
        private final Button removeButton;

        public CompatEntry(String item, Component name) {
            this.item = item;
            this.name = name;
            this.removeButton = new Button.Builder(Component.literal("X"), (button) -> {
                // remove
                System.out.println(this.item);
                ModCompatList.this.removeEntry(this);
                modCompatSupplier.get().remove(CompatEntry.this.item);
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
            this.editBox.render(poseStack, mouseX, mouseX, tickDelta);
        }

        public List<? extends GuiEventListener> children() {
            return ImmutableList.of(this.editBox, this.removeButton);
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
