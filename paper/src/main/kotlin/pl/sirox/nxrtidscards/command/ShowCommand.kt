package pl.sirox.nxrtidscards.command

import com.google.inject.Inject
import dev.rollczi.litecommands.annotations.command.Command
import dev.rollczi.litecommands.annotations.context.Context
import dev.rollczi.litecommands.annotations.execute.Execute
import org.bukkit.entity.Player
import pl.sirox.nxrtidscards.interfaces.CustomCommand
import pl.sirox.nxrtidscards.util.InventoryUtil

@Command(name = "showid")
class ShowCommand @Inject constructor(
    private val inventoryUtil: InventoryUtil
) : CustomCommand {

    @Execute
    fun executeShow(@Context sender: Player) {
        inventoryUtil.showIdInventoryNearby(sender)
    }

}
