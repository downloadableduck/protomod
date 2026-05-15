package com.jeff.protomod.fabric.client;

import com.geckolib.cache.GeckoLibResources;
import com.jeff.protomod.client.ProtogenRenderer;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.LivingEntityFeatureRenderEvents;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.world.entity.EntityType;

public final class ExampleModFabricClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {

        EntityRendererRegistry.register(EntityType.PLAYER, ProtogenRenderer::new);

        // This entrypoint is suitable for setting up client-specific logic, such as rendering.
    }
}
