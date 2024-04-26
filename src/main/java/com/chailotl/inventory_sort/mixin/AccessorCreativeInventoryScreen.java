package com.chailotl.inventory_sort.mixin;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.gui.screen.ingame.CreativeInventoryScreen;
import net.minecraft.item.ItemGroup;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Environment(EnvType.CLIENT)
@Mixin(CreativeInventoryScreen.class)
public interface AccessorCreativeInventoryScreen
{
	@Accessor
	ItemGroup getSelectedTab();
}