package edu.itechart.contactlist.util.pagination;

import edu.itechart.contactlist.util.Validator;

public class PaginationManager {
    private static final int CONTACTS_PER_PAGE = 10;
    private Pagination pagination;

    public PaginationManager(String page, int itemCount) {
        pagination = new Pagination();
        int pageCount = (itemCount + CONTACTS_PER_PAGE - 1) / CONTACTS_PER_PAGE;
        pagination.setPageCount(pageCount);
        pagination.setActivePage(getActivePage(page));
    }

    public Pagination getPagination() {
        return pagination;
    }

    public int getOffset() {
        return (pagination.getActivePage() - 1) * CONTACTS_PER_PAGE;
    }

    public int getLimit() {
        return CONTACTS_PER_PAGE;
    }

    private int getActivePage(String page) {
        int pageNumber = 1;
        int pageCount = pagination.getPageCount();
        if (Validator.isNumber(page)) {
            pageNumber = Integer.parseInt(page);
        }
        pageNumber = pageNumber >= pageCount ? pageCount : pageNumber;
        pageNumber = pageNumber < 1 ? 1 : pageNumber;
        return pageNumber;
    }
}
