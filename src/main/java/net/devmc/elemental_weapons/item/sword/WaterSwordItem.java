package net.devmc.elemental_weapons.item.sword;

import net.devmc.elemental_weapons.ElementalWeapons;
import net.minecraft.block.Blocks;
import net.minecraft.entity.Entity;
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
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraft.world.event.GameEvent;
import org.apache.logging.log4j.Level;

import java.util.List;

public class WaterSwordItem extends SwordItem {
	public WaterSwordItem(ToolMaterial toolMaterial, int attackDamage, float attackSpeed, Settings settings) {
		super(toolMaterial, attackDamage, attackSpeed, settings);
	}

	@Override
	public boolean postHit(ItemStack stack, LivingEntity target, LivingEntity attacker) {
		target.setFrozenTicks(3);
		return true;
	}

	@Override
	public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
		ItemStack stack = user.getStackInHand(hand);

		if (!world.isClient) {
			if (user.isTouchingWater()) {
				double radius = 5.0;
				Box area = new Box(user.getBlockPos()).expand(radius);
				List<LivingEntity> nearbyEntities = world.getEntitiesByClass(LivingEntity.class, area, entity -> entity != user && entity.isAlive());

				for (Entity entity : nearbyEntities) {
					Vec3d direction = entity.getPos().subtract(user.getPos()).normalize();
					entity.addVelocity(direction.x * 2, 0.5, direction.z * 2);
					entity.velocityModified = true;
				}

				user.addStatusEffect(new StatusEffectInstance(StatusEffects.DOLPHINS_GRACE, 160, 0));
				user.addStatusEffect(new StatusEffectInstance(StatusEffects.CONDUIT_POWER, 240, 1	));

				if (world instanceof ServerWorld) {
					((ServerWorld) world).spawnParticles(ParticleTypes.SPLASH, user.getX(), user.getY(), user.getZ(), 100, 2.0, 2.0, 2.0, 0.1);
				}

				world.playSound(null, user.getX(), user.getY(), user.getZ(),
						SoundEvents.ENTITY_GENERIC_SPLASH, SoundCategory.PLAYERS, 1.0F, 1.0F);

				user.getItemCooldownManager().set(this, 300);
			} else {
				double radius = 8.0;
				Box area = new Box(user.getBlockPos()).expand(radius);
				List<LivingEntity> nearbyEntities = world.getEntitiesByClass(LivingEntity.class, area, entity -> entity != user && entity.isAlive());

				for (Entity entity : nearbyEntities) {
					Vec3d geyserPosition = entity.getPos();

					entity.addVelocity(0, 1, 0);
					entity.velocityModified = true;

					BlockPos blockPos = new BlockPos((int) geyserPosition.x, (int) (geyserPosition.y - 1), (int) geyserPosition.z);
					if (world.getBlockState(blockPos).isAir()) {
						world.setBlockState(blockPos, Blocks.WATER.getDefaultState());
						world.emitGameEvent(GameEvent.BLOCK_PLACE, blockPos, GameEvent.Emitter.of(entity));
					}

					if (world instanceof ServerWorld) {
						((ServerWorld) world).spawnParticles(ParticleTypes.SPLASH,
								geyserPosition.getX(), geyserPosition.getY(), geyserPosition.getZ(),
								20, 0.5, 0.5, 0.5, 0.1);
					}
				}

				world.playSound(null, user.getX(), user.getY(), user.getZ(),
						SoundEvents.ENTITY_GENERIC_SPLASH, SoundCategory.PLAYERS, 1.0F, 1.0F);

				user.getItemCooldownManager().set(this, 200);
			}

			user.setOnFire(false);
		}

		return TypedActionResult.success(stack);
	}
}
