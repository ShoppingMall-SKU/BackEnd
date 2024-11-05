package com.mealKit.backend.interceptor;


import com.mealKit.backend.oauth2.CustomOAuth2User;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.jetbrains.annotations.NotNull;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.servlet.HandlerInterceptor;


public class UserPidResolveInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(@NotNull HttpServletRequest request, @NotNull HttpServletResponse response, @NotNull Object handler) throws Exception {
        final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication != null && authentication.isAuthenticated()) {
            Object principal = authentication.getPrincipal();
            //System.out.println("******************" + principal);

            if (principal instanceof CustomOAuth2User) {
                //System.out.println("********************" + pid);
                request.setAttribute("PID", ((CustomOAuth2User) principal).getPid());
            } else if (principal instanceof String) {
                //System.out.println("********************" + principal);
                request.setAttribute("PID", principal);
            }
        }
        return HandlerInterceptor.super.preHandle(request, response, handler);
    }

}
