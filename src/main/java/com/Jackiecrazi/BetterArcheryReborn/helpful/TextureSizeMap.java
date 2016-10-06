package com.Jackiecrazi.BetterArcheryReborn.helpful;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;

import javax.imageio.ImageIO;

import com.Jackiecrazi.BetterArcheryReborn.BAR;

import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.client.resources.IResource;
import net.minecraft.client.resources.IResourceManager;
import net.minecraft.util.ResourceLocation;

public class TextureSizeMap {
	
	public static class TextureSize
	{
		public int width;
		public int height;
		
		public TextureSize(int width, int height)
		{
			this.width = width;
			this.height = height;
		}
	}
	
	private static HashMap<ResourceLocation, TextureSize> sizesMap = new HashMap();
	
	public static void clear()
	{
		sizesMap.clear();
	}
	
	public static TextureSize getSize(ResourceLocation textureLocation, int defaultW, int defaultH)
	{
		TextureSize size = sizesMap.get(textureLocation);
		
		if (size == null)
		{
			InputStream inputstream = null;
			
			try
			{
				IResourceManager resMan = BAR.proxy.mc.getResourceManager();
	            IResource resource = resMan.getResource(textureLocation);
	            inputstream = resource.getInputStream();
	
	            if (inputstream != null)
	            {
	                BufferedImage bufferedImage = ImageIO.read(inputstream);
	                inputstream.close();
	                size = new TextureSize(bufferedImage.getWidth(), bufferedImage.getHeight());
	                sizesMap.put(textureLocation, size);
	            }
			}
			catch (Exception e) {
				BAR.log("An error occurred getting the size of a texture. Please post this log on the Better Archery Reborn thread on the Minecraft forums.");
				e.printStackTrace();
				size = null;
			}
		}
		
		if (size != null)
			return size;
		else
			return new TextureSize(defaultW, defaultH);
	}
	
}
