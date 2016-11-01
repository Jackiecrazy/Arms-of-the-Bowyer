package com.Jackiecrazi.BetterArcheryReborn.helpful;

import java.util.ArrayList;
import java.util.HashMap;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import baubles.common.lib.PlayerHandler;

import com.Jackiecrazi.BetterArcheryReborn.BAR;
import com.Jackiecrazi.BetterArcheryReborn.Items.ModItems;
import com.Jackiecrazi.BetterArcheryReborn.quivering.QuiverInventory;
import com.google.common.collect.ObjectArrays;

import cpw.mods.fml.common.Loader;

public class RepetitiveSnippets {
	/*public static ArrayList<ItemStack> getQuivers(EntityPlayer player)
	{
		ArrayList<ItemStack> quiverStacks = new ArrayList();
		
		for (ItemStack stack : player.inventory.mainInventory)
		{
			if (stack != null && stack.itemID == QuiverMod.quiver.itemID)
			{
				quiverStacks.add(stack);
			}
		}
		
		return quiverStacks;
	}
    
    public ArrayList<ItemStack> getArrowTypesHeld(EntityPlayer player)
    {
    	ArrayList<ItemStack> quivers = getQuivers(player);
    	
    	ArrayList<ItemStack> arrows = new ArrayList<ItemStack>();
    	
    	for (ItemStack stack : quivers)
    	{
    		if (stack != null)
    		{
    			QuiverInventory quiverInv = new QuiverInventory(stack);
    			
    			for (ItemStack arrowStack : quiverInv.inv)
    			{
    				if (arrowStack != null)
    				{
	    				ItemStack addStack = arrowStack.copy();
	    				addStack.stackSize = 1;
	    				
	    				if (!QuiverMod.arrayItemStackContains(arrows, addStack))
	    				{
	    					arrows.add(addStack);
	    				}
    				}
    			}
    		}
    	}
    	
    	return arrows;
    }
	public int[] getIndexesToUse(EntityPlayer player)
    {
    	String playerName = player.getDisplayName();
    	int quiverIndex = -1;
    	int arrowIndex = -1;
    	
		ItemStack[] inv = player.inventory.mainInventory;
		ArrayList<ItemStack> arrowTypesHeld = getArrowTypesHeld(player);
		int arrowTypeCount = arrowTypesHeld.size();
		int arrowItem = getSelectedArrowItem(playerName);
		
		if (arrowTypeCount > 0)
		{
			if (arrowItem >= arrowTypeCount)
			{
				arrowItem = arrowTypeCount - 1;
				setSelectedArrowItem(playerName, arrowItem);
			}
			
			ItemStack arrowType = arrowTypesHeld.get(arrowItem);
			Item arrowID = arrowType.getItem();
			int arrowDamage = arrowType.getItemDamage();
			
			for (int quiverI = inv.length - 1; quiverI >= 0; quiverI--)
			{
				ItemStack quiverStack = inv[quiverI];
				
				if (quiverStack != null && quiverStack.getItem() == ModItems.quiver)
				{
					QuiverInventory quiverInv = new QuiverInventory(quiverStack);
					
					for (int arrowI = quiverInv.size - 1; arrowI >= 0; arrowI--)
					{
						ItemStack arrowStack = quiverInv.getStackInSlot(arrowI);
						
						if (arrowStack != null && arrowStack.getItem() == arrowID && arrowStack.getItemDamage() == arrowDamage)
						{
							quiverIndex = quiverI;
							arrowIndex = arrowI;
							break;
						}
					}
				}
				
				if (arrowIndex != -1)
					break;
			}
		}
    	
    	return new int[]{quiverIndex, arrowIndex};
    }

	public int getArrowStackIndexToUse(EntityPlayer player, ArrayList<ItemStack> quiverStacks)
	{
		String playerName = player.getDisplayName();

		Item arrowID = ModItems.arrowItemList.get(getSelectedArrowItem(playerName)).itemID;
		
		for (int i = quiverStacks.size() - 1; i >= 0; i++)
		{
			QuiverInventory inv = new QuiverInventory(quiverStacks.get(i));
			
			for (int arrowI = inv.size - 1; i >= 0; i++)
			{
				ItemStack arrowStack = inv.getStackInSlot(arrowI);
				if (arrowStack != null && arrowStack.getItem() == arrowID)
				{
					return arrowI;
				}
			}
		}
		
		return -1;
	}
    public InventorySlots getArrowQuiverSlot(EntityPlayer player)
    {
    	InventorySlots out = null;
    	
        int[] indexes = getIndexesToUse(player);
        int quiverIndex = indexes[0];
        int arrowIndex = indexes[1];
        ItemStack quiverStack = null; 
        ItemStack arrowStack = null;
        
        if (quiverIndex >= 0)
        {
        	out = new InventorySlots();
        	quiverStack = player.inventory.getStackInSlot(quiverIndex);
        	InventorySlot quiver = new InventorySlot(quiverStack, quiverIndex);
        	out.set("quiver", quiver);
        	
        	arrowStack = new QuiverInventory(quiverStack).getStackInSlot(arrowIndex);
        	
        	if (arrowStack != null)
        	{
	        	InventorySlot arrow = new InventorySlot(arrowStack, arrowIndex);
	        	out.set("arrow", arrow);
        	}
        }
        
        return out;
    }
    
    public InventorySlots getArrowSlot(EntityPlayer player)
    {
    	InventorySlots out = getArrowQuiverSlot(player);
        
        if (out == null || out.get("arrow") == null)
        {
        	int i = 0;
        	
        	for (ItemStack arrow : player.inventory.mainInventory)
        	{
        		if (arrow != null && BAR.isArrow(arrow))
        		{
        			out = new InventorySlots();
        			out.set("arrow", new InventorySlot(arrow, i));
        			break;
        		}
        		
        		i++;
        	}
        }
        
        return out;
    }*/
	public static boolean hasQuiver(EntityPlayer p){
		boolean ret=false;
		ret=p.inventory.hasItem(ModItems.quiver);
		for(int rep=0;rep<PlayerHandler.getPlayerBaubles(p).getSizeInventory();rep++){
			ItemStack i=PlayerHandler.getPlayerBaubles(p).getStackInSlot(rep);
			if(i!=null&&i.getItem()==ModItems.quiver){
				ret=true;
			}
		}
		return ret;
	}
	public static ItemStack getQuiverStack(EntityPlayer p){
		ItemStack quiver=null;
		for(int x=0;x<p.inventory.getSizeInventory();x++){
			if(p.inventory.getStackInSlot(x)!=null &&p.inventory.getStackInSlot(x).getItem()==ModItems.quiver)
				quiver=p.inventory.getStackInSlot(x);
		}
		for(int rep=0;rep<PlayerHandler.getPlayerBaubles(p).getSizeInventory();rep++){
			ItemStack i=PlayerHandler.getPlayerBaubles(p).getStackInSlot(rep);
			if(i!=null&&i.getItem()==ModItems.quiver){
				quiver=PlayerHandler.getPlayerBaubles(p).getStackInSlot(rep);
			}
		}
		return quiver;
	}
	public static ArrayList<ItemStack> getQuivers(EntityPlayer p){
		ArrayList<ItemStack> quivers = new ArrayList<ItemStack>();
		for(int x=0;x<p.inventory.getSizeInventory();x++){
			if(p.inventory.getStackInSlot(x)!=null){
				if(p.inventory.getStackInSlot(x).getItem()==ModItems.quiver)
					quivers.add(p.inventory.getStackInSlot(x));
			}
		}
		if(BAR.BaublesLoaded){
		for(int x=0;x<PlayerHandler.getPlayerBaubles(p).stackList.length;x++){
			ItemStack bau=PlayerHandler.getPlayerBaubles(p).getStackInSlot(x);
			if(bau!=null&&bau.getItem()==ModItems.quiver){
				quivers.add(PlayerHandler.getPlayerBaubles(p).getStackInSlot(x));
			}
		}
	}
		return quivers;
	}
	public static ArrayList<ItemStack> getArrowTypesHeld(EntityPlayer player)
    {
    	ArrayList<ItemStack> quivers = getQuivers(player);
    	
    	ArrayList<ItemStack> arrows = new ArrayList<ItemStack>();
    	
    	for (ItemStack stack : quivers)
    	{
    		if (stack != null)
    		{
    			QuiverInventory quiverInv = new QuiverInventory(stack);
    			
    			for (ItemStack arrowStack : quiverInv.inv)
    			{
    				if (arrowStack != null)
    				{
	    				ItemStack addStack = arrowStack.copy();
	    				addStack.stackSize = 1;
	    				
	    				if (!ModItems.arrayItemStackContains(arrows, addStack))
	    				{
	    					arrows.add(addStack);
	    				}
    				}
    			}
    		}
    	}
    	
    	return arrows;
    }
	public static void setSelectedArrowItem(EntityPlayer p, int value)
    {
    	p.getEntityData().setInteger("selectedArrow", value);;
    }
	public static int getSelectedArrowItem(EntityPlayer p)
    {
    	return p.getEntityData().getInteger("selectedArrow");
    }
	
