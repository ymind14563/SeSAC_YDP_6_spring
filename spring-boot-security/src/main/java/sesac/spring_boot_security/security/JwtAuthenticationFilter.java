package sesac.spring_boot_security.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

/*
스프링 시큐리티 => "서블릿 필터의 집합"
서블릿 필터 => 서블릿 실행 전에 실행되는 클래스
디스패처 서블릿이 실행되기 전에 실행
스프링이 구현하는 서블릿 이름 => dispatcher servlet

개발자 할일)
서블릿 필터를 구현, 서블릿 필터를 서블릿 컨테이너가 실행하도록 설정해주기만 하면 돼!
        - 적절한 HTTP 요청이 오면 필터가 통과되어 디스패처 서블릿으로 넘어가고 컨트롤러한테 전달
- HTTP 요청이 잘못되면 필터에거 걸러져서 HTTP 요청 거절

참고: https://velog.io/@minthug94_/Filter-FilterChain
*/




@Slf4j
@Service // 스프링 컴포넌트로 등록

// 클라이언트 요청 당 한번만 실행하는 필터
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    // OncePerRequestFilter: 한 요청당 반드시 한 번만 실행

    @Autowired
    private TokenProvider tokenProvider;

    @Override
    protected void doFilterInternal(
            HttpServletRequest request, HttpServletResponse response,
            FilterChain filterChain
    ) throws ServletException, IOException {

        try {
            // req 에서 token 꺼내오기
            String token = parseBearerToken(request);
            log.info("JwtAuthenticationFilter is running...");

            // token 검사
            if (token != null && !token.equalsIgnoreCase("null")) { // equalsIgnoreCase: 대소문자 구별 없이 전부
                String userId = tokenProvider.validateAndGetUserId(token);
                log.info("Authenticated user id: {}", userId);

                // 이전에 추출한 userId 로 인증 객체 생성
                AbstractAuthenticationToken authentication
                        = new UsernamePasswordAuthenticationToken(userId, null, AuthorityUtils.NO_AUTHORITIES);
                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                // 생성한 인증 객체를 Security Context 에 설정
                // - SecurityContextHolder 의 createEmptyContext 메서드를 이용해 SecurityContext 객체를 생성
                SecurityContext securityContext = SecurityContextHolder.createEmptyContext();
                // - 생성한 securityContext 에 인증된 정보인 authentication 을 넣고
                securityContext.setAuthentication(authentication);
                // - 다시 securityContextHolder 에 context 로 등록
                SecurityContextHolder.setContext(securityContext);
            }
        } catch (Exception e) {
            logger.error("Could not set user authentication in security context", e);
        }

        // 필터 체인을 계속 진행
        filterChain.doFilter(request, response);
    }

    // req.headers 에서 Bearer Token 을 꺼내오는
    private String parseBearerToken(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");

        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
            // req.header jwt 토큰이 다음과 같이 들어있으므로 문자열 슬라이싱 진행하여 반환
            // Authentication: "Bearer ~~~~~.~~~~~.~~~~~" 형식
        }

        return null;
    }



}