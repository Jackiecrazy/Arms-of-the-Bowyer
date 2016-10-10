package com.Jackiecrazi.BetterArcheryReborn.Items;

import java.util.ArrayList;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

import com.Jackiecrazi.BetterArcheryReborn.Items.arrows.DrillArrow;
import com.Jackiecrazi.BetterArcheryReborn.Items.arrows.ItemQuiverModArrow;
import com.Jackiecrazi.BetterArcheryReborn.Items.arrows.PotionArrow;
import com.Jackiecrazi.BetterArcheryReborn.quivering.Quiver;

import cpw.mods.fml.common.registry.GameRegistry;

public class ModItems {
	public static ArrayList<Item> arrowItemList = new ArrayList<Item>(){{
	}};
	public static Item Bowstring=new Bowstring().setTextureName("string");
	public static Item Longbow=new Longbow().setTextureName("quivermod:quiverbowlong");
	public static Item Recurve=new Recurve().setTextureName("quivermod:quiverbowrecurve");
	public static Item Yumi=new Yumi().setTextureName("quivermod:quiverbowyumi");
	public static Item Comp=new Composite().setTextureName("quivermod:quiverbowcomposite");
	public static Item FireArrow=new ItemQuiverModArrow().setFrameCount(3).setUnlocalizedName("firearrow").setTextureName("quivermod:firearrow");
	public static Item DerpArrow=new ItemQuiverModArrow().setUnlocalizedName("arrow").setTextureName("quivermod:arrow");
	public static Item PotArrow=new PotionArrow().setUnlocalizedName("potionarrow").setTextureName("quivermod:potionarrow");
	public static Item EnderArrow=new ItemQuiverModArrow().setUnlocalizedName("enderarrow").setTextureName("quivermod:enderarrow");
	public static Item ImpactArrow=new ItemQuiverModArrow().setUnlocalizedName("impactarrow").setTextureName("quivermod:impactarrow");
	public static Item TimedArrow=new ItemQuiverModArrow().setUnlocalizedName("timedarrow").setTextureName("quivermod:timedarrow");
	public static Item SplashArrow=new ItemQuiverModArrow().setUnlocalizedName("splashpotionarrow").setTextureName("quivermod:splashpotionarrow");
	public static Item DrillArrow=new DrillArrow().setUnlocalizedName("drillarrow").setTextureName("quivermod:drillarrow");
	public static Item TorchArrow=new ItemQuiverModArrow().setUnlocalizedName("torcharrow").setTextureName("quivermod:torcharrow");
	public static Item onemisc=new MiscItemsWithSizeOne();
	public static Item misc=new MiscItems();
	public static Item acc=new Accessories();
	public static Quiver quiver=new Quiver();
	
	public static void itemify(){
		GameRegistry.registerItem(Bowstring, "bowstring");
		GameRegistry.registerItem(Longbow, "longbow");
		GameRegistry.registerItem(Recurve,"recurve");
		GameRegistry.registerItem(Yumi, "yumi");
		GameRegistry.registerItem(Comp, "composite");
		GameRegistry.registerItem(FireArrow, "firearrow");
		GameRegistry.registerItem(DerpArrow, "herparrow");
		GameRegistry.registerItem(EnderArrow, "enderarrow");
		GameRegistry.registerItem(DrillArrow, "drillarrow");
		GameRegistry.registerItem(TimedArrow, "timedarrow");
		GameRegistry.registerItem(ImpactArrow, "impactarrow");
		GameRegistry.registerItem(TorchArrow, "torcharrow");
		GameRegistry.registerItem(PotArrow, "potarrow");
		GameRegistry.registerItem(quiver, "quiver");
		GameRegistry.registerItem(misc, "quivermisc");
		GameRegistry.registerItem(onemisc, "onemisc");
		GameRegistry.registerItem(acc, "quiveraccessories");
	}
	public static boolean arrayItemStackContains(ArrayList<ItemStack> list, ItemStack findStack)
	{
		for (ItemStack stack : list)
		{
			if (stack.isItemEqual(findStack))
			{
				return true;
			}
		}
		
		return false;
	}
}
