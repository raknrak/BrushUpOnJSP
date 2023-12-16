package org.zerock.dao;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.zerock.jdbcex.dao.TodoDAO;
import org.zerock.jdbcex.domain.TodoVO;

import java.time.LocalDate;
import java.util.List;

public class TodoDAOTest {

    private TodoDAO todoDAO;

    @BeforeEach
    public void ready(){
        todoDAO = new TodoDAO();
    }
    @Test
    public void testTime() throws Exception{
        System.out.println(todoDAO.getTime2());
    }
    // C
    @Test
    public void testInsert() throws Exception{
        TodoVO todoVO = TodoVO.builder()
                // builder 체인식으로 작성(DAO의 순서와 상관없이 입력 가능함., 내가 선택한 이름만 입력됨)
                .title("Sample Title...")
                .dueDate(LocalDate.of(2023, 12, 12))
                .build();

        todoDAO.insert(todoVO);
    }
    // R
    @Test
    public void testList() throws Exception{
        List<TodoVO> list = todoDAO.selectAll();
        list.forEach(vo -> System.out.println(vo));
    }
    // R
    @Test
    public void testSelectOne()throws Exception{
        Long tno = 1L; // 반드시 존재하는 번호를 이용

        TodoVO vo = todoDAO.selectOne(tno);
        System.out.println(vo);
    }
    // U
    @Test
    public void testUpdate()throws Exception{
        TodoVO todoVO = TodoVO.builder()
                .tno(1L)
                .title("Sample Title...")
                .dueDate(LocalDate.of(2023, 12, 13))
                .finished(true)
                .build();

        todoDAO.updateOne(todoVO);
    }

    //deletes는 알아서 짜보기
    @Test
    public void testDelete()throws Exception{
        TodoVO todoVO = TodoVO.builder()
                .tno(8L)
                .build();

        todoDAO.deleteOne(todoVO.getTno());
    }
}
