package com.Jackiecrazi.BetterArcheryReborn;

import java.io.File;
import java.util.HashMap;

import net.minecraftforge.common.config.Configuration;

import org.apache.logging.log4j.LogManager;

public class ConfigofJustice {
	private static HashMap<String, String> comments = new HashMap(){{
		put("entityTorchBlock", "Block IDs");
		
		put("bow", "Item IDs");
		
		put("useChunkLoader", "Whether to use a chunk loader for the ender arrows to keep them moving when leaving loaded chunks. May cause severe lag with multiple arrows in the air.");
		put("arrowSlowMo", "How many ticks it should take for an arrow to complete one tick. 0 = No slowmo, 2 = Half-speed");
		put("offsetArrows", "Whether the arrows should be offset to the right to match the position of the bow in first person.");
		put("allowSkeletonGriefing", "Whether skeletons should be allowed to spawn with fire arrows or impact explosive arrows (in hard mode).");
		put("allowSkeletonPotionArrows", "Whether skeletons should be allowed to spawn with potion arrows.");
		put("arrowKnockbackMode", "0 = Vanilla arrow knockback, 1 = The higher the armor, the less the knockback, 2 = The less the armor, the less the knockback.");
		put("replaceVanillaTorchPlacing", "Whether to replace the vanilla torch item with one that places torches wherever on a block you right-click, like torch arrows. Must be the same on the server and client to join.");
		put("bowInfinityMode", "0 = Vanilla, 1 = Per-arrow chance, 2 = 25% chance.");

		put("highResIcons", "Whether the 32x32 textures for large bows should be used for non-equipped bows.");
		put("animationStyle", "The animation style to use for selected arrows. 0 = bouncing, 1 = bounce once, 2 = move up, stop");
		put("fancyArrowDistance", "The distance at which arrows should stop being rendered in 3D.");
		put("renderQuiverIn3DDistance", "The distance at which quivers on players should stop being rendered with proper sides.");
		put("quiverHotbarVerticalOffset", "The amount (in pixels) to offset the quiver hotbar. Higher = lower.");
	}};
	
	public static boolean settingsLoaded = false;
	
	public static String configFilePath;
	public static Configuration config;
	
	/* General */
	public static boolean useChunkLoader = true;
	public static boolean baublesIntegration=true;
	public static int slowMoSetting = 0;
	public static boolean offsetArrows = true;
	public static boolean allowSkeletonGriefing = false;
	public static boolean allowSkeletonPotionArrows = true;
	public static int arrowKnockbackMode = 0;
	
	public static boolean replaceVanillaTorchPlacing = false;
	public static boolean removeVanillaBowCrafting = true;
	public static String tileEntityTorchID = "TileEntityTorch";

	public static int bowInfinityMode = 1;

	/* Client stuff */
	public static boolean highResIcons = false;
	public static int animationStyle = 0;
	
	public static int fancyArrowDistance = 50;
	
	public static int renderQuiverIn3DDistance = 50;

	public static int quiverHotbarVerticalOffset = 0;
	
	public static void CreatioExNihilo(File dumdum){
		Configuration c=new Configuration(dumdum);
		configFilePath=c.toString();
		try{
			c.load();
			animationStyle=c.getInt("Animation Style", "Renders", 0, 0, 1, "what type of animation is it?");
			baublesIntegration=c.getBoolean("Baubles Integration", "Mod Compatibility", true, "Enable Baubles Integration(finger tab, thumb ring)");
		}
		catch(Exception er){
			LogManager.getLogger("BAR").fatal("You have done something pretty hard (easy) to do. Here's an error.");
			er.printStackTrace();
		}
		finally{
			c.save();
			settingsLoaded=true;
		}
	}
}
