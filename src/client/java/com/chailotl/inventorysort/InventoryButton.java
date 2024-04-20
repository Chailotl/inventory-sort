package com.chailotl.inventorysort;

import com.chailotl.inventorysort.mixin.client.AccessorHandledScreen;
import net.minecraft.client.gui.screen.ButtonTextures;
import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.client.gui.widget.TexturedButtonWidget;

public abstract class InventoryButton extends TexturedButtonWidget
{
	private final AccessorHandledScreen parent;
	private final int offsetX;
	private final int offsetY;
	private final boolean topAnchor;

	protected InventoryButton(HandledScreen<?> parent, int x, int y, boolean topAnchor, ButtonTextures buttonTextures, PressAction onPress)
	{
		super(x, y, 9, 9, buttonTextures, onPress);

		this.parent = (AccessorHandledScreen) parent;
		offsetX = x;
		offsetY = y;
		this.topAnchor = topAnchor;
	}

	@Override
	public int getX()
	{
		return parent.getX() + offsetX + 160;
	}

	@Override
	public int getY()
	{
		return parent.getY() + offsetY + (!topAnchor ? parent.getBackgroundHeight() - 95 : 6);
	}

	@Override
	public boolean isFocused()
	{
		return false;
	}
}
