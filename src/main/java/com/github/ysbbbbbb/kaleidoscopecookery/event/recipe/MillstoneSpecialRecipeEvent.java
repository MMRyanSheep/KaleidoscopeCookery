package com.github.ysbbbbbb.kaleidoscopecookery.event.recipe;

import com.github.ysbbbbbb.kaleidoscopecookery.api.event.MillstoneTakeItemEvent;
import com.github.ysbbbbbb.kaleidoscopecookery.blockentity.kitchen.MillstoneBlockEntity;
import com.github.ysbbbbbb.kaleidoscopecookery.init.ModItems;
import com.github.ysbbbbbb.kaleidoscopecookery.init.tag.TagMod;
import com.github.ysbbbbbb.kaleidoscopecookery.item.OilPotItem;
import com.github.ysbbbbbb.kaleidoscopecookery.util.ItemUtils;
import net.minecraft.core.component.DataComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.alchemy.PotionContents;
import net.minecraft.world.item.alchemy.Potions;
import net.minecraft.world.item.crafting.Ingredient;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.common.crafting.CompoundIngredient;
import net.neoforged.neoforge.common.crafting.DataComponentIngredient;

import java.util.List;

@EventBusSubscriber
public class MillstoneSpecialRecipeEvent {
    @SubscribeEvent
    public static void onMillstoneTakeItem(MillstoneTakeItemEvent event) {
        MillstoneBlockEntity millstone = event.getMillstone();
        ItemStack output = millstone.getOutput();

        // 油壶特殊处理
        if (output.is(ModItems.OIL_POT.get())) {
            onGetOilPot(event, millstone, output);
            return;
        }

        // 面团特殊处理
        if (output.is(ModItems.RAW_DOUGH.get())) {
            onGetRawDough(event, millstone, output);
        }
    }

    private static void onGetRawDough(MillstoneTakeItemEvent event, MillstoneBlockEntity millstone, ItemStack output) {
        // 一个水桶就能取出所有
        LivingEntity user = event.getUser();
        ItemStack heldItem = event.getHeldItem();

        // 检查是否是合适的容器
        Ingredient waterPotionIngredient = DataComponentIngredient.of(false,
                DataComponents.POTION_CONTENTS,
                new PotionContents(Potions.WATER), Items.POTION);
        Ingredient tagIngredient = Ingredient.of(TagMod.MILLSTONE_DOUGH_CONTAINER);
        CompoundIngredient anyIngredients = new CompoundIngredient(List.of(waterPotionIngredient, tagIngredient));
        Ingredient ingredient = new Ingredient(anyIngredients);
        if (heldItem.isEmpty() || !ingredient.test(heldItem)) {
            Component carrierName = Items.WATER_BUCKET.getDefaultInstance().getHoverName();
            millstone.sendActionBarMessage(user, "tip.kaleidoscope_cookery.pot.need_carrier", carrierName);
            event.setResult(false);
            event.setCanceled(true);
            return;
        }

        // 直接全部产出
        ItemUtils.getItemToLivingEntity(user, output.copy());

        // 消耗一个容器，并返还
        Item containerItem = ItemUtils.getContainerItem(heldItem.split(1));
        if (containerItem != Items.AIR) {
            ItemUtils.getItemToLivingEntity(user, containerItem.getDefaultInstance());
        }

        millstone.resetWhenTakeout();
        event.setResult(true);
        event.setCanceled(true);
    }

    private static void onGetOilPot(MillstoneTakeItemEvent event, MillstoneBlockEntity millstone, ItemStack output) {
        // 油壶是 8 个取出 1 滴油
        LivingEntity user = event.getUser();
        ItemStack heldItem = event.getHeldItem();

        // 兼容容器是否正确
        if (heldItem.isEmpty() || !heldItem.is(ModItems.OIL_POT.get())) {
            Component carrierName = ModItems.OIL_POT.get().getDefaultInstance().getHoverName();
            millstone.sendActionBarMessage(user, "tip.kaleidoscope_cookery.pot.need_carrier", carrierName);
            event.setResult(false);
            event.setCanceled(true);
            return;
        }

        // 油壶满了
        int oilCount = OilPotItem.getOilCount(heldItem);
        if (oilCount >= OilPotItem.MAX_COUNT) {
            millstone.sendActionBarMessage(user, "tip.kaleidoscope_cookery.millstone.oil_pot_is_full");
            event.setResult(false);
            event.setCanceled(true);
            return;
        }

        // 不足 8 个时，概率产出
        RandomSource random = user.level().getRandom();
        if (random.nextInt(8) < output.getCount()) {
            ItemStack takeItem = heldItem.split(1);
            OilPotItem.setOilCount(takeItem, oilCount + 1);
            ItemUtils.getItemToLivingEntity(user, takeItem);
        }

        millstone.resetWhenTakeout();
        event.setResult(true);
        event.setCanceled(true);
    }
}
