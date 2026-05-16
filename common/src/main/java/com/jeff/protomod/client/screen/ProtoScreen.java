package com.jeff.protomod.client.screen;

import com.jeff.protomod.Protogen;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.inventory.InventoryScreen;
import net.minecraft.network.chat.Component;
import org.jetbrains.annotations.NotNull;

public class ProtoScreen extends Screen {

    public ProtoScreen(Component title) {
        super(title);
    }

    @Override
    public void extractRenderState(@NotNull GuiGraphicsExtractor graphics, int mouseX, int mouseY, float a) {
        super.extractRenderState(graphics, mouseX, mouseY, a);
        int xo = (this.width / 2);
        int yo = (this.height / 2);
        int size = 100;
        if (Minecraft.getInstance().player != null)
        InventoryScreen.extractEntityInInventoryFollowsMouse(graphics, xo -size, yo - size, xo + size, yo + size, 60, 0.0625F, mouseX, mouseY, this.minecraft.player);
        else graphics.text(Minecraft.getInstance().font, "I.O.U one protogen", xo, yo, 0xFFFFFFFF);
    }

    /*@Override
    public void extractBackground(@NotNull GuiGraphicsExtractor graphics, int mouseX, int mouseY, float a) {
        super.extractBackground(graphics, mouseX, mouseY, a);
        Window window = Minecraft.getInstance().getWindow();
        int width = window.getGuiScaledWidth();
        int height = window.getGuiScaledHeight();
        graphics.blitSprite(RenderPipelines.GUI_TEXTURED, Protomod.BACKROUND_LOCATION, 0, 0, width, height);
    }*/

    @Override
    public void onClose() {
        super.onClose();
        Minecraft mc = Minecraft.getInstance();
        mc.getResourcePackRepository().addPack("file/protopack");
        mc.options.resourcePacks.add("file/protopack");
        mc.options.save();
        Protogen.isFlossing = false;
    }

    @Override
    public void init() {
        super.init();

        int width = this.width / 2;
        int height = this.height / 2;

        int leftX = width - 180;

        int rightX = width + 30;

        this.addRenderableWidget(new Buttons.ToggleProtogenButton(leftX, height - 75));
        this.addRenderableWidget(new Buttons.ReportIssuesButton(leftX, height + 25));
        this.addRenderableWidget(new Buttons.OpenResourcePackButton(rightX, height - 75));
        this.addRenderableWidget(new Buttons.FlossButton(rightX, height + 25));
        this.addRenderableWidget(new Buttons.BackToGameButton(width - 75, height + 75));
    }

    @Override
    public boolean isPauseScreen() {
        return false;
    }
}
