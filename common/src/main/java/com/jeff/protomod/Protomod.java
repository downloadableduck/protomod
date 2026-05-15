package com.jeff.protomod;

import com.jeff.protomod.client.ResourcePackBuilder;
import net.minecraft.client.OptionInstance;
import net.minecraft.resources.Identifier;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;

public final class Protomod {
    public static final String MOD_ID = "protomod";
    public static final Identifier BUTTON_LOCATION = Identifier.withDefaultNamespace("widget/button");
    public static OptionInstance<@NotNull Boolean> isProotEnabled = OptionInstance.createBoolean("prootEnabled", true);

    public static void init() {
        // Write common init code here.
        try {
            ResourcePackBuilder.build();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
