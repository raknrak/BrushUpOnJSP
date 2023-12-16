package org.zerock.jdbcex.domain;

import lombok.*;

import java.time.LocalDate;

@Getter
@Builder
@ToString
@AllArgsConstructor // Mapper 사용시 이 두 가지 생성자를 반드시 추가해야함.
@NoArgsConstructor  // 두 가지가 들어가야 제 기능을 함.
public class TodoVO {

    private Long tno;
    private String title;
    private LocalDate dueDate;
    private boolean finished;

}
