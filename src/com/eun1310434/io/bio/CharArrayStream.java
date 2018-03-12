/*=====================================================================
□ Infomation
    ○ Data : 02.03.2018
    ○ Mail : eun1310434@naver.com
    ○ Blog : https://blog.naver.com/eun1310434
    ○ Reference : 쉽게 배우는 소프트웨어 공학, Java Documentation, 헬로 자바 프로그래밍


□ Function
  ○ 문자열 입출력
    - 클래스 이름이 Reader나 Writer로 끝남 → char단위 입출력 클래스

  ○ CharArrayStream을 활용한 파일 입출력 활용
    - CharArrayWriter, CharArrayReader
      
□ Study
  ○ IO
    - IO  ← Byte ← InputStream(:abstract class)
                 ← OutputStream(:abstract class)
          ← Char ← Reader(:abstract class)
                 ← Writer(:abstract class)
    - byte단위 입출력클래스 
      : InputStream, OutputStream 추상클래스 상속
    - char(문자)단위 입출력클래스
      : Reader, Writer라는 추상클래스 상속      
    - 4가지 추상클래스를 받아들이는 생성자 -> 다양한 입출력 방법을 제공하는 클래스
    - 4가지 추상클래스를 받아들이는 생성자 없음 -> 어디로부터 입력받을 것인지, 어디에 쓸것인지를 나타내는 클래스
    - FileIO(파일 입출력) 
      : FileInputStream, OutputStream, FileReader, FileWriter
    - ByteIO(배열 입출력)
      : ByteArrayInputStream, ByteArrayOutputStream, CharReader, CharWriter
    - 다양한 방식 입출력(FileIO,ByteIO를 감싸서 기능 효율적 활용)
      : DataInputStream, DatOutputStream, BufferedReader, PrintWriter
    - 자바IO는 Decorator Pattern을 활용하여 만듬
      : 하나의 클래스를 장식 하는 것처럼 생성자에서 감싸서 새로운 기능을 계속 추가 할 수 있도록 클래스를 만드는 방식
      
  ○ BIO 
    - Basic(Clocking) Input Output
    - 입출력시 멈춤, 다양한 입출력 필요시 대기가 발생.
    - 공유자원 접근 불가

  ○ 구성
    - Writer  ← BufferWriter
              ← CharArrayWriter
              ← FilterWriter
              ← OutputStreamWriter ← FileWriter
              ← PipedWriter
              ← PrintWriter
              ← StringWriter

    - Reader ← BufferReader  ← LineNumberReader
              ← CharArrayReader
              ← FilterReader ← PushbackReader
              ← InputStreamReader ← FileWriter
              ← PipedReader
              ← StringReader
=====================================================================*/
package com.eun1310434.io.bio;
import java.io.CharArrayReader;
import java.io.CharArrayWriter;
import java.io.IOException;
public class CharArrayStream {
	public static void main(String[] ar) throws IOException {

		//Data Out
		CharArrayWriter out = new CharArrayWriter();
		out.write("eun1310434\n"); //먼저 입력
		
		char[] ch = new char[]{'A', 'B', 'C', 'D', 'E', '\n'};
		out.write(ch); // 전체를 다출력
		out.write(ch, 0, 2); // 0 ~ 2까지만 출력
		out.close();
		
		char[] data = out.toCharArray(); // out의 데이터를 갖고 옮

		//Data In
		CharArrayReader in = new CharArrayReader(data);
		while(true) {
			int x = in.read();
			if(x == -1) break;
			System.out.print((char)x);
		}
		in.close();
	}
}