package com.chailotl.inventorysort;

import blue.endless.jankson.Jankson;
import io.wispforest.owo.config.ConfigWrapper;
import io.wispforest.owo.config.Option;
import io.wispforest.owo.util.Observable;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;

public class InventorySortConfig extends ConfigWrapper<com.chailotl.inventorysort.ConfigModel> {

    public final Keys keys = new Keys();

    private final Option<java.util.List<java.lang.String>> sortOrder = this.optionForKey(this.keys.sortOrder);

    private InventorySortConfig() {
        super(com.chailotl.inventorysort.ConfigModel.class);
    }

    private InventorySortConfig(Consumer<Jankson.Builder> janksonBuilder) {
        super(com.chailotl.inventorysort.ConfigModel.class, janksonBuilder);
    }

    public static InventorySortConfig createAndLoad() {
        var wrapper = new InventorySortConfig();
        wrapper.load();
        return wrapper;
    }

    public static InventorySortConfig createAndLoad(Consumer<Jankson.Builder> janksonBuilder) {
        var wrapper = new InventorySortConfig(janksonBuilder);
        wrapper.load();
        return wrapper;
    }

    public java.util.List<java.lang.String> sortOrder() {
        return sortOrder.value();
    }

    public void sortOrder(java.util.List<java.lang.String> value) {
        sortOrder.set(value);
    }


    public static class Keys {
        public final Option.Key sortOrder = new Option.Key("sortOrder");
    }
}

