package edu.ycp.cs320.heatgem.client;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.LayoutPanel;
import com.google.gwt.user.client.ui.InlineLabel;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.SimpleCheckBox;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.user.cellview.client.CellList;
import com.google.gwt.cell.client.AbstractCell;
import com.google.gwt.cell.client.Cell.Context;
import com.google.gwt.safehtml.shared.SafeHtmlBuilder;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.IntegerBox;
import com.google.gwt.user.client.ui.LongBox;
import edu.ycp.cs320.heatgem.shared.UserProfile;

public class ProfileView extends Composite {
	
	private Image UserFace;
	
	private UserProfile model;

	/**
	 * @param args
	 */
	public ProfileView() {
		
		LayoutPanel layoutPanel = new LayoutPanel();
		initWidget(layoutPanel);
		layoutPanel.setSize("568px", "472px");
		
		Button btnNewButton = new Button("New button");
		layoutPanel.add(btnNewButton);
		layoutPanel.setWidgetLeftWidth(btnNewButton, 72.0, Unit.PX, 81.0, Unit.PX);
		layoutPanel.setWidgetTopHeight(btnNewButton, 384.0, Unit.PX, 30.0, Unit.PX);
		
		Button btnNewButton_1 = new Button("New button");
		layoutPanel.add(btnNewButton_1);
		layoutPanel.setWidgetLeftWidth(btnNewButton_1, 402.0, Unit.PX, 81.0, Unit.PX);
		layoutPanel.setWidgetTopHeight(btnNewButton_1, 384.0, Unit.PX, 30.0, Unit.PX);
		
		Label lblProfileView = new Label("Profile View");
		layoutPanel.add(lblProfileView);
		layoutPanel.setWidgetLeftWidth(lblProfileView, 244.0, Unit.PX, 99.0, Unit.PX);
		layoutPanel.setWidgetTopHeight(lblProfileView, 23.0, Unit.PX, 18.0, Unit.PX);
		
		//Image image = new Image("Defeat.png");
		UserFace = HeatGem.getImage("Defeat.png");
		
		UserFace.setAltText("you");
		layoutPanel.add(UserFace);
		layoutPanel.setWidgetLeftWidth(UserFace, 337.0, Unit.PX, 179.0, Unit.PX);
		layoutPanel.setWidgetTopHeight(UserFace, 81.0, Unit.PX, 197.0, Unit.PX);
		
		Label lblName = new Label("Name");
		layoutPanel.add(lblName);
		layoutPanel.setWidgetLeftWidth(lblName, 20.0, Unit.PX, 56.0, Unit.PX);
		layoutPanel.setWidgetTopHeight(lblName, 81.0, Unit.PX, 18.0, Unit.PX);
		
		Label lblLevel = new Label("Level");
		layoutPanel.add(lblLevel);
		layoutPanel.setWidgetLeftWidth(lblLevel, 20.0, Unit.PX, 56.0, Unit.PX);
		layoutPanel.setWidgetTopHeight(lblLevel, 128.0, Unit.PX, 18.0, Unit.PX);
		
		Label lblExperience = new Label("Experience");
		layoutPanel.add(lblExperience);
		layoutPanel.setWidgetLeftWidth(lblExperience, 17.0, Unit.PX, 70.0, Unit.PX);
		layoutPanel.setWidgetTopHeight(lblExperience, 181.0, Unit.PX, 18.0, Unit.PX);
		
		Label lblWins = new Label("Wins");
		layoutPanel.add(lblWins);
		layoutPanel.setWidgetLeftWidth(lblWins, 20.0, Unit.PX, 56.0, Unit.PX);
		layoutPanel.setWidgetTopHeight(lblWins, 236.0, Unit.PX, 18.0, Unit.PX);
		
		Label lblLosses = new Label("Losses");
		layoutPanel.add(lblLosses);
		layoutPanel.setWidgetLeftWidth(lblLosses, 20.0, Unit.PX, 56.0, Unit.PX);
		layoutPanel.setWidgetTopHeight(lblLosses, 284.0, Unit.PX, 18.0, Unit.PX);
		
		TextBox txtbxAlice = new TextBox();
		txtbxAlice.setText("Alice");
		layoutPanel.add(txtbxAlice);
		layoutPanel.setWidgetLeftWidth(txtbxAlice, 72.0, Unit.PX, 138.0, Unit.PX);
		layoutPanel.setWidgetTopHeight(txtbxAlice, 70.0, Unit.PX, 30.0, Unit.PX);
		
		IntegerBox integerBox = new IntegerBox();
		integerBox.setText("10");
		layoutPanel.add(integerBox);
		layoutPanel.setWidgetLeftWidth(integerBox, 72.0, Unit.PX, 151.0, Unit.PX);
		layoutPanel.setWidgetTopHeight(integerBox, 120.0, Unit.PX, 26.0, Unit.PX);
		
		LongBox longBox = new LongBox();
		longBox.setText("4258764049");
		layoutPanel.add(longBox);
		layoutPanel.setWidgetLeftWidth(longBox, 93.0, Unit.PX, 151.0, Unit.PX);
		layoutPanel.setWidgetTopHeight(longBox, 173.0, Unit.PX, 26.0, Unit.PX);
		
		IntegerBox integerBox_1 = new IntegerBox();
		integerBox_1.setText("2456");
		layoutPanel.add(integerBox_1);
		layoutPanel.setWidgetLeftWidth(integerBox_1, 72.0, Unit.PX, 151.0, Unit.PX);
		layoutPanel.setWidgetTopHeight(integerBox_1, 228.0, Unit.PX, 26.0, Unit.PX);
		
		IntegerBox integerBox_2 = new IntegerBox();
		integerBox_2.setText("326");
		layoutPanel.add(integerBox_2);
		layoutPanel.setWidgetLeftWidth(integerBox_2, 72.0, Unit.PX, 151.0, Unit.PX);
		layoutPanel.setWidgetTopHeight(integerBox_2, 284.0, Unit.PX, 26.0, Unit.PX);
		
		
	}
}
