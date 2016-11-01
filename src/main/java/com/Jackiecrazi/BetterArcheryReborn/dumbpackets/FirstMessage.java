package com.Jackiecrazi.BetterArcheryReborn.dumbpackets;

import com.Jackiecrazi.BetterArcheryReborn.helpful.RepetitiveSnippets;
import com.Jackiecrazi.BetterArcheryReborn.quivering.Quiver;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import io.netty.buffer.ByteBuf;
import cpw.mods.fml.common.network.ByteBufUtils;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;

public class FirstMessage implements IMessage{
	private int count;
	public FirstMessage(){}
	public FirstMessage(int h){
		this.count=h;
	}
	@Override
	public void fromBytes(ByteBuf buf) {
		// TODO Auto-generated method stub
		this.count=buf.readInt();
	}

	@Override
	public void toBytes(ByteBuf buf) {
		// TODO Auto-generated method stub
		buf.writeInt(count);
	}
	private static void clearBowUse(EntityPlayer player)
	{
		ItemStack heldItem = player.inventory.getCurrentItem();
		
		if (heldItem != null && heldItem.getItem() instanceof Quiver)
		{
			player.clearItemInUse();
		}
	}
	public static class Handler implements IMessageHandler<FirstMessage, IMessage>{

		@Override
		public IMessage onMessage(FirstMessage message, MessageContext ctx) {
			EntityPlayer player = (ctx.side.isClient() ? Minecraft.getMinecraft().thePlayer : ctx.getServerHandler().playerEntity);
			RepetitiveSnippets.setSelectedArrowItem(player, message.count);
			clearBowUse(player);
			return null;
		}
		
	}

}
