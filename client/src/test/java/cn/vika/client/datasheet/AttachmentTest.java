package cn.vika.client.datasheet;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import cn.vika.api.http.ApiCredential;
import cn.vika.client.datasheet.model.AttachmentInfo;
import cn.vika.client.datasheet.model.AttachmentRequest;

/**
 * attachment test
 *
 * @author Zoe Zheng
 * @date 2020-12-17 18:49:24
 */
public class AttachmentTest {
    @Test
    public void testUploadAttachment() throws IOException {
        ApiCredential credential = new ApiCredential(System.getenv("VIKA_TOKEN"));
        AttachmentRequest param = new AttachmentRequest();
        param.setFileName("维格数表通讯录导入模板.xlsx");
        param.setFile(Files.readAllBytes(Paths.get("/**/**/维格数表通讯录导入模板.xlsx")));
        AttachmentClient attachmentClient = new AttachmentClient(credential, "dst0Yj5aNeoHldqvf6");
        AttachmentInfo attachmentInfo = attachmentClient.uploadAttachment(param);
        Assertions.assertNotNull(attachmentInfo);
    }
}
