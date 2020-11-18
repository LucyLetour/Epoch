package com.ender.games.epoch.util

import com.ender.games.epoch.E_BRANCHES
import com.ender.games.epoch.HEX_ROOM_SIZE
import kotlin.math.absoluteValue
import kotlin.math.round
import kotlin.math.sqrt
import kotlin.random.Random

data class Room(val coord: HexCoord, val parent: Room?) {
    val children: MutableList<Room> = mutableListOf()

    fun contains(hCoord: HexCoord): Boolean = hCoord == coord || children.any { it.contains(hCoord) }

    fun parentAt(dir: Int) = parent?.coord == coord + dirs[dir]!!
    fun hasChild(dir: Int) = children.map { it.coord }.contains(coord + dirs[dir]!!)

    // Testing only
    fun dups(): List<HexCoord> {
        return children.map { it.dups() }.flatten() + coord
    }

    override fun toString(): String {
        return "$coord $children"
    }
}

data class Cube(val x: Float, val y: Float, val z: Float) {
    fun toHex() = cubeRound(this)
}

data class HexCoord(val x: Int, val y: Int, val z: Int) {
    fun toQR() = Pair(x, z)
}

fun fromQR(qr: Pair<Int, Int>) = HexCoord(qr.first, -qr.first - qr.second, qr.second)

operator fun HexCoord.plus(other: HexCoord) = HexCoord(this.x + other.x, this.y + other.y, this.z + other.z)
val HEX_ZERO = HexCoord(0, 0, 0)
val dirs = mapOf(
        0 to HexCoord(1, -1, 0),
        5 to HexCoord(1, 0, -1),
        4 to HexCoord(0, 1, -1),
        3 to HexCoord(-1, 1, 0),
        2 to HexCoord(-1, 0, 1),
        1 to HexCoord(0, -1, 1),
)

fun Pair<Int, Int>.toPoint(): Pair<Float, Float> {
    val x =  (sqrt(3.0) * this.first + sqrt(3.0) / 2f * this.second).toFloat()
    val y =  (                                    3.0  / 2f * this.second).toFloat()
    return Pair(x, y)
}

fun cubeRound(cube: Cube): HexCoord  {
    var rx = round(cube.x)
    var ry = round(cube.y)
    var rz = round(cube.z)

    val xDiff = (rx - cube.x).absoluteValue
    val yDiff = (ry - cube.y).absoluteValue
    val zDiff = (rz - cube.z).absoluteValue

    if(xDiff > yDiff && xDiff > zDiff) {
        rx = -ry - rz
    } else if (yDiff > zDiff) {
        ry = -rx - rz
    } else {
        rz = -rx - ry
    }

    return HexCoord(rx.toInt(), ry.toInt(), rz.toInt())
}

object HexMap {
    var seed = System.currentTimeMillis()
    private val rnd = Random(seed)
    val head = Room(HEX_ZERO, null)

    fun gen(numRooms: Int) {
        head.children.clear()
        gen(numRooms, head)

    }

    private fun gen(numRooms: Int, prev: Room) {
        var nr = numRooms
        for(i in 0..5) {
            if(nr <= 0) break
            if(rnd.nextFloat() <= E_BRANCHES / 6.0) {
                if(!head.contains(prev.coord + dirs[i]!!)) {
                    prev.children.add(Room(prev.coord + dirs[i]!!, prev))
                    nr--
                }
            }
        }

        if(nr > 0) {
            if(prev.children.isEmpty()) {
                var p = false
                for(i in 0..5) {
                    if(nr <= 0) break
                    if(!head.contains(prev.coord + dirs[i]!!)) {
                        prev.children.add(Room(prev.coord + dirs[i]!!, prev))
                        nr--
                        p = true
                    }
                }
                if(!p) return
            }

            var dChildRs =
                    MutableList(prev.children.size) { rnd.nextDouble(0.0, 1.0) }
            val pSum = dChildRs.sum()
            val iChildRs = dChildRs.map { it / pSum * nr}.map { it.toInt() } as MutableList<Int>
            iChildRs[iChildRs.lastIndex] = nr - iChildRs.dropLast(1).sum()
            prev.children.forEachIndexed { idx, child ->
                gen(iChildRs[idx], child)
            }
        }

    }
}

fun main() {
    for(i in 0..100) {
        HexMap.gen(100)
        val x = HexMap.head.dups()
        if(x.distinct() != x) {
            println(HexMap.head)
        }
    }
}