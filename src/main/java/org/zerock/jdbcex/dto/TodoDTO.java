package org.zerock.jdbcex.dto;

import lombok.*;

import java.time.LocalDate;
@Data // Getter, Setter, toString, equals -> VO는 Getter만
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TodoDTO {
    private Long tno;

    private String title;

    private LocalDate dueDate;

    private boolean finished;

}
