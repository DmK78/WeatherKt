package ru.job4j.weatherkt.utils;

import android.content.Context;

/**
 * @author Dmitry Kolganov (mailto:dmk78@inbox.ru)
 * @version $Id$
 * @since 01.12.2019
 */

public class Utils {
    public static int getStringIdentifier(Context pContext, String pString, String type) {
        return pContext.getResources().getIdentifier(pString, type, pContext.getPackageName());
    }




}
