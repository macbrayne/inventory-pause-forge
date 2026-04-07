// SPDX-License-Identifier: EUPL-1.2

package de.macbrayne.menupause.gui.screens;

import com.google.common.collect.ImmutableList;
import com.mojang.datafixers.util.Either;
import de.macbrayne.menupause.Constants;
import de.macbrayne.menupause.MenuPause;
import de.macbrayne.menupause.common.PauseMode;
import de.macbrayne.menupause.config.GuiEntry;
import de.macbrayne.menupause.config.ModConfig;
import de.macbrayne.menupause.gui.components.BorderedCycleButton;
import de.macbrayne.menupause.gui.components.HoverButton;
import de.macbrayne.menupause.gui.components.TexturedCycleButton;
import de.macbrayne.menupause.gui.components.TriStateTooltip;
import de.macbrayne.menupause.gui.mojank.MutableTooltip;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.*;
import net.minecraft.client.gui.components.events.GuiEventListener;
import net.minecraft.client.gui.layouts.LinearLayout;
import net.minecraft.client.gui.narration.NarratableEntry;
import net.minecraft.network.chat.Component;
import org.apache.commons.lang3.math.NumberUtils;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.function.IntConsumer;
import java.util.function.IntSupplier;

public class ConfigList extends ContainerObjectSelectionList<ConfigList.Entry> {
    private final ModConfig config = MenuPause.MOD_CONFIG;
    private static final int itemHeight = 25;
    private final ConfigScreen parent;
    private static final int numberOfColumns = 9;
    private final List<BorderedCycleButton> dynamicButtons = new ArrayList<>();

    public ConfigList(ConfigScreen parent, Minecraft minecraft) {
        super(minecraft, parent.width, parent.height - 52, 20, itemHeight);
        this.parent = parent;
        initEntries();
    }

    private void initEntries() {
        CycleButton.Builder<PauseMode> onOffBuilder = CycleButton.builder(PauseMode::getDisplayName)
                .withValues(PauseMode.OFF, PauseMode.ON);
        var save = Tooltip.create(Component.translatable("menu.menupause.settings.disableSaving.tooltip"));
        var sounds = Tooltip.create(Component.translatable("menu.menupause.settings.pauseSounds.tooltip"));
        this.addEntry(new SplitEntry<>(new BorderedCycleButton(onOffBuilder.withInitialValue(config.disableSaving ? PauseMode.OFF : PauseMode.ON)
                .withTooltip(pauseMode -> save)
                .create(0, 0, 0, 0, Component.translatable("menu.menupause.settings.disableSaving"), (button, state) -> {
                    config.disableSaving = state == PauseMode.OFF;
                })), new BorderedCycleButton(onOffBuilder.withInitialValue(config.pauseSounds ? PauseMode.ON : PauseMode.OFF)
                .withTooltip(pauseMode -> sounds)
                .create(0, 0, 0, 0, Component.translatable("menu.menupause.settings.pauseSounds"), (button, state) -> {
                    config.pauseSounds = state == PauseMode.ON;
                }))));

        initDynamicEntries();
    }

    private void initDynamicEntries() {
        Constants.LOG.debug("Adding dynamic buttons to config screen");
        this.addEntry(new TextEntry(Component.translatable("menu.menupause.settings.title.pause")));

        CycleButton.Builder<PauseMode> builder = CycleButton.builder(PauseMode::getDisplayName)
                .withValues(PauseMode.OFF, PauseMode.SLOWMO, PauseMode.ON)
                .withTooltip(t -> TriStateTooltip.withState(Component.empty()).get(t));


        var contiguous = getContiguous(MenuPause.GUI_ENTRIES.entries());

        for (var entry : contiguous) {
            entry.ifLeft(icons -> {
                Constants.LOG.debug("Continuous list of icons: {}", icons);
                int numberOfRows = icons.size() / numberOfColumns + (icons.size() % numberOfColumns > 0 ? 1 : 0);
                for (int row = 0; row < numberOfRows; row++) {
                    LinearLayout layout = LinearLayout.horizontal().spacing(4);
                    for (int i = 0; i < icons.size(); i++) {
                        int padding = i % numberOfColumns == 0 ? 14 : 0;
                        if (i / numberOfColumns == row) {
                            TexturedCycleButton child = TexturedCycleButton.fromButtonInfo(0, 0, 20, 20, icons.get(i));
                            layout.addChild(child, layout.newCellSettings().paddingLeft(padding));
                            dynamicButtons.add(child);
                        }
                    }
                    layout.arrangeElements();
                    addEntry(new LinearLayoutEntry(layout));
                }
            });
            entry.ifRight(texts -> {
                Constants.LOG.debug("Continuous list of texts: {}", texts);
                for (int i = 0; i < texts.size(); i += 2) {
                    final int finalI = i;
                    var left = new BorderedCycleButton(builder.withInitialValue(config.states.get(texts.get(i)))
                            .create(0, 0, 0, 0, Component.translatable("menu.menupause.settings." + texts.get(finalI).content()), (button, state) -> {
                                config.states.put(texts.get(finalI), state);
                            }));
                    var right = new BorderedCycleButton(builder.withInitialValue(config.states.get(texts.get(i + 1)))
                            .create(0, 0, 0, 0, Component.translatable("menu.menupause.settings." + texts.get(finalI + 1).content()), (button, state) -> {
                                config.states.put(texts.get(finalI + 1), state);
                            }));
                    addEntry(new SplitEntry<>(left, right));
                    dynamicButtons.add(left);
                    dynamicButtons.add(right);
                }
            });
        }

        addEntry(new SingleEntry<>(new Button.Builder(Component.translatable("menu.menupause.settings.toggle_all"), button -> {
            for(BorderedCycleButton entry : dynamicButtons) {
                entry.onPress();
            }
        })
                .tooltip(Tooltip.create(Component.translatable("menu.menupause.settings.toggle_all.tooltip"))).build()));

        // Time Between Compat Ticks
        NumEntry numEntry = new NumEntry(() -> MenuPause.MOD_CONFIG.modCompat.slowmoTickSpeed,
                value -> MenuPause.MOD_CONFIG.modCompat.slowmoTickSpeed = value, 1);
        this.addEntry(new TextEntry(Component.translatable("menu.menupause.settings.slowmoSpeed")));
        this.addEntry(numEntry);

        if (!MenuPause.MOD_CONFIG.settingsForModpacks.hideModCompatButton) {
            addEntry(new SingleEntry<>(new Button.Builder(Component.translatable("menu.menupause.settings.mod_compat_options"), button -> this.minecraft.setScreen(new ModCompatScreen(parent)))
                    .tooltip(Tooltip.create(Component.translatable("menu.menupause.settings.mod_compat_options.tooltip"))).build()));
        }
    }

