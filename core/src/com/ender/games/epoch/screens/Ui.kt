package com.ender.games.epoch.screens

import com.badlogic.gdx.scenes.scene2d.Actor
import com.badlogic.gdx.scenes.scene2d.Stage
import com.badlogic.gdx.scenes.scene2d.ui.Table
import com.badlogic.gdx.scenes.scene2d.ui.TextButton
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener
import com.ender.games.epoch.util.ASSET_MANAGER
import com.ender.games.epoch.util.UI


class Ui : Stage() {

    private val table = Table()
    private val skin = ASSET_MANAGER.get(UI.SKIN)
    val button1 = TextButton("Button", skin)

    init {
        table.setFillParent(true)
        table.clip = true
        table.debug = true
        table.add(button1)
        this.addActor(table)




        button1.addListener(object : ChangeListener() {
            override fun changed(event: ChangeEvent?, actor: Actor?) {
                println("Clicked! Is checked: $button1.isChecked")
                button1.setText("Good job!")
            }
        })
    }





}