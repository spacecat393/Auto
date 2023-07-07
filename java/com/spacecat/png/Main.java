package com.spacecat.png;

import java.nio.ByteBuffer;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.imageio.ImageIO;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class Main
{
	public static Logger LOGGER = Logger.getLogger(Main.class.getName());

    public static void main(String[] args)
    {
        try
        {
            File[] file_array = new File("Objects").listFiles();

            for (int i = 0; i < file_array.length; ++i)
            {
                File file = file_array[i];
                BufferedImage bufferedimage = ImageIO.read(file);

                ByteBuffer bytebuffer = ByteBuffer.allocateDirect(bufferedimage.getWidth() * bufferedimage.getHeight() * 4);
                // String string = "";
                int[] pixels = new int[bufferedimage.getWidth() * bufferedimage.getHeight()];
                bufferedimage.getRGB(0, 0, bufferedimage.getWidth(), bufferedimage.getHeight(), pixels, 0, bufferedimage.getWidth());

                for (int h = 0; h < bufferedimage.getHeight(); ++h)
                {
                    for (int w = 0; w < bufferedimage.getWidth(); ++w)
                    {
                        int pixel = pixels[h * bufferedimage.getWidth() + w];

                        // string += pixel;
                        // string += (pixel >> 16) & 0xFF;
                        // string += ' ';
                        // string += (pixel >> 8) & 0xFF;
                        // string += ' ';
                        // string += pixel & 0xFF;
                        // string += ' ';
                        // string += (pixel >> 24) & 0xFF;

                        bytebuffer.put((byte)((pixel >> 16) & 0xFF));
                        bytebuffer.put((byte)((pixel >> 8) & 0xFF));
                        bytebuffer.put((byte)(pixel & 0xFF));
                        bytebuffer.put((byte)((pixel >> 24) & 0xFF));

                        // if (w < bufferedimage.getWidth() - 1)
                        // {
                        //     string += ' ';
                        // }
                    }

                    // if (h < bufferedimage.getWidth() - 1)
                    // {
                    //     string += ' ';
                    // }
                }

                bytebuffer.flip();

                byte[] byte_array = new byte[bytebuffer.limit()];

                for (int l = 0; l < bytebuffer.limit(); ++l)
                {
                    byte_array[l] = bytebuffer.get(l);
                }

                try (FileOutputStream fileoutputstream = new FileOutputStream("Results/" + file.getName()))
                {
                    // fileoutputstream.write(string.getBytes());
                    fileoutputstream.write(byte_array);
                }
            }
        }
        catch (IOException ioexception)
        {
            LOGGER.log(Level.SEVERE, ioexception.getMessage(), ioexception);
        }
    }
}
