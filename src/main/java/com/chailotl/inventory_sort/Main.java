package com.chailotl.inventory_sort;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.util.Identifier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/*? if >=1.20.5 {*/
import com.chailotl.inventory_sort.buttons.DepositButton;
import com.chailotl.inventory_sort.buttons.LootButton;
import com.chailotl.inventory_sort.buttons.SortContainerButton;
import com.chailotl.inventory_sort.buttons.SortInventoryButton;
import net.fabricmc.fabric.api.networking.v1.PayloadTypeRegistry;
/*?}*/

public class Main implements ModInitializer
{
	public static final String MOD_ID = "inventory_sort";
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	public static final boolean IS_ITEM_FAVORITES_LOADED = FabricLoader.getInstance().isModLoaded("itemfavorites");
	public static final com.chailotl.inventory_sort.InventorySortConfig CONFIG = com.chailotl.inventory_sort.InventorySortConfig.createAndLoad();

	/*? if >=1.20.5 {*/
	public static final Identifier SORT_INVENTORY = Identifier.of(MOD_ID, "sort_inventory");
	public static final Identifier SORT_CONTAINER = Identifier.of(MOD_ID, "sort_container");
	public static final Identifier QUICK_STACK = Identifier.of(MOD_ID, "quick_stack");
	public static final Identifier RESTOCK = Identifier.of(MOD_ID, "restock");
	public static final Identifier DEPOSIT_ALL = Identifier.of(MOD_ID, "deposit_all");
	public static final Identifier LOOT_ALL = Identifier.of(MOD_ID, "loot_all");
	/*?} else {*/
	/*public static final Identifier SORT_INVENTORY = new Identifier(MOD_ID, "sort_inventory");
	public static final Identifier SORT_CONTAINER = new Identifier(MOD_ID, "sort_container");
	public static final Identifier QUICK_STACK = new Identifier(MOD_ID, "quick_stack");
	public static final Identifier RESTOCK = new Identifier(MOD_ID, "restock");
	public static final Identifier DEPOSIT_ALL = new Identifier(MOD_ID, "deposit_all");
	public static final Identifier LOOT_ALL = new Identifier(MOD_ID, "loot_all");
	*//*?}*/

	@Override
	public void onInitialize()
	{
		/*? if >=1.20.5 {*/
		PayloadTypeRegistry.playC2S().register(SortInventoryButton.SortInventoryPayload.ID, SortInventoryButton.SortInventoryPayload.CODEC);
		PayloadTypeRegistry.playC2S().register(SortContainerButton.SortContainerPayload.ID, SortContainerButton.SortContainerPayload.CODEC);
		PayloadTypeRegistry.playC2S().register(DepositButton.QuickStackPayload.ID, DepositButton.QuickStackPayload.CODEC);
		PayloadTypeRegistry.playC2S().register(LootButton.RestockPayload.ID, LootButton.RestockPayload.CODEC);
		PayloadTypeRegistry.playC2S().register(DepositButton.DepositAllPayload.ID, DepositButton.DepositAllPayload.CODEC);
		PayloadTypeRegistry.playC2S().register(LootButton.LootAllPayload.ID, LootButton.LootAllPayload.CODEC);

		ServerPlayNetworking.registerGlobalReceiver(SortInventoryButton.SortInventoryPayload.ID, (payload, context) ->
				context.server().execute(() -> InventoryManager.sortInventory(context.player(), context.player().getInventory())));

		ServerPlayNetworking.registerGlobalReceiver(SortContainerButton.SortContainerPayload.ID, (payload, context) ->
				context.server().execute(() -> InventoryManager.sortInventory(context.player(), context.player().currentScreenHandler.getSlot(0).inventory)));

		ServerPlayNetworking.registerGlobalReceiver(DepositButton.QuickStackPayload.ID, (payload, context) ->
				context.server().execute(() -> InventoryManager.quickStack(context.player().currentScreenHandler)));

		ServerPlayNetworking.registerGlobalReceiver(LootButton.RestockPayload.ID, (payload, context) ->
				context.server().execute(() -> InventoryManager.restock(context.player())));

		ServerPlayNetworking.registerGlobalReceiver(DepositButton.DepositAllPayload.ID, (payload, context) ->
				context.server().execute(() -> InventoryManager.depositAll(context.player().currentScreenHandler)));

		ServerPlayNetworking.registerGlobalReceiver(LootButton.LootAllPayload.ID, (payload, context) ->
				context.server().execute(() -> InventoryManager.lootAll(context.player().currentScreenHandler)));
		/*?} else {*/
		/*ServerPlayNetworking.registerGlobalReceiver(SORT_INVENTORY, (server, player, handler, buf, responseSender) ->
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
		*//*?}*/
	}
}