package sesac.spring_boot_security.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sesac.spring_boot_security.entity.UserEntity;
import sesac.spring_boot_security.repository.UserRepository;

@Slf4j
@Service
public class UserService {

    @Autowired
    private UserRepository repository;

    // 회원가입
    public UserEntity create(final UserEntity userEntity) {
        // 유효성 검사 1) userEntity 혹은 email 이 null 인 경우 예외 던짐
        if (userEntity == null || userEntity.getEmail() == null){
            throw new RuntimeException("Invalid argument");
        }

        final String email = userEntity.getEmail();

        // 유효성 검사 2) email 이 이미 존재하는 경우 예외를 던짐 (email 필드는 unique 해야하므로)
        if (repository.existsByEmail(email)) {
            log.warn("Email already exists {}", email);
            throw new RuntimeException("Email already exists");
        }

        return repository.save(userEntity); // UserEntity 를 DB 에 저장
    }

    // 인증: 이메일과 비밀번호로 사용자 조회
    public UserEntity getByCredentials(final String email, final String password) {
        return repository.findByEmailAndPassword(email, password); // DB 에서 해당 email, password 가 일치하는 유저 조회
    }
}
