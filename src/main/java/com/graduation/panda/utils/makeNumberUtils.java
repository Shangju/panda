package com.graduation.panda.utils;

public class makeNumberUtils {
    /**
     * 顾客编号
     * @return
     */
    public static String customerMake(){
        StringBuilder str = new StringBuilder("U");
        for(int i = 0; i < 9; i++){
            str.append((int) (1 + Math.random() * 9));
        }
//        String string = str.toString();
        return str.toString();
    }

    /**
     * 配送员员工编号
     * @param args
     */

    /**
     * 代购员员工编号
     * @param args
     */

    /**
     * 托运公司编号
     * @param args
     */
    public static void main(String[] args) {
//        System.out.println(System.currentTimeMillis());
//        for (int i = 0; i < 30; i++) {
//            System.out.print((int) (1 + Math.random() * 9));
//        }
        System.out.println(makeNumberUtils.customerMake());
    }
}
