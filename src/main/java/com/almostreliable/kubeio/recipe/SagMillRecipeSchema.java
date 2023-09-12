package com.almostreliable.kubeio.recipe;

import com.almostreliable.kubeio.util.CommonRecipeKeys;
import com.almostreliable.kubeio.util.KubeIOComponents;
import com.enderio.machines.common.recipe.SagMillingRecipe;
import com.enderio.machines.data.recipes.SagMillRecipeProvider;
import dev.latvian.mods.kubejs.recipe.RecipeKey;
import dev.latvian.mods.kubejs.recipe.component.EnumComponent;
import dev.latvian.mods.kubejs.recipe.schema.RecipeSchema;

/**
 * See {@link SagMillingRecipe.Serializer} and {@link SagMillRecipeProvider}.
 */
public interface SagMillRecipeSchema extends CommonRecipeKeys {

    RecipeKey<SagMillingRecipe.OutputItem[]> OUTPUTS = KubeIOComponents.OUTPUT_ITEM_ARRAY.key("outputs").noBuilders();
    RecipeKey<SagMillingRecipe.BonusType> BONUS_TYPE = new EnumComponent<>(SagMillingRecipe.BonusType.class).key("bonus")
        .optional(SagMillingRecipe.BonusType.MULTIPLY_OUTPUT);

    RecipeSchema SCHEMA = new RecipeSchema(
        OUTPUTS,
        SINGLE_INPUT,
        ENERGY,
        BONUS_TYPE
    );
}
