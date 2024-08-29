package net.devmc.elemental_weapons.mixin;

import dev.emi.trinkets.api.TrinketComponent;
import dev.emi.trinkets.api.TrinketsApi;
import net.devmc.elemental_weapons.ElementalWeaponsItems;
import net.minecraft.entity.Entity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.ProjectileEntity;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Optional;

@Mixin(Entity.class)
public abstract class EntityMixin {

	@Unique
	private final Entity self = (Entity) (Object) this;

	@Shadow
	public float fallDistance;

	@Inject(method = "tick", at = @At("HEAD"), cancellable = true)
	void tick(CallbackInfo ci) {
		if (self instanceof ProjectileEntity entity) {
			Vec3d velocity = entity.getVelocity();
			if (velocity.lengthSquared() < 0.001) {
				entity.discard();
				ci.cancel();
			}
		} else if (self instanceof PlayerEntity player) {
			Optional<TrinketComponent> component = TrinketsApi.getTrinketComponent(player);
			component.ifPresent(trinketComponent -> trinketComponent.getEquipped(stack -> {
				if (stack.isOf(ElementalWeaponsItems.ELEMENTAL_NECKLACE_AIR)) {
					if (player.fallDistance > 4) player.addStatusEffect(new StatusEffectInstance(StatusEffects.SLOW_FALLING, 20, 0, true, false, false));
					player.addStatusEffect(new StatusEffectInstance(StatusEffects.SPEED, 20, 0, true, false, false));
					player.addStatusEffect(new StatusEffectInstance(StatusEffects.JUMP_BOOST, 20, 1, true, false, false));
				} else if (stack.isOf(ElementalWeaponsItems.ELEMENTAL_NECKLACE_FIRE)) {
					player.addStatusEffect(new StatusEffectInstance(StatusEffects.FIRE_RESISTANCE, 20, 0, true, false, false));
				} else if (stack.isOf(ElementalWeaponsItems.ELEMENTAL_NECKLACE_WATER)) {
					player.addStatusEffect(new StatusEffectInstance(StatusEffects.WATER_BREATHING, 20, 0, true, false, false));
				}
				return false;
			}));
		}
	}

	@Inject(method = "onLanding", at = @At("TAIL"))
	public void onLanding(CallbackInfo ci) {
		if (self instanceof PlayerEntity player && !player.isCreative() && fallDistance >= 5.0 && player.getVelocity().getX() < 1) {
			Optional<TrinketComponent> component = TrinketsApi.getTrinketComponent(player);
			component.ifPresent(trinketComponent -> trinketComponent.getEquipped(stack -> {
				if (stack.isOf(ElementalWeaponsItems.ELEMENTAL_NECKLACE_EARTH) && !player.getItemCooldownManager().isCoolingDown(stack.getItem())) {
					World world = player.getWorld();

					player.getAbilities().invulnerable = true;
					world.createExplosion(self, self.getX(), self.getY(), self.getZ(), 2.0F, false, World.ExplosionSourceType.TNT);
					player.getAbilities().invulnerable = false;
					player.getItemCooldownManager().set(stack.getItem(), 100);
				}
				return false;
			}));
		}
	}

}
