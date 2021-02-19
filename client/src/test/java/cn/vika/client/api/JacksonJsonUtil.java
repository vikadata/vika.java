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

package cn.vika.client.api;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
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

    static <T> List<T> unmarshalResourceToList(Class<T> returnType, String filename) throws IOException {
        InputStream in = JacksonJsonUtil.class.getClassLoader().getResourceAsStream(filename);
        Assertions.assertNotNull(in);
        InputStreamReader reader = new InputStreamReader(in);
        return unmarshalList(returnType, reader);
    }
}
