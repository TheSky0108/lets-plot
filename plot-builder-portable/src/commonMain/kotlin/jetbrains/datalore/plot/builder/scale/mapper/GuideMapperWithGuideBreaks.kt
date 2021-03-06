/*
 * Copyright (c) 2019. JetBrains s.r.o.
 * Use of this source code is governed by the MIT license that can be found in the LICENSE file.
 */

package jetbrains.datalore.plot.builder.scale.mapper

import jetbrains.datalore.plot.builder.scale.GuideBreak
import jetbrains.datalore.plot.builder.scale.GuideMapper
import jetbrains.datalore.plot.builder.scale.WithGuideBreaks

internal class GuideMapperWithGuideBreaks<TargetT>(
        private val myMapper: (Double?) -> TargetT?,
        breaks: List<GuideBreak<*>>) :
        GuideMapper<TargetT>,
    WithGuideBreaks {

    private val myBreaks: List<GuideBreak<*>> = ArrayList(breaks)

    override val guideBreaks: List<GuideBreak<*>>
        get() = myBreaks

    override val isContinuous = false

    override fun apply(value: Double?): TargetT? {
        return myMapper(value)
    }
}
