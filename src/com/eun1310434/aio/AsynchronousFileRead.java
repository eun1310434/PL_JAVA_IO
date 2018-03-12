/*=====================================================================
�� Infomation
  �� Data : 09.03.2018
  �� Mail : eun1310434@naver.com
  �� Blog : https://blog.naver.com/eun1310434
  �� Reference : ���� ���� ����Ʈ���� ����, Java Documentation, ��� �ڹ� ���α׷���, programmers.co.kr, 

�� Function
  �� 

�� Study
  �� IO
    - IO  �� Byte �� InputStream(:abstract class)
                 �� OutputStream(:abstract class)
          �� Char �� Reader(:abstract class)
                 �� Writer(:abstract class)
    - byte���� �����Ŭ���� 
      : InputStream, OutputStream �߻�Ŭ���� ���
    - char(����)���� �����Ŭ����
      : Reader, Writer��� �߻�Ŭ���� ���      
    - 4���� �߻�Ŭ������ �޾Ƶ��̴� ������ -> �پ��� ����� ����� �����ϴ� Ŭ����
    - 4���� �߻�Ŭ������ �޾Ƶ��̴� ������ ���� -> ���κ��� �Է¹��� ������, ��� ���������� ��Ÿ���� Ŭ����
    - FileIO(���� �����) 
      : FileInputStream, OutputStream, FileReader, FileWriter
    - ByteIO(�迭 �����)
      : ByteArrayInputStream, ByteArrayOutputStream, CharReader, CharWriter
    - �پ��� ��� �����(FileIO,ByteIO�� ���μ� ��� ȿ���� Ȱ��)
      : DataInputStream, DatOutputStream, BufferedReader, PrintWriter
    - �ڹ�IO�� Decorator Pattern�� Ȱ���Ͽ� ����
      : �ϳ��� Ŭ������ ��� �ϴ� ��ó�� �����ڿ��� ���μ� ���ο� ����� ��� �߰� �� �� �ֵ��� Ŭ������ ����� ���
      
  �� AIO 
    - AIO(Asynchronous Input Output)
    - ���� ������� �����Ͽ��� �ٸ� �۾��� ���������� �۾� �� �� ����
    - ������ �۾��� �ʿ���� ���� �� �� ����� ����
      * �� ������ ������ �ʿ��(������ �����ϰ� ���Ͽ� �����ؾ� �Ǵ°��)�� ��� �Ұ�.
    - Path Ŭ������ Ȱ���Ͽ� ��� ���� ������ ����.
    - java.nio.channels ��Ű���� Ŭ����
      01) AsynchronousFileChannel
      02) AsynchronousServerSocketChannel
      03) AsynchronousSocketChannel
    - java.nio.file ��Ű���� Ŭ����
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
		
		
		//���� �̸��� ���� ��� ����
		Path path = Paths.get(file.getPath());

        //// try-with-resource ��� try-catch-finally ����
		try {
			System.err.println("AsynchronousFileChannel �׽�Ʈ ����");	
			AsynchronousFileChannel inputChannel = AsynchronousFileChannel.open(
			path, 
			StandardOpenOption.READ
			);
	        
			long startTime = System.nanoTime();
			long fileSize = inputChannel.size();
	        System.err.println("startTime : " + startTime);
	        System.err.println("fileSize : "+ fileSize);

            // ByteBuffer ũ�⸦ 8k�� ���
			ByteBuffer byteData2 = 
					//ByteBuffer.allocate(8 * 1024);
					ByteBuffer.allocate(4*1000);
 
            // attachment �� ��ü
            class AsyncIOResultInfo {
                long iterations = 0L;
                long totalBytesRead = 0L;
            }
            AsyncIOResultInfo asyncIOResultInfo = new AsyncIOResultInfo();
	        
	        System.err.println("AsynchronousFileChannel.read() ȣ��");
	        
	        inputChannel.read(
	        		byteData2, 0, asyncIOResultInfo,    // null ��� iterations ����
	                new CompletionHandler<Integer,  AsyncIOResultInfo>() {// Ÿ�� �Ķ���Ϳ� Long ��� AsyncIOResultInfo ����
	     
	                    @Override
	                    public void completed(Integer result, AsyncIOResultInfo asyncIOResultInfo) {    // Ÿ�� �Ķ���Ϳ� Object ��� Long
	                        if (result == -1) {
	                            long endTime = System.nanoTime();
	                            System.err.println("������ ���� : " + (endTime - startTime) + " ns elapsed.");
	                            

                                // asyncFileChannel �ݱ�
                                closeAsyncFileChannel(inputChannel);
	                            return;
	                        }
	     
	                        // �ݺ� ȸ�� Ȯ��
	                        System.err.println((asyncIOResultInfo.iterations + 1) + "ȸ�� �ݺ�");

                            //�о���� ����Ʈ�� ����
                            asyncIOResultInfo.totalBytesRead += result;
	                        
	                        
	                        byteData2.flip();
	                        byteData2.mark();
	                        System.out.write(byteData2.array(), 0, result);
	                        //System.out.println("");
	                        byteData2.reset();
	     
	                        // �о���� ����Ʈ����
	                        // ���ϻ������ ���ų�(���� ũ��� ���� ũ�Ⱑ ���� ���)
	                        // ���� ������� �۴ٸ� ������ ������ ���� ���̹Ƿ� ���� ó��
	                        if (result == fileSize || result < byteData2.capacity()) {
	                            long endTime = System.nanoTime();
	                            System.err.println("AsynchronousFileChannel.read() �Ϸ� : " + (endTime - startTime) + " ns elapsed.");

                                ////// �� �о���� ����Ʈ�� ��
                                System.err.println("fileSize       : " + fileSize);
                                System.err.println("totalBytesRead : " + asyncIOResultInfo.totalBytesRead);
 
                                //// inputChannel �ݱ�
                                closeAsyncFileChannel(inputChannel);
	                            
	                            return;
	                        }
                            // ���� ������ ���������Ƿ� �ݺ� ȸ���� ���� ��Ű�� �ٽ� �д´�.
                            // iterations ��� asyncIOResultInfo.iterations
                            asyncIOResultInfo.iterations++;
                            inputChannel.read(byteData2, result * asyncIOResultInfo.iterations, asyncIOResultInfo, this);
	                    }
	     
	                    @Override
	                    public void failed(Throwable exc, AsyncIOResultInfo iterations) {    // Ÿ�� �Ķ���Ϳ� Object ��� Long
	                        exc.printStackTrace();
 
                            //// asyncFileChannel �ݱ�
                            closeAsyncFileChannel(inputChannel);
	                    }
	                });
	        
	        System.err.println("AsyncFileChannel I/O ���� �߿��� �ٸ� �۾��� �� �� ������");
	        System.err.println("�׵��� �׸������� �ٳ����");
	        System.err.println("ũ�ξ�Ƽ�ƿ��� ���ٿ���");

	        
	    } catch (Exception e) {
	        e.printStackTrace();
	        throw new RuntimeException(e);    //// ��Ȳ�� �´� ���� ó�� �ʿ�
	    }
	}

    // asyncFileChannel �ݱ�
    private static void closeAsyncFileChannel(AsynchronousFileChannel asyncFileChannel) {
 
        if (asyncFileChannel != null && asyncFileChannel.isOpen()) {
 
            try {
                asyncFileChannel.close();
            } catch (IOException e) {
                e.printStackTrace();
                throw new RuntimeException(e);    //// ��Ȳ�� �´� ���� ó�� �ʿ�
            }
        }
    }
}
