package org.zerock.jdbcex.service;

import lombok.extern.log4j.Log4j2;
import org.modelmapper.ModelMapper;
import org.zerock.jdbcex.dao.TodoDAO;
import org.zerock.jdbcex.domain.TodoVO;
import org.zerock.jdbcex.dto.TodoDTO;
import org.zerock.jdbcex.util.MapperUtil;

import java.util.List;
import java.util.stream.Collectors;

@Log4j2
public enum TodoService {
    INSTANCE;

    private TodoDAO dao;

    private ModelMapper modelMapper;

    TodoService(){ // 생성자
        dao = new TodoDAO();
        modelMapper = MapperUtil.INSTANCE.get();

    }
    // 컨트롤러나 서비스에서는 resister(비즈니스 용어)
    // dao 에서는 insert 라고함.
    public void register(TodoDTO todoDTO) throws Exception{
        TodoVO todoVO = modelMapper.map(todoDTO, TodoVO.class); // DTO에 있는것을 VO에 넣고 그걸 작업함.
//        System.out.println("todoVO: " +  todoVO);
        log.info(todoVO);
        dao.insert(todoVO); // 서비스의 역할.
        // int를 반환하므로 이를 이용해서 예외처리도 가능함.
        // 서비스를 지나가면 commit , 못 지나가면 rollback
        // 서비스는 중간 단계에서 안전장치 역할을 함
        // 변경사항 발생시 컨트롤러를 수정하는 것이아니라, 서비스를 변경하면 됨.
        // 서비스 Transaction기능 + 안전장치.
    }

    /*public void resister(TodoVO todoVO) throws Exception{

        dao.insert(todoVO);  // 서비스가 필요한 이유? : 안전장치.
    }*/
    public List<TodoDTO> listAll()throws Exception{ // 전체목록
        List<TodoVO> voList = dao.selectAll();
        log.info("voList................");
        log.info(voList);

        List<TodoDTO> dtoList=voList.stream()
                //modelMapper = 통채로 넘김.
                .map(vo->modelMapper.map(vo, TodoDTO.class))
                .collect(Collectors.toList());
        return dtoList;


    }
    public TodoDTO get(Long tno) throws Exception{ // 상세보기
        log.info("tno:"  + tno);
        TodoVO todoVO = dao.selectOne(tno);
        TodoDTO todoDTO = modelMapper.map(todoVO, TodoDTO.class);
        return todoDTO;
    }

    public void remove(Long tno)throws Exception{// 삭제
        log.info("tno : " + tno);
        dao.deleteOne(tno);
    }
    public void modify(TodoDTO todoDTO) throws Exception{ // 수정
        log.info("todoDTO: " + todoDTO);
        TodoVO todoVO = modelMapper.map(todoDTO, TodoVO.class);
        dao.updateOne(todoVO);
    }
}
