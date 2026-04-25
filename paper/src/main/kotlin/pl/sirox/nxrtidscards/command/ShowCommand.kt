package pl.sirox.nxrtidscards.command

import com.google.inject.Inject
import dev.rollczi.litecommands.annotations.command.Command
import dev.rollczi.litecommands.annotations.context.Context
import dev.rollczi.litecommands.annotations.execute.Execute
import org.bukkit.entity.Player
import pl.sirox.nxrtidscards.interfaces.CustomCommand
import pl.sirox.nxrtidscards.service.InventoryService

@Command(name = "showid")
class ShowCommand @Inject constructor(
    private val inventoryService: InventoryService
) : CustomCommand {

    @Execute
    fun executeShow(@Context sender: Player) {
        inventoryService.showIdInventoryNearby(sender)
    }

}
