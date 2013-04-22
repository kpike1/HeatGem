package edu.ycp.cs320.heatgem.client;


import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Event;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.MouseDownHandler;
import com.google.gwt.event.dom.client.MouseMoveEvent;
import com.google.gwt.event.dom.client.MouseUpHandler;
import com.google.gwt.event.dom.client.MouseOverHandler;
import com.google.gwt.event.dom.client.MouseMoveHandler;
import com.google.gwt.event.dom.client.MouseOutHandler;
import com.google.gwt.canvas.client.Canvas;
import com.google.gwt.canvas.dom.client.Context2d;
import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.CanvasElement;
import com.google.gwt.dom.client.ImageElement;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.event.dom.client.KeyDownEvent;
import com.google.gwt.event.dom.client.KeyDownHandler;
import com.google.gwt.event.dom.client.KeyUpEvent;
import com.google.gwt.event.dom.client.KeyUpHandler;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FocusPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.LayoutPanel;
import com.google.gwt.user.client.ui.MouseListener;

import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.widget.client.TextButton;

import edu.ycp.cs320.heatgem.shared.Game;


public class GameUI extends Composite {


	public int MouseX;
	public int MouseY;
	private Canvas buffer;
	private Context2d bufCtx;
	private Canvas canvas;
	private Context2d ctx;
	private Timer timer;
	private Image background;
	private Image PlayerHealth;
	private Image EnemyHealth;
	private Image PlayerFace;
	private Image EnemyFace;
	private Image Attack; 
	private Image AttackSelected;
	private Image Heal;
	private Image HealSelected;
	private Image Defeat;
	
	// The game object contains all of the game state data.
	private Game game;
	
	@SuppressWarnings("deprecation")
	public GameUI() {
		
		//FocusPanel
		final FocusPanel panel = new FocusPanel();
		//LayoutPanel layoutPanel = new LayoutPanel();
		panel.setSize("800px", "480px");
		
		
		//"buffer" canvas
		this.buffer = Canvas.createIfSupported();
		buffer.setSize(Game.Width + "px", Game.Height + "px");
		buffer.setCoordinateSpaceWidth(Game.Width);
		buffer.setCoordinateSpaceHeight(Game.Height);
		this.bufCtx = buffer.getContext2d();
		
		// The visible canvas
		this.canvas = Canvas.createIfSupported();
		canvas.setSize(Game.Width + "px", Game.Height + "px");
		canvas.setCoordinateSpaceWidth(Game.Width);
		canvas.setCoordinateSpaceHeight(Game.Height);
		this.ctx = canvas.getContext2d();
		panel.add(canvas);

		initWidget(panel);		
		
		this.timer = new Timer() {
			@Override
			public void run() {
				Draw();
				//onMouseMove(panel, MouseX, MouseY);
				//onClick(panel, MouseX, MouseY);
				//System.out.println(MouseX);
				//System.out.println(MouseY);
			}
		};
		
		canvas.addMouseMoveHandler(new MouseMoveHandler() {
			
			@Override
			public void onMouseMove(MouseMoveEvent event) {
				GWT.log("Mouse moved: x="+ event.getX()+ ", y=" + event.getY());
				MouseX = event.getX();
				MouseY = event.getY();
			}
		});
		
//		panel.addMouseListener(new MouseListener() {
		
			
		
//		TextButton ATK = new TextButton("Attack");
//		layoutPanel.add(ATK);
//		ATK.setSize("75", "30");
//		layoutPanel.setWidgetLeftWidth(ATK, 380.0, Unit.PX, 84.0, Unit.PX);
//		layoutPanel.setWidgetTopHeight(ATK, 360.0, Unit.PX, 30.0, Unit.PX);
//		ATK.addClickHandler(new ClickHandler() {
//			public void onClick(ClickEvent event) {
//				System.out.println("CLICK ATK");
//			}
//		});
//		
//		TextButton HEAL = new TextButton("Heal");
//		layoutPanel.add(HEAL);
//		HEAL.setSize("75", "30");
//		layoutPanel.setWidgetLeftWidth(HEAL, 380.0, Unit.PX, 84.0, Unit.PX);
//		layoutPanel.setWidgetTopHeight(HEAL, 410.0, Unit.PX, 30.0, Unit.PX);
//		HEAL.addClickHandler(new ClickHandler() {
//			public void onClick(ClickEvent event) {
//				System.out.println("CLICK HEAL");
//			}
//		});

	}
	
