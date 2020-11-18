package com.ender.games.epoch.util

import kotlin.random.Random

const val E_BRANCHES = 2

data class Room(val coord: HexCoord) {
    val children: MutableList<Room> = mutableListOf()

    fun contains(hCoord: HexCoord): Boolean = hCoord == coord || children.any { it.contains(hCoord) }

    fun dups(): List<HexCoord> {
        return children.map { it.dups() }.flatten() + coord
    }

    override fun toString(): String {
        return "$coord $children"
    }
}

data class HexCoord(val x: Int, val y: Int, val z: Int)
operator fun HexCoord.plus(other: HexCoord) = HexCoord(this.x + other.x, this.y + other.y, this.z + other.z)
val HEX_ZERO = HexCoord(0, 0, 0)
val dirs = mapOf(
        0 to HexCoord(1, -1, 0),
        1 to HexCoord(1, 0, -1),
        2 to HexCoord(0, 1, -1),
        3 to HexCoord(-1, 1, 0),
        4 to HexCoord(-1, 0, 1),
        5 to HexCoord(0, -1, 1),
)

object Map {
    var seed = System.currentTimeMillis()
    private val rnd = Random(seed)
    val head = Room(HEX_ZERO)

    fun gen(numRooms: Int) {
        head.children.clear()
        gen(numRooms, head)

    }

    private fun gen(numRooms: Int, prev: Room) {
        for(i in 0..5) {
            if(rnd.nextFloat() <= E_BRANCHES / 6.0) {
                if(!head.contains(prev.coord + dirs[i]!!)) {
                    prev.children.add(Room(prev.coord + dirs[i]!!))
                }
            }
        }

        if(numRooms > 1) {
            if(prev.children.isEmpty()) {
                var p = false
                for(i in 0..5) {
                    if(!head.contains(prev.coord + dirs[i]!!)) {
                        prev.children.add(Room(prev.coord + dirs[i]!!))
                        p = true
                    }
                }
                if(!p) return
            }
            val nr = numRooms - 1
            var childRs =
                    MutableList(prev.children.size) {rnd.nextDouble(0.0, 1.0)}
            val pSum = childRs.sum()
            childRs = childRs.map { it / pSum * nr} as MutableList<Double>
            childRs[childRs.lastIndex] = nr - childRs.dropLast(1).sum()
            prev.children.forEachIndexed { idx, child ->
                gen(childRs[idx].toInt(), child)
            }
        }

    }
}

fun main() {
    for(i in 0..100) {
        Map.gen(100)
        val x = Map.head.dups()
        if(x.distinct() != x) {
            println(Map.head)
        }
    }
}