import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;

import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.colorchooser.ColorSelectionModel;

//그래프를 그리는 패널 클래스
class DrawingPanel extends JPanel
{
 //임의로 하는 값들
// Data d1 = new Data(0.008,0.10,0.005,0.4,40,53);
// Data d2 = new Data(0.006,0.70,0.050,0.1,80,83);
// Data d3 = new Data(0.019,0.50,0.035,0.8,30,73);
 
 int n2o4, o3, co2, so4, dust, bigDust;
 String day;
 
 public void setMap(String day) {
    this.day = day;
    HashMap<String, Data> map = new HashMap<String,Data>();
//    map.put("20190101",d1);
//    map.put("20190102",d2);
//    map.put("20190103",d3);
    
    if(map.containsKey(day)) {
        n2o4 = Integer.parseInt(map.get(day).getN2O4())*1000;
        o3 =Integer.parseInt(map.get(day).getO3())*100;
        co2 = Integer.parseInt(map.get(day).getCO2())*1000;
        so4 = Integer.parseInt(map.get(day).getSO4())*100;
        dust = Integer.parseInt(map.get(day).getDust());
        bigDust = Integer.parseInt(map.get(day).getBigDust());
     }
    
 }
 
 public void paint(Graphics g){
    g.clearRect(0,0,getWidth(),getHeight());
    g.drawLine(150,350,650,350);
    for(int cnt = 1 ;cnt<11;cnt++)
    {
       g.drawString(cnt *10 +"",125,355-30*cnt);
    }
    
    g.drawLine(150,20,150,350);
    g.drawString("이산화질소",150,370);
    g.drawString("오존농도",230,370);
    g.drawString("이산화탄소",310,370);
    g.drawString("아황산가스",390,370);
    g.drawString("미세먼지",470,370);
    g.drawString("초미세먼지",550,370);
    g.setColor(Color.BLACK);
    if(n2o4>0)
       g.fillRect(180,350-n2o4*3,10,n2o4*3);
       //g.drawString("이산화질소 : " + n2o4/1, 700, 100);
    if(o3>0)
       g.fillRect(250,350-o3*3,10,o3*3);
    if(co2>0)
       g.fillRect(335,350-co2*3,10,co2*3);
    if(so4>0)
       g.fillRect(415,350-so4*3,10,so4*3);
    if(dust>0)
       g.fillRect(490,350-dust*3,10,dust*3);
    if(bigDust>0)
       g.fillRect(575,350-bigDust*3,10,bigDust*3);
 }
}



public class MyFrame extends JFrame
{
    
