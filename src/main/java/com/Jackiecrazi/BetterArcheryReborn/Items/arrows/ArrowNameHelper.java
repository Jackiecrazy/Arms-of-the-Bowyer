package com.Jackiecrazi.BetterArcheryReborn.Items.arrows;

import com.Jackiecrazi.BetterArcheryReborn.BAR;

import cpw.mods.fml.common.registry.LanguageRegistry;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.StatCollector;
import net.minecraft.util.StringTranslate;

public class ArrowNameHelper {
	
	private static String prevLanguage = null;
	private static int useSplittingPlural = -1;
	
	public static boolean getUseSplittingPlural()
	{
		String curLanguage = BAR.proxy.mc.getLanguageManager().getCurrentLanguage().getLanguageCode();
		
		if (useSplittingPlural == -1 || prevLanguage != curLanguage)
		{
			prevLanguage = curLanguage;
			useSplittingPlural = StatCollector.translateToLocal("quiverModArrow.splitting.usePlural").equals("true") ? 1 : 0;
		}
		
		return useSplittingPlural == 1;
	}
	
	public static String getName(Item item, String unlocName, boolean multipleArrows)
	{
		unlocName += ".name";
		
		if (multipleArrows && getUseSplittingPlural())
			unlocName += ".plural";
		
		String out = StatCollector.translateToLocal(unlocName).trim();
		
		return out;
	}
	
	public static String getName(Item item, boolean multipleArrows)
	{
		return getName(item, item.getUnlocalizedName(), multipleArrows);
	}
	
	public static String getName(ItemStack stack)
	{
		Item stackItem = stack.getItem();
		boolean multipleArrows = stackItem instanceof ISplittingArrow && ((ISplittingArrow)stackItem).isSplittingArrow(stack.getItemDamage());
		return getName(stackItem, stackItem.getUnlocalizedName(stack), multipleArrows);
	}

	public static String getLocalizedSplittingArrowPrefix(ISplittingArrow splittingArrow, String arrowName, int damage)
	{
		if (splittingArrow.isSplittingArrow(damage))
		{
			return StatCollector.translateToLocal("quiverModArrow.splitting.prefix.beforeNum") +
					splittingArrow.getSplittingArrowCount(damage) +
					StatCollector.translateToLocal("quiverModArrow.splitting.prefix.afterNum") + " " +
					arrowName +
					StatCollector.translateToLocal("quiverModArrow.splitting.postfix");
		}
		
		return arrowName;
	}
	
	public static String getFullName(ItemStack stack)
	{
		String name = ArrowNameHelper.getName(stack);
		Item stackItem = stack.getItem();
		
		if (stackItem instanceof ISplittingArrow)
			name = ArrowNameHelper.getLocalizedSplittingArrowPrefix((ISplittingArrow)stack.getItem(), name, stack.getItemDamage());
		
		return name;
	}
	
}
