package net.devmc.elemental_weapons;

import net.devmc.elemental_weapons.item.crystals.AirCrystalItem;
import net.devmc.elemental_weapons.item.crystals.EarthCrystalItem;
import net.devmc.elemental_weapons.item.crystals.FireCrystalItem;
import net.devmc.elemental_weapons.item.crystals.WaterCrystalItem;
import net.devmc.elemental_weapons.item.swords.AirSwordItem;
import net.devmc.elemental_weapons.item.swords.EarthSwordItem;
import net.devmc.elemental_weapons.item.swords.FireSwordItem;
import net.devmc.elemental_weapons.item.swords.WaterSwordItem;
import net.minecraft.item.Item;
import net.minecraft.item.ToolMaterials;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import net.minecraft.util.Rarity;

import static net.devmc.elemental_weapons.ElementalWeapons.MOD_ID;

public class ElementalWeaponsItems {

	public static final Item AIR_CRYSTAL = new AirCrystalItem(new Item.Settings());
	public static final Item FIRE_CRYSTAL = new FireCrystalItem(new Item.Settings());
	public static final Item WATER_CRYSTAL = new WaterCrystalItem(new Item.Settings());
	public static final Item EARTH_CRYSTAL = new EarthCrystalItem(new Item.Settings());

	public static final Item AIR_SWORD = new AirSwordItem(ToolMaterials.DIAMOND, 5, -2.8F, new Item.Settings().rarity(Rarity.UNCOMMON));
	public static final Item FIRE_SWORD = new FireSwordItem(ToolMaterials.DIAMOND, 5, -2.8F, new Item.Settings().rarity(Rarity.UNCOMMON));
	public static final Item WATER_SWORD = new WaterSwordItem(ToolMaterials.DIAMOND, 5, -2.8F, new Item.Settings().rarity(Rarity.UNCOMMON));
	public static final Item EARTH_SWORD = new EarthSwordItem(ToolMaterials.DIAMOND, 5, -2.8F, new Item.Settings().rarity(Rarity.UNCOMMON));

	public static void init() {
		Registry.register(Registries.ITEM, new Identifier(MOD_ID, "air_crystal"), AIR_CRYSTAL);
		Registry.register(Registries.ITEM, new Identifier(MOD_ID, "fire_crystal"), FIRE_CRYSTAL);
		Registry.register(Registries.ITEM, new Identifier(MOD_ID, "water_crystal"), WATER_CRYSTAL);
		Registry.register(Registries.ITEM, new Identifier(MOD_ID, "earth_crystal"), EARTH_CRYSTAL);

		Registry.register(Registries.ITEM, new Identifier(MOD_ID, "air_sword"), AIR_SWORD);
		Registry.register(Registries.ITEM, new Identifier(MOD_ID, "fire_sword"), FIRE_SWORD);
		Registry.register(Registries.ITEM, new Identifier(MOD_ID, "water_sword"), WATER_SWORD);
		Registry.register(Registries.ITEM, new Identifier(MOD_ID, "earth_sword"), EARTH_SWORD);
	}
}
