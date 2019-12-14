package sso.server.support.auth.handler;

import java.security.GeneralSecurityException;
import java.util.Collections;

import javax.security.auth.login.FailedLoginException;

import org.apereo.cas.authentication.Credential;
import org.apereo.cas.authentication.HandlerResult;
import org.apereo.cas.authentication.PreventedException;
import org.apereo.cas.authentication.handler.support.AbstractPreAndPostProcessingAuthenticationHandler;
import org.apereo.cas.authentication.principal.PrincipalFactory;
import org.apereo.cas.services.ServicesManager;

import sso.server.support.auth.UsernamePasswordSysCredential;

/**
 * 自定义认证类
 *
 */
public class MyPreAndPostProcessingAuthenticationHandler extends AbstractPreAndPostProcessingAuthenticationHandler {
    public MyPreAndPostProcessingAuthenticationHandler(String name, ServicesManager servicesManager,
            PrincipalFactory principalFactory, Integer order) {
        super(name, servicesManager, principalFactory, order);
    }


    @Override
    protected HandlerResult doAuthentication(Credential credential) throws GeneralSecurityException, PreventedException {
        // system为sso即允许通过
        UsernamePasswordSysCredential sysCredential = (UsernamePasswordSysCredential) credential;
        if (!"sso".equals(sysCredential.getSystem())) {
            throw new FailedLoginException("只能登陆SSO系统");
        }
        String username = sysCredential.getUsername();
        String password = sysCredential.getPassword();
        if (!"admin".equals(username) || !"123".equals(password)) {
            throw new FailedLoginException("用户名或密码错误");
        }
        // 这里可以自定义属性数据
        return createHandlerResult(credential, this.principalFactory.createPrincipal(
                ((UsernamePasswordSysCredential) credential).getUsername(), Collections.emptyMap()), null);
    }


    @Override
    public boolean supports(Credential credential) {
        return credential instanceof UsernamePasswordSysCredential;
    }
}
