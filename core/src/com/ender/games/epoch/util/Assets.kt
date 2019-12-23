package com.ender.games.epoch.util

import com.badlogic.gdx.assets.AssetDescriptor
import com.badlogic.gdx.assets.AssetManager
import com.badlogic.gdx.assets.loaders.resolvers.InternalFileHandleResolver
import com.badlogic.gdx.audio.Music
import com.badlogic.gdx.audio.Sound
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.TextureAtlas
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGeneratorLoader
import com.badlogic.gdx.scenes.scene2d.ui.Skin

val ASSET_MANAGER = AssetManager().apply { setLoader(FreeTypeFontGenerator::class.java, FreeTypeFontGeneratorLoader(InternalFileHandleResolver())) }

object Spritesheets {
    var SHIPS = AssetDescriptor("ships/Ships.atlas", TextureAtlas::class.java)
    var PLANETS = AssetDescriptor("planets/Planets.atlas", TextureAtlas::class.java)
}

object TileMaps {
    //TODO Turn the tileset for this (and all maps) into a ztk or other compressed format so everything runs smooth
    var OPEN_SPACE_TILE_SET = AssetDescriptor("maps/SpaceTiles/SpaceTiles.atlas", TextureAtlas::class.java)
}

object Textures {
    //public static AssetDescriptor<Texture> name = new AssetDescriptor<>("path/file.png", Texture.class);
    var HEX_TILE = AssetDescriptor("maps/HexagonTile.png", Texture::class.java)
}

object UI {
    var ATLAS = AssetDescriptor("ui/uiskin.atlas", TextureAtlas::class.java)
    var SKIN = AssetDescriptor("ui/uiskin.json", Skin::class.java)
}

object Audio {
    var THEME = AssetDescriptor("sfx/music/fallen.wav", Music::class.java)
    var LASER_SHOOT = AssetDescriptor("sounds/laser.ogg", Sound::class.java)
    var POWERUP_SOUND = AssetDescriptor("sounds/powerup.ogg", Sound::class.java)
}