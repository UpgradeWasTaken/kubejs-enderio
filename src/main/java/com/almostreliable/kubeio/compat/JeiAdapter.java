package com.almostreliable.kubeio.compat;

import com.enderio.machines.common.integrations.jei.category.AlloySmeltingCategory;
import mezz.jei.api.runtime.IJeiRuntime;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.fml.ModList;

import java.util.Set;

public final class JeiAdapter {

    private JeiAdapter() {
    }

    private static boolean isLoaded() {
        return ModList.get().isLoaded("jei");
    }

    public static void updateRecipeFilter(Set<ResourceLocation> filters) {
        if (!isLoaded()) return;
        Adapter.collectRecipeFilters(filters);
    }

    static final class Adapter {

        private static Set<ResourceLocation> RECIPES_TO_HIDE;

        private Adapter() {
        }

        private static void collectRecipeFilters(Set<ResourceLocation> filters) {
            RECIPES_TO_HIDE = filters;
            if (JeiPlugin.RUNTIME != null) {
                applyRecipeFilters(JeiPlugin.RUNTIME);
            }
        }

        public static void applyRecipeFilters(IJeiRuntime jeiRuntime) {
            if (RECIPES_TO_HIDE == null) return;

            var recipesToHide = jeiRuntime.getRecipeManager()
                .createRecipeLookup(AlloySmeltingCategory.TYPE)
                .get()
                .filter(r -> RECIPES_TO_HIDE.contains(r.getId()))
                .toList();

            jeiRuntime.getRecipeManager().hideRecipes(AlloySmeltingCategory.TYPE, recipesToHide);

            JeiPlugin.RUNTIME = null;
            RECIPES_TO_HIDE = null;
        }
    }
}
