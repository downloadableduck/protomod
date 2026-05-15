package com.jeff.protomod.client.screen;

import com.jeff.protomod.Protogen;
import com.jeff.protomod.Protomod;
import com.mojang.blaze3d.platform.Window;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.Tooltip;
import net.minecraft.client.renderer.RenderPipelines;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.util.Util;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.util.function.Supplier;

import static com.jeff.protomod.Protomod.BUTTON_LOCATION;
import static com.jeff.protomod.Protomod.MOD_ID;

public class Buttons {
    public static class ProtogenButton extends Button {

        private final int x;
        private final int y;

        public ProtogenButton(int x, int y) {
            this.x = x;
            this.y = y;
            Window window = Minecraft.getInstance().getWindow();
            super(x, y, window.getWidth(), window.getWidth(), Component.literal("Protomod"), (button) -> {
                Minecraft.getInstance().setScreen(new ProtoScreen(Component.literal("Protomod")));
            }, Supplier::get);
        }

        @Override
        protected void extractContents(@NotNull GuiGraphicsExtractor graphics, int mouseX, int mouseY, float a) {
            graphics.blitSprite(RenderPipelines.GUI_TEXTURED, BUTTON_LOCATION, this.x, this.y, 16, 16);
            graphics.blitSprite(RenderPipelines.GUI_TEXTURED, Identifier.fromNamespaceAndPath(MOD_ID, "widget/colonthree"), this.x, this.y, 16, 16);
        }
    }
    public static class OpenResourcePackButton extends Button {

        private final int x;
        private final int y;

        protected OpenResourcePackButton(int x, int y) {
            Window window = Minecraft.getInstance().getWindow();
            this.x = x;
            this.y = y;
            super(x, y, 150, 20, Component.literal("Open Protogen Resources"), (a) -> {
                Util.getPlatform().openFile(new File("resourcepacks/protopack/assets/protomod/textures/entity"));
            }, Supplier::get);
            this.setTooltip(Tooltip.create(Component.literal("Open the directory so that you can paste your new protogen texture into it and use it. A short guide is inside.")));
        }

        @Override
        protected void extractContents(GuiGraphicsExtractor graphics, int mouseX, int mouseY, float a) {
            graphics.blitSprite(RenderPipelines.GUI_TEXTURED, Protomod.BUTTON_LOCATION, x, y, 150, 20);
            Font font = Minecraft.getInstance().font;
            int width = font.width("Open Protogen Resources");
            int x = this.x + (150 - width) / 2;
            int y = this.y + (20 - 8) / 2;
            graphics.text(font, "Open Protogen Resources", x, y, 0xFFFFFFFF, false);
        }
    }
    public static class ToggleProtogenButton extends Button {

        private final int x;
        private final int y;

        protected ToggleProtogenButton(int x, int y) {
            this.x = x;
            this.y = y;
            super(x, y, 150, 20, Component.literal("Open Protogen Resources"), (a) -> {
                Protomod.isProotEnabled.set(!Protomod.isProotEnabled.get());
            }, Supplier::get);
            this.setTooltip(Tooltip.create(Component.literal("Whether you appear as a protogen or not.")));
        }

        @Override
        protected void extractContents(GuiGraphicsExtractor graphics, int mouseX, int mouseY, float a) {
            graphics.blitSprite(RenderPipelines.GUI_TEXTURED, Protomod.BUTTON_LOCATION, x, y, 150, 20);
            Font font = Minecraft.getInstance().font;
            int width = font.width("Toggle Protogen");
            int x = this.x + (150 - width) / 2;
            int y = this.y + (20 - 8) / 2;
            graphics.text(font, "Toggle Protogen", x, y, 0xFFFFFFFF, false);
        }
    }
    public static class ReportIssuesButton extends Button {

        private final int x;
        private final int y;

        protected ReportIssuesButton(int x, int y) {
            this.x = x;
            this.y = y;
            super(x, y, 150, 20, Component.literal("Open Protogen Resources"), (a) -> {
                Minecraft.getInstance().player.sendSystemMessage(Component.literal("The owner was lazy and didn't fix this once the repository was created. Please report this."));
            }, Supplier::get);
            this.setTooltip(Tooltip.create(Component.literal("Report any issues with Protomod that you find.")));
        }

        @Override
        protected void extractContents(GuiGraphicsExtractor graphics, int mouseX, int mouseY, float a) {
            graphics.blitSprite(RenderPipelines.GUI_TEXTURED, Protomod.BUTTON_LOCATION, x, y, 150, 20);
            Font font = Minecraft.getInstance().font;
            int width = font.width("Report Issues");
            int x = this.x + (150 - width) / 2;
            int y = this.y + (20 - 8) / 2;
            graphics.text(font, "Report Issues", x, y, 0xFFFFFFFF, false);
        }
    }
    public static class FlossButton extends Button {
        private final int x;
        private final int y;

        protected FlossButton(int x, int y) {
            this.x = x;
            this.y = y;
            Component component = Protomod.isProotEnabled.get() ? Component.literal("Floss") : Component.literal("Floss (only for protogens, sorry)");
            super(x, y, 150, 20, component, (a) -> {
                Protogen.isFlossing = true;
            }, Supplier::get);
            if (Protomod.isProotEnabled.get()) this.setTooltip(Tooltip.create(Component.literal("Floss like a boss!")));
            else this.setTooltip(Tooltip.create(Component.literal("Floss like a boss! (only available while you are a protogen)")));
        }

        @Override
        protected void extractContents(GuiGraphicsExtractor graphics, int mouseX, int mouseY, float a) {
            graphics.blitSprite(RenderPipelines.GUI_TEXTURED, Protomod.BUTTON_LOCATION, x, y, 150, 20);
            Font font = Minecraft.getInstance().font;
            int width = font.width("Floss");
            int x = this.x + (150 - width) / 2;
            int y = this.y + (20 - 8) / 2;
            graphics.text(font, "Floss", x, y, 0xFFFFFFFF, false);
        }
    }
    public static class BackToGameButton extends Button {

        private final int x;
        private final int y;
        protected BackToGameButton(int x, int y) {
            this.x = x;
            this.y = y;
            super(x, y, 150, 20, Component.literal("Back to Game"), (a) -> {
                Minecraft.getInstance().setScreen(null);
            }, Supplier::get);
            this.setTooltip(Tooltip.create(Component.literal("Return to the game.")));
        }

        @Override
        protected void extractContents(GuiGraphicsExtractor graphics, int mouseX, int mouseY, float a) {
            graphics.blitSprite(RenderPipelines.GUI_TEXTURED, Protomod.BUTTON_LOCATION, x, y, 150, 20);
            Font font = Minecraft.getInstance().font;
            int width = font.width("Back to Game");
            int x = this.x + (150 - width) / 2;
            int y = this.y + (20 - 8) / 2;
            graphics.text(font, "Back to Game", x, y, 0xFFFFFFFF, false);
        }
    }
}
