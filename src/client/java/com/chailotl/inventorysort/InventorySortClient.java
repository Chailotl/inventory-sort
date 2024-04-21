package com.chailotl.inventorysort;

import com.chailotl.inventorysort.buttons.DepositButton;
import com.chailotl.inventorysort.buttons.LootButton;
import com.chailotl.inventorysort.buttons.SortContainerButton;
import com.chailotl.inventorysort.buttons.SortInventoryButton;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.screen.v1.ScreenEvents;
import net.fabricmc.fabric.api.client.screen.v1.Screens;
import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.client.gui.screen.ingame.InventoryScreen;
import net.minecraft.screen.*;
import net.minecraft.util.Identifier;

import java.util.HashSet;

public class
InventorySortClient implements ClientModInitializer
{
	public static final String MOD_ID = "inventory_sort";

	public static final com.chailotl.inventorysort.InventorySortConfig CONFIG = com.chailotl.inventorysort.InventorySortConfig.createAndLoad();

	public static final Identifier SORT_INVENTORY = new Identifier(MOD_ID, "sort_inventory");
	public static final Identifier SORT_CONTAINER = new Identifier(MOD_ID, "sort_container");
	public static final Identifier LOOT_ALL = new Identifier(MOD_ID, "loot_all");
	public static final Identifier DEPOSIT_ALL = new Identifier(MOD_ID, "deposit_all");
	public static final Identifier RESTOCK = new Identifier(MOD_ID, "restock");
	public static final Identifier QUICK_STACK = new Identifier(MOD_ID, "quick_stack");

	private final HashSet<Class<? extends ScreenHandler>> sortableScreens = new HashSet<>();
	private final HashSet<Class<? extends ScreenHandler>> offsetScreens = new HashSet<>();

	@Override
	public void onInitializeClient()
	{
		sortableScreens.add(PlayerScreenHandler.class);
		sortableScreens.add(GenericContainerScreenHandler.class);
		sortableScreens.add(ShulkerBoxScreenHandler.class);
		sortableScreens.add(Generic3x3ContainerScreenHandler.class);
		sortableScreens.add(HopperScreenHandler.class);
		sortableScreens.add(HorseScreenHandler.class);

		offsetScreens.add(PlayerScreenHandler.class);
		offsetScreens.add(Generic3x3ContainerScreenHandler.class);
		offsetScreens.add(HopperScreenHandler.class);
		offsetScreens.add(HorseScreenHandler.class);

		ScreenEvents.AFTER_INIT.register((client, screen, scaledWidth, scaledHeight) ->
		{
			if (!(screen instanceof HandledScreen<?> handledScreen)) { return; }

			ScreenHandler screenHandler = handledScreen.getScreenHandler();

			if (sortableScreens.stream().noneMatch(clazz -> clazz.isInstance(screenHandler))) { return; }

			// Silly offset for some screens
			int y = offsetScreens.stream().anyMatch(clazz -> clazz.isInstance(screenHandler)) ? 1 : 0;

			// Adding buttons
			SortInventoryButton sortInventoryButton = new SortInventoryButton(handledScreen, 0, y);

			// Ideally I should be filtering the slot types but I'm lazy
			if (!(handledScreen instanceof InventoryScreen) && screenHandler.slots.size() >= 36 + 3)
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