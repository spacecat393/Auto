package com.spacecat.summer.system.opengl.memory;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;

import com.spacecat.summer.Summer;
import com.spacecat.summer.objects.math.M4x4;
import com.spacecat.summer.system.DataLoader;
import com.spacecat.summer.system.file.FileDataReader;
import com.spacecat.summer.system.opengl.shader.OpenGLSkinningShader;

public class OpenGLSkinningMemory
{
    public static void set(String[] modeldata_xstring, String folder_path)
    {
        String model_folder_path = folder_path + "Models/" + modeldata_xstring[0];
        String animation_string = "/Animation/";
        String shaders_folder_path = "/Shaders/";

        byte texture_state = (byte)Integer.parseInt(modeldata_xstring[1]);
        byte shader_state = (byte)Integer.parseInt(modeldata_xstring[2]);
        byte culling = (byte)Integer.parseInt(modeldata_xstring[3]);
        byte max_joints = (byte)Integer.parseInt(modeldata_xstring[4]);

        Object[] object_array = new Object[19];
        ArrayList<Integer> index_integer_arraylist = new ArrayList<Integer>();
        object_array[0] = index_integer_arraylist;
        FileDataReader.getIntArrayList(model_folder_path + "/Index", ' ', object_array[0]);
        ArrayList<float[]> vertices_float_array_arraylist = new ArrayList<float[]>();
        object_array[1] = vertices_float_array_arraylist;
        FileDataReader.getXFloatArrayList(model_folder_path + "/Vertices", object_array[1], 3);
        object_array[2] = new ArrayList<float[]>();
        FileDataReader.getXFloatArrayList(model_folder_path + "/Texcoord", object_array[2], 2);
        // object_array[3] = new ArrayList<float[]>();
        // FileDataReader.getXFloatArrayList(model_folder_path + "/Normals", object_array[3], 3);

        object_array[5] = new StringBuilder();
        try (BufferedReader bufferedreader = new BufferedReader(new FileReader(folder_path + shaders_folder_path + "Fragment" + shader_state)))
        {
            String line = null;
            while ((line = bufferedreader.readLine()) != null)
            {
                ((StringBuilder)object_array[5]).append(line).append("\n");
            }
            bufferedreader.close();
        }
        catch (IOException ioexception)
        {
            Summer.LOGGER.log(Level.SEVERE, ioexception.getMessage(), ioexception);
        }

        object_array[6] = index_integer_arraylist.size();
        object_array[7] = culling;
        object_array[8] = texture_state;
        object_array[9] = new Object[11];
        // object_array[10] = new Object[13];
        object_array[12] = new ArrayList<float[]>();
        FileDataReader.getXFloatArrayList(model_folder_path + "/Weights", object_array[12], max_joints);
        ArrayList<M4x4> bindposes_m4x4_arraylist = new ArrayList<M4x4>();
        object_array[13] = bindposes_m4x4_arraylist;
        FileDataReader.getM4x4ArrayList(model_folder_path + "/BindPoses", object_array[13]);

        ArrayList<int[]> bones_int_array_arraylist = new ArrayList<int[]>();
        object_array[14] = bones_int_array_arraylist;
        FileDataReader.getMixXIntArrayList(model_folder_path + "/Bones", object_array[14]);

        object_array[15] = new ArrayList<Integer>();
        FileDataReader.getIntArrayList(model_folder_path + animation_string + "/SkinningBones", ' ', object_array[15]);
        object_array[16] = new ArrayList<Integer>();
        FileDataReader.getIntArrayList(model_folder_path + animation_string + "/AnimationBones", ' ', object_array[16]);
        // object_array[17] = new ArrayList<M4x4>();
        // FileDataReader.getM4x4ArrayList(model_folder_path + "/VisualBones", object_array[17]);
        object_array[18] = max_joints;

        object_array[4] = new StringBuilder();
        StringBuilder vertex_stringbuilder = (StringBuilder)object_array[4];

        object_array[11] = new ArrayList<int[]>();
        FileDataReader.getXIntArrayList(model_folder_path + "/Joints", object_array[11], max_joints);

        try
        {
            vertex_stringbuilder.append(OpenGLSkinningShader.getVertexHeaderShaderString(object_array)).append("\n");

            for (int j = 0; j < bones_int_array_arraylist.size(); ++j)
            {
                int[] bones = bones_int_array_arraylist.get(j);

                vertex_stringbuilder.append("int bones" + j + "[" + bones.length + "]" + " = int[](");

                for (int b = 0; b < bones.length; ++b)
                {
                    vertex_stringbuilder.append("" + bones[b]);

                    if (b < bones.length - 1)
                    {
                        vertex_stringbuilder.append(", ");
                    }
                }

                vertex_stringbuilder.append(");\n");
            }

            vertex_stringbuilder.append("mat4 bindposes" + "[" + bindposes_m4x4_arraylist.size() + "]" + " = mat4[](");

            for (int j = 0; j < bindposes_m4x4_arraylist.size(); ++j)
            {
                float[] bindpose = bindposes_m4x4_arraylist.get(j).mat;

                vertex_stringbuilder.append("mat4(");

                for (int b = 0; b < bindpose.length; ++b)
                {
                    vertex_stringbuilder.append("" + bindpose[b]);

                    if (b < bindpose.length - 1)
                    {
                        vertex_stringbuilder.append(", ");
                    }
                }

                vertex_stringbuilder.append(")");

                if (j < bindposes_m4x4_arraylist.size() - 1)
                {
                    vertex_stringbuilder.append(", ");
                }
            }

            vertex_stringbuilder.append(");\n");

            vertex_stringbuilder.append("layout(binding = 0) uniform ObjectUniform\n{\n" +
            "mat4[" + ((ArrayList<Integer>)object_array[15]).size() + "] animation_transform;\n");

            String line = null;
            BufferedReader vertex_bufferedreader = new BufferedReader(new FileReader(folder_path + shaders_folder_path + "Vertex" + shader_state + 0));
            while ((line = vertex_bufferedreader.readLine()) != null)
            {
                vertex_stringbuilder.append(line).append("\n");
            }
            vertex_bufferedreader.close();

            if (shader_state != 2 && shader_state != 4)
            {
                vertex_stringbuilder.append("for (int i = 0; i < " + max_joints + "; ++i)\n{");
            }

            // vertex_stringbuilder.append("int joint = " + (max_joints == 1 || max_joints > 4 ? "joints" : "joints[i]") + ";").append("\n");
            // vertex_stringbuilder.append("float weight = " + (max_joints == 1 || max_joints > 4 ? "weights" : "weights[i]") + ';').append("\n");

            vertex_bufferedreader = new BufferedReader(new FileReader(folder_path + shaders_folder_path + "Vertex" + shader_state + 1));
            while ((line = vertex_bufferedreader.readLine()) != null)
            {
                vertex_stringbuilder.append(line).append("\n");
            }
            vertex_bufferedreader.close();

            for (int j = 0; j < bones_int_array_arraylist.size(); ++j)
            {
                int[] bones = bones_int_array_arraylist.get(j);
                String head = "else if";

                if (j == 0)
                {
                    head = "if";
                }

                if (shader_state != 2 && shader_state != 4)
                {
                    vertex_stringbuilder.append(head + " (joints[i] == " + j + ")\n{\nfor (int b = 0; b < " + bones.length + "; ++b)\n{\ntransformVec(bones" + j + "[b]);\n}\n}").append("\n");
                }
                else
                {
                    vertex_stringbuilder.append(head + " (joints == " + j + ")\n{\nfor (int b = 0; b < " + bones.length + "; ++b)\n{\ntransformVec(bones" + j + "[b]);\n}\n}").append("\n");
                }

                // if (shader_state != 2 && shader_state != 4)
                // {
                //     vertex_stringbuilder.append(head + " (joints[i] == " + j + ")\n{\nfor (int b = 0; b < " + bones.length + "; ++b)\n{\ntemp_vertices_vec4 = transformVec(bones" + j + "[b], temp_vertices_vec4);\n}\n}").append("\n");
                // }
                // else
                // {
                //     vertex_stringbuilder.append(head + " (joints == " + j + ")\n{\nfor (int b = 0; b < " + bones.length + "; ++b)\n{\ntemp_vertices_vec4 = transformVec(bones" + j + "[b], temp_vertices_vec4);\n}\n}").append("\n");
                // }
            }

            vertex_bufferedreader = new BufferedReader(new FileReader(folder_path + shaders_folder_path + "Vertex" + shader_state + 2));
            while ((line = vertex_bufferedreader.readLine()) != null)
            {
                vertex_stringbuilder.append(line).append("\n");
            }
            vertex_bufferedreader.close();
        }
        catch (IOException ioexception)
        {
            Summer.LOGGER.log(Level.SEVERE, ioexception.getMessage(), ioexception);
        }

        DataLoader.OBJECTS_ARRAY_ARRAYLIST.add(object_array);
    }
}
