package com.almostreliable.kubeio.kube.event;

import com.almostreliable.kubeio.enderio.conduit.CustomConduitEntry;
import com.almostreliable.kubeio.enderio.conduit.CustomEnergyConduitType;
import com.enderio.EnderIO;
import com.enderio.api.conduit.ConduitItemFactory;
import com.enderio.api.conduit.ConduitTypes;
import dev.latvian.mods.kubejs.event.EventJS;
import dev.latvian.mods.rhino.util.HideFromJS;
import net.minecraft.world.item.Item;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.HashSet;
import java.util.Set;

public class ConduitRegistryEvent extends EventJS {

    @HideFromJS
    public static final Set<CustomConduitEntry> CONDUITS = new HashSet<>();

    public void registerEnergyConduit(String id, String name, int transferRate) {
        var type = ConduitTypes.CONDUIT_TYPES.register(id, () -> new CustomEnergyConduitType(
            EnderIO.loc("block/conduit/" + id),
            transferRate
        ));

        Item item = ConduitItemFactory.build(type, new Item.Properties());
        ForgeRegistries.ITEMS.register(EnderIO.loc(id), item);

        CONDUITS.add(new CustomConduitEntry(id, name, item));
    }
}
