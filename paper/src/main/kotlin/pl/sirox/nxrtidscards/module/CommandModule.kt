package pl.sirox.nxrtidscards.module

import com.google.inject.AbstractModule
import com.google.inject.multibindings.Multibinder
import org.reflections.Reflections
import pl.sirox.nxrtidscards.interfaces.CustomCommand

class CommandModule : AbstractModule() {

    override fun configure() {

        try {
            val reflections = Reflections("pl.sirox.nxrtidscards.command")

            val classes = reflections.getSubTypesOf(CustomCommand::class.java)

            val binder = Multibinder.newSetBinder(binder(), CustomCommand::class.java)

            classes.forEach {
                binder.addBinding().to(it)
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }

    }

}