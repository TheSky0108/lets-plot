/*
 * Copyright (c) 2019. JetBrains s.r.o.
 * Use of this source code is governed by the MIT license that can be found in the LICENSE file.
 */

package jetbrains.datalore.base.math

import jetbrains.datalore.base.geometry.DoubleVector
import jetbrains.datalore.base.geometry.Vector
import kotlin.math.*

fun toRadians(degrees: Double): Double = degrees * PI / 180.0
fun toDegrees(radians: Double): Double = radians * 180.0 / PI

fun round(v: DoubleVector) = round(v.x, v.y)
fun ceil(v: DoubleVector) = ceil(v.x, v.y)

fun round(x: Double, y: Double): Vector {
    return Vector(
        round(x).toInt(),
        round(y).toInt()
    )
}


fun ceil(x: Double, y: Double): Vector {
    return Vector(
        ceil(x).toInt(),
        ceil(y).toInt()
    )
}


fun distance(vector: Vector, doubleVector: DoubleVector): Double {
    val dx = doubleVector.x - vector.x
    val dy = doubleVector.y - vector.y
    return sqrt(dx * dx + dy * dy)
}


fun Int.ipow(e: Int): Double {
    return this.toDouble().pow(e)
}