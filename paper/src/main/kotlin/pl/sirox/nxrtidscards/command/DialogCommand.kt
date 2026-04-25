package pl.sirox.nxrtidscards.command

import com.google.inject.Inject
import dev.rollczi.litecommands.annotations.argument.Arg
import dev.rollczi.litecommands.annotations.command.Command
import dev.rollczi.litecommands.annotations.command.RootCommand
import dev.rollczi.litecommands.annotations.context.Context
import dev.rollczi.litecommands.annotations.execute.Execute
import dev.rollczi.litecommands.annotations.permission.Permission
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player
import pl.sirox.nxrtidscards.interfaces.CustomCommand
import pl.sirox.nxrtidscards.service.DialogService

@Command(name = "showiddialog")
class DialogCommand @Inject constructor(
    private val dialogService: DialogService
) : CustomCommand {

    @Execute
    @Permission("nxrtidscards.admin.showiddialog")
    fun executeDialog(@Context sender: CommandSender, @Arg("player") player: Player) {
        dialogService.showDialog(player)
    }

}
