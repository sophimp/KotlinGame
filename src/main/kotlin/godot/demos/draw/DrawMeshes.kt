package godot.demos.draw

import godot.annotation.RegisterClass
import godot.annotation.RegisterFunction
import godot.api.FastNoiseLite
import godot.api.Gradient
import godot.api.GradientTexture2D
import godot.api.MultiMesh
import godot.api.NoiseTexture2D
import godot.api.SphereMesh
import godot.api.TextMesh
import godot.api.Texture2D
import godot.core.Color
import godot.core.Transform2D
import godot.core.Vector2

@RegisterClass()
class DrawMeshes : BaseDraw(){
	val textMesh = TextMesh()
	val noiseTexture = NoiseTexture2D()
	var gradientTexture = GradientTexture2D()
	val sphereMesh = SphereMesh()
	val multiMesh = MultiMesh()

	@RegisterFunction
	override fun _ready() {
		textMesh.text = "Text Mesh"
		textMesh.pixelSize = 2.5f
		noiseTexture.seamless = true
		noiseTexture.asNormalMap = true
		noiseTexture.noise = FastNoiseLite()

		gradientTexture.gradient = Gradient()
		sphereMesh.height = 80f
		sphereMesh.radius = 40f

		multiMesh.apply {
			useColors = true
			instanceCount = 5
			setInstanceTransform2d(0, Transform2D(0, Vector2(0, 0)))
			setInstanceColor(0, Color(1,0.7,0.7,1))
			setInstanceTransform2d(1, Transform2D(0, Vector2(0, 100)))
			setInstanceColor(1, Color(0.7,1,0.7,1))
			setInstanceTransform2d(2, Transform2D(0, Vector2(100, 100)))
			setInstanceColor(2, Color(0.7,0.7,1, 1))
			setInstanceTransform2d(3, Transform2D(0, Vector2(100, 0)))
			setInstanceColor(3, Color(1,1,0.7,1))
			setInstanceTransform2d(4, Transform2D(0, Vector2(50, 50)))
			setInstanceColor(4, Color(0.7,0.7,1, 1))
			mesh = sphereMesh
		}
	}

	@RegisterFunction
	override fun _draw() {
		val margin = Vector2(300, 70)
		var offset = Vector2()
		drawSetTransform(margin + offset, 0.0f, Vector2(1, -1))
		drawMesh(textMesh, noiseTexture)

		offset += Vector2(0, 150)
		drawSetTransform(margin + offset)
		drawMesh(sphereMesh, noiseTexture)

		offset += Vector2(0, 300)
		drawSetTransform(margin + offset)
		drawMultimesh(multiMesh, gradientTexture)
	}
}
