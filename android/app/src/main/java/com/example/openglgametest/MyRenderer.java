package com.example.openglgametest;

import android.content.Context;
import android.opengl.GLSurfaceView;
import android.opengl.GLU;
import android.view.MotionEvent;
import android.view.View;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.nio.ShortBuffer;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

public class MyRenderer implements GLSurfaceView.Renderer {

    private Context mContext;
    private FloatBuffer mVertexBuffer = null;
    private ShortBuffer mTriangleBorderIndicesBuffer = null;
    private int mNumOfTriangleBorderIndices = 0;
    public static float mAngleX = 0.0f;
    public static float mAngleY = 0.0f;
    public float mAngleZ = 0.0f;
    private float mPreviousX;
    private float mPreviousY;
    private final float TOUCH_SCALE_FACTOR = 0.6f;

    public MyRenderer(Context context) {
        mContext = context;
    }


    private void setAllBuffers(){
        float vertices[] = {
                -0.5f, 0.5f, 0.5f, // 0
                0.5f, 0.5f, 0.5f, // 1
                0.5f, -0.5f, 0.5f, // 2
                -0.5f, -0.5f, 0.5f, // 3

                -0.5f, 0.5f, -0.5f, // 4
                0.5f, 0.5f, -0.5f, // 5
                0.5f, -0.5f, -0.5f, // 6
                -0.5f, -0.5f, -0.5f, // 7
        };
        short faces[] = {
                // front face
                0, 1, 2,
                2, 3, 0,
                // back face
                4, 5, 6,
                6, 7, 4,
                // right face
                1, 5, 6,
                6, 2, 1,
                // left face
                0, 4, 7,
                7, 3, 0,
                // top face
                4, 5, 1,
                1, 0, 4,
                // bottom face
                7, 6, 2,
                2, 3, 7
        };
        ByteBuffer vbb = ByteBuffer.allocateDirect(vertices.length * 4);
        vbb.order(ByteOrder.nativeOrder());
        mVertexBuffer = vbb.asFloatBuffer();
        mVertexBuffer.put(vertices);
        mVertexBuffer.position(0);
        mNumOfTriangleBorderIndices = faces.length;
        ByteBuffer tbibb = ByteBuffer.allocateDirect(faces.length * 2);
        tbibb.order(ByteOrder.nativeOrder());
        mTriangleBorderIndicesBuffer = tbibb.asShortBuffer();
        mTriangleBorderIndicesBuffer.put(faces);
        mTriangleBorderIndicesBuffer.position(0);
    }

    public boolean onTouchEvent(MotionEvent e) {
        float x = e.getX();
        float y = e.getY();
        switch (e.getAction()){
            case MotionEvent.ACTION_MOVE:
                float dx = x - mPreviousX;
                float dy = y - mPreviousY;
                mAngleY = (mAngleY + (int)(dx * TOUCH_SCALE_FACTOR) + 360) % 360;
                mAngleX = (mAngleX + (int)(dy * TOUCH_SCALE_FACTOR) + 360) % 360;
                break;
        }
        mPreviousX = x;
        mPreviousY = y;
        return true;
    }

    @Override
    public void onSurfaceCreated(GL10 gl, EGLConfig eglConfig) {
        gl.glClearColor(0.0f, 0.0f, 0.0f, 0.0f);
        gl.glHint(GL10.GL_PERSPECTIVE_CORRECTION_HINT, GL10.GL_NICEST);
        gl.glEnable(GL10.GL_DEPTH_TEST);
        gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);
        setAllBuffers();
    }

    @Override
    public void onSurfaceChanged(GL10 gl, int width, int height) {
        gl.glViewport(0, 0, width, height);
        float aspect = (float)width/height;
        gl.glMatrixMode(GL10.GL_PROJECTION);
        gl.glLoadIdentity();
        gl.glFrustumf(-aspect, aspect, -1.0f, 1.0f, 1.0f, 10.0f);
    }

    @Override
    public void onDrawFrame(GL10 gl) {
        gl.glClear(GL10.GL_COLOR_BUFFER_BIT | GL10.GL_DEPTH_BUFFER_BIT);
        gl.glMatrixMode(GL10.GL_MODELVIEW);
        gl.glLoadIdentity();
        gl.glTranslatef(0.0f, 0.0f, -3.0f);
        gl.glRotatef(mAngleX, 1, 0, 0);
        gl.glRotatef(mAngleY, 0, 1, 0);
        gl.glRotatef(mAngleZ, 0, 0, 1);
        gl.glVertexPointer(3, GL10.GL_FLOAT, 0, mVertexBuffer);
        gl.glColor4f(0.5f, 0.5f, 0.9f, 1.0f);
        gl.glDrawElements(
                GL10.GL_TRIANGLES,
                mNumOfTriangleBorderIndices,
                GL10.GL_UNSIGNED_SHORT,
                mTriangleBorderIndicesBuffer
        );
    }
}
