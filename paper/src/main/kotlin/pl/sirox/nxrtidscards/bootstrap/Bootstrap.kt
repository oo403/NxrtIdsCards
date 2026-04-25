package pl.sirox.nxrtidscards.bootstrap

import com.google.inject.Guice
import com.google.inject.Injector
import org.bukkit.plugin.java.JavaPlugin
import org.slf4j.Logger
import pl.sirox.common.logging.LoggerFactory
import pl.sirox.common.logging.logger
import pl.sirox.common.module.ConfigurationModule
import pl.sirox.nxrtidscards.module.CommandModule
import pl.sirox.nxrtidscards.util.CommandUtil

class Bootstrap : JavaPlugin() {

    private lateinit var injector: Injector
    private lateinit var loggerFactory: LoggerFactory
    private lateinit var logger: Logger
    private lateinit var commands: CommandUtil

    override fun onLoad() {
        try {
            injector = Guice.createInjector(
                ConfigurationModule(this.dataFolder),
                CommandModule()
            )

            loggerFactory = injector.getInstance(LoggerFactory::class.java)
            logger = loggerFactory.logger<Bootstrap>("NxtrIdCards")

            commands = injector.getInstance(CommandUtil::class.java)

        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    override fun onEnable() {
        logger.info("NxtrIdCards is starting...")

        if (this.server.pluginManager.getPlugin("PlaceholderAPI") == null) {
            logger.error("PlaceholderAPI is not installed, may cause errors!")
        }

        if (::commands.isInitialized) {
            commands.register(this)
        }

        logger.info("NxtrIdCards is enabled!")
    }

    override fun onDisable() {
        if (::commands.isInitialized) {
            commands.unregister()
        }

        logger.info("NxtrIdCards is disabled!")
    }

}