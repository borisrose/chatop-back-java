package boris_rentals.api.security.filter;

import java.io.IOException;
import java.nio.file.AccessDeniedException;

import org.springframework.stereotype.Component;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;


public class TestFilter implements Filter {

    private boolean isUserAllowed(String id){
        return true; 
    }


    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {

        HttpServletRequest request = (HttpServletRequest) servletRequest;

        HttpServletResponse response = (HttpServletResponse) servletResponse;

        String testId = request.getHeader("X-Test-Id"); 

        boolean hasAccess = isUserAllowed(testId); 

        if (hasAccess) {

            filterChain.doFilter(request, response); 
            return;
        }

        throw new AccessDeniedException("Access denied"); 
    } 
}
