package com.jeff.protomod.client;

import net.minecraft.resources.Identifier;

import java.io.*;
import java.net.URL;
import java.nio.file.Files;

public class ResourcePackBuilder {
    public static void build() throws IOException {

        File dir = new File("resourcepacks/protopack/assets/protomod/textures/entity");

        Files.createDirectories(dir.toPath());

        File file = new File(dir, "README.txt");

        File mcmeta = new File("resourcepacks/protopack/pack.mcmeta");

        if (!file.exists()) {
            file.createNewFile();
        }
        if (!mcmeta.exists()) {
            mcmeta.createNewFile();
        }
        try (FileWriter writer = new FileWriter(mcmeta)) {
            writer.write("{\n" +
                    "  \"pack\": {\n" +
                    "    \"pack_format\": 46,\n" +
                    "    \"description\": \"Pack used for making custom protogen skins in Protomod.\"\n" +
                    "  }\n" +
                    "}");
        }
        try (FileWriter writer = new FileWriter(file)) {
            writer.write("How to use your own protogen skin: \n1. Make the skin using Blockbench. The bbmodel file is available on our GitHub. \n2. Export the texture as a PNG and copy it. \n3. Paste it into this directory and rename it to 'protogen'. \nFor any questions you may have, please ask me on my GitHub.");
        }
    }
}
