/*=====================================================================
�� Infomation
  �� Data : 09.03.2018
  �� Mail : eun1310434@naver.com
  �� Blog : https://blog.naver.com/eun1310434
  �� Reference : ���� ���� ����Ʈ���� ����, Java Documentation, ��� �ڹ� ���α׷���, programmers.co.kr

�� Function
  �� ByteBuffer�� �޸� �ڸ���
    - byteBuffer.slice()

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
import java.nio.ByteBuffer;

public class ByteBufferAPI02 {

	public static void main(String[] ar) {
		byte[] data = "eun1310434@naver.com\n".getBytes();
		
		//�ý��� �޸𸮿� ���� �Ҵ�
		//ByteBuffer byteBuffer = ByteBuffer.allocateDirect(1024);
		
		//���޸𸮿� �Ҵ�
		ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
		byteBuffer.put(data);

		//�ý��� �޸𸮿� ���� �Ҵ��� �ƴҽÿ��� �� �޸𸮸� ����ϸ� Array�� Ȱ��
		//�ý��� �޸� -> hasArray() = false
		//�� �޸� -> hasArray() = true
		System.out.println("byteBuffer.hasArray() : "+ byteBuffer.hasArray());
		
		
		byte[] slice = null;
		ByteBuffer byteBuffer_slice = null;
		
		//����
		if(byteBuffer.hasArray()){
			//�� �޸� ����
			//byteBuffer.array()�� byteBuffer�� ��ü ����
			slice = byteBuffer.array();
		}else{
			//�ý��� �޸� ����
			//slice()�� byteBuffer�� ��ü ũ���� ����� ���� ������ ũ�⸦ ����
			byteBuffer_slice = byteBuffer.slice();
		}

		//byteBuffer�� put�� �ϰ� �Ǹ� byteBuffer_slice�� slice�� ���� ������ �޸𸮸� �����ϱ⿡ ���̺���
		byteBuffer.put("origin insert\n".getBytes());
		
		
		//���
		//����
		System.out.println("origin---------------------------------");
		byteBuffer.flip();
		while(byteBuffer.hasRemaining()){
			System.out.print((char)byteBuffer.get()); //buffer�� ũ�⸸ŭ ����� �ֱ⿡ ���ڰ� �ȳ���
		}
		byteBuffer.clear();//�����ϰ� ����
		
		
		//���纻
		if(byteBuffer_slice == null){
			System.out.println("slice---------------------------------");
			for(int i = 0; i < slice.length; ++i) {
				System.out.print((char)slice[i]);
			}
		}else{
			System.out.println("byteBuffer_slice---------------------------------");
			while(byteBuffer_slice.hasRemaining()){
				System.out.print((char)byteBuffer_slice.get()); //buffer�� ũ�⸸ŭ ����� �ֱ⿡ ���ڰ� �ȳ���
			}
			byteBuffer_slice.clear();//�����ϰ� ����
		}
	}
}