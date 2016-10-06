package com.Jackiecrazi.BetterArcheryReborn;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.BlockFire;
import net.minecraft.block.material.Material;
import net.minecraft.enchantment.EnchantmentProtection;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.item.EntityTNTPrimed;
import net.minecraft.init.Blocks;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EntityDamageSource;
import net.minecraft.util.EntityDamageSourceIndirect;
import net.minecraft.util.MathHelper;
import net.minecraft.util.Vec3;
import net.minecraft.world.ChunkPosition;
import net.minecraft.world.Explosion;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;;

public class ModExplosion extends Explosion
{
	public World worldObj = null;
	
    public int field_77289_h = 16;
    public Random explosionRNG = new Random();
    
    public Entity indirectExploder = null;
    public int flameMultiplier = 10;
    public int maxFlameChance = 5;

    public ModExplosion(World par1World, Entity par2Entity, double par3, double par5, double par7, float par9)
    {
    	super(par1World, par2Entity, par3, par5, par7, par9);
    	
        worldObj = par1World;
    }
	
    @Override
	/**
	 * Gets all the blocks to destroy and damages entities.
	*/
	public void doExplosionA()
	{
        float var1 = this.explosionSize;
        HashSet var2 = new HashSet();
        int endX;
        int endY;
        int endZ;
        double x;
        double y;
        double z;

        for (endX = 0; endX < this.field_77289_h; ++endX)
        {
            for (endY = 0; endY < this.field_77289_h; ++endY)
            {
                for (endZ = 0; endZ < this.field_77289_h; ++endZ)
                {
                    if (endX == 0 || endX == this.field_77289_h - 1 || endY == 0 || endY == this.field_77289_h - 1 || endZ == 0 || endZ == this.field_77289_h - 1)
                    {
                        double vecX = (double)((float)endX / ((float)this.field_77289_h - 1.0F) * 2.0F - 1.0F);
                        double vecY = (double)((float)endY / ((float)this.field_77289_h - 1.0F) * 2.0F - 1.0F);
                        double vecZ = (double)((float)endZ / ((float)this.field_77289_h - 1.0F) * 2.0F - 1.0F);
                        double amplitude = Math.sqrt(vecX * vecX + vecY * vecY + vecZ * vecZ);
                        vecX /= amplitude;
                        vecY /= amplitude;
                        vecZ /= amplitude;
                        float rayDist = this.explosionSize * (0.7F + this.worldObj.rand.nextFloat() * 0.6F);
                        x = this.explosionX;
                        y = this.explosionY;
                        z = this.explosionZ;

                        for (float checkDist = 0.3F; rayDist > 0.0F; rayDist -= checkDist * 0.75F)
                        {
                            int blockX = MathHelper.floor_double(x);
                            int blockY = MathHelper.floor_double(y);
                            int blockZ = MathHelper.floor_double(z);
                            Block blockID = this.worldObj.getBlock(blockX, blockY, blockZ);

                            if (blockID !=null)
                            {
                                float resistance = this.exploder != null ? this.exploder.func_145772_a(this, this.worldObj, blockX, blockY, blockZ, blockID) : blockID.getExplosionResistance(this.exploder, worldObj, blockX, blockY, blockZ, explosionX, explosionY, explosionZ);
                                rayDist -= (resistance + 0.3F) * checkDist;
                            }

                            if (rayDist > 0.0F && (this.exploder == null || this.exploder.func_145774_a(this, this.worldObj, blockX, blockY, blockZ, blockID, rayDist)))
                            {
                                var2.add(new ChunkPosition(blockX, blockY, blockZ));
                            }

                            x += vecX * (double)checkDist;
                            y += vecY * (double)checkDist;
                            z += vecZ * (double)checkDist;
                        }
                    }
                }
            }
        }

        int explStartX;
        int explEndX;
        int explStartY;
        this.affectedBlockPositions.addAll(var2);
        this.explosionSize *= 2.0F;
        explStartX = MathHelper.floor_double(this.explosionX - (double)this.explosionSize - 1.0D);
        explEndX = MathHelper.floor_double(this.explosionX + (double)this.explosionSize + 1.0D);
        explStartY = MathHelper.floor_double(this.explosionY - (double)this.explosionSize - 1.0D);
        int explEndY = MathHelper.floor_double(this.explosionY + (double)this.explosionSize + 1.0D);
        int explStartZ = MathHelper.floor_double(this.explosionZ - (double)this.explosionSize - 1.0D);
        int explEndZ = MathHelper.floor_double(this.explosionZ + (double)this.explosionSize + 1.0D);
        List entities = worldObj.getEntitiesWithinAABBExcludingEntity(this.exploder, this.exploder.boundingBox.expand(5.0D, 5.0D, 4.0D));
        Vec3 vector = this.exploder.getLookVec();
        
        double diffX;
        double diffY;
        double diffZ;
        
        for (int i = 0; i < entities.size(); ++i)
        {
            Entity entity = (Entity)entities.get(i);
            
            double damageSub = entity.getDistance(this.explosionX, this.explosionY, this.explosionZ) / (double)this.explosionSize;

            if (damageSub <= 1.0D)
            {
                diffX = entity.posX - this.explosionX;
                diffY = entity.posY + (double)entity.getEyeHeight() - this.explosionY;
                diffZ = entity.posZ - this.explosionZ;
                double distance = (double)MathHelper.sqrt_double(diffX * diffX + diffY * diffY + diffZ * diffZ);

                if (distance != 0.0D)
                {
                    diffX /= distance;
                    diffY /= distance;
                    diffZ /= distance;
                    double clearPathsMult = (double)this.worldObj.getBlockDensity(vector, entity.boundingBox);
                    double damageMult = (1.0D - damageSub) * clearPathsMult;
                    
                    if (indirectExploder == null)	// .setExplosionSource(this) v
                    	entity.attackEntityFrom(new EntityDamageSource("explosion", exploder), (int)((damageMult * damageMult + damageMult) / 2.0D * 8.0D * (double)this.explosionSize + 1.0D));
                    else
                    	entity.attackEntityFrom(new EntityDamageSourceIndirect("explosion", exploder, indirectExploder), (int)((damageMult * damageMult + damageMult) / 2.0D * 8.0D * (double)this.explosionSize + 1.0D));
                    
                    double protectionEnchantment = EnchantmentProtection.func_92092_a(entity, damageMult);
                    entity.motionX += diffX * protectionEnchantment;
                    entity.motionY += diffY * protectionEnchantment;
                    entity.motionZ += diffZ * protectionEnchantment;
                }
            }
        }
	}
	
