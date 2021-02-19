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

package cn.vika.client.api.models;

import java.nio.charset.StandardCharsets;
import java.util.HashMap;

import cn.vika.client.api.model.AbstractModel;

/**
 * Attachment Request
 *
 * @author Zoe Zheng
 * @date 2020-12-17 18:30:44
 */
public class AttachmentRequest extends AbstractModel {
    private String fileName;
    private byte[] file;

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public byte[] getFile() {
        return file;
    }

    public void setFile(byte[] file) {
        this.file = file;
    }

    /**
     * request query param to map
     *
     * @param map param
     * @param prefix prefix
     */
    @Override
    public void toMap(HashMap<String, String> map, String prefix) {
        setParamSimple(map, prefix + "fileName", fileName);
        setParamSimple(map, prefix + "file", file);
    }

    @Override
    public String[] getBinaryParams() {
        return new String[] {"file"};
    }

    @Override
    public HashMap<String, byte[]> getBinaryParamNames() {
        HashMap<String, byte[]> map = new HashMap<>(1);
        if (file != null) {
            map.put("file", fileName.getBytes(StandardCharsets.UTF_8));
        }
        return map;
    }

    @Override
    public HashMap<String, byte[]> getMultipartRequestParams() {
        HashMap<String, byte[]> map = new HashMap<>(2);
        if (file != null) {
            map.put("file", file);
        }

        if (fileName != null) {
            map.put("fileName", fileName.getBytes(StandardCharsets.UTF_8));
        }

        return map;
    }
}
