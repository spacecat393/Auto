package com.spacecat.summer;

import java.util.logging.Logger;

import com.spacecat.summer.system.DataLoader;

public class Summer
{
	public static Logger LOGGER = Logger.getLogger(Summer.class.getName());

    public static void main(String[] args)
    {
        DataLoader.setModels("Objects");
    }
}
