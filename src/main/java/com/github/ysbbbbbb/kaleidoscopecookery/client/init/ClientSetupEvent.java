package com.github.ysbbbbbb.kaleidoscopecookery.client.init;

import com.github.ysbbbbbb.kaleidoscopecookery.KaleidoscopeCookery;
import com.github.ysbbbbbb.kaleidoscopecookery.client.gui.overlay.PotOverlay;
import com.github.ysbbbbbb.kaleidoscopecookery.client.render.block.*;
import com.github.ysbbbbbb.kaleidoscopecookery.client.resources.ItemRenderReplacerReloadListener;
import com.github.ysbbbbbb.kaleidoscopecookery.init.ModBlocks;
import com.github.ysbbbbbb.kaleidoscopecookery.init.ModItems;
import com.github.ysbbbbbb.kaleidoscopecookery.item.*;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderers;
import net.minecraft.client.renderer.item.ItemProperties;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.neoforge.client.event.EntityRenderersEvent;
import net.neoforged.neoforge.client.event.RegisterClientReloadListenersEvent;
import net.neoforged.neoforge.client.event.RegisterGuiLayersEvent;

import static net.neoforged.neoforge.client.gui.VanillaGuiLayers.CROSSHAIR;


@EventBusSubscriber(modid = KaleidoscopeCookery.MOD_ID, value = Dist.CLIENT)
public class ClientSetupEvent {
    @SubscribeEvent
    @SuppressWarnings("all")
    public static void onClientSetup(FMLClientSetupEvent event) {
        event.enqueueWork(() -> ItemProperties.register(ModItems.KITCHEN_SHOVEL.get(), KitchenShovelItem.HAS_OIL_PROPERTY, KitchenShovelItem::getTexture));
        event.enqueueWork(() -> ItemProperties.register(ModItems.STOCKPOT_LID.get(), StockpotLidItem.USING_PROPERTY, StockpotLidItem::getTexture));
        event.enqueueWork(() -> ItemProperties.register(ModItems.OIL_POT.get(), OilPotItem.HAS_OIL_PROPERTY, OilPotItem::getTexture));
        event.enqueueWork(() -> ItemProperties.register(ModItems.RAW_DOUGH.get(), RawDoughItem.PULL_PROPERTY, RawDoughItem::getTexture));
        event.enqueueWork(() -> ItemProperties.register(ModItems.RECIPE_ITEM.get(), RecipeItem.HAS_RECIPE_PROPERTY, RecipeItem::getTexture));
        event.enqueueWork(() -> ItemProperties.register(ModItems.TRANSMUTATION_LUNCH_BAG.get(), TransmutationLunchBagItem.HAS_ITEMS_PROPERTY, TransmutationLunchBagItem::getTexture));
        event.enqueueWork(() -> ItemProperties.register(ModItems.STEAMER.get(), SteamerItem.HAS_ITEMS, SteamerItem::getTexture));
    }

    @SubscribeEvent
    public static void onEntityRenderers(EntityRenderersEvent.RegisterRenderers evt) {
        BlockEntityRenderers.register(ModBlocks.POT_BE.get(), PotBlockEntityRender::new);
        BlockEntityRenderers.register(ModBlocks.FRUIT_BASKET_BE.get(), FruitBasketBlockEntityRender::new);
        BlockEntityRenderers.register(ModBlocks.CHOPPING_BOARD_BE.get(), ChoppingBoardBlockEntityRender::new);
        BlockEntityRenderers.register(ModBlocks.STOCKPOT_BE.get(), StockpotBlockEntityRender::new);
        BlockEntityRenderers.register(ModBlocks.KITCHENWARE_RACKS_BE.get(), KitchenwareRacksBlockEntityRender::new);
        BlockEntityRenderers.register(ModBlocks.CHAIR_BE.get(), ChairBlockEntityRender::new);
        BlockEntityRenderers.register(ModBlocks.TABLE_BE.get(), TableBlockEntityRender::new);
        BlockEntityRenderers.register(ModBlocks.SHAWARMA_SPIT_BE.get(), ShawarmaSpitBlockEntityRender::new);
        BlockEntityRenderers.register(ModBlocks.MILLSTONE_BE.get(), MillstoneBlockEntityRender::new);
        BlockEntityRenderers.register(ModBlocks.RECIPE_BLOCK_BE.get(), RecipeBlockEntityRender::new);
        BlockEntityRenderers.register(ModBlocks.STEAMER_BE.get(), SteamerBlockEntityRender::new);
    }

    @SubscribeEvent
    public static void onRegisterGuiOverlays(RegisterGuiLayersEvent event) {
        event.registerAbove(CROSSHAIR, ResourceLocation.fromNamespaceAndPath(KaleidoscopeCookery.MOD_ID, "pot_overlay"), new PotOverlay());
    }

    @SubscribeEvent
    public static void onRegisterClientReloadListeners(RegisterClientReloadListenersEvent event) {
        event.registerReloadListener(new ItemRenderReplacerReloadListener());
    }
}
