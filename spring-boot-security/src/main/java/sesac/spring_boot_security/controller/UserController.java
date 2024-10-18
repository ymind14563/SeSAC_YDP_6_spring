package sesac.spring_boot_security.controller;

import org.antlr.v4.runtime.Token;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import sesac.spring_boot_security.dto.ResponseDTO;
import sesac.spring_boot_security.dto.UserDTO;
import sesac.spring_boot_security.entity.UserEntity;
import sesac.spring_boot_security.security.TokenProvider;
import sesac.spring_boot_security.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/auth")
public class UserController {
    @Autowired
    private UserService service;

    // [after] jwt token 적용 후
    @Autowired
    private TokenProvider tokenProvider;

    // [after] 패스워드 암호화 적용 후

    /*
    security/SecurityConfig 대신에 이렇게 작성해도 되지만 장점이 사라지므로 권장되지 않음
    private PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
    */

    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@RequestBody UserDTO userDTO) {
        try {
            // 요청 분문을 이용해 저장할 사용자 만들기
            UserEntity user = UserEntity.builder()
                    .email(userDTO.getEmail())
                    .username(userDTO.getUsername())
//                    .password(userDTO.getPassword())
                    .password(passwordEncoder.encode(userDTO.getPassword())) // 암호화된 비밀번호로 user 객체 생성
                    .build();

            // 서비스를 이용해 레포지터리에 사용자 저장
            UserEntity registeredUser = service.create(user);
            UserDTO responsedUserDTO = UserDTO.builder()
                    .email(registeredUser.getEmail())
                    .id(registeredUser.getId())
                    .username(registeredUser.getUsername())
                    .build();

            // 사용자 정보는 항상 1개이므로
            // 리스트로 만들어 응답하는 ResponseDTO 를 이용하지 않고 그냥 UserDTO 리턴
            return ResponseEntity.ok().body(responsedUserDTO);

        } catch (Exception e) {

            ResponseDTO responseDTO = ResponseDTO.builder().error(e.getMessage()).build();

            return ResponseEntity.badRequest().body(responseDTO);
        }
    }


    @PostMapping("/signin")
    public ResponseEntity<?> authenticate(@RequestBody UserDTO userDTO) {
        UserEntity user = service.getByCredentials(
                userDTO.getEmail(),
                userDTO.getPassword(),
                passwordEncoder // [after] 패스워드 암호화 적용 후
        );

        if (user != null) {
            // 로그인 검사 통과 (해당 유저가 존재)

            // [before] jwt token 적용 전
//            final UserDTO responsedUserDTO = userDTO.builder()
//                    .email(user.getEmail())
//                    .id(user.getId())
//                    .build();

            // [after] jwt token 적용 후
            final String token = tokenProvider.create(user);
            final UserDTO responsedUserDTO = userDTO.builder()
                    .email(user.getEmail())
                    .id(user.getId())
                    .token(token) // [after] jwt 토큰 저장
                    .build();

            return ResponseEntity.ok().body(responsedUserDTO);
        } else {
            // 로그인 검사 실패 (해당 유저가 존재하지 않았으므로)
            ResponseDTO responseDTO = ResponseDTO.builder()
                    .error("Login failed.")
                    .build();

            return ResponseEntity.badRequest().body(responseDTO);
        }
    }
}