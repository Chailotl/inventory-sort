package com.chailotl.inventory_sort.buttons;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.client.gui.tooltip.Tooltip;
import net.minecraft.text.Text;

/*? if >=1.20.5 {*/
import com.chailotl.inventory_sort.Networking;
/*?} else {*/
/*import com.chailotl.inventory_sort.Main;
import io.netty.buffer.Unpooled;
import net.minecraft.network.PacketByteBuf;
*//*?}*/

@Environment(EnvType.CLIENT)
public class SortContainerButton extends InventoryButton
{
	public SortContainerButton(HandledScreen<?> parent, int x, int y)
	{
		/*? if >=1.20.5 {*/
		super(parent, x, y,  true, Icon.SORT, (button) ->
				ClientPlayNetworking.send(new Networking.SortContainerPayload()));
		/*?} else {*/
		/*super(parent, x, y,  true, Icon.SORT, (button) -> {
			ClientPlayNetworking.send(Main.SORT_CONTAINER,
				new PacketByteBuf(Unpooled.buffer()));
		});
		*//*?}*/

		setTooltip(Tooltip.of(Text.translatable("gui.inventory_sort.tooltip.sort_container")));
	}
}