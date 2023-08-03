package com.spacecat.system.opengl.memory;

import java.io.File;

import com.spacecat.system.DataLoader;
import com.spacecat.system.file.FileDataReader;

public class OpenGLObjectMemory
{
    public static void set(String[] model_string_array, String folder_path, int object_index)
    {
        String model_folder_path = folder_path + "Models/" + model_string_array[0];
        // String shaders_folder_path = "/Shaders/";

        byte texture_state = (byte)Integer.parseInt(model_string_array[1]);
        int shader_id = Integer.parseInt(model_string_array[2]);
        // byte shader_state = (byte)Integer.parseInt(model_string_array[2]);
        byte culling = (byte)Integer.parseInt(model_string_array[3]);

        Object[] object_array = new Object[7];
        object_array[0] = FileDataReader.getIntArray(model_folder_path + "/Index");
        object_array[1] = FileDataReader.getFloatArray(model_folder_path + "/Vertices");
        object_array[2] = FileDataReader.getFloatArray(model_folder_path + "/Texcoord");
        // object_array[3] = new ArrayList<float[]>();
        // FileDataReader.getXFloatArrayList(model_folder_path + "/Normals", object_array[3], 3);

        // if (new File(model_folder_path + "/List").exists())
        // {
        //     Object[] uniform_object_array = FileDataReader.getMixXStringArray(model_folder_path + "/List");
        //     object_array[7] = uniform_object_array;
        // }

        // object_array[5] = new StringBuilder();
        // try (BufferedReader bufferedreader = new BufferedReader(new FileReader(folder_path + shaders_folder_path + "Fragment" + shader_state)))
        // {
        //     String line = null;
        //     while ((line = bufferedreader.readLine()) != null)
        //     {
        //         ((StringBuilder)object_array[5]).append(line + "\n");
        //     }
        //     bufferedreader.close();
        // }
        // catch (IOException ioexception)
        // {
        //     Main.LOGGER.error(ioexception.getMessage(), ioexception);
        // }

        object_array[3] = ((int[])object_array[0]).length;
        object_array[4] = culling;
        object_array[5] = texture_state;
        object_array[6] = new Object[5];
        ((Object[])object_array[6])[0] = shader_id;
        // object_array[10] = new Object[13];

        // object_array[4] = new StringBuilder();
        // try (BufferedReader bufferedreader = new BufferedReader(new FileReader(folder_path + shaders_folder_path + "Vertex" + shader_state)))
        // {
        //     String line = null;
        //     while ((line = bufferedreader.readLine()) != null)
        //     {
        //         ((StringBuilder)object_array[4]).append(line + "\n");
        //     }
        //     bufferedreader.close();
        // }
        // catch (IOException ioexception)
        // {
        //     Main.LOGGER.error(ioexception.getMessage(), ioexception);
        // }

        DataLoader.MODEL_OBJECT_ARRAY[object_index] = object_array;
    }
}
