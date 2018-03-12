/*=====================================================================
□ Infomation
  ○ Data : 08.03.2018
  ○ Mail : eun1310434@naver.com
  ○ Blog : https://blog.naver.com/eun1310434
  ○ Reference : 쉽게 배우는 소프트웨어 공학, Java Documentation, 헬로 자바 프로그래밍, programmers.co.kr

□ Function
  ○ 오브젝트 스트림을 활용한 파일 입출력
  ○ 오브젝트 파일의 내용을 리스트로 갖고오기
    - list = (ArrayList<ObjectStreamAPI_Info>) in.readObject();

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
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

class ObjectStreamAPI_Info implements Serializable {
	private static final long serialVersionUID = 1L;
	private String _string;
	private int _int;
	private double _double;
	private float _float;
	
	public ObjectStreamAPI_Info(String _string, int _int, double _double, float _float) {
		this._string = _string;
		this._int = _int;
		this._double = _double;
		this._float = _float;
	}
	
	public void display() {
		System.out.println("___________________________________");
		System.out.println("String : "+_string);
		System.out.println("Integer : "+_int);
		System.out.println("Double : "+_double);
		System.out.println("Float : "+_float);
	}
}


@SuppressWarnings("unchecked")
public class ObjectStreamAPI {
	
	private static Scanner cin = new Scanner(System.in);
	private static File dataFile;
	private static List<ObjectStreamAPI_Info> list = new ArrayList<>();
	
	static {
		//클래스가 생성됨과 동시에 실행
		//Folder Setting
		String dir_parent = "D:\\PJM\\ECLIPSE\\Examples\\PL_JAVA_IO\\data";
		File file_parnet = new File(dir_parent);
		if(!file_parnet.exists()){
			file_parnet.mkdir();
		}

		//Data File Setting
		dataFile = new File(dir_parent,"DATA_ObjectStreamAPI.dat" );
		if(dataFile.exists()){
			try {
				ObjectInputStream in = new ObjectInputStream(
					new BufferedInputStream(
							new FileInputStream(dataFile), 512));
				
				//오브젝트 파일의 내용을 리스트로 갖고오기
				list = (ArrayList<ObjectStreamAPI_Info>) in.readObject();
				
				in.close();
			}catch(Exception ex) {
				System.err.println("IOException : " + ex.getMessage().toString());
				System.exit(-1);
			}
		}
	}
	
	public static void inputData() {
		System.out.print("String = ");
		String _string = cin.next();
		
		System.out.print("Integer = ");
		int _integer = cin.nextInt();
		
		System.out.print("Double = ");
		double _double = cin.nextDouble();
		
		System.out.print("Float = ");
		float _float = cin.nextFloat();
		
		ObjectStreamAPI_Info info = new ObjectStreamAPI_Info(_string, _integer, _double, _float);
		list.add(info);
	}
	
	public static void outputData() {
		for(ObjectStreamAPI_Info info: list) {
			info.display();
			System.out.println("");
		}
	}	
	
	public static void saveData() {
		try {
			ObjectOutputStream out = new ObjectOutputStream(new BufferedOutputStream(new FileOutputStream(dataFile), 512));
			out.writeObject(list);
			out.close();
			
		}catch(Exception ex) {
			System.err.println("SAVE ERROR" + ex.getMessage());
			System.exit(-1);
		}
		System.out.printf("Total Info Count : %d \n", list.size());
	}
	
	
	public static void main(String[] ar) {
		int command = 0;
		up: while(true) {
			System.out.println("1.Insert");
			System.out.println("2.Print");
			System.out.print("3.Finish(save)");
			
			command = cin.nextInt();
			
			switch(command) {
				case 1 : inputData(); break;
				case 2 : outputData(); break;
				case 3 : saveData(); break up;
				default: System.out.println("wrong insert");
			}
			System.out.println("");
			System.out.println("");
			System.out.println("");
		}
		cin.close();
	}
}