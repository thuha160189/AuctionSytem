package edu.miu.cs.neptune.exception;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

@ControllerAdvice
public class ControllerExceptionHandler {

    @ExceptionHandler
    public ModelAndView handleError(HttpServletRequest req, RuntimeException exception) {
        ModelAndView mav = new ModelAndView();
        mav.addObject("error", exception.getMessage());
        mav.addObject("url", req.getRequestURL()+ "?" + req.getQueryString());
        mav.setViewName("error");
        return mav;
    }
}
