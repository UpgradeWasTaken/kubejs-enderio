package com.almostreliable.kubeio;

import com.mojang.logging.LogUtils;
import net.minecraftforge.fml.common.Mod;
import org.slf4j.Logger;

@Mod(KubeIOConstants.MOD_ID)
public final class KubeIO {

    public static final Logger LOGGER = LogUtils.getLogger();

    public KubeIO() {
        LOGGER.info("Loading EnderIO integration for KubeJS");
    }
}
