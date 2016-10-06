package com.Jackiecrazi.BetterArcheryReborn.quivering;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.Jackiecrazi.BetterArcheryReborn.BAR;
import com.Jackiecrazi.BetterArcheryReborn.helpful.ColorThing;
import com.Jackiecrazi.BetterArcheryReborn.helpful.LittleBittah;

import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemDye;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.*;
import net.minecraft.util.IIcon;
import net.minecraft.util.StatCollector;
import net.minecraft.util.StringTranslate;
import net.minecraft.world.World;

public class Quiver extends Item {
	
	public static List<Integer> ids = new ArrayList<Integer>();
	public static int frames = 6;

	public static final int maxDamage = QuiverInventory.getMaxArrowCount();
	public static final int emptyMetadata = setDamage(0, 0);
	
	private IIcon[] icons;
	private IIcon strapIcon;
	private int passes = 3;
	private boolean alreadyMade = false;

	public Quiver()
	{
		super();
		
		setMaxStackSize(1);
		setCreativeTab(BAR.BARArrow);
		setUnlocalizedName("quiver");
		setMaxDamage(0);
		
		GameRegistry.addRecipe(new ItemStack(this, 1, emptyMetadata),
				"lsl",
				" l ",
				'l', Items.leather,
				's', Items.string);
		
		int craftingMetadata = setColor(emptyMetadata, 16);
		
		for (int i = 0; i < 16; i++)
		{
			GameRegistry.addRecipe(new ItemStack(this, 1, craftingMetadata),
					"dqd",
					" d ",
					'q', new ItemStack(this, 1, setColor(emptyMetadata, i)),
					'd', new ItemStack(Items.dye, 1, 15));
		}
		
		for (int i = 0; i < 15; i++)
		{
			int colorMetadata = setColor(emptyMetadata, i + 1);
			GameRegistry.addRecipe(new ItemStack(this, 1, colorMetadata),
					"dqd",
					" d ",
					'q', new ItemStack(this, 1, craftingMetadata),
					'd', new ItemStack(Items.dye, 1, i));
		}
	}

	private static final int DMG_START = 0;
	private static final int DMG_END = 7;

	public static int setDamage(int metadata, int damage) {
		return LittleBittah.setInteger(metadata, DMG_START, DMG_END, damage);
	}

	public static int getDamage(int metadata) {
		return LittleBittah.getInteger(metadata, DMG_START, DMG_END);
	}

	private static final int COLOR_START = 10;
	private static final int COLOR_END = 14;

	public static int setColor(int metadata, int color) {
		return LittleBittah.setInteger(metadata, COLOR_START, COLOR_END, color);
	}

	public static int getColor(int metadata) {
		int color = LittleBittah.getInteger(metadata, COLOR_START, COLOR_END);
		return color;
	}

	@Override
	public void registerIcons(IIconRegister iconRegister)
	{
		icons = new IIcon[6];
		String mod = "quivermod:";
		String name = mod + getUnlocalizedName().split("\\.")[1];
		icons[0] = iconRegister.registerIcon(name + "5");
		icons[1] = iconRegister.registerIcon(name + "4");
		icons[2] = iconRegister.registerIcon(name + "3");
		icons[3] = iconRegister.registerIcon(name + "2");
		icons[4] = iconRegister.registerIcon(name + "1");
		icons[5] = iconRegister.registerIcon(name + "0");
		itemIcon = iconRegister.registerIcon(name + "color");
		strapIcon = iconRegister.registerIcon(name + "strap");
	}
	
	@Override
	public boolean requiresMultipleRenderPasses()
	{
		return true;
	}

	@Override
    public int getRenderPasses(int metadata)
    {
    	return passes;
    }
	
	@Override
	public IIcon getIconFromDamageForRenderPass(int metadata, int pass)
	{
		if (pass == 0)
		{
			int lastFrame = frames - 1;
			int damage = getDamage(metadata);
			float fraction = damage / (float)maxDamage;
			
			int frame = (int)(fraction * (lastFrame - 1)) + 1;
			
			if (damage >= maxDamage)
				frame = lastFrame;
			else if (damage <= 0)
				frame = 0;
			
			return icons[(int)frame];
		}
		
		if (pass == passes - 1)
			return strapIcon;
		
		return itemIcon;
	}
	
