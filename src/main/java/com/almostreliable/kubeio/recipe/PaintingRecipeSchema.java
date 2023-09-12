package com.almostreliable.kubeio.recipe;

import com.almostreliable.kubeio.util.CommonRecipeKeys;
import com.enderio.machines.common.recipe.PaintingRecipe;
import com.enderio.machines.data.recipes.PaintingRecipeProvider;
import dev.latvian.mods.kubejs.recipe.schema.RecipeSchema;

/**
 * See {@link PaintingRecipe.Serializer} and {@link PaintingRecipeProvider}.
 */
public interface PaintingRecipeSchema extends CommonRecipeKeys {

    RecipeSchema SCHEMA = new RecipeSchema(
        ITEM_OUTPUT,
        SINGLE_INPUT
    );
}
