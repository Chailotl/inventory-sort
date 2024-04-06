package com.chailotl.inventorysort.mixin.client;

import net.minecraft.client.gui.screen.ingame.HandledScreen;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(HandledScreen.class)
public interface AccessorHandledScreen
{
	@Accessor
	int getX();

	@Accessor
	int getY();

	@Accessor
	int getBackgroundWidth();

	@Accessor
	int getBackgroundHeight();
}
