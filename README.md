# Inventory Pause
## Pause your singleplayer game when you open your inventory and more.

![](https://media.giphy.com/media/mCJQCNkacCMGpUDj3h/giphy.gif)

This lightweight __client-side mod__ supports pausing or even just slowing down your __singleplayer__ game when you open menus like the
- Inventory
- Crafting Table, Chest, Furnace...
- Hopper, Brewing Stand, Enchanting Table...
- Gamemode Switcher, Death Screen...
- Villager, Horse Inventory...
and supports modded screens (if configured correctly)!

_If a modded screen behaves weirdly when paused try adding it to the list of "Compat mod class names" to make it slow down instead of pausing it._
Screens can be configured individually inside the config menu (accessible via the Forge mod list).
This mod is __client-side only__ and won't be active in LAN worlds or on servers.

Supported versions:
- 1.19.4: 2.0.0
- 1.20-1.20.1: 3.0.0
- 1.20.3-1.20.4: 4.0.0
- 1.20.5-1.20.6: 5.0.0

Releases prior to 1.2 depend on [Cloth Config Forge](https://www.curseforge.com/minecraft/mc-mods/cloth-config-forge/).
## License


The mod is licensed under the [EUPL 1.2](https://github.com/macbrayne/inventory-pause-forge/blob/1.20.6/LICENSE).


## Tutorial: How to add custom screens
<details>
  <summary>1.2+ (click to expand)</summary>
    
1. Check whether you have assigned the keybind for "Add to Custom Screens".
2. Navigate to the menu you want to add and press the "Add to Custom Screens" key you assigned earlier. You should get a confirmation in chat when you do so.
3. Verify it worked by checking if the screen is now paused.

If you experience any problems with modded screens (e.g. modded UI elements not working) try adding the screen to the list of "Compat mod class names".
</details>
<details>
  <summary>0.1 - 1.1.2 (click to expand)</summary>

To add custom screens to the config you need the internal name of the screen to add. If you don't know it you can try this:
1. Check the Minecraft Controls settings if you have assigned the keybinds for "Copy Class Name" and "Open Settings" (only available on versions released after October 2022)
2. Open the settings and change "Enable Debug Mode" to true. This should make an overlay appear on the top left of the screen whenever you have a screen open.
3. Navigate to the menu you want to add and depending on your mod version:
    * (0.8.2, 0.9, 1.0 or 1.1): note down the topmost text (e.g. `appeng.client.gui.implementations.IOPortScreen`). Alternatively copy it from the Minecraft log. That is the internal name of the screen.
    * (0.8.3, 0.9.1, 1.0.1, 1.1.1 or later): press the "Copy Class" key you assigned earlier. You should get a confirmation in chat when you do so.
4. Add an entry to "Custom Mod class names" and paste the internal name.

Now the screen should be paused. If it works disable "Enable Debug Mode" again.

If you experience any problems (e.g. modded UI elements not working) try adding the screen to the list of "Compat mod class names".
</details>
Note: This mod will not be able to work on all types of screens due to technical limitations

## In-Game Config
<details>
<summary>2.0+ (click to expand)</summary>

### Main Config
 ![Ingame Config Mod Version 2.0.0 and up](https://github.com/user-attachments/assets/4d1b5270-2ac6-4bf0-a236-3094997758fd)
- Enable Mod: enables / disables every part of the mod apart from the debug overlay
- Save on Pause: this can help if you experience lag spikes when quickly opening and closing inventories
- Pause Sounds: if enabled music will stop playing in paused screens
  Enable / disable pausing of specific inventories by clicking on the corresponding button

### Mod Compat Options
![Mod Compat Options Mod Version 2.0.0 and up](https://github.com/user-attachments/assets/235455c5-5209-4bd1-bc7e-9eb81a5b015e)

- Custom mod class names: List of custom screens which get paused
- Compat mod class names: List of custom screens which don't get fully paused. When they're open Minecraft time passes slower instead.
- Slow motion speed: Specifies how much screens should be slowed down
- Enable Debug Mode: enables an overlay for easier mod compat configuration
</details>
<details>
<summary>1.2+ (click to expand)</summary>

 ### Main Config
 ![Ingame Config Mod Version 1.2 and up](https://user-images.githubusercontent.com/27809595/219973047-b3745ffa-a01b-4570-880b-886a27ecb72b.png)
- Enable Mod: enables / disables every part of the mod apart from the debug overlay
- Save on Pause: this can help if you experience lag spikes when quickly opening and closing inventories
- Pause Sounds: if enabled music will stop playing in paused screens
Enable / disable pausing of specific inventories by clicking on the corresponding button
    
### Mod Compat Options
![Mod Compat Options Mod Version 1.2 and up](https://user-images.githubusercontent.com/27809595/219819318-6ca3852b-4e13-4fe0-957c-e27e556cc0fe.png)

- Custom mod class names: List of custom screens which get paused
- Compat mod class names: List of custom screens which don't get fully paused. They get ticked in regular intervals as specified in Time between compat ticks.
- Time between compat ticks: Specifies how often (20 ticks = one second) screens listed in Compat mod class names are ticked
- Enable Debug Mode: enables an overlay for easier mod compat configuration
</details>
<details>
  <summary>0.1 - 1.1.2 (click to expand)</summary>

![Ingame Config Mod Version 1.1.2 and down](https://user-images.githubusercontent.com/27809595/122673510-0c51f580-d1d1-11eb-8fef-8fb3c3c5bf22.png)

__General__:
- Enable Mod: enables / disables every part of the mod apart from the debug overlay
- Enable Save on Pause: this can help if you experience lag spikes when quickly opening and closing
  inventories
- Enable Debug Mode: enables an overlay for easier mod compat configuration
- Debug Overlay Options:
  - X-Coordinate / Y-Coordinate: The position of the debug overlay
  - Maximum Crawl Depth: Limit the number of items to display (useful for deep gui hierarchies)

__Abilities__:
- Pause on Inventory / Pause on Creative Inventory / Pause on Death Screen / Pause on Furnace / Pause on Crafting Table / Pause on Shulker Box:
  Enables / Disables the respective feature

__Mod Compat__:
- Custom mod class names: A custom list of GUI class names can be input here to force the screen to pause the game
- Time between compat ticks: Frequency in ticks (20 ticks = every second) to unpause when in screens specified in "Compat mod class names"
- Compat mod class names: A custom list of GUI class names can be input here to tick less often
</details>
