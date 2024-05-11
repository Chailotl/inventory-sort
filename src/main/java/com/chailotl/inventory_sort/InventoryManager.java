package com.chailotl.inventory_sort;

import io.wispforest.owo.config.ConfigSynchronizer;
import io.wispforest.owo.config.Option;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.slot.Slot;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.Identifier;

import java.util.*;

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
			ItemStack stack = inv.getStack(i);
			if (stack.isEmpty() || isFavorite(stack)) {  continue; }

			// Find existing stacks to merge with
			for (ItemStack storedStack: list)
			{
				int needed = storedStack.getMaxCount() - storedStack.getCount();

				if (needed > 0 && stack.getItem() == storedStack.getItem() && isNbtEqual(stack, storedStack))
				{
					int count = stack.getCount();

					if (count <= needed)
					{
						storedStack.setCount(storedStack.getCount() + count);
						count = 0;
					}
					else
					{
						storedStack.setCount(storedStack.getCount() + needed);
						count -= needed;
					}

					stack.setCount(count);
				}
			}

			// Stack might be empty after merging
			if (!stack.isEmpty()) { list.add(stack); }
		}

		// Poke the creative menu - this is dumb despite being necessary
		//ItemGroups.updateDisplayContext(FeatureFlags.DEFAULT_ENABLED_FEATURES, false, player.getWorld().getRegistryManager());

		// Get player config
		var config = ConfigSynchronizer.getClientOptions(player, "inventory_sort");
		List<String> sortOrder = (List<String>) config.get(new Option.Key("sortOrder"));

		// Assemble comparators
		List<Comparator<ItemStack>> comparators = new ArrayList<>();

		for (String sort: sortOrder)
		{
			switch (sort)
			{
				case "blocks" -> comparators.add(ComparatorTypes.blocks);
				case "items" -> comparators.add(ComparatorTypes.items);
				case "stackables" -> comparators.add(ComparatorTypes.stackables);
				case "unstackables" -> comparators.add(ComparatorTypes.unstackables);
				case "damage" -> comparators.add(ComparatorTypes.damage);
				case "count" -> comparators.add(ComparatorTypes.count);
				default ->
				{
					String[] split = sort.split("[/:]");
					if (split.length == 1)
					{
						continue;
					}
					Identifier id = new Identifier(split[1], split[2]);
					switch (split[0])
					{
						case "item" -> comparators.add(ComparatorTypes.item(id));
						case "item_tag" -> comparators.add(ComparatorTypes.itemTag(id));
						case "block_tag" -> comparators.add(ComparatorTypes.blockTag(id));
						case "item_group" -> comparators.add(ComparatorTypes.itemGroup(id));
						case "item_group_order" -> comparators.add(ComparatorTypes.itemGroupOrder(id));
					}
				}
			}
		}

		// Sort list
		list.sort(new ComparatorChain(comparators));

		for (int i = offset; i < size; ++i)
		{
			if (isFavorite(inv.getStack(i))) { continue; }
			ItemStack stack = list.size() > 0 ? list.remove(0) : ItemStack.EMPTY;
			inv.setStack(i, stack);
		}
	}

	public static void depositAll(ScreenHandler screenHandler)
	{
		// Player slot appear after container slots
		int size = screenHandler.getSlot(0).inventory.size();
		for (int i = size; i < size + 27; ++i)
		{
			if (isFavorite(screenHandler.getSlot(i).getStack())) { continue; }
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
			if (isFavorite(screenHandler.getSlot(i).getStack())) { continue; }

			ItemStack playerInvStack = slots.get(i).getStack();
			if (container.containsAny(stack -> stack.getItem() == playerInvStack.getItem() && isNbtEqual(stack, playerInvStack)))
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
					if (stackOne.getItem() == stackTwo.getItem() && isNbtEqual(stackOne, stackTwo))
					{
						int count = stackTwo.getCount();

						if (needed <= count)
						{
							inventory.insertStack(container.removeStack(j, needed));
							needed = 0;
						}
						else
						{
							inventory.insertStack(container.removeStack(j, count));
							needed -= count;
						}
					}
				}
			}
		}
	}

	public static boolean isFavorite(ItemStack itemStack)
	{
		if (!Main.IS_ITEM_FAVORITES_LOADED) { return false; }

		try
		{
			return (boolean)ItemStack.class.getMethod("isFavorite").invoke(itemStack);
		}
		catch (Exception e)
		{
			return false;
		}
	}

	public static boolean isNbtEqual(ItemStack stack1, ItemStack stack2)
	{
        return !stack1.hasNbt() || !stack2.hasNbt() || stack1.getNbt().equals(stack2.getNbt());
    }
}
