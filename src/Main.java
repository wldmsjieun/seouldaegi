public class Main 
{

   public static void main(String args[])
    {
        // FlowLayout 생성자 
        MyFrame main = new MyFrame("FlowLayout 테스트");
        
       String path = ".\\src\\test.csv";
        
        DBConnection connection = new DBConnection();
        connection.insertion(path);
//        System.out.println("test 여부 : " + connection.test(20180101, "서대문구"));
        
        

    }
}
 