package godot.demos.draw

import godot.annotation.RegisterClass
import godot.annotation.RegisterFunction
import godot.api.Texture2D
import godot.core.Color
import godot.core.Rect2
import godot.core.Vector2
import godot.global.GD

@RegisterClass
class DrawTextures : BaseDraw(){
    @RegisterFunction
    override fun _draw() {
        val ICON = GD.load<Texture2D>("icon.svg")
        val margin = Vector2(20, 40)

        var offset = Vector2()
        // Draw a texture.
        drawTexture(ICON, margin + offset, Color.white)

        // `drawSetTransform()` is a stateful command: it affects *all* `draw_` methods within this
        // `_draw()` function after it. This can be used to translate, rotate, or scale `draw_` methods
        // that don't offer dedicated parameters for this (such as `draw_rect()` not having a rotation parameter).
        // To reset back to the initial transform, call `drawSetTransform(Vector2())`.
        //
        // Draw a rotated texture at half the scale of its original pixel size.
        offset += Vector2(200, 20)
        drawSetTransform(margin + offset, GD.degToRad(45.0).toFloat(), Vector2(0.5, 0.5))
        drawTexture(ICON, Vector2(), Color.white)
        drawSetTransform(Vector2())

        // Draw a stretched texture. In this example, the icon is 128×128 so it will be drawn at 2× scale.
        offset += Vector2(70, -20)
        drawTextureRect(
            ICON,
            Rect2(margin + offset, Vector2(256, 256)),
            false,
            Color.green
        )


        // Draw a tiled texture. In this example, the icon is 128×128 so it will be drawn twice on each axis.
        offset = Vector2(0, 150)
        drawTextureRect(
            ICON,
            Rect2(margin + offset, Vector2(256, 256)),
            true,
            Color.green
        )

        offset = Vector2(0, 450)

        drawTextureRectRegion(
            ICON,
            Rect2(margin + offset, Vector2(128, 128)),
            Rect2(Vector2(32, 32), Vector2(64, 64)),
            Color.violet
        )

        // Draw a tiled texture from a region that is larger than the original texture size (128×128).
        // Transposing is enabled, which will rotate the image by 90 degrees counter-clockwise.
        // (For more advanced transforms, use `drawSetTransform()` before calling `drawTextureRectRegion()`.)
        //
        // For tiling to work with this approach, the CanvasItem in which this `_draw()` method is called
        // must have its Repeat property set to Enabled in the inspector.
        offset += Vector2(140, 0)
        drawTextureRectRegion(
            ICON,
            Rect2(margin + offset, Vector2(128, 128)),
            Rect2(Vector2(), Vector2(512, 512)),
            Color.violet,
            true
        )
    }
}
