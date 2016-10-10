package com.Jackiecrazi.BetterArcheryReborn.crafting;

import com.Jackiecrazi.BetterArcheryReborn.Items.ModItems;

import net.minecraft.init.Items;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.world.World;

public class RecipesRecurveBow implements IRecipe {

	ItemStack dummyOutput = null;

	@Override
	public boolean matches(InventoryCrafting inventoryCrafting, World world)
	{
		return getCraftingResult(inventoryCrafting) != null;
	}

	@Override
	public ItemStack getCraftingResult(InventoryCrafting inventoryCrafting)
	{
		int stackCount = 0;
		
		ItemStack bow = null;
		
		for (int i = 0; i < inventoryCrafting.getSizeInventory(); i++)
		{
			ItemStack stack = inventoryCrafting.getStackInSlot(i);
			
			if (stack != null)
			{
				if (stack.getItem() == Items.bow)
				{
					bow = stack;
				}
				
				stackCount++;
			}
		}
		
		if (bow != null && stackCount == 1 && bow.getItemDamage() < ModItems.Recurve.getMaxDamage())
		{
			ItemStack output = bow.copy();
			output = new ItemStack(ModItems.Recurve,1,bow.getItemDamage());
			
			return output;
		}
		
		return null;
	}

	@Override
	public int getRecipeSize()
	{
		return 9;
	}

	@Override
	public ItemStack getRecipeOutput()
	{
		if (dummyOutput == null)
			dummyOutput = new ItemStack(ModItems.Recurve);
		
		return dummyOutput;
	}

}
