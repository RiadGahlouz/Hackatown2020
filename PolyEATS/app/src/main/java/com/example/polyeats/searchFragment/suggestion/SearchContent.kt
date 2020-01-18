package com.example.polyeats.searchFragment.suggestion

import java.util.ArrayList
import java.util.HashMap

/**
 * Helper class for providing sample content for user interfaces created by
 * Android template wizards.
 *
 */
object SearchContent {

    val ITEMS: MutableList<SuggestionItem> = ArrayList()
    val ITEM_MAP: MutableMap<String, SuggestionItem> = HashMap()

    private val COUNT = 25

    init {
        // Add some sample items.
        for (i in 1..COUNT) {
            addItem(createSuggestionItem(i))
        }
    }

    private fun addItem(item: SuggestionItem) {
        ITEMS.add(item)
        ITEM_MAP.put(item.id, item)
    }

    private fun createSuggestionItem(position: Int): SuggestionItem {
        return SuggestionItem(position.toString(), "Item " + position, makeDetails(position))
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
    data class SuggestionItem(val id: String, val content: String, val details: String) {
        override fun toString(): String = content
    }
}
