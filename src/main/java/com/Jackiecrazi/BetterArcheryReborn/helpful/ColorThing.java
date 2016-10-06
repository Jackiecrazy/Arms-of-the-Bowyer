	package com.Jackiecrazi.BetterArcheryReborn.helpful;

	import org.lwjgl.opengl.GL11;

	public class ColorThing {

		public static int getRed(int color) {
	        return LittleBittah.getInteger(color, 16, 23);
		}
		
		public static int getGreen(int color) {
	        return LittleBittah.getInteger(color, 8, 15);
		}
		
		public static int getBlue(int color) {
	        return LittleBittah.getInteger(color, 0, 7);
		}

		public static float getFloatRed(int color) {
	        return getRed(color) / 255F;
		}
		
		public static float getFloatGreen(int color) {
	        return getGreen(color) / 255F;
		}
		
		public static float getFloatBlue(int color) {
	        return getBlue(color) / 255F;
		}

		public static void glSetColor(int color) {
			GL11.glColor4f(getFloatRed(color), getFloatGreen(color), getFloatBlue(color), 1);
		}

		public static int getColor(int red, int green, int blue) {
			int out = LittleBittah.setInteger(0, 16, 23, red);
			out = LittleBittah.setInteger(out, 8, 15, green);
			out = LittleBittah.setInteger(out, 0, 7, blue);
			return out;
		}


}
