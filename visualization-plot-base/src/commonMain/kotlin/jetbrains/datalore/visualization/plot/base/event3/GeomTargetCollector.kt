package jetbrains.datalore.visualization.plot.base.event3

import jetbrains.datalore.base.geometry.DoubleRectangle
import jetbrains.datalore.base.geometry.DoubleVector
import jetbrains.datalore.base.values.Color
import jetbrains.datalore.visualization.plot.base.Aes

interface GeomTargetCollector {

    fun addPoint(index: Int, point: DoubleVector, radius: Double, tooltipParams: TooltipParams)

    fun addRectangle(index: Int, rectangle: DoubleRectangle, tooltipParams: TooltipParams)

    fun addPath(points: List<DoubleVector>, localToGlobalIndex: (Int) -> Int, tooltipParams: TooltipParams, closePath: Boolean)

    class TooltipParams {

        private var myTipLayoutHints = emptyMap<Aes<*>, TipLayoutHint>()
        private var myColor = Color.GRAY

        fun getTipLayoutHints(): Map<Aes<*>, TipLayoutHint> {
            return myTipLayoutHints
        }

        fun setTipLayoutHints(tipLayoutHints: Map<Aes<*>, TipLayoutHint>): TooltipParams {
            myTipLayoutHints = tipLayoutHints
            return this
        }

        fun getColor(): Color {
            return myColor
        }

        fun setColor(color: Color): TooltipParams {
            myColor = color
            return this
        }

        companion object {
            fun params(): TooltipParams {
                return TooltipParams()
            }
        }
    }

}