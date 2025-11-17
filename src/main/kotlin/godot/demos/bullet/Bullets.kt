import godot.annotation.RegisterClass
import godot.annotation.RegisterFunction
import godot.api.Node2D
import godot.api.PhysicsServer2D
import godot.core.RID
import godot.core.Vector2

class BulletItem(
    var position: Vector2,
    var speed : Double = 1.0,
    var body: RID = RID(),
)

@RegisterClass
class Bullets : Node2D() {
    val BULLET_COUNT = 500
    val SPEED_MIN = 20
    val SPEED_MAX = 80
    var bullets = mutableListOf<BulletItem>()
    lateinit var shape: RID
//    val bulletImage =

    @RegisterFunction
    override fun _ready() {
        shape = PhysicsServer2D.circleShapeCreate()
        // set the collision shape's radius for each bullet in pixels
        PhysicsServer2D.shapeSetData(shape, 8)

    }
}