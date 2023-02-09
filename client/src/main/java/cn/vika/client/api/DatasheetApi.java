package cn.vika.client.api;

import cn.vika.client.api.exception.ApiException;
import cn.vika.client.api.http.AbstractApi;
import cn.vika.client.api.http.ApiHttpClient;
import cn.vika.client.api.model.*;
import cn.vika.core.http.GenericTypeReference;
import cn.vika.core.http.HttpHeader;
import cn.vika.core.utils.StringUtil;

import java.util.List;

/**
 * @author tao
 */
public class DatasheetApi extends AbstractApi {

    private static final String POST_DATASHEET_PATH = "/spaces/%s/datasheets";

    private static final String POST_EMBED_LINK_PATH = "/spaces/%s/nodes/%s/embedlinks";

    private static final String GET_EMBED_LINK_PATH = "/spaces/%s/nodes/%s/embedlinks";

    private static final String DELETE_EMBED_LINK_PATH = "/spaces/%s/nodes/%s/embedlinks/%s";

    public DatasheetApi(ApiHttpClient apiHttpClient) {
        super(apiHttpClient);
    }

    public CreateDatasheetResponse addDatasheet(String spaceId,
            CreateDatasheetRequest datasheet) {

        checkPostDatasheetPathArgs(spaceId);

        final String path = String.format(POST_DATASHEET_PATH, spaceId);

        HttpResult<CreateDatasheetResponse> result = getDefaultHttpClient().post(
                path, new HttpHeader(), datasheet,
                new GenericTypeReference<HttpResult<CreateDatasheetResponse>>() {});
        return result.getData();
    }

    public CreateEmbedLinkResponse addEmbedLink(String spaceId, String datasheetId, CreateEmbedLinkRequest embedLink){
        // check create embed link params
        checkEmbedLinkPathArgs(spaceId, datasheetId);

        final String path = String.format(POST_EMBED_LINK_PATH, spaceId, datasheetId);

        HttpResult<CreateEmbedLinkResponse> result = getDefaultHttpClient().post(path, new HttpHeader(), embedLink,
            new GenericTypeReference<HttpResult<CreateEmbedLinkResponse>>() {});
        return result.getData();
    }

    public List<GetEmbedLinkResponse> getEmbedLink(String spaceId, String datasheetId){
        // check get embed link params
        checkEmbedLinkPathArgs(spaceId, datasheetId);

        final String path = String.format(GET_EMBED_LINK_PATH, spaceId, datasheetId);

        HttpResult<List<GetEmbedLinkResponse>> result = getDefaultHttpClient().get(path, new HttpHeader(),
            new GenericTypeReference<HttpResult<List<GetEmbedLinkResponse>>>() {});

        return result.getData();
    }

    public void deleteEmbedLink(String spaceId, String datasheetId, String embedLinkId){
        // check delete embed link params
        checkDeleteEmbedLinkPathArgs(spaceId, datasheetId, embedLinkId);

        final String path = String.format(DELETE_EMBED_LINK_PATH, spaceId, datasheetId, embedLinkId);

        getDefaultHttpClient().delete(path, new HttpHeader(), Void.class);
    }


    private void checkPostDatasheetPathArgs(String spaceId) {
        if (!StringUtil.hasText(spaceId)) {
            throw new ApiException("space id must be not null");
        }
    }

    private void checkEmbedLinkPathArgs(String spaceId, String datasheetId) {
        if (!StringUtil.hasText(spaceId)) {
            throw new ApiException("space id must be not null");
        }
        if (!StringUtil.hasText(datasheetId)){
            throw new ApiException("datasheet id must be not null");
        }
    }

    private void checkDeleteEmbedLinkPathArgs(String spaceId, String datasheetId, String embedLinkId) {
        if (!StringUtil.hasText(spaceId)) {
            throw new ApiException("space id must not be null");
        }
        if (!StringUtil.hasText(datasheetId)) {
            throw new ApiException("datasheet id must not be null");
        }
        if (!StringUtil.hasText(embedLinkId)) {
            throw new ApiException("fieldId id must not be null");
        }
    }

}
