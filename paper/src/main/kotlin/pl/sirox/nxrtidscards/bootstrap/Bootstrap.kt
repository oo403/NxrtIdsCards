package pl.sirox.nxrtidscards.bootstrap

import com.google.inject.Guice
import org.bukkit.plugin.java.JavaPlugin
import pl.sirox.common.module.ConfigurationModule

class Bootstrap : JavaPlugin() {

    override fun onLoad() {
        try {
            Guice.createInjector(
                ConfigurationModule(this.dataFolder)
            )

        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    override fun onEnable() {

    }

    override fun onDisable() {
    }

}