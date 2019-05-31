package jetbrains.datalore.visualization.plotDemo

import javafx.application.Platform
import javafx.embed.swing.JFXPanel
import javafx.scene.Parent
import javafx.scene.Scene
import jetbrains.datalore.base.registration.CompositeRegistration
import jetbrains.datalore.base.registration.Disposable
import jetbrains.datalore.base.registration.Registration
import javax.swing.SwingUtilities

abstract class SwingJfxPanel : JFXPanel() {
    private var myRegFx = CompositeRegistration()

    init {
        SwingUtilities.invokeLater {
            runOnFxThread { revalidateScene() }
        }
    }

    protected fun revalidateScene() {
        assertFxThread()

        myRegFx.dispose()
        myRegFx = CompositeRegistration()

        val scene = Scene(createSceneParent())
        setScene(scene)
    }

    abstract fun createSceneParent(): Parent

    protected fun regFx(disposable: Disposable) {
        assertFxThread()
        myRegFx.add(Registration.from(disposable))
    }

    protected fun regFx(reg: Registration) {
        assertFxThread()
        myRegFx.add(reg)
    }


    companion object {
        internal fun runOnFxThread(runnable: () -> Unit) {
            if (Platform.isFxApplicationThread()) {
                runnable.invoke()
            } else {
                Platform.runLater(runnable)
            }
        }

        internal fun assertFxThread() {
            if (!Platform.isFxApplicationThread()) {
                throw IllegalStateException("Not JFX Application Thread ")
            }
        }
    }
}