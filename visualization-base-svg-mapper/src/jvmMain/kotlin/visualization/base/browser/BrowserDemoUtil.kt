package jetbrains.datalore.visualization.base.browser

import kotlinx.html.*
import kotlinx.html.stream.appendHTML
import java.awt.Desktop
import java.io.File
import java.io.FileWriter
import java.io.StringWriter

object BrowserDemoUtil {
    val KOTLIN_LIBS_JS = listOf(
        "kotlin.js",
        "kotlin-logging.js",
        "kotlin-test.js"
    )

    val BASE_MAPPER_LIBS_JS = listOf(
        "base.js",
        "mapper-core.js",
        "visualization-base-svg.js",
        "visualization-base-svg-mapper.js",
        "visualization-base-canvas.js"
    )

    val PLOT_LIBS_JS = listOf(
        "visualization-plot-common.js",
        "visualization-plot-base.js",
        "visualization-plot-builder.js",
        "visualization-plot-config.js"
    )

    private const val ROOT_PROJECT = "datalore-plot"

    fun openInBrowser(demoProject: String, html: () -> String) {
        val outputDir = "$demoProject/build/demoWeb"

        val projectRoot = getProjectRoot()
        println("Project root: $projectRoot")
        val tmpDir = File(projectRoot, outputDir)
        val file = File.createTempFile("index", ".html", tmpDir)
        println(file.canonicalFile)

        FileWriter(file).use {
            it.write(html())
        }

        val desktop = Desktop.getDesktop()
        desktop.browse(file.toURI());
    }

    private fun getProjectRoot(): String {
        // works when launching from IDEA
        val projectRoot = System.getenv()["PWD"] ?: throw IllegalStateException("'PWD' env variable is not defined")

        if (!projectRoot.contains(ROOT_PROJECT)) {
            throw IllegalStateException("'PWD' is not pointing to $ROOT_PROJECT : $projectRoot")
        }
        return projectRoot
    }


    fun mapperDemoHtml(demoProject: String, callFun: String, libs: List<String>, title: String): String {
        val mainScript = "$demoProject.js"
        val writer = StringWriter().appendHTML().html {
            lang = "en"
            head {
                title(title)
            }
            body {
                div { id = "root" }

                for (lib in libs) {
                    script {
                        type = "text/javascript"
                        src = "lib/$lib"
                    }
                }

                script {
                    type = "text/javascript"
                    src = mainScript
                }

                script {
                    type = "text/javascript"
                    unsafe {
                        +"""
                        |
                        |   window['$demoProject'].$callFun();
                    """.trimMargin()

                    }
                }
            }
        }

        return writer.toString()
    }

}