package com.Jackiecrazi.BetterArcheryReborn.Items.arrows;

import java.util.List;

import com.Jackiecrazi.BetterArcheryReborn.BAR;
import com.Jackiecrazi.BetterArcheryReborn.Items.ModItems;
import com.Jackiecrazi.BetterArcheryReborn.Items.SplittingArrowDefaults;
import com.Jackiecrazi.BetterArcheryReborn.helpful.LittleBittah;
import com.Jackiecrazi.BetterArcheryReborn.helpful.TextureSizeMap;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.renderer.entity.RenderArrow;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.IIcon;
import net.minecraft.util.StatCollector;
import net.minecraft.util.StringTranslate;
import net.minecraft.world.World;

public class ItemQuiverModArrow extends Item implements IArrowIcons, IArrowItem, ISplittingArrow {
	
	public static IIcon emptySlotIIcon = null;
	public static IIcon splittingArrowIIcon = null;

	protected float fullBright = -1;
	protected int frameCount = 1;

	protected int infinityChance = 2;
	protected boolean canAddArrowHead = true;

	public ItemQuiverModArrow() {
		
		setFull3D();
		setHasSubtypes(true);
		this.setCreativeTab(BAR.BARArrow);
		ModItems.arrowItemList.add(this);
	}
	
	public ItemQuiverModArrow setInfinityChance(int chance)
	{
		infinityChance = chance;
		return this;
	}
	
	public int getInfinityChance()
	{
		return infinityChance;
	}
	
	public boolean canAddArrowHead(ItemStack stack)
	{
		return canAddArrowHead;
	}

	@Override
	public int getArrowHeadID(ItemStack stack) {
		return stack.stackTagCompound.getInteger("arrowhead");
	}
	
	@Override
	public void setArrowHeadID(ItemStack stack, int id)
	{
		stack.stackTagCompound.setInteger("arrowhead", id);
	}

	public String getSpecialBowIconName(int damage)
	{
		return getName().toLowerCase();
		
	}
	@Override
	public String[] getSpecialBowIconNames() {
		return new String[]{getSpecialBowIconName(0)};
	}
	
	public ItemQuiverModArrow setFullBright(float brightness)
	{
		fullBright = brightness;
		return this;
	}

	public float getFullBright()
	{
		return fullBright;
	}
	
	public ItemQuiverModArrow setFrameCount(int count)
	{
		frameCount = count;
		return this;
	}

	public int getFrameCount()
	{
		return frameCount;
	}

	public boolean getNeedsTwoPasses()
	{
		return false;
	}

	public static final int SPLITSTART = 0;
	public static final int SPLITEND = 3;
	
	@Override
	public int getSplittingArrowCount(int damage)
	{
		return LittleBittah.getInteger(damage, SPLITSTART, SPLITEND);
	}
	
	@Override
	public boolean isSplittingArrow(int damage)
	{
		return SplittingArrowDefaults.isSplittingArrow(getSplittingArrowCount(damage));
	}
	
	public int getItemDamageForArrowCount(int damage, int count)
	{
		if (count < 2)
			count = 0;
		
		return LittleBittah.setInteger(damage, SPLITSTART, SPLITEND, count);
	}
	
	public boolean canCraftSplittingArrow(int damage)
	{
		return true;
	}
	
    public String getItemDisplayName(ItemStack stack)
    {
		return ArrowNameHelper.getFullName(stack);
    }
    
    @SideOnly(Side.CLIENT)
    public boolean requiresMultipleRenderPasses()
    {
        return true;
    }
    
    public int getRenderPasses(int damage)
    {
        return isSplittingArrow(damage) ? 2 : 1;
    }

    @SideOnly(Side.CLIENT)
    public IIcon getIconFromDamageForRenderPass(int damage, int pass)
    {
        return pass > 0 && isSplittingArrow(damage) ? ItemQuiverModArrow.splittingArrowIIcon : itemIcon;
    }
    
    public String getName()
    {
    	String unlocName = getUnlocalizedName();
    	return unlocName.substring(unlocName.lastIndexOf('.') + 1);
    }
	
	@Override
	public void registerIcons(IIconRegister IIconRegister)
	{
		String needed="quivermod:";
		String name = needed+getName().toLowerCase();
		itemIcon = IIconRegister.registerIcon(name);
		System.out.println("registered arrow icon for "+this.getIconString());
		emptySlotIIcon = IIconRegister.registerIcon("quivermod:emptyarrowslot");
		splittingArrowIIcon = IIconRegister.registerIcon("quivermod:splittingarrowbundle");
		
		TextureSizeMap.clear();
	}

	/*@Override
    public boolean shouldRotateAroundWhenRendering()
    {
        return true;
    }*/
	
    public void getSubItems(Item itemID, int metadata, CreativeTabs creativeTabs, List subItemList)
    {
        subItemList.add(new ItemStack(itemID, 1, metadata));
        subItemList.add(new ItemStack(itemID, 1, getItemDamageForArrowCount(metadata, 4)));
    }
	
    public void getSubItems(Item itemID, CreativeTabs creativeTabs, List subItemList)
    {
        getSubItems(itemID, 0, creativeTabs, subItemList);
    }
    

}
