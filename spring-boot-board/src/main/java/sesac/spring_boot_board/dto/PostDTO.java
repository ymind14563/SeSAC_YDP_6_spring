package sesac.spring_boot_board.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Timestamp;

@Getter
@Setter
@NoArgsConstructor
public class PostDTO {
    private Long id;
    private String writer;
    private String title;
    private String content;
    private Timestamp registered;
    private int no;
}