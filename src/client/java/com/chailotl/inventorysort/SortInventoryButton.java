package com.chailotl.inventorysort;

import io.netty.buffer.Unpooled;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.client.gui.tooltip.Tooltip;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.text.Text;

public class SortInventoryButton extends InventoryButton
{
	protected SortInventoryButton(HandledScreen<?> parent, int x, int y)
	{
		super(parent, x, y, 0, 0, (button) -> {
			ClientPlayNetworking.send(InventorySortClient.SORT_INVENTORY, new PacketByteBuf(Unpooled.buffer()));
		});

		setTooltip(Tooltip.of(Text.translatable("gui.inventory_sort.sort_inventory.tooltip")));
	}
}