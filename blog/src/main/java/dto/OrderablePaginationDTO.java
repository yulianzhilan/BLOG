package dto;

import java.util.List;

/**
 * Created by scott on 2017/4/17.
 */
public class OrderablePaginationDTO implements java.io.Serializable{
    private int size;

    private int page;

    private int totalCount;

    private List<OrderDTO> orders;

    public OrderablePaginationDTO() {
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

    public List<OrderDTO> getOrders() {
        return orders;
    }

    public void setOrders(List<OrderDTO> orders) {
        this.orders = orders;
    }

    public static class OrderDTO{
        private String propertyName;

        private String descending;

        public OrderDTO() {
        }

        public String getPropertyName() {
            return propertyName;
        }

        public void setPropertyName(String propertyName) {
            this.propertyName = propertyName;
        }
    }
}
