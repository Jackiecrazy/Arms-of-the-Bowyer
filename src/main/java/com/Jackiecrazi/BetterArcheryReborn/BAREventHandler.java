package com.Jackiecrazi.BetterArcheryReborn;

import net.minecraft.util.StatCollector;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;

import com.Jackiecrazi.BetterArcheryReborn.Items.arrows.ItemQuiverModArrow;
import com.Jackiecrazi.BetterArcheryReborn.Items.arrows.PotionArrow;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;

public class BAREventHandler {
	@SubscribeEvent
	public void tooltip(ItemTooltipEvent e){
		if(e.itemStack.getItem() instanceof ItemQuiverModArrow){
			ItemQuiverModArrow arr=(ItemQuiverModArrow)e.itemStack.getItem();
			if(arr.getDamage(e.itemStack)==15){
				e.toolTip.add(StatCollector.translateToLocal(e.itemStack.getUnlocalizedName()+"ultimate"));
			}
		}
		else if(e.itemStack.getItem() instanceof PotionArrow){
			PotionArrow pot=(PotionArrow) e.itemStack.getItem();
			if(pot.getSplittingArrowCount(pot.getDamage(e.itemStack))==15){
				e.toolTip.add(StatCollector.translateToLocal(e.itemStack.getUnlocalizedName()+"ultimate"));
			}
		}
	}
}
