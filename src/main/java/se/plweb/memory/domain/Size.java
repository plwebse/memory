package se.plweb.memory.domain;

public class Size {
	
	private final int width;
	private final int height;
	
	private Size(final int width, final int height){
		this.width = width;
		this.height = height;
	}

	public static Size create(final int width, final int height){
		return new Size(width,height);
	}

	public int getWidth(){
		return width;
	}
	
	public int getHeight(){
		return height;
	}
}
