package com.example.polyeats.restoFragment.resto

import java.util.ArrayList
import java.util.HashMap

/**
 * Helper class for providing sample content for user interfaces created by
 * Android template wizards.
 *
 */
object RestoContent {

    val ITEMS: MutableList<RestoItem> = ArrayList()
    val ITEM_MAP: MutableMap<String, RestoItem> = HashMap()

    private val COUNT = 25

    init {
        // Add some sample items.
        for (i in 1..COUNT) {
            addItem(createRestoItem(i))
        }
    }

    private fun addItem(item: RestoItem) {
        ITEMS.add(item)
        ITEM_MAP.put(item.id, item)
    }

    private fun createRestoItem(position: Int): RestoItem {
        return RestoItem(position.toString(), "Item " + position, makeDetails(position))
    }

    private fun makeDetails(position: Int): String {
        val builder = StringBuilder()
        builder.append("Details about Item: ").append(position)
        for (i in 0..position - 1) {
            builder.append("\nMore details information here.")
        }
        return builder.toString()
    }

    /**
     * A dummy item representing a piece of content.
     */
    data class RestoItem(val id: String, val content: String, val details: String) {
        override fun toString(): String = content
    }
}
