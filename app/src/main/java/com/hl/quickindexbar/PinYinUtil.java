package com.hl.quickindexbar;

import android.text.TextUtils;

import net.sourceforge.pinyin4j.PinyinHelper;
import net.sourceforge.pinyin4j.format.HanyuPinyinCaseType;
import net.sourceforge.pinyin4j.format.HanyuPinyinOutputFormat;
import net.sourceforge.pinyin4j.format.HanyuPinyinToneType;
import net.sourceforge.pinyin4j.format.exception.BadHanyuPinyinOutputFormatCombination;

public class PinYinUtil {

    public static String getPinyin(String chinese) {
        if (TextUtils.isEmpty(chinese)) {
            return null;
        }
        //设置输出格式，如大小写、声调
        HanyuPinyinOutputFormat format = new HanyuPinyinOutputFormat();
        format.setCaseType(HanyuPinyinCaseType.UPPERCASE);
        format.setToneType(HanyuPinyinToneType.WITHOUT_TONE);

        //1.由于只能对单个汉字转化，所以需要将字符串转化为字符数组，
        // 然后对每个字符转化，最后拼接起来
        char[] charArray = chinese.toCharArray();
        //通过StringBuilder对字符进行拼接
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < charArray.length; i++) {
            //2.过滤空格
            if (Character.isWhitespace(charArray[i])) {
                continue;
            }
            //3.判断是否是汉字 汉字占2个字节 一个字节范围 -128~127
            if (charArray[i] > 127) {
                //可能是汉字
                try {
                    //由于有多音字的存在所以是数组
                    String[] stringArray = PinyinHelper.toHanyuPinyinStringArray(charArray[i], format);
                    if (stringArray != null) {
                        //只取第一个，单 dan,shan
                        stringBuilder.append(stringArray[0]);
                    }
                } catch (BadHanyuPinyinOutputFormatCombination badHanyuPinyinOutputFormatCombination) {
                    badHanyuPinyinOutputFormatCombination.printStackTrace();
                }
            } else {
                //肯定不是汉字，应该是键盘上能够直接输入的字符，这些字符能够排序，但不能获取拼音
                stringBuilder.append(charArray[i]);
            }
        }
        return stringBuilder.toString();
    }
}
