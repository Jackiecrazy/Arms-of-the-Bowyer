package com.Jackiecrazi.BetterArcheryReborn.lenders;

import java.awt.AlphaComposite;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;
import org.lwjgl.opengl.GL13;
import org.lwjgl.opengl.GL20;

import com.Jackiecrazi.BetterArcheryReborn.BAR;
import com.Jackiecrazi.BetterArcheryReborn.Items.ModItems;
import com.Jackiecrazi.BetterArcheryReborn.Items.QuiverBow;
import com.Jackiecrazi.BetterArcheryReborn.Items.arrows.IArrowIcons;
import com.Jackiecrazi.BetterArcheryReborn.client.Tex2DRender3D;
import com.Jackiecrazi.BetterArcheryReborn.helpful.BowArrowIcons;
import com.Jackiecrazi.BetterArcheryReborn.helpful.ColorThing;

import cpw.mods.fml.common.ObfuscationReflectionHelper;
import cpw.mods.fml.relauncher.ReflectionHelper;



import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.profiler.Profiler;
import net.minecraft.util.IIcon;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.ForgeHooksClient;
import net.minecraftforge.client.IItemRenderer;
import net.minecraftforge.client.MinecraftForgeClient;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.client.renderer.EntityRenderer;
import net.minecraft.client.renderer.ItemRenderer;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.entity.RenderItem;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.ai.EntityAIArrowAttack;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.monster.EntitySkeleton;
import net.minecraft.entity.player.EntityPlayer;

public class WeirdRenderBowThing implements IItemRenderer {
	
    private static final ResourceLocation ITEM_GLINT = new ResourceLocation("textures/misc/enchanted_item_glint.png");

	private static final Tessellator tessellator = Tessellator.instance;
	private static final RenderItem renderItem = new RenderItem();
	
	private static Minecraft mc = null;
	
	boolean vanillaSkeletons = false;
	boolean improvedFirstPerson = false;
	boolean optifineBroken = false;
	
	public WeirdRenderBowThing()
	{
	}

	@Override
	public boolean handleRenderType(ItemStack item, ItemRenderType type)
	{
		return type.equals(ItemRenderType.EQUIPPED) || type.equals(ItemRenderType.EQUIPPED_FIRST_PERSON) ||
				type.equals(ItemRenderType.INVENTORY) ||
				type.equals(ItemRenderType.ENTITY);
	}

	@Override
	public boolean shouldUseRenderHelper(ItemRenderType type, ItemStack item, ItemRendererHelper helper)
	{
		return helper.equals(ItemRendererHelper.ENTITY_BOBBING) || helper.equals(ItemRendererHelper.ENTITY_ROTATION);
	}
	
	private void drawItem(IIcon icon, float thickness)
	{
		float xStart = icon.getMinU();
		float xEnd = icon.getMaxU();
		float yStart = icon.getMinV();
		float yEnd = icon.getMaxV();
		int height = icon.getIconHeight();
		int width = icon.getIconWidth();

		
		
		ItemRenderer.renderItemIn2D(tessellator, xEnd, yStart, xStart, yEnd, width, height, thickness);
	}
	
	private void prepareEnchantment()
	{
        GL11.glDepthFunc(GL11.GL_EQUAL);
        GL11.glDisable(GL11.GL_LIGHTING);
        GL11.glEnable(GL11.GL_BLEND);
        GL11.glBlendFunc(GL11.GL_SRC_COLOR, GL11.GL_ONE);
        GL11.glMatrixMode(GL11.GL_TEXTURE);
        float brightness = 0.76F;
        GL11.glColor4f(0.38F, 0.19F, 0.61F, 1);
        mc.renderEngine.bindTexture(ITEM_GLINT);
	}
	
	private void drawEnchantment(float thickness)
	{
        GL11.glPushMatrix();
        float enchScale = 0.125F;
        GL11.glScalef(enchScale, enchScale, enchScale);
        
        float timeOffset = (float)(Minecraft.getSystemTime() % 3000L) / 3000 * 8;
        GL11.glPushMatrix();
        GL11.glTranslatef(timeOffset, 0.0F, 0.0F);
        GL11.glRotatef(-50.0F, 0.0F, 0.0F, 1.0F);
        Tex2DRender3D.renderTwoPlanes(0, 0, 1, 1, thickness);
        //wtf is this?
        GL11.glPopMatrix();
        
        timeOffset = (float)(Minecraft.getSystemTime() % 4873L) / 4873 * 8;
        GL11.glPushMatrix();
        GL11.glTranslatef(-timeOffset, 0, 0);
        GL11.glRotatef(10, 0, 0, 1);
        Tex2DRender3D.renderTwoPlanes(0, 0, 1, 1, thickness);
        GL11.glPopMatrix();

        GL11.glPopMatrix();
	}
	
