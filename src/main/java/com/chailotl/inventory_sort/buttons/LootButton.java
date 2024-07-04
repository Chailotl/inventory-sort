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
public class LootButton extends InventoryButton
{
	public LootButton(HandledScreen<?> parent, int x, int y)
	{
		/*? if >=1.20.5 {*/
		super(parent, x, y, false, Icon.LOOT, (button) ->
				ClientPlayNetworking.send(Screen.hasShiftDown() ?
						new Networking.LootAllPayload() : new Networking.RestockPayload()
        ));
		/*?} else {*/
		/*super(parent, x, y, false, Icon.LOOT, (button) -> {
			ClientPlayNetworking.send(Screen.hasShiftDown()
				? Main.LOOT_ALL
				: Main.RESTOCK,
				new PacketByteBuf(Unpooled.buffer()));
		});
		*//*?}*/

		setTooltip(new ShiftTooltip(
			Text.translatable("gui.inventory_sort.tooltip.restock")
				.append("\n")
				.append(getHoldToActionText("gui.inventory_sort.tooltip.loot_all", Formatting.GRAY)),
			Text.translatable("gui.inventory_sort.tooltip.loot_all")
				.append("\n")
				.append(getHoldToActionText("gui.inventory_sort.tooltip.loot_all", Formatting.WHITE))));
	}
}