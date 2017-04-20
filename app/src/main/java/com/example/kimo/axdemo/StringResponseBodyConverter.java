package com.example.kimo.axdemo;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Converter;

/**
 * ClassName:${TYPE_NAME} <br/>
 * Function: ${TODO} ADD FUNCTION. <br/>
 * Reason:   ${TODO} ADD REASON. <br/>
 * Date:     2017/4/5 14:35 <br/>
 *
 * @author 76dgs02
 * @see
 * @since JDK 1.6
 */


public class StringResponseBodyConverter implements Converter<ResponseBody, String> {
    @Override
    public String convert(ResponseBody value) throws IOException {
        try {
            return value.string();
        } finally {
            value.close();
        }
    }
}
