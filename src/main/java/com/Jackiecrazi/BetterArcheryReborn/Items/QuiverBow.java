package com.Jackiecrazi.BetterArcheryReborn.Items;

import java.util.List;
import java.util.Random;

import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.init.Items;
import net.minecraft.item.EnumAction;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBow;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.potion.PotionEffect;
import net.minecraft.potion.PotionHelper;
import net.minecraft.util.IIcon;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.player.ArrowLooseEvent;
import net.minecraftforge.event.entity.player.ArrowNockEvent;

import com.Jackiecrazi.BetterArcheryReborn.BAR;
import com.Jackiecrazi.BetterArcheryReborn.Items.arrows.ItemQuiverModArrow;
import com.Jackiecrazi.BetterArcheryReborn.Items.arrows.PotionArrow;
import com.Jackiecrazi.BetterArcheryReborn.entities.EntityQuiverModArrowNew;
import com.Jackiecrazi.BetterArcheryReborn.helpful.BowArrowIcons;

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
		return it==ModItems.DerpArrow||it==ModItems.FireArrow||it==Items.arrow||it==ModItems.PotArrow||(it==ModItems.DrillArrow&&i.getItemDamage()!=16)||it==ModItems.EnderArrow||it==ModItems.TimedArrow||it==ModItems.ImpactArrow||it==ModItems.TorchArrow;
	}
	public boolean hasArrow(EntityPlayer p){
		for(ItemStack s: p.inventory.mainInventory){
			if(s!=null&&isArrow(s)){
				return true;
			}
		}
		return false;
	}
	public String getArrow(EntityPlayer player){
		String h=player.getEntityData().getString("usingArrow");
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
				if (stack != null && isArrow(stack)) {
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

        boolean flag = p_77615_3_.capabilities.isCreativeMode || EnchantmentHelper.getEnchantmentLevel(Enchantment.infinity.effectId, p_77615_1_) > 0;
        boolean hasArrow=hasArrow(p_77615_3_);//TODO moar arrows!
        if ((flag || hasArrow)&&j>0)
        {
        	for(int reps=0;reps<this.getArrowStackFromInv(p_77615_3_).getItemDamage()+1;reps++){
            float f = getUsePower(j);

            if ((double)f < 0.1D)
            {
                return;
            }

            if (f > 1.0F)
            {
                f = 1.0F;
            }

            EntityQuiverModArrowNew entityarrow = new EntityQuiverModArrowNew(p_77615_2_, p_77615_3_, f * 3.0F * arrowspeedMult).setType(this.getArrow(p_77615_3_)).setSpecialStuff(6.5F);
            entityarrow.setThrower((Entity)p_77615_3_);
            	if(reps>0){
            		double x=entityarrow.posX;
            		double y=entityarrow.posY;
            		double z=entityarrow.posZ;
            		float pitch=entityarrow.rotationPitch;
            		float yaw=entityarrow.rotationYaw;
            		Random r=new Random();
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
             p_77615_1_.damageItem(1, p_77615_3_);
            
            p_77615_2_.playSoundAtEntity(p_77615_3_, "random.bow", 1.0F, 1.0F / (itemRand.nextFloat() * 0.4F + 1.2F) + f * 0.5F);

            if (flag)
            {
                entityarrow.canBePickedUp = 2;
            }
            else if(reps==0)
            {
                p_77615_3_.inventory.consumeInventoryItem(getArrowStackFromInv(p_77615_3_).getItem());
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
        return (int) (72000*getPullbackMult());
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
    	if(is.getItemDamage()==is.getMaxDamage()-1){
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
        	if((getArrowStackFromInv(p).getItem()) instanceof ItemQuiverModArrow)p.getEntityData().setString("usingArrow", ((ItemQuiverModArrow)(getArrowStackFromInv(p).getItem())).getName());
        	else if((getArrowStackFromInv(p).getItem()) instanceof PotionArrow){
        		Item omg=getArrowStackFromInv(p).getItem();
        		String sub="";
        		String hi=((PotionArrow)omg).getName();
        		if(PotionArrow.isSplash(omg.getDamage(this.getArrowStackFromInv(p))))sub="splash";
        		p.getEntityData().setString("usingArrow", sub+hi);
        		
        	}
        	else p.getEntityData().setString("usingArrow", "arrow");
        	if(this.getArrowStackFromInv(p).getItem() instanceof ItemQuiverModArrow){
        		ItemQuiverModArrow h =(ItemQuiverModArrow) this.getArrowStackFromInv(p).getItem();
        		if(h.isSplittingArrow(this.getArrowStackFromInv(p).getItemDamage())){
        			p.getEntityData().setInteger("splitArrowCount", h.getSplittingArrowCount(this.getArrowStackFromInv(p).getItemDamage()));
        		}
        		else p.getEntityData().setInteger("splitArrowCount", 1);
        	}
        	else{
        		PotionArrow h =(PotionArrow) this.getArrowStackFromInv(p).getItem();
        		if(h.isSplittingArrow(this.getArrowStackFromInv(p).getItemDamage())){
        			p.getEntityData().setInteger("splitArrowCount", h.getSplittingArrowCount(this.getArrowStackFromInv(p).getItemDamage()));
        		}
        		else p.getEntityData().setInteger("splitArrowCount", 1);
        	}
        	//TODO so much arrow crap to deal with don't even know where to start
            p.setItemInUse(is, this.getMaxItemUseDuration(is));
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
