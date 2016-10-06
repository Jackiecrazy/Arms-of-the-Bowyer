package com.Jackiecrazi.BetterArcheryReborn;

import net.minecraft.client.Minecraft;
import net.minecraftforge.client.MinecraftForgeClient;

import com.Jackiecrazi.BetterArcheryReborn.CommonProxy;
import com.Jackiecrazi.BetterArcheryReborn.Items.*;
import com.Jackiecrazi.BetterArcheryReborn.lenders.WeirdRenderBowThing;

import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;

public class ClientProxy extends CommonProxy {

    @Override
    public void preInit(FMLPreInitializationEvent e) {
        super.preInit(e);
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
}
