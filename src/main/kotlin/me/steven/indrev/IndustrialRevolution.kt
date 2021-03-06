package me.steven.indrev

import me.steven.indrev.blockentities.MachineBlockEntity
import me.steven.indrev.gui.controllers.*
import me.steven.indrev.recipes.*
import me.steven.indrev.registry.MachineRegistry
import me.steven.indrev.registry.ModRegistry
import me.steven.indrev.utils.identifier
import me.steven.indrev.utils.registerScreenHandler
import net.fabricmc.fabric.api.client.itemgroup.FabricItemGroupBuilder
import net.minecraft.item.ItemGroup
import net.minecraft.item.ItemStack
import net.minecraft.util.registry.Registry
import team.reborn.energy.Energy
import team.reborn.energy.minecraft.EnergyModInitializer

object IndustrialRevolution : EnergyModInitializer() {
    override fun onInitialize() {
        super.onInitialize()
        Energy.registerHolder(MachineBlockEntity::class.java) { obj -> obj as MachineBlockEntity }
        ModRegistry.registerAll()
        MachineRegistry.COAL_GENERATOR_REGISTRY

        Registry.register(Registry.RECIPE_SERIALIZER, PulverizerRecipe.IDENTIFIER, PulverizerRecipe.SERIALIZER)
        Registry.register(Registry.RECIPE_TYPE, PulverizerRecipe.IDENTIFIER, PulverizerRecipe.TYPE)
        Registry.register(Registry.RECIPE_SERIALIZER, CompressorRecipe.IDENTIFIER, CompressorRecipe.SERIALIZER)
        Registry.register(Registry.RECIPE_TYPE, CompressorRecipe.IDENTIFIER, CompressorRecipe.TYPE)
        Registry.register(Registry.RECIPE_SERIALIZER, InfuserRecipe.IDENTIFIER, InfuserRecipe.SERIALIZER)
        Registry.register(Registry.RECIPE_TYPE, InfuserRecipe.IDENTIFIER, InfuserRecipe.TYPE)
        Registry.register(Registry.RECIPE_SERIALIZER, RecyclerRecipe.IDENTIFIER, RecyclerRecipe.SERIALIZER)
        Registry.register(Registry.RECIPE_TYPE, RecyclerRecipe.IDENTIFIER, RecyclerRecipe.TYPE)
        Registry.register(Registry.RECIPE_SERIALIZER, PatchouliBookRecipe.IDENTIFIER, PatchouliBookRecipe.SERIALIZER)
        Registry.register(Registry.RECIPE_TYPE, PatchouliBookRecipe.IDENTIFIER, PatchouliBookRecipe.TYPE)
        Registry.register(Registry.RECIPE_SERIALIZER, RechargeableRecipe.IDENTIFIER, RechargeableRecipe.SERIALIZER)
    }

    const val MOD_ID = "indrev"

    val MOD_GROUP: ItemGroup =
        FabricItemGroupBuilder.build(identifier("indrev_group")) { ItemStack(ModRegistry.NIKOLITE.dust.get()) }

    val COAL_GENERATOR_HANDLER = CoalGeneratorController.SCREEN_ID.registerScreenHandler(::CoalGeneratorController)
    val SOLAR_GENERATOR_HANDLER = SolarGeneratorController.SCREEN_ID.registerScreenHandler(::SolarGeneratorController)
    val BIOMASS_GENERATOR_HANDLER = BiomassGeneratorController.SCREEN_ID.registerScreenHandler(::BiomassGeneratorController)
    val HEAT_GENERATOR_HANDLER = HeatGeneratorController.SCREEN_ID.registerScreenHandler(::HeatGeneratorController)
    val BATTERY_HANDLER = BatteryController.SCREEN_ID.registerScreenHandler(::BatteryController)
    val ELECTRIC_FURNACE_HANDLER = ElectricFurnaceController.SCREEN_ID.registerScreenHandler(::ElectricFurnaceController)
    val PULVERIZER_HANDLER = PulverizerController.SCREEN_ID.registerScreenHandler(::PulverizerController)
    val COMPRESSOR_HANDLER = CompressorController.SCREEN_ID.registerScreenHandler(::CompressorController)
    val INFUSER_HANDLER = InfuserController.SCREEN_ID.registerScreenHandler(::InfuserController)
    val RECYCLER_HANDLER = RecyclerController.SCREEN_ID.registerScreenHandler(::RecyclerController)
    val CHOPPER_HANDLER = ChopperController.SCREEN_ID.registerScreenHandler(::ChopperController)
    val RANCHER_HANDLER = RancherController.SCREEN_ID.registerScreenHandler(::RancherController)
    val MINER_HANDLER = MinerController.SCREEN_ID.registerScreenHandler(::MinerController)
}