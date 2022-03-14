package com.darkhoundsstudios.supernaturalsweaponry.client.particle;

import net.minecraft.client.particle.*;
import net.minecraft.particles.BasicParticleType;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import java.util.Random;
@OnlyIn(Dist.CLIENT)
public class LevelUp_Particle extends SpriteTexturedParticle {

    protected LevelUp_Particle(World world, double x, double y, double z, double xSpd, double ySpd, double zSpd) {
        super(world, x, y, z, xSpd, ySpd, zSpd);
        float f = new Random().nextFloat();
        particleRed = f;
        particleGreen = f;
        particleBlue = f;
        setSize(0.02f, 0.02f);
        particleScale *= new Random().nextFloat() * 1.1f;
        motionX *= 0.02f;
        motionY *= 0.02f;
        motionZ *= 0.02f;
        maxAge = (int) (20d / (Math.random()));
    }


    public IParticleRenderType getRenderType() {
        return IParticleRenderType.PARTICLE_SHEET_OPAQUE;
    }

    public void tick() {
        this.prevPosX = this.posX;
        this.prevPosY = this.posY;
        this.prevPosZ = this.posZ;
        if (this.maxAge-- <= 0) {
            this.setExpired();
        } else {
            this.move(motionX,motionY,motionZ);
            this.motionX *= (double)0.86F;
            this.motionY *= (double)0.86F;
            this.motionZ *= (double)0.86F;
        }
    }

    @OnlyIn(Dist.CLIENT)
    public static class Factory implements IParticleFactory<BasicParticleType> {
        private final IAnimatedSprite spriteSet;

        public Factory(IAnimatedSprite spriteSet) {
            this.spriteSet = spriteSet;
        }

        public Particle makeParticle(BasicParticleType typeIn, World worldIn, double x, double y, double z, double xSpeed, double ySpeed, double zSpeed) {
            LevelUp_Particle levelUpParticle = new LevelUp_Particle(worldIn, x, y + 0.5D, z, xSpeed,ySpeed,zSpeed);
            levelUpParticle.setColor(1.0F, 1.0F, 1.0F);
            levelUpParticle.selectSpriteRandomly(this.spriteSet);
            return levelUpParticle;
        }
    }
}
