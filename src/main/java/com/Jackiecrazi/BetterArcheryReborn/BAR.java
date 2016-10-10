package com.Jackiecrazi.BetterArcheryReborn;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.MinecraftForge;

import com.Jackiecrazi.BetterArcheryReborn.Items.ModItems;
import com.Jackiecrazi.BetterArcheryReborn.Items.arrows.ItemQuiverModArrow;
import com.Jackiecrazi.BetterArcheryReborn.Items.arrows.PotionArrow;
import com.Jackiecrazi.BetterArcheryReborn.dumbpackets.FirstMessage;
import com.Jackiecrazi.BetterArcheryReborn.lenders.QuiverModTickHandler;

import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import cpw.mods.fml.relauncher.Side;

@Mod(modid = "BetterArcheryReborn", version = BAR.MODVER, name = "Better Archery Reborn")
public class BAR
{
	@Mod.Instance("BetterArcheryReborn")
	public static BAR inst;
	BAREventHandler eh=new BAREventHandler();
    public static final String MODID = "BetterArcheryReborn";
    public static final String MODVER = "0.0";
    public static SimpleNetworkWrapper net;
    @SidedProxy(clientSide="com.Jackiecrazi.BetterArcheryReborn.ClientProxy", serverSide="com.Jackiecrazi.BetterArcheryReborn.ServerProxy")
    public static CommonProxy proxy;
    QuiverModTickHandler h =new QuiverModTickHandler();
    public static CreativeTabs BARBow = new CreativeTabs("BABow"){
    @Override
    public Item getTabIconItem() {
    	return ModItems.Longbow;
    	}
    };
    public static CreativeTabs BARArrow = new CreativeTabs("BAArrow"){
        @Override
        public Item getTabIconItem() {
        	return Items.arrow;
        	}
        };
    @EventHandler
    public void preinit(FMLPreInitializationEvent event)
    {
    	net=NetworkRegistry.INSTANCE.newSimpleChannel("BARnet");
    	this.proxy.preInit(event);
    	FMLCommonHandler.instance().bus().register(h);
    	MinecraftForge.EVENT_BUS.register(h);
    	FMLCommonHandler.instance().bus().register(eh);
    	MinecraftForge.EVENT_BUS.register(eh);
    }
    @Mod.EventHandler
    public void init(FMLInitializationEvent event)
    {
    	this.proxy.init(event);
    	ClientProxy.initLenders();
    }
    @EventHandler
    public void postinit(FMLPostInitializationEvent event)
    {
    	this.proxy.postInit(event);
    }
	public static void log(String string) {
		// TODO Auto-generated method stub
		System.out.println(string);
	}
	public static boolean isArrow(Item item)
	{
		return item == Items.arrow || item instanceof ItemQuiverModArrow || item instanceof PotionArrow;
	}
	
	public static boolean isArrow(ItemStack itemStack)
	{
		return isArrow(itemStack.getItem());
	}
}
