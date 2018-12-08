package com.zkhc.foot.data.config.druid;

import com.alibaba.druid.support.http.StatViewServlet;

import javax.servlet.annotation.WebInitParam;
import javax.servlet.annotation.WebServlet;

/**
 * @author 武海升
 * @date 2018/12/6 13:45
 */
@WebServlet(urlPatterns="/druid/*",
        initParams={
                // IP白名单(没有配置或者为空，则允许所有访问)
                @WebInitParam(name="allow",value="192.168.1.72,127.0.0.1"),
                // IP黑名单 (存在共同时，deny优先于allow)
                @WebInitParam(name="deny",value="192.168.1.73"),
                // 用户名
                @WebInitParam(name="loginUsername",value="admin"),
                // 密码
                @WebInitParam(name="loginPassword",value="123456"),
                @WebInitParam(name="resetEnable",value="true")
        }
)
public class DruidStatViewServlet extends StatViewServlet {
}
