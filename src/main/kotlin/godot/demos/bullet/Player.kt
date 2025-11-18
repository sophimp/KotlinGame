import godot.annotation.RegisterClass
import godot.annotation.RegisterFunction
import godot.api.AnimatedSprite2D
import godot.api.Area2D
import godot.api.Input
import godot.api.InputEvent
import godot.api.InputEventMouseMotion
import godot.api.Node2D
import godot.core.RID
import godot.core.Vector2

@RegisterClass
class BulletPlayer : Area2D() {
	var touching: Int = 0
	lateinit var sprite: AnimatedSprite2D

	@RegisterFunction
	override fun _ready() {
		sprite = getNode("AnimatedSprite2D") as AnimatedSprite2D
		Input.setMouseMode(Input.MouseMode.HIDDEN)
	}

	@RegisterFunction
	override fun _input(event: InputEvent?) {
		if (event is InputEventMouseMotion) {
			position = event.position - Vector2(0, 16)
		}
	}

	@RegisterFunction
	fun onBodyShapeEntered(bodyId: RID?, body: Node2D?, bodyShapeIndex: Int, localShapeIndex: Int) {
		touching += 1
		if (touching >= 1){
			sprite.frame = 1
		}
	}

	@RegisterFunction
	fun onBodyShapeExited(bodyId: RID?, body: Node2D?, bodyShapeIndex: Int, localShapeIndex: Int) {
		touching -= 1
		if (touching == 0){
			sprite.frame = 0
		}

	}

}
