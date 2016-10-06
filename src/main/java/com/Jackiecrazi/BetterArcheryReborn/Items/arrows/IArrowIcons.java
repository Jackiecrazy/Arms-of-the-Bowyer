package com.Jackiecrazi.BetterArcheryReborn.Items.arrows;

public interface IArrowIcons {

	public String getSpecialBowIconName(int damage);

	public String[] getSpecialBowIconNames();
	
	public float getFullBright();
	
	public int getFrameCount();
	
	public boolean getNeedsTwoPasses();

}
