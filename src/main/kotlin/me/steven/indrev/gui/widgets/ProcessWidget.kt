package me.steven.indrev.gui.widgets

import io.github.cottonmc.cotton.gui.client.ScreenDrawing
import io.github.cottonmc.cotton.gui.widget.WWidget
import me.steven.indrev.utils.identifier
import net.minecraft.client.util.math.MatrixStack
import net.minecraft.screen.PropertyDelegate
import kotlin.math.round

class ProcessWidget(private val delegate: PropertyDelegate) : WWidget() {
    init {
        this.setSize(24, 17)
    }

    override fun paint(matrices: MatrixStack?, x: Int, y: Int, mouseX: Int, mouseY: Int) {
        ScreenDrawing.texturedRect(x, y, width, height, PROCESS_EMPTY, -1)
        val burnTime = delegate[3]
        val maxBurnTime = delegate[4]
        if (burnTime > 0) {
            val v = 1f - (((burnTime.toFloat() * 23 / maxBurnTime) + 1) / 24)
            val w = round(v * width).toInt()
            ScreenDrawing.texturedRect(x, y, w, height, PROCESS_FULL, 0f, 0f, v, 1f, -1)
        }
    }

    companion object {
        private val PROCESS_EMPTY =
            identifier("textures/gui/widget_processing_empty.png")
        private val PROCESS_FULL =
            identifier("textures/gui/widget_processing_full.png")
    }
}