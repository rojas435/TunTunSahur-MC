package net.mcreator.prueba.init;

import net.mcreator.prueba.PruebaMod;
import net.mcreator.prueba.entity.SahurEntity;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ModEntities {

    public static final DeferredRegister<EntityType<?>> ENTITY_TYPES =
            DeferredRegister.create(Registries.ENTITY_TYPE, PruebaMod.MODID);

    public static final DeferredHolder<EntityType<?>, EntityType<SahurEntity>> SAHUR =
            ENTITY_TYPES.register("sahur", () -> EntityType.Builder
                    .<SahurEntity>of(SahurEntity::new, MobCategory.MONSTER)
                    .sized(0.8f, 2.5f)
                    .clientTrackingRange(8)
                    .build(ResourceLocation.fromNamespaceAndPath(PruebaMod.MODID, "sahur").toString()));
}
