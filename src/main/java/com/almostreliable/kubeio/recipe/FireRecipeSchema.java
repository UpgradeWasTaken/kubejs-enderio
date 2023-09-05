package com.almostreliable.kubeio.recipe;

import com.almostreliable.kubeio.KubeIOComponents;
import com.enderio.base.common.recipe.FireCraftingRecipe;
import com.enderio.base.data.recipe.FireCraftingRecipes;
import com.mojang.datafixers.util.Either;
import dev.latvian.mods.kubejs.recipe.RecipeKey;
import dev.latvian.mods.kubejs.recipe.component.StringComponent;
import dev.latvian.mods.kubejs.recipe.schema.RecipeSchema;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.block.Block;
import org.apache.commons.lang3.ArrayUtils;

/**
 * See {@link FireCraftingRecipe.Serializer} and {@link FireCraftingRecipes}.
 */
public interface FireRecipeSchema {

    RecipeKey<String> LOOT_TABLE = StringComponent.ID.key("lootTable").noBuilders();
    RecipeKey<Either<Block, TagKey<Block>>[]> BASE_BLOCKS = KubeIOComponents.BLOCK_OR_TAG_ARRAY.key("base_blocks").noBuilders();
    RecipeKey<String[]> DIMENSIONS = StringComponent.ID.asArray().key("dimensions")
        .optional(ArrayUtils.toArray("minecraft:overworld"))
        .alwaysWrite();

    RecipeSchema SCHEMA = new RecipeSchema(
        BASE_BLOCKS,
        LOOT_TABLE,
        DIMENSIONS
    );
}
