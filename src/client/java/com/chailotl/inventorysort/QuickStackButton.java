package com.chailotl.inventorysort;

import io.netty.buffer.Unpooled;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.minecraft.client.gui.screen.ButtonTextures;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.client.gui.tooltip.Tooltip;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.text.Style;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.Identifier;

public class QuickStackButton extends InventoryButton
{
	private static final ButtonTextures BUTTON_TEXTURES = new ButtonTextures(
		new Identifier(InventorySortClient.MOD_ID, "quick_stack"),
		new Identifier(InventorySortClient.MOD_ID, "quick_stack_highlighted")
	);

	protected QuickStackButton(HandledScreen<?> parent, int x, int y)
	{
		super(parent, x, y, false, BUTTON_TEXTURES, (button) -> {
			ClientPlayNetworking.send(!Screen.hasShiftDown() ? InventorySortClient.QUICK_STACK : InventorySortClient.DEPOSIT_ALL, new PacketByteBuf(Unpooled.buffer()));
		});

		setTooltip(new ShiftTooltip(
			Text.translatable("gui.inventory_sort.tooltip.quick_stack")
				.append("\n")
				.append(Text.translatable(
						"gui.inventory_sort.tooltip.hold_to_action",
						Text.translatable("gui.inventory_sort.tooltip.shift")
							.setStyle(Style.EMPTY.withColor(Formatting.GRAY)),
						Text.translatable("gui.inventory_sort.tooltip.deposit_all"))
					.setStyle(Style.EMPTY.withColor(Formatting.DARK_GRAY))),
			Text.translatable("gui.inventory_sort.tooltip.deposit_all")));
	}
}