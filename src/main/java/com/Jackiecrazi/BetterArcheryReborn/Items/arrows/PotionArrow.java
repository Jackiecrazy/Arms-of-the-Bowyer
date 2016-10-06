package com.Jackiecrazi.BetterArcheryReborn.Items.arrows;

import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.Jackiecrazi.BetterArcheryReborn.BAR;
import com.Jackiecrazi.BetterArcheryReborn.Items.ModItems;
import com.Jackiecrazi.BetterArcheryReborn.Items.SplittingArrowDefaults;
import com.Jackiecrazi.BetterArcheryReborn.helpful.LittleBittah;
import com.Jackiecrazi.BetterArcheryReborn.helpful.TextureSizeMap;
import com.google.common.collect.ImmutableList;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemPotion;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.potion.PotionHelper;
import net.minecraft.util.IIcon;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;

public class PotionArrow extends ItemPotion implements IArrowIcons, IArrowItem,ISplittingArrow {
	private int duration,potency;
	IIcon[] icons = new IIcon[4];
	Map effectToSubItemMap = new LinkedHashMap();
	Map effectToSplittingSubItemMap = new LinkedHashMap();
	public PotionArrow() {
		this.setMaxStackSize(64);
		ModItems.arrowItemList.add(this);
		setFull3D();
		setHasSubtypes(true);
		this.setCreativeTab(BAR.BARArrow);
	}
	
	public String getName()
    {
    	String unlocName = getUnlocalizedName();
    	return unlocName.substring(unlocName.lastIndexOf('.') + 1);
    }
	public float getDuration(){
		return duration;
	}
	public PotionArrow setDuration(int tickcount){
		this.duration=tickcount;
		return this;
	}
	public float getPotency(){
		return potency;
	}
	public PotionArrow setPotency(int tickcount){
		this.potency=tickcount;
		return this;
	}
	
	public void getSubItems(Item itemID, int metadata, CreativeTabs creativeTabs, List subItemList)
    {
        subItemList.add(new ItemStack(itemID, 1, metadata));
        //subItemList.add(new ItemStack(itemID, 1, getItemDamageForArrowCount(metadata, 4)));
    }
	
	public boolean getNeedsTwoPasses(){
		return true;
	}
	
	public IIcon getIconFromDamageForRenderPass(int damage, int pass)
    {
    	if (pass > 1 && isSplittingArrow(damage))
    		return ItemQuiverModArrow.splittingArrowIIcon;
    	
    	int index = 0;
    	
    	if (isSplash(damage))
    	{
    		index += 2;
    	}
    	
        return pass == 0 ? icons[index + 1] : icons[index];
    }
	
    public void getSubItems(Item itemID, CreativeTabs creativeTabs, List par3List)
    {
    	if (effectToSubItemMap.isEmpty())
        {
            for (int i = 0; i <= 32767; ++i)
            {
                List effects = PotionHelper.getPotionEffects(i, false);

                if (effects != null && !effects.isEmpty())
                {
                	if (!effectToSubItemMap.containsKey(effects))
                	{
                		effectToSubItemMap.put(effects, Integer.valueOf(i));
                		effectToSplittingSubItemMap.put(effects, Integer.valueOf(getItemDamageForArrowCount(i, 4)));
                	}
                }
            }
        }

        Iterator iter = effectToSubItemMap.values().iterator();

        while (iter.hasNext())
        {
            int damage = ((Integer)iter.next()).intValue();
            par3List.add(new ItemStack(itemID, 1, damage));
        }

        iter = effectToSplittingSubItemMap.values().iterator();

        while (iter.hasNext())
        {
            int damage = ((Integer)iter.next()).intValue();
            par3List.add(new ItemStack(itemID, 1, damage));
        }
    }
    @Override
	public void registerIcons(IIconRegister iconRegister)
	{
    	icons[0] = iconRegister.registerIcon("quivermod:potionarrowover");
		icons[1] = iconRegister.registerIcon("quivermod:potionarrowcolor");
		icons[2] = iconRegister.registerIcon("quivermod:potionarrowsplashover");
		icons[3] = iconRegister.registerIcon("quivermod:potionarrowsplashcolor");
	}

