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
public class SortInventoryButton extends InventoryButton
{
	public SortInventoryButton(HandledScreen<?> parent, int x, int y)
	{
		/*? if >=1.20.5 {*/
		super(parent, x, y, false, Icon.SORT, (button) ->
				ClientPlayNetworking.send(new SortInventoryPayload()));
		/*?} else {*/
		/*super(parent, x, y, false, Icon.SORT, (button) -> {
			ClientPlayNetworking.send(Main.SORT_INVENTORY,
				new PacketByteBuf(Unpooled.buffer()));
		});
		*//*?}*/

		setTooltip(Tooltip.of(Text.translatable("gui.inventory_sort.tooltip.sort_inventory")));
	}

	/*? if >=1.20.5 {*/
	public record SortInventoryPayload() implements CustomPayload {
		public static final CustomPayload.Id<SortInventoryPayload> ID = new CustomPayload.Id<>(Main.SORT_INVENTORY);
		public static PacketCodec<PacketByteBuf, SortInventoryPayload> CODEC =
				PacketCodec.ofStatic(SortInventoryPayload::encode, SortInventoryPayload::decode);

		@Override
		public Id<? extends CustomPayload> getId() {
			return ID;
		}

		private static void encode(PacketByteBuf buf, SortInventoryPayload self) {}
		private static SortInventoryPayload decode(PacketByteBuf buf) {return new SortInventoryPayload();}
	}
	/*?}*/
}