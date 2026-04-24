package pl.sirox.nxrtidscards.util

import com.google.inject.Inject
import dev.rollczi.litecommands.LiteCommands
import dev.rollczi.litecommands.bukkit.LiteBukkitFactory
import org.bukkit.command.CommandSender
import pl.sirox.common.enums.ConfigurationFiles
import pl.sirox.common.logging.LoggerFactory
import pl.sirox.common.logging.logger
import pl.sirox.nxrtidscards.argument.ConfigurationFilesArgument
import pl.sirox.nxrtidscards.bootstrap.Bootstrap
import pl.sirox.nxrtidscards.interfaces.CustomCommand

class CommandUtil @Inject constructor(
    private val loggerFactory: LoggerFactory,
    private val commands: Set<CustomCommand>
) {

    private lateinit var liteCommands: LiteCommands<CommandSender>

    private val logger = loggerFactory.logger<CommandUtil>()

    fun register(plugin: Bootstrap) {
        try {
            this.liteCommands = LiteBukkitFactory.builder("nxrtidscards", plugin)
                .commands(commands)
                .argument(ConfigurationFiles::class.java, ConfigurationFilesArgument())
                .build()

            logger.info("Registering commands...")
            logger.info("Commands registered ${commands.count()}:")

            commands.forEach {
                logger.info(" - ${it.javaClass.simpleName}")
            }
        } catch (e: Exception) {
            logger.error("Failed to register commands!", e)
            e.printStackTrace()
        }
    }

    fun unregister() {
        if (::liteCommands.isInitialized) {
            logger.info("Unregistering commands...")

            liteCommands.unregister()

            logger.info("Commands unregistered!")
        }
    }
}