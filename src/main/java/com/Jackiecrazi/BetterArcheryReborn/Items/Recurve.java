package com.Jackiecrazi.BetterArcheryReborn.Items;

public class Recurve extends QuiverBow {
	public Recurve(){
		this.arrowspeedMult=1.25F;
		this.setUnlocalizedName("RecurveBow");
		this.type=this;
		this.setMaxDamage(256);
		this.pullbackMult=0.9F;
		this.zoomMult=1.35F;
		this.rangeMult=1.25F;
		this.damageMult=1.1F;
	}
}
