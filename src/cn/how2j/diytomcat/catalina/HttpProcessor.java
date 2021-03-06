package cn.how2j.diytomcat.catalina;
 
import cn.how2j.diytomcat.http.Request;
import cn.how2j.diytomcat.http.Response;
import cn.how2j.diytomcat.webappservlet.DefaultServlet;
import cn.how2j.diytomcat.webappservlet.InvokerServlet;
import cn.how2j.diytomcat.webappservlet.JspServlet;
import cn.how2j.diytomcat.util.Constant;
import cn.how2j.diytomcat.util.SessionManager;
import cn.how2j.diytomcat.util.WebXMLUtil;
import cn.how2j.diytomcat.webappservlet.HelloServlet;
import cn.hutool.core.io.FileUtil;
import cn.hutool.core.thread.ThreadUtil;
import cn.hutool.core.util.ArrayUtil;
import cn.hutool.core.util.ReUtil;
import cn.hutool.core.util.ReflectUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.log.LogFactory;
import org.apache.el.util.ReflectionUtil;

import javax.servlet.Filter;
import javax.servlet.Servlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;
import java.util.List;
 
public class HttpProcessor {
    public void execute(Socket s, Request request, Response response){
        try {
            String uri = request.getUri();
            if(null==uri)
                return;
 
            prepareSession(request, response);
 
            Context context = request.getContext();
            String servletClassName = context.getServletClassName(uri);
 
            HttpServlet workingServlet;
            if(null!=servletClassName)
                workingServlet = InvokerServlet.getInstance();
            else if(uri.endsWith(".jsp"))
            	workingServlet = JspServlet.getInstance();
            else
            	workingServlet = DefaultServlet.getInstance();
            List<Filter> filters = request.getContext().getMatchedFilters(request.getRequestURI());
            ApplicationFilterChain filterChain = new ApplicationFilterChain(filters, workingServlet);
            filterChain.doFilter(request, response);
            
            if(Constant.CODE_200 == response.getStatus()){
                handle200(s, request, response);
                return;
            }
            if(Constant.CODE_404 == response.getStatus()){
                handle404(s, uri);
                return;
            }
 
        } catch (Exception e) {
            LogFactory.get().error(e);
            handle500(s,e);
        }
        finally{
            try {
                if(!s.isClosed())
                    s.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    
	private boolean isGzip(Request request, byte[] body, String mimeType) {
		String acceptEncodings=  request.getHeader("Accept-Encoding");
		if(!StrUtil.containsAny(acceptEncodings, "gzip")) 
			return false;
		
		
		Connector connector = request.getConnector();
		if (mimeType.contains(";"))
			mimeType = StrUtil.subBefore(mimeType, ";", false);
		if (!"on".equals(connector.getCompression()))
			return false;
		if (body.length < connector.getCompressionMinSize())
			return false;
		String userAgents = connector.getNoCompressionUserAgents();
		String[] eachUserAgents = userAgents.split(",");
		for (String eachUserAgent : eachUserAgents) {
			eachUserAgent = eachUserAgent.trim();
			String userAgent = request.getHeader("User-Agent");
			if (StrUtil.containsAny(userAgent, eachUserAgent))
				return false;
		}
		String mimeTypes = connector.getCompressableMimeType();
		String[] eachMimeTypes = mimeTypes.split(",");
		for (String eachMimeType : eachMimeTypes) {
			if (mimeType.equals(eachMimeType))
				return true;
		}
		return false;
	}
 
    private static void handle200(Socket s, Request request, Response response) throws IOException {
        String contentType = response.getContentType();
        String headText = Constant.response_head_200;
        String cookiesHeader = response.getCookiesHeader();
        headText = StrUtil.format(headText, contentType, cookiesHeader);
        byte[] head = headText.getBytes();
 
        byte[] body = response.getBody();
        byte[] responseBytes = new byte[head.length + body.length];
        ArrayUtil.copy(head, 0, responseBytes, 0, head.length);
        ArrayUtil.copy(body, 0, responseBytes, head.length, body.length);
 
        OutputStream os = s.getOutputStream();
        os.write(responseBytes);
    }
 
    private void handle404(Socket s, String uri) throws IOException {
        OutputStream os = s.getOutputStream();
        String responseText = StrUtil.format(Constant.textFormat_404, uri, uri);
        responseText = Constant.response_head_404 + responseText;
        byte[] responseByte = responseText.getBytes("utf-8");
        os.write(responseByte);
    }
 
    private void handle500(Socket s, Exception e) {
        try {
            OutputStream os = s.getOutputStream();
            StackTraceElement stes[] = e.getStackTrace();
            StringBuffer sb = new StringBuffer();
            sb.append(e.toString());
            sb.append("\r\n");
            for (StackTraceElement ste : stes) {
                sb.append("\t");
                sb.append(ste.toString());
                sb.append("\r\n");
            }
 
            String msg = e.getMessage();
 
            if (null != msg && msg.length() > 20)
                msg = msg.substring(0, 19);
 
            String text = StrUtil.format(Constant.textFormat_500, msg, e.toString(), sb.toString());
            text = Constant.response_head_500 + text;
            byte[] responseBytes = text.getBytes("utf-8");
            os.write(responseBytes);
        } catch (IOException e1) {
            e1.printStackTrace();
        }
    }
    public void prepareSession(Request request, Response response) {
        String jsessionid = request.getJSessionIdFromCookie();
        HttpSession session = SessionManager.getSession(jsessionid, request, response);
        request.setSession(session);
    }
}