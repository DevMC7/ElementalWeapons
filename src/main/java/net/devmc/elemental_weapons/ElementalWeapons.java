package net.devmc.elemental_weapons;

import net.fabricmc.api.ModInitializer;
import net.minecraft.client.render.entity.model.EntityModel;
import net.minecraft.client.render.entity.model.PlayerEntityModel;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.math.RotationAxis;


public class ElementalWeapons implements ModInitializer {

	public static final String MOD_ID = "elemental_weapons";

	@Override
	public void onInitialize() {
		ElementalWeaponsItems.register();
	}

	public static void headFamiliarTrinket(MatrixStack matrices, EntityModel<? extends LivingEntity> model, LivingEntity entity, float headYaw, float headPitch) {
		if (!entity.isSneaking() && !entity.isSwimming()) {
			if (entity.isInSneakingPose() && !model.riding) {
				matrices.translate(0.0F, 0.25F, 0.0F);
			}
			matrices.multiply(RotationAxis.POSITIVE_Y.rotationDegrees(headYaw));
			matrices.multiply(RotationAxis.POSITIVE_X.rotationDegrees(headPitch));
		} else if (model instanceof PlayerEntityModel) {
			PlayerEntityModel<?> playerModel = (PlayerEntityModel<?>) model;

		}

		matrices.translate(0.0F, -0.25F, -0.3F);
	}

}
