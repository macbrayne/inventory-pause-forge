// SPDX-License-Identifier: EUPL-1.2

package de.macbrayne.inventorypause.gui.screens;

import com.mojang.datafixers.util.Either;
import de.macbrayne.inventorypause.InventoryPause;
import de.macbrayne.inventorypause.config.GuiEntry;
import de.macbrayne.inventorypause.config.ModConfig;
import de.macbrayne.inventorypause.common.PauseMode;
import de.macbrayne.inventorypause.gui.components.BorderedCycleButton;
import de.macbrayne.inventorypause.gui.components.TexturedCycleButton;
import de.macbrayne.inventorypause.gui.components.TriStateTooltip;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.*;
import net.minecraft.client.gui.components.events.GuiEventListener;
import net.minecraft.client.gui.layouts.LinearLayout;
import net.minecraft.client.gui.narration.NarratableEntry;
import net.minecraft.network.chat.Component;

import java.util.ArrayList;
import java.util.List;

public class ConfigList extends ContainerObjectSelectionList<ConfigList.Entry> {
    private final ModConfig config = InventoryPause.MOD_CONFIG;
    private static final int itemHeight = 25;
    private final ConfigScreen parent;
    private static final int numberOfColumns = 9;

    public ConfigList(ConfigScreen parent, Minecraft minecraft) {
        super(minecraft, parent.width, parent.height - 52, 20, itemHeight);
        this.parent = parent;
        initEntries();
    }

    private void initEntries() {
        CycleButton.Builder<PauseMode> onOffBuilder = CycleButton.builder(PauseMode::getDisplayName)
                .withValues(PauseMode.OFF, PauseMode.ON);
        var save = Tooltip.create(Component.translatable("menu.inventorypause.settings.disableSaving.tooltip"));
        var sounds = Tooltip.create(Component.translatable("menu.inventorypause.settings.pauseSounds.tooltip"));
        this.addEntry(new SplitEntry<>(new BorderedCycleButton(onOffBuilder.withInitialValue(config.disableSaving ? PauseMode.OFF : PauseMode.ON)
                .withTooltip(pauseMode -> save)
                .create(0, 0, 0, 0, Component.translatable("menu.inventorypause.settings.disableSaving"), (button, state) -> {
                    config.disableSaving = state == PauseMode.OFF;
                })), new BorderedCycleButton(onOffBuilder.withInitialValue(config.pauseSounds ? PauseMode.ON : PauseMode.OFF)
                .withTooltip(pauseMode -> sounds)
                .create(0, 0, 0, 0, Component.translatable("menu.inventorypause.settings.pauseSounds"), (button, state) -> {
                    config.pauseSounds = state == PauseMode.ON;
                }))));

        initDynamicEntries();
    }

    private void initDynamicEntries() {
        this.addEntry(new TextEntry(Component.translatable("menu.inventorypause.settings.title.pause")));

        CycleButton.Builder<PauseMode> builder = CycleButton.builder(PauseMode::getDisplayName)
                .withValues(PauseMode.OFF, PauseMode.SLOWMO, PauseMode.ON)
                .withTooltip(t -> TriStateTooltip.withState(Component.empty()).get(t));


        var contiguous = getContiguous(InventoryPause.GUI_ENTRIES.entries());

        for (var entry : contiguous) {
            entry.ifLeft(icons -> {
                System.out.println(icons);
                int numberOfRows = icons.size() / numberOfColumns + (icons.size() % numberOfColumns > 0 ? 1 : 0);
                for (int row = 0; row < numberOfRows; row++) {
                    LinearLayout layout = LinearLayout.horizontal().spacing(4);
                    for (int i = 0; i < icons.size(); i++) {
                        int padding = i % numberOfColumns == 0 ? 14 : 0;
                        if (i / numberOfColumns == row) {
                            layout.addChild(TexturedCycleButton.fromButtonInfo(0, 0, 20, 20, icons.get(i)), layout.newCellSettings().paddingLeft(padding));
                        }
                    }
                    layout.arrangeElements();
                    addEntry(new LinearLayoutEntry(layout));
                }
            });
            entry.ifRight(texts -> {
                System.out.println(texts);
                for (int i = 0; i < texts.size(); i += 2) {
                    final int finalI = i;
                    addEntry(new SplitEntry<>(new BorderedCycleButton(builder.withInitialValue(config.states.get(texts.get(i)))
                            .create(0, 0, 0, 0, Component.translatable("menu.inventorypause.settings." + texts.get(finalI).content()), (button, state) -> {
                                config.states.put(texts.get(finalI), state);
                            })), new BorderedCycleButton(builder.withInitialValue(config.states.get(texts.get(i + 1)))
                            .create(0, 0, 0, 0, Component.translatable("menu.inventorypause.settings." + texts.get(finalI + 1).content()), (button, state) -> {
                                config.states.put(texts.get(finalI + 1), state);
                            }))));
                }
            });
        }
        if (!InventoryPause.MOD_CONFIG.settingsForModpacks.hideModCompatButton) {
            addEntry(new SingleEntry<>(new Button.Builder(Component.translatable("menu.inventorypause.settings.mod_compat_options"), button -> this.minecraft.setScreen(new ModCompatScreen(parent)))
                    .tooltip(Tooltip.create(Component.translatable("menu.inventorypause.settings.mod_compat_options.tooltip"))).build()));
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

        @Override
        public List<? extends NarratableEntry> narratables() {
            return List.of();
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
