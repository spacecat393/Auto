package com.spacecat.summer.system.opengl.memory;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;

import com.spacecat.summer.Summer;
import com.spacecat.summer.system.DataLoader;
import com.spacecat.summer.system.file.FileDataReader;

public class OpenGLObjectMemory
{
    public static void set(String[] modeldata_xstring, String folder_path)
    {
        String model_folder_path = folder_path + "Models/" + modeldata_xstring[0];
        String shaders_folder_path = "/Shaders/";

        byte texture_state = (byte)Integer.parseInt(modeldata_xstring[1]);
        byte shader_state = (byte)Integer.parseInt(modeldata_xstring[2]);
        byte culling = (byte)Integer.parseInt(modeldata_xstring[3]);

        Object[] object_array = new Object[10];
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

        if (new File(model_folder_path + "/List").exists())
        {
            ArrayList<String[]> uniform_xstring_arraylist = new ArrayList<String[]>();
            FileDataReader.getMixXStringArrayList(model_folder_path + "/List", uniform_xstring_arraylist);
            object_array[3] = uniform_xstring_arraylist;
        }

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

        object_array[4] = new StringBuilder();
        try (BufferedReader bufferedreader = new BufferedReader(new FileReader(folder_path + shaders_folder_path + "Vertex" + shader_state)))
        {
            String line = null;
            while ((line = bufferedreader.readLine()) != null)
            {
                ((StringBuilder)object_array[4]).append(line).append("\n");
            }
            bufferedreader.close();
        }
        catch (IOException ioexception)
        {
            Summer.LOGGER.log(Level.SEVERE, ioexception.getMessage(), ioexception);
        }

        DataLoader.OBJECTS_ARRAY_ARRAYLIST.add(object_array);
    }
}
