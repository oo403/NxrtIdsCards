package pl.sirox.common.configuration

import eu.okaeri.configs.OkaeriConfig
import kotlin.collections.mutableMapOf

class InventoryConfiguration : OkaeriConfig() {

    var title: String = "ID of <player>"
    var rows: Int = 3

    var items: MutableMap<String, InventoryItems> = mutableMapOf(
        "exit" to InventoryItems().apply {
            slots = listOf(0)
            material = "minecraft:barrier"
            amount = 1
        }
    )

    class InventoryItems : OkaeriConfig() {
        var slots: List<Int> = listOf(0)
        var material: String = "minecraft:diamond_sword"
        var amount: Int = 1
    }

}