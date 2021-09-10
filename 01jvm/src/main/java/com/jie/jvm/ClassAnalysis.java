package com.jie.jvm;

/**
 * @author Jie.LJ.Liu
 * @date 2021/9/10 15:45
 */
public class ClassAnalysis {
    public static void main(String[] args) {
        int num1 = 1;
        double num2 = 2.0D;
        long num3 = 3L;
        byte num4 = 4;
        if ("".length() < 5) {
            System.out.println("num2 + num3 = " + num2 + num3);
        }

        for (int i = 0; i < num1; i++) {
            System.out.print("num1 * num4 =");
            System.out.println(num1 * num4);
        }
    }
}
