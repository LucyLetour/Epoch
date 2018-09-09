package com.epochgames.epoch.entities.components;

import com.badlogic.ashley.core.ComponentMapper;

public class Mappers {
    public static final ComponentMapper<IconComponent> icon = ComponentMapper.getFor(IconComponent.class);
    public static final ComponentMapper<TransformComponent> transform = ComponentMapper.getFor(TransformComponent.class);
}
