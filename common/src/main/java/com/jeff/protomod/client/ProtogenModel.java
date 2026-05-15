package com.jeff.protomod.client;

import com.geckolib.model.DefaultedEntityGeoModel;
import com.jeff.protomod.Protogen;
import net.minecraft.resources.Identifier;
import org.jetbrains.annotations.NotNull;

import static com.jeff.protomod.Protomod.MOD_ID;

public class ProtogenModel extends DefaultedEntityGeoModel<@NotNull Protogen> {
    public ProtogenModel() {
        super(Identifier.fromNamespaceAndPath(MOD_ID, "protogen"));
    }
}
