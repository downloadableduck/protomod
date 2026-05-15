package com.jeff.protomod.mixin;

import com.jeff.protomod.client.ProtogenRenderer;
import com.llamalad7.mixinextras.sugar.Local;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.entity.EntityRenderDispatcher;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.state.EntityRenderState;
import net.minecraft.client.renderer.state.level.CameraRenderState;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.phys.Vec3;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.*;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import static com.jeff.protomod.Protomod.isProotEnabled;

@Mixin(EntityRenderDispatcher.class)
public abstract class EntityRenderDispatcherMixin {

    @Unique
    private ProtogenRenderer protomod$protogenRenderer;

    @Redirect(at = @At(value = "INVOKE", ordinal = 0, target = "Lnet/minecraft/client/renderer/entity/EntityRenderer;getRenderOffset(Lnet/minecraft/client/renderer/entity/state/EntityRenderState;)Lnet/minecraft/world/phys/Vec3;"), method = "submit")
    private Vec3 getAvatarRenderer(EntityRenderer<?, ? super EntityRenderState> instance, EntityRenderState state, @Local(name="renderer") EntityRenderer<?, ? super EntityRenderState> renderer) {
        if (renderer == null) return new Vec3(0, 0, 0);
        else {
            return renderer.getRenderOffset(state);
        }
    }

    @Redirect(at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/entity/EntityRenderer;submit(Lnet/minecraft/client/renderer/entity/state/EntityRenderState;Lcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/SubmitNodeCollector;Lnet/minecraft/client/renderer/state/level/CameraRenderState;)V"), method = "submit")
    private void submit(EntityRenderer<?, ? super EntityRenderState> instance, EntityRenderState state, PoseStack poseStack, SubmitNodeCollector submitNodeCollector, CameraRenderState camera, @Local(name="renderer") EntityRenderer renderer) {
        if (renderer != null) instance.submit(state, poseStack, submitNodeCollector, camera);
    }


    @Inject(at = @At("HEAD"), method = "getRenderer(Lnet/minecraft/world/entity/Entity;)Lnet/minecraft/client/renderer/entity/EntityRenderer;", cancellable = true)
    private void getRendererT(Entity entity, CallbackInfoReturnable<ProtogenRenderer> cir) {
        if (this.protomod$protogenRenderer == null) {
            protomod$assignProot();
        }
        if (isProotEnabled.get() && entity instanceof LocalPlayer) {
            cir.setReturnValue(this.protomod$protogenRenderer);
        }
    }

    //NEVER remove the comments. This causes all players to be protogens.
    /*@Inject(at = @At("HEAD"), method = "getRenderer(Lnet/minecraft/client/renderer/entity/state/EntityRenderState;)Lnet/minecraft/client/renderer/entity/EntityRenderer;", cancellable = true)
    private void getRenderer(EntityRenderState entityRenderState, CallbackInfoReturnable<ProtogenRenderer> cir) {
        if (this.protomod$protogenRenderer == null) {
            this.protomod$assignProot();
        }
        if (entityRenderState instanceof AvatarRenderState) {
            cir.setReturnValue(this.protomod$protogenRenderer);
        }
    }*/

    @Inject(
            method = "getRenderer(Lnet/minecraft/world/entity/Entity;)Lnet/minecraft/client/renderer/entity/EntityRenderer;",
            at = @At("HEAD"),
            cancellable = true)
    public void getrenderer(Entity entity, CallbackInfoReturnable<net.minecraft.client.renderer.entity.EntityRenderer<?, ?>> cir) {
        //f (entity instanceof net.minecraft.world.entity.player.Player) {
            if (this.protomod$protogenRenderer == null) {
                protomod$assignProot();
            }
            if (isProotEnabled.get() && this.protomod$protogenRenderer != null && entity instanceof LocalPlayer) {
                cir.setReturnValue(this.protomod$protogenRenderer);
            //}
        }
    }

    @Unique
    private void protomod$assignProot() {
        EntityRenderDispatcher dispatcher = (EntityRenderDispatcher) (Object) this;

        EntityRendererProvider.Context context = new EntityRendererProvider.Context(
                dispatcher,
                dispatcher.blockModelResolver,
                dispatcher.itemModelResolver,
                dispatcher.mapRenderer,
                Minecraft.getInstance().getResourceManager(),
                dispatcher.entityModels.get(),
                dispatcher.equipmentAssets,
                dispatcher.atlasManager,
                dispatcher.font,
                dispatcher.playerSkinRenderCache
        );

        this.protomod$protogenRenderer = new ProtogenRenderer(context);
    }
}