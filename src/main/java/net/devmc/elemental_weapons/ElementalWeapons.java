package net.devmc.elemental_weapons;

import net.fabricmc.api.ModInitializer;


public class ElementalWeapons implements ModInitializer {

	public static final String MOD_ID = "elemental_weapons";

	@Override
	public void onInitialize() {
		ElementalWeaponsItems.init();
		ElementalWeaponsItemGroups.init();
	}

}