	private void unprepareEnchantment()
	{
        GL11.glColor4f(1, 1, 1, 1);
        GL11.glMatrixMode(GL11.GL_MODELVIEW);
        GL11.glDisable(GL11.GL_BLEND);
        GL11.glEnable(GL11.GL_LIGHTING);
        GL11.glDepthFunc(GL11.GL_LEQUAL);
        mc.renderEngine.bindTexture(TextureMap.locationItemsTexture);
	}
	
	private void renderGlint(int par2, int par3, int par4, int par5)
	{
        for (int j1 = 0; j1 < 2; ++j1)
        {
            if (j1 == 0)
            {
                GL11.glBlendFunc(GL11.GL_SRC_COLOR, GL11.GL_ONE);
            }

            if (j1 == 1)
            {
                GL11.glBlendFunc(GL11.GL_SRC_COLOR, GL11.GL_ONE);
            }

            float f = 0.00390625F;
            float f1 = 0.00390625F;
            float f2 = (float)(Minecraft.getSystemTime() % (long)(3000 + j1 * 1873)) / (3000.0F + (float)(j1 * 1873)) * 256.0F;
            float f3 = 0.0F;
            Tessellator tessellator = Tessellator.instance;
            float f4 = 4.0F;

            if (j1 == 1)
            {
                f4 = -1.0F;
            }

            tessellator.startDrawingQuads();
            tessellator.addVertexWithUV((double)(par2 + 0), (double)(par3 + par5), -50, (double)((f2 + (float)par5 * f4) * f), (double)((f3 + (float)par5) * f1));
            tessellator.addVertexWithUV((double)(par2 + par4), (double)(par3 + par5), -50, (double)((f2 + (float)par4 + (float)par5 * f4) * f), (double)((f3 + (float)par5) * f1));
            tessellator.addVertexWithUV((double)(par2 + par4), (double)(par3 + 0), -50, (double)((f2 + (float)par4) * f), (double)((f3 + 0.0F) * f1));
            tessellator.addVertexWithUV((double)(par2 + 0), (double)(par3 + 0), -50, (double)((f2 + 0.0F) * f), (double)((f3 + 0.0F) * f1));
            tessellator.draw();
        }
	}

