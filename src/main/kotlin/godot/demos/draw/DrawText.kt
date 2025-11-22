package godot.demos.draw

import godot.annotation.RegisterClass
import godot.annotation.RegisterFunction
import godot.api.TextServerManager
import godot.common.util.toRealT
import godot.core.Color
import godot.core.HorizontalAlignment
import godot.core.Vector2
import godot.global.GD

@RegisterClass
class DrawText : BaseDraw(){
	@RegisterFunction
	override fun _draw() {
		var font = getThemeDefaultFont()
		val FONT_SIZE = 24
		val STRING = "Hello world!"
		var margin = Vector2(240, 60)

		var offset = Vector2()
		var advance = Vector2()
		for (character in STRING) {
			// Draw each character with a random pastel color.
			// Notice how the advance calculated on the loop's previous iteration is used as an offset here.
			drawChar(font, margin + offset + advance, character.toString(), FONT_SIZE, Color.fromHsv(GD.randf().toRealT(), 0.4, 1.0, 1.0))

			// Get the glyph index of the character we've just drawn, so we can retrieve the glyph advance.
			// This determines the spacing between glyphs so the next character is positioned correctly.
			var glyph_idx = TextServerManager.getPrimaryInterface()!!.fontGetGlyphIndex(
				getThemeDefaultFont()!!.getRids()[0],
				FONT_SIZE.toLong(),
				character.code.toLong(),
				0
			)
			advance.x += TextServerManager.getPrimaryInterface()!!.fontGetGlyphAdvance(
				getThemeDefaultFont()!!.getRids()[0],
				FONT_SIZE.toLong(),
				glyph_idx
			).x
		}

		offset += Vector2(0, 32)
		// When drawing a font outline, it must be drawn *before* the main text.
		// This way, the outline appears behind the main text.
		drawStringOutline(
			font,
			margin + offset,
			STRING,
			HorizontalAlignment.LEFT,
			-1f,
			FONT_SIZE,
			12,
			Color.orange.darkened(0.6)
		)
		// NOTE: Use `draw_multiline_string()` to draw strings that contain line breaks (`\n`) or with
		// automatic line wrapping based on the specified width.
		// A width of `-1` is used here, which means "no limit". If width is limited, the end of the string
		// will be cut off if it doesn't fit within the specified width.
		drawString(
			font,
			margin + offset,
			STRING,
			HorizontalAlignment.LEFT,
			-1f,
			FONT_SIZE,
			Color.yellow
		)
	}
}
