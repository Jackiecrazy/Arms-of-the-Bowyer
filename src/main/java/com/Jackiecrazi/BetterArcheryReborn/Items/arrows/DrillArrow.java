package com.Jackiecrazi.BetterArcheryReborn.Items.arrows;

import java.util.List;

import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.util.StatCollector;

import com.Jackiecrazi.BetterArcheryReborn.helpful.LittleBittah;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class DrillArrow extends ItemQuiverModArrow {
	//TODO make drill arrow use damage instead of unknown bit stuff
	public IIcon drillHeadIcon;

	public DrillArrow() {
		super();
	}
	
	@Override
	public void registerIcons(IIconRegister iconRegister)
	{
		super.registerIcons(iconRegister);
		
		drillHeadIcon = iconRegister.registerIcon("quivermod:drillarrowhead");
	}
	
	public static final int BROKEN = 4;
	
	public boolean isBroken(int damage)
	{
		return LittleBittah.getBoolean(damage, BROKEN);
	}
	
	public int setBroken(int damage, boolean broken)
	{
		return LittleBittah.setBoolean(damage, BROKEN, broken);
	}
	
    public String getItemDisplayName(ItemStack stack)
    {
        return (isBroken(stack.getItemDamage()) ? StatCollector.translateToLocal("quiverModArrow.prefix.broken") + " " : "") + super.getItemDisplayName(stack);
    }
	
	public boolean canCraftSplittingArrow(int damage)
	{
		return !isBroken(damage);
	}

    @SideOnly(Side.CLIENT)
    public IIcon getIconFromDamageForRenderPass(int damage, int pass)
    {
    	if (pass == 0)
    		return itemIcon;
    	
    	if (pass == 1 && !isBroken(damage))
    		return drillHeadIcon;
    	else
    		return splittingArrowIIcon;
    }
    
    public boolean requiresMultipleRenderPasses()
    {
        return true;
    }
    
    public int getRenderPasses(int damage)
    {
        return super.getRenderPasses(damage) + (isBroken(damage) ? 0 : 1);
    }
    @cpw.mods.fml.relauncher.SideOnly(cpw.mods.fml.relauncher.Side.CLIENT)
	  public void addInformation(ItemStack par1ItemStack, EntityPlayer par2EntityPlayer, List par3List, boolean par4)
	  {
	    if(isBroken(par1ItemStack.getItemDamage())){
	    	par3List.add("Broken");
	    }
	    else par3List.remove("Broken");
	  }
}
