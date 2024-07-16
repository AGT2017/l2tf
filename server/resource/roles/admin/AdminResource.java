package com.ficbic.dpeq.resource.roles.admin;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
public class AdminResource {

    @RequiresRoles("admin")
    @RequestMapping("/admin/account-info")
    public String getAccountInfo(Model model) {

        String name = "World";

        Subject subject = SecurityUtils.getSubject();
        if (!subject.isAuthenticated()) {
            return "redirect:/";
        }

        PrincipalCollection principalCollection = subject.getPrincipals();

        if (principalCollection != null && !principalCollection.isEmpty()) {
            name = principalCollection.getPrimaryPrincipal().toString();
        }

        model.addAttribute("name", name);

        return "account-info";
    }
}
