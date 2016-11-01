/*package com.Jackiecrazi.BetterArcheryReborn.entities;

import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.Entity;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.MathHelper;
import net.minecraft.util.ResourceLocation;

import org.lwjgl.opengl.GL11;

import com.Jackiecrazi.BetterArcheryReborn.BAR;
import com.Jackiecrazi.BetterArcheryReborn.ConfigofJustice;
import com.Jackiecrazi.BetterArcheryReborn.Items.ModItems;
import com.Jackiecrazi.BetterArcheryReborn.client.Tex2DRender3D;
import com.Jackiecrazi.BetterArcheryReborn.helpful.ColorThing;
import com.Jackiecrazi.BetterArcheryReborn.helpful.TextureSizeMap;
import com.Jackiecrazi.BetterArcheryReborn.helpful.TextureSizeMap.TextureSize;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class RenderQuiverModArrow extends Render
{
	
	public static final String arrowTexturesPath = "BAR:textures/arrows/";
	public static final ResourceLocation arrowLoc = new ResourceLocation("textures/entity/arrow.png");
	
	public static final ResourceLocation impactArrowLoc = new ResourceLocation(arrowTexturesPath + "impact.png");
	public static final ResourceLocation timedArrowLoc = new ResourceLocation(arrowTexturesPath + "timed.png");
	
	public static final ResourceLocation[] fireArrowLocs = {
		new ResourceLocation(arrowTexturesPath + "fire0.png"),
		new ResourceLocation(arrowTexturesPath + "fire1.png"),
		new ResourceLocation(arrowTexturesPath + "fire2.png")
	};

	public static final ResourceLocation enderArrowLoc = new ResourceLocation(arrowTexturesPath + "enderarrow.png");
	public static final ResourceLocation torchArrowLoc = new ResourceLocation(arrowTexturesPath + "torch.png");
	
	public static final ResourceLocation splittingArrowLoc = new ResourceLocation(arrowTexturesPath + "splitting.png");
	
	public static final ResourceLocation[] drillArrowLocs = {
		new ResourceLocation(arrowTexturesPath + "drill0.png"),
		new ResourceLocation(arrowTexturesPath + "drill1.png")
	};
	public static final ResourceLocation brokenDrillArrowLoc = new ResourceLocation(arrowTexturesPath + "drillbroken.png");

	public static final ResourceLocation potionArrowInnerLoc = new ResourceLocation(arrowTexturesPath + "potioninner.png");
	public static final ResourceLocation potionArrowOuterLoc = new ResourceLocation(arrowTexturesPath + "potionouter.png");
	
	public static final ResourceLocation splashPotionArrowInnerLoc = new ResourceLocation(arrowTexturesPath + "splashpotioninner.png");
	public static final ResourceLocation splashPotionArrowOuterLoc = new ResourceLocation(arrowTexturesPath + "splashpotionouter.png");
	
	public static int distance3DSqr = -1;

    private static final float arrowLeft = 0;
    private static final float arrowRight = 16 / 32F;
    private static final float arrowTop = 0;
    private static final float arrowBottom = 5 / 32F;
    
    private static final float arrowWidth = arrowRight - arrowLeft;
    private static final float arrowHeight = arrowBottom - arrowTop;
    
    private static final float arrowWidthPx = arrowWidth * 32;
    private static final float arrowHeightPx = arrowHeight * 32;
    
    private static final float headLeft = 5 / 32F;
    private static final float headRight = 10 / 32F;
    private static final float headTop = 5 / 32F;
    private static final float headBottom = 10 / 32F;
    
    private static final float middleLeft = 10 / 32F;
    private static final float middleRight = 15 / 32F;
    private static final float middleTop = 5 / 32F;
    private static final float middleBottom = 10 / 32F;
    
    private static final float stringLeft = 16 / 32F;
    private static final float stringRight = 1;
    private static final float stringTop = 0;
    private static final float stringBottom = 9 / 32F;
	
	public RenderQuiverModArrow()
	{
		super();
	}
	
	private void render3DWithFixedWidth(float left, float top, float right, float bottom,
			int texWidth, int texHeight, float thickness)
	{
		int width = (int)(texWidth - (texWidth * left) - (texWidth * (1 - right)));
		int height = (int)(texHeight - (texHeight * top) - (texHeight * (1 - bottom)));
		
		Tex2DRender3D.render(true, left, top, right, bottom, width, height, thickness);
	}

	private void renderArrowWithTex(ResourceLocation texture, boolean fancy)
	{
        GL11.glPushMatrix();
        bindTexture(texture);
        
        Tessellator tessellator = Tessellator.instance;
        
        if (fancy)
        {
        	TextureSize texSize = TextureSizeMap.getSize(texture, 32, 32);
        	
			float px = 1 / arrowWidthPx;
			float halfPx = 0.5F * px;
			float center = -2.5F * px;
			float heightScale = arrowHeight / arrowWidth;
			
	        GL11.glRotatef(180, 0, 0, 1);
	        GL11.glRotatef(45, 1, 0, 0);
	        GL11.glScalef(0.9F, 0.8F, 0.8F);
	        GL11.glTranslatef(-4 / arrowWidthPx, 0, 0);
			
	        GL11.glPushMatrix();
	        
			for (int i = 0; i < 2; i++)
			{
				GL11.glPushMatrix();
				GL11.glTranslatef(0, center, halfPx);
				GL11.glScalef(1, heightScale, 1);
				//ItemRenderer.renderItemIn2D(tessellator, arrowRight, arrowTop, arrowLeft, arrowBottom, texSize.width, texSize.height, px);

		    	render3DWithFixedWidth(arrowLeft, arrowTop, arrowRight, arrowBottom, texSize.width, texSize.height, px);
				GL11.glPopMatrix();
				
				GL11.glRotatef(90, 1, 0, 0);
				GL11.glScalef(1, 0.99F, 0.99F);
			}
			
			GL11.glPopMatrix();
	
	        float headWidthPx = (headRight - headLeft) * 32;
	        float scale = (headWidthPx / arrowWidthPx);
	
	        GL11.glPushMatrix();
	        GL11.glScalef(1, 1.01F, 1.01F);
			GL11.glTranslatef(3 * px, center, -center);
			GL11.glRotatef(90, 0, 1, 0);
			GL11.glScalef(scale, heightScale, 1);
			render3DWithFixedWidth(headLeft, headTop, headRight, headBottom, texSize.width, texSize.height, px);
			
			GL11.glTranslatef(0, 0, 5 * px);
			render3DWithFixedWidth(middleLeft, middleTop, middleRight, middleBottom, texSize.width, texSize.height, px);
			GL11.glPopMatrix();
	
	        float stringHeightPx = (stringBottom - stringTop) * 32;
	        scale = (stringHeightPx / arrowHeightPx);
	
	        GL11.glPushMatrix();
	        GL11.glRotatef(90, 1, 0, 0);
			GL11.glTranslatef(0, -4.5F * px, halfPx);
			GL11.glScalef(1, heightScale * scale, 1);
			render3DWithFixedWidth(stringLeft, stringTop, stringRight, stringBottom, texSize.width, texSize.height, px);
			GL11.glPopMatrix();
        }
        else
        {
	        float scale = 0.05625F;
	        //GL11.glEnable(GL12.GL_RESCALE_NORMAL);
	        GL11.glEnable(GL11.GL_CULL_FACE);
	        
	        float tailLeft = 0;
	        float tailRight = 5 / 32F;
	        float tailTop = 5 / 32F;
	        float tailBottom = 10 / 32F;
	
	        GL11.glRotatef(45, 1, 0, 0);
	        GL11.glScalef(scale, scale, scale);
	        GL11.glTranslatef(-4, 0, 0);
	        
	    	GL11.glNormal3f(scale, 0, 0);
	        
	        // Tail
	        tessellator.startDrawingQuads();
	        tessellator.addVertexWithUV(-7, -2, -2, tailLeft, tailTop);
	        tessellator.addVertexWithUV(-7, -2, 2, tailRight, tailTop);
	        tessellator.addVertexWithUV(-7, 2, 2, tailRight, tailBottom);
	        tessellator.addVertexWithUV(-7, 2, -2, tailLeft, tailBottom);
	        tessellator.draw();
	        
	        // Head
	        tessellator.startDrawingQuads();
	        tessellator.addVertexWithUV(5.5, -2, -2, headLeft, headTop);
	        tessellator.addVertexWithUV(5.5, -2, 2, headRight, headTop);
	        tessellator.addVertexWithUV(5.5, 2, 2, headRight, headBottom);
	        tessellator.addVertexWithUV(5.5, 2, -2, headLeft, headBottom);
	        tessellator.draw();
	        
	        // Middle
	        tessellator.startDrawingQuads();
	        tessellator.addVertexWithUV(0, -2, -2, middleLeft, middleTop);
	        tessellator.addVertexWithUV(0, -2, 2, middleRight, middleTop);
	        tessellator.addVertexWithUV(0, 2, 2, middleRight, middleBottom);
	        tessellator.addVertexWithUV(0, 2, -2, middleLeft, middleBottom);
	        tessellator.draw();
	        
	        // String
	        tessellator.startDrawingQuads();
	        tessellator.addVertexWithUV(-8, 3.625, 0, stringLeft, stringBottom);
	        tessellator.addVertexWithUV(8, 3.625, 0, stringRight, stringBottom);
	        tessellator.addVertexWithUV(8, -3.625, 0, stringRight, stringTop);
	        tessellator.addVertexWithUV(-8, -3.625, 0, stringLeft, stringTop);
	        tessellator.draw();
	        
		    GL11.glNormal3f(-scale, 0, 0);
	        
	        // Tail
	        tessellator.startDrawingQuads();
	        tessellator.addVertexWithUV(-7, 2, -2, tailLeft, tailTop);
	        tessellator.addVertexWithUV(-7, 2, 2, tailRight, tailTop);
	        tessellator.addVertexWithUV(-7, -2, 2, tailRight, tailBottom);
	        tessellator.addVertexWithUV(-7, -2, -2, tailLeft, tailBottom);
	        tessellator.draw();
	        
	        // Head
	        tessellator.startDrawingQuads();
	        tessellator.addVertexWithUV(5.5, 2, -2, headLeft, headTop);
	        tessellator.addVertexWithUV(5.5, 2, 2, headRight, headTop);
	        tessellator.addVertexWithUV(5.5, -2, 2, headRight, headBottom);
	        tessellator.addVertexWithUV(5.5, -2, -2, headLeft, headBottom);
	        tessellator.draw();
	        
	        // Middle
	        tessellator.startDrawingQuads();
	        tessellator.addVertexWithUV(0, 2, -2, middleLeft, middleTop);
	        tessellator.addVertexWithUV(0, 2, 2, middleRight, middleTop);
	        tessellator.addVertexWithUV(0, -2, 2, middleRight, middleBottom);
	        tessellator.addVertexWithUV(0, -2, -2, middleLeft, middleBottom);
	        tessellator.draw();
	        
	        // String
	        tessellator.startDrawingQuads();
	        tessellator.addVertexWithUV(-8, -3.625, 0.0001, stringLeft, stringTop);
	        tessellator.addVertexWithUV(8, -3.625, 0.0001, stringRight, stringTop);
	        tessellator.addVertexWithUV(8, 3.625, 0.0001, stringRight, stringBottom);
	        tessellator.addVertexWithUV(-8, 3.625, 0.0001, stringLeft, stringBottom);
	        tessellator.draw();
	
	        for (int i = 0; i < 4; ++i)
	        {
	            GL11.glRotatef(90.0F, 1.0F, 0.0F, 0.0F);
	            GL11.glNormal3f(0.0F, 0.0F, scale);
	            tessellator.startDrawingQuads();
	            tessellator.addVertexWithUV(-8, -2, 0, arrowLeft, arrowTop);
	            tessellator.addVertexWithUV(8, -2, 0, arrowRight, arrowTop);
	            tessellator.addVertexWithUV(8, 2, 0, arrowRight, arrowBottom);
	            tessellator.addVertexWithUV(-8, 2, 0, arrowLeft, arrowBottom);
	            tessellator.draw();
	        }
	
	        //GL11.glDisable(GL12.GL_RESCALE_NORMAL);
	        GL11.glDisable(GL11.GL_CULL_FACE);
        }
        
        GL11.glPopMatrix();
	}
	
    public void drawAABB(double minX, double minY, double minZ, double maxX, double maxY, double maxZ)
    {
        GL11.glEnable(GL11.GL_BLEND);
        GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
        GL11.glColor4f(0.0F, 0.0F, 0.0F, 0.4F);
        GL11.glLineWidth(2.0F);
        GL11.glDisable(GL11.GL_TEXTURE_2D);
        GL11.glDepthMask(false);
        
        Tessellator tessellator = Tessellator.instance;
        tessellator.startDrawing(3);
        tessellator.addVertex(minX, minY, minZ);
        tessellator.addVertex(maxX, minY, minZ);
        tessellator.addVertex(maxX, minY, maxZ);
        tessellator.addVertex(minX, minY, maxZ);
        tessellator.addVertex(minX, minY, minZ);
        tessellator.draw();
        tessellator.startDrawing(3);
        tessellator.addVertex(minX, maxY, minZ);
        tessellator.addVertex(maxX, maxY, minZ);
        tessellator.addVertex(maxX, maxY, maxZ);
        tessellator.addVertex(minX, maxY, maxZ);
        tessellator.addVertex(minX, maxY, minZ);
        tessellator.draw();
        tessellator.startDrawing(1);
        tessellator.addVertex(minX, minY, minZ);
        tessellator.addVertex(minX, maxY, minZ);
        tessellator.addVertex(maxX, minY, minZ);
        tessellator.addVertex(maxX, maxY, minZ);
        tessellator.addVertex(maxX, minY, maxZ);
        tessellator.addVertex(maxX, maxY, maxZ);
        tessellator.addVertex(minX, minY, maxZ);
        tessellator.addVertex(minX, maxY, maxZ);
        tessellator.draw();

        GL11.glDepthMask(true);
        GL11.glEnable(GL11.GL_TEXTURE_2D);
        GL11.glDisable(GL11.GL_BLEND);
    }
	
    private void drawAABB(AxisAlignedBB bb)
    {
        drawAABB(bb.minX, bb.minY, bb.minZ, bb.maxX, bb.maxY, bb.maxZ);
    }
	
    public void renderArrow(EntityArrow arrow, double offX, double offY, double offZ, float par8, float partialTick)
    {
    	EntityQuiverModArrowNew modArrow = null;
    	
    	if (arrow instanceof EntityQuiverModArrowNew)
    		modArrow = (EntityQuiverModArrowNew)arrow;
    	
    	boolean isModArrow = modArrow != null;
    	
    	double diffX = renderManager.viewerPosX - arrow.posX;
    	double diffY = renderManager.viewerPosY - arrow.posY;
    	double diffZ = renderManager.viewerPosZ - arrow.posZ;
    	
    	int distanceSqr = (int)(diffX * diffX + diffY * diffY + diffZ * diffZ);
    	
    	if (distance3DSqr == -1)
    	{
    		distance3DSqr = ConfigofJustice.fancyArrowDistance * ConfigofJustice.fancyArrowDistance;
    	}
    	
    	boolean fancy = BAR.proxy.mc.gameSettings.fancyGraphics && distanceSqr < distance3DSqr;
    	
        GL11.glPushMatrix();
        
        if (isModArrow && modArrow.slowMo > 0)
        {
	    	partialTick += (float)(modArrow.actualTicks % modArrow.slowMo);
	    	partialTick /= (float)modArrow.slowMo;
	    	
        	offX = (arrow.prevPosX + (arrow.posX - arrow.prevPosX) * partialTick) - renderManager.viewerPosX;
        	offY = (arrow.prevPosY + (arrow.posY - arrow.prevPosY) * partialTick) - renderManager.viewerPosY;
        	offZ = (arrow.prevPosZ + (arrow.posZ - arrow.prevPosZ) * partialTick) - renderManager.viewerPosZ;
        }
        //TODO some slomo thing?
        
        GL11.glTranslated(offX, offY, offZ);
        
        GL11.glPushMatrix();
        GL11.glRotatef(arrow.prevRotationYaw + (arrow.rotationYaw - arrow.prevRotationYaw) * partialTick - 90.0F, 0.0F, 1.0F, 0.0F);
        
        float pitch = arrow.prevRotationPitch + (arrow.rotationPitch - arrow.prevRotationPitch) * partialTick;
        GL11.glRotatef(pitch, 0.0F, 0.0F, 1.0F);
        
        if (isModArrow)
        	GL11.glScalef(modArrow.arrowLength, 1, 1);
        
        //TODO arrow length?
        float shake = (float)arrow.arrowShake - partialTick;
    	
        if (shake > 0.0F)
        {
            float shakeRot = -MathHelper.sin(shake * 3.0F) * shake;
            GL11.glRotatef(shakeRot, 0.0F, 0.0F, 1.0F);
        }

    	renderArrowWithTex(arrowLoc, fancy);
    	
    	if (!isModArrow)
    	{
    		GL11.glPopMatrix();
    		GL11.glPopMatrix();
    		return;
    	}
        
        if (modArrow.type=="impactarrow")
        {
        	renderArrowWithTex(impactArrowLoc, fancy);
        	
        	
        }
        if (modArrow.type=="what am I supposed to type here?")
    		//TODO ^
    	{
        	renderArrowWithTex(timedArrowLoc, fancy);
    	}
        
    	double motionX = arrow.posX - arrow.prevPosX;
    	double motionY = arrow.posY - arrow.prevPosY;
    	double motionZ = arrow.posZ - arrow.prevPosZ;
        
        double motionX = arrow.motionX;
        double motionY = arrow.motionY;
        double motionZ = arrow.motionZ;
        
    	double motion = 0;
    	
    	if (!modArrow.inGround)
    	{
    		motion = MathHelper.sqrt_double(motionX * motionX + motionY * motionY + motionZ * motionZ);
    	}
    	
    	if (motion < 0.2)
    		motion = 0.2;
    	
        if (arrow.isBurning())
        {
        	GL11.glPushMatrix();
        	
            float vertRotMult = (float)motion;
            
            if (vertRotMult > 1)
            	vertRotMult = 1;
            
            vertRotMult = 1 - vertRotMult;
            
            vertRotMult = modArrow.prevFireVerticalRot + (vertRotMult - modArrow.prevFireVerticalRot) * (0.05F / (modArrow.slowMo > 0 ? modArrow.slowMo : 1));
            modArrow.prevFireVerticalRot = vertRotMult;
            
            GL11.glTranslatef(0.1F, 0, 0);
            GL11.glRotatef(-(pitch + 90) * vertRotMult, 0, 0, 1);
            GL11.glTranslatef(-0.1F, 0, 0);
        	
        	if (motion < 0.5)
        		motion = 0.5;
        	
        	int fireFrame = (int)(((modArrow.ticks + partialTick) * motion) % fireArrowLocs.length);
        	
        	if (fireFrame < 0)
        		fireFrame = 0;
        	else if (fireFrame >= fireArrowLocs.length)
        		fireFrame = fireArrowLocs.length - 1;
        	
        	GL11.glDisable(GL11.GL_LIGHTING);
        	renderArrowWithTex(fireArrowLocs[fireFrame], fancy);
        	GL11.glEnable(GL11.GL_LIGHTING);
        	
        	GL11.glPopMatrix();
        }
        
        if (modArrow.type=="enderarrow")
        {
        	renderArrowWithTex(enderArrowLoc, fancy);
        }
        
        if (modArrow.type=="torcharrow")
        {
        	renderArrowWithTex(torchArrowLoc, fancy);
        }
        
        if (modArrow.splitArrowCount > 1)
        {
        	renderArrowWithTex(splittingArrowLoc, fancy);
        }
        
        if (modArrow.type=="drillarrow")
        {
        	if (modArrow.isDrillBroken())
        	{
	        	renderArrowWithTex(brokenDrillArrowLoc, fancy);
        	}
        	else
        	{
	        	int frame = 0;
	        	
	        	if (!modArrow.inGround)
	        		frame = modArrow.ticks % 2;
	        	
	        	renderArrowWithTex(drillArrowLocs[frame], fancy);
        	}
        }
        
        if (modArrow.potionDamage != -1)
        {
        	GL11.glPushMatrix();
        	GL11.glScalef(1.01F, 1.01F, 1.01F);
        	
        	int color = ModItems.PotArrow.getColorFromDamage(modArrow.potionDamage);
			ColorThing.glSetColor(color);
			
			if (modArrow.type=="splashpotarrow")
			{
		    	renderArrowWithTex(splashPotionArrowInnerLoc, fancy);
		    	
		    	GL11.glColor4f(1, 1, 1, 1);
		    	renderArrowWithTex(splashPotionArrowOuterLoc, fancy);
			}
			else
			{
		    	renderArrowWithTex(potionArrowInnerLoc, fancy);
		    	
		    	GL11.glColor4f(1, 1, 1, 1);
		    	renderArrowWithTex(potionArrowOuterLoc, fancy);
			}

            GL11.glPopMatrix();
        }
        
        GL11.glPushMatrix();
        
        GL11.glRotatef(90, 0, 1, 0);
        GL11.glTranslatef(0, -0.15F, 0);
        
        for (ImpaledItem impaled : modArrow.impaledItems)
        {
            GL11.glPushMatrix();
            
            float position = impaled.getPosition(partialTick); 
            GL11.glTranslatef(0, 0, -position);
            
            impaled.item.posX = arrow.posX;
            impaled.item.posY = arrow.posY;
            impaled.item.posZ = arrow.posZ;
            
        	RenderManager.instance.renderEntityWithPosYaw(impaled.item, 0, 0, 0, 0, 0);
        	
            GL11.glPopMatrix();
        }

        GL11.glPopMatrix();

        GL11.glPopMatrix();
        
        GL11.glPopMatrix();

        GL11.glPushMatrix();
        GL11.glTranslatef((float)-RenderManager.renderPosX, (float)-RenderManager.renderPosY, (float)-RenderManager.renderPosZ);
        GL11.glDisable(GL11.GL_DEPTH_TEST);
        if (modArrow.renderBB)
        {
        	drawAABB(arrow.boundingBox);
        	drawAABB(modArrow.renderBBMinX, modArrow.renderBBMinY, modArrow.renderBBMinZ,
        			modArrow.renderBBMaxX, modArrow.renderBBMaxY, modArrow.renderBBMaxZ);
        }
        GL11.glEnable(GL11.GL_DEPTH_TEST);
        GL11.glPopMatrix();
        
        //loadTexture("nothing");
        //this.renderAABB(arrow.boundingBox.copy().offset(-arrow.posX, -arrow.posY, -arrow.posZ).offset(offX, offY, offZ));
    }

	*//**
     * Actually renders the given argument. This is a synthetic bridge method, always casting down its argument and then
     * handing it off to a worker function which does the actual work. In all probabilty, the class Render is generic
     * (Render<T extends Entity) and this method has signature public void doRender(T entity, double d, double d1,
     * double d2, float f, float f1). But JAD is pre 1.5 so doesn't do that.
     *//*
    public void doRender(Entity entity, double offX, double offY, double offZ, float par8, float partialTick)
    {
        renderArrow((EntityArrow)entity, offX, offY, offZ, par8, partialTick);
    }

	@Override
	protected ResourceLocation getEntityTexture(Entity entity)
	{
		return null;
	}
    
    *//**
     * Renders the entity's shadow and fire (if its on fire). Args: entity, x, y, z, yaw, partialTickTime
     *//*
    public void doRenderShadowAndFire(Entity par1Entity, double par2, double par4, double par6, float par8, float par9)
    {
    	if (par1Entity instanceof EntityArrow)
    	{
    		EntityArrow arrow = (EntityArrow)par1Entity;
    		
	        if (this.renderManager.options.fancyGraphics && this.shadowSize > 0.0F)
	        {
	            double d3 = this.renderManager.getDistanceToCamera(par1Entity.posX, par1Entity.posY, par1Entity.posZ);
	            float f2 = (float)((1.0D - d3 / 256.0D) * (double)this.shadowOpaque);
				
	            if (f2 > 0.0F)
	            {
	                //super.renderShadow(par1Entity, par2, par4, par6, f2, par9);
	            }
	        }
    	}
    }
    
}
*/
//TODO shelved for now