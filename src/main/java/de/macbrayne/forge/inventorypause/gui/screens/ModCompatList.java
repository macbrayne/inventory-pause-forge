// SPDX-License-Identifier: EUPL-1.2

package de.macbrayne.forge.inventorypause.gui.screens;

import com.google.common.collect.ImmutableList;
import com.mojang.blaze3d.vertex.PoseStack;
import de.macbrayne.forge.inventorypause.InventoryPause;
import de.macbrayne.forge.inventorypause.gui.components.HoverButton;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.*;
import net.minecraft.client.gui.components.events.GuiEventListener;
import net.minecraft.client.gui.narration.NarratableEntry;
import net.minecraft.network.chat.Component;
import org.apache.commons.lang3.math.NumberUtils;

import java.util.*;
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
        super(minecraft, parent.width, parent.height, 20, parent.height - 32, 25);
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
            this.addEntry(new ModCompatList.CustomEntry(aClass));
        }
        this.addEntry(new AddEntry(Component.translatable("menu.inventorypause.settings.modCompat.customScreens.add"), addEntry -> button -> {
            int i = children().indexOf(addEntry);
            children().add(new CustomEntry(newEntry));
        }));

        // Time Between Compat Ticks
        NumEntry numEntry = new ModCompatList.NumEntry(() -> InventoryPause.MOD_CONFIG.modCompat.timeBetweenCompatTicks,
                value -> InventoryPause.MOD_CONFIG.modCompat.timeBetweenCompatTicks = value, 20);
        this.addEntry(new ModCompatList.SectionEntry(Component.translatable("menu.inventorypause.settings.modCompat.timeBetweenCompatTicks"), numEntry::getTooltip));
        this.addEntry(numEntry);

        this.addEntry(new ModCompatList.SectionEntry(Component.translatable("menu.inventorypause.settings.modCompat.compatScreens"),
                Component.translatable("menu.inventorypause.settings.modCompat.compatScreens.tooltip")));
        ArrayList<String> modCompatClasses = new ArrayList<>(modCompatSupplier.get());
        for(int i = 0; i < modCompatClasses.size(); i++) {
            String aClass = modCompatClasses.get(i);
            this.addEntry(new ModCompatList.CompatEntry(aClass));
        }
        this.addEntry(new AddEntry(Component.translatable("menu.inventorypause.settings.modCompat.compatScreens.add"), addEntry -> (button) -> {
            int i = children().indexOf(addEntry);
            children().add(new CompatEntry(newEntry));
            modCustomSupplier.get().add("New Entry");
        }));
    }

    private <T extends Entry> Optional<T> getLastTypedEntry(Class<T> clazz) {
        ListIterator<? extends Entry> iterator = children().listIterator(children().size());
        while(iterator.hasPrevious()) {
            var entry = iterator.previous();
            if(clazz.isInstance(entry)) {
                return Optional.of((T) entry);
            }
        }
        return Optional.empty();
    }

    private <T extends Entry> OptionalInt getLocationOfLastTypedEntry(Class<T> clazz) {
        Optional<T> lastTypedEntry = getLastTypedEntry(clazz);
        if (lastTypedEntry.isEmpty()) {
            return OptionalInt.empty();
        }
        return OptionalInt.of(ModCompatList.this.children().lastIndexOf(lastTypedEntry.get()));
    }

    private <T extends Entry> Optional<T> getFirstTypedEntry(Class<T> clazz) {
        ListIterator<? extends Entry> iterator = children().listIterator();
        while(iterator.hasNext()) {
            var entry = iterator.next();
            if(clazz.isInstance(entry)) {
                return Optional.of((T) entry);
            }
        }
        return Optional.empty();
    }

    private <T extends Entry> OptionalInt getLocationOfFirstTypedEntry(Class<T> clazz) {
        Optional<T> firstTypedEntry = getFirstTypedEntry(clazz);
        if (firstTypedEntry.isEmpty()) {
            return OptionalInt.empty();
        }
        return OptionalInt.of(ModCompatList.this.children().indexOf(firstTypedEntry.get()));
    }

    public abstract static class Entry extends ContainerObjectSelectionList.Entry<ModCompatList.Entry> {
    }
    public class SectionEntry extends ModCompatList.Entry {
        final StringWidget title;
        final Component name;
        private final Supplier<Tooltip> tooltipSupplier;

        public SectionEntry(Component name, Component tooltip) {
            this(name, () -> Tooltip.create(tooltip));
        }

        public SectionEntry(Component name, Supplier<Tooltip> tooltipSupplier) {
            this.name = name;
            title = new StringWidget(ModCompatList.this.width, ModCompatList.this.height, name, minecraft.font).alignCenter();
            this.tooltipSupplier = tooltipSupplier;
        }

        @Override
        public List<? extends NarratableEntry> narratables() {
            return ImmutableList.of(title);
        }

        @Override
        public void render(PoseStack poseStack, int index, int y, int x, int entryWidth, int entryHeight, int mouseX, int mouseY, boolean hovered, float tickDelta) {
            title.setX(x);
            title.setY(y);
            title.setWidth(entryWidth);
            title.setHeight(entryHeight);
            title.setTooltip(tooltipSupplier.get());
            title.render(poseStack, mouseX, mouseY, tickDelta);
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
        public void render(PoseStack poseStack, int index, int y, int x, int entryWidth, int entryHeight, int mouseX, int mouseY, boolean hovered, float tickDelta) {
            this.button.setX(x);
            this.button.setY(y);
            this.button.setWidth(entryWidth);
            this.button.render(poseStack, mouseX, mouseY, tickDelta);
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

    public abstract class ItemEntry extends ModCompatList.Entry implements Saveable {
        private final String configValue;
        private final EditBox editBox;
        private final Button removeButton, moveButton;
        private final Supplier<List<String>> supplier;

        public ItemEntry(String configValue, Supplier<List<String>> supplier, Button.CreateNarration moveButtonNarrationSupplier) {
            this.configValue = configValue;
            this.supplier = supplier;
            this.removeButton = new HoverButton(new Button.Builder(Component.translatable("menu.inventorypause.settings.modCompat.delete"), (button) -> {
                ModCompatList.this.removeEntry(this);
                removedEntries.add(this);
                unfocusEntry();
            }).size(20, 20).createNarration(p_253695_ -> Component.translatable("narrator.inventorypause.settings.modCompat.delete", configValue)));
            this.moveButton = new Button.Builder(Component.translatable("menu.inventorypause.settings.modCompat.moveUp"), button -> moveItem()).size(20, 20)
                    .createNarration(moveButtonNarrationSupplier).build();
            this.editBox = new EditBox(ModCompatList.this.minecraft.font, 0, 0, 180, 20, Component.empty());
            editBox.setMaxLength(128);
            editBox.setValue(this.configValue);
            editBox.setFilter(s -> !s.contains("-"));
        }

        @Override
        public List<? extends NarratableEntry> narratables() {
            return ImmutableList.of(this.editBox, this.moveButton, this.removeButton);
        }

        public abstract void moveItem();

        @Override
        public void setFocused(boolean state) {
            super.setFocused(state);
            if(getFocused() == editBox) {
                editBox.setFocused(state);
            }
        }

        protected Button getMoveButton() {
            return moveButton;
        }

        public void render(PoseStack poseStack, int index, int y, int x, int entryWidth, int entryHeight, int mouseX, int mouseY, boolean hovered, float tickDelta) {
            this.removeButton.setX(x + 190 + 10);
            this.removeButton.setY(y);
            this.removeButton.setWidth(20);
            this.removeButton.setWidth(20);
            this.removeButton.render(poseStack, mouseX, mouseY, tickDelta);
            this.moveButton.setX(x + 190 - 10);
            this.moveButton.setY(y);
            this.moveButton.render(poseStack, mouseX, mouseY, tickDelta);
            this.editBox.setX(x);
            this.editBox.setY(y);
            this.editBox.setWidth(195 - 20);
            this.editBox.render(poseStack, mouseX, mouseY, tickDelta);
        }

        public List<? extends GuiEventListener> children() {
            return ImmutableList.of(this.editBox, this.moveButton, this.removeButton);
        }

        public String getConfigValue() {
            return configValue;
        }

        public String getValue() {
            return editBox.getValue();
        }

        public void save() {
            if(!configValue.equals(this.editBox.getValue())) {
                int index = supplier.get().indexOf(configValue);
                if(index == -1) {
                    supplier.get().add(this.editBox.getValue());
                    return;
                }
                supplier.get().set(index, this.editBox.getValue());
            }
        }
    }

    public class CompatEntry extends ItemEntry {

        public CompatEntry(String content) {
            super(content, modCompatSupplier, p_253695_ -> Component.translatable("narrator.inventorypause.settings.modCompat.moveUp"));
        }

        @Override
        public void moveItem() {
            // If there is a custom entry, move the compat entry after the last compat entry, otherwise move it before the location of the first add entry
            int newLocation = getLocationOfLastTypedEntry(CustomEntry.class).orElseGet(() -> getLocationOfFirstTypedEntry(AddEntry.class).orElseThrow() - 1) + 1;
            // Add the converted entry to the backing config
            modCustomSupplier.get().add(getValue());
            // ...and remove the current entry from the backing config
            modCompatSupplier.get().remove(getConfigValue());
            // Add the entry to the new location
            ModCompatList.this.children().add(newLocation, new ModCompatList.CustomEntry(getValue()));
            // Finally remove this entry from the list
            ModCompatList.this.children().remove(this);
        }
    }

    public class CustomEntry extends ItemEntry {
        public CustomEntry(String content) {
            super(content, modCustomSupplier, p_253695_ -> Component.translatable("narrator.inventorypause.settings.modCompat.moveDown"));
            getMoveButton().setMessage(Component.translatable("menu.inventorypause.settings.modCompat.moveDown"));
        }

        @Override
        public void moveItem() {
            // If there is a compat entry, move the custom entry after the last compat entry, otherwise move it after the location of the last add entry
            int newLocation = getLocationOfLastTypedEntry(CompatEntry.class).orElseGet(() -> getLocationOfLastTypedEntry(AddEntry.class).getAsInt() - 1) + 1;
            // Add the converted entry to the backing config
            modCompatSupplier.get().add(getValue());
            // ...and remove the current entry from the backing config
            modCustomSupplier.get().remove(getConfigValue());
            // Add the entry to the new location
            ModCompatList.this.children().add(newLocation, new ModCompatList.CompatEntry(getValue()));
            // Finally remove this entry from the list
            ModCompatList.this.children().remove(this);
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

            this.resetButton = new HoverButton(new Button.Builder(Component.translatable("menu.inventorypause.settings.modCompat.reset"), (button) -> numBox.setValue(String.valueOf(defaultValue)))
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
        public void render(PoseStack poseStack, int index, int y, int x, int entryWidth, int entryHeight, int mouseX, int mouseY, boolean hovered, float tickDelta) {
            this.numBox.setTooltip(getTooltip());
            this.resetButton.render(poseStack, mouseX, mouseY, tickDelta);
            this.resetButton.setX(x + 190 - 10);
            this.resetButton.setY(y);
            this.resetButton.render(poseStack, mouseX, mouseY, tickDelta);
            this.numBox.setX(x);
            this.numBox.setY(y);
            this.numBox.setWidth(190 - 15);
            this.numBox.render(poseStack, mouseX, mouseY, tickDelta);
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

        public Tooltip getTooltip() {
            Locale locale = Minecraft.getInstance().getLanguageManager().getJavaLocale();
            float valueInHertz = 0.00f;
            if(!numBox.getValue().isEmpty()) {
                valueInHertz = Integer.parseInt(numBox.getValue()) / 20f;
            }
            return Tooltip.create(Component.translatable("menu.inventorypause.settings.modCompat.timeBetweenCompatTicks.tooltip",
                    String.format(locale, "%.2f", valueInHertz),
                    String.format(locale, "%.2f", 0.05)));
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
        for(ItemEntry item : removedEntries) {
            item.supplier.get().remove(item.getConfigValue());
        }
    }

    interface Saveable {
        void save();
    }
}
