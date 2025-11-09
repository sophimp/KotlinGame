package godot

import godot.annotation.RegisterClass
import godot.annotation.RegisterFunction
import godot.api.Button
import godot.global.GD

@RegisterClass
class ButtonTest : Button(){
	private var count = 0

	@RegisterFunction
	fun onButtonDown() {
		count++
		GD.print("button down: $count")
	}
}
