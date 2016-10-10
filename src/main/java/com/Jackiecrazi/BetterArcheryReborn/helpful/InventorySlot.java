package com.Jackiecrazi.BetterArcheryReborn.helpful;

import net.minecraft.item.ItemStack;

public class InventorySlot {

	public ItemStack stack = null;
	public int index = -1;

	public InventorySlot()
	{
	}
	
	public InventorySlot(ItemStack stack, int index)
	{
		this.stack = stack;
		this.index = index;
	}

}
