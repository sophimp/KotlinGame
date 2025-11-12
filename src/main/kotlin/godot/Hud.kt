package godot

import godot.annotation.RegisterClass
import godot.annotation.RegisterFunction
import godot.annotation.RegisterSignal
import godot.api.Button
import godot.api.CanvasLayer
import godot.api.Label
import godot.api.Timer
import godot.core.Signal0
import godot.global.GD
import java.util.concurrent.Delayed

@RegisterClass
class Hud : CanvasLayer(){
	private var messageLabel: Label? = null
	private var scoreLabel: Label? = null
	private var startButton: Button? = null

	private var msgTimer: Timer? = null

	@RegisterSignal
	val startGame = Signal0("startGame")

	@RegisterFunction
	override fun _ready() {
		messageLabel = getNode("MessageLabel") as Label
		scoreLabel = getNode("ScoreLabel") as Label
		startButton = getNode("StartButton") as Button
		msgTimer = getNode("MessageTimer") as Timer
	}

	@RegisterFunction
	fun showMessage(msg: String) {
		messageLabel?.text = msg
		messageLabel?.show()
		msgTimer?.start()
	}

	@RegisterFunction
	fun showGameOver() {
		showMessage("Game over!")
		startButton?.show()
	}

	@RegisterFunction
	fun updateScore(score: Int) {
	   scoreLabel?.text = "$score"
	}

	@RegisterFunction
	fun onStartButtonPressed() {
		startButton?.hide()
		startGame.emit()
	}

	@RegisterFunction
	fun onMessageTimerOut() {
		messageLabel?.hide()
	}
}
