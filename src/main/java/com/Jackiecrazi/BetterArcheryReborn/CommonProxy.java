package com.Jackiecrazi.BetterArcheryReborn;


import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;

import com.Jackiecrazi.BetterArcheryReborn.Items.ModItems;
import com.Jackiecrazi.BetterArcheryReborn.crafting.ModCrafting;
import com.Jackiecrazi.BetterArcheryReborn.dumbpackets.FirstMessage;
import com.Jackiecrazi.BetterArcheryReborn.dumbpackets.SecondMessage;
import com.Jackiecrazi.BetterArcheryReborn.entities.ModEntities;
import com.Jackiecrazi.BetterArcheryReborn.lenders.QuiverModTickHandler;
import com.Jackiecrazi.BetterArcheryReborn.quivering.QuiverGuiHandler;

import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.relauncher.Side;

public class CommonProxy {
	public static Minecraft mc;
	public static QuiverModTickHandler overlayHandler;
	 @EventHandler
	    public void preInit(FMLPreInitializationEvent event)
	    {
		 ConfigofJustice.CreatioExNihilo(event.getSuggestedConfigurationFile());
	    	ModItems.itemify();
	    	ModEntities.init();
	    	int discriminator=0;
	    	BAR.net.registerMessage(FirstMessage.Handler.class, FirstMessage.class, discriminator++, Side.SERVER);
	    	BAR.net.registerMessage(SecondMessage.Handler.class, SecondMessage.class, discriminator++, Side.SERVER);
	    }
	 @EventHandler
	    public void init(FMLInitializationEvent event)
	    {
	    	ModCrafting.initCrafting();
	    	NetworkRegistry.INSTANCE.registerGuiHandler(BAR.inst, new QuiverGuiHandler());

	    }
	 @EventHandler
	    public void postInit(FMLPostInitializationEvent event)
	    {
	    	
	    }
	 public void register() { }

		public void preInitRegister() { }
     
     public void onBowUse(EntityPlayer player, float frameTime) {}
 	
 	public void resetSavedFOV() {}

 	public void spawnDiggingFX(World world, double x, double y, double z, double motionX, double motionY, double motionZ, Block block, int side, int metadata) { }
     
}
