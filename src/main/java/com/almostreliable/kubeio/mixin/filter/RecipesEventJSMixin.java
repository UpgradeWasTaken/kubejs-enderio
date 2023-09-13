package com.almostreliable.kubeio.mixin.filter;

import com.almostreliable.kubeio.KubeIOPlugin;
import com.google.gson.JsonElement;
import dev.latvian.mods.kubejs.recipe.RecipeJS;
import dev.latvian.mods.kubejs.recipe.RecipesEventJS;
import dev.latvian.mods.kubejs.recipe.filter.RecipeFilter;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.RecipeManager;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Collection;
import java.util.Map;

@Mixin(RecipesEventJS.class)
public abstract class RecipesEventJSMixin {

    @Shadow(remap = false)
    @Final
    public Map<ResourceLocation, RecipeJS> originalRecipes;

    @Shadow(remap = false)
    public abstract Collection<RecipeJS> findRecipes(RecipeFilter filter);

    @Inject(method = "post", at = @At("TAIL"), remap = false)
    private void kubeio$resolveRecipes(
        RecipeManager recipeManager, Map<ResourceLocation, JsonElement> recipes, CallbackInfo ci
    ) {
        KubeIOPlugin.EnderIORecipes.resolveRecipes(originalRecipes, this::findRecipes);
    }
}
