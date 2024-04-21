package com.chailotl.inventorysort.buttons;

import com.chailotl.inventorysort.InventorySortClient;
import io.netty.buffer.Unpooled;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.client.gui.tooltip.Tooltip;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

public class SortContainerButton extends InventoryButton
{
	public SortContainerButton(HandledScreen<?> parent, int x, int y)
	{
		super(parent, x, y,  true, Icon.SORT, (button) -> {
			ClientPlayNetworking.send(InventorySortClient.SORT_CONTAINER,
				new PacketByteBuf(Unpooled.buffer()));
		});

		setTooltip(Tooltip.of(Text.translatable("gui.inventory_sort.tooltip.sort_container")));
	}
}