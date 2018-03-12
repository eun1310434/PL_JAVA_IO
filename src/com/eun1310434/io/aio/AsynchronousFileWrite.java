/*=====================================================================
□ Infomation
  ○ Data : 09.03.2018
  ○ Mail : eun1310434@naver.com
  ○ Blog : https://blog.naver.com/eun1310434
  ○ Reference : 쉽게 배우는 소프트웨어 공학, Java Documentation, 헬로 자바 프로그래밍, programmers.co.kr

□ Function
  ○ 

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
      
  ○ AIO 
    - AIO(Asynchronous Input Output)
    - 파일 입출력을 실행하여도 다른 작업을 지속적으로 작업 할 수 있음
    - 순차적 작업이 필요없는 일을 할 때 사용이 가능
      * 단 순차적 적업이 필요시(파일을 생성하고 파일에 접근해야 되는경우)에 사용 불가.
    - Path 클래스를 활용하여 경로 설정 관리가 편함.
    - java.nio.channels 패키지의 클래스
      01) AsynchronousFileChannel
      02) AsynchronousServerSocketChannel
      03) AsynchronousSocketChannel
    - java.nio.file 패키지의 클래스
      : Files, Paths
=====================================================================*/
package com.eun1310434.io.aio;
import java.io.File;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousFileChannel;
import java.nio.channels.CompletionHandler;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

public class AsynchronousFileWrite {
	@SuppressWarnings({ "rawtypes"})
	public static void main(String[] ar) throws Exception {

		//Folder Setting
		String dir_parent = "D:\\PJM\\ECLIPSE\\Examples\\PL_JAVA_IO\\data\\";
		File file_parnet = new File(dir_parent);
		if(!file_parnet.exists()){
			file_parnet.mkdir();
		}
		

		//Data File Setting
		String dir = dir_parent + "DATA_AsynchronousFile.dat";
		File file = new File(dir);
		if(!file.exists()){file.createNewFile();}
		
		
		//파일 이름을 통한 경로 관리
		Path path = Paths.get(file.getPath());
		

		//data 생성
		String str ="";
		for(int i=0; i < 1000; i++){
			str = str + "AAA\n";
		}

		byte[] data = str.getBytes();
		
		Write(path, data,
				// CompletionHandler : 비동기로 실행하기에 언제 끝나는지 확인이 불가능 하여 사용
				new CompletionHandler(){
					@Override
					public void completed(Object result, Object attachment) {
						// 파일 출력 완료
						System.out.println(attachment + " completed and " + result + " bytes are written.");
					}

					@Override
					public void failed(Throwable exc, Object attachment) {
						// 파일 출력 실패
						System.out.println(attachment + " failed with exception : ");
						exc.printStackTrace();
					}
				}
		);
	}
	
	
	//CompletionHandler를 위한 annotation
	@SuppressWarnings({ "rawtypes", "unchecked" }) 
	public static void Write(Path _path, byte[] data, CompletionHandler handler) throws Exception{
		
		//ByteBuffer설정
		ByteBuffer byteData = ByteBuffer.wrap(data);//작성할때는 사실 파일의 크기를 알기에 wrap사용

		//ByteBuffer byteData = ByteBuffer.allocateDirect(512);//시스템메모리 할당, 작성할때는 사실 파일의 크기를 알기에 wrap사용
		//ByteBuffer byteData = ByteBuffer.allocate(512);//힙메모리(→시스템메모리) 할당, 작성할때는 사실 파일의 크기를 알기에 wrap사용
		//byteData.put(data);//메모리 입력
		//byteData.flip();//포지션을 제일 처음으로 위치
		

		//Channel 생성 - 1.7버전 후 : FileOutputStream없이 직접 할당 가능
		AsynchronousFileChannel outputChannel = 
				AsynchronousFileChannel.open(_path, StandardOpenOption.WRITE);
		outputChannel.write(byteData, 0, "MyData", handler);
		outputChannel.close();
	}
}
