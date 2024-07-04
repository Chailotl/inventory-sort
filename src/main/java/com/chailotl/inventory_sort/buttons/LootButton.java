package com.chailotl.inventory_sort.buttons;

import com.chailotl.inventory_sort.Main;
import com.chailotl.inventory_sort.ShiftTooltip;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;

/*? if >=1.20.5 {*/
import net.minecraft.network.packet.CustomPayload;
import net.minecraft.network.codec.PacketCodec;
/*?} else {*/
/*import io.netty.buffer.Unpooled;
*//*?}*/

@Environment(EnvType.CLIENT)
public class LootButton extends InventoryButton
{
	public LootButton(HandledScreen<?> parent, int x, int y)
	{
		/*? if >=1.20.5 {*/
		super(parent, x, y, false, Icon.LOOT, (button) ->
				ClientPlayNetworking.send(Screen.hasShiftDown() ?
						new LootAllPayload() : new RestockPayload()
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

	/*? if >=1.20.5 {*/
	public record LootAllPayload() implements CustomPayload {
		public static final CustomPayload.Id<LootAllPayload> ID = new CustomPayload.Id<>(Main.LOOT_ALL);
		public static PacketCodec<PacketByteBuf, LootAllPayload> CODEC =
				PacketCodec.ofStatic(LootAllPayload::encode, LootAllPayload::decode);

		@Override
		public Id<? extends CustomPayload> getId() {
			return ID;
		}

		private static void encode(PacketByteBuf buf, LootAllPayload self) {}
		private static LootAllPayload decode(PacketByteBuf buf) {return new LootAllPayload();}
	}

	public record RestockPayload() implements CustomPayload {
		public static final CustomPayload.Id<RestockPayload> ID = new CustomPayload.Id<>(Main.RESTOCK);
		public static PacketCodec<PacketByteBuf, RestockPayload> CODEC =
				PacketCodec.ofStatic(RestockPayload::encode, RestockPayload::decode);

		@Override
		public Id<? extends CustomPayload> getId() {
			return ID;
		}

		private static void encode(PacketByteBuf buf, RestockPayload self) {}
		private static RestockPayload decode(PacketByteBuf buf) {return new RestockPayload();}
	}
	/*?}*/
}