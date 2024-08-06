package net.devmc.elemental_weapons.mixin;

import net.minecraft.entity.FallingBlockEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
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

		if (!self.isOnGround() && !world.isClient) {
			Box box = self.getBoundingBox();
			List<LivingEntity> entities = world.getEntitiesByClass(LivingEntity.class, box, LivingEntity::isAlive);

			for (LivingEntity entity : entities) {
				double initialUpwardVelocity = 0.25;
				Vec3d knockbackDirection = entity.getPos().subtract(self.getPos()).normalize();
				entity.addVelocity(knockbackDirection.x * 0.75, initialUpwardVelocity, knockbackDirection.z * 0.75);
				entity.velocityModified = true;

				self.discard();
				break;
			}
		}
	}
}