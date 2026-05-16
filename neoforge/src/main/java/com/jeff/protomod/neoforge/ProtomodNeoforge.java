package com.jeff.protomod.neoforge;

import com.jeff.protomod.client.ProtogenRenderer;
import net.minecraft.world.entity.EntityType;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.Mod;

import com.jeff.protomod.Protomod;
import net.neoforged.neoforge.client.event.EntityRenderersEvent;

@Mod(value=Protomod.MOD_ID, dist = Dist.CLIENT)
public final class ProtomodNeoforge {
    public ProtomodNeoforge(IEventBus modBus) {
        // Run our common setup.
        Protomod.init();
        modBus.register(this);
    }
    @SubscribeEvent
    public void registerEntityRenderers(EntityRenderersEvent.RegisterRenderers registerRenderers) {
        registerRenderers.registerEntityRenderer(EntityType.PLAYER, ProtogenRenderer::new);
    }
}
