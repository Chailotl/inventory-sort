package com.chailotl.inventorysort.buttons;

import com.chailotl.inventorysort.InventorySortClient;
import com.chailotl.inventorysort.mixin.client.AccessorHandledScreen;
/*? if ~1.20.2 */
/*import net.minecraft.client.gui.screen.ButtonTextures;*/
import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.client.gui.widget.TexturedButtonWidget;
import net.minecraft.util.Identifier;

public abstract class InventoryButton extends TexturedButtonWidget
{
	protected enum Icon { SORT, LOOT, DEPOSIT }

	/*? if ~1.20.2 {*//*
	private static final ButtonTextures SORT_ICON = new ButtonTextures(
		new Identifier(InventorySortClient.MOD_ID, "sort"),
		new Identifier(InventorySortClient.MOD_ID, "sort_highlighted")
	);

	private static final ButtonTextures LOOT_ICON = new ButtonTextures(
		new Identifier(InventorySortClient.MOD_ID, "loot"),
		new Identifier(InventorySortClient.MOD_ID, "loot_highlighted")
	);

	private static final ButtonTextures DEPOSIT_ICON = new ButtonTextures(
		new Identifier(InventorySortClient.MOD_ID, "deposit"),
		new Identifier(InventorySortClient.MOD_ID, "deposit_highlighted")
	);
	*//*?} else {*/
	private static final Identifier SORT_ICONS = new Identifier(InventorySortClient.MOD_ID, "textures/gui/sort_icons.png");
	/*?} */

	private final AccessorHandledScreen parent;
	private final int offsetX;
	private final int offsetY;
	private final boolean topAnchor;

	protected InventoryButton(HandledScreen<?> parent, int x, int y, boolean topAnchor, Icon icon, PressAction onPress)
	{
		/*? if ~1.20.2 {*//*
		super(x, y, 9, 9, switch (icon) {
			case SORT -> SORT_ICON;
			case LOOT -> LOOT_ICON;
			case DEPOSIT -> DEPOSIT_ICON;
		}, onPress);
		*//*?} else {*/
		super(x, y, 9, 9, switch (icon) {
			case SORT -> 0;
			case LOOT -> 9;
			case DEPOSIT -> 18;
		}, 0, 9, SORT_ICONS, 27, 18, onPress);
		/*?} */

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
