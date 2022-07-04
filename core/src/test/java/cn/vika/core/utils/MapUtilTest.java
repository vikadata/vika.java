package cn.vika.core.utils;

import java.util.Map;

import org.assertj.core.util.Maps;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author Shawn Deng
 */
public class MapUtilTest {

    @Test
    public void testExtractKeyToVariables() {
        Map<String, String> uriVariables = Maps.newHashMap("page", "1");
        uriVariables.put("pageSize", "10");
        uriVariables.put("sort[].0", "{\"field\":\"id\",\"order\":\"desc\"}");
        uriVariables.put("sort[].1", "{\"field\":\"name\",\"order\":\"desc\"}");
        uriVariables.put("recordIds.0", "rec0");
        uriVariables.put("recordIds.1", "rec1");
        String urlParam = MapUtil.extractKeyToVariables(uriVariables);
        assertThat(urlParam).isEqualTo("?recordIds={recordIds.0}&sort[]={sort[].0}&sort[]={sort[].1}&recordIds={recordIds.1}&pageSize={pageSize}&page={page}");
    }
}
