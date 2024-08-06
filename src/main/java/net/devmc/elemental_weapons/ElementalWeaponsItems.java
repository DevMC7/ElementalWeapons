package net.devmc.elemental_weapons;

import net.devmc.elemental_weapons.item.*;
import net.minecraft.item.Item;
import net.minecraft.item.ToolMaterials;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import net.minecraft.util.Rarity;

import static net.devmc.elemental_weapons.ElementalWeapons.MOD_ID;

public class ElementalWeaponsItems {

	public static final Item AIR_SWORD = new AirSwordItem(ToolMaterials.DIAMOND, 5, -2.8F, new Item.Settings().rarity(Rarity.UNCOMMON));
	public static final Item FIRE_SWORD = new FireSwordItem(ToolMaterials.DIAMOND, 5, -2.8F, new Item.Settings().rarity(Rarity.UNCOMMON));
	public static final Item WATER_SWORD = new WaterSwordItem(ToolMaterials.DIAMOND, 5, -2.8F, new Item.Settings().rarity(Rarity.UNCOMMON));
	public static final Item EARTH_SWORD = new EarthSwordItem(ToolMaterials.DIAMOND, 5, -2.8F, new Item.Settings().rarity(Rarity.UNCOMMON));

	public static void register() {
		Registry.register(Registries.ITEM, new Identifier(MOD_ID, "air_sword"), AIR_SWORD);
		Registry.register(Registries.ITEM, new Identifier(MOD_ID, "fire_sword"), FIRE_SWORD);
		Registry.register(Registries.ITEM, new Identifier(MOD_ID, "water_sword"), WATER_SWORD);
		Registry.register(Registries.ITEM, new Identifier(MOD_ID, "earth_sword"), EARTH_SWORD);
	}
}
