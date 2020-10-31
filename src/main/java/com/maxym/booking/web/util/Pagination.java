package com.maxym.booking.web.util;

import javax.servlet.http.HttpServletRequest;

public final class Pagination {
    public static int[] paginateRequest(HttpServletRequest request, int numberOfRows, int recordsPerPage) {
        int noOfPages = (int) (Math.ceil(1. * numberOfRows / recordsPerPage));
        String tmp = request.getParameter("page");
        int currentPage;
        if (tmp != null && tmp.matches("^[0-9]*$")) {
            currentPage = Integer.parseInt(tmp);
        } else {
            currentPage = 1;
        }
        request.setAttribute("recordsPerPage", recordsPerPage);
        request.setAttribute("noOfPages", noOfPages);
        request.setAttribute("currentPage", currentPage);

        return new int[] {currentPage, recordsPerPage};
    }

    public static int[] getScope(int currentPage, int recordsPerPage) {
        int from = (currentPage - 1) * recordsPerPage;
        int to = from + recordsPerPage;

        return new int[] {from, to};
    }
}
