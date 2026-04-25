package pl.sirox.nxrtidscards.module

import com.google.inject.AbstractModule
import com.google.inject.multibindings.Multibinder
import org.reflections.Reflections
import pl.sirox.nxrtidscards.interfaces.CustomCommand
import pl.sirox.nxrtidscards.interfaces.CustomEvent

class EventModule : AbstractModule() {

    override fun configure() {

        try {
            val reflections = Reflections("pl.sirox.nxrtidscards.event")

            val classes = reflections.getSubTypesOf(CustomEvent::class.java)

            val binder = Multibinder.newSetBinder(binder(), CustomEvent::class.java)

            classes.forEach {
                binder.addBinding().to(it)
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }

    }

}