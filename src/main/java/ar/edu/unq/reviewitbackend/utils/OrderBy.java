package ar.edu.unq.reviewitbackend.utils;


public class OrderBy {
	
	String name;
	String dir;
    
	public OrderBy(){};

	public OrderBy(String name, String dir){
		this.setDir(dir);
		this.setName(name);
	}

	public String getDir() {
        return dir;
    }
    public String getName() {
        return name;
	}

	public void setDir(String dir){
		this.dir = dir;		
	}

	public void setName(String name){
		this.name = name;
	}
	
}
