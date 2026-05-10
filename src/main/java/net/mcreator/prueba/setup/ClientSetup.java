package net.mcreator.prueba.setup;

import net.mcreator.prueba.entity.renderer.SahurRenderer;
import net.mcreator.prueba.init.ModEntities;
import net.neoforged.neoforge.client.event.EntityRenderersEvent;

public class ClientSetup {

    public static void onRegisterRenderers(EntityRenderersEvent.RegisterRenderers event) {
        event.registerEntityRenderer(ModEntities.SAHUR.get(), SahurRenderer::new);
    }
}
