package cn.how2j.chain;
 
import java.util.List;
 
public class FilterChain {
 
    private Filter[] filters;
    private Servlet servlet;
 
    int pos;
    public FilterChain(List<Filter> filters, Servlet servlet) {
        this.servlet = servlet;
        this.filters = filters.toArray(new Filter[] {});
    }
     
    public void doFilter(Request request, Response response) {
        if(pos<filters.length) {
            Filter filter = filters[pos++];
            filter.doFilter(request, response, this);
        }
        else {
            servlet.service(request, response);
        }
    }
 
}