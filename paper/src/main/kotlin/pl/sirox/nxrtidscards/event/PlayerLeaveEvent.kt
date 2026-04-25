package pl.sirox.nxrtidscards.event

import com.google.inject.Inject
import org.bukkit.Bukkit
import org.bukkit.event.EventHandler
import org.bukkit.event.player.PlayerQuitEvent
import pl.sirox.nxrtidscards.bootstrap.Bootstrap
import pl.sirox.nxrtidscards.interfaces.CustomEvent
import pl.sirox.nxrtidscards.service.DatabaseService
import pl.sirox.nxrtidscards.service.PlayerService

class PlayerLeaveEvent @Inject constructor(
    private val databaseService: DatabaseService,
    private val playerService: PlayerService,
    private val plugin: Bootstrap
) : CustomEvent {

    @EventHandler
    fun onPlayerLeave(e: PlayerQuitEvent) {
        val player = e.player

        if (playerService.getName(player.uniqueId) == null) return

        Bukkit.getScheduler().runTaskAsynchronously(plugin, Runnable {
            databaseService.addPlayer(
                player.uniqueId.toString(),
                playerService.getName(player.uniqueId)!!,
                playerService.getSurname(player.uniqueId)!!,
                playerService.getAge(player.uniqueId)!!)
        })
    }

}