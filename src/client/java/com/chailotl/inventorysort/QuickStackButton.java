package com.chailotl.inventorysort;

import io.netty.buffer.Unpooled;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.minecraft.client.gui.screen.ButtonTextures;
import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.client.gui.tooltip.Tooltip;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

public class QuickStackButton extends InventoryButton
{
	private static final ButtonTextures BUTTON_TEXTURES = new ButtonTextures(
		new Identifier(InventorySortClient.MOD_ID, "quick_stack"),
		new Identifier(InventorySortClient.MOD_ID, "quick_stack_highlighted")
	);

	protected QuickStackButton(HandledScreen<?> parent, int x, int y)
	{
		super(parent, x, y, BUTTON_TEXTURES, (button) -> {
			ClientPlayNetworking.send(!shift() ? InventorySortClient.QUICK_STACK : InventorySortClient.DEPOSIT_ALL, new PacketByteBuf(Unpooled.buffer()));
		});

		setTooltip(Tooltip.of(Text.translatable("gui.inventory_sort.quick_stack.tooltip")));
	}

	@Override
	protected void updateTooltip()
	{
		String str = !shift() ? "gui.inventory_sort.quick_stack.tooltip" : "gui.inventory_sort.deposit_all.tooltip";
		setTooltip(Tooltip.of(Text.translatable(str)));
	}
}