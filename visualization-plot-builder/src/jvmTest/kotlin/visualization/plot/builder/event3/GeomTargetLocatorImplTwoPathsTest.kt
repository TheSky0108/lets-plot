package jetbrains.datalore.visualization.plot.builder.event3

import jetbrains.datalore.visualization.plot.base.event3.GeomTargetLocator.LookupSpace
import jetbrains.datalore.visualization.plot.base.event3.GeomTargetLocator.LookupStrategy
import jetbrains.datalore.visualization.plot.builder.event3.TestUtil.assertEncodedObjects
import jetbrains.datalore.visualization.plot.builder.event3.TestUtil.createLocator
import jetbrains.datalore.visualization.plot.builder.event3.TestUtil.horizontalPathTarget
import jetbrains.datalore.visualization.plot.builder.event3.TestUtil.point
import kotlin.test.Test

class GeomTargetLocatorImplTwoPathsTest {

    @Test
    fun nearestX() {
        val xList = doubleArrayOf(100.0, 101.0, 103.0, 104.0)

        val locator = createLocator(LookupStrategy.NEAREST, LookupSpace.X,
                horizontalPathTarget(FIRST_PATH_KEY, 100.0, xList),
                horizontalPathTarget(SECOND_PATH_KEY, 200.0, xList)
        )

        val pointInXRange = point(102.0, 110.0)
        assertEncodedObjects(locator, pointInXRange, FIRST_PATH_KEY, SECOND_PATH_KEY)

        val pointOutOfXRange = point(90.0, 150.0)
        assertEncodedObjects(locator, pointOutOfXRange, FIRST_PATH_KEY, SECOND_PATH_KEY)
    }

    @Test
    fun nearestXClosePoints() {
        val xList = doubleArrayOf(100.0, 101.1, 101.2, 101.3, 101.4, 101.5, 103.0, 104.0)

        val locator = createLocator(LookupStrategy.NEAREST, LookupSpace.X,
                horizontalPathTarget(FIRST_PATH_KEY, 100.0, xList),
                horizontalPathTarget(SECOND_PATH_KEY, 200.0, xList)
        )

        val pointInXRange = point(101.0, 110.0)
        assertEncodedObjects(locator, pointInXRange, FIRST_PATH_KEY, SECOND_PATH_KEY)

        val pointOutOfXRange = point(90.0, 150.0)
        assertEncodedObjects(locator, pointOutOfXRange, FIRST_PATH_KEY, SECOND_PATH_KEY)
    }

    companion object {
        private val FIRST_PATH_KEY = 1
        private val SECOND_PATH_KEY = 2
    }
}