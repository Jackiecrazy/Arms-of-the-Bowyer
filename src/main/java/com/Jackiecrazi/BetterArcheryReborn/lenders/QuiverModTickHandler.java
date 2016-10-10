package com.Jackiecrazi.BetterArcheryReborn.lenders;

import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.entity.RenderItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.StatCollector;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;

import com.Jackiecrazi.BetterArcheryReborn.BAR;
import com.Jackiecrazi.BetterArcheryReborn.Items.QuiverBow;
import com.Jackiecrazi.BetterArcheryReborn.Items.arrows.ItemQuiverModArrow;
import com.Jackiecrazi.BetterArcheryReborn.Items.arrows.PotionArrow;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.TickEvent;
import cpw.mods.fml.common.gameevent.TickEvent.Type;

public class QuiverModTickHandler extends Gui {
	
	public static final ResourceLocation hotbarTexture = new ResourceLocation("quivermod:textures/gui/quiverhotbar.png");
	
	public static float partialTick = 0;
	public static int frames = 0;
	
	static Tessellator tessellator = Tessellator.instance;
    static RenderItem itemRenderer = new RenderItem();
	int zLevel = -90;

	public ItemStack prevHeldStack;
	public int prevQuiverUniqueID = -1;
	public int prevQuiverDamage = -1;
	
	int fadeTimer = 0;
	int fadeTime = 80;
	int noFadeTime = 40;
	
	String infoText = "";
	
	int switchAnimation = 0;
	int switchAnimationLength = 30;
	
	int offset = 0;
	
	float animLength = 100;
	float animTickStart = animLength / 2;
	float animTick = animTickStart;
	float halfAnimLength = animLength / 2F;
	
	float lastFrameTime = 0;
	long lastFrame = BAR.proxy.mc.getSystemTime();
	
	public QuiverModTickHandler() {
	}
	
	public void tickStart(TickEvent.PlayerTickEvent hi)
	{
	    EntityPlayerSP thePlayer = BAR.proxy.mc.thePlayer;
	    
		if (hi.type==Type.PLAYER)
		{
	        partialTick = (Float)0.0F;
	        frames++;
	        
			if (thePlayer != null)
			{
		        if (thePlayer.isUsingItem())
		        {
		        	ItemStack usingStack = thePlayer.getHeldItem();
		        	
		        	if (usingStack != null && usingStack.getItem() instanceof QuiverBow)
		        	{
		        		BAR.proxy.onBowUse(thePlayer, lastFrameTime);
		        		
		        	}
		        }
		        else
		        {
		        	BAR.proxy.resetSavedFOV();
		        }
			}
		}
		
			EntityPlayer tickPlayer = (EntityPlayer)hi.player;
			
			if (thePlayer != null && tickPlayer == thePlayer)
			{
				if (thePlayer.isUsingItem())
				{
		        	ItemStack usingStack = thePlayer.getHeldItem();
		        	Item usingItem = null;
		        	
		        	if (usingStack != null && (usingItem = usingStack.getItem()) instanceof QuiverBow)
		        	{
		        		QuiverBow usingBow = (QuiverBow)usingItem;
		        		float slow = 15;
		        		
		        		if (slow > 0)
		        		{
		        			//thePlayer.getAttributeMap().getAttributeInstanceByName(par1Str)
				            //thePlayer.landMovementFactor /= 0.2F;
				            //thePlayer.landMovementFactor *= slow;
		        		}
		        	}
				}
				
/*				ItemStack quiver = null;
				
				InventorySlots slots = BAR.playerValueManager.getArrowQuiverSlot(tickPlayer);
				
				if (slots != null)
				{
					InventorySlot slot = slots.get("quiver");
					
					if (slot != null)
					{
						quiver = slot.stack;
					}
				}
				
				ItemStack heldStack = tickPlayer.getHeldItem();
				
				if (heldStack != null && heldStack.getItem() == ModItems.quiver && heldStack.equals(quiver))
				{
					int heldQuiverID = ModItems.quiver.getUniqueID(heldStack);
					
					if (ModItems.quiver.getUniqueID(quiver) == heldQuiverID)
					{
						quiver = null;
					}
				}
				
				int uniqueID = -1;
				int damage = -1;
				
				if (quiver != null)
				{
					uniqueID = ModItems.quiver.getUniqueID(quiver);
					damage = quiver.getItemDamage();
				}
				
				if (prevHeldStack != heldStack || uniqueID != prevQuiverUniqueID || damage != prevQuiverDamage)
				{
					QuiverMod.playerValueManager.setUsingQuiver(tickPlayer.getDisplayName(), damage);
					QuiverMod.playerValueManager.sendValuesToServer(tickPlayer);
				}
				
				prevQuiverUniqueID = uniqueID;
				prevQuiverDamage = damage;
				prevHeldStack = heldStack;*/
				//TODO remove seal when quivers reimplemented
		}
	}
	
	public void setInfoText(String newText)
	{
		infoText = newText;
		fadeTimer = fadeTime + noFadeTime;
	}
	
	public void resetSelectionAnimation()
	{
		/*if (Config.animationStyle == 0)
			animTick = animTickStart;
		else
			switchAnimation = switchAnimationLength;*/
		//TODO remove seal when you know wtf this is
	}

	public String getLabel() {
		return "QuiverModTickHandler";
	}

}
