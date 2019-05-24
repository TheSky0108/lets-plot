package jetbrains.datalore.visualization.plot.builder.assemble

import jetbrains.datalore.base.gcommon.collect.ClosedRange
import jetbrains.datalore.base.geometry.DoubleVector
import jetbrains.datalore.base.values.Color
import jetbrains.datalore.visualization.plot.FeatureSwitch
import jetbrains.datalore.visualization.plot.base.Scale
import jetbrains.datalore.visualization.plot.base.scale.ScaleUtil
import jetbrains.datalore.visualization.plot.base.scale.breaks.ScaleBreaksUtil
import jetbrains.datalore.visualization.plot.builder.guide.*
import jetbrains.datalore.visualization.plot.builder.layout.LegendBoxInfo
import jetbrains.datalore.visualization.plot.builder.scale.GuideBreak
import jetbrains.datalore.visualization.plot.builder.theme.LegendTheme

class ColorBarAssembler(private val legendTitle: String,
                        private val domain: ClosedRange<Double>,
                        private val scale: Scale<Color>,
                        private val theme: LegendTheme) {

    private var myOptions: ColorBarOptions? = null

    fun createColorBar(): LegendBoxInfo {
        var scale = scale
        if (!scale.hasBreaks()) {
            scale = ScaleBreaksUtil.withBreaks(scale, domain, 5)
        }

        val guideBreaks = ArrayList<GuideBreak<Double>>()
        val breaks = ScaleUtil.breaksTransformed(scale)
        val label = ScaleUtil.labels(scale).iterator()
        for (v in breaks) {
            guideBreaks.add(GuideBreak(v, label.next()))
        }

        if (guideBreaks.isEmpty()) {
            return LegendBoxInfo.EMPTY
        }

        val spec = createColorBarSpec(legendTitle, domain, guideBreaks, scale, theme, myOptions)

        return object : LegendBoxInfo(spec.size) {
            override fun createLegendBox(): LegendBox {
                val c = ColorBarComponent(spec)
                c.debug = DEBUG_DRAWING
                return c
            }
        }
    }

    internal fun setOptions(options: ColorBarOptions?) {
        myOptions = options
    }

    companion object {
        private const val DEBUG_DRAWING = FeatureSwitch.LEGEND_DEBUG_DRAWING

        fun createColorBarSpec(title: String,
                               domain: ClosedRange<Double>,
                               breaks: List<GuideBreak<Double>>,
                               scale: Scale<Color>,
                               theme: LegendTheme,
                               options: ColorBarOptions? = null): ColorBarComponentSpec {

            val legendDirection = LegendAssemblerUtil.legendDirection(theme)

            var barSize = ColorBarComponentSpec.barAbsoluteSize(legendDirection, theme)
            if (options != null) {
                if (options.hasWidth()) {
                    barSize = DoubleVector(options.width!!, barSize.y)
                }
                if (options.hasHeight()) {
                    barSize = DoubleVector(barSize.x, options.height!!)
                }
            }

            val layout = when {
                legendDirection === LegendDirection.HORIZONTAL ->
                    ColorBarComponentLayout.horizontal(title, domain, breaks, barSize)
                else ->
                    ColorBarComponentLayout.vertical(title, domain, breaks, barSize)
            }

            val spec = ColorBarComponentSpec(title, domain, breaks, scale, theme, layout)
            if (options != null) {
                if (options.hasBinCount()) {
                    spec.binCount = options.binCount
                }
            }

            return spec
        }
    }
}
