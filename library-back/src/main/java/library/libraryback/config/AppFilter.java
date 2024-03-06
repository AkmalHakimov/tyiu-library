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
            String token = request.getHeader("Authorization").replaceFirst("Bearer ","");
            String requestPath = request.getRequestURI();
            if(requestPath.startsWith("/api")){
                if(request.getMethod().equalsIgnoreCase("get") && token != null){
                    try {
                        filterChain.doFilter(request, response);
                        return;
                    }catch (Exception e){
                        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                        response.getOutputStream().write("Invalid Token".getBytes());
                        response.getOutputStream().close();
                    }
                }else
                    if(isOpenUrl(requestPath)){
                    try {
                        filterChain.doFilter(request, response);
                        return;
                    }catch (Exception e){
                        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                        response.getOutputStream().write("Invalid Token".getBytes());
                        response.getOutputStream().close();
                    }
                }

                if(token != null){
                    try {
                        String subject = jwtService.extractUserFromJwt(token);
                        UserDetails userDetails = userRepo.findById(subject).orElseThrow();
                        UsernamePasswordAuthenticationToken authenticationToken =
                                new UsernamePasswordAuthenticationToken(
                                        userDetails,
                                        null,
                                        userDetails.getAuthorities()
                                );
                        SecurityContextHolder.getContext().setAuthentication(authenticationToken);
                    } catch (ExpiredJwtException e) {
                        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED); // 401 Unauthorized
                        response.getOutputStream().write("Token has expired".getBytes());
                        response.getOutputStream().close();
                        return;
                    } catch (Exception e) {
                        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED); // 401 Unauthorized
                        response.getOutputStream().write("Invalid Token".getBytes());
                        response.getOutputStream().close();
                        return;
                    }
                }else {
                    response.setStatus(HttpServletResponse.SC_UNAUTHORIZED); // 401 Unauthorized
                    response.getOutputStream().write("Authorization header missing".getBytes());
                    response.getOutputStream().close();
                    return;
                }
            }
            filterChain.doFilter(request,response);
        }

        private static boolean isOpenUrl(String requestPath){
            return requestPath.equals("/api/auth/login")
                    || requestPath.equals("/api/auth/access");
        }
    }
