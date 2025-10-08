package com.github.ysbbbbbb.kaleidoscopecookery.init.registry;

import com.github.ysbbbbbb.kaleidoscopecookery.KaleidoscopeCookery;
import com.github.ysbbbbbb.kaleidoscopecookery.block.dispenser.OilPotDispenseBehavior;
import com.github.ysbbbbbb.kaleidoscopecookery.block.food.FoodBiteBlock;
import com.github.ysbbbbbb.kaleidoscopecookery.compat.farmersdelight.FarmersDelightCompat;
import com.github.ysbbbbbb.kaleidoscopecookery.compat.harvest.HarvestCompat;
import com.github.ysbbbbbb.kaleidoscopecookery.init.ModItems;
import com.github.ysbbbbbb.kaleidoscopecookery.init.ModSoupBases;
import com.github.ysbbbbbb.kaleidoscopecookery.item.BowlFoodBlockItem;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.DispenserBlock;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.neoforge.registries.RegisterEvent;

@EventBusSubscriber(modid = KaleidoscopeCookery.MOD_ID)
public class CommonRegistry {
    @SubscribeEvent
    public static void onSetupEvent(FMLCommonSetupEvent event) {
        event.enqueueWork(ModSoupBases::registerAll);
        event.enqueueWork(CommonRegistry::modCompat);
        event.enqueueWork(CommonRegistry::addDispenserBehavior);
    }

    @SubscribeEvent
    public static void onBlockRegistryEvent(RegisterEvent event) {
        if (event.getRegistry().equals(BuiltInRegistries.BLOCK)) {
            FoodBiteRegistry.FOOD_DATA_MAP.forEach((resourceLocation, data) ->
                    event.register(BuiltInRegistries.BLOCK.key(), resourceLocation,
                            () -> new FoodBiteBlock(data.blockFood(), data.maxBites(), data.animateTick())));
        }

        if (event.getRegistry().equals(BuiltInRegistries.ITEM)) {
            FoodBiteRegistry.FOOD_DATA_MAP.forEach((resourceLocation, data) -> {
                Block block = BuiltInRegistries.BLOCK.get(resourceLocation);
                event.register(BuiltInRegistries.ITEM.key(), resourceLocation,
                        () -> new BowlFoodBlockItem(block, data.itemFood()));
            });
        }
    }

    private static void modCompat() {
        FarmersDelightCompat.init();
        HarvestCompat.init();
    }

    private static void addDispenserBehavior() {
        DispenserBlock.registerBehavior(ModItems.OIL_POT.get(), new OilPotDispenseBehavior());
    }
}
