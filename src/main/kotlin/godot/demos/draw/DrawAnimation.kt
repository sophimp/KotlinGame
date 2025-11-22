package godot.demos.draw

import godot.annotation.RegisterClass
import godot.annotation.RegisterFunction
import godot.common.util.TAU
import godot.core.Color
import godot.core.Vector2
import godot.global.GD

@RegisterClass
class DrawAnimation : BaseDraw(){
    var time = 0.0

    @RegisterFunction
    override fun _draw() {
        val margin = Vector2(240, 70)
        val offset = Vector2()
        val lineWidthThin = if (useAntialiasing) 0.5f else -1.0f
        val pointCount = 48
        val progress = GD.wrapf(time,  0.0, 1.0)
        drawArc(
            center = margin + offset,
            radius = 50f,
            startAngle = (0.75 * TAU).toFloat(),
            endAngle = ((0.75 + progress) * TAU).toFloat(),
            pointCount = pointCount,
            color = Color.mediumAquamarine,
            width = lineWidthThin,
            antialiased = useAntialiasing
        )
    }

    @RegisterFunction
    override fun _process(delta: Double) {
        time += delta
        if(isVisibleInTree()){
            queueRedraw()
        }
    }
}