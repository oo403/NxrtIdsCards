package pl.sirox.nxrtidscards.bootstrap

import com.google.inject.Guice
import com.google.inject.Injector
import org.bukkit.plugin.java.JavaPlugin
import org.slf4j.Logger
import pl.sirox.common.logging.LoggerFactory
import pl.sirox.common.logging.logger
import pl.sirox.common.module.ConfigurationModule

class Bootstrap : JavaPlugin() {

    private lateinit var injector: Injector
    private lateinit var loggerFactory: LoggerFactory
    private lateinit var logger: Logger

    override fun onLoad() {
        try {
            injector = Guice.createInjector(
                ConfigurationModule(this.dataFolder)
            )

            loggerFactory = injector.getInstance(LoggerFactory::class.java)
            logger = loggerFactory.logger<Bootstrap>()

        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    override fun onEnable() {
        logger.info("NxtrIdCards is starting...")
        logger.info("NxtrIdCards is enabled!")
    }

    override fun onDisable() {
        logger.info("NxtrIdCards is disabled!")
    }

}