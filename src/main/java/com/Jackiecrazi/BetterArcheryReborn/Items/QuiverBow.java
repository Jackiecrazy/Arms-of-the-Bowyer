package com.Jackiecrazi.BetterArcheryReborn.Items;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.EnumAction;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBow;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.player.ArrowLooseEvent;
import net.minecraftforge.event.entity.player.ArrowNockEvent;
import baubles.common.lib.PlayerHandler;

import com.Jackiecrazi.BetterArcheryReborn.BAR;
import com.Jackiecrazi.BetterArcheryReborn.CommonProxy;
import com.Jackiecrazi.BetterArcheryReborn.Items.arrows.ItemQuiverModArrow;
import com.Jackiecrazi.BetterArcheryReborn.Items.arrows.PotionArrow;
import com.Jackiecrazi.BetterArcheryReborn.entities.EntityQuiverModArrowNew;
import com.Jackiecrazi.BetterArcheryReborn.helpful.BowArrowIcons;
import com.Jackiecrazi.BetterArcheryReborn.helpful.InventorySlot;
import com.Jackiecrazi.BetterArcheryReborn.helpful.InventorySlots;
import com.Jackiecrazi.BetterArcheryReborn.helpful.RepetitiveSnippets;
import com.Jackiecrazi.BetterArcheryReborn.quivering.Quiver;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class QuiverBow extends ItemBow{
	protected float pullbackMult = 0.75F;
	protected float arrowspeedMult = 1;
	protected float damageMult = 1;
	protected float zoomMult = 1;
	protected float rangeMult = 1;
	
	protected int animIconCount = 4;
	protected int iconSize = 1;
	protected float scale = 1;
	protected float scaleOffX = 0;
	protected float scaleOffY = 0;
	protected int arrowStep = 1;
	
	protected IIcon[] icons;
	public IIcon smallIcon;
	public IIcon smallStringIcon;
	
	protected int defaultMaxItemUse = 72000;
	protected int notchTime = 5;
	protected int noQuiverUsePenalty = 15;
	protected int accessoryBuff = 10;
	
	private IIcon[] iconArray;
	public QuiverBow type=this;
	private IIcon defaultStringIcon;
	private IIcon[] stringIcons;
	
	public QuiverBow(){
		this.setCreativeTab(BAR.BARBow);
		this.setUnlocalizedName("quiverBow");
		this.setMaxDamage(384);
		this.setFull3D();
	}
	public float getPullbackMult(){
		return pullbackMult;
	}
	public float getArrowspeedMult(){
		return arrowspeedMult;
	}
	public float getZoomMult(){
		return zoomMult;
	}
	public float getRangeMult(){
		return rangeMult;
	}
	public boolean isBroken(ItemStack is){
		return is.getItemDamage()==is.getMaxDamage()-1;
	}
	public float getDamageMult(){
		return damageMult;
	}
	public IIcon getStringIcon(int iconOffset)
    {
    	return stringIcons[iconOffset];
    }
	public float getUsePowerFromUseCount(float playerUseCount)
    {
    	return getUsePower(getMaxItemUseDuration(null) - playerUseCount); 
    }
	public boolean isArrow(ItemStack i){
		Item it = i.getItem();
		return it==ModItems.DerpArrow||it==ModItems.FireArrow||it==Items.arrow||it==ModItems.PotArrow||(it==ModItems.DrillArrow&&i.getItemDamage()!=16)||it==ModItems.EnderArrow||it==ModItems.TimedArrow||it==ModItems.ImpactArrow||it==ModItems.TorchArrow||(it==ModItems.quiver&&Quiver.getArrowCount(i)>0);
	}
	public boolean hasArrow(EntityPlayer p){
		for(ItemStack s: p.inventory.mainInventory){
			if(s!=null&&isArrow(s)){
				return true;
			}
		}
		if(BAR.BaublesLoaded){
			for(ItemStack s: PlayerHandler.getPlayerBaubles(p).stackList){
				if(s!=null &&isArrow(s))return true;
			}
		}
		return false;
	}
	public String getArrow(EntityPlayer player){
		String h=player.getEntityData().getString("BARusingArrow");
		return h;
		//TODO stuff
	}
	public int getSplit(EntityPlayer player){
		int h=player.getEntityData().getInteger("splitArrowCount");
		return h;
		//TODO stuff
	}
	public ItemStack getArrowStackFromInv(EntityPlayer player){
			for (ItemStack stack : player.inventory.mainInventory) {
				if(stack!=null&&stack.getItem()==ModItems.quiver){
					ArrayList<ItemStack> i=RepetitiveSnippets.getArrowTypesHeld(player);
					ItemStack h=null;
					try{
						h=i.get(RepetitiveSnippets.getSelectedArrowItem(player));
					}
					catch(IndexOutOfBoundsException e){
						return new ItemStack(ModItems.DerpArrow);
					}
					//TODO outofbounds because there is no arrow apparently
					return h.getItem()==Items.arrow||h==null? new ItemStack(ModItems.DerpArrow):h;
				}
				else if (stack != null && isArrow(stack)) {
					return stack;
			}
		}
		return new ItemStack(ModItems.DerpArrow);
	}
	public void onPlayerStoppedUsing(ItemStack p_77615_1_, World p_77615_2_, EntityPlayer p_77615_3_, int p_77615_4_)
    {
        float j = this.getMaxItemUseDuration(p_77615_1_) - p_77615_4_;

        ArrowLooseEvent event = new ArrowLooseEvent(p_77615_3_, p_77615_1_, (int)j);
        MinecraftForge.EVENT_BUS.post(event);
        if (event.isCanceled())
        {
            return;
        }
        j = event.charge;
        //May explain weird velocities
        
        InventorySlots arrowSlots = RepetitiveSnippets.getArrowSlot(p_77615_3_);
        int quiverIndex = -1;
        int arrowIndex = -1;
        ItemStack quiverStack = null; 
        ItemStack arrowStack = null;
        Item arrow=null;
        int reps=0;
        int tops=1;
        if (arrowSlots != null)
        {
        	InventorySlot quiverSlot = arrowSlots.get("quiver");
        	quiverStack = quiverSlot.stack;
        	quiverIndex = quiverSlot.index;
        	
        	InventorySlot arrowSlot = arrowSlots.get("arrow");
        	arrowStack = arrowSlot.stack;
        	arrowIndex = arrowSlot.index;
        	if(arrowStack.getItem() instanceof PotionArrow){
        		PotionArrow potarr=(PotionArrow)arrowStack.getItem();
        		tops=potarr.getSplittingArrowCount(arrowStack.getItemDamage());
        	}
        	else if(arrowStack.getItem()==Items.arrow){
        		//do what war is good for
        	}
        	else{
        		ItemQuiverModArrow norarr=(ItemQuiverModArrow)arrowStack.getItem();
        		tops=norarr.getSplittingArrowCount(arrowStack.getItemDamage());
        	}
        }
        
        boolean flag = p_77615_3_.capabilities.isCreativeMode || EnchantmentHelper.getEnchantmentLevel(Enchantment.infinity.effectId, p_77615_1_) > 0;
        boolean hasArrow=hasArrow(p_77615_3_);
        if ((flag || hasArrow)&&j>0)
        {
        	for(reps=0;reps<tops+1;reps++){
            float f = getUsePower(j);

            if ((double)f < 0.1D)
            {
                return;
            }

            if (f > 1.0F)
            {
                f = 1.0F;
            }
            Random r=new Random();
            EntityQuiverModArrowNew entityarrow = new EntityQuiverModArrowNew(p_77615_2_, p_77615_3_, f * (3.0F+r.nextFloat()) * arrowspeedMult).setType(this.getArrow(p_77615_3_)).setSpecialStuff(6.5F);
            entityarrow.setThrower((Entity)p_77615_3_);
            	if(reps>0){
            		double x=entityarrow.posX;
            		double y=entityarrow.posY;
            		double z=entityarrow.posZ;
            		float pitch=entityarrow.rotationPitch;
            		float yaw=entityarrow.rotationYaw;
            		double xnow=x;
            		double ynow=y;
            		double znow=z;
            		float pitchnow=pitch;
            		float yawnow=yaw;
            		xnow+=r.nextDouble();
            		xnow-=r.nextDouble();
            		ynow+=r.nextDouble();
            		ynow-=r.nextDouble();
            		znow+=r.nextDouble();
            		znow-=r.nextDouble();
            		xnow+=r.nextDouble();
            		xnow-=r.nextDouble();
            		ynow+=r.nextDouble();
            		ynow-=r.nextDouble();
            		znow+=r.nextDouble();
            		znow-=r.nextDouble();
            		entityarrow.setLocationAndAngles(xnow, ynow, znow, pitchnow, yawnow);
            	}
            if(this.getArrow(p_77615_3_).equals("potionarrow")||this.getArrow(p_77615_3_).equals("splashpotionarrow")){
            	List effects = Items.potionitem.getEffects(this.getArrowStackFromInv(p_77615_3_));
            	int nameUnloc=0;
                if (effects != null && !effects.isEmpty())
                {
                    nameUnloc = ((PotionEffect)effects.get(0)).getPotionID();
                }
                else
                {
                	nameUnloc = this.getArrowStackFromInv(p_77615_3_).getItemDamage();
                }
            	entityarrow.setDuration(((PotionEffect)effects.get(0)).getDuration());
            	entityarrow.setPotency(((PotionEffect)effects.get(0)).getAmplifier());
            	entityarrow.setSpecialStuff(nameUnloc);

            }
            if(this.getArrow(p_77615_3_).equals("firearrow"))entityarrow.setFire(10000);
            if (f == 1.0F)
            {
                entityarrow.setIsCritical(true);
            }

            int k = EnchantmentHelper.getEnchantmentLevel(Enchantment.power.effectId, p_77615_1_);

            if (k > 0)
            {
                entityarrow.setDamage(entityarrow.getDamage() + (double)k * 0.5D + 0.5D);
            }
            entityarrow.setDamage(entityarrow.getDamage()*getDamageMult());
            int l = EnchantmentHelper.getEnchantmentLevel(Enchantment.punch.effectId, p_77615_1_);

            if (l > 0)
            {
                entityarrow.setKnockbackStrength(l);
            }

            if (EnchantmentHelper.getEnchantmentLevel(Enchantment.flame.effectId, p_77615_1_) > 0)
            {
                entityarrow.setFire(100);
            }
            
            
            p_77615_2_.playSoundAtEntity(p_77615_3_, "random.bow", 1.0F, 1.0F / (itemRand.nextFloat() * 0.4F + 1.2F) + f * 0.5F);

            if (flag)
            {
                entityarrow.canBePickedUp = 2;
            }
            else if(reps==0)
            {
            	/*if(!RepetitiveSnippets.getQuivers(p_77615_3_).isEmpty()){
            		for(int pot=0;pot<p_77615_3_.inventory.getSizeInventory();pot++){
            			if(p_77615_3_.inventory.getStackInSlot(pot)!=null&&p_77615_3_.inventory.getStackInSlot(pot).getItem()==ModItems.quiver){
            				System.out.println(RepetitiveSnippets.getQuivers(p_77615_3_).get(0));
            				System.out.println(RepetitiveSnippets.getSelectedArrowItem(p_77615_3_));
            				Quiver.removeArrow(p_77615_3_, RepetitiveSnippets.getQuivers(p_77615_3_).get(0), pot, RepetitiveSnippets.getSelectedArrowItem(p_77615_3_));
            				break;
            			}
            		}
            	}*/
            	
            	if (quiverStack != null)
            	{
            		Quiver.removeArrow(p_77615_3_, quiverStack, quiverIndex, arrowIndex);
            	}
            	else
            	{
            		arrowStack.stackSize--;
            		
            		if (arrowStack.stackSize < 1)
            			p_77615_3_.inventory.setInventorySlotContents(arrowIndex, null);
            	}
            	
                p_77615_3_.inventory.consumeInventoryItem(getArrowStackFromInv(p_77615_3_).getItem());
                p_77615_1_.damageItem(1, p_77615_3_);
                p_77615_3_.addExhaustion(tops);
            }
            
            if (!p_77615_2_.isRemote)
            {
                p_77615_2_.spawnEntityInWorld(entityarrow);
                
            }
            }
        }
    }

    public ItemStack onEaten(ItemStack p_77654_1_, World p_77654_2_, EntityPlayer p_77654_3_)
    {
        return p_77654_1_;
    }

    /**
     * How long it takes to use or consume an item
     */
    public int getMaxItemUseDuration(ItemStack p_77626_1_)
    {
        return (int)(defaultMaxItemUse * getPullbackMult()) + notchTime ;
    }
    
    private int getItemUsePenalties(ItemStack stack, EntityPlayer player)
    {
    	int value = notchTime;
    	
    	InventorySlots arrowSlot = RepetitiveSnippets.getArrowQuiverSlot(player);
    	
    	if (arrowSlot == null)
    	{
    		value += noQuiverUsePenalty;
    	}
    	ItemStack acc=PlayerHandler.getPlayerBaubles(player).getStackInSlot(2);
    	if(acc!=null&&acc.getItem()==CommonProxy.acc||player.inventory.hasItem(CommonProxy.acc)) value-=accessoryBuff;
    	
    	value /= getPullbackMult();
    	
    	return value;
    }

    public int getMaxItemUseDuration(int penalties)
    {
        return (int)((defaultMaxItemUse + penalties) * getPullbackMult());
    }

    public int getMaxItemUseDuration(ItemStack stack, EntityPlayer player)
    {
        return getMaxItemUseDuration(getItemUsePenalties(stack, player));
    }
    /**
     * returns the action that specifies what animation to play when the items is being used
     */
    public EnumAction getItemUseAction(ItemStack p_77661_1_)
    {
        return EnumAction.bow;
    }

    /**
     * Called whenever this item is equipped and the right mouse button is pressed. Args: itemStack, world, entityPlayer
     */
    public ItemStack onItemRightClick(ItemStack is, World world, EntityPlayer p)
    {
    	if(is.getItemDamage()>=is.getMaxDamage()-1){
    		if(p.inventory.hasItem(ModItems.Bowstring)&&p.isSneaking()){
    		p.inventory.consumeInventoryItem(ModItems.Bowstring);
    		is.setItemDamage(0);
    		p.swingItem();
    		return is;
    		}
    		else return is;
    	}
    	else{
        ArrowNockEvent event = new ArrowNockEvent(p, is);
        MinecraftForge.EVENT_BUS.post(event);
        if (event.isCanceled())
        {
            return event.result;
        }

        if (p.capabilities.isCreativeMode || hasArrow(p))
        {
        	InventorySlots arrowSlot = RepetitiveSnippets.getArrowSlot(p);
            ItemStack arrowStack = null;
            
            if (arrowSlot != null)
            {
    	        arrowStack = arrowSlot.get("arrow").stack;
            }
        	if(arrowStack!=null){
        	if(arrowStack.getItem() instanceof ItemQuiverModArrow)p.getEntityData().setString("BARusingArrow", ((ItemQuiverModArrow)arrowStack.getItem()).getName());
        	else if(arrowStack.getItem() instanceof PotionArrow){
        		Item omg=arrowStack.getItem();
        		String sub="";
        		String hi=((PotionArrow)omg).getName();
        		if(PotionArrow.isSplash(omg.getDamage(arrowStack)))sub="splash";
        		p.getEntityData().setString("BARusingArrow", sub+hi);
        		
        	}

        	
        	if(arrowStack.getItem() instanceof ItemQuiverModArrow){
        		ItemQuiverModArrow h =(ItemQuiverModArrow) arrowStack.getItem();
        		if(h.isSplittingArrow(arrowStack.getItemDamage())){
        			p.getEntityData().setInteger("splitArrowCount", h.getSplittingArrowCount(arrowStack.getItemDamage()));
        		}
        		else p.getEntityData().setInteger("splitArrowCount", 1);
        	}
        	else if(arrowStack.getItem()==ModItems.PotArrow){
        		PotionArrow h =(PotionArrow) arrowStack.getItem();
        		if(h.isSplittingArrow(arrowStack.getItemDamage())){
        			p.getEntityData().setInteger("splitArrowCount", h.getSplittingArrowCount(arrowStack.getItemDamage()));
        		}
        		else p.getEntityData().setInteger("splitArrowCount", 1);
        	}
        	else p.getEntityData().setString("BARusingArrow", "arrow");
        	}
        	else p.getEntityData().setString("BARusingArrow", "arrow");
            p.setItemInUse(is, getMaxItemUseDuration(is, p));
        }

        return is;
    	}
    }
    public float getUsePower(float useDuration)
    {
    	useDuration /= getPullbackMult();
		float power = useDuration / 20F;
        power = (power * power + power * 2.0F) / 3.0F;
        
        if (power < 0)
        	return 0;
		
		if (power > 1)
			return 1;
		
		return power;
    }
    /**
     * Return the enchantability factor of the item, most of the time is based on material.
     */
    public int getItemEnchantability()
    {
        return 5;
    }

    @SideOnly(Side.CLIENT)
    public void registerIcons(IIconRegister p_94581_1_)
    {
        this.itemIcon = p_94581_1_.registerIcon(this.getIconString() + "0");
        this.iconArray = new IIcon[this.animIconCount];
        this.stringIcons=new IIcon[this.animIconCount];
        String name=this.getIconString();
        for (int i = 0; i < this.animIconCount; i++)
        {
            this.iconArray[i] = p_94581_1_.registerIcon(this.getIconString() + i);
            this.stringIcons[i]= p_94581_1_.registerIcon(this.getIconString()+"string" + i);
        }
        this.defaultStringIcon=this.stringIcons[0];
        BowArrowIcons.registerArrowIcons(this.getBowArrowIconName(), p_94581_1_);
    }

    /**
     * used to cycle through icons based on their used duration, i.e. for the bow
     */
    @SideOnly(Side.CLIENT)
    public IIcon getItemIconForUseDuration(int p_94599_1_)
    {
        return this.iconArray[(int)(p_94599_1_)];
    }
    @cpw.mods.fml.relauncher.SideOnly(cpw.mods.fml.relauncher.Side.CLIENT)
	  public void addInformation(ItemStack par1ItemStack, EntityPlayer par2EntityPlayer, List par3List, boolean par4)
	  {
	    if(par1ItemStack.getItemDamage()==par1ItemStack.getMaxDamage()-1){
	    	par3List.add("Broken");
	    }
	    else par3List.remove("Broken");
	  }
/*    public IIcon getIcon(ItemStack stack, int renderPass, EntityPlayer player, ItemStack usingItem, int useRemaining)
    {
      int j = stack.getMaxItemUseDuration() - useRemaining;
      if (usingItem == null) return itemIcon;
      if (j >= 34&&this.animIconCount>6)
      {
        return getItemIconForUseDuration(6);
      }
      if (j > 31&&this.animIconCount>5)
      {
        return getItemIconForUseDuration(5);
      }
      if (j > 25&&this.animIconCount>4)
      {
        return getItemIconForUseDuration(4);
      }
      
      if (j > 19)
      {
        return getItemIconForUseDuration(3);
      }
      
      if (j > 13)
      {
        return getItemIconForUseDuration(2);
      }

      if (j > 7)
      {
        return getItemIconForUseDuration(1);
      }

      if (j > 0)
      {
        return getItemIconForUseDuration(0);
      }
      return itemIcon;
    }*/
    public void doPreTransforms(boolean thirdPerson, boolean ifp, float px, float partialTick) {}
    public int getIconOffset(float power)
    {
    	//power = (float)Math.pow(power, 1.1);

        if(power == 1)
        	return animIconCount - 1;
		
		for (float i = animIconCount - 2; i > 1; i--)
		{
			if (power >= i / (animIconCount - 1))
			{
            	return (int)i;
			}
		}
        
        if(power > 0)
        	return 1;
		
    	return 0;
    }
    @Override
    @SideOnly(Side.CLIENT)
    public IIcon getIconFromDamageForRenderPass(int damage, int pass)
    {
        return pass == 0 ? itemIcon : (defaultStringIcon ==  null ? itemIcon : defaultStringIcon);
    }
    @Override
    @SideOnly(Side.CLIENT)
    public boolean requiresMultipleRenderPasses()
    {
        return true;
    }
	public float getScaleOffsetX() {
		return scaleOffX;
	}
	public float getScaleOffsetY() {
		return scaleOffY;
	}
	public float getScale(boolean thirdPerson) {
		return scale;
	}
	public int getArrowStep()
	{
		return arrowStep;
	}
	public int getIconSize()
	{
		return iconSize;
	}
	public String getBowArrowIconName()
    {
    	return "";
    }
	public void doBowTransforms(boolean thirdPerson, boolean ifp, float px, float partialTick) {}
	public IIcon getBowIconForPlayer(int iconOffset)
    {
    	return iconArray[iconOffset];
    }
	public IIcon getArrowIcon(int iconOffset, ItemStack arrowStack, int pass)
    {
    	return BowArrowIcons.getIcon(getBowArrowIconName(),
    			arrowStack, iconOffset, pass,
    			false);
    }
	public IIcon getArrowNew(int iconOffset, String h, int pass, boolean split)
    {
    	return BowArrowIcons.arrow(getBowArrowIconName(),
    			h, iconOffset, pass,
    			split);
    }
}