	public static InventorySlots getArrowQuiverSlot(EntityPlayer player)
    {
    	InventorySlots out = null;
    	
        int[] indexes = getIndexesToUse(player);
        int quiverIndex = indexes[0];
        int arrowIndex = indexes[1];
        ItemStack quiverStack = null; 
        ItemStack arrowStack = null;
        
        if (quiverIndex >= 0)
        {
        	out = new InventorySlots();
        	if(quiverIndex<=player.inventory.getSizeInventory()){
        		quiverStack = player.inventory.getStackInSlot(quiverIndex);
        		System.out.println("found quiverStack within "+player.inventory.getSizeInventory()+", commencing the crunch");
        	}
        	else if(Loader.isModLoaded("Baubles")){
        		quiverStack=PlayerHandler.getPlayerBaubles(player).getStackInSlot(quiverIndex-player.inventory.getSizeInventory());
        		System.out.println("found quiverStack out of "+player.inventory.getSizeInventory()+", commencing the crunch at Baubles slot "+(quiverIndex-player.inventory.getSizeInventory()));
        	}
        	//TODO there's your problem.
        	else throw new IllegalArgumentException("you went above 36 without Baubles? ludicrous!");
        	System.out.println("QuiverStack is "+quiverStack);
        	InventorySlot quiver = new InventorySlot(quiverStack, quiverIndex);
        	out.set("quiver", quiver);
        	
        	arrowStack = new QuiverInventory(quiverStack).getStackInSlot(arrowIndex);
        	
        	if (arrowStack != null)
        	{
	        	InventorySlot arrow = new InventorySlot(arrowStack, arrowIndex);
	        	out.set("arrow", arrow);
        	}
        }
        return out;
    }
    
