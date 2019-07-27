package exer5.client.view;
import javax.swing.JButton;

public class GameplayButton extends JButton{
	private int row;
	private int col;

	public GameplayButton(String mark, int row, int col)
	{
		super(mark);
		this.setRow(row);
		this.setCol(col);
	}

	public int getRow() {
		return row;
	}

	public void setRow(int row) {
		this.row = row;
	}

	public int getCol() {
		return col;
	}

	public void setCol(int col) {
		this.col = col;
	}
}
