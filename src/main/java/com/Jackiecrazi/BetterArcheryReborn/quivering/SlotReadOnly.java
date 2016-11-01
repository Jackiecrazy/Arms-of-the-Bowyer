package com.Jackiecrazi.BetterArcheryReborn.quivering;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class SlotReadOnly extends Slot {

	public SlotReadOnly(IInventory par1iInventory, int par2, int par3, int par4) {
		super(par1iInventory, par2, par3, par4);
	}
	
	@Override
	public boolean canTakeStack(EntityPlayer player)
	{
		return false;
	}

	@Override
	public boolean isItemValid(ItemStack stack)
	{
		return false;
	}

	@Override
    public ItemStack decrStackSize(int par1)
    {
        return getStack();
    }

	@Override
    public void putStack(ItemStack stack) {}

}
