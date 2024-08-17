package com.chailotl.inventory_sort;

import com.chailotl.sushi_bar.owo.config.SushiModmenu;
import io.wispforest.owo.config.Option;
import io.wispforest.owo.config.annotation.*;

import java.util.Arrays;
import java.util.List;

@SushiModmenu(modId = Main.MOD_ID)
@Config(name = Main.MOD_ID, wrapperName = "InventorySortConfig")
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
		"item_group_order/minecraft:search",
		"count",
		"damage"
	);

	public List<String> containerScreens = Arrays.asList(
		"net.minecraft.screen.GenericContainerScreenHandler",
		"net.minecraft.screen.ShulkerBoxScreenHandler",
		"net.minecraft.screen.Generic3x3ContainerScreenHandler",
		"net.minecraft.screen.HopperScreenHandler",
		"net.minecraft.screen.HorseScreenHandler"
	);

	/*public List<String> screenOffsetsKey = Arrays.asList(
		"net.minecraft.screen.GenericContainerScreenHandler",
		"net.minecraft.screen.ShulkerBoxScreenHandler",
		"net.minecraft.screen.EnchantmentScreenHandler"
	);

	public List<Integer> screenOffsetsValue = Arrays.asList(
		-1,
		-1,
		1
	);*/

	public List<String> disabledScreens = Arrays.asList(
		"net.minecraft.screen.BeaconScreenHandler",
		"net.minecraft.screen.LoomScreenHandler"
	);

	public boolean disableModdedScreens = false;
}