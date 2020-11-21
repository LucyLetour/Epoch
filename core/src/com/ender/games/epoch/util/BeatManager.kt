package com.ender.games.epoch.util

import com.badlogic.gdx.audio.Music
import com.ender.games.epoch.GAME_MANAGER

object BeatManager {
    var beat = 1
    var measure = 1
    val BPS = 2
    val SPB = 1f / BPS

    private var accum = 0f

    private var shopWhenAvailable = false
    private var exitShopWhenAvailable = false
    private var inShop = false

    private val regJumps = mapOf( // Increase all by one and set measure to 1 up top?
            37 to 68,
            100 to 8
    )

    private val shopJumps = mapOf( // Increase all by one and set measure to 1 up top?
            60 to 40,
    )

    private val music: Music = ASSET_MANAGER.get(Audio.MUSIC)

    var start = 0L

    fun setVolume(vol: Float) {
        music.volume = vol.coerceIn(0f, 1f)
    }

    fun start() {
        if(music.isPlaying) music.stop()
        start = GAME_MANAGER.game!!.inGameScreen.startTime
        music.play()
    }

    fun triggerShop() {
        shopWhenAvailable = !inShop
    }

    fun triggerLeaveShop() {
        exitShopWhenAvailable = inShop
    }

    fun getQuarterNoteT(): Float {
        return 1f
    }

    /**
     * Returns the value of some polynomial of the for (x - c1)*(x-c2)*...*(x-ci)
     * Where ci is the time in beats of the clap and x is the current seek time of the song in beats
     */
    fun getClapT(): Float {
        return 1f
    }

    fun tick(delta: Float) {
        accum += delta
        if(accum >= SPB) {
            beat++
            accum -= SPB
        }

        if(beat > 4) {
            beat = 1
            measure++

            if(shopWhenAvailable && measure !in 37..60) {
                shopWhenAvailable = false
                inShop = true
                jump(36)
            } else if (exitShopWhenAvailable && measure in 37..60) {
                exitShopWhenAvailable = false
                inShop = false
                jump(60)
            } else if (regJumps.containsKey(measure) && !inShop) {
                jump(regJumps[measure] ?: error(""))
            } else if (shopJumps.containsKey(measure) && inShop) {
                jump(shopJumps[measure] ?: error(""))
            }
        }
    }

    private fun jump(toMeasure: Int) {
        println(music.position)
        music.position = SPB * 4 * toMeasure.toFloat()
        println(music.position)
        measure = toMeasure
        beat = 1
        accum = 0f
    }

    fun correct() {
        // = music.position % (SPB * 4f)
    }
}