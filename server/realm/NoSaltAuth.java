package com.ficbic.dpeq.realm;

import com.ficbic.dpeq.pojo.User;
import com.ficbic.dpeq.service.DefaultUserService;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authc.*;
import org.apache.shiro.authc.credential.Sha256CredentialsMatcher;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import com.ficbic.dpeq.pojo.Role;


@Slf4j
public class NoSaltAuth extends AuthorizingRealm {

    private static final Logger LOGGER = LoggerFactory.getLogger(NoSaltAuth.class);

    @Autowired
    private DefaultUserService userService;

    public NoSaltAuth() {
        setName("SampleRealm");
        setCredentialsMatcher(new Sha256CredentialsMatcher());
    }

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        Long userId = (Long) principals.fromRealm(getName()).iterator().next();
        User user = userService.getUser(userId);
        if (user != null) {
            SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
            for (Role role : user.getRoles()) {
                info.addRole(role.getName());
                info.addStringPermissions(role.getPermissionSet());
            }
            return info;
        } else {
            return null;
        }
    }

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        UsernamePasswordToken upToken = (UsernamePasswordToken) token;
        String username = upToken.getUsername();
        if (username == null) {
            throw new AccountException("Null usernames are not allowed by this realm.");
        }
        try {
            User user = userService.getUser(username);
            if (user != null) {
                return new SimpleAuthenticationInfo(user.getId(), user.getPassword(), getName());
            } else {
                return null;
            }
        } catch (UnknownAccountException e) {
            log.error(e.getMessage(), e);
            return null;
        }
    }
}
