package com.jeff.protomod.mixin;

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
    private Identifier redirect(ClientAsset.Texture instance) {
        return Identifier.fromNamespaceAndPath(MOD_ID, "textures/entity/protogen.png");
    }
}
