package application;

public class EdgeList {
	
	private int x;
	private int y;

	
	private int height;
	private int width;
		
	public EdgeList() {
		x = 0;
		y = 0;
		
		height = 0;
		width = 0;
	}
	
	public EdgeList(int x, int y, int height, int width) {
		this.x = x;
		this.y = y;
		
		this.height = height;
		this.width = width;
	}
	
	public int check(int X, int Y, int radius) {
		int flag = 0;
		
		if (X >= x-radius && X <=x+width+radius)
			if ((Y >= y-radius &Y <= y)||(Y >= y+height+radius && Y <=  y+height+radius+5)) {
				flag = -1;
		}
		
		if ((X >= x-radius && X <=x-radius+5)||(X >= x+radius+width && X <=x+radius+width-5))
			if (Y >= y-radius && Y <= y+radius+height) {
				flag = 1;
		}
		
		return flag;
	}
	
}
