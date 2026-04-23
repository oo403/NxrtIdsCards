package pl.sirox.common.module

import com.google.inject.AbstractModule
import eu.okaeri.configs.ConfigManager
import eu.okaeri.configs.yaml.bukkit.YamlBukkitConfigurer
import pl.sirox.common.configuration.GeneralConfiguration
import java.io.File

class ConfigurationModule(
    private val dataFolder: File
) : AbstractModule() {

    val generalConfigurationFile = File(dataFolder, "general.yml")

    override fun configure() {
        val generalConfiguration = ConfigManager.create(GeneralConfiguration::class.java, { init ->
            init.configure { conf ->
                conf.configurer(YamlBukkitConfigurer())
                conf.bindFile(generalConfigurationFile)
                conf.removeOrphans(true)
            }
            init.saveDefaults()
            init.load()
        })

        bind(GeneralConfiguration::class.java).toInstance(generalConfiguration)
    }

}