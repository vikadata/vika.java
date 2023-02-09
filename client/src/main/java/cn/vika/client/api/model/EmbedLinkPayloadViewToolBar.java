package cn.vika.client.api.model;

public class EmbedLinkPayloadViewToolBar {

    private boolean basicTools;

    private boolean shareBtn;

    private boolean widgetBtn;

    private boolean apiBtn;

    private boolean formBtn;

    private boolean historyBtn;

    private  boolean robotBtn;

    public boolean isBasicTools() {
        return basicTools;
    }

    public void setBasicTools(boolean basicTools) {
        this.basicTools = basicTools;
    }

    public boolean isShareBtn() {
        return shareBtn;
    }

    public void setShareBtn(boolean shareBtn) {
        this.shareBtn = shareBtn;
    }

    public boolean isWidgetBtn() {
        return widgetBtn;
    }

    public void setWidgetBtn(boolean widgetBtn) {
        this.widgetBtn = widgetBtn;
    }

    public boolean isApiBtn() {
        return apiBtn;
    }

    public void setApiBtn(boolean apiBtn) {
        this.apiBtn = apiBtn;
    }

    public boolean isFormBtn() {
        return formBtn;
    }

    public void setFormBtn(boolean formBtn) {
        this.formBtn = formBtn;
    }

    public boolean isHistoryBtn() {
        return historyBtn;
    }

    public void setHistoryBtn(boolean historyBtn) {
        this.historyBtn = historyBtn;
    }

    public boolean isRobotBtn() {
        return robotBtn;
    }

    public void setRobotBtn(boolean robotBtn) {
        this.robotBtn = robotBtn;
    }
}
