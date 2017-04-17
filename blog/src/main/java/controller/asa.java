package controller;

import dto.OrderablePaginationDTO;
import dto.PaginationResultDTO;
import framework.core.pagination.OrderablePagination;
import framework.core.pagination.Sorter;
import framework.core.utils.HttpRequestUtils;
import framework.web.pagination.AbstractWebPagination;
import framework.web.pagination.KeyboardableWebPagination;
import org.springframework.util.ClassUtils;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by scott on 2017/4/17.
 */

public class PaginationableController extends BaseController {
    protected static final int DEFAULT_PAGE_SIZE = 20;

    public PaginationableController() {
    }

    protected List<?> executeQuery(HttpServletRequest request, PaginationableController.PaginationQueryCallback pqc) throws Exception {
        return this.executeQuery(request, 20, pqc);
    }

    protected List<?> executeQuery(HttpServletRequest request, int size, PaginationableController.PaginationQueryCallback pqc) throws Exception {
        return this.executeQuery(request, AbstractWebPagination.PAGER_ATTRIBUTE, size, KeyboardableWebPagination.class, pqc);
    }

    protected List<?> executeQuery(HttpServletRequest request, String pageAttrName, int size, Class<? extends AbstractWebPagination> clazz, PaginationableController.PaginationQueryCallback pqc) throws Exception {
        OrderablePagination op = this.newOrderablePagination(request, clazz, size);
        List result = pqc.query(op);
        this.bindOrderablePagination(request, pageAttrName, op);
        return result;
    }

    protected OrderablePagination newOrderablePagination(HttpServletRequest request, Class<? extends AbstractWebPagination> paginationClass, int size) throws Exception {
        Constructor c = ClassUtils.getConstructorIfAvailable(paginationClass, new Class[]{String.class, String.class, Integer.TYPE, Integer.TYPE, Integer.TYPE});
        AbstractWebPagination awp = (AbstractWebPagination)c.newInstance(new Object[]{request.getRequestURI(), HttpRequestUtils.generateEncodeQueryString(request.getParameterMap(), GlobalConfig.getDefaultWebEncoding()), HttpRequestUtils.getIntegerParameter(request, AbstractWebPagination.TOTAL_COUNT_ATTRIBUTE, Integer.valueOf(0)), HttpRequestUtils.getIntegerParameter(request, AbstractWebPagination.START_NAME, Integer.valueOf(0)), Integer.valueOf(size)});
        String sortName = request.getParameter("sortName");
        boolean sortDesc = "true".equalsIgnoreCase(request.getParameter("sortDesc"));
        if(!StringUtils.isEmpty(sortName)) {
            awp.addSorters(new Sorter[]{new Sorter(sortName, sortDesc)});
        }

        return awp;
    }

    protected void bindOrderablePagination(HttpServletRequest request, String pageAttrName, OrderablePagination op) {
        request.setAttribute(pageAttrName, op);
    }

    protected abstract class SerializablePaginationQueryCallback implements PaginationableController.PaginationQueryCallback {
        protected SerializablePaginationQueryCallback() {
        }

        public abstract PaginationResultDTO<?> query(OrderablePaginationDTO var1);

        public List<?> query(OrderablePagination op) throws Exception {
            OrderablePaginationDTO paginationDTO = this.assemble(op);
            PaginationResultDTO result = this.query(paginationDTO);
            if(result != null) {
                op.setTotalCount(result.getTotalCount());
                return result.getResult();
            } else {
                op.setTotalCount(0);
                return Collections.emptyList();
            }
        }

        private OrderablePaginationDTO assemble(OrderablePagination source) {
            OrderablePaginationDTO result = new OrderablePaginationDTO();
            if(source == null) {
                result.setSize(20);
                result.setPage(1);
            } else {
                result.setSize(source.getPageSize());
                result.setPage(source.getCurrentPage() <= 0?1:source.getCurrentPage());
                if(source.getSorters() != null && source.getSorters().length > 0) {
                    ArrayList orders = new ArrayList();
                    result.setOrders(orders);
                    Sorter[] var4 = source.getSorters();
                    int var5 = var4.length;

                    for(int var6 = 0; var6 < var5; ++var6) {
                        Sorter sorter = var4[var6];
                        OrderDTO orderDTO = new OrderDTO();
                        orders.add(orderDTO);
                        orderDTO.setPropertyName(sorter.getPropertyName());
                        orderDTO.setDescending(sorter.isAscending());
                    }
                }
            }

            return result;
        }
    }

    protected interface PaginationQueryCallback {
        List<?> query(OrderablePagination var1) throws Exception;
    }
}