	HashMap<Integer, Integer> quiverColors = new HashMap(){{
		put(0, 16741956);
	}};
	
    public int getColorFromItemStack(ItemStack stack, int pass)
    {
    	if (pass != 0 && pass != passes - 1)
    	{
	    	int damage = stack.getItemDamage();
	    	int colorIndex = getColor(damage);
	    	
    		Integer color = quiverColors.get(colorIndex);
    		
    		if (color == null)
    		{
		    	color = ItemDye.field_150922_c[colorIndex - 1];
		    	
		    	int red = ColorThing.getRed(color);
		    	int green = ColorThing.getGreen(color);
		    	int blue = ColorThing.getBlue(color);
		    	float avg = (float)(red + green + blue) / 3;
		    	red = (int)((red - avg) / 0.9F + avg);
		    	green = (int)((green - avg) / 0.9F + avg);
		    	blue = (int)((blue - avg) / 0.9F + avg);
		    	red /= 0.95F;
		    	green /= 0.95F;
		    	blue /= 0.95F;
		    	
		    	color = ColorThing.getColor(red, green, blue);
		    	quiverColors.put(colorIndex, color);
    		}
    		
	    	return color;
    	}
    	
        return 16777215;
    }

    public String getItemDisplayName(ItemStack stack)
    {
    	String out = super.getItemStackDisplayName(stack);
    	int color = getColor(stack.getItemDamage()) - 1;
    	
    	//if (color >= 0)
    		//out = StatCollector.translateToLocal("item.fireworksCharge." + ItemDye.dyeColorNames[color]) + " " + out;
    	
        return out;
    }
	
	private boolean addID(int uniqueID)
	{
		if (!ids.contains(uniqueID))
		{
			ids.add(uniqueID);
			
			return true;
		}
		
		return false;
	}
	
	private static int findUniqueID()
	{
		int i = 0;
		
		while (true)
		{
			if (!ids.contains(i))
			{
				return i;
			}
			
			i++;
		}
	}
	
	public int getUniqueID(ItemStack stack)
	{
		NBTTagCompound tagCompound = stack.getTagCompound();
		
		if (tagCompound == null)
			tagCompound = new NBTTagCompound();
		
		if (!tagCompound.hasKey("uniqueID"))
		{
			int uniqueID = findUniqueID();
			tagCompound.setInteger("uniqueID", uniqueID);
			ids.add(uniqueID);
			
			stack.setTagCompound(tagCompound);
		}
		else
		{
			int uniqueID = tagCompound.getInteger("uniqueID");
			addID(uniqueID);
		}
		
		return tagCompound.getInteger("uniqueID");
	}
	
	public void onUpdate(ItemStack stack, World world, Entity entity, int par4, boolean par5)
	{
		getUniqueID(stack);
	}
	
	public static int getArrowCount(ItemStack stack)
	{
		return new QuiverInventory(stack).getArrowCount();
	}
	
	public static void removeArrow(EntityPlayer player, ItemStack quiver, int quiverIndex, int arrowIndex)
	{
		QuiverInventory inv = new QuiverInventory(player.inventory, quiverIndex);
		
		ItemStack arrowStack = inv.getStackInSlot(arrowIndex);
		arrowStack.stackSize--;
		
		if (arrowStack.stackSize <= 0)
			arrowStack = null;
		
		inv.setInventorySlotContents(arrowIndex, arrowStack);
	}
	
    public ItemStack onItemRightClick(ItemStack stack, World world, EntityPlayer player)
    {
    	player.openGui(BAR.inst, 0, world, 0, 0, 0);
        return stack;
    }
    
    public void getSubItems(Item id, CreativeTabs par2CreativeTabs, List list)
    {
    	for (int i = 0; i < 17; i++)
    	{
    		list.add(new ItemStack(id, 1, setColor(emptyMetadata, i)));
    	}
    }

}
