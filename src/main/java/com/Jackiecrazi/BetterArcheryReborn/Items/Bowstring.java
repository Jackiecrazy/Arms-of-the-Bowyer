package com.Jackiecrazi.BetterArcheryReborn.Items;

import java.util.List;

import com.Jackiecrazi.BetterArcheryReborn.BAR;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.StatCollector;

public class Bowstring extends Item{
	public Bowstring(){
		this.setCreativeTab(BAR.BARBow);
		this.setUnlocalizedName("bowstring");
		this.setMaxStackSize(64);
	}
	 @cpw.mods.fml.relauncher.SideOnly(cpw.mods.fml.relauncher.Side.CLIENT)
	  public void addInformation(ItemStack par1ItemStack, EntityPlayer par2EntityPlayer, List par3List, boolean par4)
	  {
	    par3List.add(StatCollector.translateToLocal("item.bowstring.suffix"));
	  }
}
