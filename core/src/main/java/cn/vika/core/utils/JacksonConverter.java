/*
 * MIT License
 *
 * Copyright (c) 2020 vika
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package cn.vika.core.utils;

import cn.vika.core.exception.JsonConvertException;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Type;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

/**
 * @author Shawn Deng
 * @date 2020-11-07 02:13:59
 */
public class JacksonConverter {

    static ObjectMapper mapper = new ObjectMapper();

    static {
        mapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
        mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
    }

    public static <T> T toBean(InputStream inputStream, Type type) {
        try {
            return mapper.readValue(inputStream, mapper.constructType(type));
        } catch (IOException e) {
            throw new JsonConvertException(e, type);
        }
    }

    public static <T> T toGenericBean(InputStream inputStream, Type type) {
        try {
            JavaType javaType = mapper.constructType(type);
            return mapper.readValue(inputStream, javaType);
        } catch (IOException e) {
            throw new JsonConvertException(e);
        }
    }

    public static <T> T toGenericBean(InputStream inputStream, Class<?> genericClass, Class<?> contentClass) {
        try {
            JavaType javaType = mapper.getTypeFactory().constructParametricType(genericClass, contentClass);
            return mapper.readValue(inputStream, javaType);
        } catch (IOException e) {
            throw new JsonConvertException(e);
        }
    }

    /**
     * Object to json string.
     *
     * @param obj obj
     * @return json string
     * @throws JsonConvertException if transfer failed
     */
    public static String toJson(Object obj) {
        try {
            return mapper.writeValueAsString(obj);
        } catch (JsonProcessingException e) {
            throw new JsonConvertException(e, obj.getClass());
        }
    }

    /**
     * Object to json string byte array.
     *
     * @param obj obj
     * @return json string byte array
     * @throws JsonConvertException if transfer failed
     */
    public static byte[] toJsonBytes(Object obj) {
        try {
            String jsonStr = mapper.writeValueAsString(obj);
            if (jsonStr == null) {
                return new byte[0];
            }
            return jsonStr.getBytes(Charset.forName(StandardCharsets.UTF_8.name()));
        } catch (JsonProcessingException e) {
            throw new JsonConvertException(e, obj.getClass());
        }
    }
}
