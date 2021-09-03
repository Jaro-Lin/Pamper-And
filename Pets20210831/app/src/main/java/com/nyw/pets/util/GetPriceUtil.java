package com.nyw.pets.util;

/**
 * 价格显示转换
 */
public class GetPriceUtil {
    public GetPriceUtil(){

    }

    /**
     * 金额转换,服务器返回金额是人民币分制，转换成人民币元
     * @param price
     * @return
     */
    public static String getPrice(String price){
        //人民币，服务器返回来的是分，分转元
        float bondMonney=Float.parseFloat(price)/100;
        price=String.valueOf(bondMonney);

        if(price.indexOf(".") == -1)//如果没有小数点
        {
            price += ".00";//直接在后面补点并且加两个00
        }else {
            String [] array= price.split("\\.");
            if ( array[1].length()==1){
                price += "0";//直接在后面补点并且加两个00
            }
        }
        return price;
    }
}
