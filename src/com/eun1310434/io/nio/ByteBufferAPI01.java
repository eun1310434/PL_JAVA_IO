/*=====================================================================
�� Infomation
  �� Data : 09.03.2018
  �� Mail : eun1310434@naver.com
  �� Blog : https://blog.naver.com/eun1310434
  �� Reference : ���� ���� ����Ʈ���� ����, Java Documentation, ��� �ڹ� ���α׷���, programmers.co.kr

�� Function
  �� ByteBuffer�� �ý��� �޸� ���� ����Ȯ��
    - ByteBuffer.allocateDirect(128);
  �� ByteBuffer�� �� �޸𸮸� ���� �ý��� �޸� ����Ȯ��
    - ByteBuffer.allocate(128);

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
package com.eun1310434.io.nio;
import java.nio.ByteBuffer;

public class ByteBufferAPI01 {
	public static void main(String[] ar) {
		byte[] data = new byte[]{12, 12, 12, 12, 12, 12};//6
		System.out.println("data - size : " + data.length);
		
		ByteBuffer direct = ByteBuffer.allocateDirect(128); // ���� ���� ó���� �����ϳ� ���ϰ� ����
		direct.put(data);
		
		ByteBuffer nonDirect = ByteBuffer.allocate(128); // �Ϲ����� ���
		nonDirect.put(data);
		
		//Direct
		//�ý��� �޸� ���� ���� �� ���� + ���ϳ���
		System.out.println("Direct - isDirect : " + direct.isDirect()); 
		
		//�ý��� �޸� ���� ���� �� ���� + ���ϳ���
		System.out.println("Direct - hasArray : " + direct.hasArray());
		
		//IO ����
		System.out.println("Direct - isReadOnly : " + direct.isReadOnly());
		System.out.println("Direct - toString() : " + direct.toString());
		
		//Non Direct
		//�ý��� �޸� ���� ���� �� ���� + ���ϳ���
		System.out.println("Non Direct - isDirect : " + nonDirect.isDirect()); 
		
		//���޸� ��� �Ͽ� �ý��� �޸� ���١� Array�ʿ�
		System.out.println("Non Direct - hasArray : " + nonDirect.hasArray());

		//IO ����
		System.out.println("Non Direct - isReadOnly : " + nonDirect.isReadOnly());
		System.out.println("Non Direct - toString() : " + nonDirect.toString());
	}
}