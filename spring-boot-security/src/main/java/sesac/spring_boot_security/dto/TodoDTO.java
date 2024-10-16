package sesac.spring_boot_security.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import sesac.spring_boot_security.entity.TodoEntity;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data // getter, setter, equals, hashCode, toString 메서드를 자동 생성
public class TodoDTO {
    private Long id;
    private String title;
    private boolean done;

    // entity 에서 id 를 가져오려하는데 private 이기 때문에 getter setter 로 가져와야 함
    // @Data 에 같은 기능이 있기 때문에 따로 @Getter @Setter 없어도 사용 가능

    // TodoEntity 를 받아 DTO 객체로 변환하는 생성자
    public TodoDTO(final TodoEntity entity) {
        this.id = entity.getId();
        this.title = entity.getTitle();
        this.done = entity.isDone();
    }

    // DTO 를 Entity 로 변환하는 메소드
    public static TodoEntity todoEntity(final TodoDTO dto) {
        return TodoEntity.builder()
                .id(dto.getId())
                .title(dto.getTitle())
                .done(dto.isDone())
                .build();
    }
}