package com.chailotl.inventorysort;

import net.minecraft.block.Block;
import net.minecraft.item.*;
import net.minecraft.registry.Registries;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.util.Identifier;
import org.apache.commons.lang3.ArrayUtils;

import java.util.Comparator;

public class Compare
{
	// Place blocks first
	public static Comparator<ItemStack> blocks = (lhs, rhs) ->
	{
		int l = lhs.getItem() instanceof BlockItem ? 0 : 1;
		int r = rhs.getItem() instanceof BlockItem ? 0 : 1;

		return l - r;
	};

	// Place items first
	public static Comparator<ItemStack> items = (lhs, rhs) ->
	{
		int l = !(lhs.getItem() instanceof BlockItem) ? 0 : 1;
		int r = !(rhs.getItem() instanceof BlockItem) ? 0 : 1;

		return l - r;
	};

	// Place stackables first
	public static Comparator<ItemStack> stackables = (lhs, rhs) ->
	{
		int l = lhs.getItem().getMaxCount() > 1 ? 0 : 1;
		int r = rhs.getItem().getMaxCount() > 1 ? 0 : 1;

		return l - r;
	};

	// Place unstackables first
	public static Comparator<ItemStack> unstackables = (lhs, rhs) ->
	{
		int l = lhs.getItem().getMaxCount() == 1 ? 0 : 1;
		int r = rhs.getItem().getMaxCount() == 1 ? 0 : 1;

		return l - r;
	};

	public static Comparator<ItemStack> item(Identifier id)
	{
		return (lhs, rhs) ->
		{
			Item item = Registries.ITEM.get(id);

			int l = lhs.isOf(item) ? 0 : 1;
			int r = rhs.isOf(item) ? 0 : 1;

			return l - r;
		};
	}

	public static Comparator<ItemStack> itemTags(Identifier id)
	{
		return (lhs, rhs) ->
		{
			int l = lhs.isIn(TagKey.of(RegistryKeys.ITEM, id)) ? 0 : 1;
			int r = rhs.isIn(TagKey.of(RegistryKeys.ITEM, id)) ? 0 : 1;

			return l - r;
		};
	}

	public static Comparator<ItemStack> blockTags(Identifier id)
	{
		return (lhs, rhs) ->
		{
			int l = 1;
			int r = 1;

			Item lItem = lhs.getItem();
			Item rItem = rhs.getItem();

			if (lItem instanceof BlockItem)
			{
				if (Block.getBlockFromItem(lItem)
						  .getDefaultState()
						  .isIn(TagKey.of(RegistryKeys.BLOCK, id)))
				{
					l = 0;
				}
			}

			if (rItem instanceof BlockItem)
			{
				if (Block.getBlockFromItem(rItem)
						  .getDefaultState()
						  .isIn(TagKey.of(RegistryKeys.BLOCK, id)))
				{
					r = 0;
				}
			}

			return l - r;
		};
	}

	public static Comparator<ItemStack> itemGroup(Identifier id)
	{
		return (lhs, rhs) ->
		{
			ItemGroup group = Registries.ITEM_GROUP.get(id);

			if (group == null)
			{
				return 0;
			}

			int l = group.contains(lhs.getItem().getDefaultStack()) ? 0 : 1;
			int r = group.contains(rhs.getItem().getDefaultStack()) ? 0 : 1;

			InventorySort.LOGGER.info(l + " + " + r + " = " + (l - r));

			return l - r;
		};
	}

	public static Comparator<ItemStack> itemGroupOrder(Identifier id)
	{
		return (lhs, rhs) ->
		{
			ItemGroup group = Registries.ITEM_GROUP.get(id);

			if (group == null)
			{
				return 0;
			}

			Object[] arr = group.getSearchTabStacks().toArray(new ItemStack[0]);

			int l = ArrayUtils.indexOf(arr, lhs);
			int r = ArrayUtils.indexOf(arr, rhs);

			return l - r;
		};
	}

	public static Comparator<ItemStack> tools = (lhs, rhs) ->
	{
		int l = lhs.getItem() instanceof ToolItem ? 0 : 1;
		int r = rhs.getItem() instanceof ToolItem ? 0 : 1;

		return l - r;
	};

	public static Comparator<ItemStack> armor = (lhs, rhs) ->
	{
		int l = lhs.getItem() instanceof ArmorItem ? 0 : 1;
		int r = rhs.getItem() instanceof ArmorItem ? 0 : 1;

		return l - r;
	};

	public static Comparator<ItemStack> damage = (lhs, rhs) -> lhs.getDamage() - rhs.getDamage();
}