    @Override
    public int getRowWidth() {
        return super.getRowWidth() + 20;
    }

    public abstract static class Entry extends ContainerObjectSelectionList.Entry<ConfigList.Entry> {
    }

    public class SingleEntry<T extends AbstractWidget> extends Entry {
        private T entry;

        public SingleEntry(T entry) {
            this.entry = entry;
        }

        @Override
        public List<? extends NarratableEntry> narratables() {
            return List.of(entry);
        }

        @Override
        public void render(GuiGraphics guiGraphics, int index, int top, int left, int width, int height, int mouseX, int mouseY, boolean hovering, float partialTick) {
            entry.setX(left);
            entry.setY(top);
            entry.setHeight(height);
            entry.setWidth(width);
            entry.render(guiGraphics, mouseX, mouseY, partialTick);
        }

        @Override
        public List<? extends GuiEventListener> children() {
            return List.of(entry);
        }
    }

    public class TextEntry extends SingleEntry<StringWidget> {
        public TextEntry(Component text) {
            super(new StringWidget(text, minecraft.font).alignLeft());
        }

        public TextEntry(Component text, Component tooltip) {
            super(new StringWidget(text, minecraft.font).alignLeft());
            ((StringWidget)children().getFirst()).setTooltip(Tooltip.create(tooltip));
        }

        @Override
        public List<? extends NarratableEntry> narratables() {
            return List.of();
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
            this.numBox = new EditBox(minecraft.font, 0, 0, 180, 20, Component.empty());
            this.numBox.setMaxLength(2);
            this.numBox.setValue(String.valueOf(valueSupplier.getAsInt()));
            this.numBox.setFilter(s -> s.isEmpty() || (NumberUtils.isParsable(s) && !s.contains("-")));
            this.numBox.setResponder(this::onEdit);
            this.numBox.setTooltip(Tooltip.create(getTooltip()));

            this.resetButton = new HoverButton(0, 0, 40, 20, Component.translatable("menu.menupause.settings.modCompat.reset"), (button) -> {
                this.numBox.setValue(String.valueOf(defaultValue));
                this.onEdit(String.valueOf(defaultValue));
            }, p_253695_ -> Component.translatable("narrator.controls.reset", defaultValue));
            resetButton.setTooltip(Tooltip.create(Component.translatable("menu.menupause.settings.modCompat.reset.tooltip")));
            onEdit(this.numBox.getValue());
        }

        private void onEdit(String currentValue) {
            ((MutableTooltip) numBox.getTooltip()).menupause$updateMessage(minecraft, getTooltip());
            this.resetButton.active = !currentValue.equals("1");
        }

        @Override
        public @NotNull List<? extends NarratableEntry> narratables() {
            return ImmutableList.of(numBox, resetButton);
        }

        @Override
        public void render(@NotNull GuiGraphics guiGraphics, int index, int y, int x, int entryWidth, int entryHeight, int mouseX, int mouseY, boolean hovered, float tickDelta) {
            this.numBox.render(guiGraphics, mouseX, mouseY, tickDelta);
            this.resetButton.render(guiGraphics, mouseX, mouseY, tickDelta);
            this.resetButton.setX(x + 210 - 10);
            this.resetButton.setY(y);
            this.resetButton.render(guiGraphics, mouseX, mouseY, tickDelta);
            this.numBox.setX(x);
            this.numBox.setY(y);
            this.numBox.setWidth(210 - 15);
            this.numBox.render(guiGraphics, mouseX, mouseY, tickDelta);
        }

