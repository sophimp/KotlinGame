package godot

import godot.annotation.RegisterClass
import godot.annotation.RegisterFunction
import godot.annotation.RegisterProperty
import godot.api.Label

@RegisterClass
class CommonLabel() : Label(){

	@RegisterProperty
	var count = 0
	@RegisterFunction
	override fun _ready() {
	}
}
