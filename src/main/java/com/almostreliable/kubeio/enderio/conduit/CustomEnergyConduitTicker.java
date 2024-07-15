package com.almostreliable.kubeio.enderio.conduit;

import com.enderio.api.conduit.ColoredRedstoneProvider;
import com.enderio.api.conduit.ConduitGraph;
import com.enderio.api.conduit.ConduitType;
import com.enderio.api.conduit.ticker.CapabilityAwareConduitTicker;
import com.enderio.api.misc.RedstoneControl;
import com.enderio.conduits.common.conduit.type.energy.EnergyConduitData;
import com.enderio.conduits.common.conduit.type.energy.EnergyConduitTicker;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.energy.IEnergyStorage;

import java.util.List;

public class CustomEnergyConduitTicker extends EnergyConduitTicker {

    private final int transferRate;

    public CustomEnergyConduitTicker(int transferRate) {
        this.transferRate = transferRate;
    }

    @Override
    public void tickCapabilityGraph(
        ServerLevel level, ConduitType<EnergyConduitData> type,
        List<CapabilityAwareConduitTicker<EnergyConduitData, IEnergyStorage>.CapabilityConnection> inserts,
        List<CapabilityAwareConduitTicker<EnergyConduitData, IEnergyStorage>.CapabilityConnection> extracts,
        ConduitGraph<EnergyConduitData> graph, ColoredRedstoneProvider coloredRedstoneProvider
    ) {
        for (CapabilityConnection extract : extracts) {
            IEnergyStorage extractHandler = extract.capability;
            int availableForExtraction = extractHandler.extractEnergy(transferRate, true);
            if (availableForExtraction <= 0) continue;

            EnergyConduitData.EnergySidedData sidedExtractData = extract.data.castTo(EnergyConduitData.class)
                .compute(extract.direction);

            if (inserts.size() <= sidedExtractData.rotatingIndex) {
                sidedExtractData.rotatingIndex = 0;
            }

            for (int j = sidedExtractData.rotatingIndex; j < sidedExtractData.rotatingIndex + inserts.size(); j++) {
                int insertIndex = j % inserts.size();
                CapabilityConnection insert = inserts.get(insertIndex);

                int inserted = insert.capability.receiveEnergy(availableForExtraction, false);
                extractHandler.extractEnergy(inserted, false);

                if (inserted == availableForExtraction) {
                    sidedExtractData.rotatingIndex += insertIndex + 1;
                    break;
                }

                availableForExtraction -= inserted;
            }
        }
    }

    @Override
    public boolean canConnectTo(Level level, BlockPos conduitPos, Direction direction) {
        BlockEntity blockEntity = level.getBlockEntity(conduitPos.relative(direction));
        if (blockEntity == null) return false;

        LazyOptional<IEnergyStorage> cap = blockEntity.getCapability(ForgeCapabilities.ENERGY, direction.getOpposite());
        return cap.isPresent();
    }
}
