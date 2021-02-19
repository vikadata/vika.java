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

package cn.vika.client.api.datasheet;

import java.io.ByteArrayOutputStream;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Map;
import java.util.UUID;

import cn.vika.client.api.exception.ApiException;
import cn.vika.client.api.http.ApiHttpClient;
import cn.vika.client.api.model.AbstractModel;
import cn.vika.client.api.model.HttpResult;
import cn.vika.client.api.http.AbstractApi;
import cn.vika.core.http.GenericTypeReference;
import cn.vika.core.http.HttpHeader;
import cn.vika.core.http.HttpMediaType;
import net.sf.jmimemagic.Magic;
import net.sf.jmimemagic.MagicMatch;

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

    public AttachmentApi(ApiHttpClient apiHttpClient) {
        super(apiHttpClient);
    }

    /**
     * upload datasheet attachment
     *
     * @param params add attachment data
     * @param responseType response type
     * @return responseType
     */
    @Override
    public <T> T uploadAttachment(String datasheetId, AbstractModel params, GenericTypeReference<HttpResult<T>> responseType) throws ApiException {
        HttpHeader header = HttpHeader.EMPTY;
        String boundary = UUID.randomUUID().toString();
        header.setContentType("multipart/form-data; charset=utf-8" + "; boundary=" + boundary);
        HttpResult<T> result;
        try {
            byte[] binary = getMultipartPayload(params, boundary);
            result = getDefaultHttpClient().upload(basePath(datasheetId), HttpHeader.EMPTY, binary, responseType);
        }
        catch (Exception e) {
            throw new ApiException(e);
        }
        if (result.isSuccess()) {
            return result.getData();
        }
        throw new ApiException(result.getCode(), result.getMessage());
    }

    @Override
    protected String basePath(String datasheetId) {
        return String.format(PATH, datasheetId);
    }

    /**
     *
     * @param params attachment data
     * @param boundary unique key
     * @return byte[]
     * @throws Exception IO exception maybe
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
