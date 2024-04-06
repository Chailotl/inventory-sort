package com.chailotl.inventorysort.mixin;

import net.minecraft.screen.ScreenHandler;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

@Mixin(ScreenHandler.class)
public class ModifyScreenHandler
{
	@ModifyVariable(
		method = "insertItem",
		ordinal = 0,
		at = @At("HEAD"),
		argsOnly = true
	)
	private boolean mixin(boolean original) {
		return false;
	}
}
