package com.spacecat.summer.system.folder;

import java.io.File;

public class FolderWriter
{
    public static void write(String path_string)
    {
        File directory = new File(path_string);

        if (!directory.exists())
        {
            directory.mkdirs();
        }
    }
}
