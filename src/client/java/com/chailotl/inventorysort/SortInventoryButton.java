package com.chailotl.inventorysort;

import io.netty.buffer.Unpooled;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.minecraft.client.gui.screen.ButtonTextures;
import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.client.gui.tooltip.Tooltip;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

public class SortInventoryButton extends InventoryButton
{
	private static final ButtonTextures BUTTON_TEXTURES = new ButtonTextures(
		new Identifier(InventorySortClient.MOD_ID, "sort"),
		new Identifier(InventorySortClient.MOD_ID, "sort_highlighted")
	);

	protected SortInventoryButton(HandledScreen<?> parent, int x, int y)
	{
		super(parent, x, y, BUTTON_TEXTURES, (button) -> {
			ClientPlayNetworking.send(InventorySortClient.SORT_INVENTORY, new PacketByteBuf(Unpooled.buffer()));
		});

		setTooltip(Tooltip.of(Text.translatable("gui.inventory_sort.sort_inventory.tooltip")));
	}
}