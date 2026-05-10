package net.mcreator.prueba.entity.model;

import net.mcreator.prueba.PruebaMod;
import net.mcreator.prueba.entity.SahurEntity;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.GeoModel;

public class SahurModel extends GeoModel<SahurEntity> {

    @Override
    public ResourceLocation getModelResource(SahurEntity entity) {
        return ResourceLocation.fromNamespaceAndPath(PruebaMod.MODID, "geo/entities/dweller.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(SahurEntity entity) {
        return ResourceLocation.fromNamespaceAndPath(PruebaMod.MODID, "textures/entities/dweller.png");
    }

    @Override
    public ResourceLocation getAnimationResource(SahurEntity entity) {
        return ResourceLocation.fromNamespaceAndPath(PruebaMod.MODID, "animations/entities/dweller.animation.json");
    }
}
