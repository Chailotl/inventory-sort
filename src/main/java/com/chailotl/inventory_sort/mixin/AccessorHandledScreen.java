package com.chailotl.inventory_sort.mixin;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.gui.screen.ingame.HandledScreen;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Environment(EnvType.CLIENT)
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
