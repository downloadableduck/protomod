package com.jeff.protomod.mixin;

import com.jeff.protomod.client.screen.Buttons;
import com.llamalad7.mixinextras.sugar.Local;
import com.mojang.blaze3d.platform.Window;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.layouts.GridLayout;
import net.minecraft.client.gui.screens.PauseScreen;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;


@Mixin(PauseScreen.class)
public abstract class PauseScreenMixin extends Screen {
    protected PauseScreenMixin(Component title) {
        super(title);
    }

    @Inject(at = @At("TAIL"), method = "createPauseMenu")
    private void createPauseMenu(CallbackInfo ci) {
        int guiScale = Minecraft.getInstance().options.guiScale().get();
        Window window = Minecraft.getInstance().getWindow();
        int x = (window.getGuiScaledWidth() / 2) + 103;
        int y = (window.getGuiScaledHeight() / 2) - (guiScale + 2);
        this.addRenderableWidget(new Buttons.ProtogenButton(x, y));
    }
}
