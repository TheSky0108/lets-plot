package jetbrains.datalore.visualization.plotDemo.plotAssembler

import jetbrains.datalore.visualization.base.swing.BatikMapperDemoFrame
import jetbrains.datalore.visualization.plotDemo.model.plotAssembler.RasterImagePlotDemo
import kotlinx.serialization.ImplicitReflectionSerializer

@ImplicitReflectionSerializer
class RasterImagePlotDemoBatik : RasterImagePlotDemo() {

    private fun show() {
        val plots = createPlots()
        val svgRoots = createSvgRootsFromPlots(plots)
        BatikMapperDemoFrame.showSvg(svgRoots, demoComponentSize, "Raster image plot")
    }

    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            RasterImagePlotDemoBatik().show()
        }
    }
}
