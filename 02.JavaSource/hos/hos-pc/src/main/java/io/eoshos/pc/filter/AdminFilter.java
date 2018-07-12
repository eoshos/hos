package io.eoshos.pc.filter;

import java.io.IOException;
import java.util.Properties;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;


/**
 * 
 * @description
 * @version 1.0
 * @author hhj@chuangke18.com
 * @date 2016年10月9日 上午10:35:51
 * 修改记录：
 * 		时间：
 *      描述：
 *		修改人：
 */
public class AdminFilter implements Filter {

	@SuppressWarnings("unused")
	private static final Logger log = Logger.getLogger(AdminFilter.class);

	@Override
	public void init(FilterConfig arg0) throws ServletException {
		System.out.println("init done");
	}

	@Override
	public void destroy() {
	}

	@SuppressWarnings("unused")
	@Override
	public void doFilter (ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		request.setCharacterEncoding("utf-8");
		HttpServletRequest httpServletRequest = (HttpServletRequest) request;
		HttpServletResponse httpServletResponse = (HttpServletResponse) response;
		HttpSession session = httpServletRequest.getSession();
		String servletPath = httpServletRequest.getRequestURI();
		Properties prop = new Properties();
//		String charsetName = CpdetectorUtil.getFileEncode(getClass().getClassLoader().getResource("config.properties").getPath());
//		prop.load(new InputStreamReader(getClass().getClassLoader().getResourceAsStream("config.properties"), charsetName));
		
		//String adminUrl = prop.getProperty("admin_url");
		String adminUrl = "/login/Bw8gJkU6YXj.html";
		
		String url = httpServletRequest.getRequestURI();
		if (url != null && url.indexOf("data9") > 0 ) {
			/*
			String imgName = httpServletRequest.getParameter("filename");
			File f = new File(CommonProperty.getValueByKey("dataPath") + File.separator + imgName);
			if (!f.exists()) {
				///OSSUtil oss = new OSSUtil("data" + imgName.substring(0, imgName.lastIndexOf("/") + 1));
				OSSUtil oss = new OSSUtil(ConstantAdmin.FILE_PATH.OSS_DATA_PATH);
				int i = oss.downloadFile(f.getPath());
				if (i < 0){
					System.out.println("oss download failed:" + imgName);
				}
			}*/
			chain.doFilter(request, response);
		} else{
			chain.doFilter(request, response);
		}
		/*else {
			Object objUser = session.getAttribute(Constant.AUTH_USER);
			if (!servletPath.endsWith(".html")) {
				chain.doFilter(request, response);
			}
			String username = StringUtil.isNull(httpServletRequest.getParameter("username"));
			SystemUser user = (SystemUser) objUser;
			if (user == null) {
				if (!StringUtil.isBlank(adminUrl) && servletPath.startsWith(adminUrl)) {
					if (objUser == null && !servletPath.equals("/admin/user/login.html")) {
						request.setAttribute("source", "fliter");
						httpServletRequest.getRequestDispatcher("/system/indexInfo").forward(request, response);
					} else {
						chain.doFilter(request, response);
					}
				} else if (servletPath.startsWith("/admin/user/Dumfmi3hYhV.html")) { // 登录超时
					Map<String, String> result = new HashMap<String, String>();
					result.put("errorCode", "0000");
					httpServletResponse.getWriter().print(JSON.toJSON(result));
				} else {
					if (("/admin/user/login.html".equals(servletPath)) && !StringUtil.isBlank(username)
							|| "/admin/validimg.html".equals(servletPath)) {
						chain.doFilter(request, response);
					} else {
						httpServletResponse.setCharacterEncoding("UTF-8");
						if ("ajax".equals(httpServletRequest.getParameter("ckRequestType"))) {
							Map<String, String> result = new HashMap<String, String>();
							result.put("errorCode", "7777");
							result.put("errorMsg", "登录超时");
							httpServletResponse.getWriter().print(JSON.toJSONString(result));
						} else {
							String referer = httpServletRequest.getHeader("Referer");
							if (!StringUtil.isBlank(referer) && referer.endsWith("/admin/adminIndex.html")) {
								httpServletResponse.setHeader("content-type", "text/html;charset=UTF-8");
								httpServletResponse.getWriter().print(
										"<script>alert('长时间没有操作，为保证安全，请重新登录');parent.parent.window.location='/admin/jZh5zYR8.html'</script>");
							} else {
								httpServletResponse
										.sendRedirect(httpServletRequest.getContextPath() + "/notfound.html");
							}
						}
					}
				}
			} else {
				chain.doFilter(request, response);
			}
			
		}*/

	}
}