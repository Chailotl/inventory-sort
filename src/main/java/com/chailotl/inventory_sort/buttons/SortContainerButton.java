package com.chailotl.inventory_sort.buttons;

import com.chailotl.inventory_sort.Main;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.client.gui.tooltip.Tooltip;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.text.Text;

/*? if >=1.20.5 {*/
import net.minecraft.network.packet.CustomPayload;
import net.minecraft.network.codec.PacketCodec;
/*?} else {*/
/*import io.netty.buffer.Unpooled;
*//*?}*/

@Environment(EnvType.CLIENT)
public class SortContainerButton extends InventoryButton
{
	public SortContainerButton(HandledScreen<?> parent, int x, int y)
	{
		/*? if >=1.20.5 {*/
		super(parent, x, y,  true, Icon.SORT, (button) ->
				ClientPlayNetworking.send(new SortContainerPayload()));
		/*?} else {*/
		/*super(parent, x, y,  true, Icon.SORT, (button) -> {
			ClientPlayNetworking.send(Main.SORT_CONTAINER,
				new PacketByteBuf(Unpooled.buffer()));
		});
		*//*?}*/

		setTooltip(Tooltip.of(Text.translatable("gui.inventory_sort.tooltip.sort_container")));
	}

	/*? if >=1.20.5 {*/
	public record SortContainerPayload() implements CustomPayload {
		public static final CustomPayload.Id<SortContainerPayload> ID = new CustomPayload.Id<>(Main.SORT_CONTAINER);
		public static PacketCodec<PacketByteBuf, SortContainerPayload> CODEC =
				PacketCodec.ofStatic(SortContainerPayload::encode, SortContainerPayload::decode);

		@Override
		public Id<? extends CustomPayload> getId() {
			return ID;
		}

		private static void encode(PacketByteBuf buf, SortContainerPayload self) {}
		private static SortContainerPayload decode(PacketByteBuf buf) {return new SortContainerPayload();}
	}
	/*?}*/
}