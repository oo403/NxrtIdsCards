package pl.sirox.nxrtidscards.event

import com.google.inject.Inject
import org.bukkit.Bukkit
import org.bukkit.event.EventHandler
import org.bukkit.event.player.PlayerJoinEvent
import pl.sirox.nxrtidscards.bootstrap.Bootstrap
import pl.sirox.nxrtidscards.interfaces.CustomEvent
import pl.sirox.nxrtidscards.service.DatabaseService
import pl.sirox.nxrtidscards.service.PlayerService

class PlayerJoinEvent @Inject constructor(
    private val databaseService: DatabaseService,
    private val playerService: PlayerService,
    private val plugin: Bootstrap
) : CustomEvent {

    @EventHandler
    fun onPlayerJoin(e: PlayerJoinEvent) {
        val player = e.player

        Bukkit.getScheduler().runTaskAsynchronously(plugin, Runnable {
            val data = databaseService.getPlayer(player.uniqueId.toString()) ?: return@Runnable

            Bukkit.getScheduler().runTask(plugin, Runnable {
                playerService.setName(player.uniqueId, data.name)
                playerService.setSurname(player.uniqueId, data.surname)
                playerService.setAge(player.uniqueId, data.age)
            })
        })
    }
}