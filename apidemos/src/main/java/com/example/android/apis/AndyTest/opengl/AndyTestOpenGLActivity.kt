package com.example.android.apis.AndyTest.opengl

import android.app.Activity
import android.content.Context
import android.graphics.PixelFormat
import android.opengl.GLSurfaceView
import android.os.Bundle
import android.util.DisplayMetrics
import android.view.MotionEvent
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.SeekBar
import com.example.android.apis.R
import com.example.android.apis.graphics.Cube
import kotlinx.android.synthetic.main.activity_andy_test_open_gl.*
import org.jetbrains.anko.sdk25.coroutines.onCheckedChange
import org.jetbrains.anko.sdk25.coroutines.onClick
import org.jetbrains.anko.windowManager
import javax.microedition.khronos.egl.EGLConfig
import javax.microedition.khronos.opengles.GL10

val ProgressMax = 1000f

class AndyTestOpenGLActivity : Activity() {
    private lateinit var mGLSurfaceView: TouchSurfaceView

    enum class TestMode {
        Translate,
        Rotate
    }

    var testMode = TestMode.Translate
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_andy_test_open_gl)

        initSurfaceView()

        initListeners()

        mGLSurfaceView.testMode = testMode
    }

    private fun initSurfaceView() {
        mGLSurfaceView = TouchSurfaceView(this)
        mGLSurfaceView.requestFocus()
        mGLSurfaceView.isFocusableInTouchMode = true
        mGLSurfaceView.onChangeListener = object : TouchSurfaceView.OnChange {
            override fun onChange(x: Float, y: Float, z: Float, rx: Float, ry: Float, rz: Float) {
                seekBar1TV.text = "$x"
                seekBar1_2TV.text = "$rx"
                seekBar1.progress = if (testMode == TestMode.Translate) x.toInt() else rx.toInt()

                seekBar2TV.text = "$y"
                seekBar2_2TV.text = "$ry"
                seekBar2.progress = if (testMode == TestMode.Translate) y.toInt() else ry.toInt()

                seekBar3TV.text = "$z"
                seekBar3_2TV.text = "$rz"
                seekBar3.progress = if (testMode == TestMode.Translate) z.toInt() else rz.toInt()
            }

        }

        //        mGLSurfaceView = GLSurfaceView(this)
        //        // We want an 8888 pixel format because that's required for
        //        // a translucent window.
        //        // And we want a depth buffer.
        //        mGLSurfaceView.setEGLConfigChooser(8, 8, 8, 8, 16, 0)
        //        // Tell the cube renderer that we want to render a translucent version
        //        // of the cube:
        //        mGLSurfaceView.setRenderer(CubeRenderer(true))
        //        // Use a surface format with an Alpha channel:
        //        mGLSurfaceView.holder.setFormat(PixelFormat.TRANSLUCENT)

        var layoutParams: FrameLayout.LayoutParams = FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)
        glContainer.addView(mGLSurfaceView, layoutParams)
    }

    private fun initListeners() {
        seekBar1.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                mGLSurfaceView.testX(progress.toFloat(), testMode)
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {
            }

            override fun onStopTrackingTouch(seekBar: SeekBar?) {
            }
        })
        seekBar2.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                mGLSurfaceView.testY(progress.toFloat(), testMode)
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {
            }

            override fun onStopTrackingTouch(seekBar: SeekBar?) {
            }
        })
        seekBar3.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                mGLSurfaceView.testZ(progress.toFloat(), testMode)
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {
            }

            override fun onStopTrackingTouch(seekBar: SeekBar?) {
            }
        })

        resetBtn.onClick {
            mGLSurfaceView.reset()
        }
        modeBtn.onCheckedChange { _, isChecked ->
            if (isChecked) {
                testMode = TestMode.Rotate
            } else {
                testMode = TestMode.Translate
            }
            mGLSurfaceView.testMode = testMode
        }
    }


    override fun onResume() {
        super.onResume()
        mGLSurfaceView.onResume()
    }

    override fun onPause() {
        super.onPause()
        mGLSurfaceView.onPause()
    }

    internal class TouchSurfaceView(context: Context) : GLSurfaceView(context) {

        private val TOUCH_SCALE_FACTOR by lazy {
            val dm = DisplayMetrics()
            context.windowManager.defaultDisplay.getMetrics(dm)
            1.0f / dm.widthPixels
        }
        private val TRACKBALL_SCALE_FACTOR = 36.0f
        private val mRenderer: CubeRenderer
        private var mPreviousX: Float = 0.toFloat()
        private var mPreviousY: Float = 0.toFloat()

        init {
            setEGLConfigChooser(8, 8, 8, 8, 16, 0)
            // Use a surface format with an Alpha channel:
            holder.setFormat(PixelFormat.TRANSLUCENT)

            mRenderer = CubeRenderer()
            setRenderer(mRenderer)
            renderMode = GLSurfaceView.RENDERMODE_WHEN_DIRTY

        }

        interface OnChange {
            fun onChange(x: Float, y: Float, z: Float, rx: Float, ry: Float, rz: Float)
        }

        var onChangeListener: OnChange? = null

        private fun notifyChange() {
//            onChangeListener?.onChange((mRenderer.mAngleX % 360 + 360) % 360, (mRenderer.mAngleY % 360 + 360) % 360, (mRenderer.mAngleZ % 360 + 360) % 360)
            onChangeListener?.onChange(
                    (mRenderer.mTranslateX + 2) * ProgressMax / 4, (mRenderer.mTranslateY + 2) * ProgressMax / 4, (mRenderer.mTranslateZ + 8) * ProgressMax / 10,
                    (mRenderer.mAngleX % 360 + 360) % 360 * ProgressMax / 360, (mRenderer.mAngleY % 360 + 360) % 360 * ProgressMax / 360, (mRenderer.mAngleZ % 360 + 360) % 360 * ProgressMax / 360
            )
        }

        var testMode: TestMode = TestMode.Translate
            set(value) {
                field = value
                notifyChange()
            }

        fun testX(x: Float, testMode: TestMode) {
            this.testMode = testMode
            if (testMode == TestMode.Translate)
                mRenderer.mTranslateX = x * 4 / ProgressMax - 2
            else
                mRenderer.mAngleX = x * 360 / ProgressMax
            requestRender()
            notifyChange()
        }

        fun testY(y: Float, testMode: TestMode) {
            if (testMode == TestMode.Translate)
                mRenderer.mTranslateY = y * 4 / ProgressMax - 2
            else
                mRenderer.mAngleY = y * 360 / ProgressMax
            requestRender()
            notifyChange()
        }

        fun testZ(z: Float, testMode: TestMode) {
            if (testMode == TestMode.Translate)
                mRenderer.mTranslateZ = z * 10 / ProgressMax - 8
            else
                mRenderer.mAngleZ = z * 360 / ProgressMax
            requestRender()
            notifyChange()
        }

        fun reset() {
            mRenderer.mAngleX = 0f
            mRenderer.mAngleY = 0f
            mRenderer.mAngleZ = 0f
            mRenderer.mTranslateX = 0f
            mRenderer.mTranslateY = 0f
            mRenderer.mTranslateZ = -3f
            requestRender()
            notifyChange()
        }

        override fun onTrackballEvent(e: MotionEvent): Boolean {
            mRenderer.mAngleX += e.x * TRACKBALL_SCALE_FACTOR
            mRenderer.mAngleY += e.y * TRACKBALL_SCALE_FACTOR
            requestRender()
            notifyChange()
            return true
        }

        override fun onTouchEvent(e: MotionEvent): Boolean {
            val x = e.x
            val y = e.y
            when (e.action) {
                MotionEvent.ACTION_MOVE -> {
                    val dx = x - mPreviousX
                    val dy = y - mPreviousY
                    if (testMode == TestMode.Rotate) {
                        mRenderer.mAngleX += dx * TOUCH_SCALE_FACTOR * 180
                        mRenderer.mAngleY += dy * TOUCH_SCALE_FACTOR * 180
                    } else {
                        mRenderer.mTranslateX += dx * TOUCH_SCALE_FACTOR * 3
                        mRenderer.mTranslateY -= dy * TOUCH_SCALE_FACTOR * 3
                    }
                    requestRender()
                }
            }
            mPreviousX = x
            mPreviousY = y
            notifyChange()
            return true
        }

        /**
         * Render a cube.
         */
        private inner class CubeRenderer : GLSurfaceView.Renderer {
            var mAngleX: Float = 0.toFloat()
            var mAngleY: Float = 0.toFloat()
            var mAngleZ: Float = 0.toFloat()

            var mTranslateX: Float = 0.toFloat()
            var mTranslateY: Float = 0.toFloat()
            var mTranslateZ: Float = -3.toFloat()
            private val mCube: Cube

            init {
                mCube = Cube()
            }


            override fun onDrawFrame(gl: GL10) {
                /*
             * Usually, the first thing one might want to do is to clear
             * the screen. The most efficient way of doing this is to use
             * glClear().
             */

                gl.glClear(GL10.GL_COLOR_BUFFER_BIT or GL10.GL_DEPTH_BUFFER_BIT)

                /*
             * Now we're ready to draw some 3D objects
             */

                gl.glMatrixMode(GL10.GL_MODELVIEW)
                gl.glLoadIdentity()
                gl.glTranslatef(mTranslateX, mTranslateY, mTranslateZ)
                gl.glRotatef(mAngleX, 0f, 1f, 0f)
                gl.glRotatef(mAngleY, 1f, 0f, 0f)
                gl.glRotatef(mAngleZ, 0f, 0f, 1f)

                gl.glEnableClientState(GL10.GL_VERTEX_ARRAY)
                gl.glEnableClientState(GL10.GL_COLOR_ARRAY)

                mCube.draw(gl)
            }

            override fun onSurfaceChanged(gl: GL10, width: Int, height: Int) {
                gl.glViewport(0, 0, width, height)

                /*
              * Set our projection matrix. This doesn't have to be done
              * each time we draw, but usually a new projection needs to
              * be set when the viewport is resized.
              */

                val ratio = width.toFloat() / height
                gl.glMatrixMode(GL10.GL_PROJECTION)
                gl.glLoadIdentity()
                gl.glFrustumf(-ratio, ratio, -1f, 1f, 1f, 10f)
            }

            override fun onSurfaceCreated(gl: GL10, config: EGLConfig) {
                /*
             * By default, OpenGL enables features that improve quality
             * but reduce performance. One might want to tweak that
             * especially on software renderer.
             */
                gl.glDisable(GL10.GL_DITHER)

                /*
             * Some one-time OpenGL initialization can be made here
             * probably based on features of this particular context
             */
                gl.glHint(GL10.GL_PERSPECTIVE_CORRECTION_HINT,
                        GL10.GL_FASTEST)

                gl.glClearColor(0f, 0f, 0f, 0f)
//                gl.glClearColor(1f, 1f, 1f, 1f)
                gl.glEnable(GL10.GL_CULL_FACE)
                gl.glShadeModel(GL10.GL_SMOOTH)
                gl.glEnable(GL10.GL_DEPTH_TEST)
            }
        }
    }
}
