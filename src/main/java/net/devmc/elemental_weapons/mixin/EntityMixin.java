package net.devmc.elemental_weapons.mixin;

import net.minecraft.entity.Entity;
import net.minecraft.entity.projectile.ProjectileEntity;
import net.minecraft.util.math.Vec3d;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Entity.class)
public abstract class EntityMixin {

	@Inject(method = "tick", at = @At("HEAD"), cancellable = true)
	public void destroy(CallbackInfo ci) {
		if ((Entity) (Object) this instanceof ProjectileEntity entity) {
			Vec3d velocity = entity.getVelocity();
			if (velocity.lengthSquared() < 0.001) {
				entity.discard();
				ci.cancel();
			}
		}
	}
}
