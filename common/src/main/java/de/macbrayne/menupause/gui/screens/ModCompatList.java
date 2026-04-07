// SPDX-License-Identifier: EUPL-1.2

package de.macbrayne.menupause.gui.screens;

import com.google.common.collect.ImmutableList;
import de.macbrayne.menupause.Constants;
import de.macbrayne.menupause.MenuPause;
import de.macbrayne.menupause.gui.components.HoverButton;
import de.macbrayne.menupause.gui.components.IndicatingEditBox;
import de.macbrayne.menupause.gui.mojank.MutableTooltip;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.*;
import net.minecraft.client.gui.components.events.GuiEventListener;
import net.minecraft.client.gui.narration.NarratableEntry;
import net.minecraft.network.chat.Component;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.slf4j.Logger;

import java.util.*;
import java.util.function.Function;
import java.util.function.Supplier;

public class ModCompatList extends ContainerObjectSelectionList<ModCompatList.Entry> {
    private static final Logger LOGGER = Constants.LOG;
    private static final String newEntry = Component.translatable("menu.menupause.settings.modCompat.new").getString();
    private final Supplier<List<String>> modCompatSupplier;
    private final Supplier<List<String>> modCustomSupplier;
    private final List<ItemEntry> removedEntries = new ArrayList<>();

    public ModCompatList(ModCompatScreen parent, Minecraft minecraft) {
        super(minecraft, parent.width, parent.height - 52, 20, 25);
        modCompatSupplier = () -> MenuPause.MOD_CONFIG.modCompat.compatScreens;
        modCustomSupplier = () -> MenuPause.MOD_CONFIG.modCompat.customScreens;

        initEntries();
    }

    private void initEntries() {
        // Custom Screens
        this.addEntry(new ModCompatList.SectionEntry(Component.translatable("menu.menupause.settings.modCompat.customScreens"),
                Component.translatable("menu.menupause.settings.modCompat.customScreens.tooltip")));
        ArrayList<String> modCustomClasses = new ArrayList<>(modCustomSupplier.get());
        for (String aClass : modCustomClasses) {
            this.addEntry(new CustomEntry(aClass));
        }
        this.addEntry(new AddEntry(Component.translatable("menu.menupause.settings.modCompat.customScreens.add"), addEntry -> button -> {
            int i = children().indexOf(addEntry);
            children().add(i, new CustomEntry(newEntry));
            modCustomSupplier.get().add("");
        }));

        this.addEntry(new ModCompatList.SectionEntry(Component.translatable("menu.menupause.settings.modCompat.compatScreens"),
                Component.translatable("menu.menupause.settings.modCompat.compatScreens.tooltip")));
        ArrayList<String> modCompatClasses = new ArrayList<>(modCompatSupplier.get());
        for (String aClass : modCompatClasses) {
            this.addEntry(new CompatEntry(aClass));
        }
        this.addEntry(new AddEntry(Component.translatable("menu.menupause.settings.modCompat.compatScreens.add"), addEntry -> (button) -> {
            int i = children().indexOf(addEntry);
            children().add(i, new CompatEntry(newEntry));
            modCustomSupplier.get().add("");
        }));
    }

    @SuppressWarnings("ALL")
    private <T extends Entry> Optional<T> getLastTypedEntry(Class<T> clazz) {
        ListIterator<? extends Entry> iterator = children().listIterator(children().size());
        while (iterator.hasPrevious()) {
            var entry = iterator.previous();
            if (clazz.isInstance(entry)) {
                return Optional.of((T) entry);
            }
        }
        return Optional.empty();
    }

    @SuppressWarnings("ALL")
    private <T extends Entry> OptionalInt getLocationOfLastTypedEntry(Class<T> clazz) {
        Optional<T> lastTypedEntry = getLastTypedEntry(clazz);
        if (lastTypedEntry.isEmpty()) {
            return OptionalInt.empty();
        }
        return OptionalInt.of(ModCompatList.this.children().lastIndexOf(lastTypedEntry.get()));
    }

    @SuppressWarnings("ALL")
    private <T extends Entry> Optional<T> getFirstTypedEntry(Class<T> clazz) {
        ListIterator<? extends Entry> iterator = children().listIterator();
        while (iterator.hasNext()) {
            var entry = iterator.next();
            if (clazz.isInstance(entry)) {
                return Optional.of((T) entry);
            }
        }
        return Optional.empty();
    }

    @SuppressWarnings("ALL")
    private <T extends Entry> OptionalInt getLocationOfFirstTypedEntry(Class<T> clazz) {
        Optional<T> firstTypedEntry = getFirstTypedEntry(clazz);
        if (firstTypedEntry.isEmpty()) {
            return OptionalInt.empty();
        }
        return OptionalInt.of(ModCompatList.this.children().indexOf(firstTypedEntry.get()));
    }

    @Override
    public void setFocused(@Nullable GuiEventListener guiEventListener) {
        if (getFocused() != null) {
            getFocused().setFocused(false);
        }
        super.setFocused(guiEventListener);
    }

    public abstract static class Entry extends ContainerObjectSelectionList.Entry<ModCompatList.Entry> {
    }

    public class SectionEntry extends ModCompatList.Entry {
        final StringWidget title;
        private final Supplier<Component> tooltipSupplier;

        public SectionEntry(Component name, Component tooltip) {
            this(name, () -> tooltip);
        }

        public SectionEntry(Component name, Supplier<Component> tooltipSupplier) {
            title = new StringWidget(ModCompatList.this.width, ModCompatList.this.height, name, minecraft.font).alignCenter();
            title.setTooltip(Tooltip.create(tooltipSupplier.get()));
            this.tooltipSupplier = tooltipSupplier;
        }

