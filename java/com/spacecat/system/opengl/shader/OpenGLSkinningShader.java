package com.spacecat.system.opengl.shader;

import com.spacecat.system.DataLoader;

public class OpenGLSkinningShader
{
    public static String getVertexHeaderShaderString(Object[] object_array, int max_bones)
    {
        String string = "#version 430\n" +
        "precision highp float;\n" +

        "layout(location = 0) in vec3 vertices;\n" +
        "layout(location = 1) in vec2 texcoord;\n" +
        // "layout(location = 2) in vec3 normals;\n" +

        "layout(location = 2) in ivec4 joints;\n" +
        "layout(location = 3) in vec4 weights;\n";

        // "uniform mat4[" + max_bones + "] bones;\n" +

        if (DataLoader.MULTI_UNIFORM)
        {
            for (int i = 0; i < max_bones; ++i)
            {
                string += "uniform mat4 animation" + i + ";\n";
            }
        }
        else
        {
            string += "uniform mat4[" + max_bones + "] animation;\n";
        }

        return string;
    }
}
