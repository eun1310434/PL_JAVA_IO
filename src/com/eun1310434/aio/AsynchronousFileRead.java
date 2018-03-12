/*=====================================================================
□ Infomation
  ○ Data : 09.03.2018
  ○ Mail : eun1310434@naver.com
  ○ Blog : https://blog.naver.com/eun1310434
  ○ Reference : 쉽게 배우는 소프트웨어 공학, Java Documentation, 헬로 자바 프로그래밍, programmers.co.kr, 

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
package com.eun1310434.aio;
import java.io.File;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousFileChannel;
import java.nio.channels.CompletionHandler;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

public class AsynchronousFileRead {
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

        //// try-with-resource 대신 try-catch-finally 적용
		try {
			System.err.println("AsynchronousFileChannel 테스트 시작");	
			AsynchronousFileChannel inputChannel = AsynchronousFileChannel.open(
			path, 
			StandardOpenOption.READ
			);
	        
			long startTime = System.nanoTime();
			long fileSize = inputChannel.size();
	        System.err.println("startTime : " + startTime);
	        System.err.println("fileSize : "+ fileSize);

            // ByteBuffer 크기를 8k로 축소
			ByteBuffer byteData2 = 
					//ByteBuffer.allocate(8 * 1024);
					ByteBuffer.allocate(4*1000);
 
            // attachment 용 객체
            class AsyncIOResultInfo {
                long iterations = 0L;
                long totalBytesRead = 0L;
            }
            AsyncIOResultInfo asyncIOResultInfo = new AsyncIOResultInfo();
	        
	        System.err.println("AsynchronousFileChannel.read() 호출");
	        
	        inputChannel.read(
	        		byteData2, 0, asyncIOResultInfo,    // null 대신 iterations 전달
	                new CompletionHandler<Integer,  AsyncIOResultInfo>() {// 타입 파라미터에 Long 대신 AsyncIOResultInfo 전달
	     
	                    @Override
	                    public void completed(Integer result, AsyncIOResultInfo asyncIOResultInfo) {    // 타입 파라미터에 Object 대신 Long
	                        if (result == -1) {
	                            long endTime = System.nanoTime();
	                            System.err.println("비정상 종료 : " + (endTime - startTime) + " ns elapsed.");
	                            

                                // asyncFileChannel 닫기
                                closeAsyncFileChannel(inputChannel);
	                            return;
	                        }
	     
	                        // 반복 회수 확인
	                        System.err.println((asyncIOResultInfo.iterations + 1) + "회차 반복");

                            //읽어들인 바이트수 누적
                            asyncIOResultInfo.totalBytesRead += result;
	                        
	                        
	                        byteData2.flip();
	                        byteData2.mark();
	                        System.out.write(byteData2.array(), 0, result);
	                        //System.out.println("");
	                        byteData2.reset();
	     
	                        // 읽어들인 바이트수가
	                        // 파일사이즈와 같거나(버퍼 크기와 파일 크기가 같은 경우)
	                        // 버퍼 사이즈보다 작다면 파일의 끝까지 읽은 것이므로 종료 처리
	                        if (result == fileSize || result < byteData2.capacity()) {
	                            long endTime = System.nanoTime();
	                            System.err.println("AsynchronousFileChannel.read() 완료 : " + (endTime - startTime) + " ns elapsed.");

                                ////// 총 읽어들인 바이트수 비교
                                System.err.println("fileSize       : " + fileSize);
                                System.err.println("totalBytesRead : " + asyncIOResultInfo.totalBytesRead);
 
                                //// inputChannel 닫기
                                closeAsyncFileChannel(inputChannel);
	                            
	                            return;
	                        }
                            // 읽을 내용이 남아있으므로 반복 회수를 증가 시키고 다시 읽는다.
                            // iterations 대신 asyncIOResultInfo.iterations
                            asyncIOResultInfo.iterations++;
                            inputChannel.read(byteData2, result * asyncIOResultInfo.iterations, asyncIOResultInfo, this);
	                    }
	     
	                    @Override
	                    public void failed(Throwable exc, AsyncIOResultInfo iterations) {    // 타입 파라미터에 Object 대신 Long
	                        exc.printStackTrace();
 
                            //// asyncFileChannel 닫기
                            closeAsyncFileChannel(inputChannel);
	                    }
	                });
	        
	        System.err.println("AsyncFileChannel I/O 진행 중에는 다른 작업도 할 수 있지롱");
	        System.err.println("그동안 그리스에도 다녀오고");
	        System.err.println("크로아티아에도 갔다오자");

	        
	    } catch (Exception e) {
	        e.printStackTrace();
	        throw new RuntimeException(e);    //// 상황에 맞는 예외 처리 필요
	    }
	}

    // asyncFileChannel 닫기
    private static void closeAsyncFileChannel(AsynchronousFileChannel asyncFileChannel) {
 
        if (asyncFileChannel != null && asyncFileChannel.isOpen()) {
 
            try {
                asyncFileChannel.close();
            } catch (IOException e) {
                e.printStackTrace();
                throw new RuntimeException(e);    //// 상황에 맞는 예외 처리 필요
            }
        }
    }
}
