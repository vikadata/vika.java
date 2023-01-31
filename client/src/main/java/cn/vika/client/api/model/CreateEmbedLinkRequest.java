package cn.vika.client.api.model;

public class CreateEmbedLinkRequest {

    private EmbedLinkPayload payload;

    private EmbedLinkThemeEnum theme;

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
