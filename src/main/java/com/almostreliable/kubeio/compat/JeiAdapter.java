package com.almostreliable.kubeio.compat;

import com.almostreliable.kubeio.kube.recipe.RecipesBinding;
import com.enderio.machines.common.integrations.jei.category.AlloySmeltingCategory;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.fml.ModList;

import javax.annotation.Nullable;
import java.util.Set;

/**
 * Guarding class to prevent class loading errors when JEI is not present.
 */
public final class JeiAdapter {

    private JeiAdapter() {
    }

    private static boolean isLoaded() {
        return ModList.get().isLoaded("jei");
    }

    public static void collectRecipeFilters(Set<ResourceLocation> filters) {
        if (!isLoaded()) return;
        Adapter.collectRecipeFilters(filters);
    }

    static final class Adapter {

        @Nullable private static Set<ResourceLocation> FILTERS;

        private Adapter() {}

        private static void collectRecipeFilters(Set<ResourceLocation> filters) {
            FILTERS = filters;
        }

        /**
         * Applies the collected recipe filters to the JEI runtime.
         * <p>
         * On the logical client, the {@code FILTERS} field will be empty because
         * there is no recipe syncing.<br>
         * Instead, the {@code RecipesBinding.FILTERED_SMELTING_RECIPES} field
         * is populated and can be used.
         * <p>
         * On the logical server, the {@code RecipesBinding.FILTERED_SMELTING_RECIPES}
         * should always be empty and the {@code FILTERS} field should be populated.
         */
        static void applyRecipeFilters() {
            if (RecipesBinding.FILTERED_SMELTING_RECIPES.isEmpty() && FILTERS == null) return;

            var recipesToHide = JeiPlugin.RUNTIME.getRecipeManager()
                .createRecipeLookup(AlloySmeltingCategory.TYPE)
                .get()
                .filter(r -> {
                    if (RecipesBinding.FILTERED_SMELTING_RECIPES.isEmpty()) {
                        assert FILTERS != null;
                        return FILTERS.contains(r.getId());
                    }
                    return RecipesBinding.FILTERED_SMELTING_RECIPES.contains(r.getId());
                })
                .toList();

            JeiPlugin.RUNTIME.getRecipeManager().hideRecipes(AlloySmeltingCategory.TYPE, recipesToHide);

            JeiPlugin.RUNTIME = null;
            FILTERS = null;
        }
    }
}
