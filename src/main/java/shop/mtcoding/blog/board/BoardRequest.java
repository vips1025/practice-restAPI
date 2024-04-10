package shop.mtcoding.blog.board;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Data;
import shop.mtcoding.blog.user.User;

public class BoardRequest {

    @Data
    public static class UpdateDTO {
        @Size(min = 1, max = 10, message = "제목은 10자를 초과할 수 없습니다")
        @NotEmpty(message = "제목은 공백일 수 없습니다")
        private String title;
        @NotEmpty
        private String content;
    }

    @Data
    public static class SaveDTO {
        @Size(min = 1, max = 10, message = "제목은 10자를 초과할 수 없습니다")
        @NotEmpty(message = "제목은 공백일 수 없습니다")
        private String title;
        @NotEmpty
        private String content;

        public Board toEntity(User user) {
            return Board.builder()
                    .title(title)
                    .content(content)
                    .user(user)
                    .build();
        }
    }
}