	public String[] getSpecialBowIconNames()
	{
		String name = getName().toLowerCase();
		
		return new String[]{name, "splash" + name};
	}
	@Override
	public int getInfinityChance() {
		return 2;
	}
	@Override
	public boolean canAddArrowHead(ItemStack stack) {
		return false;
	}
	@Override
	public int getArrowHeadID(ItemStack stack) {
		return stack.stackTagCompound.getInteger("arrowhead");
	}
	@Override
	public void setArrowHeadID(ItemStack stack, int id) {
		stack.stackTagCompound.setInteger("arrowhead", id);
	}
	@Override
	public float getFullBright() {
		return -1;
	}
	@Override
	public int getFrameCount() {
		return 1;
	}
	@SideOnly(Side.CLIENT)
    public int getColorFromItemStack(ItemStack par1ItemStack, int pass)
    {
        return pass != 0 ? 16777215 : getColorFromDamage(par1ItemStack.getItemDamage());
    }
	private static final int POTIONMASK = 16511;
	private static final int SPLITSTART = 7;
	private static final int SPLITEND = 10;
	@Override
	public int getItemDamageForArrowCount(int damage, int count)
	{
		damage &= POTIONMASK;
		
		if (count < 2)
			count = 0;
		
		return LittleBittah.setInteger(damage, SPLITSTART, SPLITEND, count);
	}
	//TODO remove seal when arrows split
	
	public String getItemDisplayName(ItemStack stack)
    {
        //return StringTranslate.getInstance().translateNamedKey(this.getLocalizedName(par1ItemStack)).trim();
    	String out = "";
    	
        if (stack.getItemDamage() == 0)
        {
            return StatCollector.translateToLocal(getUnlocalizedName()).trim();
        }
        else
        {
            String prefix = "";

            if (isSplash(stack.getItemDamage()))
            {
                prefix += StatCollector.translateToLocal("potion.prefix.grenade").trim() + " ";
            }

            List effects = Items.potionitem.getEffects(stack);

            if (effects != null && !effects.isEmpty())
            {
                String nameUnloc = ((PotionEffect)effects.get(0)).getEffectName();
                nameUnloc += ".postfix";
                out += prefix + StatCollector.translateToLocal(nameUnloc).trim();
            }
            else
            {
            	String nameUnloc = PotionHelper.func_77905_c(stack.getItemDamage());
                out += StatCollector.translateToLocal(nameUnloc).trim() + " " + Items.potionitem.getUnlocalizedName();
            }
        }
        
        int damage = stack.getItemDamage();
        out += " " + ArrowNameHelper.getName(Items.arrow, isSplittingArrow(damage));
        
        out = ArrowNameHelper.getLocalizedSplittingArrowPrefix(this, out, damage);
        
        return out;
    }
	public ItemStack onItemRightClick(ItemStack stack, World world, EntityPlayer player)
	{
		return stack;
	}
	public String getSpecialBowIconName(int damage)
	{
		String name=(isSplash(damage) ? "splash" : "") + getName().toLowerCase();
		return name;
		
	}
	public int getRenderPasses(int damage)
    {
        return isSplittingArrow(damage) ? 3 : 2;
    }

	@Override
	public int getSplittingArrowCount(int damage) {
		return LittleBittah.getInteger(damage, SPLITSTART, SPLITEND);
	}

	@Override
	public boolean isSplittingArrow(int damage) {
		// TODO Auto-generated method stub
		return SplittingArrowDefaults.isSplittingArrow(getSplittingArrowCount(damage));
	}

	@Override
	public boolean canCraftSplittingArrow(int damage) {
		// TODO Auto-generated method stub
		return true;
	}
}
