package com.Jackiecrazi.BetterArcheryReborn;

import org.lwjgl.input.Keyboard;

import com.Jackiecrazi.BetterArcheryReborn.dumbpackets.FirstMessage;
import com.Jackiecrazi.BetterArcheryReborn.dumbpackets.SecondMessage;
import com.Jackiecrazi.BetterArcheryReborn.helpful.RepetitiveSnippets;

import net.minecraft.client.Minecraft;
import net.minecraft.client.settings.KeyBinding;
import net.minecraftforge.common.MinecraftForge;
import cpw.mods.fml.client.registry.ClientRegistry;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.InputEvent.KeyInputEvent;

public class BARBinds {
	public static final KeyBinding switchArrowForward =
			new KeyBinding("key.BAR.switchArrowForward", Keyboard.KEY_EQUALS, "key.categories.gameplay");
	public static final KeyBinding switchArrowBackwards =
			new KeyBinding("key.BAR.switchArrowBackwards", Keyboard.KEY_MINUS, "key.categories.gameplay");
	public static final KeyBinding switchQuiverMount =
			new KeyBinding("key.BAR.switchQuiverMount", Keyboard.KEY_BACK, "key.categories.gameplay");
	
	private static final KeyBinding[] bindings = new KeyBinding[]{ switchArrowForward, switchArrowBackwards,switchQuiverMount };
	
	public BARBinds() {
		MinecraftForge.EVENT_BUS.register(this);
		FMLCommonHandler.instance().bus().register(this);
		
		ClientRegistry.registerKeyBinding(switchArrowForward);
		ClientRegistry.registerKeyBinding(switchArrowBackwards);
		ClientRegistry.registerKeyBinding(switchQuiverMount);
	}
	@SubscribeEvent
	public void pressKey(KeyInputEvent e){
		if(switchArrowForward.isPressed()){
			int value=RepetitiveSnippets.getSelectedArrowItem(Minecraft.getMinecraft().thePlayer);
			int tops=RepetitiveSnippets.getArrowTypesHeld(Minecraft.getMinecraft().thePlayer).size();
			if(value==tops)
			//else RepetitiveSnippets.setSelectedArrowItem(Minecraft.getMinecraft().thePlayer, 0);
			value=-1;
			BAR.net.sendToServer(new FirstMessage(value+1));
			RepetitiveSnippets.setSelectedArrowItem(Minecraft.getMinecraft().thePlayer, value+1);
		}
		if(switchArrowBackwards.isPressed()){
			int value=RepetitiveSnippets.getSelectedArrowItem(Minecraft.getMinecraft().thePlayer);
			if(value==0)
			value=4;
			
			BAR.net.sendToServer(new FirstMessage(value-1));
			RepetitiveSnippets.setSelectedArrowItem(Minecraft.getMinecraft().thePlayer, value-1);
		}
		if(switchQuiverMount.isPressed()){
			int quiver=RepetitiveSnippets.getWornQuiverType(Minecraft.getMinecraft().thePlayer);
			int newValue=0;
			switch(quiver){
			case 0: newValue=1;
			break;
			case 1: newValue=0;
			break;
			default: newValue=1;
			break;
			}
			BAR.net.sendToServer(new SecondMessage(newValue));
			RepetitiveSnippets.setWornQuiverType(Minecraft.getMinecraft().thePlayer, newValue);
		}
	}
}