	public void onMouseDown(Widget sender, int x, int y)
    {
      
    }
    public void onMouseEnter(Widget sender)
    {
       
    }
    public void onMouseLeave(Widget sender)
    {
        
    }
    public void onClick(Widget sender, int x, int y)
    {
    	x = MouseX;
    	y = MouseY;

    }
    public void onMouseUp(Widget sender, int x, int y)
    {
       
    }
    public void onMouseMove(Widget sender, int x, int y)
    {
    	x = MouseX;
    	y = MouseY;
    }
	
	
	public void startGame() {
		// get background and sprite images that will be used for painting
		background = HeatGem.getImage("RoughBattle.jpg");
		PlayerHealth = HeatGem.getImage("TBAR.jpg");
		EnemyHealth = HeatGem.getImage("TBAR.jpg");
		PlayerFace = HeatGem.getImage("FullHealth.png");
		EnemyFace = HeatGem.getImage("YellowHealth.png");
		Attack = HeatGem.getImage("Attack.png");
		Heal = HeatGem.getImage("Heal.png");
		Defeat = HeatGem.getImage("Defeat.png");
		AttackSelected = HeatGem.getImage("AttackSelected.png");
		HealSelected = HeatGem.getImage("HealSelected.png");
		
		// Add a listener for mouse motion.
		// Each time the mouse is moved, clicked, released, etc. the handleMouseMove method
		// will be called.
		timer.scheduleRepeating(1000/10);
	}
	

	protected void handleTimerEvent() {
		// You should not need to change this method.
		game.timerTick();
		
	}
	

	
	protected void Draw() {
		// Draw onto buffer
		
		// Draw background

		
		// Draw background
		bufCtx.drawImage((ImageElement) background.getElement().cast(),
				0,
				0);
		//Draw PlayerHealth Bar
		bufCtx.drawImage((ImageElement) PlayerHealth.getElement().cast(),
				30,
				430);
		//Draw EnemyHealth Bar
		bufCtx.drawImage((ImageElement) EnemyHealth.getElement().cast(),
				450,
				35);
		//Draw Sprite for character
		bufCtx.drawImage((ImageElement) PlayerFace.getElement().cast(),
				50,
				200);
		//Draw Sprite for Enemy
		bufCtx.drawImage((ImageElement) EnemyFace.getElement().cast(),
				580,
				100);
		if((MouseX > 380 && MouseX < 455) && (MouseY > 360 && MouseY < 390))
		{
			//Draw AttackSelected Button
			bufCtx.drawImage((ImageElement) AttackSelected.getElement().cast(),
					380,
					360);
		}
		else
		{
		//Draw Attack Button
		bufCtx.drawImage((ImageElement) Attack.getElement().cast(),
				380,
				360);
		}
		if((MouseX > 380 && MouseX < 455) && (MouseY > 410 && MouseY < 440))
		{
		//Draw HealSelected Button
		bufCtx.drawImage((ImageElement) HealSelected.getElement().cast(),
				380,
				410);
		}
		else
		{
			//Draw Heal Button
			bufCtx.drawImage((ImageElement) Heal.getElement().cast(),
					380,
					410);
		}
		
		// Copy buffer onto main canvas
		ctx.drawImage((CanvasElement) buffer.getElement().cast(), 0, 0);

	}

	
}
