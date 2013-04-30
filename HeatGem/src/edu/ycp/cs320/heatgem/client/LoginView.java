package edu.ycp.cs320.heatgem.client;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.LayoutPanel;
import com.google.gwt.user.client.ui.InlineLabel;
import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.SimpleCheckBox;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.MouseMoveEvent;
import com.google.gwt.event.dom.client.MouseMoveHandler;

import edu.ycp.cs320.heatgem.shared.User;
import com.google.gwt.user.client.ui.PasswordTextBox;
import com.google.gwt.user.client.ui.Image;

public class LoginView extends Composite {
	private TextBox usernameTextBox;
	private Label errorLabel;
	private SimpleCheckBox simpleCheckBox;	
	private PasswordTextBox passwordLoginTextBox;
	private Image h_gem;
	public LoginView() {
		
		LayoutPanel layoutPanel = new LayoutPanel();
		initWidget(layoutPanel);
		layoutPanel.setSize("442px", "619px");
		
		InlineLabel usernameLabel = new InlineLabel("Username:");
		layoutPanel.add(usernameLabel);
		layoutPanel.setWidgetLeftWidth(usernameLabel, 55.0, Unit.PX, 90.0, Unit.PX);
		layoutPanel.setWidgetTopHeight(usernameLabel, 304.0, Unit.PX, 18.0, Unit.PX);
		
		usernameTextBox = new TextBox();
		layoutPanel.add(usernameTextBox);
		layoutPanel.setWidgetLeftWidth(usernameTextBox, 151.0, Unit.PX, 173.0, Unit.PX);
		layoutPanel.setWidgetTopHeight(usernameTextBox, 293.0, Unit.PX, 34.0, Unit.PX);
		//String username =  usernameTextBox.toString();
		
		InlineLabel passwordLabel = new InlineLabel("Password");
		layoutPanel.add(passwordLabel);
		layoutPanel.setWidgetLeftWidth(passwordLabel, 55.0, Unit.PX, 90.0, Unit.PX);
		layoutPanel.setWidgetTopHeight(passwordLabel, 358.0, Unit.PX, 18.0, Unit.PX);
		
		simpleCheckBox = new SimpleCheckBox();
		layoutPanel.add(simpleCheckBox);
		
		
		layoutPanel.setWidgetLeftWidth(simpleCheckBox, 86.0, Unit.PX, 20.0, Unit.PX);
		layoutPanel.setWidgetTopHeight(simpleCheckBox, 476.0, Unit.PX, 19.0, Unit.PX);
		
		if (simpleCheckBox.getValue() == true) {
			GWT.log("Checkbox is checked");
			System.out.println("Hi");
		
		}
		
		InlineLabel rememberMeNextLabel = new InlineLabel("Remember me next time.");
		layoutPanel.add(rememberMeNextLabel);
		layoutPanel.setWidgetLeftWidth(rememberMeNextLabel, 151.0, Unit.PX, 173.0, Unit.PX);
		layoutPanel.setWidgetTopHeight(rememberMeNextLabel, 476.0, Unit.PX, 18.0, Unit.PX);
		
		Button loginNewButton = new Button("New button");
		loginNewButton.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				//send username and password to server and validate login
				
				handleLogin();
				
			}		
		});
		
		
		
		loginNewButton.setText("Login!");
		layoutPanel.add(loginNewButton);
		layoutPanel.setWidgetLeftWidth(loginNewButton, 164.0, Unit.PX, 81.0, Unit.PX);
		layoutPanel.setWidgetTopHeight(loginNewButton, 424.0, Unit.PX, 30.0, Unit.PX);
		
		errorLabel = new Label("");
		layoutPanel.add(errorLabel);
		layoutPanel.setWidgetLeftWidth(errorLabel, 120.0, Unit.PX, 307.0, Unit.PX);
		layoutPanel.setWidgetTopHeight(errorLabel, 259.0, Unit.PX, 18.0, Unit.PX);
		
		Label lblNewUserClick = new Label("New user? Click here to register!");
		layoutPanel.add(lblNewUserClick);
		layoutPanel.setWidgetLeftWidth(lblNewUserClick, 151.0, Unit.PX, 193.0, Unit.PX);
		layoutPanel.setWidgetTopHeight(lblNewUserClick, 500.0, Unit.PX, 18.0, Unit.PX);
		
		passwordLoginTextBox = new PasswordTextBox();
		layoutPanel.add(passwordLoginTextBox);
		layoutPanel.setWidgetLeftWidth(passwordLoginTextBox, 151.0, Unit.PX, 173.0, Unit.PX);
		layoutPanel.setWidgetTopHeight(passwordLoginTextBox, 344.0, Unit.PX, 34.0, Unit.PX);
		
		h_gem = HeatGem.getImage("fireRuby1.gif");
		layoutPanel.add(h_gem);
		layoutPanel.setWidgetLeftWidth(h_gem, 66.0, Unit.PX, 260.0, Unit.PX);
		layoutPanel.setWidgetTopHeight(h_gem, 15.0, Unit.PX, 245.0, Unit.PX);
		
		lblNewUserClick.addMouseMoveHandler(new MouseMoveHandler() {
			public void onMouseMove(MouseMoveEvent event) {
				//change color of text for when mouse hovers over link
			}
		});
		lblNewUserClick.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				handleRegister();
			}
			
		});
		
	}
	
	protected void handleRegister() {
		RegisterView view = new RegisterView();
		HeatGem.setView(view);
	}

	protected void handleLogin() {
		String username = this.usernameTextBox.getText();
		String password = this.passwordLoginTextBox.getText();
		
		RPC.userService.logIn(username, password, new AsyncCallback<User>() {
			@Override
			public void onFailure(Throwable caught) {
				// show error message
				errorLabel.setText("Could not communicate with server?");
			}

			@Override
			public void onSuccess(User result) {
				if (result == null) {
					// show error message
					errorLabel.setText("Unknown username/password");
				} else {
					// TODO: switch to home page
					errorLabel.setText("Success! Should switch to home page");
					HomePage view = new HomePage();
					view.setUser(result);
					HeatGem.setView(view);
					view.activate(); // do any required dynamic initialization
				}
			}
		});
	}
}
// hii