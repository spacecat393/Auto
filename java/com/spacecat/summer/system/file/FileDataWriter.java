package com.spacecat.summer.system.file;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.logging.Level;

import com.spacecat.summer.Summer;

public class FileDataWriter
{
    public static void write(String path_string, byte[] byte_array)
    {
        try
        {
            try (FileOutputStream fileoutputstream = new FileOutputStream(path_string))
            {
                fileoutputstream.write(byte_array);
            }
        }
        catch (IOException ioexception)
        {
            Summer.LOGGER.log(Level.SEVERE, ioexception.getMessage(), ioexception);
        }
    }
}
