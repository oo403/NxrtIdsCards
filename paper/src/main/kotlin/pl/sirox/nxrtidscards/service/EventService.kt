package pl.sirox.nxrtidscards.service

import com.google.inject.Inject
import pl.sirox.common.configuration.GeneralConfiguration
import pl.sirox.common.logging.LoggerFactory
import pl.sirox.common.logging.logger
import pl.sirox.nxrtidscards.bootstrap.Bootstrap
import pl.sirox.nxrtidscards.interfaces.CustomEvent
import kotlin.jvm.javaClass

class EventService @Inject constructor(
    private val events: Set<CustomEvent>,
    private val generalConfiguration: GeneralConfiguration,
    private val loggerFactory: LoggerFactory
) {

    private val logger = loggerFactory.logger<EventService>()

    fun register(plugin: Bootstrap) {
        try {
            if (generalConfiguration.debug) logger.info("Registering events...")
            if (generalConfiguration.debug) logger.info("Events registered ${events.count()}:")

            events.forEach { event ->
                plugin.server.pluginManager.registerEvents(event, plugin)

                if (generalConfiguration.debug) logger.info(" - ${event.javaClass.simpleName}")
            }
        } catch (e: Exception) {
            logger.error("Failed to register events!", e)
            e.printStackTrace()
        }
    }

}