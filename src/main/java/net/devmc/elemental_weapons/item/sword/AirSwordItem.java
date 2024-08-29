package net.devmc.elemental_weapons.item.sword;

import net.devmc.elemental_weapons.ElementalWeapons;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.SwordItem;
import net.minecraft.item.ToolMaterial;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import org.apache.logging.log4j.Level;

public class AirSwordItem extends SwordItem {

	public AirSwordItem(ToolMaterial material, int attackDamage, float attackSpeed, Settings settings) {
		super(material, attackDamage, attackSpeed, settings);
	}

	@Override
	public boolean postHit(ItemStack stack, LivingEntity target, LivingEntity attacker) {
		target.addStatusEffect(new StatusEffectInstance(StatusEffects.LEVITATION, 3, 2));
		return true;
	}

	@Override
	public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
		ItemStack stack = user.getStackInHand(hand);

		if (!world.isClient) {
			Vec3d lookDirection = user.getRotationVec(1.0F);
			Vec3d dashVector = lookDirection.multiply(1.85);
			ElementalWeapons.LOGGER.log(Level.INFO, "[Air Sword] Applying " + dashVector + " velocity to player " + user.getName().getString());
			user.addVelocity(dashVector.x, dashVector.y, dashVector.z);
			user.velocityModified = true;

			if (world instanceof ServerWorld) {
				((ServerWorld) world).spawnParticles(ParticleTypes.CLOUD,
						user.getX(), user.getY(), user.getZ(),
						20, 0.5, 0.5, 0.5, 0.1);
			}
			world.playSound(null, user.getX(), user.getY(), user.getZ(),
					SoundEvents.BLOCK_FIRE_EXTINGUISH, SoundCategory.PLAYERS, 1.0F, 1.0F);

			user.getItemCooldownManager().set(this, 100);
		}

		return TypedActionResult.success(stack);
	}
}
