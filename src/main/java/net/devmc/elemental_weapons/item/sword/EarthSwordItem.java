package net.devmc.elemental_weapons.item.sword;

import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.FallingBlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.SwordItem;
import net.minecraft.item.ToolMaterial;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class EarthSwordItem extends SwordItem {

	public static final List<UUID> thrownEntities= new ArrayList<>();

	public EarthSwordItem(ToolMaterial material, int attackDamage, float attackSpeed, Settings settings) {
		super(material, attackDamage, attackSpeed, settings);
	}

	@Override
	public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
		ItemStack stack = user.getStackInHand(hand);

		if (!world.isClient) {
			double radius = 6.0;
			BlockPos userPos = user.getBlockPos();
			BlockState dirtBlockState = Blocks.DIRT.getDefaultState();

			for (double angle = 0; angle < 360; angle += 4) {
				double radians = Math.toRadians(angle);
				double x = userPos.getX() + radius * Math.cos(radians);
				double z = userPos.getZ() + radius * Math.sin(radians);
				BlockPos dirtPos = BlockPos.ofFloored(x, userPos.getY(), z);

				FallingBlockEntity fallingDirt = FallingBlockEntity.spawnFromBlock(world, dirtPos, dirtBlockState);
				Vec3d direction = new Vec3d(x - userPos.getX(), 0, z - userPos.getZ()).normalize();
				fallingDirt.setVelocity(direction.x * 0.35, 0.5, direction.z * 0.35);
				fallingDirt.velocityModified = true;
				thrownEntities.add(fallingDirt.getUuid());
			}

			world.playSound(null, user.getX(), user.getY(), user.getZ(),
					SoundEvents.BLOCK_VINE_BREAK, SoundCategory.PLAYERS, 2.0F, 1.0F);

			user.getItemCooldownManager().set(this, 200);
		}

		return TypedActionResult.success(stack);
	}
}
