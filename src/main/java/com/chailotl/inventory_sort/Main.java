package com.chailotl.inventory_sort;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.util.Identifier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/*? if >=1.21 {*/
/*import net.fabricmc.fabric.api.networking.v1.PayloadTypeRegistry;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.packet.CustomPayload;
import net.minecraft.util.Uuids;
import java.util.UUID;
*//*?}*/

public class Main implements ModInitializer
{
	public static final String MOD_ID = "inventory_sort";
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	public static final boolean IS_ITEM_FAVORITES_LOADED = FabricLoader.getInstance().isModLoaded("itemfavorites");
	public static final com.chailotl.inventory_sort.InventorySortConfig CONFIG = com.chailotl.inventory_sort.InventorySortConfig.createAndLoad();

    public static final Identifier SORT_INVENTORY = id(MOD_ID, "sort_inventory");
	public static final Identifier SORT_CONTAINER = id(MOD_ID, "sort_container");
	public static final Identifier QUICK_STACK = id(MOD_ID, "quick_stack");
	public static final Identifier RESTOCK = id(MOD_ID, "restock");
	public static final Identifier DEPOSIT_ALL = id(MOD_ID, "deposit_all");
	public static final Identifier LOOT_ALL = id(MOD_ID, "loot_all");

	@Override
	public void onInitialize() {
        /*? if <1.21 {*/
        ServerPlayNetworking.registerGlobalReceiver(SORT_INVENTORY, (server, player, handler, buf, responseSender) ->
                server.execute(() -> InventoryManager.sortInventory(player, player.getInventory())));

        ServerPlayNetworking.registerGlobalReceiver(SORT_CONTAINER, (server, player, handler, buf, responseSender) ->
                server.execute(() -> InventoryManager.sortInventory(player, player.currentScreenHandler.getSlot(0).inventory)));

        ServerPlayNetworking.registerGlobalReceiver(QUICK_STACK, (server, player, handler, buf, responseSender) ->
                server.execute(() -> InventoryManager.quickStack(player.currentScreenHandler)));

        ServerPlayNetworking.registerGlobalReceiver(RESTOCK, (server, player, handler, buf, responseSender) ->
                server.execute(() -> InventoryManager.restock(player)));

        ServerPlayNetworking.registerGlobalReceiver(DEPOSIT_ALL, (server, player, handler, buf, responseSender) ->
                server.execute(() -> InventoryManager.depositAll(player.currentScreenHandler)));

        ServerPlayNetworking.registerGlobalReceiver(LOOT_ALL, (server, player, handler, buf, responseSender) ->
                server.execute(() -> InventoryManager.lootAll(player.currentScreenHandler)));
        /*?} else {*/

        /*PayloadTypeRegistry.playS2C().register(SortInventoryPayload.ID, SortInventoryPayload.CODEC);
		ServerPlayNetworking.registerGlobalReceiver(SortInventoryPayload.ID, (payload, context) ->
                context.server().execute(() -> InventoryManager.sortInventory(context.player(), context.player().getInventory())));

        PayloadTypeRegistry.playS2C().register(SortContainerPayload.ID, SortContainerPayload.CODEC);
        ServerPlayNetworking.registerGlobalReceiver(SortContainerPayload.ID, (payload, context) ->
                context.server().execute(() -> InventoryManager.sortInventory(context.player(), context.player().currentScreenHandler.getSlot(0).inventory)));

        PayloadTypeRegistry.playS2C().register(QuickStackPayload.ID, QuickStackPayload.CODEC);
        ServerPlayNetworking.registerGlobalReceiver(QuickStackPayload.ID, (payload, context) ->
                context.server().execute(() -> InventoryManager.quickStack(context.player().currentScreenHandler)));

        PayloadTypeRegistry.playS2C().register(RestockPayload.ID, RestockPayload.CODEC);
        ServerPlayNetworking.registerGlobalReceiver(RestockPayload.ID, (payload, context) ->
                context.server().execute(() -> InventoryManager.restock(context.player())));

        PayloadTypeRegistry.playS2C().register(DepositAllPayload.ID, DepositAllPayload.CODEC);
        ServerPlayNetworking.registerGlobalReceiver(DepositAllPayload.ID, (payload, context) ->
                context.server().execute(() -> InventoryManager.depositAll(context.player().currentScreenHandler)));

        PayloadTypeRegistry.playS2C().register(LootAllPayload.ID, LootAllPayload.CODEC);
        ServerPlayNetworking.registerGlobalReceiver(LootAllPayload.ID, (payload, context) ->
                context.server().execute(() -> InventoryManager.lootAll(context.player().currentScreenHandler)));
		*//*?}*/
	}

