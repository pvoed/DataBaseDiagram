import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.TextArea;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.Random;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JEditorPane;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;



public class DiagramPanel extends JPanel {
	private static final long serialVersionUID = 1L;

	public static final int WIDTH = 800;
	public static final int HEIGHT = 600;
	public static final int ROOT_NODE = 0;
	public static final int TABLE_NODE = 1;
	public static final int ITEM_NODE = 2;
	public static final double ROOT_X = 375;
	public static final double ROOT_Y = 250;
	public static final double ROOT_H = 25;
	public static final double ROOT_W = 50;
	public static final String[] tupeTypes = { "varchar(26)", "bigint(20)" , "int(12)" , "date", "double" };
	public static final Rectangle2D root = new Rectangle2D.Double(ROOT_X, ROOT_Y, ROOT_W, ROOT_H);


	private int PANEL_STATE;
	private int SUB_STATE;
	private int TUPLE_STATE;
	private JComboBox typeList;
	private JPanel tupleOptionsMenu;
	private JFrame menuRun;
	private JButton tupleButton;
	private JButton cancelButton;
	private JTextField tupleName;
	private TextArea textinfo;
	private String sql;
	private JScrollPane pScroll;
	private ArrayList<TableNode> TNodes;
	//private JScrollPane pScroll;
	public DiagramPanel() {
		//INSERT INTO financial_advisors VALUES(?,?);
		TNodes = new ArrayList<TableNode>();
		setPreferredSize(new Dimension(WIDTH, HEIGHT));
		sql="";
		setBackground(Color.BLACK);
		JButton addButton = new JButton("add");
		JButton removeButton = new JButton("remove");
		ButtonStack bs = new ButtonStack();
		
		
		textinfo = new TextArea(7, 85);
		typeList = new JComboBox(tupeTypes);
		typeList.setSelectedIndex(4);
		 pScroll = new JScrollPane(textinfo, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		
		bs.add(addButton);
		bs.add(removeButton);
		add(bs);
		add(pScroll, BorderLayout.SOUTH);
	
		//textinfo.setBounds(0, 500, 800, 100);
		//textinfo.setAlignmentX(0);
		//textinfo.setAlignmentY(500);
		
		//BUTTON LISTENERS
		addButton.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e){
				handleActionPerformed(e);
			}
		});
		removeButton.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e){
				handleActionPerformed(e);
			}
		});

		//MOUSE LISTENERS
		addMouseListener(new MouseAdapter(){
			@Override
			public void mouseClicked(MouseEvent e) {
				handleMouseClick(e);
			}

		});



	}



	//MOUSE CLICK HANDLER ON NODES
	protected void handleMouseClick(MouseEvent e) {
		//HIGHLIGHTS NODES ON CLICK
		if(root.contains(e.getX(), e.getY())){
			PANEL_STATE = ROOT_NODE;
			repaint();
			System.out.println("tried to handle this event");
		}
		for(int i = 0; i< TNodes.size();i++){
			TableNode n = TNodes.get(i);
			Rectangle2D rec = n.getRec();
			if(rec.contains(e.getX(),e.getY())){
				PANEL_STATE = TABLE_NODE;
				SUB_STATE = i;
				repaint();
			}
		}

	}


	@Override
	public void paint(Graphics g) {

		super.paint(g);
		Graphics2D g2 = (Graphics2D)g;
		g2.setPaint(Color.GREEN);
		if(PANEL_STATE == ROOT_NODE){
			g2.setPaint(Color.BLUE);
		}
		//DRAWS ROOT
		g2.fill(root);
		g2.setPaint(Color.CYAN);
		g2.drawString("ROOT", (int)ROOT_X, (int)ROOT_Y);
		sql = "";
		//DRAWS TABLE
		for(int i = 0; i< TNodes.size();i++){
			TableNode n = TNodes.get(i);
			sql = sql + "create table " + n.getName() + " (\n"; 
			n.setX((WIDTH/(TNodes.size()+1))*(i+1)-25);
			g2.setPaint(Color.GREEN);
			if(i == SUB_STATE && PANEL_STATE == TABLE_NODE){
				g2.setPaint(Color.BLUE);
			}
			g2.fill(n.getRec());
			g2.setPaint(Color.CYAN);
			g2.drawString(n.getName(), (int)n.getX(), (int)n.getY());
			g2.setPaint(Color.WHITE);
			g2.drawLine((int)ROOT_X+25, (int) ROOT_Y+25, (int)n.getX()+25, (int)n.getY());
			//DRAW NODES
			if(!n.isEmpty()){
				System.out.println("Non-Empty Table");
				for(int k = 0; k< n.getNumTuples(); k++){
					
					TupleNode tn = n.getTuple(k);
					sql = sql +"\t" + tn.getName() + " "+tn.getType();
					g2.setPaint(Color.LIGHT_GRAY);
					g2.fillRect((int)n.getX()+5,(int)(n.getY()+((k+1)*25)+15),(int)n.getW()-10,(int)n.getH()-10);
					g2.setPaint(Color.CYAN);
					g2.drawString(tn.getDisplay(), (int)n.getX(),(int)(n.getY()+((k+1)*25)+15));
					if(k==n.getNumTuples()-1){
						sql = sql + "\n";
					}else{
						sql = sql + ",\n";
					}
				}	
			}
			sql = sql + ");\n"+n.getInsert()+ "\n\n"; 
		}
		g2.setPaint(Color.BLACK);
		textinfo.setText(sql);
		
		
		//System.out.println(sql);

		if(PANEL_STATE == TABLE_NODE){

		}
		if(PANEL_STATE == ITEM_NODE){

		}




	}

	public void handleActionPerformed(ActionEvent e) {
		//ADDS TUPLET HANDLER
		if (e.getActionCommand().equals("Ok")) {
			TupleNode tuple = new TupleNode(tupleName.getText(), typeList.getSelectedItem().toString());
			//System.out.println(tupleName.getText()+" : "+ typeList.getSelectedItem().toString());
			TNodes.get(SUB_STATE).add(tuple);
			repaint();
		}
		//create tuple HANDLER
		if (e.getActionCommand().equals("add")) {
			{
				System.out.println("add has been clicked");

				if(PANEL_STATE == ROOT_NODE){
					String tableName = JOptionPane.showInputDialog(null, "New table name : ", "Add Table", 1);

					if(tableName != null){
						JOptionPane.showMessageDialog(null, "You've created a new table : " + tableName, "Sucess!", 1);
						TNodes.add(new TableNode(tableName));
						repaint();
					}
					else{
						JOptionPane.showMessageDialog(null, "Aborted.", "Cancled", 1);
					}
				}
			}
			if(PANEL_STATE == TABLE_NODE){
				//create tuple pop up
				tupleOptionsMenu = new JPanel();
				menuRun = new JFrame();
				tupleButton = new JButton("Ok");
				cancelButton = new JButton("Cancel");
				tupleName = new JTextField();
				tupleOptionsMenu.setLayout(new GridLayout(3,2));
				tupleOptionsMenu.setPreferredSize(new Dimension(300, 150));
				tupleOptionsMenu.setBackground(Color.GRAY);
				tupleOptionsMenu.add(new JLabel("TupleName: "));
				tupleOptionsMenu.add(tupleName);
				tupleOptionsMenu.add(new JLabel("DataType: "));
				tupleOptionsMenu.add(typeList);
				tupleOptionsMenu.add(tupleButton);
				tupleOptionsMenu.add(cancelButton);
				menuRun.getContentPane().add(tupleOptionsMenu, BorderLayout.PAGE_START);
				menuRun.pack();
				menuRun.setVisible(true);

				tupleButton.addActionListener(new ActionListener(){
					@Override
					public void actionPerformed(ActionEvent e){
						handleActionPerformed(e);
						menuRun.setVisible(false);
					}
				});
				cancelButton.addActionListener(new ActionListener(){
					@Override
					public void actionPerformed(ActionEvent e){
						handleActionPerformed(e);
						menuRun.setVisible(false);
					}
				});


			}
			if(PANEL_STATE == ITEM_NODE){

			}
		}

		//REMOVE A NODE BUTTON HANDLER
		if (e.getActionCommand().equals("remove")) {
			//System.out.println("remove has been clicked");
			if(PANEL_STATE == ROOT_NODE){

				JOptionPane.showMessageDialog(null, "Cannot Remove Root!", "Error!", 1);
			}
			if(PANEL_STATE == TABLE_NODE){
				TNodes.remove(SUB_STATE);
				PANEL_STATE = ROOT_NODE;
				repaint();
			}

		}
	}

}



