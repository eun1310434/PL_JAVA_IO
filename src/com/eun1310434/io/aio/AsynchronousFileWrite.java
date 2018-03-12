/*=====================================================================
�� Infomation
  �� Data : 09.03.2018
  �� Mail : eun1310434@naver.com
  �� Blog : https://blog.naver.com/eun1310434
  �� Reference : ���� ���� ����Ʈ���� ����, Java Documentation, ��� �ڹ� ���α׷���, programmers.co.kr

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
		
		
		//���� �̸��� ���� ��� ����
		Path path = Paths.get(file.getPath());
		

		//data ����
		String str ="";
		for(int i=0; i < 1000; i++){
			str = str + "AAA\n";
		}

		byte[] data = str.getBytes();
		
		Write(path, data,
				// CompletionHandler : �񵿱�� �����ϱ⿡ ���� �������� Ȯ���� �Ұ��� �Ͽ� ���
				new CompletionHandler(){
					@Override
					public void completed(Object result, Object attachment) {
						// ���� ��� �Ϸ�
						System.out.println(attachment + " completed and " + result + " bytes are written.");
					}

					@Override
					public void failed(Throwable exc, Object attachment) {
						// ���� ��� ����
						System.out.println(attachment + " failed with exception : ");
						exc.printStackTrace();
					}
				}
		);
	}
	
	
	//CompletionHandler�� ���� annotation
	@SuppressWarnings({ "rawtypes", "unchecked" }) 
	public static void Write(Path _path, byte[] data, CompletionHandler handler) throws Exception{
		
		//ByteBuffer����
		ByteBuffer byteData = ByteBuffer.wrap(data);//�ۼ��Ҷ��� ��� ������ ũ�⸦ �˱⿡ wrap���

		//ByteBuffer byteData = ByteBuffer.allocateDirect(512);//�ý��۸޸� �Ҵ�, �ۼ��Ҷ��� ��� ������ ũ�⸦ �˱⿡ wrap���
		//ByteBuffer byteData = ByteBuffer.allocate(512);//���޸�(��ý��۸޸�) �Ҵ�, �ۼ��Ҷ��� ��� ������ ũ�⸦ �˱⿡ wrap���
		//byteData.put(data);//�޸� �Է�
		//byteData.flip();//�������� ���� ó������ ��ġ
		

		//Channel ���� - 1.7���� �� : FileOutputStream���� ���� �Ҵ� ����
		AsynchronousFileChannel outputChannel = 
				AsynchronousFileChannel.open(_path, StandardOpenOption.WRITE);
		outputChannel.write(byteData, 0, "MyData", handler);
		outputChannel.close();
	}
}
