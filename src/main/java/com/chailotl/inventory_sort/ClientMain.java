package com.chailotl.inventory_sort;

import com.chailotl.inventory_sort.buttons.DepositButton;
import com.chailotl.inventory_sort.buttons.LootButton;
import com.chailotl.inventory_sort.buttons.SortContainerButton;
import com.chailotl.inventory_sort.buttons.SortInventoryButton;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.screen.v1.ScreenEvents;
import net.fabricmc.fabric.api.client.screen.v1.Screens;
import net.minecraft.client.gui.screen.ingame.CreativeInventoryScreen;
import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.screen.*;

import java.util.Hashtable;

public class ClientMain implements ClientModInitializer
{
	private final Hashtable<String, Integer> offsetScreens = new Hashtable<>();

	@Override
	public void onInitializeClient()
	{
		offsetScreens.put("net.minecraft.screen.GenericContainerScreenHandler", -1);
		offsetScreens.put("net.minecraft.screen.ShulkerBoxScreenHandler", -1);
		offsetScreens.put("net.minecraft.screen.EnchantmentScreenHandler", 1);

		ScreenEvents.AFTER_INIT.register((client, screen, scaledWidth, scaledHeight) ->
		{
			if (!(screen instanceof HandledScreen<?> handledScreen)) { return; }

			ScreenHandler screenHandler = handledScreen.getScreenHandler();

			if (Main.CONFIG.disabledScreens().stream().anyMatch(clazz -> {
				try
				{
					return Class.forName(clazz).isInstance(screenHandler);
				}
				catch (ClassNotFoundException e)
				{
					return false;
				}
			}) || screenHandler instanceof CreativeInventoryScreen.CreativeScreenHandler) { return; }

			// Need to figure out a way to update it when changing tabs
			//if (handledScreen instanceof CreativeInventoryScreen && ((AccessorCreativeInventoryScreen) handledScreen).getSelectedTab().getType() != ItemGroup.Type.INVENTORY) { return; }

			int y = offsetScreens.getOrDefault(screenHandler.getClass().getCanonicalName(), 0);

			// Adding buttons
			SortInventoryButton sortInventoryButton = new SortInventoryButton(handledScreen, 0, y);

			if (Main.CONFIG.containerScreens().stream().anyMatch(clazz -> {
				try
				{
					return Class.forName(clazz).isInstance(screenHandler);
				}
				catch (ClassNotFoundException e)
				{
					return false;
				}
			}) || screenHandler.slots.size() >= 36 + 18)
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