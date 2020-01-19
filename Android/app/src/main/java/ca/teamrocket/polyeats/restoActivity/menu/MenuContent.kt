package ca.teamrocket.polyeats.restoActivity.menu

import java.util.ArrayList
import java.util.HashMap

/**
 * Helper class for providing sample content for user interfaces created by
 * Android template wizards.
 *
 * TODO: Replace all uses of this class before publishing your app.
 */
object MenuContent {

    /**
     * An array of sample (dummy) items.
     */
    val ITEMS: MutableList<MenuItem> = ArrayList()

    /**
     * A map of sample (dummy) items, by ID.
     */
    val ITEM_MAP: MutableMap<String, MenuItem> = HashMap()

    private val COUNT = 25

    init {
        // Add some sample items.
        for (i in 1..COUNT) {
            addItem(createMenuItem(i))
        }
    }

    private fun addItem(item: MenuItem) {
        ITEMS.add(item)
        ITEM_MAP.put(item.id, item)
    }

    private fun createMenuItem(position: Int): MenuItem {
        return MenuItem(position.toString(), "Item $position", "$position.00 $", true )
    }

    /**
     * A dummy item representing a piece of content.
     */
    data class MenuItem(val id: String, val name: String, val price: String, val vegetarian: Boolean) {
        override fun toString(): String = name
    }
}
