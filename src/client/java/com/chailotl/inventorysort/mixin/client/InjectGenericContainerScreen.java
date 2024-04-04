package com.chailotl.inventorysort.mixin.client;

import com.chailotl.inventorysort.InventorySort;
import io.netty.buffer.Unpooled;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.ingame.GenericContainerScreen;
import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.client.gui.tooltip.Tooltip;
import net.minecraft.client.gui.widget.TexturedButtonWidget;
import net.minecraft.client.util.InputUtil;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.screen.GenericContainerScreenHandler;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(GenericContainerScreen.class)
public abstract class InjectGenericContainerScreen extends HandledScreen<GenericContainerScreenHandler> {
	@Shadow
	private int rows;

	public InjectGenericContainerScreen(GenericContainerScreenHandler handler, PlayerInventory inventory, Text title) {
		super(handler, inventory, title);
	}

	private static boolean shift()
	{
		return InputUtil.isKeyPressed(MinecraftClient.getInstance().getWindow().getHandle(), 340) ||
				  InputUtil.isKeyPressed(MinecraftClient.getInstance().getWindow().getHandle(), 344);
	}

	private void addButton(int x, int y, int u, int v, Identifier id, Identifier id2, String key) {
		TexturedButtonWidget button = new TexturedButtonWidget(x, y, 9, 9, u, v, 9, InventorySort.SORT_ICONS, 27, 18, (buttonWidget) ->
		{
			ClientPlayNetworking.send(shift() ? id2 : id, new PacketByteBuf(Unpooled.buffer()));
		}, Text.translatable(key));
		button.setTooltip(Tooltip.of(Text.translatable(key)));

		addDrawableChild(button);
	}

	@Override
	protected void init() {
		super.init();

		int y_ = y + 19 + rows * 18;

		addButton(x + 160, y_, 0, 0, InventorySort.SORT_INVENTORY, InventorySort.SORT_INVENTORY, "gui.inventorysort.sort.tooltip");
		addButton(x + 160 - 11, y_, 9, 0, InventorySort.QUICK_STACK, InventorySort.DEPOSIT_ALL, "gui.inventorysort.quick_stack.tooltip");
		addButton(x + 160 - 22, y_, 18, 0, InventorySort.RESTOCK, InventorySort.LOOT_ALL, "gui.inventorysort.restock.tooltip");
		addButton(x + 160, y + 6, 0, 0, InventorySort.SORT_CONTAINER, InventorySort.SORT_CONTAINER, "gui.inventorysort.sort_container.tooltip");
	}
}