    public MyFrame(String str)
    {        
        super(str);
        this.setLayout(new BorderLayout());
        
        //선택한구
        String selectedLocation = "";
  
        
        //Panel (그래프(지도) , 수정)
        JPanel GraphicPanel = new JPanel();
        JPanel GraphPanel = new JPanel();
        JPanel EditPanel = new JPanel();
        JPanel temp = new JPanel();
        
        
        
        //그래프패널 레이아웃 설정
        GraphPanel.setLayout(new BorderLayout());
        
        
        
        DrawingPanel drawingPanel = new DrawingPanel();
        GraphPanel.add(drawingPanel, BorderLayout.CENTER); 
        
    
        
        //그래프를 그릴 패널
        JPanel stickControl = new JPanel();
        JButton button = new JButton("막대그래프 그리기");
        JTextField text1 = new JTextField(10);
        stickControl.add(new JLabel("날짜 선택"));
        stickControl.add(text1);
        stickControl.add(button);
        
        //꺾은선그래프를 그릴 패널
        JPanel lineControl = new JPanel();
        JButton button2 = new JButton("꺾은선그래프 그리기");
        JTextField text2 = new JTextField(10);
        JRadioButton radio = new JRadioButton("주간");
        JRadioButton radio2 = new JRadioButton("월간");
        ButtonGroup dayRadio = new ButtonGroup();
        dayRadio.add(radio);
        dayRadio.add(radio2);
        lineControl.add(new JLabel("항목 선택"));
        lineControl.add(text2);
        lineControl.add(radio);
        lineControl.add(radio2);
        lineControl.add(button2);
        
        
        //컨트롤패널
        JPanel controlPanel = new JPanel();
        controlPanel.setLayout(new GridLayout());
        controlPanel.add(stickControl);
        controlPanel.add(lineControl);
        //contentPane.add(detailPanel, BorderLayout.EAST);
        GraphPanel.add(controlPanel, BorderLayout.NORTH);
        button.addActionListener(new DrawActionListener(text1,drawingPanel));
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        //카드레이아웃
        final CardLayout card = new CardLayout();
        GraphicPanel.setLayout(card);
        
       
        //지도패널
        ImageIcon icon = new ImageIcon(".\\src\\map3.png"); 
        Image im2 = icon.getImage().getScaledInstance(700, 450, Image.SCALE_DEFAULT);
        JPanel map = new JPanel() {
         public void paintComponent(Graphics g) {
          g.drawImage(im2, 330, 20, null);
          setOpaque(false);
          //setLayout(null);
          super.setBackground(Color.WHITE); 
          super.paintComponent(g);
          
         }
        };
        
        //뒤로가기 리스너
        ActionListener back = new ActionListener() {
         
         @Override
         public void actionPerformed(ActionEvent e) {
            JButton b = (JButton) e.getSource();
            card.previous(GraphicPanel);
         }
      };

      //뒤로가기 버튼
        JButton backButton = new JButton("뒤로가기");
        GraphPanel.add(backButton,BorderLayout.SOUTH);
        
        backButton.addActionListener(back);
        
        
        GraphicPanel.add(map,"1");
        GraphicPanel.add(GraphPanel,"2");
        
        
        
        
        
        
        //패널들 레이아웃 설정
        this.setLayout(new GridLayout(2,1,10,10));
        this.setBackground(Color.lightGray);
        
        EditPanel.setLayout(new BorderLayout());
        
        
        
        JPanel selectPanel = new JPanel();
        selectPanel.setLayout(new GridLayout(2,1));
        
        
        JPanel searchPanel = new JPanel();
        searchPanel.setLayout(new BorderLayout());
        
        
        
        JPanel o3Panel = new JPanel();
        JPanel n2o4Panel = new JPanel();
        JPanel co2Panel = new JPanel();
        JPanel so4Panel = new JPanel();
        JPanel dustPanel = new JPanel();
        JPanel bitDustPanel = new JPanel();
     
        
        o3Panel.setLayout(new FlowLayout());
        n2o4Panel.setLayout(new FlowLayout());
        co2Panel.setLayout(new FlowLayout());
        so4Panel.setLayout(new FlowLayout());
        dustPanel.setLayout(new FlowLayout());
        bitDustPanel.setLayout(new FlowLayout());
        
        
        
        JPanel inputPanel = new JPanel();
        inputPanel.setLayout(new BoxLayout(inputPanel, BoxLayout.Y_AXIS));
   
      
        
        JPanel editButtonPanel = new JPanel();
        editButtonPanel.setLayout(new BorderLayout());
        
        searchPanel.setBackground(Color.white);
        editButtonPanel.setBackground(Color.white);
  
        
        
        
        
        //컴포넌트
        JTextField date = new JTextField(30);
        JTextField location = new JTextField(30);
        
        JButton search = new JButton("검색");
        
        selectPanel.add(date);
        selectPanel.add(location);
        
        searchPanel.add(selectPanel);
        searchPanel.add(search,BorderLayout.EAST);
        
        
        o3Panel.add(new JLabel("오존: "));
        n2o4Panel.add(new JLabel("이산화질소: "));
        co2Panel.add(new JLabel("이산화탄소: "));
        so4Panel.add(new JLabel("아황산가스: "));
        dustPanel.add(new JLabel("미세먼지: "));
        bitDustPanel.add(new JLabel("초미세먼지: "));
       
     
        
        JTextField o3TextField = new JTextField(30);
        JTextField n2o4TextField = new JTextField(30);
        JTextField co2TextField = new JTextField(30);
        JTextField so4TextField = new JTextField(30);
        JTextField dustTextField = new JTextField(30);
        JTextField bitDustTextField = new JTextField(30);
        
        o3Panel.add(o3TextField);
        n2o4Panel.add(n2o4TextField);
        co2Panel.add(co2TextField);
        so4Panel.add(so4TextField);
        dustPanel.add(dustTextField);
        bitDustPanel.add(bitDustTextField);
        
        
        o3Panel.setBackground(Color.white);
        n2o4Panel.setBackground(Color.white);
        co2Panel.setBackground(Color.white);
        so4Panel.setBackground(Color.white);
        dustPanel.setBackground(Color.white);
        bitDustPanel.setBackground(Color.white);
        
        inputPanel.add(o3Panel);
        inputPanel.add(n2o4Panel);
        inputPanel.add(co2Panel);
        inputPanel.add(so4Panel);
        inputPanel.add(dustPanel);
        inputPanel.add(bitDustPanel);
        
        
  
        JButton edit = new JButton("수정");
        
        editButtonPanel.add(inputPanel);
        editButtonPanel.add(edit,BorderLayout.EAST);
        
        
        EditPanel.add(searchPanel,BorderLayout.NORTH);
        EditPanel.add(editButtonPanel,BorderLayout.CENTER);
        
        
        
        
        
        //버튼(지역)
       JButton[] buttons = new JButton[25];
       map.setLayout(null);
      
      JButton enpyuong = new JButton(Const.ENPYOUNG);
      enpyuong.setBounds(567,138,75,25);
      buttons[0]=enpyuong;
        
        
        JButton dobong = new JButton(Const.DOBONG);
        dobong.setBounds(740,70,75,25);
        buttons[1]=dobong;
              

        JButton nowon = new JButton(Const.NOWON);
        nowon.setBounds(810,80,75,25);
        buttons[2]=nowon;
        
           
        
        JButton gangbuk = new JButton(Const.GANGBUK);
        gangbuk.setBounds(700,110,75,25);
        buttons[3]=gangbuk;
        
        
        JButton sungbuk = new JButton(Const.SUNGBUK);
        sungbuk.setBounds(710,175,75,25);
        buttons[4]=sungbuk;
        
        JButton jungrang = new JButton(Const.JUNGRANG);
        jungrang.setBounds(840,180,75,25);
        buttons[5]=jungrang;
        
        
        JButton jongro = new JButton(Const.JONGRO);
        jongro.setBounds(653,205,75,25);
        buttons[6]=jongro;
        
        
        JButton seodeamun = new JButton(Const.SEODAEMUN);
        seodeamun.setBounds(570,215,90,25);
        buttons[7]=seodeamun;
        
        JButton junggu = new JButton(Const.JUNGGU);
        junggu.setBounds(680,243,75,25);
        buttons[8]=junggu;
        
        
        JButton dongdaemun = new JButton(Const.DONGDAEMUN);
        dongdaemun.setBounds(770,205,90,25);
        buttons[9]=dongdaemun;
        
        JButton seungdong = new JButton(Const.SEUNGDONG);
        seungdong.setBounds(755,255,75,25);
        buttons[10]=seungdong;
        
        
        JButton gwangjin = new JButton(Const.GWANGJIN);
        gwangjin.setBounds(830,265,75,25);
        buttons[11]=gwangjin;
        
        JButton yongsan = new JButton(Const.YONGSAN);
        yongsan.setBounds(650,285,75,25);
        buttons[12]=yongsan;
        
        
        JButton mapo = new JButton(Const.MAPO);
        mapo.setBounds(528,243,75,25);
        buttons[13]=mapo;
        
        JButton gangseo = new JButton(Const.GANGSEO);
        gangseo.setBounds(390,235,75,25);
        buttons[14]=gangseo;
        
        JButton yangcheon = new JButton(Const.YANGCHEON);
        yangcheon.setBounds(440,310,75,25);
        buttons[15]=yangcheon;
        
        
        JButton yeongdeungpo = new JButton(Const.YEONGDEUNGPO);
        yeongdeungpo.setBounds(530,300,90,25);
        buttons[16]=yeongdeungpo;
        
        JButton guro = new JButton(Const.GURO);
        guro.setBounds(430,350,75,25);
        setButtonInVisible(guro);
        buttons[17]=guro;
        
        
        JButton dongjak = new JButton(Const.DONGJAK);
        dongjak.setBounds(600,330,75,25);
        buttons[18]=dongjak;
        
        
        JButton gwanak = new JButton(Const.GWANAK);
        gwanak.setBounds(595,395,75,25);
        buttons[19]=gwanak;
        
        JButton geumcheon = new JButton(Const.GEUMCHEON);
        geumcheon.setBounds(515,400,75,25);
        buttons[20]=geumcheon;
        

        JButton seocho = new JButton(Const.SEOCHO);
        seocho.setBounds(700,360,75,25);
        buttons[21]=seocho;
        
        JButton gangnam = new JButton(Const.GANGNAM);
        gangnam.setBounds(770,335,75,25);
        buttons[22]=gangnam;
        
        JButton songpa = new JButton(Const.SONGPA);
        songpa.setBounds(875,325,75,25);
        buttons[23]=songpa;
        
        JButton gangdong = new JButton(Const.GANGDONG);
        gangdong.setBounds(935,250,75,25);
        buttons[24]=gangdong;
        
        
        
        //리스너
        ActionListener listener = new ActionListener() {
         
         @Override
         public void actionPerformed(ActionEvent e) {
            JButton b = (JButton) e.getSource();
            System.out.println(b.getText());
            card.next(GraphicPanel);
            //panel.setVisible(false);
         }
      };

      
        //버튼 투명 + 패널에 추가 + 리스너 설정
        for(int i = 0 ; i < buttons.length ; i++) {
            setButtonInVisible(buttons[i]);
            map.add(buttons[i]);
            buttons[i].addActionListener(listener);
        }
        
        //패널추가
       this.add(GraphicPanel,BorderLayout.CENTER);
       this.add(EditPanel,BorderLayout.CENTER);
        
        
        
        //기본설정
        this.setSize(1400,1000);
        this.setLocation(100, 30);  
        setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
    }
    
    public void setButtonInVisible(JButton b) {
       b.setBorderPainted(false);
        b.setContentAreaFilled(false);
        b.setFocusPainted(false);
    }
    
}

class DrawActionListener implements ActionListener
{
   JTextField text1;
   DrawingPanel drawingPanel;
   DrawActionListener(JTextField text1, DrawingPanel drawingPanel)
   {
      this.text1=text1;
      this.drawingPanel = drawingPanel;
   }
   public void actionPerformed(ActionEvent e)
   {
      try
      {
         String day = text1.getText();
         drawingPanel.setMap(day);
         drawingPanel.repaint();
      }
      catch (NumberFormatException nfe){
         JOptionPane.showMessageDialog(drawingPanel,"잘못된 숫자 입력입니다","에러메시지",JOptionPane.ERROR_MESSAGE);
      }
   }
}
 