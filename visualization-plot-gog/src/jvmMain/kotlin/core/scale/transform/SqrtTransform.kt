package jetbrains.datalore.visualization.plot.gog.core.scale.transform

import java.util.function.Function

class SqrtTransform : FunTransform(F, F_INVERSE) {
    companion object {
        private val F = Function<Double, Double> { v ->
            //            if (v != null)
//                Math.sqrt(v!!)
//            else
//                null
            Math.sqrt(v)
        }
        private val F_INVERSE = Function<Double, Double> { v ->
            //            if (v != null)
//                v!! * v!!
//            else
//                null
            v * v
        }
    }
}