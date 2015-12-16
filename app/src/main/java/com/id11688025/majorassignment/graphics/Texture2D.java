package com.id11688025.majorassignment.graphics;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.opengl.GLES20;
import android.opengl.GLUtils;

import com.id11688025.majorassignment.ContentManager;

/**
 * A structure that defines a two-dimensional texture,
 * and its OpenGL texture name.
 */
public class Texture2D
{
    /** The texture 'name' supplied by OpenGL */
    private int glTextureName;

    /**
     * Create a Texture2D from an OpenGL texture that has already been initialized.
     * @param glTextureName The resource name supplied by OpenGL
     */
    public Texture2D(final int glTextureName)
    {
        this.glTextureName = glTextureName;
    }

    /** Create a Texture2D from an Android resource.
     * @param content The content manager.
     * @param resourceID The Android resource ID of the texture bitmap.
     */
    public Texture2D(ContentManager content, final int resourceID)
    {
        // Allocate a new texture in the OpenGL environment.
        int[] glTextureNames = new int[1];
        GLES20.glGenTextures(1, glTextureNames, 0);
        glTextureName = glTextureNames[0];

        // A texture name of '0' indicates a failure
        if(glTextureName == 0)
            throw new RuntimeException("OpenGL ES could not allocate a new texture name.");

        // Decode the bitmap from the Android resource ID provided
        Bitmap textureBmp = BitmapFactory.decodeResource(content.getResources(), resourceID);

        // Bind the texture name to the texture-2D binding point
        GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, glTextureName);

        // Configure filtering mode
        GLES20.glTexParameteri(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_MIN_FILTER, GLES20.GL_LINEAR);
        GLES20.glTexParameteri(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_MAG_FILTER, GLES20.GL_LINEAR);

        // Provide the texel data (mipmap 0, bitmap, 0 border)
        GLUtils.texImage2D(GLES20.GL_TEXTURE_2D, 0, textureBmp, 0);

        // Free the bitmap memory; OpenGL has it now
        textureBmp.recycle();
    }

    /** @return The OpenGL texture name for this texture */
    public int getTextureName()
    {
        return glTextureName;
    }
}
