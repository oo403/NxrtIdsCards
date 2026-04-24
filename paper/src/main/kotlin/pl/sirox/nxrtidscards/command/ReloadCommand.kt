package pl.sirox.nxrtidscards.command

import com.google.inject.Inject
import dev.rollczi.litecommands.annotations.argument.Arg
import dev.rollczi.litecommands.annotations.command.Command
import dev.rollczi.litecommands.annotations.context.Context
import dev.rollczi.litecommands.annotations.execute.Execute
import org.bukkit.entity.Player
import pl.sirox.common.enums.ConfigurationFiles
import pl.sirox.common.util.ConfigurationUtil
import pl.sirox.nxrtidscards.interfaces.CustomCommand

@Command(name = "nxrtidscards")
class ReloadCommand @Inject constructor(
    private val configurationUtil: ConfigurationUtil
) : CustomCommand {

    @Execute(name = "reload")
    fun executeReload(@Context player: Player, @Arg configuration: ConfigurationFiles) {
        if (configuration == ConfigurationFiles.ALL) {
            configurationUtil.reloadConfigurations()
        } else {
            configurationUtil.reloadConfiguration(configuration)
        }
    }

}
