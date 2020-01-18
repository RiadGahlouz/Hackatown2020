package com.example.polyeats.historyFragment.transaction

import java.util.ArrayList
import java.util.HashMap

/**
 * Helper class for providing sample content for user interfaces created by
 * Android template wizards.
 *
 */
object TransactionContent {

    val ITEMS: MutableList<TransactionItem> = ArrayList()
    val ITEM_MAP: MutableMap<String, TransactionItem> = HashMap()

    private val COUNT = 25

    init {
        // Add some sample items.
        for (i in 1..COUNT) {
            addItem(createTransactionItem(i))
        }
    }

    private fun addItem(item: TransactionItem) {
        ITEMS.add(item)
        ITEM_MAP.put(item.id, item)
    }

    private fun createTransactionItem(position: Int): TransactionItem {
        return TransactionItem(position.toString(), "Item " + position, makeDetails(position))
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
    data class TransactionItem(val id: String, val content: String, val details: String) {
        override fun toString(): String = content
    }
}
