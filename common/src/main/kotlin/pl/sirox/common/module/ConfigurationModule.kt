package pl.sirox.common.module

import com.eternalcode.multification.okaeri.MultificationSerdesPack
import com.google.inject.AbstractModule
import eu.okaeri.configs.ConfigManager
import eu.okaeri.configs.yaml.bukkit.YamlBukkitConfigurer
import pl.sirox.common.configuration.DialogConfiguration
import pl.sirox.common.configuration.GeneralConfiguration
import pl.sirox.common.configuration.InventoryConfiguration
import pl.sirox.common.configuration.MessagesConfiguration
import pl.sirox.common.service.MultificationService
import java.io.File

class ConfigurationModule(
    private val dataFolder: File
) : AbstractModule() {

    val generalConfigurationFile = File(dataFolder, "general.yml")
    val messagesConfigurationFile = File(dataFolder, "messages.yml")
    val inventoryConfigurationFile = File(dataFolder, "inventory.yml")
    val dialogConfigurationFile = File(dataFolder, "dialog.yml")

    private val multification = MultificationService(MessagesConfiguration())

    override fun configure() {
        val generalConfiguration = ConfigManager.create(GeneralConfiguration::class.java, { init ->
            init.configure { conf ->
                conf.configurer(YamlBukkitConfigurer())
                conf.bindFile(generalConfigurationFile)
                conf.removeOrphans(true)
            }
            init.saveDefaults()
            init.load(true)
        })

        val messageConfiguration = ConfigManager.create(MessagesConfiguration::class.java, { init ->
            init.configure { conf ->
                conf.configurer(YamlBukkitConfigurer(), MultificationSerdesPack(multification))
                conf.bindFile(messagesConfigurationFile)
                conf.removeOrphans(true)
            }
            init.saveDefaults()
            init.load(true)
        })

        val inventoryConfiguration = ConfigManager.create(InventoryConfiguration::class.java, { init ->
            init.configure { conf ->
                conf.configurer(YamlBukkitConfigurer())
                conf.bindFile(inventoryConfigurationFile)
                conf.removeOrphans(true)
            }
            init.saveDefaults()
            init.load(true)
        })

        val dialogConfiguration = ConfigManager.create(DialogConfiguration::class.java, { init ->
            init.configure { conf ->
                conf.configurer(YamlBukkitConfigurer())
                conf.bindFile(dialogConfigurationFile)
                conf.removeOrphans(true)
            }
            init.saveDefaults()
            init.load(true)
        })

        bind(GeneralConfiguration::class.java).toInstance(generalConfiguration)
        bind(MessagesConfiguration::class.java).toInstance(messageConfiguration)
        bind(InventoryConfiguration::class.java).toInstance(inventoryConfiguration)
        bind(DialogConfiguration::class.java).toInstance(dialogConfiguration)
    }

}