package com.chailotl.inventorysort;

import net.minecraft.item.ItemStack;

import java.util.Comparator;
import java.util.List;

class ChainComparator implements Comparator<ItemStack>
{
	private List<Comparator<ItemStack>> comparatorList;

	public ChainComparator(List<Comparator<ItemStack>> comparatorList)
	{
		this.comparatorList = comparatorList;
	}

	@Override
	public int compare(ItemStack lhs, ItemStack rhs) {
		int result;
		for(Comparator<ItemStack> comparator : comparatorList) {
			if ((result = comparator.compare(lhs, rhs)) != 0) {
				return result;
			}
		}
		return 0;
	}
}