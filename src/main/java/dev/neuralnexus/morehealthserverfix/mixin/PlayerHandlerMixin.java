package dev.neuralnexus.morehealthserverfix.mixin;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.nohero.morehealth.EventHandlers.PlayerHandler;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(value = PlayerHandler.class, remap = false)
public class PlayerHandlerMixin {
    @WrapOperation(method = "onPlayerLogin",
            at = @At(value = "INVOKE", target = "Lcom/nohero/morehealth/mod_moreHealthEnhanced;updateKeyBindings()V"))
    private void onUpdateKeyBindings(Operation<Void> original) {
        return;
    }
}
