package com.jeff.protomod.mixin;

import com.jeff.protomod.Protomod;
import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.client.renderer.ItemInHandRenderer;
import net.minecraft.core.ClientAsset;
import net.minecraft.resources.Identifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import static com.jeff.protomod.Protomod.MOD_ID;

/**Unused for now, may be used in the future though.*/
@Mixin(ItemInHandRenderer.class)
public class HandMixin {
    @Redirect(at = @At(value = "INVOKE", target = "Lnet/minecraft/core/ClientAsset$Texture;texturePath()Lnet/minecraft/resources/Identifier;"), method = "renderPlayerArm")
    private Identifier redirect(ClientAsset.Texture instance, @Local(name="player") AbstractClientPlayer player) {
        if (Protomod.isProotEnabled.get()) {
            return Identifier.fromNamespaceAndPath(MOD_ID, "textures/entity/protogen_arms.png");
        }
        return player.getSkin().body().texturePath();
    }
}
