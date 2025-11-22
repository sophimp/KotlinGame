package godot.demos.draw

import godot.annotation.RegisterClass
import godot.annotation.RegisterFunction
import godot.api.StyleBoxFlat
import godot.core.Color
import godot.core.Rect2
import godot.core.Vector2
import godot.global.GD

@RegisterClass
class DrawRectangles : BaseDraw(){

	@RegisterFunction
	override fun _draw() {
		val margin = Vector2(20, 40)

		// Line width of `-1.0` is only usable with draw antialiasing disabled,
		// as it uses hardware line drawing as opposed to polygon-based line drawing.
		// Automatically use polygon-based line drawing when needed to avoid runtime warnings.
		// We also use a line width of `0.5` instead of `1.0` to better match the appearance
		// of non-antialiased line drawing, as draw antialiasing tends to make lines look thicker.
		val line_width_thin = if(useAntialiasing) 0.5f else -1.0f

		// Make thick lines 1 pixel thinner when draw antialiasing is enabled,
		// as draw antialiasing tends to make lines look thicker.
		val antialiasing_width_offset = if(useAntialiasing) 1.0f  else 0.0f

		var offset = Vector2()
		drawRect(
			Rect2(margin + offset, Vector2(100, 50)),
			Color.purple,
			false,
			line_width_thin,
			useAntialiasing
		)

		offset += Vector2(120, 0)
		drawRect(
			Rect2(margin + offset, Vector2(100, 50)),
			Color.purple,
			false,
			2.0f - antialiasing_width_offset,
			useAntialiasing
		)

		offset += Vector2(120, 0)
		drawRect(
			Rect2(margin + offset, Vector2(100, 50)),
			Color.purple,
			false,
			6.0f - antialiasing_width_offset,
			useAntialiasing
		)

		// Draw a filled rectangle. The width parameter is ignored for filled rectangles (it's set to `-1.0` to avoid warnings).
		// We also reduce the rectangle's size by half the antialiasing width offset.
		// Otherwise, the rectangle becomes very slightly larger when draw antialiasing is enabled.
		offset = Vector2(120, 60)
		drawRect(
			Rect2(margin + offset, Vector2(100, 50)).grow(-antialiasing_width_offset * 0.5),
			Color.purple,
			true,
			-1.0f,
			useAntialiasing
		)

		// `drawSetTransform()` is a stateful command: it affects *all* `draw_` methods within this
		// `_draw()` function after it. This can be used to translate, rotate, or scale `draw_` methods
		// that don't offer dedicated parameters for this (such as `drawRect()` not having a rotation parameter).
		// To reset back to the initial transform, call `drawSetTransform(Vector2())`.
		offset += Vector2(170, 0)
		drawSetTransform(margin + offset, GD.degToRad(22.5).toFloat())
		drawRect(
			Rect2(Vector2(), Vector2(100, 50)),
			Color.purple,
			false,
			line_width_thin,
			useAntialiasing
		)
		offset += Vector2(120, 0)
		drawSetTransform(margin + offset, GD.degToRad(22.5).toFloat())
		drawRect(
			Rect2(Vector2(), Vector2(100, 50)),
			Color.purple,
			false,
			2.0f - antialiasing_width_offset,
			useAntialiasing
		)
		offset += Vector2(120, 0)
		drawSetTransform(margin + offset, GD.degToRad(22.5).toFloat())
		drawRect(
			Rect2(Vector2(), Vector2(100, 50)),
			Color.purple,
			false,
			6.0f - antialiasing_width_offset,
			useAntialiasing
		)

		// `drawSetTransform_matrix()` is a more advanced counterpart of `drawSetTransform()`.
		// It can be used to apply transforms that are not supported by `drawSetTransform()`, such as
		// skewing.
		offset = Vector2(20, 60)
		var custom_transform = getTransform().translated(margin + offset)
		// Perform horizontal skewing (the rectangle will appear "slanted").
		custom_transform.y.x -= 0.5
		drawSetTransformMatrix(custom_transform)
		drawRect(
			Rect2(Vector2(), Vector2(100, 50)),
			Color.purple,
			false,
			6.0f - antialiasing_width_offset,
			useAntialiasing
		)
		drawSetTransform(Vector2())

		offset = Vector2(0, 250)
		val style_box_flat = StyleBoxFlat()
		style_box_flat.setBorderWidthAll(4)
		style_box_flat.setCornerRadiusAll(8)
		style_box_flat.shadowSize = 1
		style_box_flat.shadowOffset = Vector2(4, 4)
		style_box_flat.shadowColor = Color.red
		style_box_flat.antiAliasing = useAntialiasing
		drawStyleBox(style_box_flat, Rect2(margin + offset, Vector2(100, 50)))

		offset += Vector2(130, 0)
		val style_box_flat_2 = StyleBoxFlat()
		style_box_flat_2.drawCenter = false
		style_box_flat_2.setBorderWidthAll(4)
		style_box_flat_2.setCornerRadiusAll(8)
		style_box_flat_2.cornerDetail = 1
		style_box_flat_2.borderColor = Color.green
		style_box_flat_2.antiAliasing = useAntialiasing
		drawStyleBox(style_box_flat_2, Rect2(margin + offset, Vector2(100, 50)))

		offset += Vector2(160, 0)
		val style_box_flat_3 = StyleBoxFlat()
		style_box_flat_3.drawCenter = false
		style_box_flat_3.setBorderWidthAll(4)
		style_box_flat_3.setCornerRadiusAll(8)
		style_box_flat_3.borderColor = Color.cyan
		style_box_flat_3.shadowSize = 40
		style_box_flat_3.shadowOffset = Vector2()
		style_box_flat_3.shadowColor = Color.cornflowerBlue
		style_box_flat_3.antiAliasing = useAntialiasing
		custom_transform = getTransform().translated(margin + offset)
		// Perform vertical skewing (the rectangle will appear "slanted").
		custom_transform.x.y -= 0.5
		drawSetTransformMatrix(custom_transform)
		drawStyleBox(style_box_flat_3, Rect2(Vector2(), Vector2(100, 50)))

		drawSetTransform(Vector2())
	}
}
