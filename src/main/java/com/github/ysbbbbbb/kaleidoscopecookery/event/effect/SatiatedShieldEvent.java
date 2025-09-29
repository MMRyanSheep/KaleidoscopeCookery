package com.github.ysbbbbbb.kaleidoscopecookery.event.effect;

import com.github.ysbbbbbb.kaleidoscopecookery.KaleidoscopeCookery;
import com.github.ysbbbbbb.kaleidoscopecookery.init.ModEffects;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.world.entity.player.Player;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.living.LivingDamageEvent;

@EventBusSubscriber(modid = KaleidoscopeCookery.MOD_ID)
public class SatiatedShieldEvent {
    @SubscribeEvent
    public static void onPlayerHurt(LivingDamageEvent.Pre event) {
        int amount = Math.round(event.getContainer().getNewDamage());
        if (event.getEntity() instanceof Player player && !event.getSource().is(DamageTypeTags.BYPASSES_INVULNERABILITY)) {
            if (player.getFoodData().getFoodLevel() > 0 && player.hasEffect(ModEffects.SATIATED_SHIELD)) {
                int level = player.getFoodData().getFoodLevel() - amount;
                // 原版是 4 点 Exhaustion 对应 1 点 Food Level
                player.causeFoodExhaustion(Math.max(0, level / 4f));
                event.getContainer().setNewDamage(0);
            }
        }
    }
}
