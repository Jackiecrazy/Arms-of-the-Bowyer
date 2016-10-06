package com.Jackiecrazi.BetterArcheryReborn.quivering;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraftforge.common.util.Constants;

import com.Jackiecrazi.BetterArcheryReborn.BAR;

public class QuiverInventory implements IInventory {

	public ItemStack[] inv;
	public InventoryPlayer playerInv;
	public int playerInvIndex;
	public static int size = 4;
	public static int stackLimit = 16;

	public QuiverInventory(InventoryPlayer ownerInv, int invIndex) {
		playerInv = ownerInv;
		playerInvIndex = invIndex;
		inv = new ItemStack[size];
		
		ItemStack stack = ownerInv.mainInventory[invIndex];
		getInventory(stack, false);
	}

	// Do not use for anything but getting arrow count!
	public QuiverInventory(ItemStack quiverStack) {
		inv = new ItemStack[size];
		getInventory(quiverStack, true);
	}
	
	private void getInventory(ItemStack quiverStack, boolean noUpdate)
	{
		if (quiverStack != null) {
			NBTTagCompound tagCompound = quiverStack.getTagCompound();
			if (tagCompound != null)
			{
				NBTTagList itemList = tagCompound.getTagList("quiver",Constants.NBT.TAG_COMPOUND);
				for (int i = 0; i < itemList.tagCount(); i++)
				{
					NBTTagCompound nbtStack = (NBTTagCompound)itemList.getCompoundTagAt(i);
					quiverStack = ItemStack.loadItemStackFromNBT(nbtStack);
					
					if (noUpdate)
						setInventorySlotContentsNoUpdate(nbtStack.getByte("Slot"), quiverStack);
					else
						setInventorySlotContents(nbtStack.getByte("Slot"), quiverStack);
				}
			}
		}
	}

	@Override
	public int getSizeInventory() {
		return inv.length;
	}
	
	public int getArrowCount() {
		int count = 0;
		
		for (ItemStack stack : inv)
		{
			if (stack != null && BAR.isArrow(stack))
			{
				count += stack.stackSize;
			}
		}
		
		return count;
	}
	
	public static int getMaxArrowCount() {
		return stackLimit * size;
	}

	@Override
	public ItemStack getStackInSlot(int slot) {
		return inv[slot];
	}

	public void setInventorySlotContentsNoUpdate(int slot, ItemStack stack) {
		inv[slot] = stack;
	}

	@Override
	public void setInventorySlotContents(int slot, ItemStack stack) {
		setInventorySlotContentsNoUpdate(slot, stack);
		this.markDirty();
	}

	@Override
	public ItemStack decrStackSize(int slot, int amt) {
		ItemStack stack = getStackInSlot(slot);

		if (stack != null) {
			if (stack.stackSize <= amt) {
				setInventorySlotContents(slot, null);
			} else {
				stack = stack.splitStack(amt);
				if (stack.stackSize == 0) {
					setInventorySlotContents(slot, null);
				}
			}
		}

		return stack;
	}

	@Override
	public ItemStack getStackInSlotOnClosing(int slot) {
		ItemStack stack = getStackInSlot(slot);

		if (stack != null) {
			setInventorySlotContents(slot, null);
			System.out.println("saved stack in slot "+slot);
		}

		return stack;
	}

	@Override
	public int getInventoryStackLimit() {
		return stackLimit;
	}

	@Override
	public boolean isUseableByPlayer(EntityPlayer player) {
		return true;
	}

	@Override
	public void markDirty()
	{
		for (int i = 0; i < size; i++)
		{
			ItemStack stack = getStackInSlot(i);
			
			if (stack != null && stack.stackSize <= 0)
			{
				inv[i] = null;
			}
		}
		
		NBTTagList quiverCompound = new NBTTagList();
		int i = 0;
		
		for (ItemStack item : inv)
		{
			NBTTagCompound itemCompound = new NBTTagCompound();

			if (item != null) {
				item.writeToNBT(itemCompound);
				itemCompound.setByte("Slot", (byte)i);
				quiverCompound.appendTag(itemCompound);
			}
			
			i++;
		}
		
		ItemStack quiverStack = playerInv.mainInventory[playerInvIndex];

		quiverStack.getTagCompound().setTag("quiver", quiverCompound);
		
		updateDamage(quiverStack);
	}
	
	public void updateDamage()
	{
		updateDamage(playerInv.mainInventory[playerInvIndex]);
	}
	
	public void updateDamage(ItemStack quiverStack)
	{
		int metadata = quiverStack.getItemDamage();
		metadata = Quiver.setDamage(metadata, getArrowCount());
		
		quiverStack.setItemDamage(metadata);
	}

	@Override
	public void openInventory() {}

	@Override
	public void closeInventory() {
	}

	@Override
	public String getInventoryName() {
		return "quiver.inventory";
	}

	@Override
	public boolean hasCustomInventoryName() {
		return false;
	}

	@Override
	public boolean isItemValidForSlot(int i, ItemStack itemstack) {
		return false;
	}
}
