package me.steven.indrev.components

import io.github.cottonmc.cotton.gui.PropertyDelegateHolder
import me.steven.indrev.blockentities.MachineBlockEntity
import me.steven.indrev.items.IRCoolerItem
import net.minecraft.item.ItemStack
import net.minecraft.nbt.CompoundTag
import net.minecraft.screen.PropertyDelegate

class TemperatureController(
    private val machineProvider: () -> MachineBlockEntity,
    private val heatingSpeed: Double,
    private val stableTemperature: () -> Double,
    val optimalRange: IntRange,
    val explosionLimit: Double
) : PropertyDelegateHolder {

    constructor(
        machineProvider: () -> MachineBlockEntity,
        heatingSpeed: Double,
        optimalRange: IntRange,
        explosionLimit: Double
    ) : this(machineProvider, heatingSpeed, { explosionLimit }, optimalRange, explosionLimit)

    var temperature: Double by Property(2, 12.0 + (getTemperatureModifier() * 10))
    var cooling = 0
    var coolingModifier = 0.01
    var explosionPower = 2f
    var inputOverflow = false

    fun fromTag(tag: CompoundTag?) {
        temperature = tag?.getDouble("Temperature") ?: 0.0
        cooling = tag?.getInt("Cooling") ?: 0
    }

    fun toTag(tag: CompoundTag): CompoundTag {
        tag.putDouble("Temperature", temperature)
        tag.putInt("Cooling", cooling)
        return tag
    }

    fun isFullEfficiency() = (cooling <= 0 || getCoolerStack() != null) && temperature.toInt() in optimalRange

    fun tick(isHeatingUp: Boolean) {
        val machine = machineProvider()
        val coolerStack = getCoolerStack()
        val coolerItem = coolerStack?.item
        val tempModifier = getTemperatureModifier() / 10
        val overflowModifier = if (inputOverflow) 20 else 0
        if (!isHeatingUp && !inputOverflow && temperature > 30.5)
            temperature -= coolingModifier + tempModifier - overflowModifier
        else if (cooling <= 0 && (temperature > optimalRange.last - 10 || temperature > stableTemperature())) {
            cooling = 70
            coolingModifier = 0.01
            if (coolerStack != null && coolerItem is IRCoolerItem) {
                coolingModifier = coolerItem.coolingModifier
                coolerStack.damage++
            }
        } else if (cooling > 0 && temperature > 25) {
            cooling--
            temperature -= coolingModifier + tempModifier - overflowModifier
        } else
            temperature += heatingSpeed + tempModifier + overflowModifier
        if (temperature > explosionLimit - 5) {
            machine.explode = true
        }
    }

    private fun getCoolerStack(): ItemStack? = machineProvider().inventoryController?.getInventory()?.getStack(1)

    private fun getTemperatureModifier(): Float {
        val machine = machineProvider()
        return machine.world?.getBiome(machine.pos)?.getTemperature(machine.pos) ?: 0f
    }

    override fun getPropertyDelegate(): PropertyDelegate = machineProvider().propertyDelegate
}