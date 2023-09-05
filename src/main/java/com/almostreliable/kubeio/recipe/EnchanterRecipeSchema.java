package com.almostreliable.kubeio.recipe;

import com.almostreliable.kubeio.CommonRecipeKeys;
import com.almostreliable.kubeio.KubeIOComponents;
import com.enderio.machines.common.recipe.EnchanterRecipe;
import com.enderio.machines.data.recipes.EnchanterRecipeProvider;
import dev.latvian.mods.kubejs.recipe.RecipeKey;
import dev.latvian.mods.kubejs.recipe.component.NumberComponent;
import dev.latvian.mods.kubejs.recipe.schema.RecipeSchema;
import net.minecraft.world.item.enchantment.Enchantment;

/**
 * See {@link EnchanterRecipe.Serializer} and {@link EnchanterRecipeProvider}.
 */
public interface EnchanterRecipeSchema extends CommonRecipeKeys {

    RecipeKey<Enchantment> ENCHANTMENT = KubeIOComponents.ENCHANTMENT.key("enchantment").noBuilders();
    RecipeKey<Integer> AMOUNT = NumberComponent.INT.key("amount").optional(1).alwaysWrite();
    RecipeKey<Integer> COST_MULTIPLIER = NumberComponent.INT.key("cost_multiplier")
        .preferred("costMultiplier")
        .optional(1)
        .alwaysWrite();

    RecipeSchema SCHEMA = new RecipeSchema(
        ENCHANTMENT,
        SINGLE_INPUT,
        AMOUNT,
        COST_MULTIPLIER
    );
}
