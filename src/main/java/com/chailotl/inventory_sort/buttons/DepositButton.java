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
public final class DepositButton extends InventoryButton
{
	public DepositButton(HandledScreen<?> parent, int x, int y)
	{
		/*? if >=1.20.5 {*/
		super(parent, x, y, false, Icon.DEPOSIT, (button) ->
				ClientPlayNetworking.send(Screen.hasShiftDown() ?
						new DepositAllPayload() : new QuickStackPayload()
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

	/*? if >=1.20.5 {*/
	public record DepositAllPayload() implements CustomPayload {
		public static Id<DepositAllPayload> ID = new Id<>(Main.DEPOSIT_ALL);
		public static PacketCodec<PacketByteBuf, DepositAllPayload> CODEC =
				PacketCodec.ofStatic(DepositAllPayload::encode, DepositAllPayload::decode);

		@Override
		public Id<? extends CustomPayload> getId() {
			return ID;
		}

		private static void encode(PacketByteBuf buf, DepositAllPayload self) {}
		private static DepositAllPayload decode(PacketByteBuf buf) {return new DepositAllPayload();}
	}

	public record QuickStackPayload() implements CustomPayload {
		public static final Id<QuickStackPayload> ID = new Id<>(Main.QUICK_STACK);
		public static PacketCodec<PacketByteBuf, QuickStackPayload> CODEC =
				PacketCodec.ofStatic(QuickStackPayload::encode, QuickStackPayload::decode);

		@Override
		public Id<? extends CustomPayload> getId() {
			return ID;
		}

		private static void encode(PacketByteBuf buf, QuickStackPayload self) {}
		private static QuickStackPayload decode(PacketByteBuf buf) {return new QuickStackPayload();}
	}
	/*?}*/
}