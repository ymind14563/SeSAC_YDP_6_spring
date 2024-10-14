package sesac.spring_boot_board.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ViewController {

    @GetMapping("/")
    public String redirectToPosts() {
        return "redirect:/posts";
    }

    // GET /posts 요청 시 boardList.html 템플릿뷰 반환
    @GetMapping("/posts")
    public String listPosts() {
        return "boardList";
    }

    // GET /posts/new 요청 시 boardForm.html 템플릿 뷰 반환 ("새 유저 생성")
    @GetMapping("/posts/new")
    public String newPostForm() {
        return "boardForm";
    }

    // GET /posts/{id}/new 요청시 boardForm.html 템플릿 뷰 반환 ("기존 유저 정보 수정")
    @GetMapping("/posts/{id}/edit")
    public String editPostForm() {
        return "boardForm";
    }
}
