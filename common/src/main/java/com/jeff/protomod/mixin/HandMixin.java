package com.jeff.protomod.mixin;

import com.jeff.protomod.Protomod;
import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.client.renderer.entity.player.AvatarRenderer;
import net.minecraft.resources.Identifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;

import static com.jeff.protomod.Protomod.MOD_ID;

/**Unused for now, may be used in the future though.*/
@Mixin(AvatarRenderer.class)
public class HandMixin {
    @ModifyArg(at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/rendertype/RenderTypes;entityTranslucent(Lnet/minecraft/resources/Identifier;)Lnet/minecraft/client/renderer/rendertype/RenderType;"), method = "renderHand")
    private Identifier redirect(Identifier texture) {
        if (Protomod.isProotEnabled.get()) {
            return Identifier.fromNamespaceAndPath(MOD_ID, "textures/entity/protogen_arms.png");
        }
        return Minecraft.getInstance().player.getSkin().body().texturePath();
    }
}
