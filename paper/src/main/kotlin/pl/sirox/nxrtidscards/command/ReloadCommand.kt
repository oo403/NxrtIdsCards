package pl.sirox.nxrtidscards.command

import com.google.inject.Inject
import dev.rollczi.litecommands.annotations.argument.Arg
import dev.rollczi.litecommands.annotations.command.Command
import dev.rollczi.litecommands.annotations.context.Context
import dev.rollczi.litecommands.annotations.execute.Execute
import dev.rollczi.litecommands.annotations.optional.OptionalArg
import io.papermc.paper.command.brigadier.argument.ArgumentTypes.player
import net.kyori.adventure.text.minimessage.MiniMessage
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player
import pl.sirox.common.configuration.MessagesConfiguration
import pl.sirox.common.enums.ConfigurationFiles
import pl.sirox.common.util.ConfigurationUtil
import pl.sirox.common.util.MultificationUtil
import pl.sirox.nxrtidscards.interfaces.CustomCommand

@Command(name = "nxrtidscards")
class ReloadCommand @Inject constructor(
    private val configurationUtil: ConfigurationUtil,
    private val messagesConfiguration: MessagesConfiguration,
    private val multificationUtil: MultificationUtil
) : CustomCommand {

    @Execute(name = "reload")
    fun executeReload(@Context sender: CommandSender, @OptionalArg configuration: ConfigurationFiles?) {
        if (configuration == null) {
            configurationUtil.reloadConfigurations()

            multificationUtil.create()
                .viewer(sender)
                .notice(messagesConfiguration.reloadAllMessage)
                .send()

        } else {
            configurationUtil.reloadConfiguration(configuration)

            multificationUtil.create()
                .viewer(sender)
                .notice(messagesConfiguration.reloadMessage)
                .placeholder("{CONFIG}", configuration.name)
                .send()
        }
    }

}
