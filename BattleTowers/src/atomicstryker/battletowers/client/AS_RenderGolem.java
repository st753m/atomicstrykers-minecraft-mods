package atomicstryker.battletowers.client;

import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.renderer.entity.RenderBiped;
import net.minecraft.client.resources.ResourceLocation;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;

import org.lwjgl.opengl.GL11;

import atomicstryker.battletowers.common.AS_EntityGolem;

public class AS_RenderGolem extends RenderBiped
{
    private static ResourceLocation texSleep = new ResourceLocation("battletowers", "textures/model/golemdormant.png");
    private static ResourceLocation texAwake = new ResourceLocation("battletowers", "textures/model/golem.png");

    public AS_RenderGolem()
    {
        super(new ModelBiped(), 1.0F);
        setRenderPassModel(new ModelBiped());
    }

    protected void rescaleGolem(AS_EntityGolem entitygolem, float f)
    {
        GL11.glScalef(2.0F, 2.0F, 2.0F);
    }
    
    @Override
    protected void preRenderCallback(EntityLivingBase entityliving, float f)
    {
        rescaleGolem((AS_EntityGolem)entityliving, f);
    }
    
    @Override
    protected ResourceLocation func_110856_a(EntityLiving par1EntityLiving)
    {
        boolean awake = ((AS_EntityGolem)par1EntityLiving).getIsDormant();
        return awake ? texAwake : texSleep;
    }
}
