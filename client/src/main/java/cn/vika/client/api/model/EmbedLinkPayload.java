package cn.vika.client.api.model;

public class EmbedLinkPayload {

    private EmbedLinkPayloadSideBar primarySideBar;

    private EmbedLinkPayloadViewControl viewControl;

    private boolean bannerLogo;

    private EmbedLinkPermissionEnum permissionType;

    public EmbedLinkPayloadSideBar getPrimarySideBar() {
        return primarySideBar;
    }

    public void setPrimarySideBar(EmbedLinkPayloadSideBar primarySideBar) {
        this.primarySideBar = primarySideBar;
    }

    public EmbedLinkPayloadViewControl getViewControl() {
        return viewControl;
    }

    public void setViewControl(EmbedLinkPayloadViewControl viewControl) {
        this.viewControl = viewControl;
    }

    public boolean isBannerLogo() {
        return bannerLogo;
    }

    public void setBannerLogo(boolean bannerLogo) {
        this.bannerLogo = bannerLogo;
    }

    public EmbedLinkPermissionEnum getPermissionType() {
        return permissionType;
    }

    public void setPermissionType(EmbedLinkPermissionEnum permissionType) {
        this.permissionType = permissionType;
    }
}
