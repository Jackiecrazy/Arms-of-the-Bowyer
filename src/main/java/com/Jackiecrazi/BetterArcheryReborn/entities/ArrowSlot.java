package com.Jackiecrazi.BetterArcheryReborn.entities;

import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

import com.Jackiecrazi.BetterArcheryReborn.BAR;
import com.Jackiecrazi.BetterArcheryReborn.Items.arrows.ItemQuiverModArrow;

public class ArrowSlot extends Slot {

	public ArrowSlot(IInventory par1iInventory, int par2, int par3, int par4) {
		super(par1iInventory, par2, par3, par4);
		
		setBackgroundIcon(ItemQuiverModArrow.emptySlotIIcon);
	}
	
	@Override
	public boolean isItemValid(ItemStack stack)
	{
		if (BAR.isArrow(stack))
			return true;
		
		return false;
	}

}
