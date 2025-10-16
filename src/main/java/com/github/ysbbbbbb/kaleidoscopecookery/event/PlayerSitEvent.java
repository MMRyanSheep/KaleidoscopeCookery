package com.github.ysbbbbbb.kaleidoscopecookery.event;

import com.github.ysbbbbbb.kaleidoscopecookery.entity.SitEntity;
import com.github.ysbbbbbb.kaleidoscopecookery.init.tag.TagMod;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.event.TickEvent.PlayerTickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
@Mod.EventBusSubscriber
public class PlayerSitEvent {
    @SubscribeEvent
    public static void onPlayerTick(PlayerTickEvent event) {
        if (event.player.level().isClientSide()) {
            return;
        }
        Player player = event.player;
        if (player.isPassenger()) {
            if (player.getVehicle() instanceof SitEntity seatEntity) {
                BlockPos seatPos = seatEntity.blockPosition();
                if (!seatEntity.level().getBlockState(seatEntity.blockPosition()).is(TagMod.SEATS)) {
                    player.stopRiding();
                    seatEntity.discard();
                    player.teleportTo(seatPos.getX() + 0.5, seatPos.getY(), seatPos.getZ() + 0.5);
                }
            }
        }
    }



}
