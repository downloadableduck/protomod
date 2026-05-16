package com.jeff.protomod;

import com.geckolib.animatable.GeoReplacedEntity;
import com.geckolib.animatable.instance.AnimatableInstanceCache;
import com.geckolib.animatable.manager.AnimatableManager;
import com.geckolib.animation.AnimationController;
import com.geckolib.animation.RawAnimation;
import com.geckolib.animation.object.LoopType;
import com.geckolib.animation.object.PlayState;
import com.geckolib.util.GeckoLibUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.world.entity.HumanoidArm;
import org.jetbrains.annotations.NotNull;

public class Protogen implements GeoReplacedEntity {

    public static boolean isFlossing = false;
    private final AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);

    //TODO: add sneaking, punching & crawling animations
    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllers) {
        final LocalPlayer player = Minecraft.getInstance().player;

        controllers.add(new AnimationController<>("animation.protogen.walk", 5, animTest -> {
            if (player != null) {
                if (player.isMoving()) {
                    animTest.setControllerSpeed(2.5f);
                    return animTest.setAndContinue(RawAnimation.begin().thenLoop("animation.protogen.walk"));
                } else {
                    animTest.setControllerSpeed(0.5f);
                    return animTest.setAndContinue(RawAnimation.begin().thenLoop("animation.protogen.idle"));
                }
            }
            return animTest.setAndContinue(RawAnimation.begin().thenLoop("animation.protogen.idle"));
        }));

        controllers.add(new AnimationController<>("protogen_fight", 0, animTest -> {
            if (player != null) {
                if (player.swingTime > 0) {
                    //cuts off at the end, fix this
                    animTest.setControllerSpeed(6f);
                    if (player.getMainArm().equals(HumanoidArm.LEFT)) {
                        return animTest.setAndContinue(RawAnimation.begin().then("animation.protogen.punch_left", LoopType.PLAY_ONCE));
                    } else {
                        return animTest.setAndContinue(RawAnimation.begin().then("animation.protogen.punch_right", LoopType.PLAY_ONCE));
                    }
                }
            }

            return animTest.setAndContinue(RawAnimation.begin().then("animation.protogen.do_nothing", LoopType.PLAY_ONCE));
        }));
        controllers.add(new AnimationController<>("protogen_ride", 0, animTest -> {
            if (player != null) {
                if (player.isPassenger()) {
                    return animTest.setAndContinue(RawAnimation.begin().then("animation.protogen.sit", LoopType.HOLD_ON_LAST_FRAME));
                }
            }
            return animTest.setAndContinue(RawAnimation.begin().then("animation.protogen.do_nothing", LoopType.PLAY_ONCE));
        }));

        controllers.add(new AnimationController<>("protogen_sneak", 5, animTest -> {
            if (player != null) {
                if (player.isCrouching()) {
                    return animTest.setAndContinue(RawAnimation.begin().then("animation.protogen.sneak", LoopType.HOLD_ON_LAST_FRAME));
                }
            }
            return animTest.setAndContinue(RawAnimation.begin().then("animation.protogen.do_nothing", LoopType.PLAY_ONCE));
        }));

        controllers.add(new AnimationController<>("protogen_floss", 5, animTest -> {
            if (player != null && isFlossing) {
                if (animTest.controller().getAnimationSpeed() != 0.75f) {
                    animTest.setControllerSpeed(0.75f);
                }
                return animTest.setAndContinue(RawAnimation.begin().then("animation.protogen.floss", LoopType.LOOP));
            } else {
                return animTest.setAndContinue(RawAnimation.begin().then("animation.protogen.do_nothing", LoopType.LOOP));
            }
        }));
    }

    @Override
    public @NotNull AnimatableInstanceCache getAnimatableInstanceCache() {
        return this.cache;
    }
}
