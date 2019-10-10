package jetbrains.datalore.visualization.plotDemo.component

import jetbrains.datalore.vis.swing.BatikMapperDemoFrame
import jetbrains.datalore.visualization.plotDemo.model.component.ScatterDemo

class ScatterDemoBatik : ScatterDemo() {

    private fun show() {
        val demoModels = createModels()
        val svgRoots = createSvgRoots(demoModels)
        BatikMapperDemoFrame("Point geom with scale breaks and limits")
            .showSvg(svgRoots, demoComponentSize)
    }

    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            ScatterDemoBatik().show()
        }
    }
}
