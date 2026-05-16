package com.jeff.protomod.client;

import com.geckolib.renderer.GeoReplacedEntityRenderer;
import com.geckolib.renderer.base.BoneSnapshots;
import com.geckolib.renderer.base.RenderPassInfo;
import com.jeff.protomod.Protogen;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.model.player.PlayerModel;
import net.minecraft.client.renderer.entity.ArmorModelSet;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.EquipmentLayerRenderer;
import net.minecraft.client.renderer.entity.layers.HumanoidArmorLayer;
import net.minecraft.client.renderer.entity.state.AvatarRenderState;
import net.minecraft.client.renderer.entity.state.HumanoidRenderState;
import net.minecraft.client.renderer.entity.state.LivingEntityRenderState;
import net.minecraft.client.resources.model.EquipmentClientInfo;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.equipment.EquipmentAsset;
import org.jetbrains.annotations.NotNull;
import org.jspecify.annotations.Nullable;

public class ProtogenRenderer extends GeoReplacedEntityRenderer<@NotNull Protogen, @NotNull Player, @NotNull LivingEntityRenderState> {

    AvatarRenderState hstate = new AvatarRenderState();
    private final EntityRendererProvider.Context context;
    //private final HumanoidArmorLayer armorLayer;
    private final EquipmentLayerRenderer renderer;


    public ProtogenRenderer(EntityRendererProvider.Context context) {
        super(context, new ProtogenModel(), new Protogen());
        this.context = context;
        this.renderer = new EquipmentLayerRenderer(context.getEquipmentAssets(), Minecraft.getInstance().getAtlasManager().getAtlasOrThrow(Identifier.withDefaultNamespace("blocks")));
        //this.armorLayer= new HumanoidArmorLayer<>(this, new ArmorModelSet(this, this, this, this), new EquipmentLayerRenderer(context.getEquipmentAssets(), Minecraft.getInstance().getAtlasManager().getAtlasOrThrow(Identifier.withDefaultNamespace("blocks"))));
    }

    @Override
    public void extractRenderState(Player player, LivingEntityRenderState state, float f) {
        super.extractRenderState(player, state, f);
        /*hstate.xRot = state.xRot;
        hstate.yRot = state.yRot;
        hstate.headEquipment = new ItemStack(Items.IRON_HELMET);
        this.renderer.renderLayers(EquipmentClientInfo.LayerType.HUMANOID,
                ResourceKey.create(ResourceKey.createRegistryKey(Identifier.withDefaultNamespace("registry")), Identifier.withDefaultNamespace("armor")),
                        new PlayerModel(this.context.bakeLayer(ModelLayers.PLAYER), true), hstate, new ItemStack(Items.IRON_HELMET), new PoseStack(), Minecraft.getInstance().gameRenderer.getSubmitNodeStorage(),
                state.lightCoords, state.outlineColor
                );
        //armorLayer.submit(new PoseStack(), Minecraft.getInstance().gameRenderer.getSubmitNodeStorage(), state.lightCoords, hstate, state.yRot, state.xRot);*/
    }

    @Override
    public @Nullable HumanoidRenderState createRenderState() {
        return new HumanoidRenderState();
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
