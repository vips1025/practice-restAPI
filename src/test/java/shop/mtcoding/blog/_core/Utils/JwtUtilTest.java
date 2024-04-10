package shop.mtcoding.blog._core.Utils;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import org.junit.jupiter.api.Test;
import shop.mtcoding.blog._core.utils.JwtUtil;
import shop.mtcoding.blog.user.User;

import java.util.Date;

public class JwtUtilTest {
    @Test
    public void create_test(){
        // given
        User user = User.builder()
                .id(1)
                .username("ssar")
                .build();

        // when
        String jwt = JWT.create()
                .withSubject("blog")
                .withExpiresAt(new Date(System.currentTimeMillis()+ 1000*10))
                .withClaim("id", user.getId())
                .withClaim("username", user.getUsername())
                .sign(Algorithm.HMAC512("metacoding"));
        System.out.println(jwt);
    }

    @Test
    public void verify_test(){
        // given
        String jwt = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJibG9nIiwiaWQiOjEsImV4cCI6MTcxMjMwNzEwNywidXNlcm5hbWUiOiJzc2FyIn0.BrwySVclycSnpCog_eA3ARI0dfSYi7K254uYXLsqb_1VYjIRBfKQZKWh_E39IvZ5_EQif-kwHUJKiWQQwgRtDQ";

        // when
        JwtUtil.verify(jwt);

        // then
    }
}
