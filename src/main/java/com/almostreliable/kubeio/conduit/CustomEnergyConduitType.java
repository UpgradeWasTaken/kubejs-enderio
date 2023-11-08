package com.almostreliable.kubeio.conduit;

import com.enderio.api.conduit.IConduitMenuData;
import com.enderio.api.conduit.IConduitType;
import com.enderio.api.conduit.TieredConduit;
import com.enderio.api.conduit.ticker.IConduitTicker;
import com.enderio.api.misc.RedstoneControl;
import com.enderio.api.misc.Vector2i;
import com.enderio.conduits.common.init.EnderConduitTypes;
import com.enderio.conduits.common.types.EnergyConduitType;
import com.enderio.conduits.common.types.EnergyExtendedData;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.energy.IEnergyStorage;

public class CustomEnergyConduitType extends TieredConduit<EnergyExtendedData> {

    public CustomEnergyConduitType(ResourceLocation texture, int transferRate) {
        super(
            texture,
            new ResourceLocation("forge:energy"),
            transferRate,
            EnderConduitTypes.ICON_TEXTURE,
            new Vector2i(0, 24)
        );
    }

    @Override
    public IConduitTicker getTicker() {
        return new CustomEnergyConduitTicker(getTier());
    }

    @Override
    public IConduitMenuData getMenuData() {
        return IConduitMenuData.ENERGY;
    }

    @Override
    public EnergyExtendedData createExtendedConduitData(Level level, BlockPos pos) {
        return new EnergyExtendedData();
    }

    @Override
    public ConduitConnectionData getDefaultConnection(Level level, BlockPos pos, Direction direction) {
        BlockEntity blockEntity = level.getBlockEntity(pos.relative(direction));
        if (blockEntity == null) return super.getDefaultConnection(level, pos, direction);

        LazyOptional<IEnergyStorage> cap = blockEntity.getCapability(ForgeCapabilities.ENERGY, direction.getOpposite());
        if (cap.isPresent()) {
            IEnergyStorage storage = cap.orElseThrow(() -> new RuntimeException("present capability was not found"));
            return new ConduitConnectionData(storage.canReceive(), storage.canExtract(), RedstoneControl.ALWAYS_ACTIVE);
        }

        return super.getDefaultConnection(level, pos, direction);
    }

    @Override
    public boolean canBeReplacedBy(IConduitType<?> other) {
        // allow replacing with simple energy conduit as it's infinite
        return other instanceof EnergyConduitType || super.canBeReplacedBy(other);
    }

    @Override
    public boolean canBeInSameBlock(IConduitType<?> other) {
        // don't allow simple energy conduit to be in the same block as custom energy conduits
        return !(other instanceof EnergyConduitType) && super.canBeInSameBlock(other);
    }
}
