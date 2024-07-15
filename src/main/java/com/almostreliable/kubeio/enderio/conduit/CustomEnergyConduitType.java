package com.almostreliable.kubeio.enderio.conduit;

import com.enderio.EnderIO;
import com.enderio.api.conduit.ConduitData;
import com.enderio.api.conduit.ConduitMenuData;
import com.enderio.api.conduit.ConduitType;
import com.enderio.api.conduit.TieredConduit;
import com.enderio.api.conduit.ticker.ConduitTicker;
import com.enderio.conduits.common.conduit.type.energy.EnergyConduitData;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.Level;

public class CustomEnergyConduitType extends TieredConduit {
    private final String id;

    /**
     * @param type
     * @param id
     * @param tier     The tier of the conduit. For Energy this should be it's transfer rate to easily add and compare conduit strength
     */
    public CustomEnergyConduitType(ResourceLocation type, String id, int tier) {
        super(type, EnderIO.loc(id), tier); //"block/conduit/" +
        this.id = id;
    }

    @Override
    public ConduitTicker getTicker() {
        return new CustomEnergyConduitTicker(getTier());
    }

    @Override
    public ConduitMenuData getMenuData() {
        return ConduitMenuData.ENERGY;
    }

    @Override
    public ConduitData createConduitData(Level level, BlockPos pos) {
        return new EnergyConduitData();
    }

    @Override
    public boolean canBeInSameBlock(ConduitType other) {
        return true;
    }

    @Override
    public boolean canBeReplacedBy(ConduitType other) {
        return false;
    }
}
