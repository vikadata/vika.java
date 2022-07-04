package cn.vika.core.utils;

import java.util.LinkedHashMap;
import java.util.Map;

import org.junit.jupiter.api.Test;

import static cn.vika.core.utils.UrlEncoder.encodeURIComponent;
import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author Shawn Deng
 */
public class MapUtilTest {

    @Test
    public void testExtractKeyToVariables() {
        Map<String, String> uriVariables = new LinkedHashMap<>(6);
        uriVariables.put("page", "1");
        uriVariables.put("pageSize", "10");
        uriVariables.put(encodeURIComponent("sort[].0"), encodeURIComponent("{\"field\":\"id\",\"order\":\"desc\"}"));
        uriVariables.put(encodeURIComponent("sort[].1"), encodeURIComponent("{\"field\":\"name\",\"order\":\"desc\"}"));
        uriVariables.put("recordIds.0", "rec0");
        uriVariables.put("recordIds.1", "rec1");
        String urlParam = MapUtil.extractKeyToVariables(uriVariables);
        assertThat(urlParam).isEqualTo("?page={page}&pageSize={pageSize}&sort%5B%5D={sort%5B%5D.0}&sort%5B%5D={sort%5B%5D.1}&recordIds={recordIds.0}&recordIds={recordIds.1}");
    }
}
