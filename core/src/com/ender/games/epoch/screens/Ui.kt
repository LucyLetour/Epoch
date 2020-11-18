package com.ender.games.epoch.screens

import com.badlogic.gdx.graphics.g2d.Batch
import com.badlogic.gdx.graphics.g2d.Sprite
import com.badlogic.gdx.scenes.scene2d.Actor
import com.badlogic.gdx.scenes.scene2d.Stage
import com.badlogic.gdx.scenes.scene2d.ui.*
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener
import com.badlogic.gdx.scenes.scene2d.utils.Drawable
import com.badlogic.gdx.scenes.scene2d.utils.SpriteDrawable
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable
import com.ender.games.epoch.GAME_MANAGER
import com.ender.games.epoch.entities.Player
import com.ender.games.epoch.util.ASSET_MANAGER
import com.ender.games.epoch.util.UI
import kotlin.math.ceil
import kotlin.math.min

private const val INVENTORY_GRID_SIZE = 50

class Ui : Stage() {

    private val table = Table()
    private val skin = ASSET_MANAGER.get(UI.SKIN)
    private val game by lazy {
        GAME_MANAGER.game!!
    }

    init {
        table.setFillParent(true)
        table.clip = true
        table.debug = true
        this.addActor(table)

        setInGameUI()
    }

    fun setInGameUI() {
        table.clear()
        table.add(
                TextButton("Inventory", skin).apply {
                    addListener(object : ChangeListener() {
                        override fun changed(event: ChangeEvent?, actor: Actor?) {
                            setInventoryUI()
                        }
                    })
                }
        ).expand().top().left().pad(30f)
    }

    private fun setInventoryUI() {
        val inventoryGrid = Table()
        inventoryGrid.setFillParent(true)

        table.clear()
        table.add(
            TextButton("Exit", skin).apply {
                addListener(object : ChangeListener() {
                    override fun changed(event: ChangeEvent?, actor: Actor?) {
                        setInGameUI()
                    }
                })
            }
        ).top().left().pad(30f)
        table.row()
        table.add(Label("Inventory", skin))
        table.row()
        table.add(ScrollPane(inventoryGrid)).expand().center().pad(0f, 500f, 50f, 500f)
        createInventoryGrid(inventoryGrid)
    }

    private fun createInventoryGrid(grid: Table) {
        println("${grid.width} ${grid.maxWidth} ${grid.minWidth} ${grid.prefWidth}")

        val itemsPerRow = 5//grid.prefWidth.toInt() / INVENTORY_GRID_SIZE
        val items = Player.inventory.items()
        val inventorySize = items.size
        val rows = ceil(inventorySize.toFloat() / itemsPerRow).toInt()

        println("$rows rows at $itemsPerRow items per row")
        for(i in 0 until rows) {
            for(j in 0 until itemsPerRow) {
                if(i*itemsPerRow + j >= inventorySize) break
                println("${i*itemsPerRow + j}, $inventorySize")
                val ico = SpriteDrawable(Sprite(items[i*itemsPerRow + j].icon()))
                grid.add(ImageButton(ico)).size(30f).pad(10f).top()
            }
            grid.row()
        }
        val x = TextureRegionDrawable()
        println(Player.inventory.items())
    }
}