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

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import cn.vika.core.exception.HttpClientException;
import cn.vika.core.utils.AssertUtil;
import cn.vika.core.utils.IoUtil;
import cn.vika.core.utils.JacksonConverter;
import cn.vika.core.utils.RandomUtil;
import net.sf.jmimemagic.Magic;
import net.sf.jmimemagic.MagicException;
import net.sf.jmimemagic.MagicMatch;
import net.sf.jmimemagic.MagicMatchNotFoundException;
import net.sf.jmimemagic.MagicParseException;

import static cn.vika.core.http.HttpMediaType.APPLICATION_FORM_URLENCODED;
import static cn.vika.core.http.HttpMediaType.APPLICATION_OCTET_STREAM;
import static cn.vika.core.http.HttpMediaType.MULTIPART_FORM_DATA;

/**
 *
 * @author Shawn Deng
 * @date 2021-02-24 15:54:34
 */
public class RequestBodyWrapper extends OnlyHeaderWrapper{

    private final Object requestBody;

    private final List<String> formDataMediaType = new ArrayList<>();

    public RequestBodyWrapper(HttpHeader header, Object requestBody) {
        super(header);
        this.requestBody = requestBody;

        this.formDataMediaType.add(MULTIPART_FORM_DATA);
        this.formDataMediaType.add(APPLICATION_FORM_URLENCODED);
        this.formDataMediaType.add(APPLICATION_OCTET_STREAM);
    }

    @Override
    public void wrapper(ClientHttpRequest request) throws IOException {
        super.wrapper(request);
        if (requestBody != null) {
            Class<?> requestBodyClass = requestBody.getClass();
            HttpHeader requestHeaders = request.getHeaders();
            String requestContentType = requestHeaders.getContentType();
            if (isFormData(requestBodyClass, requestContentType)) {
                if (isMultiPart((FormDataMap) requestBody, requestContentType)) {
                    writeMultiPart(request, (FormDataMap) requestBody);
                }
                else {
                    writeForm(request, (FormDataMap) requestBody);
                }
            }
            else {
                request.getHeaders().setContentType(HttpMediaType.APPLICATION_JSON);
                byte[] content = JacksonConverter.toJsonBytes(requestBody);
                request.getBody().write(content);
            }
        }
    }

    private boolean isFormData(Class<?> clazz, String contentType) {
        if (!FormDataMap.class.isAssignableFrom(clazz)) {
            return false;
        }
        if (contentType == null || HttpMediaType.ALL.equals(contentType)) {
            return true;
        }
        for (String mediaType : formDataMediaType) {
            if (mediaType.equalsIgnoreCase(contentType)) {
                return true;
            }
        }
        return false;
    }

    private boolean isMultiPart(FormDataMap mapData, String contentType) {
        if (contentType != null) {
            return contentType.startsWith("multipart");
        }
        for (Object value : mapData.values()) {
            if (value != null && !(value instanceof String)) {
                return true;
            }
        }
        return false;
    }

    private void writeMultiPart(ClientHttpRequest request, FormDataMap parts) throws IOException {
        Map<String, String> parameters = new LinkedHashMap<>();
        parameters.put("charset", StandardCharsets.UTF_8.name());
        byte[] boundary = RandomUtil.generateMultipartBoundary();
        parameters.put("boundary", new String(boundary, StandardCharsets.US_ASCII));
        StringBuilder contentType = new StringBuilder(MULTIPART_FORM_DATA);
        parameters.forEach((key, value) -> contentType.append(';').append(key).append("=").append(value));
        request.getHeaders().setContentType(contentType.toString());
        OutputStream body = request.getBody();
        writeParts(body, parts, boundary);
        writeEnd(body, boundary);
        // don't forget flush body content
        body.flush();
    }

    private void writeForm(ClientHttpRequest request, FormDataMap formData) throws IOException {
        StringBuilder builder = new StringBuilder();
        formData.forEach((name, value) -> {
            if (name == null) {
                AssertUtil.isTrue(value != null, "Null name in form data: " + formData);
                return;
            }
            try {
                if (builder.length() != 0) {
                    builder.append('&');
                }
                builder.append(URLEncoder.encode(name, StandardCharsets.UTF_8.name()));
                if (value != null) {
                    builder.append('=');
                    builder.append(URLEncoder.encode(String.valueOf(value), StandardCharsets.UTF_8.name()));
                }
            }
            catch (UnsupportedEncodingException ex) {
                throw new IllegalStateException(ex);
            }
        });
        byte[] bytes = builder.toString().getBytes(StandardCharsets.UTF_8);
        request.getHeaders().setContentLength(bytes.length);
        IoUtil.copy(bytes, request.getBody());
    }

