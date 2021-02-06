/*
 * Copyright (C) 2021 vikadata
 *
 * This program is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; either version 2 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License along
 * with this program; if not, write to the Free Software Foundation, Inc.,
 * 51 Franklin Street, Fifth Floor, Boston, MA 02110-1301 USA.
 */

package cn.vika.core.utils;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Type;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.List;

import cn.vika.core.exception.JsonConvertException;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;

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

    public static ObjectMapper instance() {
        return mapper;
    }

    public static JavaType getCollectionJavaType(Class<?> type) {
        return mapper.getTypeFactory().constructCollectionType(List.class, type);
    }

    public static <T> T toBean(InputStream inputStream, Type type) {
        try {
            return mapper.readValue(inputStream, mapper.constructType(type));
        }
        catch (IOException e) {
            throw new JsonConvertException(e, type);
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

    public static <T> T toGenericBean(InputStream inputStream, Class<?> genericClass, Class<?> contentClass) {
        try {
            JavaType javaType = mapper.getTypeFactory().constructParametricType(genericClass, contentClass);
            return mapper.readValue(inputStream, javaType);
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
        return toJsonBytes(obj, StandardCharsets.UTF_8.name());
    }


    /**
     * Object to json string byte array.
     *
     * @param obj obj
     * @return json string byte array
     * @throws JsonConvertException if transfer failed
     */
    public static byte[] toJsonBytes(Object obj, String charset) {
        try {
            String jsonStr = mapper.writeValueAsString(obj);
            if (jsonStr == null) {
                return new byte[0];
            }
            return jsonStr.getBytes(Charset.forName(charset));
        }
        catch (JsonProcessingException e) {
            throw new JsonConvertException(e, obj.getClass());
        }
    }
}