    public static InventorySlots getArrowSlot(EntityPlayer player)
    {
    	InventorySlots out = getArrowQuiverSlot(player);
        if (out == null || out.get("arrow") == null)
        {
        	int i = 0;
        	ItemStack[] baubles = null;
        	if(BAR.BaublesLoaded){ baubles=PlayerHandler.getPlayerBaubles(player).stackList;}
        	ItemStack[] both=ObjectArrays.concat(ObjectArrays.concat(player.inventory.mainInventory,player.inventory.armorInventory,ItemStack.class), baubles, ItemStack.class);
        	System.out.println("both length is "+both.length);
        	for (ItemStack arrow : both)
        	{
        		if (arrow != null && BAR.isArrow(arrow))
        		{
        			out = new InventorySlots();
        			out.set("arrow", new InventorySlot(arrow, i));
        	    	System.out.println();
        	    	System.out.println();
        	    	System.out.println();
        	    	System.out.println("found arrow slot at "+i);
        	    	System.out.println();
        	    	System.out.println();
        	    	System.out.println();
        			System.out.println();
        			break;
        		}
        		
        		i++;
        	}
        }
        
        return out;
    }
    
    public static int[] getIndexesToUse(EntityPlayer player)
    {
    	int quiverIndex = -1;
    	int arrowIndex = -1;
    	
		ItemStack[] inv = ObjectArrays.concat(player.inventory.mainInventory,player.inventory.armorInventory,ItemStack.class);
		ItemStack[] both=ObjectArrays.concat(inv, PlayerHandler.getPlayerBaubles(player).stackList, ItemStack.class);
		ArrayList<ItemStack> arrowTypesHeld = getArrowTypesHeld(player);
		int arrowTypeCount = arrowTypesHeld.size();
		int arrowItem = getSelectedArrowItem(player);
		
		if (arrowTypeCount > 0)
		{
			if (arrowItem >= arrowTypeCount)
			{
				arrowItem = arrowTypeCount - 1;
				setSelectedArrowItem(player, arrowItem);
			}
			
			ItemStack arrowType = arrowTypesHeld.get(arrowItem);
			Item arrowID = arrowType.getItem();
			int arrowDamage = arrowType.getItemDamage();
			
			for (int quiverI = both.length - 1; quiverI >= 0; quiverI--)
			{
				ItemStack quiverStack = both[quiverI];
				
				if (quiverStack != null && quiverStack.getItem() == ModItems.quiver)
				{
					QuiverInventory quiverInv = new QuiverInventory(quiverStack);
					
					for (int arrowI = quiverInv.size - 1; arrowI >= 0; arrowI--)
					{
						ItemStack arrowStack = quiverInv.getStackInSlot(arrowI);
						
						if (arrowStack != null && arrowStack.getItem() == arrowID && arrowStack.getItemDamage() == arrowDamage)
						{
							quiverIndex = quiverI;
							arrowIndex = arrowI;
							break;
						}
					}
				}
				
				if (arrowIndex != -1)
					break;
			}
		}
    	
    	return new int[]{quiverIndex, arrowIndex};
    }
    public static void setWornQuiverType(EntityPlayer playerName, int value)
    {
    	playerName.getEntityData().setInteger("wornQuiverType", value);
    }
    
    public static int getWornQuiverType(EntityPlayer playerName)
    {
    	return playerName.getEntityData().getInteger("wornQuiverType");
    }
    public void setUsingQuiver(EntityPlayer playerName, int metadata)
    {
    	playerName.getEntityData().setInteger("usingQuiverMetadata", metadata);
    }
}
