import java.awt.geom.Rectangle2D;
import java.util.ArrayList;


public class TableNode {
	private String tableName, s;
	private ArrayList<TupleNode> tuples;
	private double X, Y, H, W;
	private Rectangle2D node;
	private boolean isEmpty;
	public TableNode(String tableName){
		this.tableName = tableName;
		tuples = new ArrayList<TupleNode>();
		H = 25;
		W = 50;
		Y = 300;
		node = new Rectangle2D.Double(375, Y, W, H);
		isEmpty = true;
		s= "";
	}
	public void setX(double X){
		this.X = X;
		node = new Rectangle2D.Double(X, Y, W, H);
	}
	public double getY(){
		return Y;
	}
	public double getX(){
		return X;
	}
	
	public double getH(){
		return H;
	}
	public String getInsert(){
		 s = "INSERT INTO "+ tableName + " VALUES(";
		 for(int i = 0; i<tuples.size();i++){
			 if(i==tuples.size()-1){
			 s = s +"?";
			 }else{
				 s=s+"?,";
			 }
		 }
		 s = s + ")";
		return s;
	}
	public double getW(){
		return W;
	}
	public Rectangle2D getRec(){
		return node;
	}
	public String getName(){
		return tableName;
	}
	public void add(TupleNode item){
		tuples.add(item);
		isEmpty = false;
	}
	public boolean isEmpty(){
		return isEmpty;
	}
	public int getNumTuples(){
		return tuples.size();
	}
	public TupleNode getTuple(int index){
		return tuples.get(index);
	}
	public void remove(int index){
		tuples.remove(index);
		if (tuples.isEmpty()){
			isEmpty = true;
		}
	}
}
