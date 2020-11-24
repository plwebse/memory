package se.plweb.memory.gui;

import java.awt.Color;
import java.awt.Graphics;
import se.plweb.memory.domain.*;

/**
 * 
 * @author Peter Lindblom
 */

public class GameObjectGuiImpl implements GameObjectGui {

	private static final long serialVersionUID = 1L;
    private static final Color ACTIVE_BORDER_COLOR = Color.LIGHT_GRAY;
    private static final Color INACTIVE_BORDER_COLOR = Color.DARK_GRAY;
    private static final Color MATCHED_BORDER_COLOR = Color.RED;
    private static final Color BACKGROUND_COLOR = Color.WHITE;
    private static final Color MATCHED_BACKGROUND_COLOR = Color.LIGHT_GRAY;
    private static final Color MOUSEOVER = Color.BLACK;
	private GameObject gameObject;
	private GuiHelper guiHelper;
	private static final int borderWitdh = 5;
	private static final int borderWitdhX2 = 10;
	
	private int width, height, xTopLeft, yTopLeft;
	public enum GUIState {NORMAL, MOUSE_OVER}

    public GameObjectGuiImpl(int value, Position position, GuiHelper guiHelper) {
		gameObject = GameObjectImpl.create(value, position);
		this.guiHelper = guiHelper;
	}

	public int getValue() {
		return gameObject.getValue();
	}

	public Position getPosition() {
		return gameObject.getPosition();
	}

	public void setPosition(Position position) {
		gameObject.setPosition(position);
	}

	public GameObjectState getState() {
		return gameObject.getState();
	}

	public void setState(GameObjectState value) {
		gameObject.setState(value);
	}

	public void setValue(int value) {
		gameObject.setValue(value);
	}

	@Override
	public String toString() {
        return gameObject.toString(); 		
	}

	public boolean equals(GameObject obj) {
		return gameObject.equals(obj);
	}

    private void paintBackground(Graphics graphics, Color backgroundColor, Color borderColor){
        graphics.setColor(backgroundColor);        
        graphics.fillRect(xTopLeft, yTopLeft, width, height);
        graphics.setColor(borderColor);
        graphics.drawRect(xTopLeft, yTopLeft, width-1, height-1);        
    }

	private void paintValue(Graphics graphics, int value) {
        graphics.setColor(guiHelper.getValueColor(value));
        paintValueRect(graphics, value);        
	}

	private void paintValueRect(Graphics graphics, int value) {
		
		int tmpValue = guiHelper.getDisplayValue(value);
		
		int valueObjectWidth = calculateValueObjectWidth();
		int valueObjectHeight = calculateValueObjectHeight();
	
		int startX = calculateTopX();
		int startY = calculateTopY();
		
		int xOffset = startX;
		int yOffset = startY;

		for(int i=0, row=0;i< tmpValue; i++){			
			graphics.fillRect(xOffset, yOffset, valueObjectWidth, valueObjectHeight);
			if((i+1) % 4 != 0){
				xOffset = xOffset + (valueObjectWidth + calculateValueObjectSpace(valueObjectWidth));
			}else{				
				xOffset = xOffset + (valueObjectWidth);
				if(row != 3){
					yOffset = yOffset + (valueObjectHeight + calculateValueObjectSpace(valueObjectHeight));					
				}else{
					yOffset = yOffset + valueObjectHeight;
					row++;
				}				
				xOffset = startX;
			}
		}
	}
	
	private int calculateTopX(){
		int topLeftX = xTopLeft + borderWitdh; 		
		topLeftX += calculateCenterXPos(); 
		topLeftX = topLeftX - calculateHalfOfTotalValueWidth(); 		
		return topLeftX;
	}

	private int calculateTopY(){
		int topLeftY = yTopLeft + borderWitdh;
		topLeftY += calculateCenterYPos();
		topLeftY = topLeftY - calculateHalfOfTotalValueHeight();		
		return topLeftY;
	}

	private int calculateHalfOfTotalValueWidth(){
		int valueWidth = (calculateValueObjectWidth() * 4); 
		valueWidth = valueWidth + calculateValueObjectSpace(calculateValueObjectWidth() * 3);		
		int halfValueWidth = Math.round(valueWidth / 2);
		return halfValueWidth;
	}
	
	private int calculateHalfOfTotalValueHeight(){
		int valueWidth = (calculateValueObjectHeight() * 4); 
		valueWidth = valueWidth + calculateValueObjectSpace(calculateValueObjectHeight() * 3);		
		int halfValueWidth = Math.round(valueWidth / 2);
		return halfValueWidth;
	}
	
	private int calculateCenterXPos(){
		return Math.round((width - borderWitdhX2) / 2); 
	}
	
	private int calculateCenterYPos(){
		return Math.round((height - borderWitdhX2) / 2); 
	}
	
	private int calculateValueObjectWidth(){
		return Math.round((width - borderWitdhX2) / 6) ;
	}
	
	private int calculateValueObjectHeight(){
		return Math.round((height - borderWitdhX2) / 6) ;
	}
	
	private int calculateValueObjectSpace(int valueObjectWidthOrHeight){
		return Math.round(valueObjectWidthOrHeight / 2);
	}
	
	public boolean isInNormalState() {
		return this.gameObject.isInNormalState();
	}

	public boolean hasTheSameValueAndNotTheSameCoordinates(GameObject gameObject) {
		return this.gameObject
				.hasTheSameValueAndNotTheSameCoordinates(gameObject);
	}

	public boolean hasTheSameValueAndTheSameCoordinates(GameObject gameObject) {
		return this.gameObject.hasTheSameValueAndTheSameCoordinates(gameObject);
	}

	public GameObject getClone() {
		GameObject clone = new GameObjectGuiImpl(this.getValue(), this
				.getPosition(), guiHelper);
		clone.setState(this.getState());
		return clone;
	}

	public void draw(Graphics graphics, Size size, GUIState guiState) {
		if(size != null){
			this.width = size.getWidth();
			this.height = size.getHeight();
		}
		
		xTopLeft =  this.width * gameObject.getPosition().getXPos(); 
		yTopLeft =  this.height * gameObject.getPosition().getYPos();
		
		switch (gameObject.getState()) {
		case NORMAL_STATE:
			paintBackground(graphics, BACKGROUND_COLOR, ACTIVE_BORDER_COLOR);
			break;
		case DISABLED_STATE:
			paintBackground(graphics, BACKGROUND_COLOR, INACTIVE_BORDER_COLOR);
			break;
		case MATCHED_STATE:                         
			paintBackground(graphics, MATCHED_BACKGROUND_COLOR, MATCHED_BORDER_COLOR);
			paintValue(graphics, gameObject.getValue());
			break;
		case PRESSED_STATE:
			paintBackground(graphics, BACKGROUND_COLOR, INACTIVE_BORDER_COLOR);
			paintValue(graphics, gameObject.getValue());            
			break;
		}
		
		if(guiState.equals(GUIState.MOUSE_OVER)){			
			graphics.setColor(MOUSEOVER);
	        graphics.drawRect((xTopLeft+1), (yTopLeft+1), (width-3), (height-3));
		}
	}

	public boolean isPositionInsideOfGameObject(Position position) {

        return position != null
                && position.getXPos() >= xTopLeft
                && position.getXPos() < (xTopLeft + width)
                && position.getYPos() >= yTopLeft
                && position.getYPos() < (yTopLeft + height);
    }
}
