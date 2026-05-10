package net.mcreator.prueba.init;

import net.mcreator.prueba.PruebaMod;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ModSounds {

    public static final DeferredRegister<SoundEvent> SOUND_EVENTS =
            DeferredRegister.create(Registries.SOUND_EVENT, PruebaMod.MODID);

    public static final DeferredHolder<SoundEvent, SoundEvent> SAHUR_AMBIENT =
            SOUND_EVENTS.register("sahur_ambient", () ->
                    SoundEvent.createVariableRangeEvent(
                            ResourceLocation.fromNamespaceAndPath(PruebaMod.MODID, "sahur_ambient")));

    public static final DeferredHolder<SoundEvent, SoundEvent> SAHUR_HURT =
            SOUND_EVENTS.register("sahur_hurt", () ->
                    SoundEvent.createVariableRangeEvent(
                            ResourceLocation.fromNamespaceAndPath(PruebaMod.MODID, "sahur_hurt")));

    public static final DeferredHolder<SoundEvent, SoundEvent> SAHUR_DEATH =
            SOUND_EVENTS.register("sahur_death", () ->
                    SoundEvent.createVariableRangeEvent(
                            ResourceLocation.fromNamespaceAndPath(PruebaMod.MODID, "sahur_death")));

    public static final DeferredHolder<SoundEvent, SoundEvent> SAHUR_SEE_PLAYER =
            SOUND_EVENTS.register("sahur_see_player", () ->
                    SoundEvent.createVariableRangeEvent(
                            ResourceLocation.fromNamespaceAndPath(PruebaMod.MODID, "sahur_see_player")));
}
