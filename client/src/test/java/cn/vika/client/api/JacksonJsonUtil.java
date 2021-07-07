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

package cn.vika.client.api;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectReader;
import com.fasterxml.jackson.databind.type.CollectionType;
import org.junit.jupiter.api.Assertions;

/**
 * Jackson Util for unit test class
 * @author Shawn Deng
 * @date 2021-02-19 13:59:54
 */
public class JacksonJsonUtil {

    private static final ObjectMapper objectMapper;

    static {
        objectMapper = new ObjectMapper();
        objectMapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
        objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);

    }

    static <T> List<T> unmarshalList(Class<T> returnType, Reader reader) throws IOException {
        CollectionType javaType = objectMapper.getTypeFactory().constructCollectionType(List.class, returnType);
        return objectMapper.readValue(reader, javaType);
    }

    static <T> List<T> unmarshalInputStreamToList(Class<T> returnType, InputStream in) throws IOException {
        Assertions.assertNotNull(in);
        InputStreamReader reader = new InputStreamReader(in);
        return unmarshalList(returnType, reader);
    }

    static <T> List<T> unmarshalJsonNodeToList(Class<T> returnType, JsonNode root) throws IOException {
        CollectionType javaType = objectMapper.getTypeFactory().constructCollectionType(List.class, returnType);
        ObjectReader reader = objectMapper.readerFor(javaType);
        return reader.readValue(root);
    }

    static String toJson(Object obj, boolean prettyPrint) throws JsonProcessingException {
        return prettyPrint ? objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(obj) : objectMapper.writeValueAsString(obj);
    }
}
