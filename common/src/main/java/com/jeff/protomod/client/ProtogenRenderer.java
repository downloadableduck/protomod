package com.jeff.protomod.client;

import com.geckolib.renderer.GeoReplacedEntityRenderer;
import com.geckolib.renderer.base.BoneSnapshots;
import com.geckolib.renderer.base.RenderPassInfo;
import com.jeff.protomod.Protogen;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.state.LivingEntityRenderState;
import net.minecraft.world.entity.player.Player;
import org.jetbrains.annotations.NotNull;
import org.jspecify.annotations.Nullable;

public class ProtogenRenderer extends GeoReplacedEntityRenderer<@NotNull Protogen, @NotNull Player, @NotNull LivingEntityRenderState> {
    public ProtogenRenderer(EntityRendererProvider.Context context) {
        super(context, new ProtogenModel(), new Protogen());
    }
    @Override
    public void extractRenderState(Player player, LivingEntityRenderState state, float f) {
        super.extractRenderState(player, state, f);
    }

    @Override
    public @Nullable LivingEntityRenderState createRenderState() {
        return new LivingEntityRenderState();
    }

    @Override
    public void adjustModelBonesForRender(@NotNull RenderPassInfo<@NotNull LivingEntityRenderState> state, BoneSnapshots snapshots) {
        super.adjustModelBonesForRender(state, snapshots);
        LivingEntityRenderState renderstate = state.renderState();
        snapshots.ifPresent("head", boneSnapshot -> {
            float x = (float) Math.toRadians(renderstate.xRot);
            float y = (float) Math.toRadians(renderstate.yRot);
            boneSnapshot.setRotX(-x);
            boneSnapshot.setRotY(-y);
        });
    }
}
