package com.almostreliable.kubeio;

import com.almostreliable.kubeio.util.PacketHandler;
import com.mojang.logging.LogUtils;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.AddReloadListenerEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.slf4j.Logger;

import java.util.concurrent.CompletableFuture;

@SuppressWarnings("UtilityClassWithPublicConstructor")
@Mod(KubeIOConstants.MOD_ID)
public final class KubeIO {

    private static final Logger LOGGER = LogUtils.getLogger();

    public KubeIO() {
        LOGGER.info("Loading EnderIO integration for KubeJS");
        FMLJavaModLoadingContext.get().getModEventBus().addListener(KubeIO::onCommonSetup);
        MinecraftForge.EVENT_BUS.addListener(KubeIO::onPlayerJoin);
        MinecraftForge.EVENT_BUS.addListener(KubeIO::postReload);
    }

    public static ResourceLocation getRl(String path) {
        return new ResourceLocation(KubeIOConstants.MOD_ID, path);
    }

    private static void onCommonSetup(FMLCommonSetupEvent event) {
        PacketHandler.init();
    }

    private static void onPlayerJoin(PlayerEvent.PlayerLoggedInEvent event) {
        PacketHandler.syncSmeltingFilters(event.getEntity());
    }

    private static void postReload(AddReloadListenerEvent event) {
        event.addListener(
            (preparationBarrier, resourceManager, preparationsProfiler, reloadProfiler, backgroundExecutor, gameExecutor) ->
                CompletableFuture.supplyAsync(Object::new, backgroundExecutor)
                    .thenCompose(preparationBarrier::wait)
                    .thenAccept(object -> PacketHandler.syncSmeltingFiltersToEveryone())
        );
    }
}
