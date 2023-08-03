package com.spacecat.system;

import java.io.File;

import com.spacecat.system.file.FileDataReader;
import com.spacecat.system.file.FileDataWriter;
import com.spacecat.system.folder.FolderWriter;
import com.spacecat.system.opengl.memory.OpenGLMiscMemory;
import com.spacecat.system.opengl.memory.OpenGLObjectMemory;
import com.spacecat.system.opengl.memory.OpenGLShaderMemory;
import com.spacecat.system.opengl.memory.OpenGLSkinningMemory;

public class DataLoader
{
    public static Object[] MODEL_OBJECT_ARRAY;
    public static Object[] SHADER_OBJECT_ARRAY;
    public static boolean MULTI_UNIFORM = false;

    public static void setModels(String mod_id_string)
    {
        DataLoader.check(mod_id_string);

        String folder_path = mod_id_string + '/';

        Object[] model_object_array = FileDataReader.getMixXStringArray(folder_path + "ModelsList");
        int model_length = model_object_array.length;
        MODEL_OBJECT_ARRAY = new Object[model_length];

        for (int i = 0; i < model_length; ++i)
        {
            String[] model_string_array = (String[])model_object_array[i];

            File model_folder_file = new File(folder_path + "Models/" + model_string_array[0]);

            if (model_folder_file.isDirectory())
            {
                switch (model_string_array.length)
                {
                    case 1:
                    {
                        OpenGLMiscMemory.set(model_string_array, folder_path, i);
                        break;
                    }
                    case 4:
                    {
                        OpenGLObjectMemory.set(model_string_array, folder_path, i);
                        break;
                    }
                    case 5:
                    {
                        OpenGLSkinningMemory.set(model_string_array, folder_path, i);
                        break;
                    }
                    default:
                    {
                    }
                }
            }
            else
            {
                DataLoader.MODEL_OBJECT_ARRAY[i] = null;
            }
        }

        Object[] shader_object_array = FileDataReader.getMixXStringArray(folder_path + "ShaderList");
        int shader_length = shader_object_array.length;
        SHADER_OBJECT_ARRAY = new Object[shader_length];

        for (int i = 0; i < shader_length; ++i)
        {
            String[] shader_string_array = (String[])shader_object_array[i];

            File shader_folder_file = new File(folder_path + "Models/" + shader_string_array[0]);

            if (shader_folder_file.isDirectory())
            {
                OpenGLShaderMemory.set(shader_string_array, folder_path, i);
            }
            else
            {
                DataLoader.SHADER_OBJECT_ARRAY[i] = null;
            }
        }

        for (int i = 0; i < model_length; ++i)
        {
            Object[] temp_model_object_array = (Object[])DataLoader.MODEL_OBJECT_ARRAY[i];

            if (temp_model_object_array != null)
            {
                switch (temp_model_object_array.length)
                {
                    case 7: case 9:
                    {
                        int shader_id = (int)((Object[])temp_model_object_array[6])[0];
                        ((Object[])temp_model_object_array[6])[0] = DataLoader.SHADER_OBJECT_ARRAY[shader_id];
                        break;
                    }
                    default:
                    {
                        break;
                    }
                }
            }
        }

        DataLoader.export("Results", shader_object_array);
    }

    public static void check(String mod_id_string)
    {
        if (!new File(mod_id_string).isDirectory())
        {
            System.exit(-1);
        }
    }

    public static void export(String mod_id_string, Object[] shader_object_array)
    {
        for (int i = 0; i < shader_object_array.length; ++i)
        {
            if (SHADER_OBJECT_ARRAY[i] != null)
            {
                String[] shader_string_array = (String[])shader_object_array[i];

                String models_path_string = mod_id_string + "/Models/";
                String path_string = models_path_string + shader_string_array[0];
                String shaders_path_string = path_string + "/Shaders";

                FolderWriter.write(models_path_string);
                FolderWriter.write(path_string);
                FolderWriter.write(shaders_path_string);

                Object[] object_array = (Object[])SHADER_OBJECT_ARRAY[i];

                FileDataWriter.write(shaders_path_string + "/" + shader_string_array[0] + ".vert", ((StringBuilder)object_array[2]).toString().getBytes());
                FileDataWriter.write(shaders_path_string + "/" + shader_string_array[0] + ".frag", ((StringBuilder)object_array[3]).toString().getBytes());
            }
        }
    }
}
