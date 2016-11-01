package com.Jackiecrazi.BetterArcheryReborn.Items;

import org.lwjgl.opengl.GL11;

public class Longbow extends QuiverBow {
	float firstPersonScale = 2;
	public Longbow(){
		this.arrowspeedMult=1.5F;
		this.setUnlocalizedName("Longbow");
		this.type=this;
		this.pullbackMult=1.5F;
		this.zoomMult=1.75F;
		this.rangeMult=1.75F;
		this.animIconCount=7;
		iconSize = 2;
		scale = 2 * 0.8F;
		scaleOffX = 17;
		scaleOffY = 17;
		arrowStep = 1;
	}
	@Override
    public void doPreTransforms(boolean thirdPerson, boolean ifp, float px, float partialTick)
    {
    	if (!ifp)
    	{
	    	if (thirdPerson)
	    	{
	        	GL11.glRotatef(15, 1, 1, 0);
	    		//GL11.glScalef(0.8F, 0.8F, 1);
	    		//GL11.glTranslatef(-0.5F, -0.5F, 0);
	    	}
	    	else
	    	{
	        	GL11.glTranslatef(0, 0.1F, 0.2F);
	    	}
    	}
    }
	public float getScale(boolean thirdPerson)
    {
    	if (thirdPerson)
    		return scale;
    	else
    		return firstPersonScale;
    }
	public String getBowArrowIconName()
    {
    	return "longbow";
    }
}
