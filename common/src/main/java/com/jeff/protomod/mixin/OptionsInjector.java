package com.jeff.protomod.mixin;

import com.jeff.protomod.Protomod;
import net.minecraft.client.Options;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Options.class)
public class OptionsInjector {
    @Inject(at = @At("HEAD"), method = "processDumpedOptions")
    private void processdumped(Options.OptionAccess access, CallbackInfo ci) {
        access.process("prootEnabled", Protomod.isProotEnabled);
    }

    @Inject(at = @At("TAIL"), method = "processOptions")
    private void process(Options.FieldAccess access, CallbackInfo ci) {
        access.process("prootEnabled", Protomod.isProotEnabled);
    }
}
