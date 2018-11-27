package secure;
 
import java.io.UnsupportedEncodingException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
 
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
 
import org.apache.commons.codec.binary.Base64;
 
public class AES256Util {
  private String iv;
  private Key keySpec;
 
  // 키 생성
  public AES256Util() throws UnsupportedEncodingException {
    String key = "soldeskAES256KEY"; // key는 16자 이상
    this.iv = key.substring(0, 16);// key로 사용할 16자를  iv에 넣음
 
    byte[] keyBytes = new byte[16];
    byte[] b = key.getBytes("UTF-8");
    int len = b.length;
    if (len > keyBytes.length)
      len = keyBytes.length;
    System.arraycopy(b, 0, keyBytes, 0, len);//arraycopy를 이용해서 b에 들어간 배열을 keyBytes에 넣은다.
    SecretKeySpec keySpec = new SecretKeySpec(keyBytes, "AES");
 
    this.keySpec = keySpec;
  }
 
  // 암호화
  public String aesEncode(String str)
      throws java.io.UnsupportedEncodingException, NoSuchAlgorithmException, NoSuchPaddingException,
      InvalidKeyException, InvalidAlgorithmParameterException, IllegalBlockSizeException, BadPaddingException {
   
	//본격 암호화 과정 (Cipher : 암호화 객체) 
	Cipher c = Cipher.getInstance("AES/CBC/PKCS5Padding");
    c.init(Cipher.ENCRYPT_MODE, keySpec, new IvParameterSpec(iv.getBytes()));
    //Cipher.ENCRYPT_MODE : 암호화모드
    //keySpec : 위에서 설정한 key값
 
    byte[] encrypted = c.doFinal(str.getBytes("UTF-8"));//배열 값으로 리턴
    String enStr = new String(Base64.encodeBase64(encrypted));//배열값으로 리턴된 값을 문자열로 인코딩해서 저장
    //db에는 배열 값을 저장 할 수 없다. 암호화 하면 배열로 저장이 되기 때문에 인코등일 통해 값에는 변화 없이 문자로 저장이 된다.
    
    return enStr;
  }
  
 
  // 복호화
  public String aesDecode(String str) //str : db에 저장되어 있는 값(암호화되어진 상태)
      throws java.io.UnsupportedEncodingException, NoSuchAlgorithmException, NoSuchPaddingException,
      InvalidKeyException, InvalidAlgorithmParameterException, IllegalBlockSizeException, BadPaddingException {

	  
	Cipher c = Cipher.getInstance("AES/CBC/PKCS5Padding");
    c.init(Cipher.DECRYPT_MODE, keySpec, new IvParameterSpec(iv.getBytes("UTF-8")));
    //Cipher.DECRYPT_MODE: 복호화 모드
 
    byte[] byteStr = Base64.decodeBase64(str.getBytes());
    //encoding 되어 있는 배열 값을 풀어준다.
 
    return new String(c.doFinal(byteStr), "UTF-8");
    //c.doFinal(byteStr) :원래 값으로 복호화 한다.
  }
 
  
  
  
  /*예제*/
  public static void main(String[] args) throws Exception {
    AES256Util aes256 = new AES256Util();
 
    String text = "가나다 123 ABC !@#";
    String encText = aes256.aesEncode(text);//인코딩 -배열을 인코딩을 통해 문자로 바꿨음
    String decText = aes256.aesDecode(encText);//decode 하고 복호화 진행
 
    System.out.println("암호화할 문자 : " + text);
    System.out.println("암호화된 문자(DBMS 저장) : " + encText);
    System.out.println("복호화된 문자 : " + decText);
  }
 
}