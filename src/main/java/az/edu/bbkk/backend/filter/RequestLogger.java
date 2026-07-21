package az.edu.bbkk.backend.filter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Date;

@Component
public class RequestLogger extends OncePerRequestFilter {
   @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
      response.setHeader("x-time", "xyz");
      System.out.println("new request recieved from:" + request.getRequestId() + " on this ip:" + request.getHeader("x-forwarded-for") + " with this method:" + request.getMethod());

      // add sentry etc for best logging

      filterChain.doFilter(request,response);
   }
}
