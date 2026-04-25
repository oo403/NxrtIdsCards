package pl.sirox.nxrtidscards.command

import com.google.inject.Inject
import dev.rollczi.litecommands.annotations.argument.Arg
import dev.rollczi.litecommands.annotations.command.Command
import dev.rollczi.litecommands.annotations.command.RootCommand
import dev.rollczi.litecommands.annotations.context.Context
import dev.rollczi.litecommands.annotations.execute.Execute
import dev.rollczi.litecommands.annotations.permission.Permission
import net.kyori.adventure.text.Component
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player
import pl.sirox.nxrtidscards.interfaces.CustomCommand
import pl.sirox.nxrtidscards.service.DialogService
import pl.sirox.nxrtidscards.service.PlayerService

@Command(name = "showiddialog")
class DialogCommand @Inject constructor(
    private val dialogService: DialogService,
    private val playerService: PlayerService
) : CustomCommand {

    @Execute
    @Permission("nxrtidscards.admin.showiddialog")
    fun executeDialog(@Context sender: CommandSender, @Arg("player") player: Player) {
        dialogService.showDialog(player)
    }

}
