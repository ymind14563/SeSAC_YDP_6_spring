package sesac.spring_boot_jpa.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name="users") // users 명시하지 않으면 user 로 생성됨
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User {
    @Id // pk 지정
    @GeneratedValue(strategy = GenerationType.IDENTITY) // pk 생성 auto increment 포함
    private Long id;

    @Column(nullable = false, length = 50)
    private String username;

    @Column(nullable = false, length = 100)
    private String email;

    @Column(name="create_at", nullable = false, updatable = false,
            columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private LocalDateTime createAt;

    @PrePersist // 엔티티가 데이터베이스에 저장되기 전에 필요한 초기화 작업 수행
    protected void onCreate() {
        // 엔티티가 처음 저장될 때 createAt 필드에 현재 시작을 저장
        // 메서드 이름 자유롭게 설정 가능(단, 메서드 반환 타입은 void, 매개변수 X)
        createAt = LocalDateTime.now();
    }
}
