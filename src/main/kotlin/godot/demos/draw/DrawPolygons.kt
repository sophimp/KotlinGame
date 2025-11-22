package godot.demos.draw

import godot.annotation.RegisterClass
import godot.annotation.RegisterFunction
import godot.core.Color
import godot.core.PackedColorArray
import godot.core.PackedVector2Array
import godot.core.Vector2
import godot.core.variantArrayOf

@RegisterClass
class DrawPolygons : BaseDraw() {

	@RegisterFunction
	override fun _draw() {
		val margin = Vector2(40, 40)

		// Line width of `-1.0` is only usable with draw antialiasing disabled,
		// as it uses hardware line drawing as opposed to polygon-based line drawing.
		// Automatically use polygon-based line drawing when needed to avoid runtime warnings.
		// We also use a line width of `0.5` instead of `1.0` to better match the appearance
		// of non-antialiased line drawing, as draw antialiasing tends to make lines look thicker.
		val line_width_thin = if (useAntialiasing) 0.5f else -1.0f

		// Make thick lines 1 pixel thinner when draw antialiasing is enabled,
		// as draw antialiasing tends to make lines look thicker.
		val antialiasing_width_offset = if (useAntialiasing) 1.0f else 0.0f

		val points = PackedVector2Array(
			arrayOf(
				Vector2(0, 0),
				Vector2(0, 60),
				Vector2(60, 90),
				Vector2(60, 0),
				Vector2(40, 25),
				Vector2(10, 40),
			)
		)
		val colors = PackedColorArray(
			variantArrayOf(
				Color.white,
				Color.red,
				Color.green,
				Color.blue,
				Color.magenta,
				Color.magenta,
			)
		)

		var offset = Vector2()
		// `drawSetTransform()` is a stateful command: it affects *all* `draw_` methods within this
		// `_draw()` function after it. This can be used to translate, rotate, or scale `draw_` methods
		// that don't offer dedicated parameters for this (such as `drawPrimitive()` not having a position parameter).
		// To reset back to the initial transform, call `drawSetTransform(Vector2())`.
		drawSetTransform(margin + offset)
		drawPrimitive(points.slice(0, 1), colors.slice(0, 1), PackedVector2Array())

		offset += Vector2(90, 0)
		drawSetTransform(margin + offset)
		drawPrimitive(points.slice(0, 2), colors.slice(0, 2), PackedVector2Array())

		offset += Vector2(90, 0)
		drawSetTransform(margin + offset)
		drawPrimitive(points.slice(0, 3), colors.slice(0, 3), PackedVector2Array())

		offset += Vector2(90, 0)
		drawSetTransform(margin + offset)
		drawPrimitive(points.slice(0, 4), colors.slice(0, 4), PackedVector2Array())

		// Draw a polygon with multiple colors that are interpolated between each point.
		// Colors are specified in the same order as the points' positions, but in a different array.
		offset = Vector2(0, 120)
		drawSetTransform(margin + offset)
		drawPolygon(points, colors)

		// Draw a polygon with a single color. Only a points array is needed in this case.
		offset += Vector2(90, 0)
		drawSetTransform(margin + offset)
		drawColoredPolygon(points, Color.yellow)

		// Draw a polygon-based line. Each segment is connected to the previous one, which means
		// `drawPolyline()` always draws a contiguous line.
		offset = Vector2(0, 240)
		drawSetTransform(margin + offset)
		drawPolyline(points, Color.skyBlue, line_width_thin, useAntialiasing)

		offset += Vector2(90, 0)
		drawSetTransform(margin + offset)
		drawPolyline(points, Color.skyBlue, 2.0f - antialiasing_width_offset, useAntialiasing)

		offset += Vector2(90, 0)
		drawSetTransform(margin + offset)
		drawPolyline(points, Color.skyBlue, 6.0f - antialiasing_width_offset, useAntialiasing)

		offset += Vector2(90, 0)
		drawSetTransform(margin + offset)
		drawPolylineColors(points, colors, line_width_thin, useAntialiasing)

		offset += Vector2(90, 0)
		drawSetTransform(margin + offset)
		drawPolylineColors(points, colors, 2.0f - antialiasing_width_offset, useAntialiasing)

		offset += Vector2(90, 0)
		drawSetTransform(margin + offset)
		drawPolylineColors(points, colors, 6.0f - antialiasing_width_offset, useAntialiasing)

		// Draw multiple lines in a single draw command. Unlike `drawPolyline()`,
		// lines are not connected to the last segment.
		// This is faster than calling `draw_line()` several times and should be preferred
		// when drawing dozens of lines or more at once.
		offset = Vector2(0, 360)
		drawSetTransform(margin + offset)
		drawMultiline(points, Color.skyBlue, line_width_thin, useAntialiasing)

		offset += Vector2(90, 0)
		drawSetTransform(margin + offset)
		drawMultiline(points, Color.skyBlue, 2.0f - antialiasing_width_offset, useAntialiasing)

		offset += Vector2(90, 0)
		drawSetTransform(margin + offset)
		drawMultiline(points, Color.skyBlue, 6.0f - antialiasing_width_offset, useAntialiasing)

		// `drawMultilineColors()` makes it possible to draw lines of different colors in a single
		// draw command, although gradients are not possible this way (unlike `drawPolygon()` and `drawPolyline()`).
		// This means the number of supplied colors must be equal to the number of segments, which means
		// we have to only pass a subset of the colors array in this example.
		offset += Vector2(90, 0)
		drawSetTransform(margin + offset)
		drawMultilineColors(points, colors.slice(0, 3), line_width_thin, useAntialiasing)

		offset += Vector2(90, 0)
		drawSetTransform(margin + offset)
		drawMultilineColors(points, colors.slice(0, 3), 2.0f - antialiasing_width_offset, useAntialiasing)

		offset += Vector2(90, 0)
		drawSetTransform(margin + offset)
		drawMultilineColors(points, colors.slice(0, 3), 6.0f - antialiasing_width_offset, useAntialiasing)
	}
}
