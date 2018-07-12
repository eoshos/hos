package io.eoshos.pc.interceptor;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.core.NamedThreadLocal;
import org.springframework.util.StopWatch;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import io.eoshos.pc.util.JSONUtil;


public class LoginInterceptor implements HandlerInterceptor {
	
	private static String AUTH_USER = "auth_user";

	// 日志
	protected static Logger log = LogManager.getLogger(LoginInterceptor.class);
	private static final ThreadLocal<StopWatch> stopWatchLocal = new NamedThreadLocal<StopWatch>(
			"ThreadLocal StopWatch");
	
	List<String> allowUri = new ArrayList<String>();// 不需要拦截的请求

	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object arg2, Exception arg3)
			throws Exception {
		String uri = request.getRequestURI();
		if (uri.indexOf("admin/public/api") == -1 ) {
			HttpSession session = request.getSession();
			if (session != null) {
				Object sessionObj = request.getSession().getAttribute(AUTH_USER);
				if (sessionObj != null) {
					/*
					 * if(startTimeThreadLocal!=null){ long beginTime =
					 * startTimeThreadLocal.get();//得到线程绑定的局部变量（开始时间） long
					 * endTime = System.currentTimeMillis(); //2、结束时间 log.
					 * error("计时结束：{}  耗时：{}  URI: {}  最大内存: {}m  已分配内存: {}m  已分配内存中的剩余空间: {}m  最大可用内存: {}m"
					 * , new SimpleDateFormat("HH:mm:ss.SSS").format(endTime),
					 * DateUtil.formatDateTime(endTime - beginTime),
					 * request.getRequestURI(),
					 * Runtime.getRuntime().maxMemory()/1024/1024,
					 * Runtime.getRuntime().totalMemory()/1024/1024,
					 * Runtime.getRuntime().freeMemory()/1024/1024,
					 * (Runtime.getRuntime().maxMemory()-Runtime.getRuntime().
					 * totalMemory()+Runtime.getRuntime().freeMemory())/1024/
					 * 1024); startTimeThreadLocal.set(null); }
					 */
					StopWatch stopWatch = stopWatchLocal.get();
					if (stopWatch != null) {
						stopWatch.stop();
						String currentPath = request.getRequestURI();
						String queryString = request.getQueryString();
						queryString = queryString == null ? "" : "?" + queryString;
						log.info("access url path:" + currentPath + queryString + " |time:"
								+ stopWatch.getTotalTimeMillis() + "ms");
						stopWatchLocal.set(null);
					}
				}
			}
		}
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object arg2,
			ModelAndView modelAndView) throws Exception {
		String uri = request.getRequestURI();
		String devtype = request.getParameter("devtype");
		// 拦截json的数据
		if (request.getHeader("x-requested-with") != null
				&& request.getHeader("x-requested-with").equalsIgnoreCase("XMLHttpRequest")) { // 如果是ajax请求响应头会有x-requested-with
			if (modelAndView != null) {
				String json = JSONUtil.objectToJson(modelAndView.getModel().get("result"));
				PrintWriter out = response.getWriter();
				out.print(json);
				out.flush();
				out.close();
			} else {
				Object sessionObj = request.getSession().getAttribute(AUTH_USER);
				String local = StringUtils.isBlank(request.getParameter("local")) ||
						StringUtils.equals(request.getParameter("local"), "zh") ? "en" : "zh";
				if (sessionObj == null 
						&& uri.indexOf("enroll/validationcode") == -1 
						&& uri.indexOf("enroll/index") == -1
						&& uri.indexOf("enroll/enroll") == -1 
						&& uri.indexOf("login/validate") == -1
						&& uri.indexOf("login/repassword") == -1
						&& uri.indexOf("login/password") == -1) {
					//response.sendRedirect(request.getContextPath()+"/login/Bw8gJkU6YXj.html?local=" + local + "&devtype=" + devtype);
					response.sendRedirect("https://eoshos.io/login/Bw8gJkU6YXj.html?local=" + local + "&devtype=" + devtype);
				}
			}
		}
		// log.info("post");
	}

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		// log.info("开始拦截");
		// String path = request.getContextPath();
		String uri = request.getRequestURI();
		String devtype = request.getParameter("devtype");
		if (uri.indexOf("signIndex") == -1 && uri.indexOf("instalmentsign") == -1 && uri.indexOf("agreement") == -1
				&& uri.indexOf("Bw8gJkU6YXj.html") == -1 && uri.indexOf("validimg.html") == -1
				&& uri.indexOf("login.html") == -1 && uri.indexOf("logOut.html") == -1 && uri.indexOf(".gif") == -1
				&& uri.indexOf(".png") == -1 && uri.indexOf(".css") == -1 && uri.indexOf(".woff") == -1
				&& uri.indexOf(".js") == -1 && uri.indexOf(".eot") == -1 && uri.indexOf(".svg") == -1
				&& uri.indexOf(".ttf") == -1 && uri.indexOf(".woff2") == -1 && uri.indexOf(".jpg") == -1
				&& uri.indexOf("admin/public/api") == -1 
				&& uri.indexOf("enroll/index") == -1
				&& uri.indexOf("enroll/enroll") == -1
				&& uri.indexOf("enroll/validationcode") == -1
				&& uri.indexOf("login/repassword") == -1
				&& uri.indexOf("login/password") == -1
				&& uri.indexOf("login/downloadwhitepaper") == -1
				&& uri.indexOf("login/validate") == -1
				&& uri.indexOf("login/validGraphicCode") == -1
				&& uri.indexOf("login/index") == -1
				&& uri.toLowerCase().indexOf("getimage") == -1
				&& uri.indexOf("admin/api") == -1) {
			Object sessionObj = request.getSession().getAttribute(AUTH_USER);
			if (sessionObj != null) {
				StopWatch stopWatch = new StopWatch(handler.toString());
				stopWatchLocal.set(stopWatch);
				stopWatch.start(handler.toString());
				log.info("stopWatch  start.......");
				return true;
			} else {
				log.info("session失效或者没有登录"); 
				String local = StringUtils.isBlank(request.getParameter("local")) ||
						StringUtils.equals(request.getParameter("local"), "zh") ? "en" : "zh";
				//response.sendRedirect(request.getContextPath()+"/login/Bw8gJkU6YXj.html?local=" + local + "&devtype=" + devtype);
				response.sendRedirect("https://eoshos.io/login/Bw8gJkU6YXj.html?local=" + local + "&devtype=" + devtype);
				return false;
			}
		}

		return true;
	}
	
	/*@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
		String uri = request.getRequestURI();
		try {
			if ("/".equals(uri)) {
				response.sendRedirect("/admin/Bw8gJkU6YXj.html");
				return false;
			}
			boolean intercept = true;
			for (String url : allowUri) {
				if (uri.indexOf(url) != -1) {
					intercept = false;
					break;
				}
			}
			if (intercept) {
				Object sessionObj = request.getSession().getAttribute(ConstantAdmin.SESSION.AUTH_USER);
				if (sessionObj != null) {
					StopWatch stopWatch = new StopWatch(handler.toString());
					stopWatchLocal.set(stopWatch);
					stopWatch.start(handler.toString());
					log.info("stopWatch  start.......");
					return true;
				} else {
					log.info("请求路径1:" + uri);
					log.info("getContextPath():" + request.getContextPath());
					log.info("getLocalPort():" + request.getLocalPort());
					log.info("getUserPrincipal():" + request.getUserPrincipal());
					log.info("getLocalName():" + request.getLocalName());
					log.info("getLocalAddr():" + request.getLocalAddr());
					log.info("getPathInfo():" + request.getPathInfo());
					log.info("getRemoteAddr():" + request.getRemoteAddr());
					log.info("getRemoteHost():" + request.getRemoteHost());
					log.info("getRemotePort():" + request.getRemotePort());
					log.info("getRemoteUser():" + request.getRemoteUser());
					log.info("session失效或者没有登录");
					response.sendRedirect("/login/Bw8gJkU6YXj.html");
					return false;
				}
			}
			if (!(uri.indexOf("/data/contract") == -1)) {
				Object session = null;
				System.out.println(uri);
				String type = request.getParameter("type");
				if (type != null) {
					session = request.getSession().getAttribute(ConstantAdmin.SESSION.AUTH_USER);
					if (session == null) {
						log.info("请求路径2:" + uri);
						log.info("session失效或者没有登录");
						response.sendRedirect("/login/Bw8gJkU6YXj.html");
						return false;
					}
				} else {
					session = request.getSession().getAttribute("customerSign");
					if (session == null) {
						log.info("请求路径3:" + uri);
						log.info("session失效或者没有登录");
						response.sendRedirect("/pdf-viewer/examples/mobile-viewer/404.html");
						return false;
					}
				}
			}
			return true;
		} catch (IOException e) {
			return false;
		}
	}	*/
	
	public List<String> getAllowUri() {
		return allowUri;
	}

	public void setAllowUri(List<String> allowUri) {
		this.allowUri = allowUri;
	}	

}