package hello.login.web.fiter;

import lombok.extern.slf4j.Slf4j;
import org.springframework.util.PatternMatchUtils;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.UUID;

@Slf4j
public class LogFilter implements Filter {
    private static final String[] whiteList = {"/css/*"};

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        String requestURI = httpServletRequest.getRequestURI();
        String uuid = UUID.randomUUID().toString();

        try {
            if (isLogCheckPath(requestURI)) {
                log.info("REQUEST [{}][{}]", uuid, requestURI);
            }
            chain.doFilter(request, response);
        } catch (Exception e) {
            throw e;
        } finally {
            if (isLogCheckPath(requestURI)) {
                log.info("RESPONSE [{}][{}]", uuid, requestURI);
            }
        }
    }

    /**
     * 화이트 리스트의 경우 로그 X
     */
    private boolean isLogCheckPath(String requestURI) {
        return !PatternMatchUtils.simpleMatch(whiteList, requestURI);
    }
}
