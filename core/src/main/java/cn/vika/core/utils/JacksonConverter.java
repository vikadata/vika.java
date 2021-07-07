/*
 * Copyright (c) 2021 vikadata, https://vika.cn <support@vikadata.com>
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

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Type;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Map;

import cn.vika.core.exception.JsonConvertException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectReader;
import com.fasterxml.jackson.databind.type.CollectionType;

/**
 * @author Shawn Deng
 * @date 2020-11-07 02:13:59
 */
public class JacksonConverter {

    static ObjectMapper mapper = new ObjectMapper();

    static {
        mapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
    }

    public static ObjectMapper instance() {
        return mapper;
    }

    public static JavaType getCollectionJavaType(Class<?> type) {
        return mapper.getTypeFactory().constructCollectionType(List.class, type);
    }

    public static <T> Map<String, Object> toMap(T bean) {
        return mapper.convertValue(bean, new TypeReference<Map<String, Object>>() {});
    }

    public static <T> T toBean(InputStream inputStream, Type type) {
        try {
            return mapper.readValue(inputStream, mapper.constructType(type));
        }
        catch (IOException e) {
            throw new JsonConvertException(e, type);
        }
    }

    public static <T> T toGenericBean(byte[] bytes, Type type) {
        try {
            JavaType javaType = mapper.constructType(type);
            return mapper.readValue(bytes, javaType);
        }
        catch (IOException e) {
            throw new JsonConvertException(e);
        }
    }

    public static <T> T toGenericBean(InputStream inputStream, Type type) {
        try {
            JavaType javaType = mapper.constructType(type);
            return mapper.readValue(inputStream, javaType);
        }
        catch (IOException e) {
            throw new JsonConvertException(e);
        }
    }

    public static <T> T toGenericBean(Object object, JavaType javaType) {
        try {
            return mapper.readValue(toJsonBytes(object), javaType);
        }
        catch (IOException e) {
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
        }
        catch (JsonProcessingException e) {
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
        return toJsonBytes(obj, StandardCharsets.UTF_8);
    }


    /**
     * Object to json string byte array.
     *
     * @param obj obj
     * @return json string byte array
     * @throws JsonConvertException if transfer failed
     */
    public static byte[] toJsonBytes(Object obj, Charset charset) {
        try {
            String jsonStr = mapper.writeValueAsString(obj);
            if (jsonStr == null) {
                return new byte[0];
            }
            return jsonStr.getBytes(charset);
        }
        catch (JsonProcessingException e) {
            throw new JsonConvertException(e, obj.getClass());
        }
    }

    public static <T> List<T> unmarshalToList(Class<T> returnType, JsonNode root) throws IOException {
        CollectionType javaType = mapper.getTypeFactory().constructCollectionType(List.class, returnType);
        ObjectReader reader = mapper.readerFor(javaType);
        return reader.readValue(root);
    }

    public static JsonNode unmarshal(byte[] jsonString) throws IOException {
        return mapper.readTree(jsonString);
    }
}
