package cn.vika.client.datasheet.model;

/**
 * record detail
 *
 * @author Zoe Zheng
 * @date 2020-12-16 14:10:31
 */
public class RecordPageInfo {
    /**
     * page number
     */
    private Integer pageNum;

    private Integer pageSize;

    private Integer total;

    private RecordDetail[] records;

    public Integer getPageNum() {
        return pageNum;
    }

    public void setPageNum(Integer pageNum) {
        this.pageNum = pageNum;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }

    public RecordDetail[] getRecords() {
        return records;
    }

    public void setRecords(RecordDetail[] records) {
        this.records = records;
    }
}
