package com.Jackiecrazi.BetterArcheryReborn.lenders;

import java.util.ArrayList;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.entity.RenderItem;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.entity.boss.BossStatus;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.MathHelper;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.GuiIngameForge;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.client.event.RenderGameOverlayEvent.ElementType;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;

import baubles.common.lib.PlayerHandler;

import com.Jackiecrazi.BetterArcheryReborn.BAR;
import com.Jackiecrazi.BetterArcheryReborn.ConfigofJustice;
import com.Jackiecrazi.BetterArcheryReborn.Items.ModItems;
import com.Jackiecrazi.BetterArcheryReborn.helpful.ColorThing;
import com.Jackiecrazi.BetterArcheryReborn.helpful.RepetitiveSnippets;
import com.Jackiecrazi.BetterArcheryReborn.quivering.QuiverInventory;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;

public class QuiverModOverlayRenderer extends Gui {
	
	public static final ResourceLocation HOTBAR_TEX = new ResourceLocation("quivermod:textures/gui/quiverhotbar.png");
	
    static RenderItem itemRenderer = new RenderItem();
	
	int fadeTimer = 0;
	int fadeTime = 80;
	
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
	
	@SubscribeEvent
	public void pre(RenderGameOverlayEvent.Pre event)
	{
		if (GuiIngameForge.renderHotbar ? event.type == ElementType.HOTBAR : event.type == ElementType.ALL)
		{
			GL11.glPushMatrix();
			
			Minecraft mc = BAR.proxy.mc;
			
			long timeNow = BAR.proxy.mc.getSystemTime();
			float frameDiff = ((float)(timeNow - lastFrame)) / 10F;
			lastFrameTime = frameDiff;
			lastFrame = timeNow;
			
			if (mc != null)
			{
				EntityPlayer player = mc.thePlayer;
				
				if (player != null)
				{
					String playerName = player.getDisplayName();
					
					ItemStack heldStack = player.getCurrentEquippedItem();
					ArrayList<ItemStack> quiverStacks = RepetitiveSnippets.getQuivers(player);
					
					int quiverCount = quiverStacks.size();
					int quiverSlotCount = quiverCount * QuiverInventory.size;
					
					ArrayList<Integer> selectedQuiverSlots = new ArrayList<Integer>();
					
					int selectedArrowItemIndex = RepetitiveSnippets.getSelectedArrowItem(player);
					ArrayList<ItemStack> arrowTypeList = RepetitiveSnippets.getArrowTypesHeld(player);
					
					if (selectedArrowItemIndex >= 0 && selectedArrowItemIndex < arrowTypeList.size())
					{
						ItemStack selectedArrowItem = arrowTypeList.get(selectedArrowItemIndex);
						
						int slotIndex = 0;
						
						for (ItemStack quiverStack : quiverStacks)
						{
							QuiverInventory inv = new QuiverInventory(quiverStack);
							
							for (int i = 0; i < inv.size; i++)
							{
								ItemStack arrowStack = inv.getStackInSlot(i);
								
								if (arrowStack != null && arrowStack.isItemEqual(selectedArrowItem))
								{
									selectedQuiverSlots.add(slotIndex);
								}
								
								slotIndex++;
							}
						}
					}
					else if (selectedArrowItemIndex != 0)
					{
						selectedArrowItemIndex = arrowTypeList.size() - 1;
						
						if (selectedArrowItemIndex < 0)
							selectedArrowItemIndex = 0;
						
						RepetitiveSnippets.setSelectedArrowItem(player, selectedArrowItemIndex);
					}
					
					if (quiverCount > 0 && (RepetitiveSnippets.hasQuiver(player)))
					{
						ScaledResolution res = event.resolution;
						FontRenderer fontRenderer = mc.fontRenderer;
						TextureManager renderEngine = mc.renderEngine;
						
						int w = res.getScaledWidth();
						int h = res.getScaledHeight();
						
						int slotsOnScreen = w / 19;
						int slotsOnHalfScreen = (int)Math.floor(slotsOnScreen / 2F);
						
						GL11.glDisable(GL11.GL_BLEND);
			            RenderHelper.enableGUIStandardItemLighting();
						
						GL11.glTranslatef(0, ConfigofJustice.quiverHotbarVerticalOffset, 0);
						
						if (BossStatus.bossName != null && BossStatus.healthScale > 0)
						{
							GL11.glTranslatef(0, 16, 0);
						}
						
						int firstSlotIndex = -1;
						
						if (selectedQuiverSlots.size() > 0)
						{
							for (int slot : selectedQuiverSlots)
							{
								if (firstSlotIndex == -1 || firstSlotIndex > slot)
								{
									firstSlotIndex = slot;
								}
							}
							
							if (firstSlotIndex == -1)
							{
								firstSlotIndex = 0;
							}
							else
							{
								firstSlotIndex = MathHelper.clamp_int(firstSlotIndex, slotsOnHalfScreen, quiverSlotCount - slotsOnHalfScreen);
								firstSlotIndex -= quiverSlotCount / 2;	// Center
							}
						
							int targetOffset = (firstSlotIndex * 19) + (firstSlotIndex / QuiverInventory.size);	// (firstSlotIndex / QuiverInventory.size) is for pixels between quivers
							float floatSpeedDir = (float)(offset - targetOffset) / 10F;
							int speedDir = 0;
							
							if (floatSpeedDir > 0)
							{
								speedDir = (int)Math.ceil(floatSpeedDir);
							}
							else
							{
								speedDir = (int)Math.floor(floatSpeedDir);
							}
							
							offset -= speedDir;
						}
						
						if (fadeTimer > 0)
						{
							float part = fadeTimer / (float)fadeTime;
							int fade = (int)(part * 255);
							
							if (fade > 255)
								fade = 255;
							else if (fade < 4)
								fade = 4;
	
		                    GL11.glEnable(GL11.GL_BLEND);
		                    GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
							fontRenderer.drawStringWithShadow(infoText, (w - fontRenderer.getStringWidth(infoText)) / 2, 23, 16777215 + (fade << 24));
		                    GL11.glDisable(GL11.GL_BLEND);
							
							fadeTimer--;
						}
						else
						{
							infoText = "";
						}
						
						int quiverW = quiverSlotCount * 19;
						int x = (w - quiverW - quiverCount) / 2;
						
						if (quiverSlotCount * 19 > w)
						{
							x -= offset;
						}
						
						int y = 2;
						
						float selectedYOff = 0;
						float selectedScaleX = 1;
						float selectedScaleY = 1;
						int animationStyle=0;
						if (animationStyle == 0 && selectedQuiverSlots.size() > 0)
						{
							animTick += frameDiff;
							animTick = animTick % animLength;
			                
			                selectedYOff = animTick;
			                
			                if (selectedYOff > halfAnimLength)
			                {
			                	selectedYOff = ((selectedYOff - halfAnimLength) * -1F) + halfAnimLength;
			                }
			                
			                selectedYOff /= animLength / 8F;
			                selectedYOff = (selectedYOff * selectedYOff) / 2.5F;
							
			                float selectedScale = (animTick % halfAnimLength) * 2F;
							
							if (selectedScale > halfAnimLength)
							{
								selectedScale = ((selectedScale - halfAnimLength) * -1F) + halfAnimLength;
							}
							
							selectedScale /= animLength;
							selectedScaleX = (1F - selectedScale) * 1.3F;
							selectedScaleY = (0.5F + selectedScale) * 1.1F;
						}
						else
						{
							if (switchAnimation > 0)
							{
								switchAnimation -= frameDiff;
								
								if (switchAnimation < 0)
									switchAnimation = 0;
							}
						}

						int quiversSoFar = 0;
						int slotIndex = 0;
						
						for (ItemStack quiverStack : quiverStacks)
						{
							QuiverInventory quiverInventory = new QuiverInventory(quiverStack);

							int color = ModItems.quiver.getColorFromItemStack(quiverStack, 1);
					    	float red = ColorThing.getFloatRed(color);
					    	float green = ColorThing.getFloatGreen(color);
					    	float blue = ColorThing.getFloatBlue(color);
							
							for (int i = 0; i < quiverInventory.size; i++)
							{
								GL11.glDisable(GL11.GL_LIGHTING);
								GL11.glDisable(GL11.GL_BLEND);
						        GL11.glColor4f(red, green, blue, 1);
								renderEngine.bindTexture(HOTBAR_TEX);
								
								boolean firstSlot = i == 0;
								boolean lastSlot = i == quiverInventory.size - 1;
								
								int texOffset = 0;
	
								if (lastSlot)
								{
									texOffset = 38;
								}
								else if (!firstSlot)
								{
									texOffset = 19;
								}
								
								drawTexturedModalRect(x, y, texOffset, 0, 19, 19);
		
								ItemStack arrowStack = quiverInventory.getStackInSlot(i);
								boolean hasArrowStack = arrowStack != null;
								boolean selected = selectedQuiverSlots.contains(slotIndex);
								
								if (selected)
									drawTexturedModalRect(x, y, 57 + texOffset, 38, 19, 19);
								else
									drawTexturedModalRect(x, y, texOffset, 38, 19, 19);
	
								int stackPosX = x + 1;
								int stackPosY = y;
								
								if (hasArrowStack)
								{
									int iconPosY = stackPosY;
									
									GL11.glPushMatrix();
									GL11.glTranslatef(1, 0, 0);
									
									if (animationStyle == 0)
									{
										if (selected)
										{
							                GL11.glTranslatef(0, selectedYOff, 0);
		
							                float offsetX = stackPosX + 8;
							                float offsetY = iconPosY;
							                GL11.glTranslatef(offsetX, offsetY, 0.0F);
											GL11.glScalef(selectedScaleX, selectedScaleY, 1);
							                GL11.glTranslatef(-offsetX, -offsetY, 0.0F);
							                
							                offsetY += 16;
							                GL11.glTranslatef(offsetX, offsetY, 0.0F);
											GL11.glScalef(1, 1.2F, 1);
							                GL11.glTranslatef(-offsetX, -offsetY, 0.0F);
										}
									}
									else if (selected)
									{
										GL11.glTranslatef(0, -Math.min((switchAnimationLength - switchAnimation) * 2F / (float)switchAnimationLength, 2), 0);
							            
										if (ConfigofJustice.animationStyle == 1 && switchAnimation > 0)
										{
							                float amountScale = 1 + (switchAnimation / (float)switchAnimationLength);
							                float offsetX = stackPosX + 8;
							                float offsetY = iconPosY + 12;
							                GL11.glTranslatef(offsetX, offsetY, 0.0F);
							                GL11.glScalef(1 / amountScale, (amountScale + 1) / 2F, 1);
							                GL11.glTranslatef(-offsetX, -offsetY, 0.0F);
										}
									}
	
									itemRenderer.zLevel = 50;
									GL11.glEnable(GL11.GL_DEPTH_TEST);
									GL11.glEnable(GL12.GL_RESCALE_NORMAL);
									itemRenderer.renderItemAndEffectIntoGUI(fontRenderer, renderEngine, arrowStack, stackPosX, iconPosY);
									
									GL11.glPopMatrix();
									
									GL11.glDisable(GL11.GL_DEPTH_TEST);
								}
	
								GL11.glDisable(GL11.GL_LIGHTING);
						        GL11.glColor4f(red, green, blue, 1);
								renderEngine.bindTexture(HOTBAR_TEX);
								
								drawTexturedModalRect(x, y, texOffset, 19, 19, 19);
								
								if (hasArrowStack)
								{
									itemRenderer.renderItemOverlayIntoGUI(fontRenderer, renderEngine, arrowStack, stackPosX, stackPosY);
								}
								
								GL11.glDisable(GL11.GL_LIGHTING);
						        GL11.glColor4f(1, 1, 1, 1);
								
								x += 19;
								
								if (lastSlot)
									x++;
								
								slotIndex++;
							}
							
							quiversSoFar++;
						}
						
						GL11.glEnable(GL11.GL_DEPTH_TEST);
						//GL11.glEnable(GL11.GL_BLEND);

	                    mc.getTextureManager().bindTexture(Gui.icons);
					}
				}
			}
			
			GL11.glPopMatrix();
		}
	}
	
}
