/*=====================================================================
□ Infomation
  ○ Data : 08.03.2018
  ○ Mail : eun1310434@naver.com
  ○ Blog : https://blog.naver.com/eun1310434
  ○ Reference : 쉽게 배우는 소프트웨어 공학, Java Documentation, 헬로 자바 프로그래밍, programmers.co.kr

□ Function
  ○ 파일 입출력의 기본
  ○ 파일 루트확인
  ○ 다양한 디렉토리 표현
  ○ 해당경로의 확장 파일 리스트 갖고오기
  ○ 폴더만들기
  ○ 파일만들기 

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
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
public class FileAPI {
	
	public static void FileRoot(){
		//파일 루트확인
		System.out.println("File Roots list--------------------");
		File[] roots = File.listRoots();
		for(File root: roots) {
			System.out.print(root + "\t");
		}
	}
	
	public static void getFileListRoot(String dir, String extention){
		//해당경로의 확장 파일 리스트 갖고오기
		File file = new File(dir);
		
		//확장자가 java로 끝나는 파일 리스트 갖고 오기
		File[] list = file.listFiles((directory, name) -> directory.exists() && name.endsWith(extention));
		
		
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		for(int n = 0; n < list.length; ++n) {
			System.out.println(n + "-------------------------");
			System.out.println(list[n].getName());
			System.out.println("Date : " + format.format(new Date(list[n].lastModified())));
			System.out.println("Exec : " + list[n].canExecute());
			System.out.println("Read : " + list[n].canRead());
			System.out.println("Writ : " + list[n].canWrite());
			System.out.println();
		}
		
	}

	public static void makeFolderSetting(String dir){
		//폴더 만들기
		File file_parnet = new File(dir);
		if(!file_parnet.exists()){
			System.out.println("Make folder");
			file_parnet.mkdir();
		}else{
			System.out.println("Areadly folder");
		}
	}
	
	public static void makeFileSetting(String dir){
		//폴더 만들기
		File file = new File(dir);
		if(!file.exists()){
			try {
				System.out.println("Make file");
				file.createNewFile();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}else{
			System.out.println("Areadly file");
		}
	}

	
	public static void main(String[] ar) {

		//파일 루트확인
		FileRoot();
		System.out.println();
		System.out.println();

		//다양한 디렉토리 표현
		String dir = "";
		dir = "D:\\PJM\\ECLIPSE\\Examples\\PL_JAVA_IO";
		dir = "D:/PJM/ECLIPSE/Examples/PL_JAVA_IO";
		dir = "D:" + File.separator + "PJM" + File.separator + "ECLIPSE" + File.separator + "Examples" + File.separator + "PL_JAVA_IO";

		//해당경로의 확장 파일 리스트 갖고오기
		getFileListRoot(dir + "\\src\\com\\eun1310434\\bio", ".java");

		//폴더만들기
		String dir_parent = "D:\\PJM\\ECLIPSE\\Examples\\PL_JAVA_IO\\data\\";
		makeFolderSetting(dir_parent);


		//파일만들기
		String dir_make = dir_parent + "DATA_FileAPI.txt";
		makeFileSetting(dir_make);
	}
}