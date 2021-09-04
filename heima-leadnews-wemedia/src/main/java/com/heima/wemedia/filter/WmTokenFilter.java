package com.heima.wemedia.filter;

import cn.hutool.core.convert.Convert;
import cn.hutool.core.util.StrUtil;
import com.heima.model.wemedia.pojos.WmUser;
import com.heima.utils.threadlocal.WmThreadLocalUtils;
import org.apache.commons.lang3.StringUtils;
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
@WebFilter(urlPatterns = "/*",filterName = "globalFilter")
public class WmTokenFilter extends GenericFilterBean {
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) servletRequest;
        HttpServletResponse resp = (HttpServletResponse) servletResponse;
        if(StringUtils.contains(req.getRequestURI(), "/login/in")){
            filterChain.doFilter(req,resp);
            return;
        }
        String token = req.getHeader("userId");
        if(StrUtil.isEmpty(token)){
            //未获取到userId 则不放行
            resp.setStatus(401);
            return;
        }
        //将登陆的用户信息存放到当前线程中
        WmUser wmUser = new WmUser();
        wmUser.setId(Convert.toInt(token));
        WmThreadLocalUtils.set(wmUser);
        filterChain.doFilter(req,resp);
    }
}
