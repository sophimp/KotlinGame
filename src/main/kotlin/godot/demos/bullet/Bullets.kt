import godot.annotation.RegisterClass
import godot.annotation.RegisterFunction
import godot.api.Node2D
import godot.api.PhysicsServer2D
import godot.api.Texture2D
import godot.core.RID
import godot.core.Transform2D
import godot.core.Vector2
import godot.global.GD

class BulletItem(
	var position: Vector2,
	var speed: Float = 1f,
	var body: RID = RID(),
)

@RegisterClass
class Bullets : Node2D() {
	val BULLET_COUNT = 500
	val SPEED_MIN = 20f
	val SPEED_MAX = 80f
	var bullets = mutableListOf<BulletItem>()
	lateinit var shape: RID
	val bulletImage = GD.load<Texture2D>("res://art/bullet/bullet.png")

	@RegisterFunction
	override fun _ready() {
		shape = PhysicsServer2D.circleShapeCreate()
		// set the collision shape's radius for each bullet in pixels
		PhysicsServer2D.shapeSetData(shape, 8)

		repeat(BULLET_COUNT) {
			val bullet = BulletItem(
				// Place bullets randomly on the viewport and move bullets outside the play area so that they fade in nicely.
				position = Vector2(
					GD.randfRange(0f, getViewportRect().size.x.toFloat()) + getViewportRect().size.x,
					GD.randfRange(0f, getViewportRect().size.y.toFloat()),
				),
				speed = GD.randfRange(SPEED_MIN, SPEED_MAX),
				body = PhysicsServer2D.bodyCreate()
			)
			PhysicsServer2D.bodySetSpace(bullet.body, getWorld2d()!!.getSpace())
			PhysicsServer2D.bodyAddShape(bullet.body, shape)
			// Don't make bullets check collision with other bullets to improve performance
			PhysicsServer2D.bodySetCollisionMask(bullet.body, 0)
			val transform2d = Transform2D()
			transform2d.origin = bullet.position
			PhysicsServer2D.bodySetState(bullet.body, PhysicsServer2D.BodyState.TRANSFORM, transform2d)
			bullets.add(bullet)
		}
	}

	@RegisterFunction
	override fun _process(delta: Double) {
		queueRedraw()
	}

	@RegisterFunction
	override fun _physicsProcess(delta: Double) {
		val transform2d = Transform2D()
		val offset = getViewportRect().size.x + 16
		bullets.forEach {
			it.position.x -= it.speed * delta
			if (it.position.x < -16) {
				it.position.x = offset
			}
			transform2d.origin = it.position
			PhysicsServer2D.bodySetState(it.body, PhysicsServer2D.BodyState.TRANSFORM, transform2d)
		}
	}

	@RegisterFunction
	override fun _draw() {
		bulletImage?.let { image ->
			val offset = image.getSize() * 0.5
			bullets.forEach { bullet ->
				drawTexture(image, bullet.position + offset)
			}
		}
	}

	@RegisterFunction
	override fun _exitTree() {
		bullets.forEach { bullet ->
			PhysicsServer2D.freeRid(bullet.body)
		}
		PhysicsServer2D.freeRid(shape)
		bullets.clear()
	}
}
