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
            new File("Objects").mkdirs();
            new File("Results").mkdirs();

            File[] file_array = new File("Objects").listFiles();

            for (int i = 0; i < file_array.length; ++i)
            {
                // ArrayList<byte[]> byte_arraylist = new ArrayList<byte[]>();
                // ArrayList<Integer> index_integer_arraylist = new ArrayList<Integer>();

                File file = file_array[i];
                BufferedImage bufferedimage = ImageIO.read(file);

                int width = bufferedimage.getWidth();
                int height = bufferedimage.getHeight();

                ByteBuffer bytebuffer = ByteBuffer.allocateDirect(width * height * 4);
                // String string = "";
                int[] pixels = new int[width * height];
                bufferedimage.getRGB(0, 0, width, height, pixels, 0, width);

                for (int h = 0; h < height; ++h)
                {
                    for (int w = 0; w < width; ++w)
                    {
                        int pixel = pixels[h * width + w];

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

                        // byte[] new_byte_array = new byte[]{(byte)((pixel >> 16) & 0xFF), (byte)((pixel >> 8) & 0xFF), (byte)(pixel & 0xFF), (byte)((pixel >> 24) & 0xFF)};

                        // if (cannotPack(byte_arraylist, new_byte_array))
                        // {
                        //     byte_arraylist.add(new_byte_array);
                        // }

                        // index_integer_arraylist.add(byte_arraylist.size() - 1);

                        // if (w < width - 1)
                        // {
                        //     string += ' ';
                        // }
                    }

                    // if (h < width - 1)
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

                try (FileOutputStream fileoutputstream = new FileOutputStream("Results/" + file.getName() + ' ' + width + ' ' + height))
                {
                    // fileoutputstream.write(string.getBytes());
                    fileoutputstream.write(byte_array);
                }

                // try (FileOutputStream fileoutputstream = new FileOutputStream("Results/" + file.getName() + ' ' + width + ' ' + height))
                // {
                //     // fileoutputstream.write(string.getBytes());

                //     for (int b = 0; b < byte_arraylist.size(); ++b)
                //     {
                //         fileoutputstream.write(byte_arraylist.get(b));
                //     }
                // }

                // try (FileOutputStream fileoutputstream = new FileOutputStream("Results/" + file.getName() + "Index"))
                // {
                //     for (int b = 0; b < byte_arraylist.size(); ++b)
                //     {
                //         int index = index_integer_arraylist.get(b);

                //         fileoutputstream.write((byte)((index >> 16) & 0xFF));
                //         fileoutputstream.write((byte)((index >> 8) & 0xFF));
                //         fileoutputstream.write((byte)(index & 0xFF));
                //         fileoutputstream.write((byte)((index >> 24) & 0xFF));
                //     }
                // }
            }
        }
        catch (IOException ioexception)
        {
            LOGGER.log(Level.SEVERE, ioexception.getMessage(), ioexception);
        }
    }

    // public static boolean cannotPack(ArrayList<byte[]> byte_arraylist, byte[] new_byte_array)
    // {
    //     if (byte_arraylist.size() == 0)
    //     {
    //         return true;
    //     }
    //     else
    //     {
    //         for (int x = 0; x < byte_arraylist.size(); ++x)
    //         {
    //             byte[] byte_array = byte_arraylist.get(x);

    //             if
    //             (
    //                 byte_array[0] != new_byte_array[0] ||
    //                 byte_array[1] != new_byte_array[1] ||
    //                 byte_array[2] != new_byte_array[2] ||
    //                 byte_array[3] != new_byte_array[3]
    //             )
    //             {
    //                 return true;
    //             }
    //         }
    //     }

    //     return false;
    // }
}
