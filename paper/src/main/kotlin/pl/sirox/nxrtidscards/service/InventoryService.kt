package pl.sirox.nxrtidscards.service

import com.google.inject.Inject
import dev.triumphteam.gui.builder.item.ItemBuilder
import dev.triumphteam.gui.guis.Gui
import dev.triumphteam.gui.guis.GuiItem
import me.clip.placeholderapi.PlaceholderAPI
import net.kyori.adventure.text.minimessage.MiniMessage
import net.kyori.adventure.text.minimessage.tag.resolver.Placeholder
import org.bukkit.Material
import org.bukkit.entity.Player
import org.bukkit.inventory.ItemStack
import pl.sirox.common.configuration.InventoryConfiguration

class InventoryService @Inject constructor(
    private val inventoryConfiguration: InventoryConfiguration
) {

    private val mini = MiniMessage.miniMessage()

    private val title: String get() = inventoryConfiguration.title
    private val rows: Int get() = inventoryConfiguration.rows
    private val items: Map<String, InventoryConfiguration.InventoryItems> get() = inventoryConfiguration.items

    fun buildIdInventory(owner: Player): Gui {
        val gui = Gui.gui()
            .title(mini.deserialize(
                PlaceholderAPI.setPlaceholders(owner, title),
                Placeholder.parsed("player", owner.name))
            )
            .rows(rows)
            .create()

        gui.setDefaultClickAction { event ->
            event.isCancelled = true
        }

        items.forEach { (id, item) ->
            val material: Material = Material.matchMaterial(item.material) ?: throw IllegalArgumentException("Material ${item.material} not found!")
            val itemStack = ItemStack(material, item.amount)

            val guiItem: GuiItem = ItemBuilder.from(itemStack).asGuiItem()

            item.slots.forEach { slot ->
                gui.setItem(slot, guiItem)

                item.action?.let { action ->
                    when (action.actionType) {
                        "close" -> {
                            gui.addSlotAction(slot) { event ->
                                gui.close(event.whoClicked)
                            }
                        }
                    }
                }
            }
        }

        return gui
    }

    fun showIdInventoryNearby(owner: Player) {
        val gui = buildIdInventory(owner)
        gui.open(owner)

        val ownerLoc = owner.location
        val playersInDistance = ownerLoc.world.getNearbyPlayers(ownerLoc, 10.0, 10.0, 10.0)

        playersInDistance.forEach { player ->
            gui.open(player)
        }
    }

}