package net.rk.addon.menu;

import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.inventory.MenuType;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.common.extensions.IMenuTypeExtension;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.rk.addon.Thingamajigsgoodies;

public class TGMenu{
    public static final DeferredRegister<MenuType<?>> MENU_TYPES = DeferredRegister.create(
            BuiltInRegistries.MENU, Thingamajigsgoodies.MODID);

    public static final DeferredHolder<MenuType<?>,MenuType<CombinerMenu>> COMBINER_MENU =
            MENU_TYPES.register("combiner_menu", () -> IMenuTypeExtension.create((id, inv, data) ->
                    new CombinerMenu(id,inv)));

    public static void register(IEventBus eventBus){MENU_TYPES.register(eventBus);}
}
