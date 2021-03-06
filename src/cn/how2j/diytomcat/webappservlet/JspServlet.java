package cn.how2j.diytomcat.webappservlet;
 
import cn.how2j.diytomcat.http.Request;
import cn.how2j.diytomcat.http.Response;
import cn.how2j.diytomcat.util.Constant;
import cn.how2j.diytomcat.util.WebXMLUtil;
import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.StrUtil;
 
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
 
public class JspServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private static JspServlet instance = new JspServlet();
 
    public static synchronized JspServlet getInstance() {
        return instance;
    }
 
    private JspServlet() {
 
    }
 
    public void service(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse)
            throws IOException, ServletException {
        try {
            Request request = (Request) httpServletRequest;
            Response response = (Response) httpServletResponse;
 
            String uri = request.getRequestURI();
 
            if ("/".equals(uri))
                uri = WebXMLUtil.getWelcomeFile(request.getContext());
 
            String fileName = StrUtil.removePrefix(uri, "/");
            File file = FileUtil.file(request.getRealPath(fileName));
 
            if (file.exists()) {
                String extName = FileUtil.extName(file);
                String mimeType = WebXMLUtil.getMimeType(extName);
                response.setContentType(mimeType);
 
                byte body[] = FileUtil.readBytes(file);
                response.setBody(body);
 
                response.setStatus(Constant.CODE_200);
            } else {
                response.setStatus(Constant.CODE_404);
            }
 
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}