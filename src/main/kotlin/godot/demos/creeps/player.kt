import godot.annotation.RegisterClass
import godot.annotation.RegisterFunction
import godot.annotation.RegisterProperty
import godot.annotation.RegisterSignal
import godot.api.*
import godot.core.Signal0
import godot.core.Vector2
import godot.core.asStringName

@RegisterClass
class Player : Area2D() {

	@RegisterProperty
	var speed: Int = 400

	@RegisterSignal
	val hitEvent : Signal0 = Signal0("hitEvent")

	lateinit var screeSize : Vector2

	private var collision2D: CollisionShape2D? = null
	private var animation2d: AnimatedSprite2D? = null

	@RegisterFunction
	override fun _ready() {
		screeSize = getViewportRect().size
		collision2D = getNode("CollisionShape2D") as CollisionShape2D?
		animation2d = getNode("AnimatedSprite2D") as AnimatedSprite2D?
        hide()
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
	fun onBodyEntered(body: Node2D) {
		// 确保只发射一次
		hide()
		hitEvent.emit()
		collision2D?.setDeferred("disabled", true)
	}

	@RegisterFunction
	fun start(pos: Vector2?) {
		pos?.let {
			position = pos
		}
		show()
		collision2D?.disabled = false
	}
}
