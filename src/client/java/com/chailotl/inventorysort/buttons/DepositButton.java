package com.chailotl.inventorysort.buttons;

import com.chailotl.inventorysort.InventorySortClient;
import com.chailotl.inventorysort.ShiftTooltip;
import io.netty.buffer.Unpooled;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.text.Style;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.Identifier;

public class DepositButton extends InventoryButton
{
	public DepositButton(HandledScreen<?> parent, int x, int y)
	{
		super(parent, x, y, false, Icon.DEPOSIT, (button) -> {
			ClientPlayNetworking.send(Screen.hasShiftDown()
				? InventorySortClient.DEPOSIT_ALL
				: InventorySortClient.QUICK_STACK,
				new PacketByteBuf(Unpooled.buffer()));
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