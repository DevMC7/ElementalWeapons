package net.devmc.elemental_weapons;

import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

import static net.devmc.elemental_weapons.ElementalWeapons.MOD_ID;

public class ElementalWeaponsItemGroups {

	public static final ItemGroup MISC_ITEM_GROUP = FabricItemGroup.builder()
			.icon(() -> new ItemStack(ElementalWeaponsItems.EARTH_CRYSTAL))
			.displayName(Text.translatable("itemGroup.elemental_weapons.misc_item_group"))
			.entries((context, entries) -> {
				entries.add(ElementalWeaponsItems.AIR_CRYSTAL);
				entries.add(ElementalWeaponsItems.FIRE_CRYSTAL);
				entries.add(ElementalWeaponsItems.WATER_CRYSTAL);
				entries.add(ElementalWeaponsItems.EARTH_CRYSTAL);
			})
			.build();

	public static final ItemGroup EQUIPMENT_ITEM_GROUP = FabricItemGroup.builder()
			.icon(() -> new ItemStack(ElementalWeaponsItems.EARTH_SWORD))
			.displayName(Text.translatable("itemGroup.elemental_weapons.equipment_item_group"))
			.entries((context, entries) -> {
				entries.add(ElementalWeaponsItems.AIR_SWORD);
				entries.add(ElementalWeaponsItems.FIRE_SWORD);
				entries.add(ElementalWeaponsItems.WATER_SWORD);
				entries.add(ElementalWeaponsItems.EARTH_SWORD);
			})
			.build();

	public static void init() {
		Registry.register(Registries.ITEM_GROUP, new Identifier(MOD_ID, "misc_item_group"), MISC_ITEM_GROUP);
		Registry.register(Registries.ITEM_GROUP, new Identifier(MOD_ID, "equipment_item_group"), EQUIPMENT_ITEM_GROUP);
	}
}
