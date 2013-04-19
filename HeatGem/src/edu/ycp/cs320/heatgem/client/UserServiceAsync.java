package edu.ycp.cs320.heatgem.client;

import com.google.gwt.user.client.rpc.AsyncCallback;

import edu.ycp.cs320.heatgem.shared.User;

public interface UserServiceAsync {

	void logIn(String username, String password, AsyncCallback<User> callback);

	void addUser(String username, String password, String confirmPassword,
			String email, AsyncCallback<Void> callback);
	
	//void updateBoard();

}
