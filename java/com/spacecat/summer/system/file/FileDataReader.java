package com.spacecat.summer.system.file;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.logging.Level;

import com.spacecat.summer.Summer;
import com.spacecat.summer.objects.math.M4x4;

public class FileDataReader
{
    public static void getXFloatArrayList(String path_string, Object object, int max_size)
    {
        ArrayList<float[]> xfloat_arraylist = (ArrayList<float[]>)object;

        try
        {
            ArrayList<Float> float_arraylist = new ArrayList<Float>();
            String data_string = new String(Files.readAllBytes(Paths.get(path_string)));
            String number_string = "";
            int state = 1;

            for (int i = 0; i < data_string.length(); ++i)
            {
                if (data_string.charAt(i) == ' ')
                {
                    float_arraylist.add(Float.parseFloat(number_string));
                    number_string = "";

                    if (state == max_size)
                    {
                        float[] float_array = new float[max_size];

                        for (int l = 0; l < float_array.length; ++l)
                        {
                            float_array[l] = float_arraylist.get(l);
                        }

                        float_arraylist.clear();

                        xfloat_arraylist.add(float_array);

                        state = 1;
                    }
                    else
                    {
                        ++state;
                    }
                }
                else
                {
                    number_string += data_string.charAt(i);
                }
            }

            float_arraylist.add(Float.parseFloat(number_string));

            float[] float_array = new float[max_size];

            for (int l = 0; l < float_array.length; ++l)
            {
                float_array[l] = float_arraylist.get(l);
            }

            xfloat_arraylist.add(float_array);
        }
        catch (IOException ioexception)
        {
            Summer.LOGGER.log(Level.SEVERE, ioexception.getMessage(), ioexception);
        }
    }

    public static void getXIntArrayList(String path_string, Object object, int max_size)
    {
        ArrayList<int[]> xint_arraylist = (ArrayList<int[]>)object;

        try
        {
            ArrayList<Integer> int_arraylist = new ArrayList<Integer>();
            String data_string = new String(Files.readAllBytes(Paths.get(path_string)));
            String number_string = "";
            int state = 1;

            for (int i = 0; i < data_string.length(); ++i)
            {
                if (data_string.charAt(i) == ' ')
                {
                    int_arraylist.add(Integer.parseInt(number_string));
                    number_string = "";

                    if (state == max_size)
                    {
                        int[] int_array = new int[max_size];

                        for (int l = 0; l < int_array.length; ++l)
                        {
                            int_array[l] = int_arraylist.get(l);
                        }

                        int_arraylist.clear();

                        xint_arraylist.add(int_array);

                        state = 1;
                    }
                    else
                    {
                        ++state;
                    }
                }
                else
                {
                    number_string += data_string.charAt(i);
                }
            }

            int_arraylist.add(Integer.parseInt(number_string));

            int[] int_array = new int[max_size];

            for (int l = 0; l < int_array.length; ++l)
            {
                int_array[l] = int_arraylist.get(l);
            }

            xint_arraylist.add(int_array);
        }
        catch (IOException ioexception)
        {
            Summer.LOGGER.log(Level.SEVERE, ioexception.getMessage(), ioexception);
        }
    }

    public static void getXM4x4ArrayList(String path_string, Object object, int max_size)
    {
        ArrayList<M4x4[]> xm4x4_arraylist = (ArrayList<M4x4[]>)object;

        try
        {
            int max_matrix_size = 16;

            ArrayList<Float> float_arraylist = new ArrayList<Float>();
            String data_string = new String(Files.readAllBytes(Paths.get(path_string)));
            String number_string = "";
            int state = 1;

            for (int i = 0; i < data_string.length(); ++i)
            {
                if (data_string.charAt(i) == ' ')
                {
                    float_arraylist.add(Float.parseFloat(number_string));
                    number_string = "";

                    if (state == max_matrix_size * max_size)
                    {
                        M4x4[] m4x4_arraylist = new M4x4[max_size];

                        for (int z = 0; z < max_size; ++z)
                        {
                            M4x4 m4x4 = new M4x4();

                            for (int x = 0; x < max_matrix_size; ++x)
                            {
                                m4x4.mat[x] = float_arraylist.get(z * 16 + x);
                            }

                            m4x4_arraylist[z] = m4x4;
                        }

                        float_arraylist.clear();

                        xm4x4_arraylist.add(m4x4_arraylist);

                        state = 1;
                    }
                    else
                    {
                        ++state;
                    }
                }
                else
                {
                    number_string += data_string.charAt(i);
                }
            }

            float_arraylist.add(Float.parseFloat(number_string));

            M4x4[] m4x4_arraylist = new M4x4[max_size];

            for (int z = 0; z < max_size; ++z)
            {
                M4x4 m4x4 = new M4x4();

                for (int x = 0; x < max_matrix_size; ++x)
                {
                    m4x4.mat[x] = float_arraylist.get(z * 16 + x);
                }

                m4x4_arraylist[z] = m4x4;
            }

            xm4x4_arraylist.add(m4x4_arraylist);
        }
        catch (IOException ioexception)
        {
            Summer.LOGGER.log(Level.SEVERE, ioexception.getMessage(), ioexception);
        }
    }

