package cn.vika.client.api.model;

public class EmbedLinkPayloadViewControl {

    private String viewId;

    private boolean tabBar;

    private EmbedLinkPayloadViewToolBar toolBar;

    private boolean collapsed;

    public String getViewId() {
        return viewId;
    }

    public void setViewId(String viewId) {
        this.viewId = viewId;
    }

    public boolean isTabBar() {
        return tabBar;
    }

    public void setTabBar(boolean tabBar) {
        this.tabBar = tabBar;
    }

    public EmbedLinkPayloadViewToolBar getToolBar() {
        return toolBar;
    }

    public void setToolBar(EmbedLinkPayloadViewToolBar toolBar) {
        this.toolBar = toolBar;
    }

    public boolean isCollapsed() {
        return collapsed;
    }

    public void setCollapsed(boolean collapsed) {
        this.collapsed = collapsed;
    }
}
