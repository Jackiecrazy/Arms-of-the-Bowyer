package com.Jackiecrazi.BetterArcheryReborn.helpful;

import java.util.ArrayList;
import java.util.HashMap;

import net.minecraft.item.ItemStack;

public class InventorySlots {
	
	private HashMap<Object, InventorySlot> slots = new HashMap(44);
	
	public InventorySlot get(Object key)
	{
		InventorySlot slot = slots.get(key);
		
		if (slot == null)
		{
			slot = new InventorySlot();
		}
		
		return slot;
	}
	
	public void set(Object key, InventorySlot slot)
	{
		slots.put(key, slot);
	}
	
	public HashMap<Object, InventorySlot> getSlots()
	{
		return (HashMap)slots.clone();
	}

}
