package edu.ycp.cs320.heatgem.server;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;

import edu.ycp.cs320.heatgem.client.UserService;
import edu.ycp.cs320.heatgem.shared.User;
import edu.ycp.cs320.heatgem.shared.UserProfile;

public class UserServiceImpl extends RemoteServiceServlet implements UserService {
	private static final long serialVersionUID = 1L;

	@Override
	public User logIn(String username, String password) {
		return DB.instance().logIn(username, password);
	}
	
	@Override
	public void addUser(String username, String password, String confirmPassword, String email){
		DB.instance().addUser(username, password, confirmPassword, email);
		
	}
	
//	@Override
//	public void updateBoard() {
//		
//	}
	
	@Override
	public UserProfile leaderBoard(String username, int level, int experience,
			int losses, int wins, int highScore) {
		// TODO Auto-generated method stub
		return null;
	}

	public UserServiceImpl() {
		// TODO Auto-generated constructor stub
	}
	
	
	@Override
	public void deleteUserAccount(String username) {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public UserProfile getUserProfile(String username, String password, String email, int level, int experience, int losses, int wins, int highScore) {
		return DB.instance().getUserProfile(username);
	}
	
	@Override
	public Boolean updateUserProfile(String username, UserProfile updatedProfile) {
		return DB.instance().updateUserProfile(username, updatedProfile);
	}
}
