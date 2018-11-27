package secure;
 
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
 
public class Cmd {
  public static void main(String args[]) {
    String s;
 
    try {
      /*
       * 자바 1.4 이하 Runtime oRuntime = Runtime.getRuntime(); Process oProcess =
       * oRuntime.exec("cmd /c dir /?");
       */
 
      // 자바 1.5 이상, "/c"는 명령어 실행 후 Console 종료
      // Process oProcess = new ProcessBuilder("cmd", "/c", "dir").start();
      // Process oProcess = new ProcessBuilder("cmd", "/c", "dir", "/?").start();
      String dir = args[0];
      StringBuffer cmd = new StringBuffer("");
      cmd.append(dir);
      Process oProcess = new ProcessBuilder("cmd", "/c",cmd.toString()).start();
      //cmd 실행, /c : 명령어 콘솔 외에 닫음
      //cmd.toString()).start() : 객체 생성
 
      // 외부 프로그램 출력 읽기
      BufferedReader stdOut = new BufferedReader(new InputStreamReader(oProcess.getInputStream()));
      BufferedReader stdError = new BufferedReader(new InputStreamReader(oProcess.getErrorStream()));
 
      // "표준 출력"과 "표준 에러 출력"을 출력
      while ((s = stdOut.readLine()) != null)
        System.out.println(s);
      while ((s = stdError.readLine()) != null)
        System.err.println(s);
 
      // 외부 프로그램 반환값 출력
      System.out.println("Exit Code: " + oProcess.exitValue());
      System.exit(oProcess.exitValue()); // 외부 프로그램의 반환값을, 이 자바 프로그램 자체의 반환값으로
                                         // 삼기
 
    } catch (IOException e) { // 에러 처리
      System.err.println("에러! 외부 명령 실행에 실패했습니다.\n" + e.getMessage());
      System.exit(-1);
    }
  }
}