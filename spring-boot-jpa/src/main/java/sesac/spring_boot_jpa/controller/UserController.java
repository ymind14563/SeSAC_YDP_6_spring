package sesac.spring_boot_jpa.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import sesac.spring_boot_jpa.dto.UserDTO;
import sesac.spring_boot_jpa.service.UserService;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {
    @Autowired
    private UserService userService;

    @GetMapping
    public List<UserDTO> listUsers() {
        return userService.getAllUsers();
    }

    @GetMapping("/{id}")
    public UserDTO getUser(@PathVariable("id") Long id) { // 3.2.X 이상 버전 사용 시에 명시해야함 (Spring Framework 5.3.2 이상) - 안정성 및 명확성 목적
//    public UserDTO getUser(@PathVariable Long id) { // 3.2.X 전 까지는 명시하지 않아도 사용 가능
        return userService.getUserById(id);
    }
}
