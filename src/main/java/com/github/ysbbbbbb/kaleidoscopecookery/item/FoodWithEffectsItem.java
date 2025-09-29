package com.github.ysbbbbbb.kaleidoscopecookery.item;

import com.github.ysbbbbbb.kaleidoscopecookery.init.registry.CompatRegistry;
import com.google.common.collect.Lists;
import net.minecraft.network.chat.Component;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.alchemy.PotionContents;

import java.util.List;

public class FoodWithEffectsItem extends Item {
    private final List<MobEffectInstance> effectInstances = Lists.newArrayList();

    public FoodWithEffectsItem(FoodProperties properties) {
        super(new Item.Properties().food(properties));
        properties.effects().forEach(effect -> {
            if (effect.probability() >= 1F) {
                effectInstances.add(effect.effect());
            }
        });
    }

    @Override
    public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltip, TooltipFlag flag) {
        if (!this.effectInstances.isEmpty() && CompatRegistry.SHOW_POTION_EFFECT_TOOLTIPS) {
            PotionContents.addPotionTooltip(this.effectInstances, tooltip::add, 1.0F, context.tickRate());
        }
    }
}
