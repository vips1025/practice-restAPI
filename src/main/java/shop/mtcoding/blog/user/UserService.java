package shop.mtcoding.blog.user;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import shop.mtcoding.blog._core.errors.exception.Exception400;
import shop.mtcoding.blog._core.errors.exception.Exception401;
import shop.mtcoding.blog._core.errors.exception.Exception404;
import shop.mtcoding.blog._core.utils.JwtUtil;

import java.util.Optional;

@RequiredArgsConstructor
@Service // IoC 등록
public class UserService {

    private final UserJPARepository userJPARepository;

    @Transactional
    public SessionUser 회원수정(int id, UserRequest.UpdateDTO reqDTO) {
        User user = userJPARepository.findById(id)
                .orElseThrow(() -> new Exception404("회원정보를 찾을 수 없습니다"));

        user.setPassword(reqDTO.getPassword());
        user.setEmail(reqDTO.getEmail());
        return new SessionUser(user);
    } // 더티체킹

    public UserResponse.DTO 회원조회(int id) {
        User user = userJPARepository.findById(id)
                .orElseThrow(() -> new Exception404("회원정보를 찾을 수 없습니다"));
        return new UserResponse.DTO(user); // 엔티티 생명 종료
    }

    public String 로그인(UserRequest.LoginDTO reqDTO) {
        User user = userJPARepository.findByUsernameAndPassword(reqDTO.getUsername(), reqDTO.getPassword())
                .orElseThrow(() -> new Exception401("인증되지 않았습니다"));
        String jwt = JwtUtil.create(user);
        return jwt;
    }

    @Transactional
    public UserResponse.DTO 회원가입(UserRequest.JoinDTO reqDTO) {
        Optional<User> userOP = userJPARepository.findByUsername(reqDTO.getUsername());

        if (userOP.isPresent()) {
            throw new Exception400("중복된 유저네임입니다");
        }

        // 2. 회원가입
        User user = userJPARepository.save(reqDTO.toEntity());
        return new UserResponse.DTO(user);
    }
}
