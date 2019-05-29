package jetbrains.datalore.visualization.plotDemo.component

import jetbrains.datalore.visualization.plotDemo.SwingDemoFrame
import jetbrains.datalore.visualization.plotDemo.model.component.TextLabelDemo

class TextLabelDemoAwt : TextLabelDemo() {

    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            TextLabelDemoAwt().show()
        }
    }

    private fun show() {
        val demoModels = listOf(createModel())
        val svgRoots = createSvgRoots(demoModels)
        SwingDemoFrame.showSvg(svgRoots, demoComponentSize, "Text label anchor and rotation")
    }
}