package net.mcreator.prueba.entity.renderer;

import net.mcreator.prueba.entity.SahurEntity;
import net.mcreator.prueba.entity.model.SahurModel;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

public class SahurRenderer extends GeoEntityRenderer<SahurEntity> {

    public SahurRenderer(EntityRendererProvider.Context context) {
        super(context, new SahurModel());
    }
}
