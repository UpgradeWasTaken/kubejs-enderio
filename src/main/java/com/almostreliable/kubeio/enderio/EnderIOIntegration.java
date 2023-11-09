package com.almostreliable.kubeio.enderio;

import com.almostreliable.kubeio.kube.recipe.RecipesBinding;
import com.enderio.api.integration.Integration;
import net.minecraft.world.item.crafting.SmeltingRecipe;

public class EnderIOIntegration implements Integration {

    @Override
    public boolean acceptSmeltingRecipe(SmeltingRecipe recipe) {
        return !RecipesBinding.FILTERED_SMELTING_RECIPES.contains(recipe.getId());
    }
}
