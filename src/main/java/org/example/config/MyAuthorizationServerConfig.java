package org.example.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.TokenStore;

/**
 * 授权服务器
 *
 * @author Mr.Lan
 * @create: 2022-01-19 16:48
 */
@Configuration
@EnableAuthorizationServer
public class MyAuthorizationServerConfig extends AuthorizationServerConfigurerAdapter {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    @Qualifier("redisTokenStore")
    private TokenStore tokenStore;

    //配置客户端信息
    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        //这里暂时使用内存存储方式，创建一个客户端信息，一般会通过建表进行存储
        clients.inMemory()
                //客户端ID
                .withClient("client")
                //客户端密钥,通过加密明文，使得在获取token时传输更加安全
                .secret(passwordEncoder.encode("client123"))
                //授权范围
                .scopes("all")
                //重定向地址
                .redirectUris("http://localhost:8081/login")
                //授权方式：授权码
                .authorizedGrantTypes("authorization_code","refresh_token")
                //token失效时间
                .accessTokenValiditySeconds(60)
                //用于刷新token的token失效时间
                .refreshTokenValiditySeconds(86400)
                //自动认证
                .autoApprove(true);
    }

    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
        endpoints.tokenStore(tokenStore);
    }

    /**
     * 配置单点登录需要
     */
    @Override
    public void configure(AuthorizationServerSecurityConfigurer security) throws Exception {
        // 开启/oauth/check_token验证端口认证权限访问
        security.checkTokenAccess("isAuthenticated()");
    }

}
