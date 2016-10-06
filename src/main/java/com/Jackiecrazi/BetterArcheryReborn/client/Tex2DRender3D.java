package com.Jackiecrazi.BetterArcheryReborn.client;

import org.lwjgl.opengl.GL11;

import net.minecraft.client.renderer.ItemRenderer;
import net.minecraft.client.renderer.Tessellator;

public class Tex2DRender3D {

	private static final Tessellator tessellator = Tessellator.instance;
	private static final float nonFancyEdgeOff = 0.0025F;
	private static final float nonFancyEdgeOff2 = nonFancyEdgeOff * 2;
	
	public static void renderTwoPlanes(float left, float top, float right, float bottom, float thickness)
	{
        tessellator.startDrawingQuads();
        tessellator.setNormal(0, 0, 1);
        tessellator.addVertexWithUV(0, 0, 0, right, bottom);
        tessellator.addVertexWithUV(1, 0, 0, left, bottom);
        tessellator.addVertexWithUV(1, 1, 0, left, top);
        tessellator.addVertexWithUV(0, 1, 0, right, top);
        tessellator.draw();
        tessellator.startDrawingQuads();
        tessellator.setNormal(0, 0, -1);
        tessellator.addVertexWithUV(0, 1, -thickness, right, top);
        tessellator.addVertexWithUV(1, 1, -thickness, left, top);
        tessellator.addVertexWithUV(1, 0, -thickness, left, bottom);
        tessellator.addVertexWithUV(0, 0, -thickness, right, bottom);
        tessellator.draw();
	}
	
	public static void render(boolean fancy, float left, float top, float right, float bottom,
			int texWidth, int texHeight, float thickness)
	{
		
		if (fancy)
		{
			ItemRenderer.renderItemIn2D(tessellator, right, top, left, bottom, texWidth, texHeight, thickness);
		}
		else
		{
	        GL11.glDisable(GL11.GL_CULL_FACE);
			thickness -= nonFancyEdgeOff2;
	        
			for (float posY = nonFancyEdgeOff; posY <= thickness + nonFancyEdgeOff2; posY += thickness / 4)
			{
		        tessellator.startDrawingQuads();
		        tessellator.setNormal(0.0F, 0.0F, 1.0F);
		        tessellator.addVertexWithUV(0, 1, -posY, right, top);
		        tessellator.addVertexWithUV(1, 1, -posY, left, top);
		        tessellator.addVertexWithUV(1, 0, -posY, left, bottom);
		        tessellator.addVertexWithUV(0, 0, -posY, right, bottom);
		        tessellator.draw();
			}
		}
	}
	
}
