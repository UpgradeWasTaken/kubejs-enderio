package com.almostreliable.kubeio.recipe;

import com.almostreliable.kubeio.KubeIOComponents;
import com.enderio.base.common.recipe.GrindingBallRecipe;
import com.enderio.base.data.recipe.GrindingBallRecipeProvider;
import dev.latvian.mods.kubejs.recipe.RecipeKey;
import dev.latvian.mods.kubejs.recipe.component.NumberComponent;
import dev.latvian.mods.kubejs.recipe.schema.RecipeSchema;
import net.minecraft.world.item.Item;

/**
 * See {@link GrindingBallRecipe.Serializer} and {@link GrindingBallRecipeProvider}.
 */
public interface GrindingBallRecipeSchema {

    RecipeKey<Item> ITEM = KubeIOComponents.ITEM.key("item").noBuilders();
    RecipeKey<Float> GRINDING = NumberComponent.FLOAT.key("grinding").preferred("doublingChance").optional(1f).alwaysWrite();
    RecipeKey<Float> CHANCE = NumberComponent.FLOAT.key("chance").preferred("bonusMultiplier").optional(1f).alwaysWrite();
    RecipeKey<Float> POWER = NumberComponent.FLOAT.key("power").preferred("powerUse").optional(1f).alwaysWrite();
    RecipeKey<Integer> DURABILITY = NumberComponent.INT.key("durability").optional(10).alwaysWrite();

    RecipeSchema SCHEMA = new RecipeSchema(
        ITEM,
        GRINDING,
        CHANCE,
        POWER,
        DURABILITY
    );
}
