
public class TupleNode {
	private String type, defau, tupleName;
	private boolean autoInc;
	
	public TupleNode(String tupleName, String type){
		this.type = type;
		this.tupleName = tupleName;
		
	}
	public void setDefau(){
		
	}
	public String getName(){
		return tupleName;
	}
	public String getType(){
		return type;
	}
	public String getDisplay(){
		return (tupleName+ ": "+type);
	}
}

//“Type
//
//varchar(#)
//bigint(#)
//date
//double
//null: yes or no
//default None null int
//extra: AUTO_INCREMENT