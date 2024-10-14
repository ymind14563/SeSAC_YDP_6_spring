package sesac.spring_boot_jpa.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import sesac.spring_boot_jpa.entity.User;

@Repository
// 해당 인터페이스가 Repository 컨포넌트임을 명시
public interface UserRepository extends JpaRepository<User, Long> {
    // ㄴ JpaRepository<User, Long> 을 상속받아 기본적인 CRUD 작업을 위한 메서드들을 제공 받음
    // ex. findAll(), findById(), save(), deleteById(), ... 등

    // JpaRepository<User, Long> 의미
    // 제네릭 첫번째 타입: 관련 테이블
    // 제네릭 두번째 타입: 관련 테이블 PK 의 타입 (Entity 에서 설정한 것과 일치해야 함)
}
