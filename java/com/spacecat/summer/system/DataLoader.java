package com.spacecat.summer.system;

import java.io.File;
import java.util.ArrayList;

import com.spacecat.summer.Summer;
import com.spacecat.summer.system.file.FileDataReader;
import com.spacecat.summer.system.file.FileDataWriter;
import com.spacecat.summer.system.folder.FolderWriter;
import com.spacecat.summer.system.opengl.memory.OpenGLObjectMemory;
import com.spacecat.summer.system.opengl.memory.OpenGLSkinningMemory;

public class DataLoader
{
    public static ArrayList<Object[]> OBJECTS_ARRAY_ARRAYLIST = new ArrayList<Object[]>();
    public static ArrayList<Integer> INTEGER_ARRAYLIST = new ArrayList<Integer>();

    public static void setModels(String mod_id_string)
    {
        if (!new File(mod_id_string).isDirectory())
        {
            Summer.LOGGER.severe("No Data Found!");
            System.exit(-1);
        }

        ArrayList<String[]> modeldata_xstring_arraylist = new ArrayList<String[]>();

        String folder_path = mod_id_string + '/';

        FileDataReader.getMixXStringArrayList(folder_path + "List", modeldata_xstring_arraylist);

        for (int i = 0; i < modeldata_xstring_arraylist.size(); ++i)
        {
            String[] modeldata_xstring = modeldata_xstring_arraylist.get(i);

            switch (modeldata_xstring.length)
            {
                case 4:
                {
                    OpenGLObjectMemory.set(modeldata_xstring, folder_path);
                    INTEGER_ARRAYLIST.add(i);
                    break;
                }
                case 5:
                {
                    OpenGLSkinningMemory.set(modeldata_xstring, folder_path);
                    INTEGER_ARRAYLIST.add(i);
                    break;
                }
                default:
                {
                    Summer.LOGGER.severe("No " + modeldata_xstring[0] + " Engine");
                }
            }
        }

        DataLoader.export("Results", modeldata_xstring_arraylist);
    }

    public static void export(String mod_id_string, ArrayList<String[]> modeldata_xstring_arraylist)
    {
        for (int i = 0; i < OBJECTS_ARRAY_ARRAYLIST.size(); ++i)
        {
            String[] modeldata_xstring = modeldata_xstring_arraylist.get(INTEGER_ARRAYLIST.get(i));

            String models_path_string = mod_id_string + "/Models/";
            String path_string = models_path_string + modeldata_xstring[0];
            String shaders_path_string = path_string + "/Shaders";

            FolderWriter.write(models_path_string);
            FolderWriter.write(path_string);
            FolderWriter.write(shaders_path_string);

            Object[] object_array = OBJECTS_ARRAY_ARRAYLIST.get(i);

            // FileDataWriter.write(shaders_path_string + "/Vertex", ((StringBuilder)object_array[4]).toString().getBytes());
            // FileDataWriter.write(shaders_path_string + "/Fragment", ((StringBuilder)object_array[5]).toString().getBytes());

            FileDataWriter.write(shaders_path_string + "/" + modeldata_xstring[0] + ".vert", ((StringBuilder)object_array[4]).toString().getBytes());
            FileDataWriter.write(shaders_path_string + "/" + modeldata_xstring[0] + ".frag", ((StringBuilder)object_array[5]).toString().getBytes());
        }
    }
}
