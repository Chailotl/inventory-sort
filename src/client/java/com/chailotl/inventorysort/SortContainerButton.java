package com.chailotl.inventorysort;

import io.netty.buffer.Unpooled;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.client.gui.tooltip.Tooltip;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.text.Text;

public class SortContainerButton extends InventoryButton
{
	protected SortContainerButton(HandledScreen<?> parent, int x, int y)
	{
		super(parent, x, y, 0, 0, (button) -> {
			ClientPlayNetworking.send(!shift() ? InventorySortClient.SORT_CONTAINER : InventorySortClient.RANDOMIZE, new PacketByteBuf(Unpooled.buffer()));
		});

		topAnchor = true;
		setTooltip(Tooltip.of(Text.translatable("gui.inventory_sort.sort_container.tooltip")));
	}
}