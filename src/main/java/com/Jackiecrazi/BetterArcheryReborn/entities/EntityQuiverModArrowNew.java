package com.Jackiecrazi.BetterArcheryReborn.entities;

import io.netty.buffer.ByteBuf;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.IProjectile;
import net.minecraft.entity.monster.EntityEnderman;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.Item.ToolMaterial;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.play.server.S2BPacketChangeGameState;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MathHelper;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.living.EnderTeleportEvent;

import com.Jackiecrazi.BetterArcheryReborn.Items.ModItems;
import com.Jackiecrazi.BetterArcheryReborn.Items.arrows.DrillArrow;
import com.Jackiecrazi.BetterArcheryReborn.Items.arrows.ItemQuiverModArrow;

import cpw.mods.fml.common.registry.IEntityAdditionalSpawnData;
import cpw.mods.fml.common.registry.IThrowableEntity;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class EntityQuiverModArrowNew extends EntityArrow implements IThrowableEntity,IProjectile,IEntityAdditionalSpawnData {
	public String type;
	private int field_145791_d = -1;
    private int field_145792_e = -1;
    private int field_145789_f = -1;
    private Block field_145790_g;
    private int inData;
    boolean inGround;
    /** 1 if the player can pick up the arrow */
    public int canBePickedUp;
    /** Seems to be some sort of timer for animating an arrow. */
    public int arrowShake;
    /** The owner of this arrow. */
    public Entity shootingEntity;
    private int ticksInGround;
    private int ticksInAir;
    private double damage = 2.0D;
    /** The amount of knockback an arrow applies when it hits a mob. */
    private int knockbackStrength;
    private float usefulID;
    private int duration,potency;
    public int potionDamage;
	public ArrayList<PotionEffect> potionEffects;
    
    private static final String __OBFID = "CL_00001715";
    
    public void setPotionEffects(int damage, ArrayList<PotionEffect> effects)
	{
		potionDamage = damage;
		potionEffects = effects;
		//splashPotion = ModItems.PotArrow.isSplash(damage);
	}
    
    public EntityQuiverModArrowNew setType(String h) {
		this.type = h;
		return this;
	}
    
    public boolean isDrillBroken() {
		return this.type=="drillarrow"&&this.usefulID < 1;
	}
    
    public EntityQuiverModArrowNew setSpecialStuff(float f){
    	this.usefulID=f;
    	return this;
    }
    
    public int getDuration(){
		return duration;
	}
	public EntityQuiverModArrowNew setDuration(int tickcount){
		this.duration=tickcount;
		return this;
	}
	public int getPotency(){
		return potency;
	}
	public EntityQuiverModArrowNew setPotency(int tickcount){
		this.potency=tickcount;
		return this;
	}
    
    public float getSpecialStuff(){
    	return this.usefulID;
    }

    public EntityQuiverModArrowNew(World p_i1753_1_)
    {
        super(p_i1753_1_);
    }

    public EntityQuiverModArrowNew(World p_i1754_1_, double p_i1754_2_, double p_i1754_4_, double p_i1754_6_)
    {
        super(p_i1754_1_,p_i1754_2_,p_i1754_4_,p_i1754_6_);
    }

    public EntityQuiverModArrowNew(World p_i1755_1_, EntityLivingBase p_i1755_2_, EntityLivingBase p_i1755_3_, float p_i1755_4_, float p_i1755_5_)
    {
        super(p_i1755_1_,p_i1755_2_,p_i1755_3_,p_i1755_4_,p_i1755_5_);
    }

    public EntityQuiverModArrowNew(World p_i1756_1_, EntityLivingBase p_i1756_2_, float p_i1756_3_)
    {
        super(p_i1756_1_,p_i1756_2_,p_i1756_3_);
        if (p_i1756_2_ instanceof EntityPlayer)
        {
            this.canBePickedUp = 1;
        }
        this.setThrowableHeading(this.motionX, this.motionY, this.motionZ, p_i1756_3_ * 1.5F, 1.0F);
    }
    
    public void setThrowableHeading(double p_70186_1_, double p_70186_3_, double p_70186_5_, float p_70186_7_, float p_70186_8_)
    {
        super.setThrowableHeading(p_70186_1_, p_70186_3_, p_70186_5_, p_70186_7_, p_70186_8_);
    }

    /**
     * Sets the position and rotation. Only difference from the other one is no bounding on the rotation. Args: posX,
     * posY, posZ, yaw, pitch
     */
    @SideOnly(Side.CLIENT)
    public void setPositionAndRotation2(double p_70056_1_, double p_70056_3_, double p_70056_5_, float p_70056_7_, float p_70056_8_, int p_70056_9_)
    {
    	this.setPosition(p_70056_1_, p_70056_3_, p_70056_5_);
        this.setRotation(p_70056_7_, p_70056_8_);
    }
    public void setVelocity(double par1, double par3, double par5)
    {
        this.motionX = par1;
        this.motionY = par3;
        this.motionZ = par5;

        if (this.prevRotationPitch == 0.0F && this.prevRotationYaw == 0.0F)
        {
            float var7 = MathHelper.sqrt_double(par1 * par1 + par5 * par5);
            this.prevRotationYaw = this.rotationYaw = (float)(Math.atan2(par1, par5) * 180.0D / Math.PI);
            this.prevRotationPitch = this.rotationPitch = (float)(Math.atan2(par3, (double)var7) * 180.0D / Math.PI);
            this.prevRotationPitch = this.rotationPitch;
            this.prevRotationYaw = this.rotationYaw;
            this.setLocationAndAngles(this.posX, this.posY, this.posZ, this.rotationYaw, this.rotationPitch);
            this.ticksInGround = 0;
        }
    }
    @Override
    public void writeEntityToNBT(NBTTagCompound n){
    	super.writeEntityToNBT(n);
    	n.setString("type", type);
    	n.setFloat("special", getSpecialStuff());
    	n.setInteger("duration", getDuration());
    	n.setInteger("potency", getPotency());
    	n.setDouble("damage", getDamage());
    	n.setInteger("potionDamage", potionDamage);
    	NBTTagCompound shooter=new NBTTagCompound();
    	if(this.getThrower()!=null){
    		this.getThrower().writeToNBT(shooter);
    	}
    	n.setTag("shooter", shooter);
    }
    @Override
    public void readEntityFromNBT(NBTTagCompound n){
    	super.readEntityFromNBT(n);
    	this.type=n.getString("type");
    	this.usefulID=n.getInteger("special");
    	this.duration=n.getInteger("duration");
    	this.potency=n.getInteger("potency");
    	this.damage=n.getDouble("damage");
    	this.potionDamage=n.getInteger("potionDamage");
    	//TODO get the shooter
    }
    /**
     * Called to update the entity's position/logic.
     */
    public void onUpdate()
    {
    	super.onEntityUpdate();
    	if (this.prevRotationPitch == 0.0F && this.prevRotationYaw == 0.0F)
        {
            float f = MathHelper.sqrt_double(this.motionX * this.motionX + this.motionZ * this.motionZ);
            this.prevRotationYaw = this.rotationYaw = (float)(Math.atan2(this.motionX, this.motionZ) * 180.0D / Math.PI);
            this.prevRotationPitch = this.rotationPitch = (float)(Math.atan2(this.motionY, (double)f) * 180.0D / Math.PI);
        }

        Block block = this.worldObj.getBlock(this.field_145791_d, this.field_145792_e, this.field_145789_f);

        if (block.getMaterial() != Material.air)
        {
            block.setBlockBoundsBasedOnState(this.worldObj, this.field_145791_d, this.field_145792_e, this.field_145789_f);
            AxisAlignedBB axisalignedbb = block.getCollisionBoundingBoxFromPool(this.worldObj, this.field_145791_d, this.field_145792_e, this.field_145789_f);

            if (axisalignedbb != null && axisalignedbb.isVecInside(Vec3.createVectorHelper(this.posX, this.posY, this.posZ)))
            {
            	if(this.type!=null&&this.type.equals("drillarrow")){
            		if(this.usefulID<=0)
            				this.inGround = true;
            		else{
            			boolean brokeAnotherBlock=false;
            			int x=(int)this.posX;
	            		int y=(int)this.posY;
	            		int z=(int)this.posZ;
    	            	float hardness = block.getBlockHardness(worldObj, x, y, z);
    	            	
    	            	if (hardness > 0)
    	            	{
    		            	int metadata = worldObj.getBlockMetadata(x, y, z);
    		            	float toolHardness = 6-ToolMaterial.IRON.getEfficiencyOnProperMaterial();
    		            	
    		            	if (toolHardness < 1)
    		            		toolHardness = 1;
    		            	float damage = hardness * toolHardness/2;
    		            	
    		            	if (usefulID >= damage)
    		            	{
    		            		
    		            		worldObj.func_147480_a(x, y, z, true);

    		            		brokeAnotherBlock = true;
    		                    
    		            		usefulID -= damage;
    		            		motionX *= 0.8;
    		            		motionY *= 0.8;
    		            		motionZ *= 0.8;
    		            	}
    	            	}
            		}
            	}
            	//TODO stuff
            	else this.inGround = true;
            }
        }
        else this.inGround=false;

        if (this.arrowShake > 0)
        {
            --this.arrowShake;
        }

        if (this.inGround)
        {
            int j = this.worldObj.getBlockMetadata(this.field_145791_d, this.field_145792_e, this.field_145789_f);

            if (block == this.field_145790_g && j == this.inData)
            {
                ++this.ticksInGround;

                if (this.ticksInGround == 1200)
                {
                    this.setDead();
                }
                if(this.ticksInGround==60){
                	if(this.type!=null&&this.type.equals("timedarrow")){
                		this.worldObj.createExplosion(this, this.posX, this.posY, this.posZ, 3, this.worldObj.getGameRules().getGameRuleBooleanValue("mobGriefing"));
                		this.setDead();
                	}
                }
            }
            else
            {
                this.inGround = false;
                this.motionX *= (double)(this.rand.nextFloat() * 0.2F);
                this.motionY *= (double)(this.rand.nextFloat() * 0.2F);
                this.motionZ *= (double)(this.rand.nextFloat() * 0.2F);
                this.ticksInGround = 0;
                this.ticksInAir = 0;
            }
        }
        else
        {
            ++this.ticksInAir;
            Vec3 vec31 = Vec3.createVectorHelper(this.posX, this.posY, this.posZ);
            Vec3 vec3 = Vec3.createVectorHelper(this.posX + this.motionX, this.posY + this.motionY, this.posZ + this.motionZ);
            MovingObjectPosition movingobjectposition = this.worldObj.func_147447_a(vec31, vec3, false, true, false);
            vec31 = Vec3.createVectorHelper(this.posX, this.posY, this.posZ);
            vec3 = Vec3.createVectorHelper(this.posX + this.motionX, this.posY + this.motionY, this.posZ + this.motionZ);
            Vec3 fromVec = null;
            Vec3 toVec = null;
            MovingObjectPosition mop = null;
            
            double motion = MathHelper.sqrt_double(motionX * motionX + motionY * motionY + motionZ * motionZ);
        	ArrayList<Vec3> blocksToBreak = new ArrayList();
            
        	while ((fromVec == null || mop != null)&&(this.type!=null&&this.type.equals("drillarrow")))
        	{
	            fromVec = Vec3.createVectorHelper(posX, posY, posZ);
	            toVec = Vec3.createVectorHelper(posX + motionX, posY + motionY, posZ + motionZ);
	            mop = this.worldObj.rayTraceBlocks(fromVec, toVec, false);
	            
	            boolean brokeAnotherBlock = false;
	            
	            if (!worldObj.isRemote && mop != null)
	            {
	            	Block bloc = worldObj.getBlock(mop.blockX, mop.blockY, mop.blockZ);
	            	
	            	float hardness = bloc.getBlockHardness(worldObj, mop.blockX, mop.blockY, mop.blockZ);
	            	
	            	if (hardness > 0)
	            	{
		            	int metadata = worldObj.getBlockMetadata(mop.blockX, mop.blockY, mop.blockZ);
		            	float toolHardness = 6-ToolMaterial.IRON.getEfficiencyOnProperMaterial();
		            	
		            	if (toolHardness < 1)
		            		toolHardness = 1;
		            	float damage = hardness * toolHardness/2;
		            	
		            	if (usefulID >= damage)
		            	{
		            		int x=mop.blockX;
		            		int y=mop.blockY;
		            		int z=mop.blockZ;
		            		worldObj.func_147480_a(x, y, z, true);

		            		brokeAnotherBlock = true;
		                    
		            		usefulID -= damage;
		            		motionX *= 0.8;
		            		motionY *= 0.8;
		            		motionZ *= 0.8;
		            	}
	            	}
	            }
	            
	            if (!brokeAnotherBlock)
	            {
	            	break;
	            }
        	}
            
            if (movingobjectposition != null)
            {
                vec3 = Vec3.createVectorHelper(movingobjectposition.hitVec.xCoord, movingobjectposition.hitVec.yCoord, movingobjectposition.hitVec.zCoord);
            }

            Entity entity = null;
            List list = this.worldObj.getEntitiesWithinAABBExcludingEntity(this, this.boundingBox.addCoord(this.motionX, this.motionY, this.motionZ).expand(1.0D, 1.0D, 1.0D));
            double d0 = 0.0D;
            int i;
            float f1;

            for (i = 0; i < list.size(); ++i)
            {
                Entity entity1 = (Entity)list.get(i);

                if (entity1.canBeCollidedWith() && (entity1 != this.shootingEntity || this.ticksInAir >= 5))
                {
                    f1 = 0.3F;
                    AxisAlignedBB axisalignedbb1 = entity1.boundingBox.expand((double)f1, (double)f1, (double)f1);
                    MovingObjectPosition movingobjectposition1 = axisalignedbb1.calculateIntercept(vec31, vec3);

                    if (movingobjectposition1 != null)
                    {
                        double d1 = vec31.distanceTo(movingobjectposition1.hitVec);

                        if (d1 < d0 || d0 == 0.0D)
                        {
                            entity = entity1;
                            d0 = d1;
                        }
                    }
                }
            }

            if (entity != null)
            {
                movingobjectposition = new MovingObjectPosition(entity);
            }

            if (movingobjectposition != null && movingobjectposition.entityHit != null && movingobjectposition.entityHit instanceof EntityPlayer)
            {
                EntityPlayer entityplayer = (EntityPlayer)movingobjectposition.entityHit;

                if (entityplayer.capabilities.disableDamage || this.shootingEntity instanceof EntityPlayer && !((EntityPlayer)this.shootingEntity).canAttackPlayer(entityplayer))
                {
                    movingobjectposition = null;
                }
            }

            float f2;
            float f4;

            if (movingobjectposition != null)
            {
                if (movingobjectposition.entityHit != null&&this.type!=null&&!this.type.equals("timedarrow"))
                {
                	
                    f2 = MathHelper.sqrt_double(this.motionX * this.motionX + this.motionY * this.motionY + this.motionZ * this.motionZ);
                    int k = MathHelper.ceiling_double_int((double)f2 * this.damage);

                    if (this.getIsCritical())
                    {
                        k += this.rand.nextInt(k / 2 + 2);
                    }
                    if(this.type.equals("potionarrow")||this.type.equals("splashpotionarrow")){
                    	performEffectEntity(type, movingobjectposition.entityHit, shootingEntity);
                    	this.setDead();
                    	return;
                    }
                    DamageSource damagesource = null;

                    if (this.shootingEntity == null)
                    {
                        damagesource = DamageSource.causeArrowDamage(this, this);
                    }
                    else
                    {
                        damagesource = DamageSource.causeArrowDamage(this, this.shootingEntity);
                    }

                    if (this.isBurning() && !(movingobjectposition.entityHit instanceof EntityEnderman))
                    {
                        movingobjectposition.entityHit.setFire(5);
                    }

                    if (movingobjectposition.entityHit.attackEntityFrom(damagesource, (float)k))
                    {
                        if (movingobjectposition.entityHit instanceof EntityLivingBase)
                        {
                            EntityLivingBase entitylivingbase = (EntityLivingBase)movingobjectposition.entityHit;
                            entitylivingbase.hurtResistantTime=0;
                            if (!this.worldObj.isRemote)
                            {
                                entitylivingbase.setArrowCountInEntity(entitylivingbase.getArrowCountInEntity() + 1);
                            }

                            if (this.knockbackStrength > 0)
                            {
                                f4 = MathHelper.sqrt_double(this.motionX * this.motionX + this.motionZ * this.motionZ);

                                if (f4 > 0.0F)
                                {
                                    movingobjectposition.entityHit.addVelocity(this.motionX * (double)this.knockbackStrength * 0.6000000238418579D / (double)f4, 0.1D, this.motionZ * (double)this.knockbackStrength * 0.6000000238418579D / (double)f4);
                                }
                            }

                            if (this.shootingEntity != null && this.shootingEntity instanceof EntityLivingBase)
                            {
                                EnchantmentHelper.func_151384_a(entitylivingbase, this.shootingEntity);
                                EnchantmentHelper.func_151385_b((EntityLivingBase)this.shootingEntity, entitylivingbase);
                                
                                if(type!=null){
                                performEffectEntity(type, movingobjectposition.entityHit, shootingEntity);
                            }
                            }

                            if (this.shootingEntity != null && movingobjectposition.entityHit != this.shootingEntity && movingobjectposition.entityHit instanceof EntityPlayer && this.shootingEntity instanceof EntityPlayerMP)
                            {
                                ((EntityPlayerMP)this.shootingEntity).playerNetServerHandler.sendPacket(new S2BPacketChangeGameState(6, 0.0F));
                            }
                        }

                        this.playSound("random.bowhit", 1.0F, 1.2F / (this.rand.nextFloat() * 0.2F + 0.9F));

                        if (!(movingobjectposition.entityHit instanceof EntityEnderman))
                        {
                            this.setDead();
                        }
                    }
                    else
                    {
                        this.motionX *= -0.10000000149011612D;
                        this.motionY *= -0.10000000149011612D;
                        this.motionZ *= -0.10000000149011612D;
                        this.rotationYaw += 180.0F;
                        this.prevRotationYaw += 180.0F;
                        this.ticksInAir = 0;
                    }
                }
                else
                {
                		/*if(type.equals("drillarrow")&&this.usefulID>0){
                			int x=movingobjectposition.blockX;
                			int y=movingobjectposition.blockY;
                			int z=movingobjectposition.blockZ;
                			if(this.worldObj.getBlock(movingobjectposition.blockX, movingobjectposition.blockY, movingobjectposition.blockZ)!=null){
                				Block bl=this.worldObj.getBlock(x, y, z);
                				if(bl.getBlockHardness(this.worldObj, x, y, z)>=0){
                					this.usefulID-=bl.getBlockHardness(this.worldObj, x, y, z);
                					if(usefulID>0){
                						this.worldObj.setBlockToAir(x, y, z);
                						bl.dropBlockAsItemWithChance(worldObj, x, y, z, worldObj.getBlockMetadata(x, y, z), 0.8F, 0);
                						bl.onBlockDestroyedByPlayer(worldObj, x, y, z, this.worldObj.getBlockMetadata(x, y, z));
                    					System.out.println("broke block at x: "+x+" y: "+y+" z: "+z);
                					}
                				}
                			}
                		}
                		else{*/
                    this.field_145791_d = movingobjectposition.blockX;
                    this.field_145792_e = movingobjectposition.blockY;
                    this.field_145789_f = movingobjectposition.blockZ;
                    this.field_145790_g = this.worldObj.getBlock(this.field_145791_d, this.field_145792_e, this.field_145789_f);
                    this.inData = this.worldObj.getBlockMetadata(this.field_145791_d, this.field_145792_e, this.field_145789_f);
                    this.motionX = (double)((float)(movingobjectposition.hitVec.xCoord - this.posX));
                    this.motionY = (double)((float)(movingobjectposition.hitVec.yCoord - this.posY));
                    this.motionZ = (double)((float)(movingobjectposition.hitVec.zCoord - this.posZ));
                    f2 = MathHelper.sqrt_double(this.motionX * this.motionX + this.motionY * this.motionY + this.motionZ * this.motionZ);
                    this.posX -= this.motionX / (double)f2 * 0.05000000074505806D;
                    this.posY -= this.motionY / (double)f2 * 0.05000000074505806D;
                    this.posZ -= this.motionZ / (double)f2 * 0.05000000074505806D;
                    this.playSound("random.bowhit", 1.0F, 1.2F / (this.rand.nextFloat() * 0.2F + 0.9F));
                    this.ticksInGround=0;
                    this.inGround = true;
                    this.arrowShake = 7;
                    this.setIsCritical(false);

                    if (this.field_145790_g.getMaterial() != Material.air)
                    {
                        this.field_145790_g.onEntityCollidedWithBlock(this.worldObj, this.field_145791_d, this.field_145792_e, this.field_145789_f, this);
                        performEffectBlock(type, shootingEntity, movingobjectposition);
                    }
                		//}
                }
            }

            if (this.getIsCritical())
            {
                for (i = 0; i < 4; ++i)
                {
                    this.worldObj.spawnParticle("crit", this.posX + this.motionX * (double)i / 4.0D, this.posY + this.motionY * (double)i / 4.0D, this.posZ + this.motionZ * (double)i / 4.0D, -this.motionX, -this.motionY + 0.2D, -this.motionZ);
                }
            }

            this.posX += this.motionX;
            this.posY += this.motionY;
            this.posZ += this.motionZ;
            f2 = MathHelper.sqrt_double(this.motionX * this.motionX + this.motionZ * this.motionZ);
            this.rotationYaw = (float)(Math.atan2(this.motionX, this.motionZ) * 180.0D / Math.PI);

            for (this.rotationPitch = (float)(Math.atan2(this.motionY, (double)f2) * 180.0D / Math.PI); this.rotationPitch - this.prevRotationPitch < -180.0F; this.prevRotationPitch -= 360.0F)
            {
                ;
            }

            while (this.rotationPitch - this.prevRotationPitch >= 180.0F)
            {
                this.prevRotationPitch += 360.0F;
            }

            while (this.rotationYaw - this.prevRotationYaw < -180.0F)
            {
                this.prevRotationYaw -= 360.0F;
            }

            while (this.rotationYaw - this.prevRotationYaw >= 180.0F)
            {
                this.prevRotationYaw += 360.0F;
            }

            this.rotationPitch = this.prevRotationPitch + (this.rotationPitch - this.prevRotationPitch) * 0.2F;
            this.rotationYaw = this.prevRotationYaw + (this.rotationYaw - this.prevRotationYaw) * 0.2F;
            float f3 = 0.99F;
            f1 = 0.05F;

            if (this.isInWater())
            {
                for (int l = 0; l < 4; ++l)
                {
                    f4 = 0.25F;
                    this.worldObj.spawnParticle("bubble", this.posX - this.motionX * (double)f4, this.posY - this.motionY * (double)f4, this.posZ - this.motionZ * (double)f4, this.motionX, this.motionY, this.motionZ);
                }

                f3 = 0.8F;
            }

            if (this.isWet())
            {
                this.extinguish();
            }

            this.motionX *= (double)f3;
            this.motionY *= (double)f3;
            this.motionZ *= (double)f3;
            this.motionY -= (double)f1;
            this.setPosition(this.posX, this.posY, this.posZ);
            this.func_145775_I();
        }
    }

    /**
     * Called by a player entity when they collide with an entity
     */
    public void onCollideWithPlayer(EntityPlayer p_70100_1_)
    {
        if (!this.worldObj.isRemote && this.inGround && this.arrowShake <= 0&&this.type!=null&&!(this.type.equals("timedarrow")))
        {
            boolean flag = this.canBePickedUp == 1 || this.canBePickedUp == 2 && p_70100_1_.capabilities.isCreativeMode;
            Item arrow=Items.arrow;
            for(int x=0;x<ModItems.arrowItemList.size();x++){
            	if(((ModItems.arrowItemList.get(x)))instanceof ItemQuiverModArrow&&ModItems.arrowItemList.get(x) !=ModItems.TorchArrow){
            		if(((ItemQuiverModArrow)ModItems.arrowItemList.get(x)).getName().equals(this.type)){
            			arrow=(ModItems.arrowItemList.get(x));
            			
            		}
            	}
            	//TODO should pot arrows return pot arrows?
            }
            ItemStack i=new ItemStack(arrow,1);
            if(this.type.equals("drillarrow")){
            	i.setItemDamage(((DrillArrow) arrow).setBroken(0, true));
			}
            ;
            if (this.canBePickedUp == 1 && !p_70100_1_.inventory.addItemStackToInventory(i))
            {
                flag = false;
            }

            if (flag)
            {
                this.playSound("random.pop", 0.2F, ((this.rand.nextFloat() - this.rand.nextFloat()) * 0.7F + 1.0F) * 2.0F);
                p_70100_1_.onItemPickup(this, 1);
                this.setDead();
            }
        }
    }

    /**
     * returns if this entity triggers Block.onEntityWalking on the blocks they walk on. used for spiders and wolves to
     * prevent them from trampling crops
     */
    protected boolean canTriggerWalking()
    {
        return false;
    }

    @SideOnly(Side.CLIENT)
    public float getShadowSize()
    {
        return 0.0F;
    }

    public void setDamage(double p_70239_1_)
    {
        this.damage = p_70239_1_;
    }

    public double getDamage()
    {
        return this.damage;
    }

    /**
     * Sets the amount of knockback the arrow applies when it hits a mob.
     */
    public void setKnockbackStrength(int p_70240_1_)
    {
        this.knockbackStrength = p_70240_1_;
    }

    /**
     * If returns false, the item will not inflict any damage against entities.
     */
    public boolean canAttackWithItem()
    {
        return false;
    }

    /**
     * Whether the arrow has a stream of critical hit particles flying behind it.
     */
    public void setIsCritical(boolean p_70243_1_)
    {
        byte b0 = this.dataWatcher.getWatchableObjectByte(16);

        if (p_70243_1_)
        {
            this.dataWatcher.updateObject(16, Byte.valueOf((byte)(b0 | 1)));
        }
        else
        {
            this.dataWatcher.updateObject(16, Byte.valueOf((byte)(b0 & -2)));
        }
    }

    /**
     * Whether the arrow has a stream of critical hit particles flying behind it.
     */
    public boolean getIsCritical()
    {
        byte b0 = this.dataWatcher.getWatchableObjectByte(16);
        return (b0 & 1) != 0;
    }
    
	public void performEffectEntity(String type, Entity uke, Entity seme) {
		if(this.type!=null&&seme!=null){
		if (type.equals("firearrow")) {
			uke.setFire(1000);
		}
		if (type.equals("potionarrow")) {
			if (uke instanceof EntityLivingBase&&this.usefulID!=0) {
				((EntityLivingBase) uke).addPotionEffect(new PotionEffect(
						(int)usefulID, this.getDuration(), this.getPotency()));
			}
		}
		if(type.equals("impactarrow")){
			this.worldObj.createExplosion(this, this.posX, this.posY, this.posZ, 2, false);
			this.setDead();
		}
		
	}
	}

	@SuppressWarnings("unchecked")
	public void performEffectBlock(String type, Entity seme,
			MovingObjectPosition mop) {
		if(type!=null&&seme!=null){
		int x=mop.blockX;
		int y=mop.blockY;
		int z=mop.blockZ;
		int side=mop.sideHit;
		if (type.equals("firearrow")&&seme!=null) {
			World w = seme.worldObj;
			switch (side) {
			case 0:
				y -= 1;
				break;
			case 1:
				y += 1;
				break;
			case 2:
				z -= 1;
				break;
			case 3:
				z += 1;
				break;
			case 4:
				x -= 1;
				break;
			case 5:
				x += 1;
				break;
			}
			if (w.isAirBlock((int)x, (int)y, (int)z)) {
				w.playSoundEffect((double) x + 0.5D, (double) y + 0.5D,
						(double) z + 0.5D, "fire.ignite", 1.0F,
						w.rand.nextFloat() * 0.4F + 0.8F);
				w.setBlock((int)x, (int)y, (int)z, Blocks.fire);
				this.setDead();
			}
		}
		if(type.equals("impactarrow")){
			this.worldObj.createExplosion(this, this.posX, this.posY, this.posZ, 2, false);
			this.setDead();
		}
		if(this.type.equals("splashpotionarrow")){
			List<EntityLivingBase> hi=this.worldObj.getEntitiesWithinAABB(EntityLivingBase.class, AxisAlignedBB.getBoundingBox(x-2, y-1, z-2, x+2, y+3, z+2));
			if(!hi.isEmpty()){
				Iterator i=hi.iterator();
				while(i.hasNext()){
					EntityLivingBase l=(EntityLivingBase) i.next();
					l.addPotionEffect(new PotionEffect(
						(int)usefulID, getDuration(), getPotency()));
				}
			}
		}
		
		if(this.type.equals("torcharrow")){
			World w = seme.worldObj;
			switch (side) {
			case 0:
				y+=1;
				break;
			case 1:
				y += 1;
				break;
			case 2:
				z -= 1;
				break;
			case 3:
				z += 1;
				break;
			case 4:
				x -= 1;
				break;
			case 5:
				x += 1;
				break;
			}
			if (w.isAirBlock((int)x, (int)y, (int)z)) {
				w.setBlock((int)x, (int)y, (int)z, Blocks.torch);
			}
		}
		
		if (type.equals("enderarrow")&&seme!=null) {
			for (int i = 0; i < 32; ++i) {
				this.worldObj.spawnParticle("portal", this.posX,
						this.posY + this.rand.nextDouble() * 2.0D,
						this.posZ, this.rand.nextGaussian(), 0.0D,
						this.rand.nextGaussian());
			}

			if (!this.worldObj.isRemote) {
				if (this.shootingEntity != null
						&& this.shootingEntity instanceof EntityPlayerMP) {
					EntityPlayerMP entityplayermp = (EntityPlayerMP)this.getThrower();

	                if (entityplayermp.playerNetServerHandler.func_147362_b().isChannelOpen() && entityplayermp.worldObj == this.worldObj)
	                {
	                    EnderTeleportEvent event = new EnderTeleportEvent(entityplayermp, this.posX, this.posY, this.posZ, 5.0F);
	                    if (!MinecraftForge.EVENT_BUS.post(event))
	                    { // Don't indent to lower patch size
	                    if (this.getThrower().isRiding())
	                    {
	                        this.getThrower().mountEntity((Entity)null);
	                    }

	                    ((EntityPlayerMP)this.getThrower()).setPositionAndUpdate(event.targetX, event.targetY, event.targetZ);
	                    this.getThrower().fallDistance = 0.0F;
	                    this.getThrower().attackEntityFrom(DamageSource.fall, event.attackDamage);
	                    }
	                }
				}
			}
			
		}
		}
		
	}

	public Entity getThrower() {
		return shootingEntity;
	}

	public void setThrower(Entity entity) {
		this.shootingEntity=entity;
	}

	@Override
	public void writeSpawnData(ByteBuf buffer) {
		buffer.writeInt(shootingEntity != null ? shootingEntity.getEntityId() : -1);
	}

	@Override
	public void readSpawnData(ByteBuf buffer) {
		Entity shooter = worldObj.getEntityByID(buffer.readInt());
		if (shooter instanceof EntityLivingBase) {
			shootingEntity = (EntityLivingBase) shooter;
		}
	}
}
