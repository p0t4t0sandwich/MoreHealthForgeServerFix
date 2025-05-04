package dev.neuralnexus.morehealthserverfix.mixin;

import com.nohero.morehealth.GUI.MoreHealthGui;
import com.nohero.morehealth.mod_moreHealthEnhanced;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.relauncher.Side;
import net.minecraft.client.settings.KeyBinding;
import net.minecraftforge.common.config.Property;
import org.lwjgl.input.Keyboard;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value = mod_moreHealthEnhanced.class, remap = false)
public class mod_moreHealthEnhancedMixin {
    @Shadow private static Property guiKeyBinding;

    @Inject(method = "updateKeyBindings", at = @At(value = "HEAD"), cancellable = true)
    private static void onUpdateKeyBindings(CallbackInfo ci) {
        System.out.println("INJECT-------------------------------------------------------");
        if (FMLCommonHandler.instance().getEffectiveSide() == Side.CLIENT) {
            String keyName = guiKeyBinding.getString().toUpperCase();
            int keyIndex = Keyboard.getKeyIndex(keyName);
            if (MoreHealthGui.keyBinding.getKeyCode() != keyIndex) {
                MoreHealthGui.keyBinding.setKeyCode(keyIndex);
                KeyBinding.resetKeyBindingArrayAndHash();
            }
        }
        System.out.println("POST INJECT---------------------------------------------------------------");
        ci.cancel();
    }
}
