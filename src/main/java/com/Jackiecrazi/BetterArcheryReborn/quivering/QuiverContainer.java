package com.Jackiecrazi.BetterArcheryReborn.quivering;

import java.util.List;

import com.Jackiecrazi.BetterArcheryReborn.entities.ArrowSlot;

import cpw.mods.fml.common.FMLCommonHandler;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.ICrafting;
import net.minecraft.inventory.InventoryBasic;
import net.minecraft.inventory.Slot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.*;

public class QuiverContainer extends Container {

	private ItemStack heldItem = null;
	private QuiverInventory quiverInventory;
	private InventoryPlayer playerInventory;
	
	public QuiverContainer(InventoryPlayer playerInv, QuiverInventory quiverInv) {
    	heldItem = playerInv.getCurrentItem();
    	playerInventory = playerInv;
    	
    	if (heldItem.stackTagCompound == null)
    		heldItem.setTagCompound(new NBTTagCompound());
		
    	// Quiver inventory
    	int x = 89 - ((18 * quiverInv.size) / 2);
    	
		for (int i = 0; i < quiverInv.size; i++) {
			addSlotToContainer(new ArrowSlot(quiverInv, i, x, 16));
			x += 18;
		}
		// End quiver inventory
		
		// Player inventory
        for (int i = 0; i < 3; i++)
        {
            for (int j = 0; j < 9; j++)
            {
            	int slotIndex = i * 9 + j + 9;
            	int slotPosX = 8 + j * 18;
            	int slotPosY = 66 + i * 18;
            	
            	//if (slotIndex != playerInv.currentItem)
            		addSlotToContainer(new Slot(playerInv, slotIndex, slotPosX, slotPosY));
            	/*else
            		addSlotToContainer(new SlotReadOnly(playerInv, slotIndex, slotPosX, slotPosY));*/
            }
	    }
	
	    for (int i = 0; i < 9; i++)
	    {
        	int slotPosX = 8 + i * 18;
        	int slotPosY = 124;

        	//if (i != playerInv.currentItem)
        		addSlotToContainer(new Slot(playerInv, i, slotPosX, slotPosY));
        	/*else
        		addSlotToContainer(new SlotReadOnly(playerInv, i, slotPosX, slotPosY));*/
	    }
	    // End player inventory
		
		quiverInventory = quiverInv;
	}

	@Override
	public boolean canInteractWith(EntityPlayer player) {
		boolean out = false;
		ItemStack curItem = player.getCurrentEquippedItem();
		
		if (curItem != null)
			if (curItem.stackTagCompound.getInteger("uniqueID") == heldItem.stackTagCompound.getInteger("uniqueID"))
				out = true;
		
		return out;
	}
	
	public List<ICrafting> getCrafters()
	{
		return crafters;
	}

    @Override
    public ItemStack transferStackInSlot(EntityPlayer player, int slotIndex) {
        ItemStack stackCopy = null;
        Slot slot = (Slot)inventorySlots.get(slotIndex);

        if (slot != null && slot.getHasStack())
        {
            ItemStack stack = slot.getStack();
            stackCopy = stack.copy();

            if (slotIndex < quiverInventory.size)
            {
                if (!mergeItemStack(stack, quiverInventory.size, inventorySlots.size(), true))	// Player inventory
                {
                    return null;
                }
            }
            else
            {
            	int itemsLeft = stack.stackSize;
            	int i = 0;
            	
            	while (itemsLeft > 0 && i < quiverInventory.size)
            	{
            		ItemStack stackInSlot = quiverInventory.getStackInSlot(i);
            		
            		if (stackInSlot != null && stackInSlot.stackSize < quiverInventory.stackLimit && stackInSlot.getItem() == stack.getItem() && stackInSlot.getItemDamage() == stack.getItemDamage())
            		{
            			int amount = quiverInventory.stackLimit - stackInSlot.stackSize;
            			
            			if (amount > itemsLeft)
            				amount = itemsLeft;
            			
            			stackInSlot.stackSize += amount; 
            			
            			itemsLeft -= amount;
            		}

        			i++;
            	}
            	
            	i = 0;
            	
            	while (itemsLeft > 0 && i < quiverInventory.size)
            	{
            		ItemStack stackInSlot = quiverInventory.getStackInSlot(i);
            		
            		if (stackInSlot == null)
            		{
            			if (getSlot(i).isItemValid(stack))
            			{
	            			ItemStack newStack = stack.copy();
	            			
	            			if (itemsLeft > quiverInventory.stackLimit)
	            				newStack.stackSize = quiverInventory.stackLimit;
	            			else
	            				newStack.stackSize = itemsLeft;
	            			
	            			itemsLeft -= newStack.stackSize;
	            			quiverInventory.setInventorySlotContents(i, newStack);
            			}
            		}

        			i++;
            	}

    			quiverInventory.markDirty();
    			playerInventory.markDirty();
            	
            	if (itemsLeft <= 0)
            	{
            		getSlot(slotIndex).putStack(null);
            	}
            	else
            	{
        			stack.stackSize = itemsLeft;
            	}
            	
            	return null;
            }

            if (stack.stackSize == 0)
            {
                slot.putStack((ItemStack)null);
            }
            else
            {
                slot.onSlotChanged();
            }
        }

        return stackCopy;
    }

	public void transferAllStacks() {
		for (int i = 0; i < quiverInventory.size; i++)
		{
			transferStackInSlot(null, i);
		}
	}
	
}
