package sesac.spring_boot_security.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "User", uniqueConstraints = {@UniqueConstraint(columnNames = "email")})
// uniqueConstraints = {@UniqueConstraint(columnNames = "email")}
// - email 필드 unique 제약 조건
// - unique 제약 조건? 해당 테이블의 컬럼이 중복된 값을 허용하지 않음, pk 와 다른 점은 null 이 들어갈 수 있음
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", updatable = false)
    private Long id;

    @Column(name = "username",updatable = false)
    private String username;

    @Column(name = "email", updatable = false)
    private String email;

    @Column(name = "password", updatable = false)
    private String password;
}
