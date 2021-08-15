package cn.how2j.chain;
 
public class URLFilter implements Filter {
 
    @Override
    public void doFilter(Request request, Response response, FilterChain chain) {
        System.out.println("URLFilter : \t"+request.getRemoteIp() +  "\t" + request.getUrl());
        chain.doFilter(request,response);
    }
 
}