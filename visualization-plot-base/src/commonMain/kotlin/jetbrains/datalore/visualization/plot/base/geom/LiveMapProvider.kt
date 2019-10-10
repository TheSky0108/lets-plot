package jetbrains.datalore.visualization.plot.base.geom

import jetbrains.datalore.base.geometry.DoubleVector
import jetbrains.datalore.visualization.base.canvasFigure.CanvasFigure
import jetbrains.datalore.visualization.plot.base.Aesthetics
import jetbrains.datalore.visualization.plot.base.interact.MappedDataAccess

interface LiveMapProvider {
    fun createLiveMap(
        aesthetics: Aesthetics,
        dataAccess: MappedDataAccess,
        dimension: DoubleVector,
        layers: List<LiveMapLayerData>
    ): CanvasFigure
}
