package com.epochgames.epoch.util;

import com.badlogic.gdx.assets.AssetDescriptor;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.resolvers.InternalFileHandleResolver;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGeneratorLoader;
import com.badlogic.gdx.maps.tiled.AtlasTmxMapLoader;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;

/**
 * {@link AssetManager} wrapper class that contains some {@link AssetDescriptor}s
 * to easily load in assets on the fly.
 *
 * @author Jared Tulayan (A.K.A. Endoman123)
 */
public class Assets {
    public static final AssetManager MANAGER;

    // Initialize the asset manager by constructing it.
    static {
        MANAGER = new AssetManager();
        MANAGER.setLoader(TiledMap.class, new AtlasTmxMapLoader(new InternalFileHandleResolver()));
        MANAGER.setLoader(FreeTypeFontGenerator.class, new FreeTypeFontGeneratorLoader(new InternalFileHandleResolver()));
    }

    /**
     * Subclass containing {@link AssetDescriptor}s for game spritemaps
     */
    public static class Spritesheets {
        public static AssetDescriptor<TextureAtlas> SHIPS = new AssetDescriptor<>("ships/Ships.atlas", TextureAtlas.class);
        public static AssetDescriptor<TextureAtlas> PLANETS = new AssetDescriptor<>("planets/Planets.atlas", TextureAtlas.class);
    }

    /**
     * Subclass containing {@link AssetDescriptor}s for game tileMaps
     */
    public static class TileMaps {
        //TODO Turn the tileset for this (and all maps) into a ztk or other compressed format so everything runs smooth
        public static AssetDescriptor<TiledMap> OPEN_SPACE = new AssetDescriptor<>("maps/OpenSpaceMap.tmx", TiledMap.class);
        public static AssetDescriptor<TextureAtlas> OPEN_SPACE_TILE_SET = new AssetDescriptor<>("maps/SpaceTiles/SpaceTiles.atlas", TextureAtlas.class);
    }

    /**
     * Subclass containing {@link AssetDescriptor}s for game object textures
     */
    public static class Textures {
        //public static AssetDescriptor<Texture> name = new AssetDescriptor<>("path/file.png", Texture.class);
        public static AssetDescriptor<Texture> HEX_TILE = new AssetDescriptor<>("maps/HexagonTile.png", Texture.class);
    }

    /**
     * Subclass containing {@link AssetDescriptor}s for ui textures
     */
    public static class UI {
        public static AssetDescriptor<TextureAtlas> ATLAS = new AssetDescriptor<>("ui/uiskin.atlas", TextureAtlas.class);
        public static AssetDescriptor<Skin> SKIN = new AssetDescriptor<>("ui/uiskin.json", Skin.class);
    }

    /**
     * Subclass containing {@link AssetDescriptor}s for music and sfx
     */
    public static class Audio {
        public static AssetDescriptor<Music> THEME = new AssetDescriptor<>("sfx/music/fallen.wav", Music.class);
        public static AssetDescriptor<Sound> LASER_SHOOT = new AssetDescriptor<Sound>("sounds/laser.ogg", Sound.class);
        public static AssetDescriptor<Sound> POWERUP_SOUND = new AssetDescriptor<Sound>("sounds/powerup.ogg", Sound.class);
    }
}
