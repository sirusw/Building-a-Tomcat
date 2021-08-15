package cn.how2j.chain;
 
public class PfmFilter implements Filter {
 
    @Override
    public void doFilter(Request request, Response response, FilterChain chain) {
        long start=  System.currentTimeMillis();
        chain.doFilter(request,response);
        long end=  System.currentTimeMillis();
        long duration = end -start;
        System.out.println("PfmFilter : \t"  + request.getUrl()  + " takes " + duration + " ms");
         
    }
 
}