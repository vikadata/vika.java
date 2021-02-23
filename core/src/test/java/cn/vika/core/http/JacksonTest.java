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

package cn.vika.core.http;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

import cn.vika.core.utils.JacksonConverter;
import cn.vika.core.utils.StringUtil;
import com.fasterxml.jackson.databind.JsonNode;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 *
 * @author Shawn Deng
 * @date 2021-02-20 22:01:52
 */
public class JacksonTest {

    @Test
    public void testStringToJsonNode() throws IOException {
        String jsonString = "\"code\":200,\"success\":true,\"message\":\"SUCCESS\"}";
        System.out.println(jsonString);
        System.out.println(StringUtil.wrap(jsonString, "{"));
        String json = StringUtil.wrap(jsonString, "{");
        JsonNode jsonNode = JacksonConverter.unmarshal(json.getBytes(StandardCharsets.UTF_8));
        JsonNode success = jsonNode.get("success");
        assertThat(success.asBoolean()).isTrue();
    }
}
