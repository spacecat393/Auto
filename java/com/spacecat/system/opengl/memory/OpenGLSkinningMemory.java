package com.spacecat.system.opengl.memory;

import com.spacecat.system.DataLoader;
import com.spacecat.system.file.FileDataReader;

public class OpenGLSkinningMemory
{
    public static void set(String[] model_string_array, String folder_path, int object_index)
    {
        String model_folder_path = folder_path + "Models/" + model_string_array[0];
        // String animation_string = "/Animation/";
        // String shaders_folder_path = "/Shaders/";

        byte texture_state = (byte)Integer.parseInt(model_string_array[1]);
        int shader_id = Integer.parseInt(model_string_array[2]);
        byte culling = (byte)Integer.parseInt(model_string_array[3]);
        byte max_joints = (byte)Integer.parseInt(model_string_array[4]);

        Object[] object_array = new Object[9];
        object_array[0] = FileDataReader.getIntArray(model_folder_path + "/Index");
        object_array[1] = FileDataReader.getFloatArray(model_folder_path + "/Vertices");
        object_array[2] = FileDataReader.getFloatArray(model_folder_path + "/Texcoord");
        // object_array[3] = new ArrayList<float[]>();
        // FileDataReader.getXFloatArrayList(model_folder_path + "/Normals", object_array[3], 3);
        // FileDataReader.getFloatArray(model_folder_path + "/BonesM4x4", object_array, 3);

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
        object_array[6] = new Object[7];
        ((Object[])object_array[6])[0] = shader_id;
        // object_array[10] = new Object[13];
        int[] joints_int_array = FileDataReader.getIntArray(model_folder_path + "/Joints");
        float[] weights_float_array = FileDataReader.getFloatArray(model_folder_path + "/Weights");
        // object_array[13] = FileDataReader.getFloatArray(model_folder_path + "/BindPoses");

        if (max_joints == 4)
        {
            object_array[7] = joints_int_array;
            object_array[8] = weights_float_array;
        }
        else
        {
            int step = 4 - max_joints;
            int joints_int_array_length = joints_int_array.length;
            int[] temp_joints_int_array = new int[joints_int_array_length + (joints_int_array_length / max_joints) * step];
            float[] temp_weights_float_array = new float[joints_int_array_length + (joints_int_array_length / max_joints) * step];
            int index = 0;
            int temp_index = 0;
            for (int x = 0; x < temp_joints_int_array.length; x += 4)
            {
                for (int y = 0; y < max_joints; ++y)
                {
                    temp_joints_int_array[temp_index] = joints_int_array[index];
                    temp_weights_float_array[temp_index++] = weights_float_array[index++];
                }

                for (int y = 0; y < step; ++y)
                {
                    temp_joints_int_array[temp_index] = -1;
                    temp_weights_float_array[temp_index++] = 0.0F;
                }
            }

            object_array[7] = temp_joints_int_array;
            object_array[8] = temp_weights_float_array;
        }

        DataLoader.MODEL_OBJECT_ARRAY[object_index] = object_array;
    }
}
