package com.Jackiecrazi.BetterArcheryReborn.Items;

import java.util.List;

import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import baubles.api.BaubleType;
import baubles.api.IBauble;

import com.Jackiecrazi.BetterArcheryReborn.BAR;
public class Accessories extends Item implements IBauble{
	public String[] stuff;
	public IIcon[] icons;
	public Accessories(){
		super();
		stuff=new String[]{"thumbring","fingertab"};//,"yugake","bracer"};
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
	@Override
	public BaubleType getBaubleType(ItemStack i) {
		return BaubleType.RING;
	}
	@Override
	public void onWornTick(ItemStack paramItemStack,
			EntityLivingBase paramEntityLivingBase) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void onEquipped(ItemStack is,
			EntityLivingBase elb) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void onUnequipped(ItemStack is,
			EntityLivingBase elb) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public boolean canEquip(ItemStack paramItemStack,
			EntityLivingBase paramEntityLivingBase) {
		return true;
	}
	@Override
	public boolean canUnequip(ItemStack paramItemStack,
			EntityLivingBase paramEntityLivingBase) {
		return true;
	}
}
