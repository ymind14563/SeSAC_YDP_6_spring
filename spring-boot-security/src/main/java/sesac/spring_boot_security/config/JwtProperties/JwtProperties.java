package sesac.spring_boot_security.config.JwtProperties;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Getter
@Setter
@Component
@ConfigurationProperties("jwt") // properties 파일 내 설정에서 jwt 로 시작하므로
public class JwtProperties {
    private String issuer;
    private String secretKey;
}

// application.properties 에 있는 설정 값을 가져오고자 하는 클래스
