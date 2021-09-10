package com.jie.jvm;

import com.jie.jvm.classloader.HelloClassLoader;
import org.apache.commons.io.IOUtils;

import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.util.Base64;

/**
 * @author Jie.LJ.Liu
 * @date 2021/9/10 15:50
 */
public class Hello {

    public static void main(String[] args) {
        byte[] classBinaryBytes = getClassBinaryBytes();

        System.out.println(getClazzBase64String());
    }

    public void hello() {
        System.out.println("hello");
    }


    public static byte[] getClassBinaryBytes() {

        Class clazz = Hello.class;
        String clazzName = clazz.getName();
        String clazzAsPath = clazzName.replace('.', '/') + ".class";
        InputStream inputStream = clazz.getClassLoader().getResourceAsStream(clazzAsPath);
        try {
            return IOUtils.toByteArray(inputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return new byte[0];
    }

    public static String getClazzBase64String() {
        return Base64.getEncoder().encodeToString(getClassBinaryBytes());
    }

    public static void generateClazzFile(String suffix) throws IOException {
        FileWriter fileWriter = new FileWriter(Hello.class.getName() + "." + suffix);
        IOUtils.write(getClassBinaryBytes(), fileWriter, Charset.defaultCharset());
    }
}
