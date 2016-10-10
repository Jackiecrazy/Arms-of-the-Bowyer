package com.Jackiecrazi.BetterArcheryReborn.dumbpackets;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;

import com.Jackiecrazi.BetterArcheryReborn.helpful.RepetitiveSnippets;

import io.netty.buffer.ByteBuf;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;

public class SecondMessage implements IMessage{

	private int quiver;
	public SecondMessage(){}
	public SecondMessage(int h){
		this.quiver=h;
	}
	@Override
	public void fromBytes(ByteBuf buf) {
		// TODO Auto-generated method stub
		this.quiver=buf.readInt();
	}

	@Override
	public void toBytes(ByteBuf buf) {
		// TODO Auto-generated method stub
		buf.writeInt(quiver);
	}
	public static class Handler implements IMessageHandler<SecondMessage, IMessage>{

		@Override
		public IMessage onMessage(SecondMessage message, MessageContext ctx) {
			EntityPlayer player = (ctx.side.isClient() ? Minecraft.getMinecraft().thePlayer : ctx.getServerHandler().playerEntity);
			RepetitiveSnippets.setWornQuiverType(player, message.quiver);
			return null;
		}
		
	}

	

}
