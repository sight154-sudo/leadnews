package com.heima.behavior.filter;

import cn.hutool.core.util.StrUtil;
import com.heima.model.wemedia.pojos.WmUser;
import com.heima.utils.threadlocal.WmThreadLocalUtils;
import org.springframework.core.annotation.Order;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author: tang
 * @date: Create in 18:54 2021/9/3
 * @description:
 */
@Order(4)
@WebFilter(urlPatterns = "/*",filterName = "appTokenFilter")
public class AppTokenFilter extends GenericFilterBean {
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) servletRequest;
        HttpServletResponse resp = (HttpServletResponse) servletResponse;
        String token = req.getHeader("userId");
        //将登陆的用户信息存放到当前线程中
        if(StrUtil.isNotBlank(token)){
            WmThreadLocalUtils.set(WmUser.builder().id(Integer.valueOf(token)).build());
        }
        filterChain.doFilter(req,resp);
    }
}
