package godot.demos.draw

import godot.annotation.RegisterClass
import godot.annotation.RegisterFunction
import godot.api.Control
import godot.api.TabContainer
import godot.api.Viewport

@RegisterClass
class CustomDrawing : Control(){

	@RegisterFunction
	fun onMsaa2dItemSelected(index:Int) {
		getViewport()?.msaa2d = Viewport.MSAA.from(index.toLong())
	}

	@RegisterFunction
	fun onDrawAntialiasingToggled(toggleOn: Boolean) {
		val tabContainer = getNode("TabContainer") as TabContainer
		val nodes = tabContainer.getChildren()
		getNode("Animation/AnimationSlices")?.let {
			nodes.pushBack(it)
		}
		nodes.forEach {
			(it as BaseDraw).useAntialiasing = toggleOn
			it.queueRedraw()
		}
	}
}