	private boolean canSetFire(int x, int y, int z)
	{
		return Blocks.fire.canPlaceBlockAt(worldObj, x, y, z);
	}

    private int getFlammability(int x, int y, int z)
    {
    	int flammability = 0;
    	
    	int curX;
    	int curY;
    	int curZ;
    	Block blockID;
    	int thisFlammability;
    	
    	for (int i = 0; i <= 6; i++)
    	{
	    	curX = x;
	    	curY = y;
	    	curZ = z;
	    	
    		switch (i)
    		{
    		case 1:
    			curY++;
    		case 2:
    			curY--;
    		case 3:
    			curX++;
    		case 4:
    			curX--;
    		case 5:
    			curZ++;
    		case 6:
    			curZ--;
    		}
	    	
	    	blockID = worldObj.getBlock(curX, curY, curZ);
	    	
	    	if (blockID !=null)
	    	{
		    	thisFlammability = blockID.getFlammability(worldObj, curX, curY, curZ, ForgeDirection.UP);
		    	
		    	if (thisFlammability > flammability)
		    		flammability = thisFlammability;
	    	}
    	}
    	
		return flammability;
	}
    
    @Override
    /**
     * Makes sounds, destroys blocks and makes drops, spawns particles
     */
    public void doExplosionB(boolean particles)
    {
        this.worldObj.playSoundEffect(this.explosionX, this.explosionY, this.explosionZ, "random.explode", 4.0F, (1.0F + (this.worldObj.rand.nextFloat() - this.worldObj.rand.nextFloat()) * 0.2F) * 0.7F);

        if (this.explosionSize >= 2.0F && this.isSmoking)
        {
            this.worldObj.spawnParticle("hugeexplosion", this.explosionX, this.explosionY, this.explosionZ, 1.0D, 0.0D, 0.0D);
        }
        else
        {
            this.worldObj.spawnParticle("largeexplode", this.explosionX, this.explosionY, this.explosionZ, 1.0D, 0.0D, 0.0D);
        }

        Iterator iterator;
        ChunkPosition pos;
        int blockX;
        int blockY;
        int blockZ;
        Block blockID = worldObj.getBlock((int)explosionX, (int)explosionY, (int)explosionZ);
        boolean explosionInWater = (blockID == Blocks.water);
        boolean explosionInLava = (blockID == Blocks.lava);

        if (this.isSmoking)
        {
            iterator = this.affectedBlockPositions.iterator();

            while (iterator.hasNext())
            {
                pos = (ChunkPosition)iterator.next();
                blockX = pos.chunkPosX;
                blockY = pos.chunkPosY;
                blockZ = pos.chunkPosZ;
                blockID = this.worldObj.getBlock(blockX, blockY, blockZ);

                if (particles)
                {
                    double particleX = (double)((float)blockX + this.worldObj.rand.nextFloat());
                    double particleY = (double)((float)blockY + this.worldObj.rand.nextFloat());
                    double particleZ = (double)((float)blockZ + this.worldObj.rand.nextFloat());
                    double particleVelX = particleX - this.explosionX;
                    double particleVelY = particleY - this.explosionY;
                    double particleVelZ = particleZ - this.explosionZ;
                    double dist = (double)MathHelper.sqrt_double(particleVelX * particleVelX + particleVelY * particleVelY + particleVelZ * particleVelZ);
                    particleVelX /= dist;
                    particleVelY /= dist;
                    particleVelZ /= dist;
                    double velMult = 0.5D / (dist / (double)this.explosionSize + 0.1D);
                    velMult *= (double)(this.worldObj.rand.nextFloat() * this.worldObj.rand.nextFloat() + 0.3F);
                    particleVelX *= velMult;
                    particleVelY *= velMult;
                    particleVelZ *= velMult;
                    worldObj.spawnParticle("explode", (particleX + this.explosionX) / 2.0D, (particleY + this.explosionY) / 2.0D, (particleZ + this.explosionZ) / 2.0D, particleVelX, particleVelY, particleVelZ);
                    worldObj.spawnParticle("smoke", particleX, particleY, particleZ, particleVelX, particleVelY, particleVelZ);
                }

                if (blockID !=null)
                {
                    if (blockID.canDropFromExplosion(this))
                    {
                        blockID.dropBlockAsItemWithChance(this.worldObj, blockX, blockY, blockZ, this.worldObj.getBlockMetadata(blockX, blockY, blockZ), 1.0F / this.explosionSize, 0);
                    }

                    blockID.onBlockExploded(worldObj, blockX, blockY, blockZ, this);
                }
            }

            if (explosionInLava)
            {
	            for (int j = 0; j < 16; j++)
	            {
	            	worldObj.spawnParticle("lava", explosionX + (worldObj.rand.nextFloat() - 0.5), explosionY + (worldObj.rand.nextFloat() - 0.5), explosionZ + (worldObj.rand.nextFloat() - 0.5), 0, 0, 0);
	            }
            }
            
			if (BAR.proxy.mc != null && BAR.proxy.mc.gameSettings.particleSetting == 0)
			{
	        	for (int i = 0; i < 256; i++)
	        	{
	        		float piFloat = (float)Math.PI;
	                float radius = worldObj.rand.nextFloat() * explosionSize * 2;
	                float longitude = worldObj.rand.nextFloat() * (float)piFloat * 2;
	                float latitude = (worldObj.rand.nextFloat() * piFloat * 2) - piFloat;
	                float x = radius * MathHelper.cos(longitude) * MathHelper.cos(latitude);
	                float y = radius * MathHelper.sin(longitude) * MathHelper.cos(latitude);
	                float z = radius * MathHelper.sin(latitude);
	                x += explosionX;
	                y += explosionY;
	                z += explosionZ;
	    	    	
	                double particleVelX = x - this.explosionX;
	                double particleVelY = y - this.explosionY;
	                double particleVelZ = z - this.explosionZ;
	                double dist = (double)MathHelper.sqrt_double(particleVelX * particleVelX + particleVelY * particleVelY + particleVelZ * particleVelZ);
	                particleVelX /= dist;
	                particleVelY /= dist;
	                particleVelZ /= dist;
	                double velMult = 0.5D / (dist / (double)this.explosionSize + 0.1D);
	                velMult *= (double)(this.worldObj.rand.nextFloat() * this.worldObj.rand.nextFloat() + 0.3F);
	                particleVelX *= velMult;
	                particleVelY *= velMult;
	                particleVelZ *= velMult;
	
	                if (explosionInWater)
	                {
	                    for (int j = 0; j < 8; j++)
	                    {
	                    	worldObj.spawnParticle("splash", x + (worldObj.rand.nextFloat() - 0.5) * 0.5, y + (worldObj.rand.nextFloat() - 0.5) * 0.5, z + (worldObj.rand.nextFloat() - 0.5) * 0.5, 0, 0, 0);
	                    }
	                }
	        		
	        		worldObj.spawnParticle("bubble", x, y, z, particleVelX, 0, particleVelY);
	        	}
			}
        }

        if (this.isFlaming)
        {
            iterator = this.affectedBlockPositions.iterator();

            while (iterator.hasNext())
            {
                pos = (ChunkPosition)iterator.next();
                blockX = pos.chunkPosX;
                blockY = pos.chunkPosY;
                blockZ = pos.chunkPosZ;

                int diffX = (int)explosionX - blockX;
                int diffY = (int)explosionY - blockY;
                int diffZ = (int)explosionZ - blockZ;
                float dist = MathHelper.sqrt_float((diffX * diffX) + (diffY * diffY) + (diffZ * diffZ));
                float distMultiplier = MathHelper.sqrt_float(dist) / MathHelper.sqrt_float(explosionSize);
                
                blockID = worldObj.getBlock(blockX, blockY, blockZ);
                boolean canSetFire = canSetFire(blockX, blockY, blockZ);
                int flammability = (int)(getFlammability(blockX, blockY, blockZ) * flameMultiplier * distMultiplier);
                int chance = -1;
                
                if (flammability > 0)
                	chance = (300 / flammability);
                
                if (chance > maxFlameChance || chance <= -1)
                	chance = maxFlameChance;

                if (canSetFire && (chance == 0 || explosionRNG.nextInt(chance) == 0))
                {
                    this.worldObj.setBlock(blockX, blockY, blockZ, Blocks.fire);
                }
            }
        }
    }
}