        @Override
        public @NotNull List<? extends NarratableEntry> narratables() {
            return ImmutableList.of(title);
        }

        @Override
        public void render(@NotNull GuiGraphics guiGraphics, int index, int y, int x, int entryWidth, int entryHeight, int mouseX, int mouseY, boolean hovered, float tickDelta) {
            title.setX(x);
            title.setY(y);
            title.setWidth(entryWidth);
            title.setHeight(entryHeight);
            ((MutableTooltip) title.getTooltip()).menupause$updateMessage(minecraft, tooltipSupplier.get());
            title.render(guiGraphics, mouseX, mouseY, tickDelta);
        }

        @Override
        public @NotNull List<? extends GuiEventListener> children() {
            return ImmutableList.of(title);
        }
    }

    public static class AddEntry extends ModCompatList.Entry {
        final Button button;

        public AddEntry(Component text, Function<AddEntry, Button.OnPress> onPress) {
            this.button = Button.builder(text, onPress.apply(this)).size(180, 20).build();
        }

        @Override
        public @NotNull List<? extends NarratableEntry> narratables() {
            return ImmutableList.of(button);
        }

        @Override
        public void render(@NotNull GuiGraphics guiGraphics, int index, int y, int x, int entryWidth, int entryHeight, int mouseX, int mouseY, boolean hovered, float tickDelta) {
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
        public @NotNull List<? extends GuiEventListener> children() {
            return ImmutableList.of(button);
        }
    }

    public abstract class ItemEntry extends ModCompatList.Entry implements Saveable {
        private final String configValue;
        private final IndicatingEditBox editBox;
        private final Button removeButton, moveButton;
        private final Supplier<List<String>> supplier;

        public ItemEntry(String configValue, Supplier<List<String>> supplier, Button.CreateNarration moveButtonNarrationSupplier) {
            this.configValue = configValue;
            this.supplier = supplier;
            this.removeButton = new HoverButton(0, 0, 20, 20, Component.translatable("menu.menupause.settings.modCompat.delete"), (button) -> {
                ModCompatList.this.removeEntry(this);
                removedEntries.add(this);
                unfocusEntry();
            }, p_253695_ -> Component.translatable("narrator.menupause.settings.modCompat.delete", configValue));
            this.moveButton = new Button.Builder(Component.translatable("menu.menupause.settings.modCompat.moveUp"), button -> moveItem()).size(20, 20)
                    .createNarration(moveButtonNarrationSupplier).build();
            this.editBox = new IndicatingEditBox(ModCompatList.this.minecraft.font, 180, 20);
            editBox.setMaxLength(512);
            editBox.setValue(this.configValue);
            editBox.setFilter(s -> !s.contains("-"));
        }

        @Override
        public @NotNull List<? extends NarratableEntry> narratables() {
            return ImmutableList.of(this.editBox, this.moveButton, this.removeButton);
        }

        public abstract void moveItem();

        @Override
        public void setFocused(boolean state) {
            super.setFocused(state);
            if (getFocused() == editBox) {
                editBox.setFocused(state);
            }
        }

        protected Button getMoveButton() {
            return moveButton;
        }

        public void render(@NotNull GuiGraphics guiGraphics, int index, int y, int x, int entryWidth, int entryHeight, int mouseX, int mouseY, boolean hovered, float tickDelta) {
            this.removeButton.setX(x + 190 + 10);
            this.removeButton.setY(y);
            this.removeButton.setWidth(20);
            this.removeButton.setHeight(20);
            this.removeButton.render(guiGraphics, mouseX, mouseY, tickDelta);
            this.moveButton.setX(x + 190 - 10);
            this.moveButton.setY(y);
            this.moveButton.render(guiGraphics, mouseX, mouseY, tickDelta);
            this.editBox.setX(x);
            this.editBox.setY(y);
            this.editBox.setWidth(195 - 20);
            this.editBox.render(guiGraphics, mouseX, mouseY, tickDelta);
        }

        public @NotNull List<? extends GuiEventListener> children() {
            return ImmutableList.of(this.editBox, this.moveButton, this.removeButton);
        }

        public String getConfigValue() {
            return configValue;
        }

        public String getValue() {
            return editBox.getValue();
        }

        public void save() {
            if (!configValue.equals(this.editBox.getValue())) {
                int index = supplier.get().indexOf(configValue);
                if (index == -1) {
                    supplier.get().add(this.editBox.getValue());
                    return;
                }
                supplier.get().set(index, this.editBox.getValue());
            }
        }

    }

    public class CompatEntry extends ItemEntry {

        public CompatEntry(String content) {
            super(content, modCompatSupplier, p_253695_ -> Component.translatable("narrator.menupause.settings.modCompat.moveUp"));
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
            super(content, modCustomSupplier, contentSupplier -> Component.translatable("narrator.menupause.settings.modCompat.moveDown"));
            getMoveButton().setMessage(Component.translatable("menu.menupause.settings.modCompat.moveDown"));
        }

        @Override
        public void moveItem() {
            // If there is a compat entry, move the custom entry after the last compat entry, otherwise move it after the location of the last add entry
            @SuppressWarnings("OptionalGetWithoutIsPresent") int newLocation = getLocationOfLastTypedEntry(CompatEntry.class).orElseGet(() -> getLocationOfLastTypedEntry(AddEntry.class).getAsInt() - 1) + 1;
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
        if (getFocused() != null) {
            // getFocused().setFocused(false);
            getFocused().setFocused(false);
            setFocused(null);
        }
    }

    public void saveChanges() {
        for (Entry item : children()) {
            if (item instanceof Saveable saveable) {
                saveable.save();
            }
        }
        for (ItemEntry item : removedEntries) {
            item.supplier.get().remove(item.getConfigValue());
        }
    }

}
