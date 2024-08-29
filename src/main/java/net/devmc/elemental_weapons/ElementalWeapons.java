package net.devmc.elemental_weapons;

import net.fabricmc.api.ModInitializer;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ElementalWeapons implements ModInitializer {

	public static final Logger LOGGER = LogManager.getLogger();

	public static final String MOD_ID = "elemental_weapons";

	@Override
	public void onInitialize() {
		ElementalWeaponsItems.init();
		ElementalWeaponsItemGroups.init();
	}

}