        @Override
        public void setFocused(boolean state) {
            super.setFocused(state);
            if (getFocused() == numBox) {
                numBox.setFocused(state);
            }
            if(Integer.parseInt(numBox.getValue()) > 20) {
                numBox.setValue("20");
            }
        }

        @Override
        public @NotNull List<? extends GuiEventListener> children() {
            return ImmutableList.of(numBox, resetButton);
        }

        @Override
        public void save() {
            if (numBox.getValue().isEmpty()) {
                return;
            }
            int value = Math.abs(Integer.parseInt(this.numBox.getValue()));
            if (value > 0) {
                valueConsumer.accept(value);
            }
        }

        @Override
        public boolean isDirty() {
            return Math.abs(Integer.parseInt(this.numBox.getValue())) != valueSupplier.getAsInt();
        }

        @Override
        public void reset() {
            numBox.setValue(String.valueOf(valueSupplier.getAsInt()));
        }

        public Component getTooltip() {
            String selected = Minecraft.getInstance().getLanguageManager().getSelected();
            final String[] langSplit = selected.split("_", 2);
            var locale = langSplit.length == 1 ? new java.util.Locale(langSplit[0]) : new java.util.Locale(langSplit[0], langSplit[1]);

            float valueInHertz = 20f;
            if (!numBox.getValue().isEmpty()) {
                valueInHertz = Integer.parseInt(numBox.getValue());
            }
            return Component.translatable("menu.menupause.settings.slowmoSpeed.tooltip",
                    String.format(locale, "%.2f", valueInHertz),
                    String.format(locale, "%.2f", (1 - 1 / (20f / valueInHertz)) * 100));
        }
    }

    public class SplitEntry<T extends AbstractWidget> extends Entry {
        private T leftEntry, rightEntry;

        public SplitEntry(T leftEntry, T rightEntry) {
            this.leftEntry = leftEntry;
            this.rightEntry = rightEntry;
        }

        @Override
        public List<? extends NarratableEntry> narratables() {
            return List.of(leftEntry, rightEntry);
        }

        @Override
        public void render(GuiGraphics guiGraphics, int index, int top, int left, int width, int height, int mouseX, int mouseY, boolean hovering, float partialTick) {
            leftEntry.setX(left);
            leftEntry.setY(top);
            leftEntry.setHeight(height);
            leftEntry.setWidth(width / 2 - 2);
            leftEntry.render(guiGraphics, mouseX, mouseY, partialTick);
            rightEntry.setX(left + width / 2 + 2);
            rightEntry.setY(top);
            rightEntry.setWidth(width / 2 - 2);
            rightEntry.setHeight(height);
            rightEntry.render(guiGraphics, mouseX, mouseY, partialTick);
        }

        @Override
        public List<? extends GuiEventListener> children() {
            return List.of(leftEntry, rightEntry);
        }
    }

    public class LinearLayoutEntry extends Entry {
        private LinearLayout layout;
        private ArrayList<AbstractWidget> children = new ArrayList<>();

        public LinearLayoutEntry(LinearLayout layout) {
            this.layout = layout;
            layout.visitWidgets(children::add);
        }

        @Override
        public void render(GuiGraphics guiGraphics, int index, int top, int left, int width, int height, int mouseX, int mouseY, boolean hovering, float partialTick) {
            layout.setX(left);
            layout.setY(top);
            layout.visitWidgets(widget -> widget.render(guiGraphics, mouseX, mouseY, partialTick));
        }

        @Override
        public List<? extends NarratableEntry> narratables() {
            return children;
        }

        @Override
        public List<? extends GuiEventListener> children() {
            return children;
        }
    }


    private static List<Either<List<GuiEntry.Icon>, List<GuiEntry.Text>>> getContiguous(List<GuiEntry<?>> entries) {
        List<Either<List<GuiEntry.Icon>, List<GuiEntry.Text>>> result = new ArrayList<>();
        List<GuiEntry<?>> currentGroup = new ArrayList<>();
        for (GuiEntry<?> entry : entries) {
            if (!currentGroup.isEmpty() && entry.getClass() != currentGroup.getFirst().getClass()) {
                addGroupToResult(result, currentGroup);
                currentGroup.clear();
            }
            currentGroup.add(entry);
        }
        if (!currentGroup.isEmpty()) {
            addGroupToResult(result, currentGroup);
        }
        return result;
    }

    private static void addGroupToResult(List<Either<List<GuiEntry.Icon>, List<GuiEntry.Text>>> result, List<GuiEntry<?>> group) {
        if (group.getFirst() instanceof GuiEntry.Icon icon) {
            result.add(Either.left(group.stream().map(item -> (GuiEntry.Icon) item).toList()));
        } else if (group.getFirst() instanceof GuiEntry.Text text) {
            result.add(Either.right(group.stream().map(item -> (GuiEntry.Text) item).toList()));
        }
    }
}
