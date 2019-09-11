package jetbrains.datalore.visualization.plot.builder.scale.provider

import jetbrains.datalore.base.values.Color
import jetbrains.datalore.visualization.plot.base.DataFrame
import jetbrains.datalore.visualization.plot.base.Transform
import jetbrains.datalore.visualization.plot.base.data.DataFrameUtil
import jetbrains.datalore.visualization.plot.base.scale.MapperUtil
import jetbrains.datalore.visualization.plot.builder.scale.GuideMapper
import jetbrains.datalore.visualization.plot.builder.scale.mapper.ColorMapper
import jetbrains.datalore.visualization.plot.builder.scale.mapper.GuideMappers
import jetbrains.datalore.visualization.plot.common.data.SeriesUtil

class ColorGradientMapperProvider(low: Color?, high: Color?, naValue: Color) : MapperProviderBase<Color>(naValue) {

    private val low: Color = low ?: ColorMapper.DEF_GRADIENT_LOW
    private val high: Color = high ?: ColorMapper.DEF_GRADIENT_HIGH

    override fun createDiscreteMapper(data: DataFrame, variable: DataFrame.Variable): GuideMapper<Color> {
        val domainValues = DataFrameUtil.distinctValues(data, variable)
        val domainValuesAsNumbers = MapperUtil.mapDiscreteDomainValuesToNumbers(domainValues)
        val mapperDomain = SeriesUtil.range(domainValuesAsNumbers.values)!!
        val gradient = ColorMapper.gradient(mapperDomain, low, high, naValue)
        return GuideMappers.adapt(gradient)
    }

    override fun createContinuousMapper(
        data: DataFrame,
        variable: DataFrame.Variable,
        lowerLimit: Double?,
        upperLimit: Double?,
        trans: Transform?
    ): GuideMapper<Color> {
        val domain = MapperUtil.rangeWithLimitsAfterTransform(data, variable, lowerLimit, upperLimit, trans)
        val gradient = ColorMapper.gradient(domain, low, high, naValue)
        return GuideMappers.adaptContinuous(gradient)
    }

    companion object {
        val DEFAULT = ColorGradientMapperProvider(null, null, ColorMapper.NA_VALUE)
    }
}