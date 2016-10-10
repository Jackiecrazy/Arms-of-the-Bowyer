package com.Jackiecrazi.BetterArcheryReborn;

import net.minecraft.client.Minecraft;
import net.minecraftforge.client.MinecraftForgeClient;
import net.minecraftforge.common.MinecraftForge;
import api.player.render.RenderPlayerAPI;

import com.Jackiecrazi.BetterArcheryReborn.Items.ModItems;
import com.Jackiecrazi.BetterArcheryReborn.dumbpackets.FirstMessage;
import com.Jackiecrazi.BetterArcheryReborn.lenders.QuiverModOverlayRenderer;
import com.Jackiecrazi.BetterArcheryReborn.lenders.QuiverRenderPlayerBase;
import com.Jackiecrazi.BetterArcheryReborn.lenders.WeirdRenderBowThing;

import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.relauncher.Side;

public class ClientProxy extends CommonProxy {

    @Override
    public void preInit(FMLPreInitializationEvent e) {
        super.preInit(e);
        
    		try
    		{
    			RenderPlayerAPI.register("quivermod", QuiverRenderPlayerBase.class);
    		}
    		catch (NoClassDefFoundError a)
    		{
    			BAR.log("RenderPlayer API not found. Quivers will not be rendered on players.");
    		}
    		
    		makeSettingsGui();
    		
    		MinecraftForge.EVENT_BUS.register(new QuiverModOverlayRenderer());
    		new BARBinds();
        mc=Minecraft.getMinecraft();
    }

    @Override
    public void init(FMLInitializationEvent e) {
        super.init(e);
    }

    @Override
    public void postInit(FMLPostInitializationEvent e) {
        super.postInit(e);
    }
    public static void initLenders(){
    	MinecraftForgeClient.registerItemRenderer(ModItems.Longbow, new WeirdRenderBowThing());
    	MinecraftForgeClient.registerItemRenderer(ModItems.Recurve, new WeirdRenderBowThing());
    	MinecraftForgeClient.registerItemRenderer(ModItems.Yumi, new WeirdRenderBowThing());
    	MinecraftForgeClient.registerItemRenderer(ModItems.Comp, new WeirdRenderBowThing());
    	
    }
    public void makeSettingsGui()
	{
		/*try
		{
			ModSettingScreen modSettingScreen = new ModSettingScreen("Better Bows Settings (R: restart, C: client)", "Better Bows");
			QuiverModSettings modSettings = new QuiverModSettings(QuiverMod.ID, modSettingScreen);
			modSettingsContainer = new ContainModSettings(modSettings, modSettingScreen);
		}
		catch (NoClassDefFoundError e)
		{
			
		}*/
	}
}
