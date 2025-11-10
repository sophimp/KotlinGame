package godot

import godot.annotation.RegisterClass
import godot.annotation.RegisterFunction
import godot.api.Input
import godot.api.Sprite2D
import godot.api.Timer
import godot.core.Callable
import godot.core.Vector2
import godot.core.asStringName
import godot.global.GD


@RegisterClass
class Sprite2DTest : Sprite2D() {

	private var speed : Int = 400
	private var angularSpeed = Math.PI

	@RegisterFunction
	override fun _ready() {
		GD.print("Hello Sprite2D test !")
//		val timer = getNode("Timer") as Timer
//		timer.timeout.connect(Callable.invoke(this, "on_timer_out".asStringName()))
	}

	@RegisterFunction
	override fun _process(delta: Double) {
		var direction = 0
		if (Input.isActionPressed("ui_left")) {
			direction = -1
		} else if (Input.isActionPressed("ui_right")) {
			direction = 1
		}
		rotation += (angularSpeed * delta * direction).toFloat()
		var velocity = Vector2.ZERO
		if (Input.isActionPressed("ui_up")) {
			velocity = Vector2.UP.rotated(rotation.toDouble()) * speed
		} else if (Input.isActionPressed("ui_down")) {
			velocity = Vector2.DOWN.rotated(rotation.toDouble()) * speed
		}
		position += velocity * delta
	}

	@RegisterFunction
	fun onButtonPressed() {
		GD.print("onButtonPressed")
		setProcess(!isProcessing())
	}

	@RegisterFunction
	fun onTimerOut() {
		visible = !visible
	}
}
