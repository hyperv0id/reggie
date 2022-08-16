package com.example.reggie.filter;

import com.alibaba.fastjson.JSON;
import com.example.reggie.util.R;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.AntPathMatcher;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @program: reggie
 * @description: 登录功能的过滤器
 * @author: 超级虚空
 * @create: 2022-08-16 12:05
 **/
@Slf4j
@WebFilter(filterName = "loginCheckFilter", urlPatterns = "/*")
public class LoginCheckFilter implements Filter {
    // 路径匹配器
    public static final AntPathMatcher PATH_MATCHER = new AntPathMatcher();
    /**
     * 1.获得本次请求URI
     * 2. 判断本次请求是否需要处理
     * 3. 放行部分请求
     * 4. 判断是否已经登陆，如果已经登录，就放行
     * 5. 返回登录页面
     */
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse)servletResponse;
        // 1. 获得本次请求URI
        String uri = request.getRequestURI();

        // 2. 判断本次请求是否需要处理
        String[] urls = new String[]{
                "/employee/login",
                "/employee/logout",
                "/backend/**", // 后端静态资源
                "/front/**", // 前端静态资源
        };
        // 3. 放行部分请求
        if(checkUrl(urls, uri)){
            filterChain.doFilter(request, response);
            return; // 不向后执行
        }
        // 4. 判断是否已经登陆，如果已经登录，就放行
        if(request.getSession().getAttribute("employee")!=null){
            log.info("用户已登录，id为{}", request.getSession().getAttribute("employee"));
            filterChain.doFilter(request,response);
            return;
        }


        // 下面是不满足规则的

        // 5. 返回登录页面
        log.info("用户没有登陆");
        response.getWriter().write(JSON.toJSONString(R.error("NOTLOGIN")));




    }

    /**
     * 循环判断是否满足放行规则
     * @param urls 放行的url们
     * @param requestURI 请求路径
     * @return 是否放行
     */
    boolean checkUrl(String[] urls, String requestURI){
        for(String url:urls){
            if(PATH_MATCHER.match(url, requestURI)){
                return true;
            }
        }
        return false;
    }
}
