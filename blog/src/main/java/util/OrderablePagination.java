package util;

import dto.Sorter;
import org.springframework.util.ObjectUtils;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by scott on 2017/4/17.
 */
public class OrderablePagination extends Pagination implements java.io.Serializable{

    private static final long serialVersionUID = 2586767986756329908L;

    private List<Sorter> sorters;

    public OrderablePagination(int totalCount, int startIndex, int pageSize) {
        super(totalCount, startIndex, pageSize);
        this.sorters = new LinkedList<Sorter>();
    }

    public OrderablePagination(Pagination pagination) {
        this(pagination.getTotalCount(), pagination.getStartIndex(), pagination.getPageSize());
    }

    public void addSorters(Sorter... sorters) {
        if (!ObjectUtils.isEmpty(sorters)) {
            for (Sorter sorter : sorters) {
                if (sorter != null && !this.sorters.contains(sorter)) {
                    this.sorters.add(sorter);
                }
            }
        }
    }

    public void removeSorter(Sorter sorter) {
        this.sorters.remove(sorter);
    }

    public void removeAllSorters() {
        this.sorters.clear();
    }

    public Sorter[] getSorters() {
        return this.sorters.toArray(new Sorter[this.sorters.size()]);
    }

    public boolean hasSorter() {
            return this.sorters.size() > 0;
        }

}
