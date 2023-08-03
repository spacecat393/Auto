package com.spacecat.system.opengl.memory;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import com.spacecat.Main;
import com.spacecat.system.DataLoader;
import com.spacecat.system.file.FileDataReader;

public class OpenGLMiscMemory
{
    public static void set(String[] model_string_array, String folder_path, int object_index)
    {
        String model_folder_path = folder_path + "Models/" + model_string_array[0];
        String animation_string = "/Animation/";

        boolean have_animation_folder = new File(model_folder_path + animation_string).isDirectory();

        Object[] object_array = new Object[have_animation_folder ? 5 : 2];

        File[] file_array = new File(model_folder_path + "/Textures/").listFiles();
        Object[] texture_object_array = new Object[file_array.length];
        for (int i = 0; i < file_array.length; ++i)
        {
            FileInputStream fileinputstream = null;

            try
            {
                File file = file_array[i];
                String[] string_array = file.getName().split(" ");

                fileinputstream = new FileInputStream(file);
                byte[] byte_array = new byte[fileinputstream.available()];
                fileinputstream.read(byte_array);

                int index = Integer.parseInt(string_array[0]);
                int width = Integer.parseInt(string_array[1]);
                int height = Integer.parseInt(string_array[2]);
                Object[] temp_texture_object_array = new Object[3];
                temp_texture_object_array[1] = width;
                temp_texture_object_array[2] = height;

                texture_object_array[index] = temp_texture_object_array;
            }
            catch (IOException ioexception)
            {
            }
            finally
            {
                if (fileinputstream != null)
                {
                    try
                    {
                        fileinputstream.close();
                    }
                    catch (IOException ioexception)
                    {
                    }
                }
            }
        }
        object_array[0] = texture_object_array;

        object_array[1] = new Object[1];

        if (have_animation_folder)
        {
            // int max_frame = Integer.parseInt(model_string_array[1]);

            // ArrayList<M4x4[]> transforms_m4x4_arraylist = new ArrayList<M4x4[]>();
            // object_array[2] = transforms_m4x4_arraylist;
            // FileDataReader.getXM4x4ArrayList(model_folder_path + animation_string + "/Transforms", object_array[2], max_frame);
            object_array[2] = FileDataReader.getFloatArray(model_folder_path + animation_string + "/Transforms");
            // object_array[2] = new ArrayList<float[]>();
            // FileDataReader.getXFloatArrayList(model_folder_path + animation_string + "/Transforms", object_array[2], max_frame * 9);

//            object_array[3] = FileDataReader.getIntArray(model_folder_path + "/Bones");

            // int bones_file_array_length = new File(model_folder_path + "/Bones").listFiles().length;
            // Object[] bones_object_array = new Object[bones_file_array_length];
            // for (int i = 0; i < bones_file_array_length; ++i)
            // {
            //     bones_object_array[i] = FileDataReader.getIntArray(model_folder_path + "/Bones/" + i);
            // }
            // object_array[3] = bones_object_array;
            try
            {
				object_array[3] = Files.readAllBytes(Paths.get(model_folder_path + "/IdleBones"));
			}
            catch (IOException ioexception)
            {
			}

            object_array[4] = (((float[])object_array[2]).length / 16) / new File(model_folder_path + animation_string + "Bones").listFiles().length;
        }

        // for (int x = 0; x < transforms_m4x4_arraylist.size(); ++x)
        // {
        //     for (int y = 0; y < transforms_m4x4_arraylist.get(x).length; ++y)
        //     {
        //         transforms_m4x4_arraylist.get(x)[y].inverse();
        //     }
        // }

        // ArrayList<Integer> bones_integer_arraylist = (ArrayList<Integer>)object_array[16];
        // ArrayList<M4x4[]> transforms_m4x4_arraylist = (ArrayList<M4x4[]>)object_array[17];
        // ArrayList<M4x4> visualbones_m4x4_arraylist = new ArrayList<M4x4>();
        // FileDataReader.getM4x4ArrayList(model_folder_path + "/VisualBones", visualbones_m4x4_arraylist);
        // for (int m0 = 0; m0 < bones_integer_arraylist.size(); ++m0)
        // {
        //     for (int m1 = 0; m1 < max_key_frame; ++m1)
        //     {
        //         transforms_m4x4_arraylist.get(m0)[m1].mat[3] -= visualbones_m4x4_arraylist.get(bones_integer_arraylist.get(m0)).mat[3];
        //         transforms_m4x4_arraylist.get(m0)[m1].mat[7] -= visualbones_m4x4_arraylist.get(bones_integer_arraylist.get(m0)).mat[7];
        //         transforms_m4x4_arraylist.get(m0)[m1].mat[11] -= visualbones_m4x4_arraylist.get(bones_integer_arraylist.get(m0)).mat[11];
        //         transforms_m4x4_arraylist.get(m0)[m1].inverse();
        //     }
        // }

        // for (int i = 0; i < bones_integer_arraylist.size(); ++i)
        // {
        //     bones_integer_arraylist.set(i, bones_integer_arraylist.get(i) * 16);
        // }

        DataLoader.MODEL_OBJECT_ARRAY[object_index] = object_array;
    }
}