    public static void getM4x4ArrayList(String path_string, Object object)
    {
        ArrayList<M4x4> m4x4_arraylist = (ArrayList<M4x4>)object;

        try
        {
            int max_matrix_size = 16;

            ArrayList<Float> float_arraylist = new ArrayList<Float>();
            String data_string = new String(Files.readAllBytes(Paths.get(path_string)));
            String number_string = "";
            int state = 1;

            for (int i = 0; i < data_string.length(); ++i)
            {
                if (data_string.charAt(i) == ' ')
                {
                    float_arraylist.add(Float.parseFloat(number_string));
                    number_string = "";

                    if (state == max_matrix_size)
                    {
                        M4x4 m4x4 = new M4x4();

                        for (int x = 0; x < max_matrix_size; ++x)
                        {
                            m4x4.mat[x] = float_arraylist.get(x);
                        }

                        float_arraylist.clear();

                        m4x4_arraylist.add(m4x4);

                        state = 1;
                    }
                    else
                    {
                        ++state;
                    }
                }
                else
                {
                    number_string += data_string.charAt(i);
                }
            }

            float_arraylist.add(Float.parseFloat(number_string));

            M4x4 m4x4 = new M4x4();

            for (int x = 0; x < max_matrix_size; ++x)
            {
                m4x4.mat[x] = float_arraylist.get(x);
            }

            m4x4_arraylist.add(m4x4);
        }
        catch (IOException ioexception)
        {
            Summer.LOGGER.log(Level.SEVERE, ioexception.getMessage(), ioexception);
        }
    }

    public static void getMixXIntArrayList(String path_string, Object object)
    {
        ArrayList<int[]> xint_arraylist = (ArrayList<int[]>)object;

        try
        {
            String data_string = new String(Files.readAllBytes(Paths.get(path_string)));
            String number_string = "";
            ArrayList<Integer> integer_arraylist = new ArrayList<Integer>();

            for (int i = 0; i < data_string.length(); ++i)
            {
                if (data_string.charAt(i) == ' ')
                {
                    integer_arraylist.add(Integer.parseInt(number_string));
                    number_string = "";
                }
                else if (data_string.charAt(i) == '\n')
                {
                    integer_arraylist.add(Integer.parseInt(number_string));
                    number_string = "";

                    int[] int_array = new int[integer_arraylist.size()];

                    for (int l = 0; l < int_array.length; ++l)
                    {
                        int_array[l] = integer_arraylist.get(l);
                    }

                    integer_arraylist.clear();

                    xint_arraylist.add(int_array);
                }
                else
                {
                    number_string += data_string.charAt(i);
                }
            }

            integer_arraylist.add(Integer.parseInt(number_string));

            int[] int_array = new int[integer_arraylist.size()];

            for (int l = 0; l < int_array.length; ++l)
            {
                int_array[l] = integer_arraylist.get(l);
            }

            xint_arraylist.add(int_array);
        }
        catch (IOException ioexception)
        {
            Summer.LOGGER.log(Level.SEVERE, ioexception.getMessage(), ioexception);
        }
    }

    public static void getIntArrayList(String path_string, char end_char, Object object)
    {
        ArrayList<Integer> integer_arraylist = (ArrayList<Integer>)object;

        try
        {
            String data_string = new String(Files.readAllBytes(Paths.get(path_string)));
            String number_string = "";

            for (int i = 0; i < data_string.length(); ++i)
            {
                if (data_string.charAt(i) == end_char)
                {
                    integer_arraylist.add(Integer.parseInt(number_string));
                    number_string = "";
                }
                else
                {
                    number_string += data_string.charAt(i);
                }
            }

            integer_arraylist.add(Integer.parseInt(number_string));
        }
        catch (IOException ioexception)
        {
            Summer.LOGGER.log(Level.SEVERE, ioexception.getMessage(), ioexception);
        }
    }

    public static void getMixXStringArrayList(String path_string, ArrayList<String[]> xstring_arraylist)
    {
        try
        {
            String data_string = new String(Files.readAllBytes(Paths.get(path_string)));
            String number_string = "";
            ArrayList<String> string_arraylist = new ArrayList<String>();

            for (int i = 0; i < data_string.length(); ++i)
            {
                if (data_string.charAt(i) == ' ')
                {
                    string_arraylist.add(number_string);
                    number_string = "";
                }
                else if (data_string.charAt(i) == '\n')
                {
                    string_arraylist.add(number_string);
                    number_string = "";

                    String[] string_array = new String[string_arraylist.size()];

                    for (int l = 0; l < string_array.length; ++l)
                    {
                        string_array[l] = string_arraylist.get(l);
                    }

                    string_arraylist.clear();

                    xstring_arraylist.add(string_array);
                }
                else
                {
                    number_string += data_string.charAt(i);
                }
            }

            string_arraylist.add(number_string);

            String[] string_array = new String[string_arraylist.size()];

            for (int l = 0; l < string_array.length; ++l)
            {
                string_array[l] = string_arraylist.get(l);
            }

            xstring_arraylist.add(string_array);
        }
        catch (IOException ioexception)
        {
            Summer.LOGGER.log(Level.SEVERE, ioexception.getMessage(), ioexception);
        }
    }
}
