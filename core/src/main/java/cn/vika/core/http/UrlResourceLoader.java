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
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLConnection;

import cn.vika.core.utils.IoUtil;
import cn.vika.core.utils.StringUtil;

/**
 * Url resource loader
 * @author Shawn Deng
 * @date 2021-02-23 10:13:35
 */
public class UrlResourceLoader implements ResourceLoader {

    private static final String URL_PROTOCOL_FILE = "file";

    private static final String URL_PROTOCOL_VFSFILE = "vfsfile";

    private static final String URL_PROTOCOL_VFS = "vfs";

    protected URL url;

    protected String name;

    public UrlResourceLoader(URL url) {
        this(url, null);
    }

    public UrlResourceLoader(URL url, String name) {
        this.url = url;
        if (StringUtil.hasText(name)) {
            this.name = name;
        }
        else {
            this.name = url != null ? StringUtil.getName(url.getPath()) : null;
        }
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public URL getUrl() {
        return this.url;
    }

    @Override
    public InputStream getInputStream() throws IOException {
        if (null == this.url) {
            throw new RuntimeException("URL is null!");
        }
        return url.openStream();
    }

    @Override
    public byte[] readBytes() throws IOException {
        InputStream in = null;
        try {
            in = getInputStream();
            return IoUtil.readBytes(in);
        }
        finally {
            IoUtil.close(in);
        }
    }

    @Override
    public long contentLength() throws IOException {
        if (isFileURL(this.url)) {
            // Proceed with file system resolution
            File file = getFile();
            long length = file.length();
            if (length == 0L && !file.exists()) {
                throw new FileNotFoundException("URL[ " + this.url + " ] cannot be resolved in the file system for checking its content length");
            }
            return length;
        }
        else {
            // Try a URL connection content-length header
            URLConnection con = url.openConnection();
            setConnection(con);
            return con.getContentLengthLong();
        }
    }

    protected void setConnection(URLConnection con) throws IOException {
        con.setUseCaches(con.getClass().getSimpleName().startsWith("JNLP"));
        if (con instanceof HttpURLConnection) {
            ((HttpURLConnection) con).setRequestMethod("HEAD");
        }
    }

    public boolean isFileURL(URL url) {
        String protocol = url.getProtocol();
        return (URL_PROTOCOL_FILE.equals(protocol) || URL_PROTOCOL_VFSFILE.equals(protocol) ||
                URL_PROTOCOL_VFS.equals(protocol));
    }

    public File getFile() {
        try {
            return new File(new URI(this.url.toString()));
        }
        catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public String toString() {
        return (null == this.url) ? "null" : this.url.toString();
    }
}
