package godot.demos.draw

import godot.annotation.RegisterClass
import godot.annotation.RegisterFunction
import godot.common.util.toRealT
import godot.core.Color
import godot.core.Rect2
import godot.core.Vector2
import godot.global.GD

@RegisterClass
class DrawAnimationSlices : BaseDraw() {

	@RegisterFunction
	override fun _draw() {

		val animationLength = 2.0
		val animationFrames = 10
		repeat(animationFrames) { frame ->
			val sliceBegin = GD.remap(frame.toDouble(), 0.0, animationFrames.toDouble(), 0.0, animationLength)
			val sliceEnd = GD.remap((frame + 1).toDouble(), 0.0, animationFrames.toDouble(), 0.0, animationLength)
			drawAnimationSlice(animationLength, sliceBegin, sliceEnd)
			drawSetTransform(Vector2(200, 300), GD.degToRad(GD.randfRange(-5.0f, 5.0f)).toFloat())
			drawRect(
				rect = Rect2(Vector2(), Vector2(100, 50)),
				color = Color.fromHsv(
					GD.randf().toRealT(), 0.4, 1.0, 1.0),
				filled = true,
				width = -1.0f,
				antialiased = useAntialiasing
			)
		}
		drawEndAnimation()
	}
}
