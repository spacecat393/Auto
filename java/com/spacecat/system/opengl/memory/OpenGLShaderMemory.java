package com.spacecat.system.opengl.memory;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import com.spacecat.Main;
import com.spacecat.system.DataLoader;
import com.spacecat.system.file.FileDataReader;
import com.spacecat.system.opengl.shader.OpenGLSkinningShader;

public class OpenGLShaderMemory
{
    public static void set(String[] model_string_array, String folder_path, int object_index)
    {
        String model_folder_path = folder_path + "Models/" + model_string_array[0];
        String shaders_folder_path = "/Shaders/";
        String animation_string = "/Animation/";

        byte shader_state = (byte)Integer.parseInt(model_string_array[1]);

        boolean have_animation_folder = new File(model_folder_path + animation_string).isDirectory();

        Object[] object_array = new Object[have_animation_folder ? 5 : 4];
        object_array[0] = new Object[4];

        if (new File(model_folder_path + "/Uniform").exists())
        {
            Object[] uniform_object_array = FileDataReader.getMixXStringArray(model_folder_path + "/Uniform");
            object_array[1] = uniform_object_array;
        }

        if (have_animation_folder)
        {
            float[] bind_poses_float_array = FileDataReader.getFloatArray(model_folder_path + animation_string + "BindPoses");

            int bones_file_array_length = new File(model_folder_path + animation_string + "Bones").listFiles().length;
            Object[] bones_object_array = new Object[bones_file_array_length];
            for (int i = 0; i < bones_file_array_length; ++i)
            {
                bones_object_array[i] = FileDataReader.getIntArray(model_folder_path + animation_string + "Bones/" + i);
            }

            int max_bones = bind_poses_float_array.length / 16;
            object_array[4] = max_bones;

            try
            {
                StringBuilder vertex_stringbuilder = new StringBuilder();
                object_array[2] = vertex_stringbuilder;
                vertex_stringbuilder.append(OpenGLSkinningShader.getVertexHeaderShaderString(object_array, max_bones) + "\n");

                for (int j = 0; j < bones_file_array_length; ++j)
                {
                    int[] bones = (int[])bones_object_array[j];

                    vertex_stringbuilder.append("int bones" + j + "[" + bones.length + "]" + " = int[](");

                    for (int b = bones.length - 1; b > -1; --b)
                    {
                        vertex_stringbuilder.append("" + bones[b]);

                        if (b > 0)
                        {
                            vertex_stringbuilder.append(", ");
                        }
                    }

                    vertex_stringbuilder.append(");\n");
                }

                // float[] bones_float_array = (float[])object_array[3];
                // int bones_size = bones_float_array.length / 16;
                // vertex_stringbuilder.append("mat4 bones" + "[" + bones_size + "]" + " = mat4[](");

                // for (int j = 0; j < bones_size; ++j)
                // {
                //     vertex_stringbuilder.append("mat4(");

                //     int bindposes_index = (j + 1) * 16;
                //     for (int b = j * 16; b < bindposes_index; ++b)
                //     {
                //         vertex_stringbuilder.append("" + bones_float_array[b]);

                //         if (b < bindposes_index - 1)
                //         {
                //             vertex_stringbuilder.append(", ");
                //         }
                //     }

                //     vertex_stringbuilder.append(")");

                //     if (j < bones_size - 1)
                //     {
                //         vertex_stringbuilder.append(", ");
                //     }
                // }

                // vertex_stringbuilder.append(");\n");
                // object_array[3] = null;

                int bindposes_size = bind_poses_float_array.length / 16;
                vertex_stringbuilder.append("mat4 bindposes" + "[" + bindposes_size + "]" + " = mat4[](");

                for (int j = 0; j < bindposes_size; ++j)
                {
                    vertex_stringbuilder.append("mat4(");

                    int bindposes_index = (j + 1) * 16;
                    for (int b = j * 16; b < bindposes_index; ++b)
                    {
                        vertex_stringbuilder.append("" + bind_poses_float_array[b]);

                        if (b < bindposes_index - 1)
                        {
                            vertex_stringbuilder.append(", ");
                        }
                    }

                    vertex_stringbuilder.append(")");

                    if (j < bindposes_size - 1)
                    {
                        vertex_stringbuilder.append(", ");
                    }
                }

                vertex_stringbuilder.append(");\n");

                // for (int i = 0; i < bindposes_float_array.length; i += 16)
                // {
                //     M4x4.inverse(bindposes_float_array, i);
                // }

                String line = null;
                BufferedReader vertex_bufferedreader = new BufferedReader(new FileReader(folder_path + shaders_folder_path + "Vertex" + shader_state + 0));
                while ((line = vertex_bufferedreader.readLine()) != null)
                {
                    vertex_stringbuilder.append(line + "\n");
                }
                vertex_bufferedreader.close();

                boolean multi_uniform = DataLoader.MULTI_UNIFORM;

                if (multi_uniform)
                {
                    vertex_stringbuilder.append("\nmat4 getAnimation(int b)\n{\n");
                    for (int j = 0; j < bindposes_size; ++j)
                    {
                        String head = "else if";

                        if (j == 0)
                        {
                            head = "if";
                        }

                        vertex_stringbuilder.append(head + " (b == " + j + ")\n{return animation" + j + ";\n}\n");
                    }
                    vertex_stringbuilder.append("return mat4(1, 0, 0, 0, 0, 1, 0, 0, 0, 0, 1, 0, 0, 0, 0, 1);\n}\n");
                }

                String transform_vec_string = "\nvec4 transformVec(int b, vec4 vertices_vec4)\n" +
                "{\n" +
                "mat4 skinning_mat4 = bindposes[b];\n" +
                "vertices_vec4 *= skinning_mat4;\n";
                // temp_normals_vec4 *= skinning_mat4;

                if (multi_uniform)
                {
                    transform_vec_string += "skinning_mat4 *= getAnimation(b);\n";
                }
                else
                {
                    transform_vec_string += "skinning_mat4 *= animation[b];\n";
                }

                // skinning_mat4 *= animation[b];
                transform_vec_string += "skinning_mat4 = inverse(skinning_mat4);\n" +
                "vertices_vec4 *= skinning_mat4;\n" +
                // temp_normals_vec4 *= skinning_mat4;

                "return vertices_vec4;\n" +
                "}\n";

                vertex_stringbuilder.append(transform_vec_string);

                vertex_bufferedreader = new BufferedReader(new FileReader(folder_path + shaders_folder_path + "Vertex" + shader_state + 1));
                while ((line = vertex_bufferedreader.readLine()) != null)
                {
                    vertex_stringbuilder.append(line + "\n");
                }
                vertex_bufferedreader.close();

                // if (shader_state != 2 && shader_state != 4)
                // {
                //     vertex_stringbuilder.append("for (int i = 0; i < " + max_joints + "; ++i)\n{\n");
                // }
                // vertex_stringbuilder.append("vec4 temp_vertices_vec4 = vec4(vertices, 1.0);\n");
                //     vertex_stringbuilder.append("if (joints[i] != -1)\n{\nvec4 temp_vertices_vec4 = vec4(vertices, 1.0);\n");
                // }
                // else
                // {
                //     vertex_stringbuilder.append("if (joints != -1)\n{\nvec4 temp_vertices_vec4 = vec4(vertices, 1.0);\n");
                // }

                // vertex_stringbuilder.append("int joint = " + (max_joints == 1 || max_joints > 4 ? "joints" : "joints[i]") + ";\n");
                // vertex_stringbuilder.append("float weight = " + (max_joints == 1 || max_joints > 4 ? "weights" : "weights[i]") + ";\n");

                for (int j = 0; j < bones_object_array.length; ++j)
                {
                    int[] bones = (int[])bones_object_array[j];
                    String head = "else if";

                    if (j == 0)
                    {
                        head = "if";
                    }

                    vertex_stringbuilder.append(head + " (joints[i] == " + j + ")\n{\nfor (int b = 0; b < " + bones.length + "; ++b)\n{\ntemp_vertices_vec4 = transformVec(bones" + j + "[b], temp_vertices_vec4);\n}\n}\n");
                }

                vertex_bufferedreader = new BufferedReader(new FileReader(folder_path + shaders_folder_path + "Vertex" + shader_state + 2));
                while ((line = vertex_bufferedreader.readLine()) != null)
                {
                    vertex_stringbuilder.append(line + "\n");
                }
                vertex_bufferedreader.close();
            }
            catch (IOException ioexception)
            {
            }
        }
        else
        {
            object_array[2] = new StringBuilder();
            try (BufferedReader bufferedreader = new BufferedReader(new FileReader(folder_path + shaders_folder_path + "Vertex" + shader_state)))
            {
                String line = null;
                while ((line = bufferedreader.readLine()) != null)
                {
                    ((StringBuilder)object_array[2]).append(line + "\n");
                }
                bufferedreader.close();
            }
            catch (IOException ioexception)
            {
            }
        }

        object_array[3] = new StringBuilder();
        try (BufferedReader bufferedreader = new BufferedReader(new FileReader(folder_path + shaders_folder_path + "Fragment" + shader_state)))
        {
            String line = null;
            while ((line = bufferedreader.readLine()) != null)
            {
                ((StringBuilder)object_array[3]).append(line + "\n");
            }
            bufferedreader.close();
        }
        catch (IOException ioexception)
        {
        }

        DataLoader.SHADER_OBJECT_ARRAY[object_index] = object_array;
    }
}
