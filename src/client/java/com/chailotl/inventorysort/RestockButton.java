package com.chailotl.inventorysort;

import io.netty.buffer.Unpooled;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.minecraft.client.gui.screen.ButtonTextures;
import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.client.gui.tooltip.Tooltip;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

public class RestockButton extends InventoryButton
{
	private static final ButtonTextures BUTTON_TEXTURES = new ButtonTextures(
		new Identifier(InventorySortClient.MOD_ID, "restock"),
		new Identifier(InventorySortClient.MOD_ID, "restock_highlighted")
	);

	protected RestockButton(HandledScreen<?> parent, int x, int y)
	{
		super(parent, x, y, BUTTON_TEXTURES, (button) -> {
			ClientPlayNetworking.send(!shift() ? InventorySortClient.RESTOCK : InventorySortClient.LOOT_ALL, new PacketByteBuf(Unpooled.buffer()));
		});

		setTooltip(Tooltip.of(Text.translatable("gui.inventory_sort.restock.tooltip")));
	}

	@Override
	protected void updateTooltip()
	{
		String str = !shift() ? "gui.inventory_sort.restock.tooltip" : "gui.inventory_sort.loot_all.tooltip";
		setTooltip(Tooltip.of(Text.translatable(str)));
	}
}