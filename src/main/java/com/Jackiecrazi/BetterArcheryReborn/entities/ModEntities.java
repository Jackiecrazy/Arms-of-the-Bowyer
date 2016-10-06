package com.Jackiecrazi.BetterArcheryReborn.entities;

import com.Jackiecrazi.BetterArcheryReborn.BAR;

import cpw.mods.fml.common.registry.EntityRegistry;

public class ModEntities {
	public static void init(){
		EntityRegistry.registerModEntity(EntityQuiverModArrowNew.class, "itsanarrownothingtoseehere", 1, BAR.inst, 64, 20, true);
	}
}
