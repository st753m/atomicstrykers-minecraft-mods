package atomicstryker.infernalmobs.client;

import java.util.Map;

import atomicstryker.infernalmobs.common.InfernalMobsCore;
import atomicstryker.infernalmobs.common.MobModifier;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.culling.Frustum;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.math.Vec3d;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class RendererBossGlow
{
    private static long lastRender = 0L;
    
    @SubscribeEvent
    public void onRenderWorldLast(RenderWorldLastEvent event)
    {
        if (System.currentTimeMillis() > lastRender+10L)
        {
            lastRender = System.currentTimeMillis();
            
            renderBossGlow(event.getPartialTicks());
        }
    }
    
    private void renderBossGlow(float renderTick)
    {
        Minecraft mc = Minecraft.getMinecraft();
        Entity viewEnt = mc.getRenderViewEntity();
        Vec3d curPos = viewEnt.getPositionVector();
        
        Frustum f = new Frustum();
        double var7 = viewEnt.lastTickPosX + (viewEnt.posX - viewEnt.lastTickPosX) * (double)renderTick;
        double var9 = viewEnt.lastTickPosY + (viewEnt.posY - viewEnt.lastTickPosY) * (double)renderTick;
        double var11 = viewEnt.lastTickPosZ + (viewEnt.posZ - viewEnt.lastTickPosZ) * (double)renderTick;
        f.setPosition(var7, var9, var11);
        
        Map<EntityLivingBase, MobModifier> mobsmap = InfernalMobsCore.proxy.getRareMobs();
        mobsmap.keySet().stream().filter(ent -> ent.isInRangeToRenderDist(curPos.squareDistanceTo(ent.getPositionVector()))
                && (ent.ignoreFrustumCheck || f.isBoundingBoxInFrustum(ent.getEntityBoundingBox()))
                && ent.isEntityAlive()).forEach(ent -> mc.renderGlobal.spawnParticle(EnumParticleTypes.SPELL_MOB.getParticleID(),
                        EnumParticleTypes.SPELL_MOB.getShouldIgnoreRange(), ent.posX + (ent.world.rand.nextDouble() - 0.5D) * (double) ent.width,
                        ent.posY + ent.world.rand.nextDouble() * (double) ent.height - 0.25D,
                        ent.posZ + (ent.world.rand.nextDouble() - 0.5D) * (double) ent.width,
                        (ent.world.rand.nextDouble() - 0.5D) * 2.0D,
                        -ent.world.rand.nextDouble(),
                        (ent.world.rand.nextDouble() - 0.5D) * 2.0D));
    }
}
