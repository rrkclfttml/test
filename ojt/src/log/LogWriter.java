package log;
 
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
 
public class LogWriter {
    
    private static int TEST_MODE = 1; // 0: 배포, 1: WTP 테스트
    
    public LogWriter(){
     
    }
 
    /**
     * 로그 기록을 전담하는 메소드입니다.
     * @param logName 로그이름
     * @param level 로그 기록 레벨
     * @param class_methodname 클래스, 메소드명
     * @param msg 기록할 메시지
     */
    public static void LogWrite(String logName, String level, String class_methodname, String msg, Throwable t){
        String str="";
        
        if (TEST_MODE == 0){ // 배포
            str = "./WEB-INF/log.properties";
        }
 
        if (TEST_MODE == 1){ // WTP 테스트
            str = "./WebContent/WEB-INF/log.properties";
        }
 
        System.out.print(str);
        
        // LOG 속성 파일을 읽어옴
        PropertyConfigurator.configure(str);
 
        //Logger rootLogger = Logger.getRootLogger();
        
        //로그이름 지정(아무 이름이나 가능, 일반적으로 최상위 패키지명)
        Logger www = Logger.getLogger(logName);        
 
        //Logger Level 지정, INFO를 포함한 상위 Level만 저장 
        www.setLevel(Level.DEBUG);
 
        //기록할 메시지 조합
        msg = logName + "/" + class_methodname + "/" + msg;
 
        if ( level.toUpperCase().equals("FATAL")){
            if (TEST_MODE == 0){ // 배포
                www.fatal(msg, t);
            }else{
                www.fatal(msg);
            }
        }
     
        if ( level.toUpperCase().equals("ERROR")){
            if (TEST_MODE == 0){ // 배포
                www.error(msg, t);
            }else{
                www.error(msg);
            }
        }
 
        if ( level.toUpperCase().equals("WARN")){
            if (TEST_MODE == 0){ // 배포
                www.warn(msg, t);
            }else{
                www.warn(msg);
            }
        }
 
        if ( level.toUpperCase().equals("INFO")){
            www.info(msg);
        }
 
        if ( level.toUpperCase().equals("DEBUG")){
            www.debug(msg, t);     
        }
        
    }
}