package com.almostreliable.kubeio;

import com.almostreliable.kubeio.conduit.CustomConduitEntry;
import com.almostreliable.kubeio.core.ConduitRegistryEvent;
import com.almostreliable.kubeio.core.EIOIntegration;
import com.almostreliable.kubeio.core.KubePlugin;
import com.almostreliable.kubeio.util.SmeltingFilterSynchronizer;
import com.enderio.api.integration.IntegrationManager;
import com.enderio.base.common.init.EIOCreativeTabs;
import com.mojang.logging.LogUtils;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.event.BuildCreativeModeTabContentsEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegisterEvent;
import org.slf4j.Logger;

@SuppressWarnings("UtilityClassWithPublicConstructor")
@Mod(KubeIOConstants.MOD_ID)
public final class ModInitializer {

    private static final Logger LOGGER = LogUtils.getLogger();

    public ModInitializer() {
        LOGGER.info("Loading EnderIO integration for KubeJS");
        var modEventBus = FMLJavaModLoadingContext.get().getModEventBus();
        modEventBus.addListener(ModInitializer::onRegistration);
        modEventBus.addListener(ModInitializer::onTabContents);
        IntegrationManager.addIntegration(new EIOIntegration());
    }

    public static ResourceLocation getRl(String path) {
        return new ResourceLocation(KubeIOConstants.MOD_ID, path);
    }

    private static void onRegistration(RegisterEvent event) {
        if (event.getRegistryKey().equals(Registries.RECIPE_TYPE)) {
            ForgeRegistries.RECIPE_TYPES.register(SmeltingFilterSynchronizer.ID, SmeltingFilterSynchronizer.TYPE);
        }
        if (event.getRegistryKey().equals(Registries.RECIPE_SERIALIZER)) {
            ForgeRegistries.RECIPE_SERIALIZERS.register(
                SmeltingFilterSynchronizer.ID,
                SmeltingFilterSynchronizer.SERIALIZER
            );
        }
        if (event.getRegistryKey().equals(Registries.ITEM)) {
            KubePlugin.Events.CONDUIT_REGISTRY.post(new ConduitRegistryEvent());
        }
    }

    private static void onTabContents(BuildCreativeModeTabContentsEvent event) {
        if (event.getTabKey() != EIOCreativeTabs.CONDUITS) return;

        for (CustomConduitEntry conduit : ConduitRegistryEvent.CONDUITS) {
            event.accept(conduit.item());
        }
    }
}