    public static Identifier id(String namespace, String path) {
        /*? if <1.21 {*/
        return new Identifier(namespace, path);
        /*?} else {*/
        /*return Identifier.of(namespace, path);
        *//*?}*/
    }

    /*? if >=1.21 {*/

    /*public record SortInventoryPayload() implements CustomPayload {
        public static final CustomPayload.Id<SortInventoryPayload> ID = new CustomPayload.Id<>(Main.SORT_INVENTORY);
        public static PacketCodec<PacketByteBuf, SortInventoryPayload> CODEC =
            PacketCodec.ofStatic(SortInventoryPayload::encode, SortInventoryPayload::decode);

        @Override
        public Id<? extends CustomPayload> getId() {
            return ID;
        }

        private static void encode(PacketByteBuf buf, SortInventoryPayload self) {}
        private static SortInventoryPayload decode(PacketByteBuf buf) {return new SortInventoryPayload();}
    }

    public record SortContainerPayload() implements CustomPayload {
        public static final CustomPayload.Id<SortContainerPayload> ID = new CustomPayload.Id<>(Main.SORT_CONTAINER);
        public static PacketCodec<PacketByteBuf, SortContainerPayload> CODEC =
            PacketCodec.ofStatic(SortContainerPayload::encode, SortContainerPayload::decode);

        @Override
        public Id<? extends CustomPayload> getId() {
            return ID;
        }

        private static void encode(PacketByteBuf buf, SortContainerPayload self) {}
        private static SortContainerPayload decode(PacketByteBuf buf) {return new SortContainerPayload();}
    }

    public record QuickStackPayload() implements CustomPayload {
        public static final Id<QuickStackPayload> ID = new Id<>(Main.QUICK_STACK);
        public static PacketCodec<PacketByteBuf, QuickStackPayload> CODEC =
            PacketCodec.ofStatic(QuickStackPayload::encode, QuickStackPayload::decode);

        @Override
        public Id<? extends CustomPayload> getId() {
            return ID;
        }

        private static void encode(PacketByteBuf buf, QuickStackPayload self) {}
        private static QuickStackPayload decode(PacketByteBuf buf) {return new QuickStackPayload();}
    }

    public record RestockPayload() implements CustomPayload {
        public static final CustomPayload.Id<RestockPayload> ID = new CustomPayload.Id<>(Main.RESTOCK);
        public static PacketCodec<PacketByteBuf, RestockPayload> CODEC =
            PacketCodec.ofStatic(RestockPayload::encode, RestockPayload::decode);

        @Override
        public Id<? extends CustomPayload> getId() {
            return ID;
        }

        private static void encode(PacketByteBuf buf, RestockPayload self) {}
        private static RestockPayload decode(PacketByteBuf buf) {return new RestockPayload();}
    }

    public record DepositAllPayload() implements CustomPayload {
        public static Id<DepositAllPayload> ID = new Id<>(Main.DEPOSIT_ALL);
        public static PacketCodec<PacketByteBuf, DepositAllPayload> CODEC =
            PacketCodec.ofStatic(DepositAllPayload::encode, DepositAllPayload::decode);

        @Override
        public Id<? extends CustomPayload> getId() {
            return ID;
        }

        private static void encode(PacketByteBuf buf, DepositAllPayload self) {}
        private static DepositAllPayload decode(PacketByteBuf buf) {return new DepositAllPayload();}
    }

    public record LootAllPayload() implements CustomPayload {
        public static final CustomPayload.Id<LootAllPayload> ID = new CustomPayload.Id<>(Main.LOOT_ALL);
        public static PacketCodec<PacketByteBuf, LootAllPayload> CODEC =
            PacketCodec.ofStatic(LootAllPayload::encode, LootAllPayload::decode);

        @Override
        public Id<? extends CustomPayload> getId() {
            return ID;
        }

        private static void encode(PacketByteBuf buf, LootAllPayload self) {}
        private static LootAllPayload decode(PacketByteBuf buf) {return new LootAllPayload();}
    }

    *//*?}*/
}