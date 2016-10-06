package com.Jackiecrazi.BetterArcheryReborn.helpful;

import java.util.HashMap;

import com.Jackiecrazi.BetterArcheryReborn.ClientProxy;
import com.Jackiecrazi.BetterArcheryReborn.Items.ModItems;
import com.Jackiecrazi.BetterArcheryReborn.Items.arrows.IArrowIcons;

import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;

public class BowArrowIcons {

	public static HashMap<String, HashMap<String, Object>> iconArrayMap = new HashMap();
	public static HashMap<String, Float> fullBrightMap = new HashMap();
	public static boolean registered = false;

	public static void register(String bowName, String specialArrowName, IIconRegister iconRegister,
			int frames)
	{
		String name = "quivermod:bowarrowicons/" + bowName;
		
		HashMap<String, Object> specialArrowMap = iconArrayMap.get(bowName);
		
		if (specialArrowMap == null)
			specialArrowMap = new HashMap();
		
		if (frames > 1)
		{
			IIcon[] arrowIcons = new IIcon[4];
			
			for (int i = 0; i < arrowIcons.length; i++)
			{
				arrowIcons[i] = iconRegister.registerIcon(name + specialArrowName + i);
			}
			
			specialArrowMap.put(specialArrowName, arrowIcons);
		}
		else
		{
			IIcon arrowIcon = iconRegister.registerIcon(name + specialArrowName);
			specialArrowMap.put(specialArrowName, arrowIcon);
		}
		
		iconArrayMap.put(bowName, specialArrowMap);
	}

	public static void registerArrowIcons(String bowName, IIconRegister iconRegister)
	{
		for (Item arrowItem : ModItems.arrowItemList)
		{
			if (arrowItem instanceof IArrowIcons)
			{
				IArrowIcons arrowIcons = (IArrowIcons)arrowItem;
				String[] specialArrowNames = arrowIcons.getSpecialBowIconNames();
				for (int i = 0; i < specialArrowNames.length; i++)
				{
					String specialArrowName = specialArrowNames[i];
					
					register(bowName, specialArrowName, iconRegister, arrowIcons.getFrameCount());
					
					if (arrowIcons.getNeedsTwoPasses())
						register(bowName, specialArrowName + "1", iconRegister, arrowIcons.getFrameCount());
					
					fullBrightMap.put(specialArrowName, arrowIcons.getFullBright());
				}
			}
		}
		
		register(bowName, "splittingarrow", iconRegister, 1);
	}
	
	public static IIcon getIcon(String bowName, ItemStack arrow, int iconOffset, int pass, boolean splitting)
	{
		ClientProxy.mc.mcProfiler.startSection("getArrowIcon");
		IIcon output = null;
		
		if (arrow.getItem() instanceof IArrowIcons)
		{
			IArrowIcons arrowIcons = (IArrowIcons)arrow.getItem();
			
			String specialArrowName = arrowIcons.getSpecialBowIconName(arrow.getItemDamage());
			HashMap<String, Object> bowArrowIconMap = iconArrayMap.get(bowName);
			Object icons = null;
			switch (pass)
			{
			case 0:
			case 1:
				icons = bowArrowIconMap.get(specialArrowName + (pass > 0 ? pass : ""));
				break;
			case 2:
				if (splitting)
					icons = bowArrowIconMap.get("splittingarrow");
				break;
			}
			
			if (icons != null)
			{
				if (icons instanceof IIcon)
				{
					output = (IIcon)icons;
				}
				else if (icons instanceof IIcon[])
				{
					IIcon[] iconsArray = (IIcon[])icons;
					int index = iconOffset >= iconsArray.length ? iconsArray.length - 1 : iconOffset;
					output = iconsArray[index];
				}
			}
		}
		
		ClientProxy.mc.mcProfiler.endSection();
		return output;
	}
	public static IIcon arrow(String bowName, String s, int iconOffset, int pass, boolean splitting)
	{
		ClientProxy.mc.mcProfiler.startSection("getArrowIcon");
		IIcon output = null;
			
			String specialArrowName = s;
			HashMap<String, Object> bowArrowIconMap = iconArrayMap.get(bowName);
			Object icons = null;
			switch (pass)
			{
			case 0:
			case 1:
				icons = bowArrowIconMap.get(specialArrowName + (pass > 0 ? pass : ""));
				break;
			case 2:
				if (splitting)
					icons = bowArrowIconMap.get("splittingarrow");
				break;
			}
			
			if (icons != null)
			{
				if (icons instanceof IIcon)
				{
					output = (IIcon)icons;
				}
				else if (icons instanceof IIcon[])
				{
					IIcon[] iconsArray = (IIcon[])icons;
					int index = iconOffset >= iconsArray.length ? iconsArray.length - 1 : iconOffset;
					output = iconsArray[index];
				}
			}
			ClientProxy.mc.mcProfiler.endSection();
			return output;
		}
	public static float getFullBright(String bowName, String itemArrowName)
	{
		if (fullBrightMap.containsKey(itemArrowName))
		{
			return fullBrightMap.get(itemArrowName);
		}
		
		return -1;
	}

}
