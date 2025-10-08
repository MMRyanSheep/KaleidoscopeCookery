package com.github.ysbbbbbb.kaleidoscopecookery.compat.kubejs;


import dev.latvian.mods.kubejs.recipe.RecipeKey;
import dev.latvian.mods.kubejs.recipe.component.IngredientComponent;
import dev.latvian.mods.kubejs.recipe.component.ItemStackComponent;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.List;

public class KubeJSCompat {
    private static final boolean IS_KUBEJS_FLAG;

    static {
        boolean foundKubeJs72 = false; //找到1.7.2版本的KubeJS
        try {
            Class.forName("dev.latvian.mods.kubejs.recipe.component.RecipeComponentType");
            foundKubeJs72 = true;
        } catch (ClassNotFoundException e) {
            foundKubeJs72 = false;
        }
        IS_KUBEJS_FLAG = foundKubeJs72;
    }

    public static RecipeKey<ItemStack> getStrictItemStackOutput(String keyName) {
        if (IS_KUBEJS_FLAG) {
            return NewApiHandler.getStrictItemStackOutput(keyName);
        } else {
            return OldApiHandler.getStrictItemStackOutput(keyName);
        }
    }
//    public static RecipeKey<List<Ingredient>> getIngredientListInput(String keyName) {
//        if (IS_KUBEJS_FLAG) {
//            return NewApiHandler.getIngredientListInput(keyName);
//        } else {
//            return OldApiHandler.getIngredientListInput(keyName);
//        }
//    }

    public static class NewApiHandler {
        static RecipeKey<ItemStack> getStrictItemStackOutput(String keyName) {
            return ItemStackComponent.ITEM_STACK.outputKey(keyName);
        }
//        static RecipeKey<ItemStack> getIngredientListInput(String keyName) {
//            return ListRecipeComponent.create(IngredientComponent.INGREDIENT, false, false).inputKey(keyName);
//        }
    }

    public static class OldApiHandler {
        static RecipeKey<ItemStack> getStrictItemStackOutput(String keyName) {
            try {
                Class<?> componentClass = Class.forName("dev.latvian.mods.kubejs.recipe.component.ItemStackComponent");
                Field field = componentClass.getField("STRICT_ITEM_STACK");
                Object strictComponentObj = field.get(null);
                return (RecipeKey<ItemStack>) strictComponentObj.getClass().getMethod("outputKey", String.class).invoke(strictComponentObj, keyName);
            }
            catch (Exception e) {
                throw new RuntimeException("Faild to load KubeJS", e);
            }
        }
    }
}