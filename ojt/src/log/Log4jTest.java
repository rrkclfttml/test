package log;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

 
public class Log4jTest {
    Connection con=null;
    
    public Log4jTest(){ }
    
    public Connection oracleConnect(){
        //데이터베이스 접속 연결 정보를 가지고 있는 객체입니다.
        //Oracle 접속 정보
        String driver="oracle.jdbc.driver.OracleDriver";
        String conStr="jdbc:oracle:thin:@127.0.0.1:1521:XE";
        String userid="soldesk";
        String password="1234";
        
        try{
            //JDBC드라이버를 로딩합니다.
            Class.forName(driver);
            
            //데이터베이스에 연결을 합니다.               Oracle Server IP:Port:SID     계정명         패스워드                      
            con=DriverManager.getConnection(conStr,userid,password);
            
            System.out.println("Oracle ora10g2에 접속 했습니다.");
            
            // 로그이름: package 이름 명시
            // 로그레벨: fatal, error, warning, info, debug
            // 클래스.메소드명
            // 메시지
            // exception 객체
            LogWriter.LogWrite("test", "info", "Log4jTest.oracleConnect()", userid + " 계정 데이터베이스 접속 성공", null);
        }catch(SQLException  e){
            System.out.println("SQLException:"+e);
            LogWriter.LogWrite("test", "error", "Log4jTest.oracleConnect()", userid + " Oracle 접속 에러 발생", e);
        }catch(Exception e){
            System.out.println("Exception:"+e.toString());
            LogWriter.LogWrite("test", "fatal", "Log4jTest.oracleConnect()", userid + " JDBC Driver 로딩 실패, 기타 에러 발생", e);            
        }finally{
        }
        
        return con;
        
    }
    
    public static void main(String args[]){
        Log4jTest dt = new Log4jTest();
        Connection con1 = null;
 
        try{
            con1 = dt.oracleConnect();
        }catch(Exception e){
            System.out.println(e.toString());
        }finally{
            try{
                if ( con1 != null) con1.close();
            }catch(Exception e){
                System.out.println(e.toString());
            }
        }
    }
}