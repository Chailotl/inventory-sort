package com.chailotl.inventorysort;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.slot.Slot;
import net.minecraft.util.Identifier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class InventorySort implements ModInitializer
{
	// This logger is used to write text to the console and the log file.
	// It is considered best practice to use your mod id as the logger's name.
	// That way, it's clear which mod wrote info, warnings, and errors.
	public static final Logger LOGGER = LoggerFactory.getLogger("inventory-sort");

	public static final String MOD_ID = "inventory-sort";

	public static final Identifier SORT_ICONS = new Identifier(MOD_ID, "textures/gui/sort_icons.png");
	public static final Identifier SORT_INVENTORY = new Identifier(MOD_ID, "sort_inventory");
	public static final Identifier SORT_CONTAINER = new Identifier(MOD_ID, "sort_container");
	public static final Identifier RANDOMIZE = new Identifier(MOD_ID, "randomize");
	public static final Identifier QUICK_STACK = new Identifier(MOD_ID, "quick_stack");
	public static final Identifier RESTOCK = new Identifier(MOD_ID, "restock");
	public static final Identifier DEPOSIT_ALL = new Identifier(MOD_ID, "deposit_all");
	public static final Identifier LOOT_ALL = new Identifier(MOD_ID, "loot_all");

	@Override
	public void onInitialize()
	{
		ServerPlayNetworking.registerGlobalReceiver(SORT_INVENTORY, (server, player, handler, buf, responseSender) ->
				  server.execute(() -> sortInventory(player.getInventory())));

		ServerPlayNetworking.registerGlobalReceiver(SORT_CONTAINER, (server, player, handler, buf, responseSender) ->
				  server.execute(() -> sortInventory(player.currentScreenHandler.getSlot(0).inventory)));

		ServerPlayNetworking.registerGlobalReceiver(RANDOMIZE, (server, player, handler, buf, responseSender) ->
				  server.execute(() -> randomizeInventory(player.currentScreenHandler.getSlot(0).inventory)));

		ServerPlayNetworking.registerGlobalReceiver(DEPOSIT_ALL, (server, player, handler, buf, responseSender) ->
				  server.execute(() ->
				  {
					  int size = player.currentScreenHandler.getSlot(0).inventory.size();
					  for (int i = size; i < size + 27; ++i)
					  {
						  player.currentScreenHandler.quickMove(null, i);
					  }
				  }));

		ServerPlayNetworking.registerGlobalReceiver(LOOT_ALL, (server, player, handler, buf, responseSender) ->
				  server.execute(() ->
				  {
					  int size = player.currentScreenHandler.getSlot(0).inventory.size();
					  for (int i = 0; i < size; ++i)
					  {
						  player.currentScreenHandler.quickMove(null, i);
					  }
				  }));

		ServerPlayNetworking.registerGlobalReceiver(QUICK_STACK, (server, player, handler, buf, responseSender) ->
				  server.execute(() ->
				  {
					  Inventory container = player.currentScreenHandler.getSlot(0).inventory;
					  List<Slot> slots = player.currentScreenHandler.slots;
					  int size = slots.size();
					  for (int i = size - 36; i < size - 9; ++i)
					  {
						  if (container.containsAny(Collections.singleton(slots.get(i).getStack().getItem())))
						  {
							  player.currentScreenHandler.quickMove(null, i);
						  }
					  }
				  }));

		ServerPlayNetworking.registerGlobalReceiver(RESTOCK, (server, player, handler, buf, responseSender) ->
				  server.execute(() ->
				  {
					  Inventory container = player.currentScreenHandler.getSlot(0).inventory;
					  PlayerInventory inventory = player.getInventory();

					  for (int i = 0; i < 36; ++i)
					  {
						  ItemStack stackOne = inventory.getStack(i);
						  int needed = stackOne.getMaxCount() - stackOne.getCount();
						  if (needed > 0)
						  {
							  for (int j = 0; j < container.size(); ++j)
							  {
								  ItemStack stackTwo = container.getStack(j);
								  if (stackOne.getItem() == stackTwo.getItem())
								  {
									  int count = stackTwo.getCount();

									  if (needed <= count)
									  {
										  inventory.insertStack(container.removeStack(j, needed));
										  needed = 0;
									  } else
									  {
										  inventory.insertStack(container.removeStack(j, count));
										  needed -= count;
									  }
								  }
							  }
						  }
					  }
				  }));
	}

	public static void randomizeInventory(Inventory inv)
	{
		List<ItemStack> list = new ArrayList<>();

		int offset = 0;
		int size = inv.size();

		if (inv instanceof PlayerInventory)
		{
			offset = 9;
			size = 36;
		}

		for (int i = offset; i < size; ++i)
		{
			ItemStack stack = inv.removeStack(i);
			if (!stack.isEmpty())
			{
				list.add(stack);
			}
		}

		Collections.shuffle(list);

		for (int i = 0; i < list.size(); ++i)
		{
			inv.setStack(i + offset, list.get(i));
		}
	}

	public static void sortInventory(Inventory inv)
	{
		List<ItemStack> list = new ArrayList<>();

		int offset = 0;
		int size = inv.size();

		if (inv instanceof PlayerInventory)
		{
			offset = 9;
			size = 36;
		}

		for (int i = offset; i < size; ++i)
		{
			ItemStack stack = inv.removeStack(i);
			if (!stack.isEmpty())
			{
				list.add(stack);
			}
		}

		list.sort(Compare.blocks
				  .thenComparing(Compare.itemGroup(new Identifier("minecraft", "natural_blocks")))
				  .thenComparing(Compare.itemGroupOrder(new Identifier("minecraft", "building_blocks")))
				  .thenComparing(Compare.stackables)
				  .thenComparing(Compare.itemTags(new Identifier("inventory-sort", "netherite_equipment")))
				  .thenComparing(Compare.itemTags(new Identifier("inventory-sort", "diamond_equipment")))
				  .thenComparing(Compare.itemTags(new Identifier("inventory-sort", "golden_equipment")))
				  .thenComparing(Compare.itemTags(new Identifier("inventory-sort", "iron_equipment")))
				  .thenComparing(Compare.itemTags(new Identifier("inventory-sort", "stone_equipment")))
				  .thenComparing(Compare.itemTags(new Identifier("inventory-sort", "chainmail_equipment")))
				  .thenComparing(Compare.itemTags(new Identifier("inventory-sort", "wooden_equipment")))
				  .thenComparing(Compare.itemTags(new Identifier("inventory-sort", "leather_equipment")))
				  .thenComparing(Compare.itemTags(new Identifier("inventory-sort", "weapons")))
				  .thenComparing(Compare.tools)
				  .thenComparing(Compare.armor)
				  .thenComparing(Compare.itemTags(new Identifier("c", "swords")))
				  .thenComparing(Compare.itemTags(new Identifier("c", "bows")))
				  .thenComparing(Compare.item(new Identifier("minecraft", "bow")))
				  .thenComparing(Compare.item(new Identifier("minecraft", "crossbow")))
				  .thenComparing(Compare.itemTags(new Identifier("c", "spears")))
				  .thenComparing(Compare.itemTags(new Identifier("c", "shields")))
				  .thenComparing(Compare.itemTags(new Identifier("c", "pickaxes")))
				  .thenComparing(Compare.itemTags(new Identifier("c", "axes")))
				  .thenComparing(Compare.itemTags(new Identifier("c", "shovels")))
				  .thenComparing(Compare.itemTags(new Identifier("c", "hoes")))
				  .thenComparing(Compare.itemTags(new Identifier("minecraft", "head_armor")))
				  .thenComparing(Compare.itemTags(new Identifier("minecraft", "chest_armor")))
				  .thenComparing(Compare.itemTags(new Identifier("minecraft", "leg_armor")))
				  .thenComparing(Compare.itemTags(new Identifier("minecraft", "foot_armor")))
				  .thenComparing(Compare.damage));

		for (int i = 0; i < list.size(); ++i)
		{
			inv.setStack(i + offset, list.get(i));
		}
	}
}