    private void writeParts(OutputStream os, Map<String, Object> parts, byte[] boundary) throws IOException {
        for (Entry<String, Object> entry : parts.entrySet()) {
            Object part = entry.getValue();
            if (part != null) {
                // write boundary
                writeBoundary(os, boundary);
                // write multi part
                writePart(os, entry.getKey(), part);
                // write new line
                writeNewLine(os);
            }
        }
    }

    private void writePart(OutputStream os, String name, Object partBody) throws IOException {
        if (partBody == null) {
            throw new IllegalStateException("Empty body for part '" + name + "'");
        }
        HttpHeader httpHeader = new HttpHeader();
        if (partBody instanceof File) {
            File file = (File) partBody;
            httpHeader.setContentDispositionFormData(name, file.getName());
            defaultHeadersIfFileType(httpHeader, file);
            writeHeaders(os, httpHeader);
            byte[] data = Files.readAllBytes(file.toPath());
            IoUtil.copy(data, os);
        }
        else if (partBody instanceof ResourceLoader) {
            ResourceLoader loader = (ResourceLoader) partBody;
            httpHeader.setContentDispositionFormData(name, loader.getName());
            defaultHeadersIfResourceLoader(httpHeader, loader);
            writeHeaders(os, httpHeader);
            IoUtil.copy(loader.getInputStream(), os);
        } else {
            throw new HttpClientException("not support part param key " + name);
        }
    }

    private void writeHeaders(OutputStream os, HttpHeader headers) throws IOException {
        for (Map.Entry<String, List<String>> entry : headers.entrySet()) {
            byte[] headerName = entry.getKey().getBytes(StandardCharsets.UTF_8);
            for (String headerValueString : entry.getValue()) {
                byte[] headerValue = headerValueString.getBytes(StandardCharsets.UTF_8);
                os.write(headerName);
                os.write(':');
                os.write(' ');
                os.write(headerValue);
                writeNewLine(os);
            }
        }
        writeNewLine(os);
    }

    private void writeBoundary(OutputStream os, byte[] boundary) throws IOException {
        os.write('-');
        os.write('-');
        os.write(boundary);
        writeNewLine(os);
    }

    private void writeNewLine(OutputStream os) throws IOException {
        os.write('\r');
        os.write('\n');
    }

    private void writeEnd(OutputStream os, byte[] boundary) throws IOException {
        os.write('-');
        os.write('-');
        os.write(boundary);
        os.write('-');
        os.write('-');
        writeNewLine(os);
    }

    private void defaultHeadersIfFileType(HttpHeader headers, File file) throws IOException {
        if (headers.getContentType() == null) {
            String fileContentType = getFileMimeType(file);
            if (fileContentType != null) {
                headers.setContentType(fileContentType);
            }
        }
        // Set Content-Length
        if (headers.getContentLength() < 0 && !headers.containsKey(HttpHeader.TRANSFER_ENCODING)) {
            Long contentLength = getContentLength(file);
            if (contentLength != null) {
                headers.setContentLength(contentLength);
            }
        }
    }

    private void defaultHeadersIfResourceLoader(HttpHeader headers, ResourceLoader loader) throws IOException {
        if (headers.getContentType() == null) {
            String fileContentType = getFileMimeType(loader);
            if (fileContentType != null) {
                headers.setContentType(fileContentType);
            }
        }
        // Set Content-Length
        if (headers.getContentLength() < 0 && !headers.containsKey(HttpHeader.TRANSFER_ENCODING)) {
            Long contentLength = getContentLength(loader);
            if (contentLength != null) {
                headers.setContentLength(contentLength);
            }
        }
    }

    private String getFileMimeType(File file) throws IOException {
        try {
            MagicMatch match = Magic.getMagicMatch(file, false);
            return match.getMimeType();
        }
        catch (MagicParseException | MagicMatchNotFoundException | MagicException e) {
            return null;
        }
    }

    private String getFileMimeType(ResourceLoader loader) throws IOException {
        try {
            MagicMatch match = Magic.getMagicMatch(loader.readBytes(), false);
            return match.getMimeType();
        }
        catch (MagicParseException | MagicMatchNotFoundException | MagicException e) {
            return null;
        }
    }

    private Long getContentLength(File file) throws IOException {
        long contentLength = file.length();
        return (contentLength < 0 ? null : contentLength);
    }

    private Long getContentLength(ResourceLoader loader) throws IOException {
        long contentLength = loader.contentLength();
        return (contentLength < 0 ? null : contentLength);
    }
}
