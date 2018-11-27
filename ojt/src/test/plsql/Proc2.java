package test.plsql;
 
import java.sql.*;
import java.io.*;
 
public class Proc2 {
 
    public Proc2(){ }
    
    public static void main(String[] args) {
        try{
            String driver="oracle.jdbc.driver.OracleDriver";
            String url="jdbc:oracle:thin:@127.0.0.1:1521:XE";
            String id = "soldesk";
            String password = "1234";
            Class.forName(driver);
            Connection conn = DriverManager.getConnection(url, id, password);
 
/*
CREATE or REPLACE PROCEDURE proc2(
    i_deptno IN number, -- 부서 번호
    o_count OUT number  -- 직원 수
)
IS
BEGIN
    SELECT count(*) INTO o_count FROM EMP
    WHERE deptno = i_deptno;
END;
/
 
*/
            CallableStatement cstmt = conn.prepareCall("{call proc2(?,?)}");
            
            cstmt.setInt(1, 10);
            // output 파라미터의 지정
            cstmt.registerOutParameter(2, java.sql.Types.INTEGER);
            cstmt.executeUpdate();
            
            // Output 파라미터를 가져오는 부분, output 파라미터는 2번째 
            // 임으로 getInt(2)를 지정 합니다.
            System.out.println("10번 부서의 직원수: " + cstmt.getInt(2));
        }catch(Exception e){
            System.out.println(e.toString());
        }
        System.out.println("정상적으로 실행되었습니다.");
    }
}