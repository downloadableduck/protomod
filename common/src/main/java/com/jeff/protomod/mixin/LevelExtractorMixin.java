package com.jeff.protomod.mixin;

import net.minecraft.client.Camera;
import net.minecraft.client.DeltaTracker;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.player.ItemActivation;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.client.renderer.Lightmap;
import net.minecraft.client.renderer.culling.Frustum;
import net.minecraft.client.renderer.entity.state.AvatarRenderState;
import net.minecraft.client.renderer.entity.state.EntityRenderState;
import net.minecraft.client.renderer.extract.LevelExtractor;
import net.minecraft.client.renderer.state.level.FirstPersonHandsAndItemsRenderState;
import net.minecraft.client.renderer.state.level.PlayerRenderState;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.core.BlockPos;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.ARGB;
import net.minecraft.util.Mth;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.ItemOwner;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.level.block.state.BlockState;
import org.jspecify.annotations.Nullable;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(LevelExtractor.class)
public abstract class LevelExtractorMixin {
    @Shadow
    protected abstract EntityRenderState extractEntity(Entity entity, float partialTickTime);

    @Shadow
    @Final
    private Minecraft minecraft;

    @Shadow
    private @Nullable ClientLevel level;

    AvatarRenderState state = new AvatarRenderState();
    FirstPersonHandsAndItemsRenderState firstPersonHandsAndItemsRenderState = new FirstPersonHandsAndItemsRenderState();

    @Shadow
    private static @Nullable BlockState getViewBlockingState(LocalPlayer player, Frustum frustum) {
        throw new UnsupportedOperationException("Implemented via mixin");
    }

    @Inject(at = @At("HEAD"), method = "extractPlayerState", cancellable = true)
    private void onExtractPlayerState(Camera camera, DeltaTracker deltaTracker, float worldPartialTicks, PlayerRenderState state, CallbackInfo ci) {
        state.reset();
        LocalPlayer player = this.minecraft.player;
        if (player != null && this.level != null) {
            state.hasPlayer = true;
            float playerPartialTick = deltaTracker.getGameTimeDeltaPartialTick(!this.level.tickRateManager().isEntityFrozen(player));
            EntityRenderState entityRenderState = this.extractEntity(player, playerPartialTick);
            //AvatarRenderState avatarRenderState = (AvatarRenderState)entityRenderState;
                state.avatarRenderState = this.state;
                this.state.swingAnimation = player.getSwingAnimation(playerPartialTick);
                this.state.ticksUsingItem = player.getTicksUsingItem();
                this.state.pose = player.getPose();
                this.state.ticksSinceKineticHitFeedback = player.getTicksSinceLastKineticHitFeedback(playerPartialTick);
                this.state.currentSwing = player.getCurrentSwing();
                player.firstPersonHandsAndItems().extractRenderState(player, playerPartialTick, state.firstPersonHandsAndItems);
                state.portalEffectIntensity = Mth.lerp(worldPartialTicks, player.oPortalEffectIntensity, player.portalEffectIntensity);
                state.nauseaEffectIntensity = player.getEffectBlendFactor(MobEffects.NAUSEA, worldPartialTicks);
                state.spinningEffectAngle = player.getSpinningEffectAngle(worldPartialTicks);
                state.isUnderWater = player.isUnderWater();
                state.eyePositionY = player.getEyePosition(worldPartialTicks).y;
                if (player.itemActivation().isActive()) {
                    ItemActivation activation = player.itemActivation();
                    PlayerRenderState.ItemActivationRenderState activationState = new PlayerRenderState.ItemActivationRenderState(activation.item().copy(), activation.ticks(), activation.offX(), activation.offY());
                    this.minecraft.getItemModelResolver().updateForTopItem(activationState.itemState, activationState.item, ItemDisplayContext.FIXED, this.level, (ItemOwner)null, 0);
                    state.itemActivation = activationState;
                }

                Entity sprite = camera.entity();
                /*if (sprite instanceof LivingEntity) {
                    LivingEntity livingEntity = (LivingEntity)sprite;
                    if (livingEntity.isSleeping()) {
                        return;
                    }
                }*/

                BlockState viewBlockingState = getViewBlockingState(player, camera.getCullFrustum());
                if (viewBlockingState != null) {
                    TextureAtlasSprite textureAtlasSprite = this.minecraft.getModelManager().getBlockStateModelSet().getParticleMaterial(viewBlockingState).sprite();
                    state.blockOverlay = new PlayerRenderState.BlockOverlay(textureAtlasSprite.atlasLocation(), textureAtlasSprite.getU0(), textureAtlasSprite.getV0(), textureAtlasSprite.getU1(), textureAtlasSprite.getV1());
                }

                if (this.minecraft.options.getCameraType().isFirstPerson()) {
                    state.isEyeInWater = player.isEyeInFluid(FluidTags.WATER);
                    state.isOnFire = player.isOnFire();
                    if (state.isEyeInWater) {
                        BlockPos eyePos = BlockPos.containing(player.getEyePosition());
                        float brightness = Lightmap.getBrightness(player.level().dimensionType(), player.level().getMaxLocalRawBrightness(eyePos));
                        state.waterOverlay = new PlayerRenderState.WaterOverlay(ARGB.colorFromFloat(0.1F, brightness, brightness, brightness), -player.getYRot() / 64.0F, player.getXRot() / 64.0F);
                    }

                }
        }
        ci.cancel();
    }
}
