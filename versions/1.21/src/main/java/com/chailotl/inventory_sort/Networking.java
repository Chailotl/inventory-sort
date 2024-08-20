package com.chailotl.inventory_sort;

import net.minecraft.network.PacketByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.packet.CustomPayload;

public class Networking {

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

    public record SortInventoryPayload() implements CustomPayload {
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
}
