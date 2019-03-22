package com.graduation.panda.utils;

public class MakeNumberUtils {
    /**
     * 顾客编号，用U开头
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
     * 商品编号，用G开头
     * @return
     */
    public static String goodsMake(){
        StringBuilder str = new StringBuilder("G");
        for(int i = 0; i < 9; i++){
            str.append((int) (1 + Math.random() * 9));
        }
        return str.toString();
    }

    /**
     * 订单编号，用G开头
     * @return
     */
    public static String orderMake(){
        StringBuilder str = new StringBuilder("O");
        for(int i = 0; i < 12; i++){
            str.append((int) (1 + Math.random() * 9));
        }
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
//        System.out.println(MakeNumberUtils.customerMake());
//        System.out.println(MakeNumberUtils.goodsMake());
        for(int i=25;i>0;i--){
            System.out.println(i);
        }
    }
}
