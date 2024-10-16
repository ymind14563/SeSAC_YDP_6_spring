package sesac.spring_boot_board.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sesac.spring_boot_board.dto.PostDTO;
import sesac.spring_boot_board.mapper.PostMapper;
import sesac.spring_boot_board.domain.Post;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PostService {
    @Autowired
    private PostMapper postMapper;

    public List<PostDTO> getAllPosts() {

        List<Post> posts = postMapper.findAll();

        List<PostDTO> postDTOs = new ArrayList<>();

        for (Post post: posts) {
            PostDTO postDTO = convertToDto(post);
            postDTOs.add(postDTO);
        }

        return postDTOs;
    }

    public PostDTO getPostById(Long id) {
        Post post = postMapper.findById(id);
        return  convertToDto(post);
    }

    public void createPost(PostDTO postDTO) {
        Post post = convertToEntity(postDTO);
        postMapper.insert(post);
    }

    public void updatePost(PostDTO postDTO) {
        Post post = convertToEntity(postDTO);
        postMapper.update(post);
    }

    private Post convertToEntity(PostDTO dto) {
        Post post = new Post();
        post.setId(dto.getId());
        post.setWriter(dto.getWriter());
        post.setTitle(dto.getTitle());
        post.setContent(dto.getContent());
        post.setRegistered(dto.getRegistered());
        return post;
    }

    public void deletePost(Long id) {
        postMapper.delete(id);
    }

    private PostDTO convertToDto(Post post) {
        PostDTO dto = new PostDTO();
        dto.setId(post.getId());
        dto.setWriter(post.getWriter());
        dto.setTitle(post.getTitle());
        dto.setContent(post.getContent());
        dto.setNo((int) (post.getId() + 100));
        dto.setRegistered(post.getRegistered());
        return dto;
    }

    // 작성자 검색
    public List<PostDTO> getPostsByWriter(String writer) {
        List<Post> posts = postMapper.findByWriter(writer);
        List<PostDTO> postDTOs = new ArrayList<>();
        for (Post post : posts) {
            postDTOs.add(convertToDto(post));
        }
        return postDTOs;
    }

}

