package com.jeff.protomod.mixin;

import com.jeff.protomod.Protomod;
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
        int y = 0;
        if (guiScale == 0) {
            y = (window.getGuiScaledHeight() / 2) - 2;
        } else if (guiScale == 1 && !window.isFullscreen()) {
            y = (window.getGuiScaledHeight()/ 2) - 63;
        } else if (guiScale == 1 && window.isFullscreen()) {
            y = (window.getGuiScaledHeight() / 2) - 241;
        } else if (guiScale == 2 && window.isFullscreen()) {
            y = (window.getGuiScaledHeight() / 2) - 92;
        } else if (guiScale == 2 && !window.isFullscreen()) {
            y = (window.getGuiScaledHeight() / 2) - 2;
        } else if (guiScale == 3 && !window.isFullscreen()) {
            y = (window.getGuiScaledHeight() / 2) - 2;
        } else if (guiScale == 3 && window.isFullscreen()) {
            y = (window.getGuiScaledHeight() / 2) - 42;
        } else if (guiScale == 4 && !window.isFullscreen()) {
            y = (window.getGuiScaledHeight() / 2) - 2;
        } else if (guiScale == 4 && window.isFullscreen()) {
            y = (window.getGuiScaledHeight() / 2) - 17;
        } else if (guiScale == 5) {
            y = (window.getGuiScaledHeight() / 2) - 2;
        }

        else {
            Protomod.LOGGER.error("Ran into an issue with checking the GUI scale! Current scale: {} Fullscreen: {}", guiScale, window.isFullscreen());
        }
        this.addRenderableWidget(new Buttons.ProtogenButton(x, y));
    }
}
