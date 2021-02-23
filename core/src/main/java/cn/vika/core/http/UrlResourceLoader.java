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
