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

import java.io.File;

import cn.vika.client.api.models.AttachmentInfo;
import cn.vika.core.http.ClassPathResourceLoader;
import cn.vika.core.http.FileResourceLoader;
import cn.vika.core.http.UrlResourceLoader;
import cn.vika.core.utils.UrlUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import static cn.vika.client.api.ConstantKey.TEST_DATASHEET_ID;
import static org.assertj.core.api.Assertions.assertThat;

/**
 * attachment test
 *
 * @author Zoe Zheng
 * @date 2020-12-17 18:49:24
 */
@TestMethodOrder(OrderAnnotation.class)
public class AttachmentTest extends BaseTest {

    private static VikaApiClient vikaApiClient;

    @BeforeAll
    public static void setup() {
        vikaApiClient = testInitApiClient();
    }

    @Test
    @Order(1)
    public void testUploadWithClassPathResource() throws JsonProcessingException {
        AttachmentInfo attachmentInfo = vikaApiClient.getAttachmentApi().upload(TEST_DATASHEET_ID.get(), new ClassPathResourceLoader("test.txt"));
        assertThat(attachmentInfo).isNotNull();
        System.out.println(JacksonJsonUtil.toJson(attachmentInfo, true));
    }

    @Test
    @Order(2)
    public void testUploadWithUrlResource() throws JsonProcessingException {
        AttachmentInfo attachmentInfo = vikaApiClient.getAttachmentApi().upload(TEST_DATASHEET_ID.get(), new UrlResourceLoader(UrlUtil.url("https://ss1.bdstatic.com/70cFuXSh_Q1YnxGkpoWK1HF6hhy/it/u=3483500324,2196746779&fm=26&gp=0.jpg")));
        assertThat(attachmentInfo).isNotNull();
        System.out.println(JacksonJsonUtil.toJson(attachmentInfo, true));
    }

    @Test
    @Order(3)
    public void testUploadWithFileResource() throws JsonProcessingException {
        File file = new File("/Users/shawndeng/Documents/Project/vika.java/client/src/test/resources/test.docx");
        AttachmentInfo attachmentInfo = vikaApiClient.getAttachmentApi().upload(TEST_DATASHEET_ID.get(), new FileResourceLoader(file));
        assertThat(attachmentInfo).isNotNull();
        System.out.println(JacksonJsonUtil.toJson(attachmentInfo, true));
    }

    @Test
    @Order(4)
    public void testUploadWithFile() throws JsonProcessingException {
        File file = new File("/Users/shawndeng/Documents/Project/vika.java/client/src/test/resources/test.docx");
        AttachmentInfo attachmentInfo = vikaApiClient.getAttachmentApi().upload(TEST_DATASHEET_ID.get(), file);
        assertThat(attachmentInfo).isNotNull();
        System.out.println(JacksonJsonUtil.toJson(attachmentInfo, true));
    }

}
