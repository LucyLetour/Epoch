package com.ender.games.epoch.entities.components

import com.badlogic.ashley.core.ComponentMapper

val render: ComponentMapper<RenderComponent> = ComponentMapper.getFor(RenderComponent::class.java)
val renderLine: ComponentMapper<RenderLineComponent> = ComponentMapper.getFor(RenderLineComponent::class.java)
val physics: ComponentMapper<PhysicsComponent> = ComponentMapper.getFor(PhysicsComponent::class.java)
val player: ComponentMapper<PlayerComponent> = ComponentMapper.getFor(PlayerComponent::class.java)
val bulletReq: ComponentMapper<BulletRequestComponent> = ComponentMapper.getFor(BulletRequestComponent::class.java)
val bullet: ComponentMapper<BulletComponent> = ComponentMapper.getFor(BulletComponent::class.java)
val weapon: ComponentMapper<WeaponComponent> = ComponentMapper.getFor(WeaponComponent::class.java)
val inputCode: ComponentMapper<InputCodeComponent> = ComponentMapper.getFor(InputCodeComponent::class.java)