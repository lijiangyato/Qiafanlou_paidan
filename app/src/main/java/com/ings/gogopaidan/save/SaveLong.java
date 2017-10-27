package com.ings.gogopaidan.save;

import android.content.Context;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by 17550 on 2017/2/23.
 */

public class SaveLong {
    public static boolean laibao(String naem, String Prasspe,String beizhu) {
        //保存用户名和密码
        //指定保存文件路径的名称
        String Piao = "/data/data/com.ings.gogopaidan";
        //同过File这个类进行 保存到文件里面去
        File file = new File(Piao, "userinfo.txt");
        try {
            //封装用户名密码
            String baocheng = naem + "##" + Prasspe+ "##" + beizhu;
            //创建一个文件写入流往文件里面读写内容
            FileOutputStream fileOutputStream = new FileOutputStream(file);
            //将用户名密码写入文件
            fileOutputStream.write(baocheng.getBytes());
            //光流
            fileOutputStream.close();
            return true;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    /*读写文件理的密码和账号*/
    public static Map<String, String> getMap(Context content) {
        String Piao = "/data/data/com.ings.gogopaidan";
        //同过File这个类进行 保存到文件里面去
        File file = new File(Piao, "userinfo.txt");
        try {
            //读出流
            FileInputStream fileInputStream = new FileInputStream(file);
            //，将其包装成BufferedReader包装流用于行读取
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(fileInputStream));
            //进行行读取用户名密码，还要进行简析
            String duqu = bufferedReader.readLine();
            //简析
            String[] shepo = duqu.split("##");
            //
            HashMap<String, String> hashMa = new HashMap<String, String>();
            hashMa.put("naem", shepo[0]);
            hashMa.put("Prasspe", shepo[1]);
            hashMa.put("bei",shepo[2]);
            bufferedReader.close();
            fileInputStream.close();
            return hashMa;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }






}
