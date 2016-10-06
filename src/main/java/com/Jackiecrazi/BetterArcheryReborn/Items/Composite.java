package com.Jackiecrazi.BetterArcheryReborn.Items;

import org.lwjgl.opengl.GL11;


public class Composite extends QuiverBow
{

	public Composite() {
        
        setMaxDamage(1024);
        setUnlocalizedName("CompositeBow");
		
		pullbackMult = 0.5F;
		damageMult = 1.25F;
		arrowspeedMult = 0.65F;
		zoomMult = 0.8F;
		rangeMult = 1;

		arrowStep = 2;
	}
	
	/*@Override
	public float getSlow()
	{
		return 0.6F;
	}*/

    @Override
    public void doPreTransforms(boolean thirdPerson, boolean ifp, float px, float partialTick)
    {
    	GL11.glTranslatef(-2 * px, -2 * px, 0);
    	
    	if (!thirdPerson)
    		GL11.glTranslatef(0, 0, -0.01F);
    }

    @Override
    public void doBowTransforms(boolean thirdPerson, boolean ifp, float px, float partialTick)
    {
    	GL11.glTranslatef(2 * px, 2 * px, 0);
    	
    	if (!thirdPerson)
    		GL11.glTranslatef(0, 0, 0.01F);
    }
}