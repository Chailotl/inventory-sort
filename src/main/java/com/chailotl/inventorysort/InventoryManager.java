package com.chailotl.inventorysort;

import io.wispforest.owo.config.ConfigSynchronizer;
import io.wispforest.owo.config.Option;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemGroups;
import net.minecraft.item.ItemStack;
import net.minecraft.resource.featuretoggle.FeatureFlags;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.slot.Slot;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.Identifier;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class InventoryManager
{
	public static void sortInventory(ServerPlayerEntity player, Inventory inv)
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
				// Find existing stacks to merge with
				for (ItemStack foo: list)
				{

				}

				list.add(stack);
			}
		}

		// Poke the creative menu - this is dumb despite being necessary
		ItemGroups.updateDisplayContext(FeatureFlags.DEFAULT_ENABLED_FEATURES, false, player.getWorld().getRegistryManager());

		// Get player config
		var config = ConfigSynchronizer.getClientOptions(player, "inventory_sort");
		List<String> sortOrder = (List<String>) config.get(new Option.Key("sortOrder"));

		// Assemble comparators
		List<Comparator<ItemStack>> comparators = new ArrayList<>();

		for (String sort: sortOrder)
		{
			if (sort.equals("blocks"))
			{
				comparators.add(Compare.blocks);
			}
			else if (sort.equals("items"))
			{
				comparators.add(Compare.items);
			}
			else if (sort.equals("stackables"))
			{
				comparators.add(Compare.stackables);
			}
			else if (sort.equals("unstackables"))
			{
				comparators.add(Compare.unstackables);
			}
			else if (sort.equals("damage"))
			{
				comparators.add(Compare.damage);
			}
			else if (sort.equals("count"))
			{
				comparators.add(Compare.count);
			}
			else
			{
				String[] split = sort.split("[\\/:]");

				if (split.length == 1) { continue; }

				Identifier id = new Identifier(split[1], split[2]);

				if (split[0].equals("item"))
				{
					comparators.add(Compare.item(id));
				}
				else if (split[0].equals("item_tag"))
				{
					comparators.add(Compare.itemTag(id));
				}
				else if (split[0].equals("block_tag"))
				{
					comparators.add(Compare.blockTag(id));
				}
				else if (split[0].equals("item_group"))
				{
					comparators.add(Compare.itemGroup(id));
				}
				else if (split[0].equals("item_group_order"))
				{
					comparators.add(Compare.itemGroupOrder(id));
				}
			}
		}

		// Sort list
		list.sort(new ChainComparator(comparators));

		for (int i = 0; i < list.size(); ++i)
		{
			inv.setStack(i + offset, list.get(i));
		}
	}

	public static void depositAll(ScreenHandler screenHandler)
	{
		// Player slot appear after container slots
		int size = screenHandler.getSlot(0).inventory.size();
		for (int i = size; i < size + 27; ++i)
		{
			screenHandler.quickMove(null, i);
		}
	}

	public static void lootAll(ScreenHandler screenHandler)
	{
		int size = screenHandler.getSlot(0).inventory.size();
		for (int i = 0; i < size; ++i)
		{
			screenHandler.quickMove(null, i);
		}
	}

	public static void quickStack(ScreenHandler screenHandler)
	{
		Inventory container = screenHandler.getSlot(0).inventory;
		List<Slot> slots = screenHandler.slots;
		int size = slots.size();
		for (int i = size - 36; i < size - 9; ++i)
		{
			if (container.containsAny(Collections.singleton(slots.get(i).getStack().getItem())))
			{
				screenHandler.quickMove(null, i);
			}
		}
	}

	public static void restock(ServerPlayerEntity player)
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
}
