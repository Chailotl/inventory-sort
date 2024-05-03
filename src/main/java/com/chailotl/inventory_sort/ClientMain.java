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

import java.util.HashSet;
import java.util.Hashtable;

public class ClientMain implements ClientModInitializer
{
	private final HashSet<Class<? extends ScreenHandler>> ignoreScreens = new HashSet<>();
	private final HashSet<Class<? extends ScreenHandler>> containerScreens = new HashSet<>();
	private final Hashtable<Class<? extends ScreenHandler>, Integer> offsetScreens = new Hashtable<>();

	@Override
	public void onInitializeClient()
	{
		ignoreScreens.add(BeaconScreenHandler.class);
		ignoreScreens.add(LoomScreenHandler.class);
		ignoreScreens.add(CreativeInventoryScreen.CreativeScreenHandler.class);

		containerScreens.add(GenericContainerScreenHandler.class);
		containerScreens.add(ShulkerBoxScreenHandler.class);
		containerScreens.add(Generic3x3ContainerScreenHandler.class);
		containerScreens.add(HopperScreenHandler.class);
		containerScreens.add(HorseScreenHandler.class);

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

			if (containerScreens.stream().anyMatch(clazz -> clazz.isInstance(screenHandler)) ||
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