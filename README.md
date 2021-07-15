# Inventory Pause
<a href="https://www.curseforge.com/minecraft/mc-mods/inventory-pause-forge"><img src="http://cf.way2muchnoise.eu/versions/495153_all(555-0C8E8E-fff-010101).svg" alt="Supported Versions"></a>
<a href="https://modrinth.com/mod/inventory-pause-forge"><img src="https://waffle.coffee/modrinth/inventory-pause-forge/downloads" alt="Modrinth Download Count"></a>
<a href="https://www.curseforge.com/minecraft/mc-mods/inventory-pause-forge"><img src="http://cf.way2muchnoise.eu/full_495153_downloads(E04E14-555-fff-010101-1C1C1C).svg" alt="CurseForge Download Count"></a>
<a href="https://github.com/PieKing1215/InvMove/blob/master/LICENSE.md"><img src="https://img.shields.io/github/license/macbrayne/inventory-pause-forge?style=flat&color=0C8E8E" alt="License"></a>
---
## Pause your single player game when you open your inventory.
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
</table>

![](https://media.giphy.com/media/mCJQCNkacCMGpUDj3h/giphy.gif)

This is an ongoing port of [umollu's inventory-pause mod](https://modrinth.com/mod/inventory-pause) to forge which is currently fabric only.
It supports pausing on opening the inventory, opening furnaces, crafting tables, shulker boxes and configurable basic support for other mods.

All of these features can be disabled individually inside the config menu (accessible via the forge mod list).

This mod is __client-side only__ and won't be active in LAN worlds or on servers.

Current version: 1.16.5 only (1.17 as soon as Forge updates)

## [Releases](https://github.com/macbrayne/inventory-pause-forge/releases)

This mod depends on [Cloth Config Forge](https://www.curseforge.com/minecraft/mc-mods/cloth-config-forge/).

The mod is licensed under the [MIT License](LICENSE.md)

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
- Pause on Inventory / Pause on Creative Inventory / Pause on Furnace / Pause on Crafting Table / Pause on Shulker Box:
Enables / Disables the respective feature

__Mod Compat__:
- Custom mod class names: A custom list of GUI class names can be input here to force the screen to pause the game

</details>