package godot.demos.draw

import godot.annotation.RegisterClass
import godot.annotation.RegisterFunction
import godot.common.util.TAU
import godot.core.Color
import godot.core.Vector2

@RegisterClass
class DrawLines : BaseDraw() {

	@RegisterFunction
	override fun _draw() {
		val margin = Vector2(20, 50)
		val lineWithThin = if (useAntialiasing) 5f else - 1f
		val antialiasingWidthOffset = if (useAntialiasing) 1f else 0f

		var offset = Vector2()
		val lineLength = Vector2(140, 35)
		drawLine(margin + offset, margin + offset + lineLength, Color.green, lineWithThin, useAntialiasing)
		offset += Vector2(lineLength.x + 15, 0)
		drawLine(
			margin + offset,
			margin + offset + lineLength,
			Color.green,
			2f - antialiasingWidthOffset,
			useAntialiasing
		)
		offset += Vector2(lineLength.x + 15, 0)
		drawLine(
			margin + offset,
			margin + offset + lineLength,
			Color.green,
			6f - antialiasingWidthOffset,
			useAntialiasing
		)
		offset = Vector2(0, 100)
		drawDashedLine(
			margin + offset,
			margin + offset + lineLength,
			Color.cyan,
			lineWithThin,
			5f,
			true,
			useAntialiasing
		)
		offset += Vector2(lineLength.x + 15, 0)
		drawDashedLine(
			margin + offset,
			margin + offset + lineLength,
			Color.cyan,
			2f - antialiasingWidthOffset,
			10f,
			true,
			useAntialiasing
		)
		offset += Vector2(lineLength.x + 15, 0)
		drawDashedLine(
			margin + offset,
			margin + offset + lineLength,
			Color.cyan,
			6f - antialiasingWidthOffset,
			15f,
			true,
			useAntialiasing
		)


		offset = Vector2(40, 220)
		drawCircle(margin + offset, 40f, Color.orange, false, lineWithThin, useAntialiasing)

		offset += Vector2(100, 0)
		drawCircle(margin + offset, 40f, Color.orange, false, 2f - antialiasingWidthOffset, useAntialiasing)

		offset += Vector2(100, 0)
		drawCircle(margin + offset, 40f, Color.orange, false, 6f - antialiasingWidthOffset, useAntialiasing)

		// Draw a filled circle. The width parameter is ignored for filled circles (it's set to `-1.0` to avoid warnings).
		// We also reduce the radius by half the antialiasing width offset.
		// Otherwise, the circle becomes very slightly larger when draw antialiasing is enabled.
		offset = Vector2(40, 320)
		drawCircle(margin + offset, 40f - antialiasingWidthOffset * 0.5f, Color.orange, true, -1f, useAntialiasing)

		// `drawSetTransform()` is a stateful command: it affects *all* `draw_` methods within this
		// `_draw()` function after it. This can be used to translate, rotate, or scale `draw_` methods
		// that don't offer dedicated parameters for this (such as `draw_primitive()` not having a position parameter).
		// To reset back to the initial transform, call `drawSetTransform(Vector2())`.
		//
		// Draw a horizontally stretched circle.
		offset += Vector2(200, 0)
		drawSetTransform(margin + offset, 0f, Vector2(3.0, 1.0))
		drawCircle(Vector2(), 40f, Color.orange, false, lineWithThin, useAntialiasing)
		drawSetTransform(Vector2())

		// Draw a quarter circle (`TAU` represents a full turn in radians).
		val POINT_COUNT_HIGH = 24
		offset = Vector2(40, 420)
		drawArc(
			margin + offset, 60f, 0f,
			(0.25 * TAU).toFloat(), POINT_COUNT_HIGH, Color.yellow, lineWithThin, useAntialiasing
		)

		offset += Vector2(100, 0)
		drawArc(
			margin + offset,
			60f,
			0f,
			(0.25 * TAU).toFloat(),
			POINT_COUNT_HIGH,
			Color.yellow,
			2f - antialiasingWidthOffset,
			useAntialiasing
		)

		offset += Vector2(100, 0)
		drawArc(
			margin + offset,
			60f,
			0f,
			(0.25 * TAU).toFloat(),
			POINT_COUNT_HIGH,
			Color.yellow,
			6.0f - antialiasingWidthOffset,
			useAntialiasing
		)

		// Draw a three quarters of a circle with a low point count, which gives it an angular look.
		val POINT_COUNT_LOW = 7
		offset = Vector2(40, 550)
		drawArc(
			margin + offset,
			40f,
			(-0.25 * TAU).toFloat(),
			(0.5 * TAU).toFloat(),
			POINT_COUNT_LOW,
			Color.yellow,
			lineWithThin,
			useAntialiasing
		)

		offset += Vector2(100, 0)
		drawArc(
			margin + offset,
			40f,
			(-0.25 * TAU).toFloat(),
			(0.5 * TAU).toFloat(),
			POINT_COUNT_LOW,
			Color.yellow,
			2.0f - antialiasingWidthOffset,
			useAntialiasing
		)

		offset += Vector2(100, 0)
		drawArc(
			margin + offset,
			40f,
			(-0.25 * TAU).toFloat(),
			(0.5 * TAU).toFloat(),
			POINT_COUNT_LOW,
			Color.yellow,
			6.0f - antialiasingWidthOffset,
			useAntialiasing
		)

		// Draw a horizontally stretched arc.
		offset = Vector2(40, 670)
		drawSetTransform(margin + offset, 0f, Vector2(3.0, 1.0))
		drawArc(Vector2(), 40f,
			(-0.25 * TAU).toFloat(), (0.5 * TAU).toFloat(), POINT_COUNT_LOW, Color.yellow, lineWithThin, useAntialiasing)
		drawSetTransform(Vector2())
	}
}
