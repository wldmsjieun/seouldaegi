public class Main 
{

   public static void main(String args[])
    {
        // FlowLayout ������ 
        MyFrame main = new MyFrame("FlowLayout �׽�Ʈ");
        
       String path = ".\\src\\test.csv";
        
        DBConnection connection = new DBConnection();
        connection.insertion(path);
//        System.out.println("test ���� : " + connection.test(20180101, "���빮��"));
        
        

    }
}
 