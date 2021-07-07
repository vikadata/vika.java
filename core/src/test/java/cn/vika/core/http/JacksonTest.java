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
