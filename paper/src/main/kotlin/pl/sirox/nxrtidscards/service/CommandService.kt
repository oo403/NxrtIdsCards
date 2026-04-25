package pl.sirox.nxrtidscards.service

import com.google.inject.Inject
import dev.rollczi.litecommands.LiteCommands
import dev.rollczi.litecommands.bukkit.LiteBukkitFactory
import org.bukkit.command.CommandSender
import pl.sirox.common.configuration.GeneralConfiguration
import pl.sirox.common.enums.ConfigurationFiles
import pl.sirox.common.logging.LoggerFactory
import pl.sirox.common.logging.logger
import pl.sirox.nxrtidscards.argument.ConfigurationFilesArgument
import pl.sirox.nxrtidscards.bootstrap.Bootstrap
import pl.sirox.nxrtidscards.handler.MissingPermissionHandler
import pl.sirox.nxrtidscards.interfaces.CustomCommand

class CommandService @Inject constructor(
    private val loggerFactory: LoggerFactory,
    private val commands: Set<CustomCommand>,
    private val generalConfiguration: GeneralConfiguration,
    private val missingPermissionHandler: MissingPermissionHandler
) {

    private lateinit var liteCommands: LiteCommands<CommandSender>

    private val logger = loggerFactory.logger<CommandService>()

    fun register(plugin: Bootstrap) {
        try {
            this.liteCommands = LiteBukkitFactory.builder("nxrtidscards", plugin)
                .commands(commands)
                .missingPermission(missingPermissionHandler)
                .argument(ConfigurationFiles::class.java, ConfigurationFilesArgument())
                .build()

            if (generalConfiguration.debug) logger.info("Registering commands...")
            if (generalConfiguration.debug) logger.info("Commands registered ${commands.count()}:")

            if (generalConfiguration.debug) {
                commands.forEach {
                    logger.info(" - ${it.javaClass.simpleName}")
                }
            }
        } catch (e: Exception) {
            logger.error("Failed to register commands!", e)
            e.printStackTrace()
        }
    }

    fun unregister() {
        if (::liteCommands.isInitialized) {
            if (generalConfiguration.debug) logger.info("Unregistering commands...")

            liteCommands.unregister()

            if (generalConfiguration.debug) logger.info("Commands unregistered!")
        }
    }
}