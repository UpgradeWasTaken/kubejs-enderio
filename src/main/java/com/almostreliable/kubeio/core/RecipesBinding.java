package com.almostreliable.kubeio.core;

import dev.latvian.mods.kubejs.recipe.RecipeJS;
import dev.latvian.mods.kubejs.recipe.filter.IDFilter;
import dev.latvian.mods.kubejs.recipe.filter.RecipeFilter;
import dev.latvian.mods.rhino.util.HideFromJS;
import net.minecraft.resources.ResourceLocation;

import java.util.*;
import java.util.function.Function;

public final class RecipesBinding {

    @HideFromJS public static final Set<ResourceLocation> FILTERED_SMELTING_RECIPES = new HashSet<>();
    private static final List<RecipeFilter> SMELTING_RECIPE_FILTERS = new ArrayList<>();

    private RecipesBinding() {
    }

    @HideFromJS
    public static void resolveRecipes(
        Map<ResourceLocation, RecipeJS> recipes, Function<RecipeFilter, Collection<RecipeJS>> recipeLookup
    ) {
        FILTERED_SMELTING_RECIPES.clear();

        for (RecipeFilter filter : SMELTING_RECIPE_FILTERS) {
            if (filter instanceof IDFilter id) {
                RecipeJS r = recipes.get(id.id);
                if (r != null) addFilteredSmeltingRecipe(r);
                return;
            }

            recipeLookup.apply(filter).forEach(RecipesBinding::addFilteredSmeltingRecipe);
        }

        SMELTING_RECIPE_FILTERS.clear();
    }

    private static void addFilteredSmeltingRecipe(RecipeJS recipe) {
        if (recipe.type.id.toString().equals("minecraft:smelting")) {
            FILTERED_SMELTING_RECIPES.add(recipe.id);
        }
    }

    @SuppressWarnings("unused")
    public static void removeVanillaSmeltingRecipe(RecipeFilter filter) {
        SMELTING_RECIPE_FILTERS.add(filter);
    }
}
