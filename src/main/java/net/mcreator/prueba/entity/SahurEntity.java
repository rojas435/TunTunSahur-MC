package net.mcreator.prueba.entity;

import net.mcreator.prueba.init.ModSounds;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import software.bernie.geckolib.animatable.GeoEntity;
import software.bernie.geckolib.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.animation.*;
import software.bernie.geckolib.util.GeckoLibUtil;

public class SahurEntity extends Monster implements GeoEntity {

    private static final RawAnimation IDLE      = RawAnimation.begin().thenLoop("idle");
    private static final RawAnimation RUN       = RawAnimation.begin().thenLoop("run");
    private static final RawAnimation SAW_YOU   = RawAnimation.begin().thenLoop("ISawYou");
    private static final RawAnimation ATTACK1   = RawAnimation.begin().then("attack1", Animation.LoopType.PLAY_ONCE);
    private static final RawAnimation ATTACK2   = RawAnimation.begin().then("attack2", Animation.LoopType.PLAY_ONCE);
    private static final RawAnimation HURT_ANIM = RawAnimation.begin().then("hurt", Animation.LoopType.PLAY_ONCE);
    private static final RawAnimation DEAD_ANIM = RawAnimation.begin().thenLoop("dead");

    private final AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);

    private boolean justSpottedPlayer = false;
    private int spottedTimer = 0;

    public SahurEntity(EntityType<? extends Monster> type, Level level) {
        super(type, level);
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Monster.createMonsterAttributes()
                .add(Attributes.MAX_HEALTH, 40.0)
                .add(Attributes.MOVEMENT_SPEED, 0.38)
                .add(Attributes.ATTACK_DAMAGE, 6.0)
                .add(Attributes.FOLLOW_RANGE, 35.0)
                .add(Attributes.ARMOR, 2.0);
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(1, new FloatGoal(this));
        this.goalSelector.addGoal(2, new MeleeAttackGoal(this, 1.3, true));
        this.goalSelector.addGoal(3, new WaterAvoidingRandomStrollGoal(this, 1.0));
        this.goalSelector.addGoal(4, new LookAtPlayerGoal(this, Player.class, 8.0f));
        this.goalSelector.addGoal(5, new RandomLookAroundGoal(this));
        this.targetSelector.addGoal(1, new HurtByTargetGoal(this));
        // ignoreCreative=false para que también persiga en modo creativo
        this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, Player.class, 10, true, false,
                player -> !player.isSpectator()));
    }

    @Override
    public int getAmbientSoundInterval() {
        return 40; // cada ~2 segundos en lugar de 4, más fácil de escuchar
    }

    @Override
    public void setTarget(LivingEntity target) {
        LivingEntity prev = this.getTarget();
        super.setTarget(target);
        if (prev == null && target != null) {
            // acaba de ver a un jugador: reproduce sonido y animacion ISawYou
            this.playSound(ModSounds.SAHUR_SEE_PLAYER.get(), 1.5f, 1.0f);
            justSpottedPlayer = true;
            spottedTimer = 65; // ~3.25 segundos de animacion ISawYou
        }
    }

    @Override
    protected void customServerAiStep() {
        super.customServerAiStep();
        if (spottedTimer > 0) {
            spottedTimer--;
            if (spottedTimer == 0) justSpottedPlayer = false;
        }
    }

    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllers) {
        // Controlador de movimiento/estado base
        controllers.add(new AnimationController<>(this, "movement", 5, state -> {
            if (!this.isAlive()) return state.setAndContinue(DEAD_ANIM);
            if (justSpottedPlayer) return state.setAndContinue(SAW_YOU);
            if (state.isMoving()) return state.setAndContinue(RUN);
            return state.setAndContinue(IDLE);
        }));

        // Controlador separado para ataques y daño (play-once)
        controllers.add(new AnimationController<>(this, "attack_hurt", 2, state -> PlayState.STOP)
                .triggerableAnim("attack1", ATTACK1)
                .triggerableAnim("attack2", ATTACK2)
                .triggerableAnim("hurt", HURT_ANIM));
    }

    @Override
    public boolean doHurtTarget(Entity target) {
        boolean hit = super.doHurtTarget(target);
        if (hit) {
            // alterna entre attack1 y attack2
            String anim = this.random.nextBoolean() ? "attack1" : "attack2";
            this.triggerAnim("attack_hurt", anim);
        }
        return hit;
    }

    @Override
    public boolean hurt(DamageSource source, float amount) {
        boolean wasHurt = super.hurt(source, amount);
        if (wasHurt) this.triggerAnim("attack_hurt", "hurt");
        return wasHurt;
    }

    @Override
    protected SoundEvent getAmbientSound() {
        return ModSounds.SAHUR_AMBIENT.get();
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource source) {
        return ModSounds.SAHUR_HURT.get();
    }

    @Override
    protected SoundEvent getDeathSound() {
        return ModSounds.SAHUR_DEATH.get();
    }

    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return cache;
    }
}
