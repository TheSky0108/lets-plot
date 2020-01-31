/*
 * Copyright (c) 2019. JetBrains s.r.o.
 * Use of this source code is governed by the MIT license that can be found in the LICENSE file.
 */

package jetbrains.datalore.plot.base.geom

import jetbrains.datalore.base.geometry.DoubleRectangle
import jetbrains.datalore.base.geometry.DoubleVector
import jetbrains.datalore.plot.base.*
import jetbrains.datalore.plot.base.geom.util.BarTooltipHelper
import jetbrains.datalore.plot.base.geom.util.CrossBarHelper
import jetbrains.datalore.plot.base.geom.util.GeomUtil
import jetbrains.datalore.plot.base.geom.util.HintColorUtil
import jetbrains.datalore.plot.base.render.LegendKeyElementFactory
import jetbrains.datalore.plot.base.render.SvgRoot

class CrossBarGeom : GeomBase() {
    var fattenMidline: Double = 2.5

    override val legendKeyElementFactory: LegendKeyElementFactory
        get() = LEGEND_FACTORY

    override fun buildIntern(
        root: SvgRoot,
        aesthetics: Aesthetics,
        pos: PositionAdjustment,
        coord: CoordinateSystem,
        ctx: GeomContext
    ) {
        CrossBarHelper.buildBoxes(root, aesthetics, pos, coord, ctx, rectangleByDataPoint(ctx))
        CrossBarHelper.buildMidlines(root, aesthetics, pos, coord, ctx, fattenMidline)
        BarTooltipHelper.collectRectangleTargets(
            listOf(Aes.YMAX, Aes.MIDDLE, Aes.YMIN),
            aesthetics, pos, coord, ctx,
            rectangleByDataPoint(ctx),
            { HintColorUtil.fromColor(it) }
        )
    }

    companion object {
        const val HANDLES_GROUPS = false

        private val LEGEND_FACTORY = CrossBarHelper.legendFactory(false)

        private fun rectangleByDataPoint(ctx: GeomContext): (DataPointAesthetics) -> DoubleRectangle? {
            return { p ->
                if (p.defined(Aes.X) &&
                    p.defined(Aes.YMIN) &&
                    p.defined(Aes.YMAX) &&
                    p.defined(Aes.WIDTH)
                ) {
                    val x = p.x()!!
                    val ymin = p.ymin()!!
                    val ymax = p.ymax()!!
                    val width = GeomUtil.widthPx(p, ctx, 2.0)

                    val origin = DoubleVector(x - width / 2, ymin)
                    val dimensions = DoubleVector(width, ymax - ymin)
                    DoubleRectangle(origin, dimensions)
                } else {
                    null
                }
            }
        }
    }
}
