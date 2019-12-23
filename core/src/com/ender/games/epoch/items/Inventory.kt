package com.ender.games.epoch.items

interface InventoryItem

interface Equippable: InventoryItem

object Inventory {
    var money: Int = 0
        private set
    private val slots = ArrayList<InventoryItem?>(0)

    fun getItem(slot: Int): InventoryItem? {
        if(slot >= slots.size) {
            return null
        }
        return slots[slot]
    }
}