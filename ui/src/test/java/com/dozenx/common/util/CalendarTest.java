package com.dozenx.common.util;

import java.util.Calendar;

public class CalendarTest {

    public static void main(String args[]){
        Calendar calendar = Calendar.getInstance();
        System.out.println(calendar.get(Calendar.YEAR)+"-"+calendar.get(Calendar.MONTH)+"-"+calendar.get(Calendar.DATE));
    }
}
