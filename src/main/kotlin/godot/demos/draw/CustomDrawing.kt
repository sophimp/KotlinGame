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
		val tabContainer = getNode("tabContainer") as TabContainer
		val nodes = tabContainer.getChildren()
//        nodes.pushBack(DrawAnimationSlices())
		nodes.forEach {
//            (it as Control).useAntialiasing = toggleOn
			(it as Control).useParentMaterial = toggleOn
			it.queueRedraw()
		}
	}
}
