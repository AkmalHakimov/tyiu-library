package library.libraryback.config;


import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import library.libraryback.repository.UserRepo;
import library.libraryback.services.JwtService.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class AppFilter extends OncePerRequestFilter {

    private final JwtService jwtService;
    private final UserRepo userRepo;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String token = request.getHeader("token");
        String requestPath = request.getRequestURI();

        if(requestPath.startsWith("/api")){
            if(request.getMethod().equalsIgnoreCase("get")){
                try {
                    filterChain.doFilter(request, response);
                }catch (Exception e){
                    response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                    response.getWriter().write("Invalid token");
                    response.getWriter().flush();
                }
            }else if(isOpenUrl(requestPath,request.getMethod())){
                try {
                    filterChain.doFilter(request, response);
                }catch (Exception e){
                    response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                    response.getWriter().write("Invalid token");
                    response.getWriter().flush();
                }
            }

            if(token != null){
                try {
                    String subject = jwtService.extractUserFromJwt(token);
                    UserDetails userDetails = userRepo.findById(subject).orElseThrow();
//                System.out.println(userDetails.getUsername());
                    UsernamePasswordAuthenticationToken authenticationToken =
                            new UsernamePasswordAuthenticationToken(
                                    userDetails,
                                    null,
                                    userDetails.getAuthorities()
                            );
                    SecurityContextHolder.getContext().setAuthentication(authenticationToken);
                } catch (ExpiredJwtException e) {
                    response.setStatus(HttpServletResponse.SC_UNAUTHORIZED); // 401 Unauthorized
                    response.getWriter().write("Token expired");
                    response.getWriter().flush();
                    return;
                } catch (Exception e) {
                    response.setStatus(HttpServletResponse.SC_UNAUTHORIZED); // 401 Unauthorized
                    response.getWriter().write("Invalid token");
                    response.getWriter().flush();
                    return;
                }
            }else {
                // No Authorization header found, throw 401 Unauthorized error
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED); // 401 Unauthorized
                response.getOutputStream().close();
                response.getWriter().write("Authorization header missing");
                response.getWriter().flush();
                return;
            }
        }
        filterChain.doFilter(request,response);
    }

    private static boolean isOpenUrl(String requestPath, String method){
        return requestPath.equals("/api/auth/login")
                || requestPath.equals("/api/auth/access")
                || requestPath.equals("/api/book")
                || requestPath.startsWith("/api/category");
    }
}
