package godot.enemy

import godot.annotation.RegisterClass
import godot.annotation.RegisterFunction
import godot.api.AnimatedSprite2D
import godot.api.CollisionShape2D
import godot.api.RigidBody2D
import godot.core.asStringName

@RegisterClass
class Enemy : RigidBody2D(){

	private var animatedSprite2D: AnimatedSprite2D? = null
	private var collisionShape2D: CollisionShape2D? = null

    @RegisterFunction
	override fun _ready() {
		animatedSprite2D = getNode("AnimatedSprite2D") as AnimatedSprite2D?
		collisionShape2D = getNode("CollisionShape2D") as CollisionShape2D?
		animatedSprite2D?.apply {
			val mobTypes = spriteFrames?.getAnimationNames()?.toList()
			mobTypes?.random()?.asStringName()?.let {
				animation = it
			}
			play()
		}
	}

    @RegisterFunction
	override fun _process(delta: Double) {
	}

	@RegisterFunction
	fun onVisibleOnScreenNotifier2DScreenExited() {
		queueFree()
	}

}
