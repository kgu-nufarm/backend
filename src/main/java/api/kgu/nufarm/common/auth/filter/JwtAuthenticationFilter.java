package api.kgu.nufarm.common.auth.filter;

import api.kgu.nufarm.common.auth.dao.TokenRepository;
import api.kgu.nufarm.common.auth.jwt.JwtTokenProvider;
import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Slf4j
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtTokenProvider jwtTokenProvider;
    private final TokenRepository tokenRepository;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        try {
            String accessToken = jwtTokenProvider.resolveToken(request);

            if (accessToken != null && jwtTokenProvider.validateToken(accessToken)) {
                String userId = jwtTokenProvider.getAuthentication(accessToken).getName();
                String redisToken = tokenRepository.getAccessToken(userId);

                if (redisToken != null && redisToken.equals(accessToken)) {
                    Authentication authentication = jwtTokenProvider.getAuthentication(accessToken);
                    SecurityContextHolder.getContext().setAuthentication(authentication);
                } else {
                    log.info("Invalid access token in Redis for user: {}", userId);
                    setErrorResponse(response, "Invalid access token", HttpServletResponse.SC_UNAUTHORIZED);
                    return;
                }
            }
        } catch (ExpiredJwtException e) {
            log.info("Expired access token: {}", e.getMessage());
            setErrorResponse(response, "Expired access token", HttpServletResponse.SC_UNAUTHORIZED);
            return;
        } catch (Exception e) {
            log.error("Error processing JWT token: {}", e.getMessage());
            setErrorResponse(response, "JWT authentication error", HttpServletResponse.SC_UNAUTHORIZED);
            return;
        }

        filterChain.doFilter(request, response);
    }

    private void setErrorResponse(HttpServletResponse response, String message, int statusCode) throws IOException {
        response.setContentType("application/json");
        response.setStatus(statusCode);
        response.getWriter().write("{\"error\": \"" + message + "\"}");
    }
}
