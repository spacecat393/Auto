package com.spacecat.system.file;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class FileDataWriter
{
    public static int write(String path_string, byte[] string_byte)
    {
        try
        {
            int offset = 0;
            File file = null;

            do
            {
                file = new File(path_string + offset);
                ++offset;
            }
            while (file.exists());

            FileOutputStream fileoutputstream = new FileOutputStream(file);
            fileoutputstream.write(string_byte);
            fileoutputstream.close();

            return offset - 1;
        }
        catch (IOException ioexception)
        {
        }

        return -1;
    }
}
