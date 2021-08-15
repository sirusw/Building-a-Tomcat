package cn.how2j.chain;
 
public interface Filter {
    void doFilter(Request request, Response response, FilterChain chain);
}