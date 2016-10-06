/*package com.Jackiecrazi.BetterArcheryReborn.entities;

import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.ObfuscationReflectionHelper;
import cpw.mods.fml.common.registry.IEntityAdditionalSpawnData;
import cpw.mods.fml.relauncher.ReflectionHelper;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import io.netty.buffer.ByteBuf;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.util.ArrayList;
import java.util.EmptyStackException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;
import java.util.Set;
import java.util.TreeMap;

import com.Jackiecrazi.BetterArcheryReborn.BAR;
import com.Jackiecrazi.BetterArcheryReborn.ModExplosion;
import com.Jackiecrazi.BetterArcheryReborn.QuiverModConfigManager;
import com.Jackiecrazi.BetterArcheryReborn.Items.ModItems;
import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteArrayDataOutput;

import net.minecraft.block.Block;
import net.minecraft.block.BlockDoor;
import net.minecraft.block.BlockPistonBase;
import net.minecraft.block.BlockPistonExtension;
import net.minecraft.block.BlockPistonMoving;
import net.minecraft.block.material.Material;
import net.minecraft.client.particle.EntityFX;
import net.minecraft.client.renderer.RenderGlobal;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.EnchantmentThorns;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.EntityTracker;
import net.minecraft.entity.EntityTrackerEntry;
import net.minecraft.entity.IProjectile;
import net.minecraft.entity.boss.EntityDragonPart;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.monster.EntityEnderman;
import net.minecraft.entity.monster.EntitySkeleton;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemFishingRod;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.item.ItemTool;
import net.minecraft.item.crafting.FurnaceRecipes;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.network.play.server.S27PacketExplosion;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.potion.PotionHelper;
import net.minecraft.server.MinecraftServer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityPiston;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EntityDamageSourceIndirect;
import net.minecraft.util.Facing;
import net.minecraft.util.MathHelper;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.Vec3;
import net.minecraft.world.ChunkCoordIntPair;
import net.minecraft.world.Explosion;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.gen.ChunkProviderServer;
import net.minecraftforge.common.ForgeChunkManager;
import net.minecraftforge.common.ForgeChunkManager.Ticket;
import net.minecraftforge.common.util.ForgeDirection;
import net.minecraftforge.common.MinecraftForge;

public class EntityQuiverModArrow extends EntityArrow implements IProjectile,
		IEntityAdditionalSpawnData {
	private float gravity = -0.05F;

	public int slowMo = 0;// QuiverModConfigManager.slowMoSetting;

	public static final float maxRotSpeed = 10;

	public int actualTicks = -1;
	public float prevYawForSlowmo = 0;
	public float prevPitchForSlowmo = 0;

	MovingObjectPosition lastHitMOP = null;
	private double hitVecX = 0;
	private double hitVecY = 0;
	private double hitVecZ = 0;

	protected int xTile = -1;
	protected int yTile = -1;
	protected int zTile = -1;
	protected int hitSide = -1;
	protected Block inTile = null;
	protected int inData = 0;
	public boolean wasInGround = false;
	public boolean inGround = false;

	public AxisAlignedBB headBoundingBox;
	double headWidth = 0.1;

	public boolean renderBB = false;
	public double renderBBMinX;
	public double renderBBMinY;
	public double renderBBMinZ;
	public double renderBBMaxX;
	public double renderBBMaxY;
	public double renderBBMaxZ;

	*//** != 0 if the player can pick up the arrow *//*
	public int canBePickedUp = 0;
	private ItemStack pickupItemStack = new ItemStack(Items.arrow);
	public float arrowLength = 1;

	*//** The owner of this arrow. *//*
	private int shootingEntityID = -1;
	private String shootingPlayerName = null;

	protected int ticksInGround;
	protected int ticksInAir = 0;
	protected double damage = 2.0D;

	*//** The amount of knockback an arrow applies when it hits a mob. *//*
	protected int knockbackStrength;
	protected int knockbackMode;

	public int ticks = 0;

	private int setFireTicks = -1;

	public boolean exploded = false;
	public float explosionSize = 0;
	public boolean explosionBurn = false;
	public int explosionTime = -1;
	public boolean explodeInWater = false;

	public boolean teleportShooter = false;
	public int teleportShooterDamage = 5;
	public Ticket chunkLoader = null;

	public boolean placeTorch = false;

	public float breakBlocks = -1;

	public int potionDamage = -1;
	public ArrayList<PotionEffect> potionEffects;
	public boolean splashPotion = false;

	public int splitTime = 0;
	public int splitArrowCount = 1;

	private int smokeParticlesPerBlock = 4;

	private boolean pistonMoved = false;
	private float prevPistonProgress = 0;

	public float prevFireVerticalRot = 0;

	public static class ImpaledItem {
		public EntityItem item;

		public float targetPosition = 0;
		public int ticksImpaled = 0;

		private float prevPosition = 0;
		private float position = 0;

		private static EntityItem createEntityItem(World world) {
			EntityItem item = new EntityItem(world);
			item.hoverStart = 0;
			return item;
		}

		public ImpaledItem(EntityItem impalingItem) {
			EntityItem dupe = createEntityItem(impalingItem.worldObj);
			// dupe.copyDataFrom(impalingItem, true);

			ItemStack dupedStack = impalingItem.getEntityItem().copy();
			dupedStack.stackSize = 1;

			dupe.setEntityItemStack(dupedStack);

			if (!impalingItem.worldObj.isRemote)
				impalingItem.getEntityItem().stackSize -= dupedStack.stackSize;

			item = dupe;
		}

		public ImpaledItem(World world, NBTTagCompound tags) {
			item = createEntityItem(world);
			item.readEntityFromNBT(tags.getCompoundTag("item"));

			position = tags.getFloat("position");
			prevPosition = position;
			targetPosition = tags.getFloat("targetPosition");
			ticksImpaled = tags.getInteger("ticksImpaled");
		}

		public ImpaledItem(World world, Item itemID, int damage,
				float position, float targetPosition) {
			item = createEntityItem(world);
			item.setEntityItemStack(new ItemStack(itemID, 1, damage));

			this.position = prevPosition = position;
			this.targetPosition = targetPosition;
		}

		public NBTTagCompound getNBT() {
			NBTTagCompound out = new NBTTagCompound();

			NBTTagCompound entityNBT = new NBTTagCompound();
			item.writeToNBT(entityNBT);
			out.setTag("item", entityNBT);

			out.setFloat("position", position);
			out.setInteger("ticksImpaled", ticksImpaled);

			return out;
		}

		public void update(EntityQuiverModArrow owner) {
			prevPosition = position;

			position += (targetPosition - position) * 0.3F;

			ItemStack stack = item.getEntityItem();

			if (owner.isBurning() && ticksImpaled > 20) {
				ItemStack cookedStack = FurnaceRecipes.smelting()
						.getSmeltingResult(stack);

				if (cookedStack != null) {
					cookedStack = cookedStack.copy();
					cookedStack.stackSize = stack.stackSize;
					item.setEntityItemStack(cookedStack);
				}
			}

			ticksImpaled++;
		}

		public float getPosition(float partialTick) {
			if (partialTick == 0)
				return prevPosition;
			else if (partialTick == 1)
				return position;

			return prevPosition + (position - prevPosition) * partialTick;
		}
	}

	public ArrayList<ImpaledItem> impaledItems = new ArrayList();

	public EntityQuiverModArrow(World world) {
		super(world);
		this.renderDistanceWeight = 10.0D;
		this.setSize(0.5F, 0.5F);
		this.yOffset = 0;

		knockbackMode = QuiverModConfigManager.arrowKnockbackMode;
	}

	public EntityQuiverModArrow(World world, double x, double y, double z) {
		super(world);
		this.renderDistanceWeight = 10;
		this.setSize(0.5F, 0.5F);
		this.setPosition(x, y, z);
		this.yOffset = 0.0F;

		knockbackMode = QuiverModConfigManager.arrowKnockbackMode;
	}

	public EntityQuiverModArrow(World world, EntityLivingBase shooter,
			EntityLivingBase target, float speed, float deviation) {
		super(world);

		renderDistanceWeight = 10;
		shootingEntity = shooter;

		if (shooter instanceof EntityPlayer) {
			canBePickedUp = 1;
		} else if (shooter instanceof EntitySkeleton) {
			teleportShooterDamage = 1;
		}

		posY = shooter.posY + (double) shooter.getEyeHeight() - 0.1;

		double initVecX = target.posX - shooter.posX;
		double initVecY = target.boundingBox.minY + (target.height / 2)
				- this.posY;
		double initVecZ = target.posZ - shooter.posZ;

		double vecX = initVecX;
		double vecY = initVecY;
		double vecZ = initVecZ;

		float distance = 0;
		float flightTicks = 0;
		float approxSpeed = speed;

		double moveX = target.posX - target.lastTickPosX;
		double moveY = target.posY - target.lastTickPosY;
		double moveZ = target.posZ - target.lastTickPosZ;

		int passes = 15;

		for (int i = 0; i <= passes; i++) {
			distance = MathHelper.sqrt_double(vecX * vecX + vecY * vecY + vecZ
					* vecZ);
			flightTicks = distance / approxSpeed;

			if (i < passes) {
				if (worldObj.difficultySetting == worldObj.difficultySetting.NORMAL) {
					vecX = initVecX + moveX * flightTicks;
					vecY = initVecY + moveY * flightTicks;
					vecZ = initVecZ + moveZ * flightTicks;
				}

				approxSpeed = (float) (speed * Math.pow(0.99, flightTicks));
			}
		}

		double antiGrav = 0.5F * gravity * flightTicks * flightTicks;
		vecY -= antiGrav;

		double horizDistance = (double) MathHelper.sqrt_double(vecX * vecX
				+ vecZ * vecZ);

		if (horizDistance >= 1.0E-7D) {
			float yaw = (float) (Math.atan2(vecZ, vecX) * 180.0D / Math.PI) - 90.0F;
			float pitch = (float) (-(Math.atan2(vecY, horizDistance) * 180.0D / Math.PI));
			double offsetX = vecX / horizDistance;
			double offsetY = vecY / horizDistance;
			double offsetZ = vecZ / horizDistance;
			this.setLocationAndAngles(shooter.posX + offsetX, this.posY
					+ offsetY, shooter.posZ + offsetZ, yaw, pitch);
			this.yOffset = 0.0F;
			this.setThrowableHeading(vecX, vecY, vecZ, speed, deviation);
		}

		knockbackMode = QuiverModConfigManager.arrowKnockbackMode;
	}

	public EntityQuiverModArrow(World world, EntityLivingBase shooter,
			float speed) {
		super(world);

		renderDistanceWeight = 10;
		shootingEntity = shooter;

		if (shooter instanceof EntityPlayer) {
			canBePickedUp = 1;
		}

		setSize(0.5F, 0.5F);
		setLocationAndAngles(shooter.posX,
				shooter.posY + shooter.getEyeHeight(), shooter.posZ,
				shooter.rotationYaw, shooter.rotationPitch);

		posY -= 0.1;

		if (QuiverModConfigManager.offsetArrows) {
			posX -= MathHelper.cos(rotationYaw / 180.0F * (float) Math.PI) * 0.16F;
			posZ -= MathHelper.sin(rotationYaw / 180.0F * (float) Math.PI) * 0.16F;
		}

		yOffset = 0.0F;

		motionX = -MathHelper.sin(rotationYaw / 180.0F * (float) Math.PI)
				* MathHelper.cos(rotationPitch / 180.0F * (float) Math.PI);
		motionZ = MathHelper.cos(rotationYaw / 180.0F * (float) Math.PI)
				* MathHelper.cos(rotationPitch / 180.0F * (float) Math.PI);
		motionY = -MathHelper.sin(rotationPitch / 180.0F * (float) Math.PI);

		setLocationAndAngles(posX, posY, posZ, 0, 0);
		setThrowableHeading(motionX, motionY, motionZ, speed * 1.5F, 1.0F);

		
		 * Vec3 startPos = world.getWorldVec3Pool().getVecFromPool(shooter.posX,
		 * shooter.posY + shooter.getEyeHeight(), shooter.posZ); Vec3 endPos =
		 * world.getWorldVec3Pool().getVecFromPool(startPos.xCoord + motionX *
		 * 100, shooter.posY + shooter.getEyeHeight() + motionY * 100,
		 * shooter.posZ + motionZ * 100); MovingObjectPosition hit =
		 * world.rayTraceBlocks_do_do(startPos, endPos, false, true);
		 * 
		 * if (hit != null) { motionX = hit.hitVec.xCoord - posX; motionY =
		 * hit.hitVec.yCoord - posY; motionZ = hit.hitVec.zCoord - posZ;
		 * 
		 * setThrowableHeading(motionX, motionY, motionZ, speed * 1.5F, 1.0F); }
		 

		knockbackMode = QuiverModConfigManager.arrowKnockbackMode;
	}

	protected void entityInit() {
		super.entityInit();
	}

	*//**
	 * Similar to setArrowHeading, it's point the throwable entity to a x, y, z
	 * direction.
	 *//*
	public void setThrowableHeading(double vecX, double vecY, double vecZ,
			float speed, float deviation) {
		float distance = MathHelper.sqrt_double(vecX * vecX + vecY * vecY
				+ vecZ * vecZ);
		vecX /= (double) distance;
		vecY /= (double) distance;
		vecZ /= (double) distance;
		vecX += rand.nextGaussian() * (double) (rand.nextBoolean() ? -1 : 1)
				* 0.007499999832361937D * (double) deviation;
		vecY += rand.nextGaussian() * (double) (rand.nextBoolean() ? -1 : 1)
				* 0.007499999832361937D * (double) deviation;
		vecZ += rand.nextGaussian() * (double) (rand.nextBoolean() ? -1 : 1)
				* 0.007499999832361937D * (double) deviation;
		vecX *= (double) speed;
		vecY *= (double) speed;
		vecZ *= (double) speed;
		motionX = vecX;
		motionY = vecY;
		motionZ = vecZ;
		double horizDistance = MathHelper
				.sqrt_double(vecX * vecX + vecZ * vecZ);
		prevRotationYaw = rotationYaw = (float) (Math.atan2(vecX, vecZ) * 180.0D / Math.PI);
		prevRotationPitch = rotationPitch = (float) (Math.atan2(vecY,
				(double) horizDistance) * 180.0D / Math.PI);
		ticksInGround = 0;
	}

	public static double degreesToRad = Math.PI / 180;

	protected Vec3 getRotatedCoords(double x, double y, double z, float length) {
		float pitch = (float) (rotationPitch * degreesToRad);
		float yaw = (float) ((rotationYaw - 90) * degreesToRad);

		x = x + (length * Math.cos(pitch) * Math.cos(yaw));
		y = y + (length * Math.sin(pitch));
		z = z + (-length * Math.cos(pitch) * Math.sin(yaw));

		return Vec3.createVectorHelper(x, y, z);
	}

	protected void setHeadBounds() {
		if (headBoundingBox == null)
			headBoundingBox = AxisAlignedBB.getBoundingBox(0, 0, 0, 0, 0, 0);

		Vec3 headCoords = getRotatedCoords(posX, posY, posZ, 0.4F);

		headBoundingBox.setBounds(headCoords.xCoord - headWidth,
				headCoords.yCoord - headWidth, headCoords.zCoord - headWidth,
				headCoords.xCoord + headWidth, headCoords.yCoord + headWidth,
				headCoords.zCoord + headWidth);
	}

	protected void setBounds() {
		if (worldObj != null) {
			// setHeadBounds();

			Vec3 headCoords = getRotatedCoords(posX, posY, posZ,
					0.15F * arrowLength);
			Vec3 tailCoords;

			if (inGround) {
				tailCoords = getRotatedCoords(posX, posY, posZ, -0.6F
						* arrowLength);
			} else {
				tailCoords = getRotatedCoords(posX - motionX, posY - motionY,
						posZ - motionZ, -0.6F * arrowLength);
			}

			boundingBox.setBounds(headCoords.xCoord, headCoords.yCoord,
					headCoords.zCoord, headCoords.xCoord, headCoords.yCoord,
					headCoords.zCoord);
			AxisAlignedBB newBoundingBox = boundingBox.addCoord(
					tailCoords.xCoord - headCoords.xCoord,
					tailCoords.yCoord - headCoords.yCoord,
					tailCoords.zCoord - headCoords.zCoord).expand(0.15, 0.15,
					0.15);
			boundingBox.setBB(newBoundingBox);
		}
	}

	public void setPosition(double par1, double par3, double par5) {
		super.setPosition(par1, par3, par5);
		setBounds();
	}

	public void setPosition(Vec3 position) {
		setPosition(position.xCoord, position.yCoord, position.zCoord);
	}

	protected void setRotation(float yaw, float pitch) {
		super.setRotation(yaw, pitch);
		setBounds();
	}

	@SideOnly(Side.CLIENT)
	*//**
	 * Sets the position and rotation. Only difference from the other one is no bounding on the rotation. Args: posX,
	 * posY, posZ, yaw, pitch
	 *//*
	public void setPositionAndRotation2(double par1, double par3, double par5,
			float par7, float par8, int par9) {
		this.setPosition(par1, par3, par5);
		this.setRotation(par7, par8);
	}

	@SideOnly(Side.CLIENT)
	*//**
	 * Sets the velocity to the args. Args: x, y, z
	 *//*
	public void setVelocity(double par1, double par3, double par5) {
		this.motionX = par1;
		this.motionY = par3;
		this.motionZ = par5;

		if (this.prevRotationPitch == 0.0F && this.prevRotationYaw == 0.0F) {
			float var7 = MathHelper.sqrt_double(par1 * par1 + par5 * par5);
			this.prevRotationYaw = this.rotationYaw = (float) (Math.atan2(par1,
					par5) * 180.0D / Math.PI);
			this.prevRotationPitch = this.rotationPitch = (float) (Math.atan2(
					par3, (double) var7) * 180.0D / Math.PI);
			this.prevRotationPitch = this.rotationPitch;
			this.prevRotationYaw = this.rotationYaw;
			this.setLocationAndAngles(this.posX, this.posY, this.posZ,
					this.rotationYaw, this.rotationPitch);
			this.ticksInGround = 0;
		}
	}

	public void setValuesForStack(ItemStack arrowStack) {
		ItemStack drop = null;

		if (arrowStack != null)
			drop = arrowStack.copy();
		else
			drop = new ItemStack(Items.arrow);

		drop.stackSize = 1;

		if (arrowStack.getItem() == ModItems.fireArrow) {
			setFire(100);
		}

		
		 * if (arrowStack.getItem() == ModItems.impactExplosiveArrow.itemID) {
		 * setExplosive(1.5F); setExplodeWhileInWater(true); } else if
		 * (arrowStack.getItem() == ModItems.timedExplosiveArrow.itemID) {
		 * setExplosive(3); setExplosionTimer(40); } else if
		 * (arrowStack.getItem() == ModItems.enderArrow.itemID) {
		 * setTeleportShooter(true); } else if (arrowStack.getItem() ==
		 * ModItems.torchArrow.itemID) { setPlaceTorch(true); } else if
		 * (arrowStack.getItem() == ModItems.drillArrow.itemID) { if
		 * (ModItems.drillArrow.isBroken(arrowStack.getItemDamage())) {
		 * setBreakBlocks(0.5F); } else { setBreakBlocks(6.5F);
		 * drop.setItemDamage
		 * (ModItems.drillArrow.setBroken(drop.getItemDamage(), true)); } } else
		 * if (arrowStack.getItem() == ModItems.potionArrow.itemID) {
		 * ArrayList<PotionEffect> effects =
		 * (ArrayList<PotionEffect>)ModItems.potionArrow.getEffects(arrowStack);
		 * setPotionEffects(arrowStack.getItemDamage(), effects); }
		 * 
		 * if (arrowStack.getItem() instanceof ISplittingArrow) { int splitCount
		 * =
		 * ((ISplittingArrow)arrowStack.getItem()).getSplittingArrowCount(arrowStack
		 * .getItemDamage());
		 * 
		 * if (splitCount <= 0) splitCount = 1;
		 * 
		 * if (splitCount > 1) { double mult = Math.pow(1.1, splitCount);
		 * setSplit(2, splitCount, mult); setDamage(getDamage() * mult); } }
		 

		setPickupItemStack(drop);
	}

	public void setPickupItemStack(ItemStack itemStack) {
		pickupItemStack = itemStack;
	}

	public void setExplosive(float size) {
		explosionSize = size;
	}

	public void setExplosionBurn(boolean burn) {
		explosionBurn = burn;
	}

	public void setExplosionTimer(int time) {
		explosionTime = time;
	}

	public void setExplodeWhileInWater(boolean explode) {
		explodeInWater = true;
	}

	public void setTeleportShooter(boolean teleport) {
		teleportShooter = teleport;

		if (QuiverModConfigManager.useChunkLoader) {
			chunkLoader = ForgeChunkManager.requestTicket(BAR.inst, worldObj,
					ForgeChunkManager.Type.ENTITY);

			if (chunkLoader != null) {
				chunkLoader.bindEntity(this);
				chunkLoader.setChunkListDepth(chunkLoader
						.getMaxChunkListDepth());
			}
		}
	}

	public void setPlaceTorch(boolean place) {
		placeTorch = place;
	}

	public void setBreakBlocks(float blockCount) {
		breakBlocks = blockCount;
	}

	
	 * public void setPotionEffects(int damage, ArrayList<PotionEffect> effects)
	 * { potionDamage = damage; potionEffects = effects; splashPotion =
	 * QuiverMod.potionArrow.isSplash(damage); }
	 
	// TODO remove seal when potion arrows are back

	public void setSplit(int time, int splitCount, double slow) {
		splitTime = time;
		splitArrowCount = splitCount;

		motionX /= slow;
		motionY /= slow;
		motionZ /= slow;
	}

	private void getShooter() {
		if (actualTicks < 5) {
			if (shootingEntity == null) {
				if (shootingEntityID != -1) {
					shootingEntity = worldObj.getEntityByID(shootingEntityID);
				} else if (shootingPlayerName != null) {
					shootingEntity = worldObj
							.getPlayerEntityByName(shootingPlayerName);
				}
			}

			
			 * if (worldObj.isRemote && shootingEntity == null &&
			 * QuiverMod.proxy.mc != null &&
			 * QuiverMod.proxy.mc.isSingleplayer()) { shootingEntity =
			 * QuiverMod.proxy.mc.thePlayer; }
			 
		}
	}

	private int[] getPos() {
		int[] pos = new int[3];

		if (inGround) {
			int x = xTile;
			int y = yTile;
			int z = zTile;

			if (x == 0 && y == 0 && z == 0) {
				x = (int) posX;
				y = (int) posY;
				z = (int) posZ;
			}

			if (hitSide == 0)
				y--;
			else if (hitSide == 1)
				y++;
			else if (hitSide == 2)
				z--;
			else if (hitSide == 3)
				z++;
			else if (hitSide == 4)
				x--;
			else if (hitSide == 5)
				x++;

			pos[0] = x;
			pos[1] = y;
			pos[2] = z;
		} else {
			pos[0] = (int) posX;
			pos[1] = (int) posY;
			pos[2] = (int) posZ;
		}

		return pos;
	}

	private Vec3 getTeleportPos(MovingObjectPosition mop) {
		Vec3 out = Vec3.createVectorHelper(mop.hitVec.xCoord,
				mop.hitVec.yCoord, mop.hitVec.zCoord);

		if (shootingEntity != null) {
			if (mop.sideHit == 0) {
				out.yCoord -= shootingEntity.height;
			} else if (mop.sideHit == 2) {
				out.zCoord -= 0.5;
			} else if (mop.sideHit == 3) {
				out.zCoord += 0.5;
			} else if (mop.sideHit == 4) {
				out.xCoord -= 0.5;
			} else if (mop.sideHit == 5) {
				out.xCoord += 0.5;
			}
		}

		return out;
	}

	private boolean tryExplodeNoDeath(double x, double y, double z) {
		if (explosionSize > 0 && !exploded) {
			Block id = worldObj.getBlock((int) x, (int) y, (int) z);

			if (explodeInWater || (id != Blocks.water)) {
				if (!worldObj.isRemote) {
					
					 * ModExplosion explosion = new ModExplosion(worldObj, this,
					 * x, y, z, explosionSize); explosion.indirectExploder =
					 * shootingEntity; explosion.isFlaming = explosionBurn ||
					 * isBurning(); explosion.isSmoking = true;
					 * explosion.doExplosionA(); explosion.doExplosionB(true);
					 * 
					 * Iterator playerIter = worldObj.playerEntities.iterator();
					 * 
					 * while (playerIter.hasNext()) {
					 * ((EntityPlayerMP)playerIter
					 * .next()).playerNetServerHandler.sendPacketToPlayer(new
					 * S27PacketExplosion(x, y, z, explosionSize,
					 * explosion.affectedBlockPositions, null)); }
					 
					worldObj.createExplosion((Entity)null, x, y, z, explosionSize,
							explodeInWater);
				}

				exploded = true;
				return true;
			}
		}

		return false;
	}

	private void explode(double x, double y, double z) {
		if (tryExplodeNoDeath(x, y, z))
			setDead();
	}

	private void explode(Vec3 pos) {
		explode(pos.xCoord, pos.yCoord, pos.zCoord);
	}

	private void explode(int x, int y, int z) {
		explode((double) x, (double) y, (double) z);
	}

	private void teleportParticles() {
		if (shootingEntity != null) {
			for (int i = 0; i < 40; i++) {
				worldObj.spawnParticle("portal", shootingEntity.posX,
						shootingEntity.posY - 2 + rand.nextDouble() * 2.0,
						shootingEntity.posZ, rand.nextGaussian(), 0,
						rand.nextGaussian());
			}
		}
	}

	private boolean teleport(double x, double y, double z) {
		if (teleportShooter && shootingEntity != null
				&& shootingEntity instanceof EntityLivingBase) {
			EntityLivingBase shooter = ((EntityLivingBase) shootingEntity);

			worldObj.playSoundEffect(shooter.posX, shooter.posY, shooter.posZ,
					"mob.endermen.portal", 1.0F, 1.0F);

			shooter.setPositionAndUpdate(x, y, z);
			shooter.fallDistance = 0;
			shooter.attackEntityFrom(DamageSource.fall, teleportShooterDamage);

			teleportShooter = false;
			return true;
		}

		return false;
	}

	private boolean teleport(Vec3 pos) {
		return teleport(pos.xCoord, pos.yCoord, pos.zCoord);
	}

	private void split() {
		if (lastHitMOP == null) {
			setDamage(getDamage() / (double) splitArrowCount);

			Item pickupItem = pickupItemStack.getItem();

			if (pickupItem instanceof ISplittingArrow) {
				ISplittingArrow splittingPickupItem = (ISplittingArrow) pickupItem;
				pickupItemStack.setItemDamage(splittingPickupItem
						.getItemDamageForArrowCount(
								pickupItemStack.getItemDamage(), 1));
			} else {
				pickupItemStack.setItemDamage(0);
			//}

			if (!worldObj.isRemote) {
				for (int i = 0; i < splitArrowCount - 1; i++) {
					EntityQuiverModArrow arrow = new EntityQuiverModArrow(
							worldObj);

					arrow.copyDataFrom(this, true);

					arrow.slowMo = slowMo;

					arrow.splitTime = 0;
					arrow.splitArrowCount = 1;

					arrow.setLocationAndAngles(prevPosX, prevPosY, prevPosZ,
							rotationYaw, rotationPitch);

					arrow.prevRotationYaw = prevRotationYaw;
					arrow.prevRotationPitch = prevRotationPitch;

					arrow.shootingEntity = shootingEntity;
					arrow.canBePickedUp = canBePickedUp;

					arrow.setIsCritical(getIsCritical());
					arrow.setDamage(getDamage());

					double devX = (rand.nextDouble() - 0.5) * 0.5;
					double devY = (rand.nextDouble() - 0.5) * 0.5;
					double devZ = (rand.nextDouble() - 0.5) * 0.5;

					arrow.motionX = motionX;
					arrow.motionY = motionY;
					arrow.motionZ = motionZ;

					arrow.motionX += devX;
					arrow.motionY += devY;
					arrow.motionZ += devZ;

					arrow.onUpdate();

					worldObj.spawnEntityInWorld(arrow);
				}
			}

			
			 * if (worldObj.isRemote) { Block web = Block.web;
			 * 
			 * //worldObj.playSoundAtEntity(this, web.stepSound.getBreakSound(),
			 * (web.stepSound.getVolume() + 1.0F) / 2.0F,
			 * web.stepSound.getPitch() * 0.8F);
			 * 
			 * for (int i = 0; i < 16; i++) {
			 * QuiverMod.proxy.particleSpawning.spawnDiggingFX(worldObj,
			 * prevPosX, prevPosY, prevPosZ, 0, 0, 0, web, 2, 0); } }
			 

			splitTime = 0;
			splitArrowCount = 0;
		}
	}

	public static AxisAlignedBB createBoundingBox(Vec3 vec1, Vec3 vec2) {
		AxisAlignedBB out = AxisAlignedBB.getBoundingBox(0, 0, 0, 0, 0, 0);

		if (vec1.xCoord < vec2.xCoord) {
			out.minX = vec1.xCoord;
			out.maxX = vec2.xCoord;
		} else {
			out.minX = vec2.xCoord;
			out.maxX = vec1.xCoord;
		}

		if (vec1.yCoord < vec2.yCoord) {
			out.minY = vec1.yCoord;
			out.maxY = vec2.yCoord;
		} else {
			out.minY = vec2.yCoord;
			out.maxY = vec1.yCoord;
		}

		if (vec1.zCoord < vec2.zCoord) {
			out.minZ = vec1.zCoord;
			out.maxZ = vec2.zCoord;
		} else {
			out.minZ = vec2.zCoord;
			out.maxZ = vec1.zCoord;
		}

		return out;
	}

	private void onGroundImpact(MovingObjectPosition mop) {
		Vec3 teleportPos = getTeleportPos(mop);

		if (!worldObj.isRemote && teleport(teleportPos)) {
			setDead();
		} else if (teleportShooter) {
			setDead();
		}

		int[] posInt = getPos();
		int intX = posInt[0];
		int intY = posInt[1];
		int intZ = posInt[2];
		Block blockID = worldObj.getBlock(intX, intY, intZ);

		if (inGround && explosionTime < 0) {
			explode(mop.hitVec);
		}

		if (splashPotion) {
			makePotionSplash();
			setDead();
		}

		if (placeTorch) {
			intY = (int) mop.hitVec.yCoord;

			if (!worldObj.isRemote)
				worldObj.setBlock(intX, intY, intZ, Blocks.torch);
			placeTorch = false;
		}
	}

	private boolean canAttackEntity(Entity entity) {
		if (entity == null)
			return false;

		return true;
	}

	private void resetTickCounters() {
		ticksInGround = 0;
		ticksInAir = 0;
	}

	private void onDislodgedFromBlock(boolean broken) {
		inGround = false;

		double vecX, vecY, vecZ;
		vecX = vecY = vecZ = 0;

		if (broken) {
			vecX = posX - (xTile + 0.5);
			vecY = posY - (yTile + 0.5);
			vecZ = posZ - (zTile + 0.5);

			double vecDist = Math.sqrt(vecX * vecX + vecY * vecY + vecZ * vecZ);
			vecX /= vecDist;
			vecY /= vecDist;
			vecZ /= vecDist;

			motionX = vecX * 0.2;
			motionY = vecY * 0.2;
			motionZ = vecZ * 0.2;
		} else {
			
			 * vecX = (xTile + 0.5) - posX; vecY = (yTile + 0.5) - posY; vecZ =
			 * (zTile + 0.5) - posZ;
			 

			double motionDist = Math.sqrt(motionX * motionX + motionY * motionY
					+ motionZ * motionZ);
			motionX /= motionDist;
			motionY /= motionDist;
			motionZ /= motionDist;
			motionX *= 0.05;
			motionY *= 0.05;
			motionZ *= 0.05;
		}

		resetTickCounters();
	}

	private Vec3 getHitPos(MovingObjectPosition mop) {
		double motion = Math.sqrt(motionX * motionX + motionY * motionY
				+ motionZ * motionZ);
		double offsetX = motionX;
		double offsetY = motionY;
		double offsetZ = motionZ;
		offsetX /= motion * 20;
		offsetY /= motion * 20;
		offsetZ /= motion * 20;

		return Vec3.createVectorHelper(
				mop.hitVec.xCoord - offsetX, mop.hitVec.yCoord - offsetY,
				mop.hitVec.zCoord - offsetZ);
	}

	private boolean movePiston() {
		TileEntityPiston piston = null;
		Block inBlock = worldObj.getBlock(xTile, yTile, zTile);

		int orientation = -1;
		int offX = 0;
		int offY = 0;
		int offZ = 0;
		int dirX = 0;
		int dirY = 0;
		int dirZ = 0;
		int baseX = xTile;
		int baseY = yTile;
		int baseZ = zTile;

		if (inBlock == null) {
			orientation = BlockPistonBase.getPistonOrientation(inData);
			offX = -Facing.offsetsXForSide[orientation];
			offY = -Facing.offsetsYForSide[orientation];
			offZ = -Facing.offsetsZForSide[orientation];
			baseX += offX;
			baseY += offY;
			baseZ += offZ;
			TileEntity tileEntity = worldObj.getTileEntity(xTile + offX,
					yTile + offY, zTile + offZ);

			if (tileEntity instanceof TileEntityPiston) {
				piston = (TileEntityPiston) tileEntity;
			}
		} else if (inBlock instanceof BlockPistonMoving) {
			TileEntity tileEntity = worldObj.getTileEntity(xTile, yTile,
					zTile);

			if (tileEntity instanceof TileEntityPiston) {
				piston = (TileEntityPiston) tileEntity;
				orientation = piston.getPistonOrientation();
				offX = Facing.offsetsXForSide[orientation];
				offY = Facing.offsetsYForSide[orientation];
				offZ = Facing.offsetsZForSide[orientation];

				if (!piston.isExtending()) {
					offX *= -1;
					offY *= -1;
					offZ *= -1;
				} else {
					baseX -= offX;
					baseY -= offY;
					baseZ -= offZ;
				}
			}
		} else if (inBlock instanceof BlockPistonBase) {
			orientation = BlockPistonBase.getPistonOrientation(inData);
			offX = Facing.offsetsXForSide[orientation];
			offY = Facing.offsetsYForSide[orientation];
			offZ = Facing.offsetsZForSide[orientation];
			TileEntity tileEntity = worldObj.getTileEntity(xTile + offX,
					yTile + offY, zTile + offZ);

			if (tileEntity instanceof TileEntityPiston) {
				piston = (TileEntityPiston) tileEntity;
			}
		}

		if (piston != null) {
			boolean extending = piston.isExtending();

			dirX = Facing.offsetsXForSide[orientation];
			dirY = Facing.offsetsYForSide[orientation];
			dirZ = Facing.offsetsZForSide[orientation];

			int blockX = xTile + offX;
			int blockY = yTile + offY;
			int blockZ = zTile + offZ;
			Block blockID = worldObj.getBlock(blockX, blockY, blockZ);

			AxisAlignedBB pistonBounds = null;

			switch (orientation) {
			case 0:
				pistonBounds = AxisAlignedBB.getBoundingBox(0, 0.25, 0,
						1, 1, 1);
				pistonBounds = AxisAlignedBB.getBoundingBox(0, 0, 0, 1,
						0.25, 1);
				break;
			case 1:
				pistonBounds = AxisAlignedBB.getBoundingBox(0, 0, 0, 1,
						0.75, 1);
				pistonBounds = AxisAlignedBB.getBoundingBox(0, 0.75, 0,
						1, 1, 1);
				break;
			case 2:
				pistonBounds = AxisAlignedBB.getBoundingBox(0, 0, 0.25,
						1, 1, 1);
				pistonBounds = AxisAlignedBB.getBoundingBox(0, 0, 0, 1,
						1, 0.25);
				break;
			case 3:
				pistonBounds = AxisAlignedBB.getBoundingBox(0, 0, 0, 1,
						1, 0.75);
				pistonBounds = AxisAlignedBB.getBoundingBox(0, 0, 0.75,
						1, 1, 1);
				break;
			case 4:
				pistonBounds = AxisAlignedBB.getBoundingBox(0.25, 0, 0,
						1, 1, 1);
				pistonBounds = AxisAlignedBB.getBoundingBox(0, 0, 0,
						0.25, 1, 1);
				break;
			case 5:
				pistonBounds = AxisAlignedBB.getBoundingBox(0, 0, 0,
						0.75, 1, 1);
				pistonBounds = AxisAlignedBB.getBoundingBox(0.75, 0, 0,
						1, 1, 1);
				break;
			}

			double expand = 0.1;
			pistonBounds.offset(baseX, baseY, baseZ);

			float progress = piston.func_145860_a(1);
			float lastProgress = piston.func_145860_a(0);

			if (!extending) {
				progress = 1 - progress;
				lastProgress = 1 - lastProgress;
			}

			float diff = lastProgress - progress;

			
			 * pistonBounds = pistonBounds.addCoord(dirX * progress + dirX *
			 * expand, dirY * progress + dirY * expand, dirZ * progress + dirZ *
			 * expand);
			 
			
			 * pistonBounds = pistonBounds.addCoord(dirX * (1 + expand), dirY *
			 * (1 + expand), dirZ * (1 + expand));
			 
			pistonBounds.offset(dirX * progress, dirY * progress, dirZ
					* progress);
			pistonBounds = pistonBounds.addCoord(dirX * diff, dirY * diff, dirZ
					* diff);

			pistonBounds = pistonBounds.addCoord(dirX * expand, dirY * expand,
					dirZ * expand);
			pistonBounds = pistonBounds.expand(dirX == 0 ? expand : 0,
					dirY == 0 ? expand : 0, dirZ == 0 ? expand : 0);

			if (!extending)
				pistonBounds = pistonBounds.addCoord(-dirX * expand, -dirY
						* expand, -dirZ * expand);

			if (pistonBounds.isVecInside(Vec3.createVectorHelper(posX, posY, posZ))) {
				if (!(inBlock instanceof BlockPistonMoving)) {
					xTile = blockX;
					yTile = blockY;
					zTile = blockZ;
					inTile = blockID;
					inData = worldObj.getBlockMetadata(xTile, yTile, zTile);
					prevPistonProgress = 0;
				}

				if (!worldObj.isRemote) {
					progress = piston.func_145860_a(1);
					diff = progress - prevPistonProgress;
					prevPistonProgress = progress;
					setPosition(posX + offX * diff, posY + offY * diff, posZ
							+ offZ * diff);
					forceTrackingUpdate();
				}

				return true;
			}
		}

		return false;
	}

	private void setRenderBB(AxisAlignedBB bounds) {
		renderBB = true;
		renderBBMinX = bounds.minX;
		renderBBMinY = bounds.minY;
		renderBBMinZ = bounds.minZ;
		renderBBMaxX = bounds.maxX;
		renderBBMaxY = bounds.maxY;
		renderBBMaxZ = bounds.maxZ;
	}

	@Override
	public void moveEntity(double x, double y, double z) {
		boolean allow = false;
		// Block block = Block.blocksList[inTile];

		if (!pistonMoved) {
			allow = true;
		}

		if (allow) {
			StackTraceElement[] trace = Thread.currentThread().getStackTrace();
			StackTraceElement calledFrom = trace[2];

			if (calledFrom.getClassName().equals(
					"net.minecraft.tileentity.TileEntityPiston")) {
				allow = false;
			}
		}

		if (allow) {
			double oldPosX = posX;
			double oldPosY = posY;
			double oldPosZ = posZ;
			super.moveEntity(x, y, z);
			setPosition(oldPosX + x, oldPosY + y, oldPosZ + z);
		}
	}

	protected DamageSource makeArrowDamageSource() {
		if (shootingEntity == null) {
			return new EntityDamageSourceIndirect("arrow", this, this)
					.setProjectile();
		}

		return new EntityDamageSourceIndirect("arrow", this, shootingEntity)
				.setProjectile();
	}

	public void forceTrackingUpdate() {
		if (!worldObj.isRemote) {
			WorldServer serverWorld = (WorldServer) worldObj;
			EntityTracker tracker = serverWorld.getEntityTracker();
			Set trackerSet = ObfuscationReflectionHelper.getPrivateValue(
					EntityTracker.class, tracker, "trackedEntities",
					"field_72793_b");

			for (Object trackerEntryObj : trackerSet) {
				EntityTrackerEntry trackerEntry = (EntityTrackerEntry) trackerEntryObj;
				trackerEntry.ticks = 0;
			}
		}
	}

	public void sendImpaledItemsPacket() {
		ArrayList dataList = new ArrayList();
		dataList.add(getEntityId());

		for (ImpaledItem impaled : impaledItems) {
			ItemStack stack = impaled.item.getEntityItem();
			dataList.add(stack.getItem());
			dataList.add(stack.getItemDamage());

			dataList.add(impaled.getPosition(1));
			dataList.add(impaled.targetPosition);
		}

		dataList.add(-1);
		//TODO make packet dispatchers
		PacketDispatcher
				.sendPacketToAllInDimension(
						PacketHelper.makePacket("QMArImpaledItems",
								dataList.toArray()), dimension);
	}

	public void checkItemsToImpale(Vec3 fromVec, Vec3 toVec) {
		// Vec3 fromVec = worldObj.getWorldVec3Pool().getVecFromPool(posX, posY,
		// posZ);
		// Vec3 toVec = worldObj.getWorldVec3Pool().getVecFromPool(posX +
		// motionX, posY + motionY, posZ + motionZ);

		// Vec3 fromVec = worldObj.getWorldVec3Pool().getVecFromPool(prevX,
		// prevY, prevZ);
		// Vec3 toVec = worldObj.getWorldVec3Pool().getVecFromPool(posX, posY,
		// posZ);

		if (worldObj.isRemote)
			return;

		AxisAlignedBB checkBounds = boundingBox.addCoord(motionX, motionY,
				motionZ).expand(1, 1, 1);
		List<Entity> entityList = worldObj
				.getEntitiesWithinAABBExcludingEntity(this, checkBounds);
		TreeMap<Double, EntityItem> hitItems = new TreeMap();

		for (Entity entity : entityList) {
			if (entity instanceof EntityItem) {
				EntityItem itemEnt = (EntityItem) entity;
				ItemStack stack = itemEnt.getEntityItem();

				if (stack != null) {
					Item item = stack.getItem();
					boolean full3D = ObfuscationReflectionHelper
							.getPrivateValue(Item.class, item, "bFull3D",
									"field_77789_bW");

					if (!full3D && !(item instanceof ItemTool)
							&& !(item instanceof ItemSword)
							&& !(item instanceof ItemFishingRod)
							&& !(item instanceof ItemBlock)) {
						AxisAlignedBB bobbedBB = itemEnt.boundingBox.copy()
								.offset(0, 0.05F, 0).addCoord(0, 0.3F, 0);
						this.setRenderBB(bobbedBB);

						MovingObjectPosition hit = bobbedBB.calculateIntercept(
								fromVec, toVec);

						if (hit != null) {
							hitItems.put(fromVec.distanceTo(hit.hitVec),
									itemEnt);
						}
					}
				}
			}
		}

		for (Entry<Double, EntityItem> entry : hitItems.entrySet()) {
			EntityItem hitItem = entry.getValue();
			ItemStack stack = hitItem.getEntityItem();

			while (impaledItems.size() < 5 && stack.stackSize > 0) {
				for (ImpaledItem impaled : impaledItems) {
					impaled.targetPosition += 0.1F;
				}

				ImpaledItem newImpaled = new ImpaledItem(hitItem);
				impaledItems.add(newImpaled);

				sendImpaledItemsPacket();
			}
		}
	}

	public void onImpaledItemsUpdate() {
		for (ImpaledItem impaled : impaledItems) {
			impaled.update(this);
		}

		if (impaledItems.size() >= 2) {
			ImpaledItem first = impaledItems.get(0);

			if (first.ticksImpaled % 10 == 0) {
				ImpaledItem last = impaledItems.get(impaledItems.size() - 1);

				float lastPosition = last.targetPosition;
				// last.targetPosition = first.targetPosition;
				// first.targetPosition = lastPosition;
			}
		}
	}

	public void pickUpImpaled(EntityPlayer player) {
		Iterator<ImpaledItem> iter = impaledItems.iterator();

		ImpaledItem impaled;
		ItemStack stack;

		while (iter.hasNext()) {
			impaled = iter.next();
			stack = impaled.item.getEntityItem();

			player.inventory.addItemStackToInventory(stack);

			if (stack.stackSize <= 0)
				iter.remove();
		}
	}

	*//**
	 * Called to update the entity's position/logic.
	 *//*
	public void onUpdate() {
		if (ticksInGround == 3) {
			forceTrackingUpdate();
		}

		actualTicks++;

		if (slowMo > 0) {
			prevRotationYaw = prevYawForSlowmo;
			prevRotationPitch = prevPitchForSlowmo;
		}

		if (slowMo < 1 || actualTicks % slowMo == 0) {
			getShooter();

			if (chunkLoader != null) {
				Chunk currentChunk;
				ChunkCoordIntPair chunkPos;
				int size = 25;
				int skip = 5;

				for (int x = -size; x <= size; x += skip) {
					for (int z = -size; z <= size; z += skip) {
						if (x != 0 || z != 0) {
							currentChunk = worldObj.getChunkFromBlockCoords(
									(int) (posX + x), (int) (posZ + z));
							chunkPos = currentChunk.getChunkCoordIntPair();

							ForgeChunkManager.forceChunk(chunkLoader, chunkPos);
						}
					}
				}

				currentChunk = worldObj.getChunkFromBlockCoords((int) posX,
						(int) posZ);
				chunkPos = currentChunk.getChunkCoordIntPair();

				ForgeChunkManager.forceChunk(chunkLoader, chunkPos);
			}

			if (wasInGround != inGround)
				setBounds();

			wasInGround = inGround;

			double thePrevX = prevPosX;
			double thePrevY = prevPosY;
			double thePrevZ = prevPosZ;

			onEntityUpdate();

			ticks++;
			EntityPlayer player;

			if (prevRotationPitch == 0 && prevRotationYaw == 0) {
				float motion = MathHelper.sqrt_double(motionX * motionX
						+ motionZ * motionZ);
				prevRotationYaw = rotationYaw = (float) (Math.atan2(motionX,
						motionZ) * 180.0D / Math.PI);
				prevRotationPitch = rotationPitch = (float) (Math.atan2(
						motionY, (double) motion) * 180.0D / Math.PI);
			}

			onImpaledItemsUpdate();

			
			 * int inBlockID = worldObj.getBlockId(xTile, yTile, zTile);
			 * 
			 * if (inBlockID > 0) {
			 * Block.blocksList[inBlockID].setBlockBoundsBasedOnState(worldObj,
			 * xTile, yTile, zTile); AxisAlignedBB bounds =
			 * Block.blocksList[inBlockID
			 * ].getCollisionBoundingBoxFromPool(worldObj, xTile, yTile, zTile);
			 * 
			 * if (bounds != null &&
			 * bounds.isVecInside(worldObj.getWorldVec3Pool
			 * ().getVecFromPool(posX, posY, posZ))) { inGround = true; } }
			 

			if (arrowShake > 0) {
				--arrowShake;
			}

			float horizMotion;

			if (lastHitMOP != null) {
				lastHitMOP.hitVec.xCoord = hitVecX;
				lastHitMOP.hitVec.yCoord = hitVecY;
				lastHitMOP.hitVec.zCoord = hitVecZ;

				float motion = MathHelper.sqrt_double(motionX * motionX
						+ motionY * motionY + motionZ * motionZ);

				if (lastHitMOP.typeOfHit == EnumMovingObjectType.ENTITY) {
					if (lastHitMOP.entityHit.isEntityAlive()) {
						float hitDamage = (float) (motion * damage);

						if (getIsCritical()) {
							hitDamage += rand.nextFloat() * (hitDamage / 2 + 2);
						}

						DamageSource damageSource = makeArrowDamageSource();

						if (isBurning()) {
							lastHitMOP.entityHit.setFire(5);
						}

						int prevHurtResistantTime = 0;

						if (lastHitMOP.entityHit instanceof EntityLivingBase) {
							prevHurtResistantTime = ((EntityLivingBase) lastHitMOP.entityHit).hurtResistantTime;
						}

						float hitDrillDamage = (float) hitDamage / 2;

						float prevHealth = 0;

						if (lastHitMOP.entityHit instanceof EntityLivingBase) {
							prevHealth = ((EntityLivingBase) lastHitMOP.entityHit)
									.getHealth();
						}

						if (breakBlocks > 0 && hitDrillDamage > breakBlocks) {
							hitDamage = (int) (breakBlocks * 2);
							hitDrillDamage = (float) hitDamage / 2;
						}

						double lastEntityMotionX = lastHitMOP.entityHit.motionX;
						double lastEntityMotionY = lastHitMOP.entityHit.motionY;
						double lastEntityMotionZ = lastHitMOP.entityHit.motionZ;

						if (explosionTime < 0
								&& lastHitMOP.entityHit.attackEntityFrom(
										damageSource, hitDamage)) {
							if (lastHitMOP.entityHit instanceof EntityLivingBase) {
								EntityLivingBase entityLiving = (EntityLivingBase) lastHitMOP.entityHit;
								float damageTaken = prevHealth
										- entityLiving.getHealth();

								if (breakBlocks >= 1) {
									breakBlocks -= damageTaken;
									motionX *= 0.6;
									motionY *= 0.6;
									motionZ *= 0.6;
								}

								if (splitArrowCount > 1)
									entityLiving.hurtResistantTime = prevHurtResistantTime;

								if (!worldObj.isRemote) {
									entityLiving
											.setArrowCountInEntity(entityLiving
													.getArrowCountInEntity() + 1);
								}

								if (knockbackStrength > 0) {
									horizMotion = MathHelper
											.sqrt_double(motionX * motionX
													+ motionZ * motionZ);

									if (horizMotion > 0.0F) {
										lastHitMOP.entityHit
												.addVelocity(
														motionX
																* (double) knockbackStrength
																* 0.6
																/ (double) horizMotion,
														0.1,
														motionZ
																* (double) knockbackStrength
																* 0.6
																/ (double) horizMotion);
									}
								}

								double motionDiffX = lastHitMOP.entityHit.motionX
										- lastEntityMotionX;
								double motionDiffY = lastHitMOP.entityHit.motionY
										- lastEntityMotionY;
								double motionDiffZ = lastHitMOP.entityHit.motionZ
										- lastEntityMotionZ;
								float motionMult = 1;

								if (knockbackMode > 0) {
									float armor = (1 - damageTaken
											/ (float) hitDamage) * 20;

									switch (knockbackMode) {
									case 1:
										motionMult /= Math.max(1, armor / 4);
										break;
									case 2:
										motionMult /= Math.max(1,
												(20 - armor) / 4);
										break;
									}
								}

								if (motionMult < 0.5F)
									motionMult = 0.5F;

								lastHitMOP.entityHit.motionX = lastEntityMotionX
										+ motionDiffX * motionMult;
								lastHitMOP.entityHit.motionY = lastEntityMotionY
										+ motionDiffY * motionMult;
								lastHitMOP.entityHit.motionZ = lastEntityMotionZ
										+ motionDiffZ * motionMult;

								if (shootingEntity != null) {
									EnchantmentThorns.func_151367_b(
											(EntityLivingBase)shootingEntity, entityLiving, rand.nextInt());
								}
								//TODO dammit. reenable thorns some time

								if (shootingEntity != null
										&& lastHitMOP.entityHit != shootingEntity
										&& lastHitMOP.entityHit instanceof EntityPlayer
										&& shootingEntity instanceof EntityPlayerMP) {
									((EntityPlayerMP) shootingEntity).playerNetServerHandler
											.sendPacketToPlayer(new Packet70GameEvent(
													6, 0));
								}
							}

							explode(lastHitMOP.hitVec.xCoord,
									lastHitMOP.hitVec.yCoord,
									lastHitMOP.hitVec.zCoord);

							if (breakBlocks < 1
									&& !(lastHitMOP.entityHit instanceof EntityEnderman)) {
								playSound("random.bowhit", 1.0F,
										1.2F / (rand.nextFloat() * 0.2F + 0.9F));
								setDead();
							} else if (teleportShooter) {
								setDead();
							}

							teleport(lastHitMOP.hitVec.xCoord,
									lastHitMOP.hitVec.yCoord,
									lastHitMOP.hitVec.zCoord);
						} else if (lastHitMOP.entityHit != shootingEntity
								|| ticksInAir >= 5) {
							float diffX = (float) (posX - lastHitMOP.entityHit.posX);
							float diffY = (float) (posY - lastHitMOP.entityHit.posY);
							float diffZ = (float) (posZ - lastHitMOP.entityHit.posZ);
							float distance = MathHelper.sqrt_float(diffX
									* diffX + diffY * diffY + diffZ * diffZ);
							diffX /= distance;
							diffY /= distance;
							diffZ /= distance;
							distance = MathHelper.sqrt_double(motionX * motionX
									+ motionY * motionY + motionZ * motionZ) / 3;
							motionX += diffX;
							motionY += diffY;
							motionZ += diffZ;
							float newDistance = MathHelper.sqrt_double(motionX
									* motionX + motionY * motionY + motionZ
									* motionZ);
							motionX /= newDistance;
							motionY /= newDistance;
							motionZ /= newDistance;
							motionX *= distance;
							motionY *= distance;
							motionZ *= distance;
							lastHitMOP.entityHit.motionX -= diffX / 5D;
							lastHitMOP.entityHit.motionY -= diffY / 5D;
							lastHitMOP.entityHit.motionZ -= diffZ / 5D;
						}

						if (potionEffects != null || potionDamage != -1) {
							EntityLivingBase entityHitLiving = null;

							if (lastHitMOP.entityHit instanceof EntityLivingBase)
								entityHitLiving = (EntityLivingBase) lastHitMOP.entityHit;
							else if (lastHitMOP.entityHit instanceof EntityDragonPart)
								entityHitLiving = (EntityLivingBase) ((EntityDragonPart) lastHitMOP.entityHit).entityDragonObj;

							if (entityHitLiving != null && !splashPotion) {
								if (potionEffects != null) {
									for (PotionEffect effect : potionEffects) {
										entityHitLiving.addPotionEffect(effect);
									}
								}
							} else {
								makePotionSplash();
								setDead();
							}
						}
					}
				} else // TILE
				{
					xTile = lastHitMOP.blockX;
					yTile = lastHitMOP.blockY;
					zTile = lastHitMOP.blockZ;
					inTile = worldObj.getBlockId(xTile, yTile, zTile);

					if (inTile > 0) {
						inGround = true;
						inData = worldObj.getBlockMetadata(xTile, yTile, zTile);
						hitSide = lastHitMOP.sideHit;

						setIsCritical(false);

						arrowShake = 7;

						float volume = motion / 3;

						
						 * if (volMult > 1.5F) volMult = 1.5F; else
						 if (volume < 0.25F)
							volume = 0.25F;

						playSound("random.bowhit", volume,
								1.2F / (rand.nextFloat() * 0.2F + 0.9F));

						Block block = Block.blocksList[inTile];

						if (block != null) {
							StepSound stepSound = block.stepSound;

							if (stepSound != null)
								playSound(stepSound.getPlaceSound(), volume,
										1.2F / (rand.nextFloat() * 0.2F + 0.9F));
						}

						if (inTile != 0) {
							Block.blocksList[inTile].onEntityCollidedWithBlock(
									worldObj, xTile, yTile, zTile, this);
						}

						onGroundImpact(lastHitMOP);
					} else {
						inTile = 0;
						xTile = 0;
						yTile = 0;
						zTile = 0;
					}
				}

				lastHitMOP = null;
				// motionX *= 0.001;
				// motionY *= 0.001;
				// motionZ *= 0.001;
			}

			float distance = 0;

			if (inGround) {
				Block prevInBlock = Block.blocksList[inTile];

				int blockID = worldObj.getBlockId(xTile, yTile, zTile);
				int metadata = worldObj.getBlockMetadata(xTile, yTile, zTile);
				Block inNowBlock = Block.blocksList[blockID];

				boolean associated = false;

				if (prevInBlock != null) {
					associated = Block.isAssociatedBlockID(inTile, blockID);

					if (!associated && inNowBlock != null) {
						associated = inNowBlock.idDropped(metadata, rand, 0) == prevInBlock
								.idDropped(inData, rand, 0);
					}

					if (pistonMoved) {
						if (inNowBlock == null
								|| inNowBlock instanceof BlockPistonMoving
								|| inNowBlock instanceof BlockPistonExtension
								|| inNowBlock instanceof BlockPistonBase) {
							associated = true;

							if (!movePiston()) {
								pistonMoved = false;
							}
						} else {
							pistonMoved = false;
						}
					} else if (inData != metadata
							&& inNowBlock != null
							&& prevInBlock instanceof BlockPistonBase
							&& (inNowBlock instanceof BlockPistonBase || inNowBlock instanceof BlockPistonMoving)) {
						associated = true;
						pistonMoved = true;
						movePiston();
					} else if (inTile != blockID
							&& prevInBlock instanceof BlockPistonExtension
							&& inNowBlock == null) {
						associated = true;
						pistonMoved = movePiston();
					} else if (!associated) {
						associated = prevInBlock instanceof BlockPistonMoving
								&& (inNowBlock instanceof BlockPistonExtension || inNowBlock instanceof BlockPistonBase);
					}

					if (associated) {
						blockID = worldObj.getBlockId(xTile, yTile, zTile);
						metadata = worldObj.getBlockMetadata(xTile, yTile,
								zTile);
						inNowBlock = Block.blocksList[blockID];
					}
				}

				if (associated) {
					if (inNowBlock != null
							&& (inData != metadata || inNowBlock instanceof BlockDoor)) {
						MovingObjectPosition mop = null;

						if (inNowBlock.canCollideCheck(metadata, false)
								&& inNowBlock.getCollisionBoundingBoxFromPool(
										worldObj, xTile, yTile, zTile) != null) {
							double motion = Math.sqrt(motionX * motionX
									+ motionY * motionY + motionZ * motionZ);
							double normMotionX = motionX;
							double normMotionY = motionY;
							double normMotionZ = motionZ;
							normMotionX /= motion;
							normMotionY /= motion;
							normMotionZ /= motion;

							Vec3 pos1 = worldObj.getWorldVec3Pool()
									.getVecFromPool(posX - normMotionX * 0.1,
											posY - normMotionY * 0.1,
											posZ - normMotionZ * 0.1);
							Vec3 pos2 = worldObj.getWorldVec3Pool()
									.getVecFromPool(posX + normMotionX,
											posY + normMotionY,
											posZ + normMotionZ);

							mop = inNowBlock.collisionRayTrace(worldObj, xTile,
									yTile, zTile, pos1, pos2);
						}

						if (mop == null && !pistonMoved) {
							onDislodgedFromBlock(false);
							motionX = 0;
							motionY = 0;
							motionZ = 0;
						}

						if (mop != null && !pistonMoved) {
							AxisAlignedBB smallBounds = AxisAlignedBB
									.getBoundingBox(posX, posY, posZ, posX,
											posY, posZ)
									.expand(0.08, 0.08, 0.08);
							Vec3 hitPos = getHitPos(mop);

							if (!smallBounds.isVecInside(hitPos)) {
								inGround = false;
								resetTickCounters();

								// motionX = hitPos.xCoord - posX;
								// motionY = hitPos.yCoord - posY;
								// motionZ = hitPos.zCoord - posZ;
								setPosition(hitPos);

								lastHitMOP = mop;
								hitVecX = mop.hitVec.xCoord;
								hitVecY = mop.hitVec.yCoord;
								hitVecZ = mop.hitVec.zCoord;
							}
						}
					} else if (inNowBlock == null) {
						onDislodgedFromBlock(false);
					}

					if (inGround) {
						ticksInGround++;

						if (ticksInGround >= 1200) {
							setDead();
						}
					}

					
					 * if (pistonMoved && !(worldObj.getBlockTileEntity(xTile,
					 * yTile, zTile) instanceof TileEntityPiston)) {
					 * QuiverMod.log("resetmoved"); pistonMoved = false; }
					 
				} else {
					onDislodgedFromBlock(true);
				}

				if (inGround && (inTile != blockID || inData != metadata)) {
					if (blockID > 0) {
						inTile = blockID;
						inData = metadata;
					}
				}
			} else {
				++this.ticksInAir;
				Vec3 fromVec = null;
				Vec3 toVec = null;
				MovingObjectPosition mop = null;

				double motion = MathHelper.sqrt_double(motionX * motionX
						+ motionY * motionY + motionZ * motionZ);
				ArrayList<Vec3> blocksToBreak = new ArrayList();

				while (fromVec == null || mop != null) {
					fromVec = worldObj.getWorldVec3Pool().getVecFromPool(posX,
							posY, posZ);
					toVec = worldObj.getWorldVec3Pool().getVecFromPool(
							posX + motionX, posY + motionY, posZ + motionZ);
					mop = worldObj.rayTraceBlocks_do_do(fromVec, toVec, false,
							true);

					boolean brokeAnotherBlock = false;

					if (!worldObj.isRemote && mop != null) {
						int id = worldObj.getBlockId(mop.blockX, mop.blockY,
								mop.blockZ);
						Block block = Block.blocksList[id];

						float hardness = block.getBlockHardness(worldObj,
								mop.blockX, mop.blockY, mop.blockZ);

						if (hardness > 0) {
							int metadata = worldObj.getBlockMetadata(
									mop.blockX, mop.blockY, mop.blockZ);
							float toolHardness = 6 - Item.pickaxeIron
									.getStrVsBlock(new ItemStack(
											Item.pickaxeIron), block, metadata);

							if (toolHardness < 1)
								toolHardness = 1;

							float damage = hardness * toolHardness / 1.5F;

							if (breakBlocks >= damage) {
								worldObj.setBlockToAir(mop.blockX, mop.blockY,
										mop.blockZ);
								worldObj.playAuxSFX(2001, mop.blockX,
										mop.blockY, mop.blockZ, id);
								block.dropBlockAsItemWithChance(worldObj,
										mop.blockX, mop.blockY, mop.blockZ,
										metadata, 0.8F, 0);
								brokeAnotherBlock = true;

								breakBlocks -= damage;
								motionX *= 0.8;
								motionY *= 0.8;
								motionZ *= 0.8;
							}
						}
					}

					if (!brokeAnotherBlock) {
						break;
					}
				}

				fromVec = worldObj.getWorldVec3Pool().getVecFromPool(posX,
						posY, posZ);
				toVec = worldObj.getWorldVec3Pool().getVecFromPool(
						posX + motionX, posY + motionY, posZ + motionZ);

				// fromVec =
				// worldObj.getWorldVec3Pool().getVecFromPool(thePrevX,
				// thePrevY, thePrevZ);
				// toVec = worldObj.getWorldVec3Pool().getVecFromPool(posX,
				// posY, posZ);

				if (mop != null) {
					toVec = worldObj.getWorldVec3Pool().getVecFromPool(
							mop.hitVec.xCoord, mop.hitVec.yCoord,
							mop.hitVec.zCoord);
				}

				List entityList = worldObj
						.getEntitiesWithinAABBExcludingEntity(this,
								boundingBox.addCoord(motionX, motionY, motionZ)
										.expand(1.0D, 1.0D, 1.0D));
				// List entityList =
				// this.worldObj.getEntitiesWithinAABBExcludingEntity(this,
				// this.boundingBox.addCoord(diffVec.xCoord, diffVec.yCoord,
				// diffVec.zCoord).expand(1.0D, 1.0D, 1.0D));
				// AxisAlignedBB hitBounds = createBoundingBox(fromVec,
				// toVec).expand(width + 1, width + 1, width + 1);
				// List entityList =
				// this.worldObj.getEntitiesWithinAABBExcludingEntity(this,
				// hitBounds);

				float expand = 0.3F;

				Entity hitEntity = null;
				double entityDist = -1;
				MovingObjectPosition entityIntercept = null;

				for (int i = 0; i < entityList.size(); ++i) {
					Entity curEntity = (Entity) entityList.get(i);

					if (curEntity.canBeCollidedWith()
							&& (ticksInAir >= 5 || curEntity != shootingEntity)) {
						Entity rideEntity = null;

						if (shootingEntity != null) {
							rideEntity = shootingEntity.ridingEntity;

							if (rideEntity == null)
								rideEntity = shootingEntity.riddenByEntity;
						}

						if (rideEntity == null
								|| (rideEntity.canBeCollidedWith() && (ticksInAir >= 5 || curEntity != rideEntity))) {
							AxisAlignedBB entityBounds = curEntity.boundingBox
									.expand((double) expand, (double) expand,
											(double) expand);
							MovingObjectPosition intercept = entityBounds
									.calculateIntercept(fromVec, toVec);

							if (intercept != null) {
								double curDist = fromVec
										.distanceTo(intercept.hitVec);

								if (entityDist == -1 || curDist < entityDist) {
									hitEntity = curEntity;
									entityDist = curDist;
									entityIntercept = intercept;
								}
							}
						}
					}
				}

				checkItemsToImpale(fromVec, toVec);

				if (hitEntity != null) {
					mop = new MovingObjectPosition(hitEntity);
					mop.hitVec = worldObj.getWorldVec3Pool().getVecFromPool(
							entityIntercept.hitVec.xCoord,
							entityIntercept.hitVec.yCoord,
							entityIntercept.hitVec.zCoord);
					mop.sideHit = entityIntercept.sideHit;
				}

				if (mop != null && mop.entityHit != null
						&& mop.entityHit instanceof EntityPlayer) {
					EntityPlayer entityplayer = (EntityPlayer) mop.entityHit;

					if (entityplayer.capabilities.disableDamage
							|| this.shootingEntity instanceof EntityPlayer
							&& !((EntityPlayer) this.shootingEntity)
									.canAttackPlayer(entityplayer)) {
						mop = null;
					}
				}

				if (mop != null && hitEntity != null) {
					if (getIsCritical()) {
						double velX = entityIntercept.hitVec.xCoord
								- hitEntity.posX;
						double velZ = entityIntercept.hitVec.zCoord
								- hitEntity.posZ;
						double hitDistSqr = velX * velX + velZ * velZ;
						velX /= hitDistSqr;
						velZ /= hitDistSqr;

						for (int i = 0; i < 32; i++) {
							worldObj.spawnParticle(
									"crit",
									(hitEntity.posX + entityIntercept.hitVec.xCoord) / 2,
									entityIntercept.hitVec.yCoord,
									(hitEntity.posZ + entityIntercept.hitVec.zCoord) / 2,
									velX + (rand.nextDouble() - 0.5) * 0.5,
									rand.nextDouble(),
									velZ + (rand.nextDouble() - 0.5) * 0.5);
						}
					}
				}

				if (mop != null
						&& (mop.typeOfHit == EnumMovingObjectType.ENTITY ? canAttackEntity(mop.entityHit)
								: true)) {
					Vec3 hitVec = mop.hitVec;

					if (mop.typeOfHit.equals(EnumMovingObjectType.ENTITY)) {
						
						 * AxisAlignedBB entityBB = mop.entityHit.boundingBox;
						 * Vec3 newHitVec;
						 * 
						 * if (mop.sideHit <= 1) { newHitVec =
						 * fromVec.getIntermediateWithYValue(toVec,
						 * (entityBB.maxY - entityBB.minY) / 2); } else if
						 * (mop.sideHit <= 3) { newHitVec =
						 * fromVec.getIntermediateWithZValue(toVec,
						 * (entityBB.maxZ - entityBB.minZ) / 2); } else {
						 * newHitVec = fromVec.getIntermediateWithXValue(toVec,
						 * (entityBB.maxX - entityBB.minX) / 2); }
						 * 
						 * if (newHitVec != null) { hitVec = newHitVec; }
						 
					} else {
						Vec3 hitPos = getHitPos(mop);
						setPosition(hitPos.xCoord - motionX, hitPos.yCoord
								- motionY, hitPos.zCoord - motionZ);

						
						 * motionX = hitPos.xCoord - posX; motionY =
						 * hitPos.yCoord - posY; motionZ = hitPos.zCoord - posZ;
						 
						
						 * motionDist = MathHelper.sqrt_double(this.motionX *
						 * this.motionX + this.motionY * this.motionY +
						 * this.motionZ * this.motionZ); posX -= motionX /
						 * motionDist * 0.05000000074505806D; posY -= motionY /
						 * motionDist * 0.05000000074505806D; posZ -= motionZ /
						 * motionDist * 0.05000000074505806D;
						 
					}

					lastHitMOP = mop;
					hitVecX = mop.hitVec.xCoord;
					hitVecY = mop.hitVec.yCoord;
					hitVecZ = mop.hitVec.zCoord;
				}

				posX += motionX;
				posY += motionY;
				posZ += motionZ;

				horizMotion = MathHelper.sqrt_double(motionX * motionX
						+ motionZ * motionZ);

				double posDiffX = posX - prevPosX;
				double posDiffY = posY - prevPosY;
				double posDiffZ = posZ - prevPosZ;
				distance = MathHelper.sqrt_double(posDiffX * posDiffX
						+ posDiffY * posDiffY + posDiffZ * posDiffZ);

				if (this.getIsCritical()) {
					for (int i = 0; i < distance; ++i) {
						worldObj.spawnParticle("crit", posX + motionX
								* (double) i / distance, posY + motionY
								* (double) i / distance, posZ + motionZ
								* (double) i / distance, -motionX,
								-motionY + 0.2D, -motionZ);
					}
				}

				float targetPitch = (float) (Math.atan2(motionY, horizMotion) * 180.0D / Math.PI);

				for (targetPitch = (float) (Math.atan2(motionY,
						(double) horizMotion) * 180.0D / Math.PI); targetPitch
						- prevRotationPitch < -180.0F; prevRotationPitch -= 360.0F) {
					;
				}

				while (targetPitch - prevRotationPitch >= 180.0F) {
					prevRotationPitch += 360.0F;
				}

				float pitchDiff = QuiverModMath.clamp(rotationPitch
						- targetPitch, -maxRotSpeed, maxRotSpeed);

				rotationPitch -= pitchDiff;

				// this.rotationPitch = this.prevRotationPitch +
				// (this.rotationPitch - this.prevRotationPitch) * 0.2F;

				if (horizMotion > 0.1F) {
					float targetYaw = (float) (Math.atan2(motionX, motionZ) * 180.0D / Math.PI);

					while (targetYaw - prevRotationYaw < -180.0F) {
						prevRotationYaw -= 360.0F;
					}

					while (targetYaw - prevRotationYaw >= 180.0F) {
						prevRotationYaw += 360.0F;
					}

					float yawDiff = QuiverModMath.clamp(
							rotationYaw - targetYaw, -maxRotSpeed, maxRotSpeed);

					// rotationYaw = prevRotationYaw + (targetYaw -
					// prevRotationYaw) * 0.2F;

					rotationYaw -= yawDiff;
				}

				float slowdown = 0.99F;

				if (this.isInWater()) {
					for (int var25 = 0; var25 < 4; ++var25) {
						horizMotion = 0.25F;
						this.worldObj
								.spawnParticle("bubble", this.posX
										- this.motionX * (double) horizMotion,
										this.posY - this.motionY
												* (double) horizMotion,
										this.posZ - this.motionZ
												* (double) horizMotion,
										this.motionX, this.motionY,
										this.motionZ);
					}

					slowdown = 0.8F;
				}

				this.motionX *= (double) slowdown;
				this.motionY *= (double) slowdown;
				this.motionZ *= (double) slowdown;
				this.motionY += (double) gravity;
				this.setPosition(this.posX, this.posY, this.posZ);
				this.doBlockCollisions();
			}

			int[] pos = getPos();
			int x = pos[0];
			int y = pos[1];
			int z = pos[2];
			int blockID = worldObj.getBlockId(x, y, z);

			if ((explosionTime > 0 && ticks >= explosionTime)
					|| blockID == Block.lavaStill.blockID
					|| blockID == Block.lavaMoving.blockID) {
				explode(posX, posY, posZ);
			}

			if (splitTime > 0 && ticks >= splitTime) {
				split();
			}

			if (breakBlocks > 0 && !inGround) {
				String pistonSound = "tile.piston.";

				if (ticks % 2 == 1)
					pistonSound += "out";
				else
					pistonSound += "in";

				worldObj.playSoundAtEntity(this, pistonSound, 0.3F,
						worldObj.rand.nextFloat() * 0.25F + 0.6F);
			}

			if (isBurning()) {
				if (QuiverMod.proxy.mc != null
						&& QuiverMod.proxy.mc.gameSettings.particleSetting <= 1) {
					int particles = (int) (smokeParticlesPerBlock * distance);

					if (particles <= 0)
						particles = 1;

					double moveX = posX - prevPosX;
					double moveY = posY - prevPosY;
					double moveZ = posZ - prevPosZ;

					double mult;

					for (int i = 0; i < particles; i++) {
						mult = (i / (float) particles); // Multiplier for
														// current position's
														// weight for
														// interpolation
						worldObj.spawnParticle("smoke", posX + (moveX * mult)
								+ ((rand.nextDouble() - 0.5) * 0.25), posY
								+ (moveY * mult)
								+ ((rand.nextDouble() - 0.5) * 0.25), posZ
								+ (moveZ * mult)
								+ ((rand.nextDouble() - 0.5) * 0.25), 0, 0, 0);
					}
				}

				if (!inGround) {
					setFireTicks = -1;
				}

				if (!worldObj.isRemote && inGround && ticksInGround > 25
						&& setFireTicks == -1) {
					boolean replace = false;

					if (worldObj.isAirBlock(x, y, z)) {
						replace = true;
					} else {
						Block block = Block.blocksList[worldObj.getBlockId(x,
								y, z)];

						if (block != null) {
							replace = block.blockMaterial.isReplaceable()
									&& !block.blockMaterial.isLiquid();
						}
					}

					if (replace)
						worldObj.setBlock(x, y, z, Block.fire.blockID);

					setFireTicks = ticksInGround;
				}

				if (setFireTicks >= 0 && ticksInGround - setFireTicks > 50) {
					setDead();
				}
			}
		}

		if (slowMo > 0) {
			prevYawForSlowmo = prevRotationYaw;
			prevPitchForSlowmo = prevRotationPitch;
		}

		
		 * } else { this.prevRotationPitch = rotationPitch; this.prevRotationYaw
		 * = rotationYaw; this.rotationPitch += 1; this.rotationYaw += 1;
		 * 
		 * rotationPitch = rotationPitch % 360; rotationYaw = rotationYaw % 360;
		 * 
		 * ticks++; }
		 
	}

	public boolean isDrillBroken() {
		return breakBlocks < 1;
	}

	private void makePotionSplash() {
		if ((potionEffects != null && !potionEffects.isEmpty())
				|| potionDamage != -1) {
			if (worldObj.isRemote) {
				String particle = "iconcrack_" + Item.potion.itemID;
				RenderGlobal renderGlobal = QuiverMod.proxy.mc.renderGlobal;

				for (int i = 0; i < 8; ++i) {
					renderGlobal.spawnParticle(particle, posX, posY, posZ,
							worldObj.rand.nextGaussian() * 0.15D,
							worldObj.rand.nextDouble() * 0.2D,
							worldObj.rand.nextGaussian() * 0.15D);
				}

				int color = Item.potion.getColorFromDamage(potionDamage);
				float red = ColorHelper.getFloatRed(color);
				float green = ColorHelper.getFloatGreen(color);
				float blue = ColorHelper.getFloatBlue(color);
				String particleName = "spell";

				if (Item.potion.isEffectInstant(potionDamage)) {
					particleName = "instantSpell";
				}

				for (int i = 0; i < 100; ++i) {
					double distance = worldObj.rand.nextDouble() * 4;
					double randomRot = worldObj.rand.nextDouble() * Math.PI * 2;
					double velX = Math.cos(randomRot) * distance;
					double velY = 0.01 + worldObj.rand.nextDouble() * 0.5;
					double velZ = Math.sin(randomRot) * distance;
					EntityFX particleEntity = renderGlobal.doSpawnParticle(
							particleName, posX + velX * 0.1D, posY + 0.3D, posZ
									+ velZ * 0.1D, velX, velY, velZ);

					if (particleEntity != null) {
						float brightness = 0.75F + worldObj.rand.nextFloat() * 0.25F;
						particleEntity.setRBGColorF(red * brightness, green
								* brightness, blue * brightness);
						particleEntity.multiplyVelocity((float) distance);
					}
				}

				worldObj.playSound(posX, posY, posZ, "random.glass", 1,
						worldObj.rand.nextFloat() * 0.1F + 0.9F, false);
			} else {
				AxisAlignedBB axisalignedbb = this.boundingBox.expand(4.0D,
						2.0D, 4.0D);
				List<EntityLivingBase> entityList = this.worldObj
						.getEntitiesWithinAABB(EntityLivingBase.class,
								axisalignedbb);

				if (entityList != null && !entityList.isEmpty()) {
					Iterator<EntityLivingBase> entityIter = entityList
							.iterator();

					while (entityIter.hasNext()) {
						EntityLivingBase checkingEntity = entityIter.next();
						double distSqr = getDistanceSqToEntity(checkingEntity);

						if (distSqr < 16.0D) {
							double power = 1.0D - Math.sqrt(distSqr) / 4.0D;

							if (checkingEntity == lastHitMOP.entityHit) {
								power = 1.0D;
							}

							Iterator effectsIter = potionEffects.iterator();

							while (effectsIter.hasNext()) {
								PotionEffect potioneffect = (PotionEffect) effectsIter
										.next();
								int potionID = potioneffect.getPotionID();

								if (Potion.potionTypes[potionID].isInstant()) {
									Potion.potionTypes[potionID].affectEntity(
											(EntityLivingBase) shootingEntity,
											checkingEntity,
											potioneffect.getAmplifier(), power);
								} else {
									int duration = (int) (power
											* (double) potioneffect
													.getDuration() + 0.5D);

									if (duration > 20) {
										checkingEntity
												.addPotionEffect(new PotionEffect(
														potionID, duration,
														potioneffect
																.getAmplifier()));
									}
								}
							}
						}
					}
				}

				// worldObj.playAuxSFX(2002, (int)Math.round(posX),
				// (int)Math.round(posY), (int)Math.round(posZ), 0);
			}
		}
	}

	public void setDead() {
		if (teleportShooter) {
			if (chunkLoader != null) {
				Iterator chunkIter = chunkLoader.getChunkList().iterator();

				while (chunkIter.hasNext()) {
					ChunkCoordIntPair chunk = (ChunkCoordIntPair) chunkIter
							.next();
					ForgeChunkManager.unforceChunk(chunkLoader, chunk);
				}
			}

			teleportParticles();
		}

		super.setDead();
	}

	public void writeSpawnData(ByteArrayDataOutput data) {
		data.writeInt(ticks);

		data.writeInt(knockbackStrength);

		data.writeBoolean(exploded);
		data.writeFloat(explosionSize);
		data.writeBoolean(explosionBurn);
		data.writeInt(explosionTime);
		data.writeBoolean(explodeInWater);

		data.writeBoolean(teleportShooter);
		data.writeInt(teleportShooterDamage);

		data.writeBoolean(placeTorch);

		data.writeFloat(breakBlocks);

		data.writeInt(potionDamage);
		data.writeBoolean(splashPotion);

		data.writeInt(splitTime);
		data.writeInt(splitArrowCount);

		if (shootingEntity != null)
			data.writeInt(shootingEntity.entityId);
		else
			data.writeInt(-1);

		data.writeFloat(arrowLength);

		data.writeInt(slowMo);

		data.writeDouble(posX);
		data.writeDouble(posY);
		data.writeDouble(posZ);
		data.writeDouble(prevPosX);
		data.writeDouble(prevPosY);
		data.writeDouble(prevPosZ);

		data.writeDouble(motionX);
		data.writeDouble(motionY);
		data.writeDouble(motionZ);

		data.writeFloat(rotationYaw);
		data.writeFloat(rotationPitch);
		data.writeFloat(prevRotationYaw);
		data.writeFloat(prevRotationPitch);
	}

	public void readSpawnData(ByteArrayDataInput data) {
		ticks = data.readInt();

		knockbackStrength = data.readInt();

		exploded = data.readBoolean();
		explosionSize = data.readFloat();
		explosionBurn = data.readBoolean();
		explosionTime = data.readInt();
		explodeInWater = data.readBoolean();

		teleportShooter = data.readBoolean();
		teleportShooterDamage = data.readInt();

		placeTorch = data.readBoolean();

		breakBlocks = data.readFloat();

		potionDamage = data.readInt();
		splashPotion = data.readBoolean();

		splitTime = data.readInt();
		splitArrowCount = data.readInt();

		shootingEntityID = data.readInt();

		arrowLength = data.readFloat();

		slowMo = data.readInt();

		posX = data.readDouble();
		posY = data.readDouble();
		posZ = data.readDouble();

		double prevPosX = data.readDouble();
		double prevPosY = data.readDouble();
		double prevPosZ = data.readDouble();

		double motionX = data.readDouble();
		double motionY = data.readDouble();
		double motionZ = data.readDouble();
		this.motionX = motionX;
		this.motionY = motionY;
		this.motionZ = motionZ;

		setLocationAndAngles(posX, posY, posZ, data.readFloat(),
				data.readFloat());

		this.prevPosX = prevPosX;
		this.prevPosY = prevPosY;
		this.prevPosZ = prevPosZ;

		prevRotationYaw = data.readFloat();
		prevRotationPitch = data.readFloat();

		if (worldObj.isRemote) {
			PacketDispatcher.sendPacketToServer(PacketHelper.makePacket(
					"QMArGetSpecial", entityId));
		}
	}

	*//**
	 * (abstract) Protected helper method to write subclass entity data to NBT.
	 *//*
	public void writeEntityToNBT(NBTTagCompound tagCompound) {
		tagCompound.setShort("xTile", (short) this.xTile);
		tagCompound.setShort("yTile", (short) this.yTile);
		tagCompound.setShort("zTile", (short) this.zTile);
		tagCompound.setByte("inTile", (byte) this.inTile);
		tagCompound.setInteger("hitSide", hitSide);
		tagCompound.setByte("inData", (byte) this.inData);
		tagCompound.setByte("shake", (byte) this.arrowShake);
		tagCompound.setByte("inGround", (byte) (this.inGround ? 1 : 0));
		tagCompound.setByte("pickup", (byte) this.canBePickedUp);
		tagCompound.setDouble("damage", this.damage);

		tagCompound.setInteger("knockbackMode", knockbackMode);

		tagCompound.setCompoundTag("pickupItemStack",
				pickupItemStack.writeToNBT(new NBTTagCompound()));

		tagCompound.setInteger("actualTicks", actualTicks);
		tagCompound.setInteger("ticks", ticks);
		tagCompound.setInteger("ticksInAir", ticksInAir);
		tagCompound.setInteger("ticksInGround", ticksInGround);
		tagCompound.setInteger("setFireTicks", setFireTicks);

		tagCompound.setBoolean("exploded", exploded);
		tagCompound.setFloat("explosionSize", explosionSize);
		tagCompound.setInteger("explosionTime", explosionTime);
		tagCompound.setBoolean("explodeInWater", explodeInWater);

		tagCompound.setBoolean("teleportShooter", teleportShooter);

		tagCompound.setBoolean("placeTorch", placeTorch);

		tagCompound.setFloat("breakBlocks", breakBlocks);

		tagCompound.setInteger("splitTime", splitTime);
		tagCompound.setInteger("splitArrowCount", splitArrowCount);

		if (shootingEntity != null && shootingEntity instanceof EntityPlayer) {
			String entName = ((EntityPlayer) shootingEntity).getEntityName();
			tagCompound.setString("shootingEntityName", entName);
		}

		if (potionEffects != null && !potionEffects.isEmpty()) {
			tagCompound.setInteger("potionDamage", potionDamage);

			NBTTagList effectsList = tagCompound
					.getTagList("CustomPotionEffects");

			for (PotionEffect effect : potionEffects) {
				effectsList.appendTag(effect
						.writeCustomPotionEffectToNBT(new NBTTagCompound()));
			}
		}

		if (pistonMoved) {
			tagCompound.setFloat("prevPistonProgress", prevPistonProgress);
		}

		NBTTagList impaledItemList = new NBTTagList();

		for (ImpaledItem impaled : impaledItems) {
			NBTTagCompound impaledNBT = impaled.getNBT();
			impaledItemList.appendTag(impaledNBT);
		}

		tagCompound.setTag("impaledItems", impaledItemList);
	}

	*//**
	 * (abstract) Protected helper method to read subclass entity data from NBT.
	 *//*
	public void readEntityFromNBT(NBTTagCompound tagCompound) {
		xTile = tagCompound.getShort("xTile");
		yTile = tagCompound.getShort("yTile");
		zTile = tagCompound.getShort("zTile");
		inTile = tagCompound.getByte("inTile") & 255;
		hitSide = tagCompound.getInteger("hitSide");
		inData = tagCompound.getByte("inData") & 255;
		arrowShake = tagCompound.getByte("shake") & 255;
		inGround = tagCompound.getByte("inGround") == 1;

		if (tagCompound.hasKey("damage")) {
			damage = tagCompound.getDouble("damage");
		}

		if (tagCompound.hasKey("pickup")) {
			canBePickedUp = tagCompound.getByte("pickup");
		} else if (tagCompound.hasKey("player")) {
			canBePickedUp = tagCompound.getBoolean("player") ? 1 : 0;
		}

		if (tagCompound.hasKey("pickupItemStack")) {
			setKnockbackStrength(tagCompound.getInteger("knockbackStrength"));

			pickupItemStack = ItemStack.loadItemStackFromNBT(tagCompound
					.getCompoundTag("pickupItemStack"));

			actualTicks = tagCompound.getInteger("actualTicks");
			ticks = tagCompound.getInteger("ticks");
			ticksInAir = tagCompound.getInteger("ticksInAir");
			ticksInGround = tagCompound.getInteger("ticksInGround");
			setFireTicks = tagCompound.getInteger("setFireTicks");

			exploded = tagCompound.getBoolean("exploded");
			explosionSize = tagCompound.getFloat("explosionSize");
			explosionTime = tagCompound.getInteger("explosionTime");
			explodeInWater = tagCompound.getBoolean("explodeInWater");

			teleportShooter = tagCompound.getBoolean("teleportShooter");

			if (teleportShooter && inGround)
				setDead();

			placeTorch = tagCompound.getBoolean("placeTorch");

			breakBlocks = tagCompound.getFloat("breakBlocks");

			splitTime = tagCompound.getInteger("splitTime");
			splitArrowCount = tagCompound.getInteger("splitArrowCount");

			if (tagCompound.hasKey("shootingEntityName")) {
				String entName = tagCompound.getString("shootingEntityName");
				shootingPlayerName = entName;
			}

			if (tagCompound.hasKey("potionDamage")) {
				ItemStack potionArrow = new ItemStack(
						QuiverMod.potionArrow.itemID, 1,
						tagCompound.getInteger("potionDamage"));

				if (potionArrow.stackTagCompound == null)
					potionArrow.stackTagCompound = new NBTTagCompound();

				potionArrow.stackTagCompound.setTag("CustomPotionEffects",
						tagCompound.getTagList("CustomPostionEffects"));

				setPotionEffects(potionArrow.getItemDamage(),
						(ArrayList<PotionEffect>) QuiverMod.potionArrow
								.getEffects(potionArrow));
			}

			if (tagCompound.hasKey("prevPistonProgress")) {
				pistonMoved = true;
				prevPistonProgress = tagCompound.getFloat("prevPistonProgress");
			}

			NBTTagList impaledItemList = tagCompound.getTagList("impaledItems");

			for (int i = 0; i < impaledItemList.tagCount(); i++) {
				NBTTagCompound itemComp = (NBTTagCompound) impaledItemList
						.tagAt(i);
				impaledItems.add(new ImpaledItem(worldObj, itemComp));
			}
		}
	}

	*//**
	 * Called by a player entity when they collide with an entity
	 *//*
	public void onCollideWithPlayer(EntityPlayer player) {
		if (!worldObj.isRemote && inGround && arrowShake <= 0) {
			pickUpImpaled(player);

			boolean pickedUp = (canBePickedUp == 1 || canBePickedUp == 2);

			if (canBePickedUp == 1
					&& !player.inventory
							.addItemStackToInventory(pickupItemStack)) {
				pickedUp = false;
			}

			if (pickedUp) {
				playSound(
						"random.pop",
						0.2F,
						((rand.nextFloat() - rand.nextFloat()) * 0.7F + 1.0F) * 2.0F);
				player.onItemPickup(this, 1);
				setDead();
			}
		}
	}

	*//**
	 * returns if this entity triggers Block.onEntityWalking on the blocks they
	 * walk on. used for spiders and wolves to prevent them from trampling crops
	 *//*
	protected boolean canTriggerWalking() {
		return false;
	}

	@SideOnly(Side.CLIENT)
	public float getShadowSize() {
		return 0.5F;
	}

	public void setDamage(double newDamage) {
		if (newDamage < 0)
			newDamage = 0;

		damage = newDamage;
	}

	public double getDamage() {
		return damage;
	}

	*//**
	 * Sets the amount of knockback the arrow applies when it hits a mob.
	 *//*
	public void setKnockbackStrength(int par1) {
		knockbackStrength = par1;
	}

	*//**
	 * If returns false, the item will not inflict any damage against entities.
	 *//*
	public boolean canAttackWithItem() {
		return false;
	}

	*//**
	 * Whether the arrow has a stream of critical hit particles flying behind
	 * it.
	 *//*
	public void setIsCritical(boolean par1) {
		byte var2 = this.dataWatcher.getWatchableObjectByte(16);

		if (par1) {
			this.dataWatcher.updateObject(16, Byte.valueOf((byte) (var2 | 1)));
		} else {
			this.dataWatcher.updateObject(16, Byte.valueOf((byte) (var2 & -2)));
		}
	}

	*//**
	 * Whether the arrow has a stream of critical hit particles flying behind
	 * it.
	 *//*
	public boolean getIsCritical() {
		byte var1 = this.dataWatcher.getWatchableObjectByte(16);
		return (var1 & 1) != 0;
	}

	@Override
	public boolean canRenderOnFire() {
		return false;
	}

	@Override
	public void writeSpawnData(ByteBuf buffer) {
		// TODO Auto-generated method stub

	}

	@Override
	public void readSpawnData(ByteBuf additionalData) {
		// TODO Auto-generated method stub

	}

}
*/