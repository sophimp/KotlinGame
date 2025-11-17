import godot.annotation.RegisterClass
import godot.annotation.RegisterFunction
import godot.api.CharacterBody2D
import godot.api.Input
import godot.core.Vector2
import kotlin.math.tan

@RegisterClass
class Troll : CharacterBody2D() {
	val MOTION_SPEED = 30
	val FRICATION_FACTOR = 0.89
	val TAN30DEG = tan(Math.toRadians(30.toDouble())).toFloat()
	@RegisterFunction
	override fun _physicsProcess(delta: Double) {
		val motion = Vector2()
		motion.x = Input.getAxis("move_left", "move_right").toDouble()
		motion.y = Input.getAxis("move_up", "move_down").toDouble()
		// make diagonal movement fit for hexagonal tiles
		motion.y *= TAN30DEG
		velocity += motion.normalized() * MOTION_SPEED

		velocity *= FRICATION_FACTOR
		moveAndSlide()
	}
}
