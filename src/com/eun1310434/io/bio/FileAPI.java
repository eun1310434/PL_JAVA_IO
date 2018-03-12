/*=====================================================================
�� Infomation
  �� Data : 08.03.2018
  �� Mail : eun1310434@naver.com
  �� Blog : https://blog.naver.com/eun1310434
  �� Reference : ���� ���� ����Ʈ���� ����, Java Documentation, ��� �ڹ� ���α׷���, programmers.co.kr

�� Function
  �� ���� ������� �⺻
  �� ���� ��ƮȮ��
  �� �پ��� ���丮 ǥ��
  �� �ش����� Ȯ�� ���� ����Ʈ �������
  �� ���������
  �� ���ϸ���� 

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
      
  �� BIO 
    - Basic(Clocking) Input Output
    - ����½� ����, �پ��� ����� �ʿ�� ��Ⱑ �߻�.
    - �����ڿ� ���� �Ұ�

  �� ����
    - Writer  �� BufferWriter
              �� CharArrayWriter
              �� FilterWriter
              �� OutputStreamWriter �� FileWriter
              �� PipedWriter
              �� PrintWriter
              �� StringWriter

    - Reader �� BufferReader  �� LineNumberReader
              �� CharArrayReader
              �� FilterReader �� PushbackReader
              �� InputStreamReader �� FileWriter
              �� PipedReader
              �� StringReader
              
=====================================================================*/
package com.eun1310434.io.bio;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
public class FileAPI {
	
	public static void FileRoot(){
		//���� ��ƮȮ��
		System.out.println("File Roots list--------------------");
		File[] roots = File.listRoots();
		for(File root: roots) {
			System.out.print(root + "\t");
		}
	}
	
	public static void getFileListRoot(String dir, String extention){
		//�ش����� Ȯ�� ���� ����Ʈ �������
		File file = new File(dir);
		
		//Ȯ���ڰ� java�� ������ ���� ����Ʈ ���� ����
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
		//���� �����
		File file_parnet = new File(dir);
		if(!file_parnet.exists()){
			System.out.println("Make folder");
			file_parnet.mkdir();
		}else{
			System.out.println("Areadly folder");
		}
	}
	
	public static void makeFileSetting(String dir){
		//���� �����
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

		//���� ��ƮȮ��
		FileRoot();
		System.out.println();
		System.out.println();

		//�پ��� ���丮 ǥ��
		String dir = "";
		dir = "D:\\PJM\\ECLIPSE\\Examples\\PL_JAVA_IO";
		dir = "D:/PJM/ECLIPSE/Examples/PL_JAVA_IO";
		dir = "D:" + File.separator + "PJM" + File.separator + "ECLIPSE" + File.separator + "Examples" + File.separator + "PL_JAVA_IO";

		//�ش����� Ȯ�� ���� ����Ʈ �������
		getFileListRoot(dir + "\\src\\com\\eun1310434\\bio", ".java");

		//���������
		String dir_parent = "D:\\PJM\\ECLIPSE\\Examples\\PL_JAVA_IO\\data\\";
		makeFolderSetting(dir_parent);


		//���ϸ����
		String dir_make = dir_parent + "DATA_FileAPI.txt";
		makeFileSetting(dir_make);
	}
}