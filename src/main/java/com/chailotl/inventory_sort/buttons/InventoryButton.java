package com.chailotl.inventory_sort.buttons;

import com.chailotl.inventory_sort.Main;
import com.chailotl.inventory_sort.mixin.AccessorHandledScreen;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.client.gui.widget.TexturedButtonWidget;
import net.minecraft.text.Style;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.Identifier;

/*? if ~1.20.2 || ~1.21*/
import net.minecraft.client.gui.screen.ButtonTextures;

@Environment(EnvType.CLIENT)
public abstract class InventoryButton extends TexturedButtonWidget
{
	protected enum Icon { SORT, LOOT, DEPOSIT }

	/*? if >=1.20.5 {*/
	private static final ButtonTextures SORT_ICON = new ButtonTextures(
			Identifier.of(Main.MOD_ID, "sort"),
			Identifier.of(Main.MOD_ID, "sort_highlighted")
	);

	private static final ButtonTextures LOOT_ICON = new ButtonTextures(
			Identifier.of(Main.MOD_ID, "loot"),
			Identifier.of(Main.MOD_ID, "loot_highlighted")
	);

	private static final ButtonTextures DEPOSIT_ICON = new ButtonTextures(
			Identifier.of(Main.MOD_ID, "deposit"),
			Identifier.of(Main.MOD_ID, "deposit_highlighted")
	);
	/*?} else if ~1.20.2 {*/
	/*private static final ButtonTextures SORT_ICON = new ButtonTextures(
		new Identifier(Main.MOD_ID, "sort"),
		new Identifier(Main.MOD_ID, "sort_highlighted")
	);

	private static final ButtonTextures LOOT_ICON = new ButtonTextures(
		new Identifier(Main.MOD_ID, "loot"),
		new Identifier(Main.MOD_ID, "loot_highlighted")
	);

	private static final ButtonTextures DEPOSIT_ICON = new ButtonTextures(
		new Identifier(Main.MOD_ID, "deposit"),
		new Identifier(Main.MOD_ID, "deposit_highlighted")
	);
	*//*?} else {*/
	/*private static final Identifier SORT_ICONS = new Identifier(Main.MOD_ID, "textures/gui/sort_icons.png");
	*//*?}*/

	private final AccessorHandledScreen parent;
	private final int offsetX;
	private final int offsetY;
	private final boolean topAnchor;

	protected InventoryButton(HandledScreen<?> parent, int x, int y, boolean topAnchor, Icon icon, PressAction onPress)
	{
		/*? if ~1.20.2 || ~1.21 {*/
		super(x, y, 9, 9, switch (icon) {
			case SORT -> SORT_ICON;
			case LOOT -> LOOT_ICON;
			case DEPOSIT -> DEPOSIT_ICON;
		}, onPress);
		/*?} else {*/
		/*super(x, y, 9, 9, switch (icon) {
			case SORT -> 0;
			case LOOT -> 9;
			case DEPOSIT -> 18;
		}, 0, 9, SORT_ICONS, 27, 18, onPress);
		*//*?}*/

		this.parent = (AccessorHandledScreen) parent;
		offsetX = x;
		offsetY = y;
		this.topAnchor = topAnchor;
	}

	protected Text getHoldToActionText(String action, Formatting color)
	{
		return Text.translatable(
				"gui.inventory_sort.tooltip.hold_to_action",
				Text.translatable("gui.inventory_sort.tooltip.shift")
					.setStyle(Style.EMPTY.withColor(color)),
				Text.translatable(action))
			.setStyle(Style.EMPTY.withColor(Formatting.DARK_GRAY));
	}

	@Override
	public int getX()
	{
		return parent.getX() + offsetX + 160;
	}

	@Override
	public int getY()
	{
		return parent.getY() + offsetY + (!topAnchor ? parent.getBackgroundHeight() - 94 : 6);
	}

	@Override
	public boolean isFocused()
	{
		return false;
	}
}
