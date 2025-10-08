package com.github.ysbbbbbb.kaleidoscopecookery.compat.kubejs.recipe;

import com.github.ysbbbbbb.kaleidoscopecookery.compat.kubejs.KubeJSCompat;
import dev.latvian.mods.kubejs.recipe.RecipeKey;
import dev.latvian.mods.kubejs.recipe.component.IngredientComponent;
import dev.latvian.mods.kubejs.recipe.component.ItemStackComponent;
import dev.latvian.mods.kubejs.recipe.schema.RecipeSchema;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;

public interface MillstoneRecipeSchema {
    RecipeKey<ItemStack> OUTPUT = KubeJSCompat.getStrictItemStackOutput("result");
    RecipeKey<Ingredient> INGREDIENT = IngredientComponent.INGREDIENT.inputKey("ingredient");
    RecipeKey<Ingredient> CARRIER = IngredientComponent.INGREDIENT.inputKey("carrier").optional(Ingredient.EMPTY);

    RecipeSchema SCHEMA = new RecipeSchema(OUTPUT, INGREDIENT, CARRIER);
}
