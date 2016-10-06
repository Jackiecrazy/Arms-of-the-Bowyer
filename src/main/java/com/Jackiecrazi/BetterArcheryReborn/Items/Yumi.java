package com.Jackiecrazi.BetterArcheryReborn.Items;

import org.lwjgl.opengl.GL11;

public class Yumi extends Longbow {
	public Yumi(){
		this.arrowspeedMult=1.5F;
		this.setUnlocalizedName("Yumi");
		this.type=this;
		this.setMaxDamage(384);
		this.pullbackMult=1.15F;
		this.zoomMult=1.45F;
		this.rangeMult=1.5F;
		this.damageMult=1F;
		this.animIconCount=5;
		iconSize = 2;
		scale = 2;
		firstPersonScale = 2;
		scaleOffX = 17;
		scaleOffY = 17;
	}
	@Override
    public void doPreTransforms(boolean thirdPerson, boolean ifp, float px, float partialTick)
    {
    	GL11.glTranslatef(1 * px, 1 * px, 0);
    	
    	if (!ifp)
    	{
	    	if (thirdPerson)
	    	{
	        	GL11.glTranslatef(-2 * px, 2 * px, 0);
	        	//GL11.glRotatef(-15, 1, 1, 0);
	        	//GL11.glRotatef(-8, 0, 0, 1);
	    	}
	    	else
	    	{
	        	//GL11.glTranslatef(0, 0.1F, 0.2F);
	    	}
    	}
    }

    @Override
    public void doBowTransforms(boolean thirdPerson, boolean ifp, float px, float partialTick)
    {
    	GL11.glTranslatef(-1 * px, -1 * px, 0);
    	
    	GL11.glTranslatef(-4 * px, 5 * px, 0);
    	
    	if (!ifp)
    	{
	    	if (thirdPerson)
	    	{
	        	//GL11.glRotatef(-15, 0, 0, 1);
	    	}
	    	else
	    	{
	        	//GL11.glTranslatef(0, 0.1F, 0.2F);
	    	}
    	}
    }

}
