package net.devmc.elemental_weapons.item.swords;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.FireballEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.SwordItem;
import net.minecraft.item.ToolMaterial;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;

public class FireSwordItem extends SwordItem {

	public FireSwordItem(ToolMaterial toolMaterial, int attackDamage, float attackSpeed, Settings settings) {
		super(toolMaterial, attackDamage, attackSpeed, settings);
	}

	@Override
	public boolean postHit(ItemStack stack, LivingEntity target, LivingEntity attacker) {
		if (!target.isTouchingWaterOrRain()) {
			target.setOnFireFor(5);
		}
		return true;
	}

	@Override
	public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
		ItemStack stack = user.getStackInHand(hand);

		if (!world.isClient) {
			FireballEntity fireball = new FireballEntity(EntityType.FIREBALL, world);
			fireball.setVelocity(user, user.getPitch(), user.getYaw(), 0.0F, 1.5F, 0F);
			fireball.setPos(user.getX(), user.getY() + 1.5, user.getZ());
			world.spawnEntity(fireball);
			user.getItemCooldownManager().set(this, 80);
		}

		return TypedActionResult.success(stack);
	}
}
