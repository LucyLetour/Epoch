package com.epochgames.epoch.entities.components;

import com.badlogic.ashley.core.ComponentMapper;

public class Mappers {
    public static final ComponentMapper<ActionCompletenessComponent> actionCompleteness = ComponentMapper.getFor(ActionCompletenessComponent.class);
    public static final ComponentMapper<HealthComponent> health = ComponentMapper.getFor(HealthComponent.class);
    public static final ComponentMapper<IconComponent> icon = ComponentMapper.getFor(IconComponent.class);
    public static final ComponentMapper<InteractableComponent> interactable = ComponentMapper.getFor(InteractableComponent.class);
    public static final ComponentMapper<TransformComponent> transform = ComponentMapper.getFor(TransformComponent.class);
    public static final ComponentMapper<TurnComponent> turn = ComponentMapper.getFor(TurnComponent.class);
    public static final ComponentMapper<TypeComponent> type = ComponentMapper.getFor(TypeComponent.class);
}
