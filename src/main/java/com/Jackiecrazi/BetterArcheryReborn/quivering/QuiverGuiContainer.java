package com.Jackiecrazi.BetterArcheryReborn.quivering;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;

import org.lwjgl.opengl.GL11;





import com.Jackiecrazi.BetterArcheryReborn.Items.ModItems;

import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.InventoryBasic;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.StatCollector;

public class QuiverGuiContainer extends GuiContainer {
	
	public static ResourceLocation backgroundLocation = new ResourceLocation("quivermod:textures/gui/quivergui.png");
	
	InventoryPlayer playerInventory;
	QuiverInventory inv;
	
	GuiButton removeAll;
	
	protected ItemStack titleItem;
	
	public QuiverGuiContainer(InventoryPlayer playerInv, QuiverInventory quiverInv)
	{
		super(new QuiverContainer(playerInv, quiverInv));
		playerInventory = playerInv;
		inv = quiverInv;
		
		titleItem = new ItemStack(ModItems.quiver);
	}
	
	@Override
	public void initGui()
	{
		super.initGui();
		
		removeAll = new GuiButton(0, (width - 20) / 2, guiTop + 36, 20, 20, "V");
		
		buttonList.add(removeAll);
	}
	
    protected void actionPerformed(GuiButton button)
    {
        /*if (button.id == removeAll.id)
        {
	        ((QuiverContainer)inventorySlots).transferAllStacks();
	        
            ByteArrayOutputStream byteOutputStream = new ByteArrayOutputStream(3);
            EntityPlayer player = playerInventory.player;
            
            try {
            	DataOutputStream outputStream = new DataOutputStream(byteOutputStream);
                outputStream.writeInt(inv.playerInvIndex);
            }
            catch (Exception ex) {
                ex.printStackTrace();
            }

	    	Packet250CustomPayload packet = new Packet250CustomPayload();
	    	packet.channel = "RemoveAllArrows";
			packet.data = byteOutputStream.toByteArray();
			packet.length = byteOutputStream.size();
			
	        PacketDispatcher.sendPacketToServer(packet);
        }*/
    	//TODO readd the dump button some time
    }
    
    @Override
    protected void drawGuiContainerForegroundLayer(int param1, int param2)
    {
        fontRendererObj.drawString(titleItem.getDisplayName(), 8, 6, 4210752);
        fontRendererObj.drawString(StatCollector.translateToLocal("container.inventory"), 8, ySize - 111, 4210752);
    }
    
    private void drawSlot(int x, int y, int u, int v)
    {
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        Tessellator tessellator = Tessellator.instance;
        tessellator.startDrawingQuads();
        tessellator.addVertexWithUV(x, y + 18, zLevel, (float)u * 0.0078125F, (float)(v + 18) * 0.0078125F);
        tessellator.addVertexWithUV(x + 18, y + 18, zLevel, (float)(u + 18) * 0.0078125F, (float)(v + 18) * 0.0078125F);
        tessellator.addVertexWithUV(x + 18, y, zLevel, (float)(u + 18) * 0.0078125F, (float)v * 0.0078125F);
        tessellator.addVertexWithUV(x, y, zLevel, (float)u * 0.0078125F, (float)v * 0.0078125F);
        tessellator.draw();
    }
    
    @Override
    protected void drawGuiContainerBackgroundLayer(float par1, int par2, int par3)
    {
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        
        int x = (width - xSize) / 2;
        int y = (height - ySize) / 2;
        
        mc.renderEngine.bindTexture(backgroundLocation);
        drawTexturedModalRect(x, y, 0, 0, xSize, ySize);
        
    	mc.renderEngine.bindTexture(statIcons);
    	
        x = (width - (inv.size * 18)) / 2;
        y += 15;
        
        for (int i = 0; i < inv.size; i++)
        {
        	drawSlot(x, y, 0, 0);
        	
        	x += 18;
        }
    }
    
}
