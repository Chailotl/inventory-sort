package com.chailotl.inventorysort;

import io.wispforest.owo.config.Option;
import io.wispforest.owo.config.annotation.Config;
import io.wispforest.owo.config.annotation.Modmenu;
import io.wispforest.owo.config.annotation.Sync;

import java.util.Arrays;
import java.util.List;

@Modmenu(modId = InventorySortClient.MOD_ID)
@Config(name = InventorySortClient.MOD_ID, wrapperName = "InventorySortConfig")
public class ConfigModel
{
	@Sync(Option.SyncMode.INFORM_SERVER)
	public List<String> sortOrder = Arrays.asList(
			  "blocks",
			  "stackables",
			  "item_tag/sort:netherite_equipment",
			  "item_tag/sort:diamond_equipment",
			  "item_tag/sort:golden_equipment",
			  "item_tag/sort:iron_equipment",
			  "item_tag/sort:stone_equipment",
			  "item_tag/sort:chainmail_equipment",
			  "item_tag/sort:wooden_equipment",
			  "item_tag/sort:leather_equipment",
			  "item_tag/sort:combat",
			  "item_tag/sort:tools",
			  "item_tag/sort:armor",
			  "item_tag/c:swords",
			  "item_tag/c:bows",
			  "item_tag/c:spears",
			  "item_tag/c:shields",
			  "item_tag/c:pickaxes",
			  "item_tag/c:axes",
			  "item_tag/c:shovels",
			  "item_tag/c:hoes",
			  "item_tag/minecraft:head_armor",
			  "item_tag/minecraft:chest_armor",
			  "item_tag/minecraft:leg_armor",
			  "item_tag/minecraft:foot_armor",
			  "damage",
			  "item_group_order/minecraft:search"
	);
}