package com.chailotl.inventorysort;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.util.Identifier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class InventorySort implements ModInitializer
{
	public static final String MOD_ID = "inventory_sort";

	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	public static final Identifier SORT_INVENTORY = new Identifier(MOD_ID, "sort_inventory");
	public static final Identifier SORT_CONTAINER = new Identifier(MOD_ID, "sort_container");
	public static final Identifier QUICK_STACK = new Identifier(MOD_ID, "quick_stack");
	public static final Identifier RESTOCK = new Identifier(MOD_ID, "restock");
	public static final Identifier DEPOSIT_ALL = new Identifier(MOD_ID, "deposit_all");
	public static final Identifier LOOT_ALL = new Identifier(MOD_ID, "loot_all");

	@Override
	public void onInitialize()
	{
		ServerPlayNetworking.registerGlobalReceiver(SORT_INVENTORY, (server, player, handler, buf, responseSender) ->
				  server.execute(() -> InventoryManager.sortInventory(player, player.getInventory())));

		ServerPlayNetworking.registerGlobalReceiver(SORT_CONTAINER, (server, player, handler, buf, responseSender) ->
				  server.execute(() -> InventoryManager.sortInventory(player, player.currentScreenHandler.getSlot(0).inventory)));

		ServerPlayNetworking.registerGlobalReceiver(QUICK_STACK, (server, player, handler, buf, responseSender) ->
				  server.execute(() -> InventoryManager.quickStack(player.currentScreenHandler)));

		ServerPlayNetworking.registerGlobalReceiver(RESTOCK, (server, player, handler, buf, responseSender) ->
				  server.execute(() -> InventoryManager.restock(player)));

		ServerPlayNetworking.registerGlobalReceiver(DEPOSIT_ALL, (server, player, handler, buf, responseSender) ->
				  server.execute(() -> InventoryManager.depositAll(player.currentScreenHandler)));

		ServerPlayNetworking.registerGlobalReceiver(LOOT_ALL, (server, player, handler, buf, responseSender) ->
				  server.execute(() -> InventoryManager.lootAll(player.currentScreenHandler)));
	}
}