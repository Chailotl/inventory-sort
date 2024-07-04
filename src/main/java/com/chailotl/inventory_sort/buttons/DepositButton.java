package com.chailotl.inventory_sort.buttons;

import com.chailotl.inventory_sort.ShiftTooltip;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;

/*? if >=1.20.5 {*/
import com.chailotl.inventory_sort.Networking;
/*?} else {*/
/*import com.chailotl.inventory_sort.Main;
import io.netty.buffer.Unpooled;
import net.minecraft.network.PacketByteBuf;
*//*?}*/

@Environment(EnvType.CLIENT)
public final class DepositButton extends InventoryButton
{
	public DepositButton(HandledScreen<?> parent, int x, int y)
	{
		/*? if >=1.20.5 {*/
		super(parent, x, y, false, Icon.DEPOSIT, (button) ->
				ClientPlayNetworking.send(Screen.hasShiftDown() ?
						new Networking.DepositAllPayload() : new Networking.QuickStackPayload()
        ));
		/*?} else {*/
		/*super(parent, x, y, false, Icon.DEPOSIT, (button) -> {
			ClientPlayNetworking.send(Screen.hasShiftDown()
							? Main.DEPOSIT_ALL
							: Main.QUICK_STACK,
					new PacketByteBuf(Unpooled.buffer()));
		});
		*//*?}*/

		setTooltip(new ShiftTooltip(
			Text.translatable("gui.inventory_sort.tooltip.quick_stack")
				.append("\n")
				.append(getHoldToActionText("gui.inventory_sort.tooltip.deposit_all", Formatting.GRAY)),
			Text.translatable("gui.inventory_sort.tooltip.deposit_all")
				.append("\n")
				.append(getHoldToActionText("gui.inventory_sort.tooltip.deposit_all", Formatting.WHITE))));
	}
}