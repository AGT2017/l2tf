package com.ficbic.dpeq.resource.anon;

import com.alibaba.druid.support.json.JSONUtils;
import com.ficbic.dpeq.pojo.User;
import com.ficbic.dpeq.pojo.UserInfo;
import com.ficbic.dpeq.resource.Const;
import com.ficbic.dpeq.service.DefaultUserService;
import com.ficbic.dpeq.utils.LoginValidator;
import com.ficbic.dpeq.utils.RegisterValidator;
import com.ficbic.dpeq.utils.command.LoginCommand;
import com.ficbic.dpeq.utils.command.RegisterCommand;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.catalina.connector.Response;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.crypto.hash.Sha256Hash;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.Optional;

@Slf4j
@Controller
public class AuthControl {
    @Autowired
    private DefaultUserService userService;

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String getLogin() {
        return "login";
    }

    @RequestMapping(value = "/register", method = RequestMethod.GET)
    public String getRegister() {
        return "register";
    }

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public String login(Model model, @ModelAttribute LoginCommand loginCommand, BindingResult errors) {
        new LoginValidator().validate(loginCommand, errors);
        if (errors.hasErrors()) {
            return "login";
        }
        String userName = loginCommand.getUsername();
        String password = new Sha256Hash(loginCommand.getPassword()).toHex();
        Subject currentUser = SecurityUtils.getSubject();
        Optional<PrincipalCollection> principalCollectionOptional = Optional.ofNullable(currentUser.getPrincipals());
        try {
            UsernamePasswordToken token = new UsernamePasswordToken(userName, password);
            token.setRememberMe(loginCommand.isRememberMe());
            currentUser.login(token);

            User queriedUser = userService.getUser(userName);
            model.addAttribute(Const.USERNAME, queriedUser.getUsername());
            model.addAttribute(Const.EMAIL, queriedUser.getEmail());

            if (currentUser.hasRole( "admin" ) ) {
                log.info("May the Schwartz be with you!" );
                return "account-info";
            }

            log.info( "User [" + currentUser.getPrincipal() + "] login success." );
            return "hello";
        } catch (UnknownAccountException uae ) {
            log.error("User [" + currentUser.getPrincipal() + "] login failed for UnknownAccountException", uae);
            return "register";
        } catch (IncorrectCredentialsException ice) {
            log.error("User [" + currentUser.getPrincipal() + "] login failed for IncorrectCredentialsException", ice);
        } catch (LockedAccountException lae) {
            log.error("User [" + currentUser.getPrincipal() + "] login failed for LockedAccountException", lae);
        } catch (AuthenticationException ae) {
            log.error("User [" + currentUser.getPrincipal() + "] login failed for AuthenticationException", ae);
        } catch (Exception e) {
            log.error("User [" + currentUser.getPrincipal() + "] login failed for unknown exception", e);
        }

        return "login";
    }

    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public String register(Model model, @ModelAttribute RegisterCommand registerCommand, BindingResult errors) {
        RegisterValidator registerValidator = new RegisterValidator();
        registerValidator.validate(registerCommand, errors);
        if (errors.hasErrors()) {
            return "register";
        }

        userService.createUser(registerCommand.getUsername(), registerCommand.getEmail(), new Sha256Hash(registerCommand.getPassword()).toHex());
        log.info( "User registered successfully." );

        SecurityUtils.getSubject().login(new UsernamePasswordToken(registerCommand.getUsername(), registerCommand.getPassword()));

        return "redirect:/";
    }

    @RequestMapping("/logout")
    public String logout(HttpServletRequest request) {
        Subject currentUser = SecurityUtils.getSubject();
        String userName = request.getParameter(Const.USERNAME);
        String password = request.getParameter(Const.PASSWORD);
        UsernamePasswordToken token = new UsernamePasswordToken(userName, password);
        currentUser.logout();
        return "redirect:/";
    }
}

@Data
@NoArgsConstructor
class AuthResult {
    private UserInfo userInfo;
    private String url;

    public AuthResult(User user, String url) {
        if (user != null) {
            this.userInfo = new UserInfo(user);
        }
        this.url = url;
    }
}
