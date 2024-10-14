package sesac.spring_boot_board.domain;

import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;

@Getter
@Setter
public class Post {
    private Long id;
    private String writer;
    private String title;
    private String content;
    private Timestamp registered;

}