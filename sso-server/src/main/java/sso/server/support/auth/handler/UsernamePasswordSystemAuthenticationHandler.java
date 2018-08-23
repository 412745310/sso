package sso.server.support.auth.handler;

import java.security.GeneralSecurityException;
import java.util.Collections;

import javax.security.auth.login.AccountNotFoundException;

import org.apereo.cas.authentication.Credential;
import org.apereo.cas.authentication.HandlerResult;
import org.apereo.cas.authentication.PreventedException;
import org.apereo.cas.authentication.handler.support.AbstractPreAndPostProcessingAuthenticationHandler;
import org.apereo.cas.authentication.principal.PrincipalFactory;
import org.apereo.cas.services.ServicesManager;

import sso.server.support.auth.UsernamePasswordSysCredential;

/**
 * 用户名系统认证，只要是admin用户加上sso系统就允许通过
 *
 */
public class UsernamePasswordSystemAuthenticationHandler extends AbstractPreAndPostProcessingAuthenticationHandler {
    public UsernamePasswordSystemAuthenticationHandler(String name, ServicesManager servicesManager, PrincipalFactory principalFactory, Integer order) {
        super(name, servicesManager, principalFactory, order);
    }

    @Override
    protected HandlerResult doAuthentication(Credential credential) throws GeneralSecurityException, PreventedException {
        //当用户名为admin,并且system为sso即允许通过
        UsernamePasswordSysCredential sysCredential = (UsernamePasswordSysCredential) credential;
        if ("admin".equals(sysCredential.getUsername()) && "sso".equals(sysCredential.getSystem())) {
            //这里可以自定义属性数据
            return createHandlerResult(credential, this.principalFactory.createPrincipal(((UsernamePasswordSysCredential) credential).getUsername(), Collections.emptyMap()), null);
        } else {
            throw new AccountNotFoundException("必须是admin用户才允许通过");
        }
    }


    @Override
    public boolean supports(Credential credential) {
        return credential instanceof UsernamePasswordSysCredential;
    }
}