package dto;

import java.util.List;

/**
 * Created by scott on 2017/4/17.
 */
public class PaginationResultDTO<T> implements java.io.Serializable{
    private List<T> result;
    private int size;
    private int page;
    private int totalCount;

    public PaginationResultDTO() {
    }

    public PaginationResultDTO(OrderablePaginationDTO op, List<T> result) {
        this.setResult(result);
        if(op != null){
            this.setPage(op.getPage());
            this.setSize(op.getSize());
            this.setTotalCount(op.getTotalCount());
        } else{
            this.setTotalCount(0);
        }

    }

    public List<T> getResult() {
        return result;
    }

    public void setResult(List<T> result) {
        this.result = result;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(int totalCount) {
        this.totalCount = totalCount;
    }
}
