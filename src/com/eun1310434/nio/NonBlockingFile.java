/*=====================================================================
�� Infomation
  �� Data : 09.03.2018
  �� Mail : eun1310434@naver.com
  �� Blog : https://blog.naver.com/eun1310434
  �� Reference : ���� ���� ����Ʈ���� ����, Java Documentation, ��� �ڹ� ���α׷���, programmers.co.kr

�� Function
  �� FileChannel�� Ȱ���� non blacking io ���

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
      
  �� NIO 
    - BIO(���ŷ, ����Ʈ ��Ʈ�� ���) �� NIO(�ͺ��ŷ, ���۸� ����ϴ� ä�� ���)
    - BIO(�������� �޸𸮸� ��� �ý��� �޸� ����) �� NIO(ByteBuffer�� ��� �ٷ� �ý��ۿ� �����Ͽ� �޸𸮸� �Ҵ�)
    - �����ڿ� ���� ����, ����ӵ��� �ſ� ����, ���ϵ� ����
    - Blocking? : ��� ���α׷����� ������ ����ϰ� ������, �ٸ� ����ڴ� �ش������� �������� ���� �ǹ�
    
  �� ����
    - Buffer �� ByteBuffer �� MappedByteBuffer
             �� CharBuffer
             �� SherBuffer
             �� intBuffer
             �� LongBuffer
             �� DoubleBuffer
=====================================================================*/
package com.eun1310434.nio;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

public class NonBlockingFile {
	public static void main(String[] ar) throws Exception {
		
		//Folder Setting
		String dir_parent = "D:\\PJM\\ECLIPSE\\Examples\\PL_JAVA_IO\\data\\";
		File file_parnet = new File(dir_parent);
		if(!file_parnet.exists()){
			file_parnet.mkdir();
		}
		

		//Data File Setting
		String dir = dir_parent + "DATA_NonBlockingFile.txt";
		File file = new File(dir);
		if(!file.exists()){
			file.createNewFile();
		}
		

		//Data Out
		byte[] data = "eun1310434@naver.com\n".getBytes();

		//ByteBuffer����
		//ByteBuffer out_byteData = ByteBuffer.wrap(data);//�ۼ��Ҷ��� ��� ������ ũ�⸦ �˱⿡ wrap���
		//ByteBuffer out_byteData = ByteBuffer.allocate(512);//�޸� �Ҵ�
		ByteBuffer out_byteData = ByteBuffer.allocateDirect(512);//�޸� �Ҵ�
		out_byteData.put(data);//�޸� �Է�
		out_byteData.flip();//�������� ���� ó������ ��ġ
		
		//ByteBuffer out_byteData = ByteBuffer.wrap(data);//�޸� �Է�
		
		FileOutputStream out = new FileOutputStream(file);//������� ����
		
		//Channel ���� - 1.7���� ��
		FileChannel out_channel = out.getChannel();//ü���� �����... �����ڿ� ����
		
		//Channel ���� - 1.7���� �� : FileOutputStream���� ���� �Ҵ� ����
		//FileChannel out_channel = FileChannel.open(Paths.get(file.getPath()), StandardOpenOption.WRITE);
		
		out_channel.write(out_byteData);//ä�ΰ� �޸� ����
		out.close();

		
		//Data In
		//ByteBuffer in_byteData = ByteBuffer.allocate(512);//�޸� �Ҵ�
		ByteBuffer in_byteData = ByteBuffer.allocateDirect(512);//�޸� �Ҵ�
		
		FileInputStream in = new FileInputStream(file);
		
		FileChannel in_channel = in.getChannel();//ä���� �����... �����ڿ� ����
		
		int result = in_channel.read(in_byteData);//ä�ΰ� �޸� �����Ͽ� �Ҵ� �޸� ��ŭ �о��
		
		while(result != -1){
			in_byteData.flip();//�������� ���� ó������ ��ġ
			
			while(in_byteData.hasRemaining()){
				System.out.print((char)in_byteData.get()); //buffer�� ũ�⸸ŭ ����� �ֱ⿡ ���ڰ� �ȳ���
			}
			in_byteData.clear();//�����ϰ� ����
			
			result = in_channel.read(in_byteData);//ä�ΰ� �޸� �����Ͽ� �Ҵ� �޸� ��ŭ �о��
		}
		in.close();
	}
}
