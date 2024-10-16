package sesac.spring_boot_board.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import sesac.spring_boot_board.dto.PostDTO;
import sesac.spring_boot_board.service.PostService;


import java.util.List;

@RestController
@RequestMapping("api/posts")
public class PostController {

    @Autowired
    private PostService postService;

    @GetMapping
    public List<PostDTO> listPosts() {
        return postService.getAllPosts();
    }

    @GetMapping("/{id}")
    public PostDTO getPost(@PathVariable Long id) {
        return postService.getPostById(id);
    }

    @PostMapping
    public PostDTO createPost(@RequestBody PostDTO postDTO) {
        postService.createPost(postDTO);
        return postDTO;
    }

    @PutMapping("/{id}")
    public PostDTO updatePost(@PathVariable Long id, @RequestBody PostDTO postDTO) {
        postDTO.setId(id);
        postService.updatePost(postDTO);
        return postDTO;
    }

    @DeleteMapping("/{id}")
    public void deletePost(@PathVariable Long id) {
        postService.deletePost(id);
    }

    // 작성자 검색
    @GetMapping("/search")
    public List<PostDTO> searchPostsByWriter(@RequestParam String writer) {
        return postService.getPostsByWriter(writer);
    }

}
