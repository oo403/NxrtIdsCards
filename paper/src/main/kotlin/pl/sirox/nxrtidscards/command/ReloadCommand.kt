package pl.sirox.nxrtidscards.command

import com.google.inject.Inject
import dev.rollczi.litecommands.annotations.command.Command
import dev.rollczi.litecommands.annotations.context.Context
import dev.rollczi.litecommands.annotations.execute.Execute
import dev.rollczi.litecommands.annotations.optional.OptionalArg
import org.bukkit.command.CommandSender
import pl.sirox.common.configuration.MessagesConfiguration
import pl.sirox.common.enums.ConfigurationFiles
import pl.sirox.common.service.ConfigurationService
import pl.sirox.common.service.MultificationService
import pl.sirox.nxrtidscards.interfaces.CustomCommand

@Command(name = "nxrtidscards")
class ReloadCommand @Inject constructor(
    private val configurationService: ConfigurationService,
    private val messagesConfiguration: MessagesConfiguration,
    private val multificationService: MultificationService
) : CustomCommand {

    @Execute(name = "reload")
    fun executeReload(@Context sender: CommandSender, @OptionalArg configuration: ConfigurationFiles?) {
        if (configuration == null) {
            configurationService.reloadConfigurations()

            multificationService.create()
                .viewer(sender)
                .notice(messagesConfiguration.reloadAllMessage)
                .send()

        } else {
            configurationService.reloadConfiguration(configuration)

            multificationService.create()
                .viewer(sender)
                .notice(messagesConfiguration.reloadMessage)
                .placeholder("{CONFIG}", configuration.name)
                .send()
        }
    }

}
