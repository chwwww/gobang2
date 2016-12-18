/*������������
 * ѧ�ţ�0837077
 * */
import java.awt.Container;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
@SuppressWarnings("serial")
public class ChessMap extends JFrame {
	private ImageIcon map;				//���̱���λͼ
	private ImageIcon blackchess;		//����λͼ
	private ImageIcon whitechess;		//����λͼ
	private ChessPanel cp;				//����
	private JPanel east;
	private JPanel west;
	private static final int FINAL_WIDTH = 450;
	private static final int FINAL_HEIGHT = 500;
	//����Ϊ�����˵�
	private JMenuBar menubar;			
	private JMenu menu=new JMenu("��ʼ");
	private JMenuItem[] menuitem1={new JMenuItem("���¿�ʼ"),new JMenuItem("����"),new JMenuItem("�˳�")};
	
	Mouseclicked mouseclicked=new Mouseclicked();
	/*MouseMoved mousemoved=new MouseMoved();*/
	Menuitemclicked menuclicked=new Menuitemclicked();
	
	//���캯��
	public ChessMap(){
		setTitle("������ ");
		setSize(FINAL_WIDTH,FINAL_HEIGHT);
		setResizable(false);
		init();
		setLocation(Toolkit.getDefaultToolkit().getScreenSize().width / 2
				- FINAL_WIDTH / 2, Toolkit.getDefaultToolkit()
				.getScreenSize().height
				/ 2 - FINAL_HEIGHT / 2);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		cp.reset();	//�¿���֣���:������Ϊ�յ�
		setVisible(true);
	}
	
	
	
	//��ʼ����Ĭ��ֵ
 	public void init() 
 	{
		map=new ImageIcon(getClass().getResource("bg.jpg"));
		blackchess=new ImageIcon(getClass().getResource("blackchess.gif"));
		whitechess=new ImageIcon(getClass().getResource("whitechess.gif"));
		cp=new ChessPanel(map,blackchess,whitechess);
		menubar=new JMenuBar();
		menuitem1[0].setActionCommand("Restart");
		menuitem1[1].setActionCommand("Rollback");
		menuitem1[2].setActionCommand("Exit");
		
		for(int i=0;i<3;i++){
			menu.add(menuitem1[i]);
		}
			menubar.add(menu);
		Container p = getContentPane();
		setJMenuBar(menubar);
		east = new JPanel();
		west = new JPanel();
		p.add(east, "East");
		p.add(west, "West");
		p.add(cp, "Center");
		cp.addMouseListener(mouseclicked);
		/*cp.addMouseMotionListener(mousemoved);*/
		menuitem1[0].addActionListener(menuclicked);
		menuitem1[1].addActionListener(menuclicked);
		menuitem1[2].addActionListener(menuclicked);
	
	}
	class Mouseclicked extends MouseAdapter		//�ж���������֪ͨ���̺͵���
	{
		public void mouseClicked(MouseEvent e)
		{
		  if(cp.win==false){
    		              Point p1=new Point();
    		              p1=cp.getPoint(e.getX(),e.getY());
    		              int x=p1.x;
    		              int y=p1.y;
                          // �����λ���Ѿ���������
                          if (cp.isChessOn[x][y] != 2)
                                     return;
                          boolean flag=cp.haveWin(x, y, cp.bw);
                          cp.update( x, y );
                          cp.putVoice();  //��������
                          
                          // ��һ����,���ʼ�����ñ߽�ֵ
                         if( cp.chess_num == 1){  
                      	 if(x-1>=0)
                     	          cp.x_min = x-1;
                         if(x-1<=15)
                     	          cp.x_max = x+1;
                         if(y-1>=0)
                     	          cp.y_min = y-1;
                         if(y-1<=15)
                     	          cp.y_max = y+1;
                  }
                 else 
                 	cp.resetMaxMin(x,y);
                 if (flag) {
                     cp.wined(1 - cp.bw);
                     return;
                 }
                 
                 cp.putOne(cp.bw);
		  } 
		}
	}
	
	class Menuitemclicked implements ActionListener		//�˵���Ϣ����
	{
		public void actionPerformed(ActionEvent e) 
		{
      		JMenuItem target = (JMenuItem)e.getSource();
      		String actionCommand = target.getActionCommand();
      		if(actionCommand.equals("Restart")){ 		//�ؿ�һ��
        	   cp.reset();	
        	   if(cp.sbw==cp.WHITE_ONE)
        		   cp.update(7, 7); 
        	   //player=cp.BLACK_ONE;
      		}
      		if(actionCommand.equals("Rollback")){ 		//����
      			if(cp.win) {
        			JOptionPane.showMessageDialog(null,"����Ѿ�����,���ܻ���!�����¿�ʼ�µ����!");
        			return;
                }
        		// ��ǰ�ֵ��������,ȡ������  ����,ȡ��һ��
        		if(cp.chess_num >= 2 && cp.bw == cp.sbw){
        			cp.isChessOn[cp.pre[cp.chess_num-1][0]][cp.pre[cp.chess_num-1][1]] = 2;
        			cp.isChessOn[cp.pre[cp.chess_num-2][0]][cp.pre[cp.chess_num-2][1]] = 2;
        			cp.chess_num -= 2;
        			cp.repaint();
        		}
        		else if(cp.chess_num >= 1 && cp.bw == 1-cp.sbw){
        			cp.isChessOn[cp.pre[cp.chess_num-1][0]][cp.pre[cp.chess_num-1][1]] = 2;
        			cp.chess_num --;
       				cp.repaint();
       			}
      		}
      		else if(actionCommand.equals("Exit")){ 		//�˳�
        		System.exit(1);	
      		}
      		
          	
    	}

	}
  public static void main(String[] args) {
	    new ChessMap();	
  }
} 
