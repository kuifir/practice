package com.kuifir.http.converter;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 处理返回数据
 */
public interface HttpMessageConverter {

    void write(Object obj, HttpServletResponse response) throws IOException;
}
