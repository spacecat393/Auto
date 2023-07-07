package com.spacecat.summer.system.opengl.shader;

import java.util.ArrayList;

public class OpenGLSkinningShader
{
    public static String getVertexHeaderShaderString(Object[] object_array)
    {
        byte max_joints = (byte)object_array[18];
        int max_bones = ((ArrayList<Integer>)object_array[15]).size();

        return "#version 430\n" +
        "precision highp float;\n" +

        "layout(location = 0) in vec3 vertices;\n" +
        "layout(location = 1) in vec2 texcoord;\n" +
        // "layout(location = 2) in vec3 normals;\n" +

        "layout(location = 2) in " + (max_joints == 1 || max_joints > 4 ? "int" : "ivec" + max_joints) + " joints;\n" +
        "layout(location = 3) in " + (max_joints == 1 || max_joints > 4 ? "float" : "vec" + max_joints) + " weights;\n";

        // "uniform mat4[" + max_bones + "] bindposes;\n" +
    }

    // public static String getLowVertexHeaderShaderString(Object[] object_array)
    // {
    //     byte max_joints = (byte)object_array[18];
    //     int max_bones = ((ArrayList<Integer>)object_array[15]).size();

    //     return "#version 120\n" +
    //     "precision highp float;\n" +

    //     "attribute vec3 vertices;\n" +
    //     "attribute vec2 texcoord;\n" +

    //     "attribute " + (max_joints == 1 || max_joints > 4 ? "float" : "vec" + max_joints) + " joints;\n" +
    //     "attribute " + (max_joints == 1 || max_joints > 4 ? "float" : "vec" + max_joints) + " weights;\n" +

    //     "uniform mat4[" + max_bones + "] animation_transform;\n";
    // }
}
