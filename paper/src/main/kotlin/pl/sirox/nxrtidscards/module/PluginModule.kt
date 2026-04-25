package pl.sirox.nxrtidscards.module

import com.google.inject.AbstractModule
import pl.sirox.nxrtidscards.bootstrap.Bootstrap

class PluginModule(
    private val plugin: Bootstrap
) : AbstractModule() {

    override fun configure() {
        bind(Bootstrap::class.java).toInstance(plugin)
    }

}