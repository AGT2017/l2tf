package com.ficbic.dpeq;

public class MainApp {

    public static void main(String[] args) {
        try {
            SpringApplication.run(MainApp.class, args);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
    }

    @ExceptionHandler(AuthorizationException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public String handleException(AuthorizationException e, Model model) {
        log.debug("AuthorizationException was thrown", e);

        Map<String, Object> map = new HashMap<String, Object>();
        map.put("status", HttpStatus.FORBIDDEN.value());
        map.put("message", "No message available");
        model.addAttribute("errors", map);

        return "error";
    }


    @Bean
    protected NoSaltAuth jdbcRealm(DataSource dataSource) {
        return new NoSaltAuth();
    }

    @Bean
    public ShiroFilterChainDefinition shiroFilterChainDefinition() {
        DefaultShiroFilterChainDefinition chainDefinition = new DefaultShiroFilterChainDefinition();
        chainDefinition.addPathDefinition("/", "anon");
        chainDefinition.addPathDefinition("/login", "anon");
        chainDefinition.addPathDefinition("/register", "anon");
        chainDefinition.addPathDefinition("/logout", "anon");
        chainDefinition.addPathDefinition("/admin/**", "authc, roles[admin]");
        chainDefinition.addPathDefinition("/edit/**", "authc, perms[edit]");
        return chainDefinition;
    }

    @ModelAttribute(name = "subject")
    public Subject subject() {
        return SecurityUtils.getSubject();
    }
}
