package com.almostreliable.kubeio.kube.event;

import com.almostreliable.kubeio.enderio.conduit.CustomConduitEntry;
import com.almostreliable.kubeio.enderio.conduit.CustomEnergyConduitType;
import com.enderio.EnderIO;
import com.enderio.api.conduit.ConduitItemFactory;
import com.enderio.api.conduit.ConduitType;
import com.enderio.conduits.common.init.EIOConduitTypes;
import com.google.common.base.Preconditions;
import dev.latvian.mods.kubejs.event.EventJS;
import dev.latvian.mods.rhino.util.HideFromJS;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.HashSet;
import java.util.Set;

public class ConduitRegistryEvent extends EventJS {

    @HideFromJS
    public static final Set<CustomConduitEntry> CONDUITS = new HashSet<>();

    public void registerEnergyConduit(String id, String name, int transferRate) {
        Preconditions.checkArgument(!id.contains(":"), "id must not contain a colon (:)");
        Preconditions.checkArgument(!id.contains(" "), "id must not contain a space");
        Preconditions.checkArgument(
            CONDUITS.stream().noneMatch(conduit -> conduit.id().equals(id)),
            "id must be unique"
        );

        RegistryObject<CustomEnergyConduitType> type = EIOConduitTypes.CONDUIT_TYPES.register(id, () -> new CustomEnergyConduitType(
            new ResourceLocation("enderio:energy"),
            id,
            transferRate
        ));

        Item item = createItem((RegistryObject<ConduitType<?>>) (RegistryObject<?>) type, new Item.Properties(), id);

        CONDUITS.add(new CustomConduitEntry(id, name, item));
    }

    @HideFromJS
    private Item createItem(RegistryObject<? extends ConduitType<?>> type, Item.Properties properties, String id){
        Item item = ConduitItemFactory.build(type, properties);
        ForgeRegistries.ITEMS.register(EnderIO.loc(id), item);
        return item;
    }
}
