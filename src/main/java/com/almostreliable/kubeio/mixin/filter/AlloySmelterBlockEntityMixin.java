package com.almostreliable.kubeio.mixin.filter;

import com.almostreliable.kubeio.core.RecipesBinding;
import com.enderio.machines.common.recipe.AlloySmeltingRecipe;
import net.minecraft.world.item.crafting.SmeltingRecipe;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

import java.util.Optional;

@Mixin(targets = "com.enderio.machines.common.blockentity.AlloySmelterBlockEntity$AlloySmeltingMachineTaskHost")
public abstract class AlloySmelterBlockEntityMixin {

    @SuppressWarnings("OptionalUsedAsFieldOrParameterType")
    @Inject(
        method = "findRecipe",
        at = @At(value = "INVOKE_ASSIGN", target = "Lnet/minecraft/world/item/crafting/RecipeManager;getRecipeFor(Lnet/minecraft/world/item/crafting/RecipeType;Lnet/minecraft/world/Container;Lnet/minecraft/world/level/Level;)Ljava/util/Optional;"),
        locals = LocalCapture.CAPTURE_FAILHARD,
        cancellable = true,
        remap = false
    )
    private void kubeio$filterVanillaSmeltingRecipes(
        CallbackInfoReturnable<Optional<AlloySmeltingRecipe>> cir,
        Level level, int i, Optional<SmeltingRecipe> recipe
    ) {
        if (recipe.isPresent() &&
            RecipesBinding.FILTERED_SMELTING_RECIPES.contains(recipe.get().getId())) {
            cir.setReturnValue(Optional.empty());
        }
    }
}