	@Override
	public void renderItem(ItemRenderType type, ItemStack bowStack, Object... data)
	{
		if (mc == null)
			mc = BAR.proxy.mc;
		
        Item item = bowStack.getItem();
		
		if (type.equals(ItemRenderType.INVENTORY))
		{
            GL11.glDisable(GL11.GL_LIGHTING);

            for (int pass = 0; pass < 2; ++pass)
            {
                IIcon icon = item.getIcon(bowStack, pass);
                
                if (icon != null)
                {
	                int color = item.getColorFromItemStack(bowStack, pass);
	                ColorThing.glSetColor(color);
	                renderItem.renderIcon(0, 0, icon, 16, 16);
                }
                
                if (pass == 0 && bowStack.hasEffect(pass))
                {
                	GL11.glPushMatrix();
                	
                    GL11.glDepthFunc(GL11.GL_GREATER);
                    GL11.glDisable(GL11.GL_LIGHTING);
                    GL11.glDepthMask(false);
                    GL11.glEnable(GL11.GL_BLEND);
                    GL11.glBlendFunc(GL11.GL_DST_COLOR, GL11.GL_DST_COLOR);
                    
                    GL11.glColor4f(0.5F, 0.25F, 0.8F, 1.0F);
                    mc.renderEngine.bindTexture(ITEM_GLINT);
                    
                    renderGlint(-2, -2, 20, 20);
                    
                    mc.renderEngine.bindTexture(TextureMap.locationItemsTexture);
                    
                    GL11.glDisable(GL11.GL_BLEND);
                    GL11.glDepthMask(true);
                    
                    GL11.glEnable(GL11.GL_LIGHTING);
                    GL11.glDepthFunc(GL11.GL_LEQUAL);
                    
                    GL11.glPopMatrix();
                }
            }

            GL11.glEnable(GL11.GL_LIGHTING);
		}
		else if (type.equals(ItemRenderType.ENTITY))
		{
			EntityItem droppedItem = (EntityItem)data[1];
            //byte miniBlocks = renderItem.getMiniBlockCount(bowStack, (byte) 0);
            
            GL11.glTranslatef(-0.5F, -0.25F, 0.04F);
            
            GL11.glPushMatrix();
			
            for (int pass = 0; pass < 2; ++pass)
            {
                IIcon icon = item.getIcon(bowStack, pass);
                
                if (icon != null)
                {
	                int color = item.getColorFromItemStack(bowStack, pass);
	                ColorThing.glSetColor(color);
	                
	                if (pass == 0)
	                {
	                	drawItem(icon, 0.08F);
	                }
	                else
	                {
	                	GL11.glTranslatef(0, 0, -0.01F);
	                	drawItem(icon, 0.06F);
	                }
                }
            }
            
            GL11.glPopMatrix();
            
            if (bowStack.isItemEnchanted())
            {
            	prepareEnchantment();
            	drawEnchantment(0.08F);
            	unprepareEnchantment();
            }
		}
		else if (type.equals(ItemRenderType.EQUIPPED) || type.equals(ItemRenderType.EQUIPPED_FIRST_PERSON))
		{
			Entity entity = (Entity)data[1];
			
			QuiverBow bow = (QuiverBow)item;
			boolean wholeBow = !(bow.isBroken(bowStack));
			
			float partialTick = BAR.proxy.overlayHandler.partialTick;
			GL11.glPushMatrix();
			EntityPlayer player = null;
			
			ItemStack arrowStack = null;
			float fullBright = -1;
			
			int iconOffset = 0;
			IIcon icon;
			int size = bow.getIconSize();
			
			float scale = 1;
			int arrowStep = 1;
			
			float px = 1 / (float)(16 * size);
			
			float scaleOffsetX = bow.getScaleOffsetX() * px;
			float scaleOffsetY = bow.getScaleOffsetY() * px;
			
			float usePower = 0;
			
			boolean thirdPerson = !type.equals(ItemRenderType.EQUIPPED_FIRST_PERSON);
			boolean ifp = false;
			
			
			if (entity instanceof EntityPlayer)
			{
				player = (EntityPlayer)entity;
				ifp = improvedFirstPerson;
				
				scale = bow.getScale(thirdPerson);
				
				arrowStep = bow.getArrowStep();
				
				if (player.isUsingItem())
				{
					usePower = bow.getUsePowerFromUseCount(player.getItemInUseCount() - partialTick);
				}
				
				arrowStack = bow.getArrowStackFromInv(player);
			}
			else
			{
				GL11.glTranslatef(0, 0, -0.05F);
				
				/*if (entity instanceof EntityQuiverModSkeleton)
				{
					EntityQuiverModSkeleton skeleton = (EntityQuiverModSkeleton)entity;
					arrowStack = skeleton.getUsingArrowStack();
					
					int usingLeft = skeleton.getUseLeft();
					
					if (usingLeft > 0)
					{
						int startUseLeft = skeleton.getStartUseLeft();
						float useTime = startUseLeft - usingLeft - 5 + partialTick;
						
						if (useTime > 0)
						{
							usePower = bow.getUsePower(useTime);
						}
					}
				}*/
				//TODO uncomment when skeletons are added
				
			}
			
			if (arrowStack == null)
				arrowStack = new ItemStack(ModItems.DerpArrow);
			
			Item arrowItem = arrowStack.getItem();
			
			iconOffset = bow.getIconOffset(usePower);
			
			if (usePower > 0 && arrowItem instanceof IArrowIcons)
				fullBright = BowArrowIcons.getFullBright(bow.getBowArrowIconName(),
		    			((IArrowIcons)arrowItem).getSpecialBowIconName(arrowStack.getItemDamage()));
			
			//TODO reactivate when custom arrow come back
			boolean isFullBright = fullBright > 0;
			
			float lightmapX = -1;
			float lightmapY = -1;

			if (wholeBow)
			{
				try
				{
					fullBright = Math.abs(fullBright);
					
					lightmapX = OpenGlHelper.lastBrightnessX;
					lightmapY = OpenGlHelper.lastBrightnessY;
	
					float fullBrightX = fullBright;
					float fullBrightY = 0;

					if (fullBrightY < lightmapY)
					{
						fullBrightY = lightmapY;
						
						if (fullBrightX < lightmapX)
						{
							fullBrightX = lightmapX;
						}
					}
					
			        OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, fullBrightX, fullBrightY);
				}
				catch (NoSuchFieldError e)
				{
					optifineBroken = true;
				}
			}
			
			if (!ifp)
			{
				if (thirdPerson)
				{
					GL11.glRotatef(20, 1, 0.4F, 0);//20
					GL11.glTranslatef(0.1F, -0.4F, 0.15F);
					GL11.glRotatef(-2, 1, -1, 0);
				}
				else
				{
					float rot = 1 - usePower;
					GL11.glRotatef(-6 * rot, 0, 1, 1);
				}
			}
			
	        // Begin transforms and rendering
			bow.doPreTransforms(thirdPerson, ifp, px * scale, partialTick);
			
			GL11.glPushMatrix();
			bow.doBowTransforms(thirdPerson, ifp, px * scale, partialTick);
			
			GL11.glTranslatef(scaleOffsetX, scaleOffsetY, 0);
			GL11.glScalef(scale, scale, 1);
			GL11.glTranslatef(-scaleOffsetX, -scaleOffsetY, 0);
			
			icon = bow.getBowIconForPlayer(iconOffset);
			drawItem(icon, 0.09375F);
			
			boolean arrowHasEffect = false;
			
			if (wholeBow)
			{
				icon = bow.getStringIcon(iconOffset);
				
				if (icon != null)
				{
					GL11.glPushMatrix();
					GL11.glTranslatef(0, 0, -0.015625F);
					drawItem(icon, 0.0625F);
					GL11.glPopMatrix();
				}
	            
	            GL11.glPopMatrix();

				GL11.glTranslatef(scaleOffsetX, scaleOffsetY, 0);
				GL11.glScalef(scale, scale, 1);
				GL11.glTranslatef(-scaleOffsetX, -scaleOffsetY, 0);
				
				GL11.glPushMatrix();
				
				if (iconOffset > 0)
				{
					if (thirdPerson && ifp)
					{
						GL11.glRotatef(5, 1, -1, 0);
						GL11.glTranslatef(0, 0, -0.03F);
					}
					else
					{
						GL11.glRotatef(-5, 1, -1, 0);
						GL11.glTranslatef(0, 0, 0.03F);
					}
					
					float offset = -(iconOffset - 3) * arrowStep * px;
					GL11.glTranslatef(offset, offset, 0);
					//TODO arrows
					arrowHasEffect = arrowStack.hasEffect();
					float defaultThickness = 0.08F;
					
					for (int pass = 0; pass < 3; pass++)
					{
						icon = bow.getArrowNew(iconOffset, bow.getArrow(player), pass,bow.getSplit(player)!=1);
						//TODO splitting arrow
						if (icon != null)
						{
							GL11.glPushMatrix();

							float thickness = 0.08F;
							float depthOffset = 0;
							
							switch (pass)
							{
							case 0:
							case 1:
								int color = arrowStack.getItem().getColorFromItemStack(arrowStack, pass);
								ColorThing.glSetColor(color);
								
								if (isFullBright)
							        GL11.glDisable(GL11.GL_LIGHTING);
								
								break;
							case 2:
								thickness = 0.1F;
								depthOffset = 0.01F;
								break;
							}

							GL11.glTranslatef(0, 0, depthOffset);
							
							drawItem(icon, thickness);
							
				        	GL11.glPopMatrix();
						}
					}
					//end arrow
				}
			}
			
			boolean bowEnch = bowStack.isItemEnchanted();
			boolean ench = bowEnch || arrowHasEffect;
			
	        if (ench)
	        {
		        prepareEnchantment();
	        }
			
			if (arrowHasEffect)
			{
            	drawEnchantment(0.08F);
			}

			GL11.glMatrixMode(GL11.GL_MODELVIEW);
			GL11.glPopMatrix();
			
			if (bowEnch)
			{
				bow.doBowTransforms(thirdPerson, ifp, px * scale, partialTick);
				
				if (!wholeBow)
				{
					GL11.glTranslatef(scaleOffsetX, scaleOffsetY, 0);
					GL11.glScalef(scale, scale, 1);
					GL11.glTranslatef(-scaleOffsetX, -scaleOffsetY, 0);
				}
		        
		        GL11.glMatrixMode(GL11.GL_TEXTURE);
				drawEnchantment(0.09375F);
			}
			
	        if (ench)
	        {
		        unprepareEnchantment();
	        }
			// End rendering and transforms
			
			if (lightmapX != -1)
			{
				OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, lightmapX, lightmapY);
				GL11.glEnable(GL11.GL_LIGHTING);
			}
			
			//QuiverMod.debug.endProfSect();
			
			GL11.glPopMatrix();
		}
	}
}
