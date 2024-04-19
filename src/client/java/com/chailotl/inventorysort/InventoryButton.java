package com.chailotl.inventorysort;

import com.chailotl.inventorysort.mixin.client.AccessorHandledScreen;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.ButtonTextures;
import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.client.gui.widget.TexturedButtonWidget;
import net.minecraft.client.util.InputUtil;
import net.minecraft.util.Identifier;

public abstract class InventoryButton extends TexturedButtonWidget
{
	private final AccessorHandledScreen parent;
	private final int offsetX;
	private final int offsetY;
	protected boolean topAnchor = false;

	protected InventoryButton(HandledScreen<?> parent, int x, int y, ButtonTextures buttonTextures, PressAction onPress)
	{
		super(x, y, 9, 9, buttonTextures, onPress);

		this.parent = (AccessorHandledScreen) parent;
		offsetX = x;
		offsetY = y;
	}

	@Override
	public void renderButton(DrawContext context, int mouseX, int mouseY, float delta) {
		setX(parent.getX() + offsetX + 160);
		setY(parent.getY() + offsetY + (!topAnchor ? parent.getBackgroundHeight() - 95 : 6));

		updateTooltip();
		setFocused(false); // This breaks tab to select

		super.renderButton(context, mouseX, mouseY, delta);
	}

	protected void updateTooltip() {}

	protected static boolean shift() {
		return InputUtil.isKeyPressed(MinecraftClient.getInstance().getWindow().getHandle(), 340) || InputUtil.isKeyPressed(MinecraftClient.getInstance().getWindow().getHandle(), 344);
	}
}
