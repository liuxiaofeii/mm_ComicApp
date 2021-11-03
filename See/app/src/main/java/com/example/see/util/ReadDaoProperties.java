package com.example.see.util;

import org.apache.ibatis.io.Resources;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Properties;
import java.util.ResourceBundle;

public class ReadDaoProperties {
    public static void readProperties(InputStream is){
        Properties properties=null;



        try {

            //properties = new Properties(Resources.getResourceAsProperties(config));
            properties=new Properties();
            properties.load(is);
            String name= (String) properties.get("name");
            System.out.println(name);
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
