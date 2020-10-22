package com.guuidea.inreading;

import com.guuidea.inreading.utils.Constant;
import com.guuidea.inreading.utils.StringUtils;

import org.junit.Test;

/**
 * Created by guuidea on 17-4-22.
 */

public class DateConvertTest{

    @Test
    public void testDate(){
        String date = "2017-04-22T13:20:19.700Z";
        String pattern = Constant.FORMAT_BOOK_DATE;
        String value = StringUtils.dateConvert(date,pattern);
        System.out.print(value);
    }
}
