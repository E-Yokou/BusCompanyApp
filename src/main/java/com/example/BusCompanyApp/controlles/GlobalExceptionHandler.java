package com.example.BusCompanyApp.controlles;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.boot.web.server.ErrorPage;
import org.springframework.boot.web.server.WebServerFactoryCustomizer;
import org.springframework.boot.web.servlet.server.ConfigurableServletWebServerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;

@Controller
@ControllerAdvice
@RequestMapping("/error")
public class GlobalExceptionHandler {

    // Handle 403 Forbidden errors
    @ExceptionHandler(AccessDeniedException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public String handleAccessDeniedException(AccessDeniedException ex, Model model) {
        model.addAttribute("error", "403 Forbidden");
        model.addAttribute("message", "У вас нет прав доступа к этому ресурсу.");
        return "error/403";
    }

    // Handle other errors
    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public String handleGenericException(Exception ex, Model model) {
        model.addAttribute("error", "Произошла ошибка");
        model.addAttribute("message", ex.getMessage());
        return "error/general-error";
    }

    @RequestMapping("/error")
    public String handleError(HttpServletRequest request, Model model) {
        Object status = request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);
        if (status != null) {
            int statusCode = Integer.parseInt(status.toString());
            if (statusCode == HttpStatus.FORBIDDEN.value()) {
                model.addAttribute("error", "403 Forbidden");
                model.addAttribute("message", "У вас нет прав доступа к этому ресурсу.");
                return "error/403";  // Шаблон должен существовать
            }
        }
        model.addAttribute("error", "Ошибка");
        model.addAttribute("message", "Что-то пошло не так.");
        return "error/general-error";  // Шаблон должен существовать
    }

    @Bean
    public WebServerFactoryCustomizer<ConfigurableServletWebServerFactory> containerCustomizer() {
        return container -> container.addErrorPages(new ErrorPage(HttpStatus.FORBIDDEN, "/error"));
    }
}