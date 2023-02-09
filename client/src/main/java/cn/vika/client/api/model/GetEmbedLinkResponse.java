package cn.vika.client.api.model;

import java.util.List;

public class GetEmbedLinkResponse {

    private String linkId;

    private String url;

    private EmbedLinkPayload payload;

    private EmbedLinkThemeEnum theme;

    public String getLinkId() {
        return linkId;
    }

    public void setLinkId(String linkId) {
        this.linkId = linkId;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public EmbedLinkPayload getPayload() {
        return payload;
    }

    public void setPayload(EmbedLinkPayload payload) {
        this.payload = payload;
    }

    public EmbedLinkThemeEnum getTheme() {
        return theme;
    }

    public void setTheme(EmbedLinkThemeEnum theme) {
        this.theme = theme;
    }
}
