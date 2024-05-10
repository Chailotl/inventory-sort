package com.chailotl.inventory_sort;

import net.fabricmc.api.DedicatedServerModInitializer;
import net.fabricmc.loader.api.FabricLoader;

public class ServerMain implements DedicatedServerModInitializer {

    public static final boolean IS_ITEM_FAVORITES_LOADED = FabricLoader.getInstance().isModLoaded("itemfavorites");

    @Override
    public void onInitializeServer() {}
}
