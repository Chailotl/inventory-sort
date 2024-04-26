package com.chailotl.inventory_sort;

import com.chailotl.inventory_sort.buttons.DepositButton;
import com.chailotl.inventory_sort.buttons.LootButton;
import com.chailotl.inventory_sort.buttons.SortContainerButton;
import com.chailotl.inventory_sort.buttons.SortInventoryButton;
import com.chailotl.inventory_sort.mixin.AccessorCreativeInventoryScreen;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.client.screen.v1.ScreenEvents;
import net.fabricmc.fabric.api.client.screen.v1.Screens;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.client.gui.screen.ingame.CreativeInventoryScreen;
import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.client.gui.screen.ingame.InventoryScreen;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemGroups;
import net.minecraft.screen.*;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

public class Main implements ModInitializer, ClientModInitializer
{
	public static final String MOD_ID = "inventory_sort";
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	public static final com.chailotl.inventory_sort.InventorySortConfig CONFIG = com.chailotl.inventory_sort.InventorySortConfig.createAndLoad();

	public static final Identifier SORT_INVENTORY = new Identifier(MOD_ID, "sort_inventory");
	public static final Identifier SORT_CONTAINER = new Identifier(MOD_ID, "sort_container");
	public static final Identifier QUICK_STACK = new Identifier(MOD_ID, "quick_stack");
	public static final Identifier RESTOCK = new Identifier(MOD_ID, "restock");
	public static final Identifier DEPOSIT_ALL = new Identifier(MOD_ID, "deposit_all");
	public static final Identifier LOOT_ALL = new Identifier(MOD_ID, "loot_all");

	private final HashSet<Class<? extends ScreenHandler>> ignoreScreens = new HashSet<>();
	private final HashSet<Class<? extends ScreenHandler>> sortableScreens = new HashSet<>();
	private final Hashtable<Class<? extends ScreenHandler>, Integer> offsetScreens = new Hashtable<>();

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

	@Override
	public void onInitializeClient()
	{
		ignoreScreens.add(BeaconScreenHandler.class);
		ignoreScreens.add(LoomScreenHandler.class);
		ignoreScreens.add(CreativeInventoryScreen.CreativeScreenHandler.class);

		sortableScreens.add(PlayerScreenHandler.class);
		sortableScreens.add(GenericContainerScreenHandler.class);
		sortableScreens.add(ShulkerBoxScreenHandler.class);
		sortableScreens.add(Generic3x3ContainerScreenHandler.class);
		sortableScreens.add(HopperScreenHandler.class);
		sortableScreens.add(HorseScreenHandler.class);

		offsetScreens.put(GenericContainerScreenHandler.class, -1);
		offsetScreens.put(ShulkerBoxScreenHandler.class, -1);
		offsetScreens.put(EnchantmentScreenHandler.class, 1);

		ScreenEvents.AFTER_INIT.register((client, screen, scaledWidth, scaledHeight) ->
		{
			if (!(screen instanceof HandledScreen<?> handledScreen)) { return; }

			ScreenHandler screenHandler = handledScreen.getScreenHandler();

			if (ignoreScreens.stream().anyMatch(clazz -> clazz.isInstance(screenHandler))) { return; }

			// Need to figure out a way to update it when changing tabs
			//if (handledScreen instanceof CreativeInventoryScreen && ((AccessorCreativeInventoryScreen) handledScreen).getSelectedTab().getType() != ItemGroup.Type.INVENTORY) { return; }

			int y = offsetScreens.getOrDefault(screenHandler.getClass(), 0);

			// Adding buttons
			SortInventoryButton sortInventoryButton = new SortInventoryButton(handledScreen, 0, y);

			if (sortableScreens.stream().anyMatch(clazz -> clazz.isInstance(screenHandler)) ||
				screenHandler.slots.size() >= 36 + 18)
			{
				DepositButton depositButton = new DepositButton(handledScreen, -11, y);
				LootButton lootButton = new LootButton(handledScreen, -22, y);
				SortContainerButton sortContainerButton = new SortContainerButton(handledScreen, 0, 0);

				Screens.getButtons(handledScreen).add(sortContainerButton);
				Screens.getButtons(handledScreen).add(lootButton);
				Screens.getButtons(handledScreen).add(depositButton);
			}

			// This is down here to preserve tab order
			Screens.getButtons(handledScreen).add(sortInventoryButton);
		});
	}
}