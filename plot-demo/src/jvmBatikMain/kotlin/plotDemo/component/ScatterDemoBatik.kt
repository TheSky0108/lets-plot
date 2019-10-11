package jetbrains.datalore.plotDemo.component

import jetbrains.datalore.plotDemo.model.component.ScatterDemo
import jetbrains.datalore.vis.swing.BatikMapperDemoFrame

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