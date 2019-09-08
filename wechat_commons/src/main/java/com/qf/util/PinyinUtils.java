package com.qf.util;

import net.sourceforge.pinyin4j.PinyinHelper;
import net.sourceforge.pinyin4j.format.HanyuPinyinOutputFormat;
import net.sourceforge.pinyin4j.format.HanyuPinyinToneType;
import net.sourceforge.pinyin4j.format.exception.BadHanyuPinyinOutputFormatCombination;

/**
 * @version 1.0
 * @user ken
 * @date 2019/8/2 9:07
 */
public class PinyinUtils {


    /**
     * 将字符串转成拼音
     * @param str
     * @return
     */
    public static String str2Pinyin(String str){

        if(str != null && !str.trim().equals("")){
            
            //
            char[] chars = str.toCharArray();

            //没有声调
            HanyuPinyinOutputFormat format = new HanyuPinyinOutputFormat();
            format.setToneType(HanyuPinyinToneType.WITHOUT_TONE);

            StringBuffer sb = new StringBuffer();

            for (char c : chars) {
                try {
                    String[] pinyins = PinyinHelper.toHanyuPinyinStringArray(c, format);
                    if(pinyins != null){
                        sb.append(pinyins[0]);
                    } else {
//                        sb.append(c);
                    }

                } catch (BadHanyuPinyinOutputFormatCombination badHanyuPinyinOutputFormatCombination) {
                    badHanyuPinyinOutputFormatCombination.printStackTrace();
                }
            }


            return sb.toString().toUpperCase();
        }
        return null;
    }

    public static void main(String[] args)  {
        String s = str2Pinyin("#飞@@流！……直&*下三千尺");
        System.out.println(s);
    }

}
