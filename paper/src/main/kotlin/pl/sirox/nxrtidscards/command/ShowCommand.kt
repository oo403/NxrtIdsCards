package pl.sirox.nxrtidscards.command

import com.google.inject.Inject
import dev.rollczi.litecommands.annotations.command.Command
import dev.rollczi.litecommands.annotations.context.Context
import dev.rollczi.litecommands.annotations.execute.Execute
import org.bukkit.Bukkit
import org.bukkit.entity.Player
import pl.sirox.common.configuration.GeneralConfiguration
import pl.sirox.common.configuration.MessagesConfiguration
import pl.sirox.common.service.MultificationService
import pl.sirox.nxrtidscards.bootstrap.Bootstrap
import pl.sirox.nxrtidscards.interfaces.CustomCommand
import pl.sirox.nxrtidscards.service.InventoryService
import pl.sirox.nxrtidscards.service.PlayerService

@Command(name = "showid")
class ShowCommand @Inject constructor(
    private val inventoryService: InventoryService,
    private val playerService: PlayerService,
    private val messagesConfiguration: MessagesConfiguration,
    private val generalConfiguration: GeneralConfiguration,
    private val multificationService: MultificationService,
    private val plugin: Bootstrap
) : CustomCommand {

    @Execute
    fun executeShow(@Context sender: Player) {
        val senderLoc = sender.location
        val playersInDistance = senderLoc.world.getNearbyPlayers(senderLoc, 10.0, 10.0, 10.0)

        playersInDistance.forEach { player ->
            playerService.setShowRequest(player.uniqueId, sender.uniqueId)

            multificationService.create()
                .viewer(player)
                .notice(messagesConfiguration.showIdRequest)
                .placeholder("{PLAYER}", sender.name)
                .send()

            Bukkit.getScheduler().runTaskLaterAsynchronously(plugin, Runnable {
                playerService.removeShowRequest(player.uniqueId)
            }, 20L * generalConfiguration.showIdRequestTime.toLong())
        }
    }

    @Execute(name = "accept")
    fun executeAccept(@Context sender: Player) {
        val showRequest = playerService.getShowRequest(sender.uniqueId)

        if (showRequest == null) {
            return
        } else {
            val showRequestPlayer: Player = Bukkit.getPlayer(showRequest) ?: return

            inventoryService.showIdInventory(sender, showRequestPlayer)
        }
    }

}
