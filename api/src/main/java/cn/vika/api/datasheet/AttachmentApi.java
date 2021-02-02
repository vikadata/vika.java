package cn.vika.api.datasheet;

import java.io.ByteArrayOutputStream;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Map;
import java.util.UUID;

import cn.vika.api.exception.VikaApiException;
import cn.vika.api.http.AbstractApi;
import cn.vika.api.http.ApiCredential;
import cn.vika.api.http.ApiHttpClient;
import cn.vika.api.model.AbstractModel;
import cn.vika.core.http.GenericTypeReference;
import cn.vika.core.http.HttpHeader;
import cn.vika.core.http.HttpMediaType;
import cn.vika.core.model.HttpResult;
import net.sf.jmimemagic.Magic;
import net.sf.jmimemagic.MagicMatch;

import static cn.vika.api.exception.VikaApiException.DEFAULT_CODE;

/**
 * test
 *
 * @author Zoe Zheng
 * @date 2020-12-17 16:25:42
 */
public class AttachmentApi extends AbstractApi implements IAttachmentApi {
    private static final String PATH = "/datasheets/%s/attachments";

    /**
     * datasheetId
     */
    private final String datasheetId;

    public AttachmentApi(ApiCredential credential, String datasheetId) {
        super(credential);
        this.datasheetId = datasheetId;
    }

    public AttachmentApi(ApiCredential credential, ApiHttpClient httpClient, String datasheetId) {
        super(credential, httpClient);
        this.datasheetId = datasheetId;
    }

    /**
     * upload datasheet attachment
     *
     * @param params add attachment data
     * @param responseType response type
     * @return responseType
     */
    @Override
    public <T> T uploadAttachment(AbstractModel params, GenericTypeReference<HttpResult<T>> responseType) {
        HttpHeader header = HttpHeader.EMPTY;
        String boundary = UUID.randomUUID().toString();
        header.setContentType("multipart/form-data; charset=utf-8" + "; boundary=" + boundary);
        HttpResult<T> result;
        try {
            byte[] binary = getMultipartPayload(params, boundary);
            result = getDefaultHttpClient().upload(basePath(), HttpHeader.EMPTY, binary, responseType);
        }
        catch (Exception e) {
            throw new VikaApiException(DEFAULT_CODE, e.getMessage());
        }
        if (result.isSuccess()) {
            return result.getData();
        }
        throw new VikaApiException(result.getCode(), result.getMessage());
    }

    @Override
    protected String basePath() {
        return String.format(PATH, datasheetId);
    }

    /**
     *
     * @param params attachment data
     * @param boundary unique key
     * @return byte[]
     * @throws Exception
     */
    private byte[] getMultipartPayload(AbstractModel params, String boundary) throws Exception {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        String[] binaryParams = params.getBinaryParams();
        for (Map.Entry<String, byte[]> entry : params.getMultipartRequestParams().entrySet()) {
            baos.write("--".getBytes(StandardCharsets.UTF_8));
            baos.write(boundary.getBytes(StandardCharsets.UTF_8));
            baos.write("\r\n".getBytes(StandardCharsets.UTF_8));
            baos.write("Content-Disposition: form-data; name=\"".getBytes(StandardCharsets.UTF_8));
            // binary
            if (Arrays.asList(binaryParams).contains(entry.getKey())) {
                baos.write(entry.getKey().getBytes(StandardCharsets.UTF_8));
                baos.write("\"; filename=\"".getBytes(StandardCharsets.UTF_8));
                baos.write(params.getBinaryParamNames().get(entry.getKey()));
                baos.write("\"\r\n".getBytes(StandardCharsets.UTF_8));
                baos.write("Content-Type:".getBytes(StandardCharsets.UTF_8));
                MagicMatch match = Magic.getMagicMatch(entry.getValue(), false);
                if (match != null) {
                    baos.write(match.getMimeType().getBytes(StandardCharsets.UTF_8));
                }
                else {
                    baos.write(HttpMediaType.APPLICATION_OCTET_STREAM.getBytes(StandardCharsets.UTF_8));
                }
                baos.write(";charset=utf-8".getBytes(StandardCharsets.UTF_8));
                baos.write("\r\n".getBytes(StandardCharsets.UTF_8));
            }
            else {
                baos.write(entry.getKey().getBytes(StandardCharsets.UTF_8));
                baos.write("\"\r\n".getBytes(StandardCharsets.UTF_8));
            }
            baos.write("\r\n".getBytes(StandardCharsets.UTF_8));
            baos.write(entry.getValue());
            baos.write("\r\n".getBytes(StandardCharsets.UTF_8));
        }
        if (baos.size() != 0) {
            baos.write("--".getBytes(StandardCharsets.UTF_8));
            baos.write(boundary.getBytes(StandardCharsets.UTF_8));
            baos.write("--\r\n".getBytes(StandardCharsets.UTF_8));
        }
        byte[] bytes = baos.toByteArray();
        baos.close();
        return bytes;
    }
}
