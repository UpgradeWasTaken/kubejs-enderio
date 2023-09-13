package com.almostreliable.kubeio.util;

import com.almostreliable.kubeio.ModInitializer;
import com.almostreliable.kubeio.compat.JeiAdapter;
import com.almostreliable.kubeio.core.RecipesBinding;
import com.google.gson.JsonObject;
import net.minecraft.core.RegistryAccess;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.Container;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.Level;

import javax.annotation.Nullable;
import java.util.HashSet;
import java.util.Set;

/**
 * A dummy recipe used to sync the filtered vanilla smelting recipes
 * for the Alloy Smelter at the correct time because it didn't work with
 * packets. Timing issues with JEI and all of that.<br>
 * Hahahahaha... please help me.
 */
public final class SmeltingFilterSynchronizer implements Recipe<Container> {

    public static final ResourceLocation ID = ModInitializer.getRl("smelting_filter_synchronizer");
    public static final RecipeType<SmeltingFilterSynchronizer> TYPE = new RecipeType<>() {
        @Override
        public String toString() {
            return ID.toString();
        }
    };
    public static final RecipeSerializer<SmeltingFilterSynchronizer> SERIALIZER = new Serializer();

    private final ResourceLocation id;

    private SmeltingFilterSynchronizer(ResourceLocation id) {
        this.id = id;
    }

    @Override
    public boolean matches(Container container, Level level) {
        return false;
    }

    @Override
    public ItemStack assemble(Container container, RegistryAccess registryAccess) {
        return ItemStack.EMPTY;
    }

    @Override
    public boolean canCraftInDimensions(int width, int height) {
        return false;
    }

    @Override
    public ItemStack getResultItem(RegistryAccess registryAccess) {
        return ItemStack.EMPTY;
    }

    @Override
    public ResourceLocation getId() {
        return id;
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return SERIALIZER;
    }

    @Override
    public RecipeType<?> getType() {
        return TYPE;
    }

    public static class Serializer implements RecipeSerializer<SmeltingFilterSynchronizer> {

        @Override
        public SmeltingFilterSynchronizer fromJson(ResourceLocation id, JsonObject json) {
            return new SmeltingFilterSynchronizer(id);
        }

        @Override
        public void toNetwork(FriendlyByteBuf buffer, SmeltingFilterSynchronizer recipe) {
            var filters = RecipesBinding.FILTERED_SMELTING_RECIPES;
            buffer.writeInt(filters.size());
            for (ResourceLocation filter : filters) {
                buffer.writeResourceLocation(filter);
            }
        }

        @Nullable
        @Override
        public SmeltingFilterSynchronizer fromNetwork(ResourceLocation id, FriendlyByteBuf buffer) {
            int size = buffer.readInt();
            if (size > 0) {
                Set<ResourceLocation> filters = new HashSet<>();
                for (int i = 0; i < size; i++) {
                    filters.add(buffer.readResourceLocation());
                }
                JeiAdapter.collectRecipeFilters(filters);
            }

            return new SmeltingFilterSynchronizer(id);
        }
    }
}
