package com.ender.games.epoch.items

import kotlin.reflect.KClass

interface InventoryItem

interface Equippable: InventoryItem

object Inventory {
    var money: Int = 0
        private set
    private val slots = mutableListOf<Stack<InventoryItem>>()

    /**
     * Remove the first item from the correct Stack
     *
     * @param item The class of the item to be retrieved
     *
     * @return The item retrieved, or null if not found
     */
    fun removeItem(item: KClass<out InventoryItem>): InventoryItem? {
        with(slots.find { item == it.type() }) {
            if(this != null) {
                val ret = this.first()
                if(this.size() <= 0) {
                    slots.remove(this)
                }
                return ret
            }
            return null
        }

    }

    /**
     * Adds an item to the Inventory. If at least one copy of the item already exists in the inventory,
     * add the new copy to a new Stack of that item. Else, make a new Stack of that Item.
     *
     * @param item The item to add to the inventory
     */
    fun addItem(item: InventoryItem) {
        // TODO Don't add if inventory is full
        if(slots.find { it.type().isInstance(item) } != null) {
            slots.find { it.type().isInstance(item) }!!.add(item)
        } else {
            slots.add(Stack(mutableListOf(item)))
        }
    }
}


/**
 * A Stack of an item, this represents a list of items of the same type
 */
class Stack<T: InventoryItem>(val list: MutableList<T>) {
    fun size() = list.size
    fun type() = list[0]::class
    fun first() = list.removeAt(0)
    fun add(item: T) {
        list.add(item)
    }

    override fun toString(): String {
        return list.toString()
    }
}