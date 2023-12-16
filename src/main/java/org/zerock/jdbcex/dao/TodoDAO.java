package org.zerock.jdbcex.dao;

import lombok.Cleanup;
import org.zerock.jdbcex.domain.TodoVO;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class TodoDAO {
    public String getTime(){
        String now = null;
        // try catch finally대시 -> try-catch 안에 넣으면 close가 됨.
        // 또한 이 코드가 지저분해 cleanup을 사용하게 됨.(getTime2)
        try(Connection connection = ConnectionUtil.INSTANCE.getConnection();
            PreparedStatement preparedStatement = connection.
                                                  prepareStatement("select now()");
        ResultSet resultSet = preparedStatement.executeQuery();
        ){
        resultSet.next(); // 다음으로 가라 enter와 비슷한 의미.

        now= resultSet.getString(1);

        } catch(Exception e){
             e.printStackTrace();
        }
        return now;
    }
    // 복잡한 것을 @Cleanup을 통해 간결하게 작성할 수 있다.
    // 실무에서는 @Cleanup을 기존 프로젝트에는 적용하지 말고, 건드리지 말아라 - 혼자만 알기 때문?
    // 신규 프로젝트에서는 적용해도 된다?
    // 최소한의 코드로 close()가 보장되는 코드를 작성할 수 있다는 장점이 있음.

    public String getTime2() throws Exception { //@Cleanup을 사용하기 때문에 tyr-catch가 필요없음
        @Cleanup Connection connection = ConnectionUtil.INSTANCE.getConnection();
        @Cleanup PreparedStatement preparedStatement = connection.
                prepareStatement("select now()");
        @Cleanup ResultSet resultSet = preparedStatement.executeQuery();
        resultSet.next();
        String now = resultSet.getString(1);
        return now;
    }
    //DAO의 등록 기능 구현하기
    // TodoVO 객체를 데이터베이스에 추가하는 기능.
    //insert() 메소드를 구성 및 작성
    public void insert(TodoVO vo) throws Exception{
        String sql = "insert into tbl_todo (title, dueDate, finished) values (?, ?, ?)";
        /*String sql = "INSERT INTO tbl_todo(title, dueDate, finished)\n" +
                "VALUES(?, ?, ?);";*/ // 원래의 SQL문을 복사해서 넣고 values 값만 ?로 변경하기.
        @Cleanup Connection connection = ConnectionUtil.INSTANCE.getConnection();
        // 쿼리를 다 넣는게 아니라 sql을 만들어서 sql로 넣음.
        @Cleanup PreparedStatement preparedStatement = connection.prepareStatement(sql);

        preparedStatement.setString(1, vo.getTitle());
        preparedStatement.setDate(2, Date.valueOf(vo.getDueDate())); // 날짜 형식은 좀 까다로움.
        preparedStatement.setBoolean(3, vo.isFinished());

        // 실제 내용이 고쳐지는 것이라 Query가 아닌 update를 사용한다.
        preparedStatement.executeUpdate();
    }

    public List<TodoVO> selectAll() throws Exception{

        String sql = "select * from tbl_todo";
        @Cleanup Connection connection = ConnectionUtil.INSTANCE.getConnection();
        @Cleanup PreparedStatement preparedStatement = connection.
                prepareStatement(sql);
        @Cleanup ResultSet resultSet = preparedStatement.executeQuery();

        List<TodoVO> list = new ArrayList<>();

        while(resultSet.next()){ // 다음줄 -> 더 이상 다음줄이 없으면 false
            TodoVO vo = TodoVO.builder()
                    .tno(resultSet.getLong("tno"))
                    .title(resultSet.getString("title"))
                    .dueDate(resultSet.getDate("dueDate").toLocalDate())
                    .finished(resultSet.getBoolean("finished"))
                    .build();

            list.add(vo); // 한 줄 읽을 때 마다 리스트에 넣어 순차적으로 들어감.

        }
        return list;

    }
    public TodoVO selectOne(Long tno) throws Exception{
        String sql = "select tno, title, dueDate, finished from tbl_todo where tno=? ";

        @Cleanup Connection connection = ConnectionUtil.INSTANCE.getConnection();
        @Cleanup PreparedStatement preparedStatement = connection.prepareStatement(sql);

        preparedStatement.setLong(1,tno);

        @Cleanup ResultSet resultSet = preparedStatement.executeQuery();

        List<TodoVO> list = new ArrayList<>();

        resultSet.next();
        TodoVO vo = TodoVO.builder()
                .tno(resultSet.getLong("tno"))
                .title(resultSet.getString("title"))
                .dueDate(resultSet.getDate("dueDate").toLocalDate())
                .finished(resultSet.getBoolean("finished"))
                .build();
        return vo;
    }
    public void deleteOne(Long tno) throws Exception{
        String sql = "delete from tbl_todo where tno = ?";

        @Cleanup Connection connection = ConnectionUtil.INSTANCE.getConnection();
        @Cleanup PreparedStatement preparedStatement = connection.prepareStatement(sql);

        preparedStatement.setLong(1,tno);
        preparedStatement.executeUpdate(); //삭제니까 테이블이 변경되므로 update
    }

    public void updateOne(TodoVO todoVO)throws Exception{ //vo가 아닌 todoVO
        String sql = "update tbl_todo set title = ?, dueDate = ?, finished = ? where tno = ?";

        @Cleanup Connection connection = ConnectionUtil.INSTANCE.getConnection();
        @Cleanup PreparedStatement preparedStatement = connection.prepareStatement(sql);

        preparedStatement.setString(1,todoVO.getTitle());
        preparedStatement.setDate(2,Date.valueOf(todoVO.getDueDate()));
        preparedStatement.setBoolean(3, todoVO.isFinished());
        preparedStatement.setLong(4, todoVO.getTno());

        preparedStatement.executeUpdate();
    }

}
