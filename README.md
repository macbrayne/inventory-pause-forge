# Inventory Pause
<a href="https://modrinth.com/mod/inventory-pause-forge"><img src="https://img.shields.io/modrinth/game-versions/inventory-pause-forge?label=modrinth&color=00AF5C&logo=modrinth" alt="Supported Versions"></a>
<a href="https://modrinth.com/mod/inventory-pause-forge"><img src="https://img.shields.io/badge/dynamic/json?color=00AF5C&label=modrinth&suffix=%20downloads&query=downloads&url=https://api.modrinth.com/v2/project/F39sgYmY&style=flat&logo=modrinth" alt="Modrinth Download Count"></a>
<a href="https://www.curseforge.com/minecraft/mc-mods/inventory-pause-forge"><img src="http://cf.way2muchnoise.eu/full_495153_downloads(E04E14-555-fff-010101-1C1C1C).svg" alt="CurseForge Download Count"></a>
<a href="https://github.com/macbrayne/inventory-pause-forge"><img src="https://img.shields.io/badge/side-client--only-5da545" alt="Side: Client-Only"></a>
<a href="https://github.com/macbrayne/inventory-pause-forge/blob/master/LICENSE.md"><img src="https://img.shields.io/github/license/macbrayne/inventory-pause-forge?style=flat&color=0C8E8E" alt="License"></a>
---
## Pause your single player game when you open your inventory and more.
<table>
    <tr>
        <td>Forge</td>
        <td><a href="https://github.com/macbrayne/inventory-pause-forge/">GitHub</a></td>
        <td><a href="https://modrinth.com/mod/inventory-pause-forge">Modrinth</a></td>
        <td><a href="https://www.curseforge.com/minecraft/mc-mods/inventory-pause-forge">CurseForge</a></td>
    </tr>
    <tr>
        <td>Fabric (by umollu)</td>
        <td><a href="https://github.com/umollu/inventory-pause/">GitHub</a></td>
        <td><a href="https://modrinth.com/mod/inventory-pause">Modrinth</a></td>
        <td><a href="https://www.curseforge.com/minecraft/mc-mods/inventory-pause">CurseForge</a></td>
    </tr>
    <tr>
        <td>Fabric Legacy</td>
        <td><a href="https://github.com/macbrayne/inventory-pause-cursed">GitHub</a></td>
        <td><a href="https://modrinth.com/mod/inventory-pause-cursed">Modrinth</a></td>
    </tr>
</table>

![](https://media.giphy.com/media/mCJQCNkacCMGpUDj3h/giphy.gif)

This small client-side mod supports pausing when opening the inventory, when dying, opening furnaces, crafting tables, shulker boxes and even has basic support for other mods.
In case of incompatibilities try adding the modded screen to the list of "Compat mod class names".

All features can be disabled individually inside the config menu (accessible via the Forge mod list).

This mod is __client-side only__ and won't be active in LAN worlds or on servers.

Current versions:
- 1.16.5: 0.8.3
- 1.17: 0.9.1
- 1.18: 1.0.2
- 1.19: 1.1.2

## [Releases](https://github.com/macbrayne/inventory-pause-forge/releases)

This mod depends on [Cloth Config Forge](https://www.curseforge.com/minecraft/mc-mods/cloth-config-forge/).

The mod is licensed under the [EUPL 1.2](LICENSE) apart from three files which are [MIT](LICENSE-MIT) licensed.


## Tutorial: How to add custom screens

To add custom screens to the config you need the internal name of the screen to add. If you don't know that you can try this:
1. Check the Minecraft Controls settings if you have assigned the keybinds for "Copy Class Name" and "Open Settings" (only available on versions released after October 2022)
2. Open the settings and change "Enable Debug Mode" to true. This should make an overlay appear on the top left of the screen whenever you have a screen open.
3. Navigate to the menu you want to add and depending on your mod version:
  * (0.8.2, 0.9, 1.0 or 1.1): note down the topmost text (e.g. `appeng.client.gui.implementations.IOPortScreen`). Alternatively copy it from the Minecraft log. That is the internal name of the screen.
  * (0.8.3, 0.9.1, 1.0.1, 1.1.1 or later): press the "Copy Class" key you assigned earlier. You should get a confirmation in chat when you do so.
4. Add an entry to "Custom Mod class names" and paste the internal name.

Now the screen should be paused. If it works disable "Enable Debug Mode" again.

If you experience any problems (e.g. modded UI elements not working) try adding the screen to the list of "Compat mod class names".

Note: This mod will not be able to work on all types of screens due to technical limitations

## In-Game Config
<details>
  <summary>Click to expand.</summary>

![image](https://user-images.githubusercontent.com/27809595/122673510-0c51f580-d1d1-11eb-8fef-8fb3c3c5bf22.png)

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
