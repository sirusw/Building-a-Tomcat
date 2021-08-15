package cn.how2j.chain;
 
import java.util.ArrayList;
import java.util.List;
 
public class ChainDemo {
 
    public static void main(String[] args) {
        List<Filter> filters = new ArrayList<>();
         
        Servlet servlet = new Servlet();
         
        URLFilter urlFilter = new URLFilter();
        PfmFilter pfmFilter = new PfmFilter();
         
        filters.add(urlFilter);
        filters.add(pfmFilter);
         
        FilterChain chain = new FilterChain(filters, servlet);
         
        Request request = new Request();
        request.setRemoteIp("28.21.194.201");
        request.setUrl("/a.jsp");
        Response response = new Response();
        chain.doFilter(request, response);
    }
}