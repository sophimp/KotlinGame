import godot.annotation.RegisterClass
import godot.annotation.RegisterFunction
import godot.annotation.RegisterProperty
import godot.api.AnimatedSprite2D
import godot.api.Area2D
import godot.api.Input
import godot.core.Vector2
import godot.core.asStringName
import godot.global.GD

@RegisterClass
class Player : Area2D() {

	@RegisterProperty
	var speed: Int = 400
	lateinit var screeSize : Vector2

	@RegisterFunction
	override fun _ready() {
		screeSize = getViewportRect().size
	}

	@RegisterFunction
	override fun _process(delta: Double) {
		var velocity = Vector2.ZERO
		if (Input.isActionPressed("move_right")) {
			velocity.x += 1
		}
		if (Input.isActionPressed("move_left")) {
			velocity.x -= 1
		}
		if (Input.isActionPressed("move_up")) {
			velocity.y -= 1
		}
		if (Input.isActionPressed("move_down")) {
			velocity.y += 1
		}
		val animation2d = getNode("AnimatedSprite2D") as AnimatedSprite2D?
		if (velocity.length() > 0) {
			velocity = velocity.normalized() * speed
			animation2d?.play()
		} else {
			animation2d?.stop()
		}

		if (velocity.x.toInt() != 0) {
			animation2d?.animation = "walk".asStringName()
			animation2d?.flipV = false
			animation2d?.flipH = velocity.x < 0
		} else if (velocity.y.toInt() != 0) {
			animation2d?.animation = "up".asStringName()
			animation2d?.flipV = velocity.y > 0
		}
		position += velocity * delta
		position = position.clamp(Vector2.ZERO, screeSize)
	}

	@RegisterFunction
	fun hitEventHandler() {
		GD.print("hit_event")
	}
}
