package com.almostreliable.kubeio.util;

import com.almostreliable.kubeio.KubeIO;
import com.almostreliable.kubeio.KubeIOPlugin;
import com.almostreliable.kubeio.compat.JeiAdapter;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.network.NetworkEvent;
import net.minecraftforge.network.NetworkRegistry;
import net.minecraftforge.network.PacketDistributor;
import net.minecraftforge.network.simple.SimpleChannel;
import net.minecraftforge.server.ServerLifecycleHooks;

import java.util.HashSet;
import java.util.Set;
import java.util.function.Supplier;

public final class PacketHandler {

    private static final String PROTOCOL_VERSION = "1";
    private static final SimpleChannel INSTANCE = NetworkRegistry.newSimpleChannel(
        KubeIO.getRl("main"),
        () -> PROTOCOL_VERSION,
        PROTOCOL_VERSION::equals,
        PROTOCOL_VERSION::equals
    );

    private PacketHandler() {
    }

    public static void init() {
        INSTANCE.registerMessage(
            0,
            UpdateSmeltingFilterPacket.class,
            UpdateSmeltingFilterPacket::encode,
            UpdateSmeltingFilterPacket::decode,
            UpdateSmeltingFilterPacket::handle
        );
    }

    public static void syncSmeltingFilters(Player player) {
        if (player instanceof ServerPlayer serverPlayer && !KubeIOPlugin.EnderIORecipes.FILTERED_SMELTING_RECIPES.isEmpty()) {
            INSTANCE.send(
                PacketDistributor.PLAYER.with(() -> serverPlayer),
                new UpdateSmeltingFilterPacket(KubeIOPlugin.EnderIORecipes.FILTERED_SMELTING_RECIPES)
            );
        }
    }

    public static void syncSmeltingFiltersToEveryone() {
        if (ServerLifecycleHooks.getCurrentServer() != null && !KubeIOPlugin.EnderIORecipes.FILTERED_SMELTING_RECIPES.isEmpty()) {
            INSTANCE.send(
                PacketDistributor.ALL.noArg(),
                new UpdateSmeltingFilterPacket(KubeIOPlugin.EnderIORecipes.FILTERED_SMELTING_RECIPES)
            );
        }
    }

    private record UpdateSmeltingFilterPacket(Set<ResourceLocation> filteredRecipes) {

        private void encode(FriendlyByteBuf buffer) {
            buffer.writeInt(filteredRecipes.size());
            for (ResourceLocation filteredRecipe : filteredRecipes) {
                buffer.writeResourceLocation(filteredRecipe);
            }
        }

        private static UpdateSmeltingFilterPacket decode(FriendlyByteBuf buffer) {
            Set<ResourceLocation> filteredRecipes = new HashSet<>();
            int size = buffer.readInt();
            for (int i = 0; i < size; i++) {
                filteredRecipes.add(buffer.readResourceLocation());
            }
            return new UpdateSmeltingFilterPacket(filteredRecipes);
        }

        private static void handle(UpdateSmeltingFilterPacket packet, Supplier<NetworkEvent.Context> ctx) {
            ctx.get().enqueueWork(() -> DistExecutor.unsafeRunWhenOn(Dist.CLIENT, () -> () -> handlePacket(packet)));
            ctx.get().setPacketHandled(true);
        }

        private static void handlePacket(UpdateSmeltingFilterPacket packet) {
            JeiAdapter.updateRecipeFilter(packet.filteredRecipes);
        }
    }
}
