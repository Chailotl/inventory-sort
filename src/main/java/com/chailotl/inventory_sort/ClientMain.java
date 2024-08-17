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
import net.minecraft.registry.Registries;
import net.minecraft.screen.*;
import net.minecraft.util.Identifier;

import java.util.Hashtable;

public class ClientMain implements ClientModInitializer
{
	private final Hashtable<Identifier, Integer> offsetScreens = new Hashtable<>();

	@Override
	public void onInitializeClient()
	{
		offsetScreens.put(Identifier.of("minecraft", "generic_9x1"), -1);
		offsetScreens.put(Identifier.of("minecraft", "generic_9x2"), -1);
		offsetScreens.put(Identifier.of("minecraft", "generic_9x3"), -1);
		offsetScreens.put(Identifier.of("minecraft", "generic_9x4"), -1);
		offsetScreens.put(Identifier.of("minecraft", "generic_9x5"), -1);
		offsetScreens.put(Identifier.of("minecraft", "generic_9x6"), -1);
		offsetScreens.put(Identifier.of("minecraft", "shulker_box"), -1);
		offsetScreens.put(Identifier.of("minecraft", "enchantment"), 1);

		ScreenEvents.AFTER_INIT.register((client, screen, scaledWidth, scaledHeight) ->
		{
			if (!(screen instanceof HandledScreen<?> handledScreen)) { return; }

			ScreenHandler screenHandler = handledScreen.getScreenHandler();
			Identifier id = null;

			try
			{
				id = Registries.SCREEN_HANDLER.getId(screenHandler.getType());
				//Main.LOGGER.info(id.toString()); // DEBUG
			}
			catch (Exception ignored) {}

			if (screenHandler instanceof HorseScreenHandler)
			{
				id = Identifier.of("minecraft", "horse");
			}
			else if (screenHandler instanceof CreativeInventoryScreen.CreativeScreenHandler)
			{
				id = Identifier.of("minecraft", "creative");
			}

			// Ignore disabled screens
			if (Main.CONFIG.disabledScreens().contains(id)) { return; }
			if (Main.CONFIG.disableModdedScreens() && !id.getNamespace().equals("minecraft")) { return; }

			// Need to figure out a way to update it when changing tabs
			//if (handledScreen instanceof CreativeInventoryScreen && ((AccessorCreativeInventoryScreen) handledScreen).getSelectedTab().getType() != ItemGroup.Type.INVENTORY) { return; }

			int y = id == null ? 0 : offsetScreens.getOrDefault(id, 0);

			// Adding buttons
			SortInventoryButton sortInventoryButton = new SortInventoryButton(handledScreen, 0, y);

			if (Main.CONFIG.containerScreens().contains(id) || screenHandler.slots.size() >= 36 + 18)
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