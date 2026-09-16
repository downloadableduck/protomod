package com.jeff.protomod.client;

import com.geckolib.renderer.GeoReplacedEntityRenderer;
import com.geckolib.renderer.base.BoneSnapshots;
import com.geckolib.renderer.base.RenderPassInfo;
import com.jeff.protomod.Protogen;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.layers.EquipmentLayerRenderer;
import net.minecraft.client.renderer.entity.player.AvatarRenderer;
import net.minecraft.client.renderer.entity.state.AvatarRenderState;
import net.minecraft.client.renderer.entity.state.HumanoidRenderState;
import net.minecraft.client.renderer.entity.state.LivingEntityRenderState;
import net.minecraft.client.renderer.item.ItemModelResolver;
import net.minecraft.client.renderer.state.level.FirstPersonHandsAndItemsRenderState;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
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
        this.renderer = new EquipmentLayerRenderer(context.getEquipmentAssets(), Minecraft.getInstance().getPalettedTextureManager());
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
    public @Nullable @NotNull LivingEntityRenderState createRenderState() {
        return new AvatarRenderState();
    }

    /*@Override
    public AvatarRenderState createRenderState(Protogen animateable, Player relatedObject) {
        return this.createRenderState();
    }*/

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

    public static void copyFrom(Player player, FirstPersonHandsAndItemsRenderState state) {
        state.attackHand = player.getUsedItemHand();
        state.mainHandItem = player.getItemInHand(player.getUsedItemHand());
    }
}
