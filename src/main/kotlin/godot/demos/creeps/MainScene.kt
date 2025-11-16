import godot.demos.creeps.Hud
import godot.annotation.Export
import godot.annotation.RegisterClass
import godot.annotation.RegisterFunction
import godot.annotation.RegisterProperty
import godot.api.*
import godot.core.Callable
import godot.core.Vector2
import godot.core.asStringName
import godot.core.connect
import godot.global.GD
import kotlin.math.PI

@RegisterClass
class MainScene : Node() {

	@Export
	@RegisterProperty
	var mobScene: PackedScene? = null

	private var scoreTimer: Timer? = null
	private var mobTimer: Timer? = null
	private var startTimer: Timer? = null

	private var startPosition: Marker2D? = null

	private var score: Int = 0

	private var player: Player? = null

	private var hud: Hud? = null

	@RegisterFunction
	override fun _ready() {
		scoreTimer = getNode("ScoreTimer") as Timer?
		mobTimer = getNode("MobTimer") as Timer?
		startTimer = getNode("StartTimer") as Timer?
		startPosition = getNode("StartPosition") as Marker2D?
		player = getNode("Player") as Player?
		hud = getNode("HUD") as Hud?
		player?.hitEvent?.connect {
			GD.print("hit event received!")
			gameOver()
		}
		hud?.startGame?.connect {
			GD.print("startGame event received!")
			newGame()
		}
	}

	@RegisterFunction
	override fun _onDestroy() {
		player?.hitEvent?.disconnect(Callable.invoke(this, "gameOver".asStringName()))
		hud?.startGame?.disconnect(Callable.invoke(this, "newGame".asStringName()))
		super._onDestroy()
	}

	@RegisterFunction
	fun gameOver() {
		scoreTimer?.stop()
		mobTimer?.stop()
		hud?.showGameOver()
	}

	@RegisterFunction
	fun newGame() {
		score = 0
		player?.start(startPosition?.position)
		startTimer?.start()
		hud?.updateScore(score)
		hud?.showMessage("准备好了！")
	}

	@RegisterFunction
	fun onMobTimerTimeout() {
		val mob = mobScene?.instantiate() as RigidBody2D
		val mobSpawnLocation = getNode("MobPath/MobSpawnLocation") as PathFollow2D
		mobSpawnLocation.progressRatio = GD.randf()
		mob.position = mobSpawnLocation.position
		var direction = mobSpawnLocation.rotation + PI / 2
		direction += GD.randfRange((-PI / 4).toFloat(), (-PI / 4).toFloat())
		mob.rotation = direction.toFloat()

		val velocity = Vector2(GD.randfRange(150f, 250f), 0.0)
		mob.linearVelocity = velocity.rotated(direction)

		addChild(mob)

	}
	@RegisterFunction
	fun onScoreTimerTimeout() {
		score += 1
		hud?.updateScore(score)
	}
	@RegisterFunction
	fun onStartTimerTimeout() {
		mobTimer?.start()
		scoreTimer?.start()
	}
}
