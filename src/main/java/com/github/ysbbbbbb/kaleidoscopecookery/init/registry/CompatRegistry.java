package com.github.ysbbbbbb.kaleidoscopecookery.init.registry;


import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.ModList;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.event.lifecycle.InterModEnqueueEvent;


@EventBusSubscriber
public class CompatRegistry {
    public static boolean SHOW_POTION_EFFECT_TOOLTIPS = true;

    @SubscribeEvent
    public static void onEnqueue(final InterModEnqueueEvent event) {
        event.enqueueWork(() -> {
            // 当安装 Food Effect Tooltips (Forge) 模组时，关闭药水效果提示
            SHOW_POTION_EFFECT_TOOLTIPS = !ModList.get().isLoaded("foodeffecttooltips");
        });
    }

    private static void checkModLoad(String modId, Runnable runnable) {
        if (ModList.get().isLoaded(modId)) {
            runnable.run();
        }
    }
}
