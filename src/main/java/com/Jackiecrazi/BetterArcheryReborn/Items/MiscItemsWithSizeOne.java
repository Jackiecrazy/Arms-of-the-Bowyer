package com.Jackiecrazi.BetterArcheryReborn.Items;

import java.util.ArrayList;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.FurnaceRecipes;
import net.minecraft.util.IIcon;
import cpw.mods.fml.common.registry.GameRegistry;

public class MiscItemsWithSizeOne extends MiscItems {
	private ArrayList<Integer> bottledItems = new ArrayList(){{
    	add(0);
    	add(1);
    }};
	public MiscItemsWithSizeOne(){
		super();
		this.names=new String[]{"bottledrottenflesh", "hideglue"};
        setHasSubtypes(true);
        setMaxDamage(0);
        setCreativeTab(CreativeTabs.tabMaterials);
	}
			
	@Override
	public int getItemStackLimit()
	{
		return 1;
	}
	@Override
    public boolean requiresMultipleRenderPasses()
    {
    	return true;
    }
    
    @Override
    public int getRenderPasses(int damage)
    {
    	if (bottledItems.contains(damage))
    		return 2;
    	
    	return 1;
    }
    
    @Override
    public IIcon getIconFromDamageForRenderPass(int damage, int pass)
    {
    	if (bottledItems.contains(damage) && pass == 1)
    	{
    		return Items.glass_bottle.getIconFromDamage(0);
    	}
    	
    	return super.getIconFromDamageForRenderPass(damage, pass);
    }
}
