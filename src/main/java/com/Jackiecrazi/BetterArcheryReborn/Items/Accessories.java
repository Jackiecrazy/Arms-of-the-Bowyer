package com.Jackiecrazi.BetterArcheryReborn.Items;

import java.util.List;

import com.Jackiecrazi.BetterArcheryReborn.BAR;

import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;

public class Accessories extends Item{
	public String[] stuff;
	public IIcon[] icons;
	public Accessories(){
		super();
		stuff=new String[]{"thumbring","fingertab","yugake","bracer"};
		this.setCreativeTab(BAR.BARBow);
		this.hasSubtypes=true;
	}
	public void getSubItems(Item itemID, CreativeTabs tabs, List list)
    {
        for (int i = 0; i < stuff.length; i++)
        {
            list.add(new ItemStack(itemID, 1, i));
        }
    }
	@Override
	public int getItemStackLimit()
	{
		return 64;
	}
	
	@Override
	public String getUnlocalizedName(ItemStack stack)
	{
		if (stack.getItemDamage() >= stuff.length)
		{
			return "";
		}
		
		return "item." + stuff[stack.getItemDamage()];
	}
    
    @Override
    public void registerIcons(IIconRegister iconRegister)
    {
        icons = new IIcon[stuff.length];
        
        for (int i = 0; i < icons.length; i++)
        {
        	icons[i] = iconRegister.registerIcon("quivermod:" + stuff[i].toLowerCase());
        }
    }
	
	@Override
    public IIcon getIconFromDamage(int damage)
    {
		if (damage >= icons.length)
		{
			return icons[0];
		}
		
        return icons[damage];
    }
}
