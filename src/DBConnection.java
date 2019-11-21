import java.io.BufferedReader;
import java.io.FileReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.StringTokenizer;


public class DBConnection {

   private Connection con;
   private Statement st;
   private ResultSet rs;
   PreparedStatement pstmt=null;

   public DBConnection() {
      try {
         Class.forName("com.mysql.cj.jdbc.Driver"); 
         con = DriverManager.getConnection("jdbc:mysql://localhost:3306/Daegi?serverTimezone=UTC&autoReconnect=true&validationQuery=select1", "root", "admin");
         con.setAutoCommit(false);
         st = con.createStatement();
      }
      catch(Exception e){
         System.out.println("데이터베이스 연결 오류 : " + e.getMessage());
      }
   }

   
   public void insertion(String path) {
      BufferedReader reader = null;
      try {
         List <Data> list = new ArrayList<Data>();
         String str = "";
         reader = new BufferedReader(new FileReader(path));
//         reader.readLine();

         while((str = reader.readLine())!= null) {
//          
            StringTokenizer tokens = new StringTokenizer(str,",");
//            if (fields.length > 0) {
            while(tokens.hasMoreElements()) {
               Data d = new Data(); 
               
               d.setDate(Integer.parseInt(tokens.nextToken()));
               d.setlocation(tokens.nextToken());
              try {
//                  System.out.println("맞아2");
                  d.setN2o4(tokens.nextToken());
               }catch(NoSuchElementException e) {
                  d.setN2o4(null);
               }
               
              try {
//                  System.out.println("맞아3");
                  d.setO3(tokens.nextToken());
              }catch(NoSuchElementException e) {
                  
                  d.setO3(null);
               }
               
               try {
//                  System.out.println("맞아4");
                  d.setCO2(tokens.nextToken());
               }catch(NoSuchElementException e) {
                  
                  d.setCO2(null);
               }
               
               try {
//                  System.out.println("맞아5");
                  d.setSO4(tokens.nextToken());
               }catch(NoSuchElementException e) {
                  d.setSO4(null);
               }
               
               try {
//                  System.out.println("맞아6");
                  d.setDust(tokens.nextToken());
               }catch(NoSuchElementException e) {
                  d.setDust(null);
               }
               
               try {
//                  System.out.println("맞아7");
                  d.setBigDust(tokens.nextToken());
               }catch(NoSuchElementException e) {
                  d.setBigDust(null);
               }
               

               list.add(d);
            }
         }
//         for (Data d : list) {
//            System.out.printf("%d\t%s\t%d\n", d.getDate(), d.getlocation(), d.getBigDust());
//            
//         }
         
         for (int i=0; i<list.size(); i++) {
            Data d = list.get(i);
            String SQL = "insert into seouldaegi values (?,?,?,?,?,?,?,?)";
            
            pstmt = con.prepareStatement(SQL);
            pstmt.setInt(1, d.getDate());
            pstmt.setString(2, d.getlocation());
            pstmt.setString(3, d.getN2O4());
            pstmt.setString(4, d.getO3());
            pstmt.setString(5, d.getCO2());
            pstmt.setString(6, d.getSO4());
            pstmt.setString(7, d.getDust());
            pstmt.setString(8, d.getBigDust());
            System.out.println(pstmt);
            pstmt.executeUpdate(); 
            
         }
         con.commit();
         
      }catch(Exception e){
         e.printStackTrace();
      }finally {
         try{
            reader.close();
            pstmt.close(); 
            con.close();
         }catch(Exception e) {
            e.printStackTrace();
         }
      }
   }

   public boolean test(int date, String loc) {
      try {
         String SQL = "SELECT * FROM seouldaegi WHERE date = " + date + " and location = '" + loc +"'";
//         pstmt = con.prepareStatement(SQL);
//         st = getDBConn().createStatement();
         rs = st.executeQuery(SQL);
        
        
         if (rs.next()) {
            return true;
         }
      }
      catch(Exception e){
         System.out.println("데이터베이스 검색 오류 : " + e.getMessage());
      }
      return false;
   }

}