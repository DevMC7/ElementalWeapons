package net.devmc.elemental_weapons.mixin;

import net.devmc.elemental_weapons.ElementalWeapons;
import net.devmc.elemental_weapons.item.sword.EarthSwordItem;
import net.minecraft.entity.FallingBlockEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import org.apache.logging.log4j.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.List;

@Mixin(FallingBlockEntity.class)
public abstract class FallingBlockEntityMixin {

	@Inject(method = "tick", at = @At("HEAD"))
	public void onTick(CallbackInfo ci) {
		FallingBlockEntity self = (FallingBlockEntity) (Object) this;
		World world = self.getWorld();

		if (!self.isOnGround() && !world.isClient && EarthSwordItem.thrownEntities.contains(self.getUuid())) {
			Box box = self.getBoundingBox();
			List<LivingEntity> entities = world.getEntitiesByClass(LivingEntity.class, box, LivingEntity::isAlive);

			for (LivingEntity entity : entities) {
				double initialUpwardVelocity = 0.25;
				Vec3d knockbackDirection = entity.getPos().subtract(self.getPos()).normalize();
				ElementalWeapons.LOGGER.log(Level.INFO, "[Earth Sword Dirt Circle] Applying " + knockbackDirection + " velocity to entity " + entity.getName().getString());
				entity.addVelocity(knockbackDirection.x * 0.75, initialUpwardVelocity, knockbackDirection.z * 0.75);
				entity.velocityModified = true;

				EarthSwordItem.thrownEntities.remove(self.getUuid());
				self.discard();
				break;
			}
		}
	}
}