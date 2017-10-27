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
        //�����û���������
        //ָ�������ļ�·��������
        String Piao = "/data/data/com.ings.gogopaidan";
        //ͬ��File�������� ���浽�ļ�����ȥ
        File file = new File(Piao, "userinfo.txt");
        try {
            //��װ�û�������
            String baocheng = naem + "##" + Prasspe+ "##" + beizhu;
            //����һ���ļ�д�������ļ������д����
            FileOutputStream fileOutputStream = new FileOutputStream(file);
            //���û�������д���ļ�
            fileOutputStream.write(baocheng.getBytes());
            //����
            fileOutputStream.close();
            return true;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    /*��д�ļ����������˺�*/
    public static Map<String, String> getMap(Context content) {
        String Piao = "/data/data/com.ings.gogopaidan";
        //ͬ��File�������� ���浽�ļ�����ȥ
        File file = new File(Piao, "userinfo.txt");
        try {
            //������
            FileInputStream fileInputStream = new FileInputStream(file);
            //�������װ��BufferedReader��װ�������ж�ȡ
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(fileInputStream));
            //�����ж�ȡ�û������룬��Ҫ���м���
            String duqu = bufferedReader.readLine();
            //����
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
