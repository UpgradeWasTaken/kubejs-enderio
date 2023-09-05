package com.almostreliable.kubeio.recipe;

import com.almostreliable.kubeio.CommonRecipeKeys;
import com.enderio.machines.common.recipe.SlicingRecipe;
import com.enderio.machines.data.recipes.SlicingRecipeProvider;
import dev.latvian.mods.kubejs.recipe.RecipeJS;
import dev.latvian.mods.kubejs.recipe.schema.RecipeSchema;

/**
 * See {@link SlicingRecipe.Serializer} and {@link SlicingRecipeProvider}.
 */
public interface SlicerRecipeSchema extends CommonRecipeKeys {

    RecipeSchema SCHEMA = new RecipeSchema(
        SlicerRecipeJS.class,
        SlicerRecipeJS::new,
        ITEM_OUTPUT,
        MULTI_INPUT,
        ENERGY
    );

    class SlicerRecipeJS extends RecipeJS {

        @Override
        public void afterLoaded() {
            super.afterLoaded();
            var inputs = getValue(MULTI_INPUT);
            if (inputs.length != 6) {
                throw new IllegalArgumentException("Slicer recipe must have 6 inputs");
            }
        }
    }
}
