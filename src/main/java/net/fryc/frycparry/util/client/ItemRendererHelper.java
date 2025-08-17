package net.fryc.frycparry.util.client;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fryc.frycparry.FrycParry;
import net.minecraft.client.render.model.json.Transformation;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Quaternion;
import net.minecraft.util.math.Vec3f;

@Environment(EnvType.CLIENT)
public class ItemRendererHelper {

    public static boolean shouldApplyParryTransform = false;


    public static void applyParryTransform(MatrixStack matrices, Transformation originalTransformation) {
        float x = FrycParry.config.client.useOriginalRotation ? originalTransformation.rotation.getX() : FrycParry.config.client.itemRotationX;
        float y = FrycParry.config.client.useOriginalRotation ? originalTransformation.rotation.getY() : FrycParry.config.client.itemRotationY;
        float z = FrycParry.config.client.useOriginalRotation ? originalTransformation.rotation.getZ() : FrycParry.config.client.itemRotationZ;

        Vec3f vector3f = FrycParry.config.client.useOriginalTranslation ? originalTransformation.translation : new Vec3f(FrycParry.config.client.itemTranslationX, FrycParry.config.client.itemTranslationY, FrycParry.config.client.itemTranslationZ);
        vector3f.scale(0.0625F);
        vector3f.set(MathHelper.clamp(vector3f.getX(), -5.0F, 5.0F), MathHelper.clamp(vector3f.getY(), -5.0F, 5.0F), MathHelper.clamp(vector3f.getZ(), -5.0F, 5.0F));

        Vec3f vector3f2 = FrycParry.config.client.useOriginalScale ? originalTransformation.scale : new Vec3f(FrycParry.config.client.itemScaleX, FrycParry.config.client.itemScaleY, FrycParry.config.client.itemScaleZ);
        vector3f2.set(MathHelper.clamp(vector3f2.getX(), -4.0F, 4.0F), MathHelper.clamp(vector3f2.getY(), -4.0F, 4.0F), MathHelper.clamp(vector3f2.getZ(), -4.0F, 4.0F));

        matrices.translate(vector3f.getX(), vector3f.getY(), vector3f.getZ());
        matrices.multiply(new Quaternion(x * 0.017453292F, y * 0.017453292F, z * 0.017453292F, false));
        matrices.scale(vector3f2.getX(), vector3f2.getY(), vector3f2.getZ());
    }
}