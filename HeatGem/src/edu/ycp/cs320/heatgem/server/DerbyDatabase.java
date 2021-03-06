package edu.ycp.cs320.heatgem.server;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;

import edu.ycp.cs320.heatgem.shared.User;
import edu.ycp.cs320.heatgem.shared.UserProfile;
import edu.ycp.cs320.heatgem.server.DB;
import edu.ycp.cs320.heatgem.server.ITransaction;


public class DerbyDatabase implements IDatabase {
	private static final String DATASTORE = "H:/heatgemdb";
	
	static {
		try {
			Class.forName("org.apache.derby.jdbc.EmbeddedDriver");
		} catch (Exception e) {
			throw new RuntimeException("Could not load Derby JDBC driver");
		}
	}
	
	private class DatabaseConnection {
		public Connection conn;
		public int refCount;
	}
	
	private final ThreadLocal<DatabaseConnection> connHolder = new ThreadLocal<DatabaseConnection>();
	
	private DatabaseConnection getConnection() throws SQLException {
		DatabaseConnection dbConn = connHolder.get();
		if (dbConn == null) {
			dbConn = new DatabaseConnection();
			dbConn.conn = DriverManager.getConnection("jdbc:derby:" + DATASTORE + ";create=true");
			dbConn.refCount = 0;
			connHolder.set(dbConn);
		}
		dbConn.refCount++;
		return dbConn;
	}
	
	private void releaseConnection(DatabaseConnection dbConn) throws SQLException {
		dbConn.refCount--;
		if (dbConn.refCount == 0) {
			try {
				dbConn.conn.close();
			} finally {
				connHolder.set(null);
			}
		}
	}
	
	private<E> E databaseRun(ITransaction<E> transaction) throws SQLException {
		// FIXME: retry if transaction times out due to deadlock
		
		DatabaseConnection dbConn = getConnection();
		
		try {
			boolean origAutoCommit = dbConn.conn.getAutoCommit();
			try {
				dbConn.conn.setAutoCommit(false);

				E result = transaction.run(dbConn.conn);
				dbConn.conn.commit();
				return result;
			} finally {
				dbConn.conn.setAutoCommit(origAutoCommit);
			}
		} finally {
			releaseConnection(dbConn);
		}
	}
	
	void createTables() throws SQLException {
		databaseRun(new ITransaction<Boolean>() {
			@Override
			public Boolean run(Connection conn) throws SQLException {
				PreparedStatement stmt = null;
				
				try {
					stmt = conn.prepareStatement(
							"create table users (" +
							"  id INTEGER NOT NULL GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1), " +
							"  username VARCHAR(50) NOT NULL, " +
							"  password VARCHAR(50) NOT NULL, " +
							"  highscore INT, " + 
							"  email VARCHAR(50) NOT NULL, " + 
							"  exp INT, " +				//unused
							"  level INT NOT NULL, " +	//unused
							"  losses INT, " + 
							"  wins INT " +
							")"
					);
					
					stmt.executeUpdate();
				} finally {
					DB.closeQuietly(stmt);
				}
				
				return true;
			}
		});
	}

	@Override
	public User logIn(final String username, final String password) throws SQLException {
		return databaseRun(new ITransaction<User> () {
			@Override
			public User run(Connection conn) throws SQLException {
				PreparedStatement stmt = null;
				ResultSet resultSet = null;
				User result = new User();
				
				try {
					stmt = conn.prepareStatement("select * from users where username = ? and password = ?");
					stmt.setString(1, username);
					stmt.setString(2, password);
					resultSet = stmt.executeQuery();
					
					if (!resultSet.next()) {
						// no such user/password
						return null;
					}
					
					result.setId(resultSet.getInt(1));
					result.setUsername(resultSet.getString(2));
					result.setPassword(resultSet.getString(3));
					result.setHighScore(resultSet.getInt(4));
					result.setEmail(resultSet.getString(5));
					result.setExperience(resultSet.getInt(6));
					result.setLevel(resultSet.getInt(7));
					result.setLosses(resultSet.getInt(8));
					result.setWins(resultSet.getInt(9));
					
					return result;
					
				} finally {
					DB.closeQuietly(stmt);
					DB.closeQuietly(resultSet);
				}
			}
		});
	}

	@Override
	public boolean addUser(final String username, final String password,
			final String confirmPassword, final String email) throws SQLException {

		return  databaseRun(new ITransaction<Boolean>() {
				@Override
				public Boolean run(Connection conn) throws SQLException {
					PreparedStatement stmt = null;
					PreparedStatement stmt2 = null;
					ResultSet resultSet = null;
					
					try {
						stmt = conn.prepareStatement("select * from users where username = ?");
						stmt.setString(1, username);
						resultSet = stmt.executeQuery();
						
						if (!resultSet.next()) {
							//no such user
							stmt = conn.prepareStatement("select * from users where email = ?");
							stmt.setString(1, email);
							resultSet = stmt.executeQuery();
							
							if (!resultSet.next()) {
								// no such user OR email
								System.out.println("Unique user, add to DB!");
								stmt2 = conn.prepareStatement(
										"insert into users(username, password, highscore, email, exp, level, losses, wins) " +
										"values (?, ?, 0, ?, 0, 1, 0, 0)"
								);
	
								stmt2.setString(1, username);
								stmt2.setString(2, password);
								stmt2.setString(3, email);
								stmt2.executeUpdate();
	
								return true;
							} else {
								System.out.println("Email already exists!");
								
								return false;
							}
						
						} else {
							System.out.println("Username already exists!");

							return false;
						}
					} finally {
						DB.closeQuietly(stmt);
						DB.closeQuietly(stmt2);
						DB.closeQuietly(resultSet);
					}
				}
			});
	}

	@Override
	public UserProfile getUserProfile(final String username) {
		// TODO Auto-generated method stub
			try {
				return databaseRun(new ITransaction<UserProfile>() {
					@Override
					public UserProfile run(Connection conn) throws SQLException {
						PreparedStatement stmt = null;
						ResultSet resultSet = null;
						UserProfile result = new UserProfile();
						
						try {
							stmt = conn.prepareStatement("select * from users where username = ?");
							stmt.setString(1, username);
							resultSet = stmt.executeQuery();
							
							if (!resultSet.next()) {
								// no such user
								return null;
							}
							
							result.setUserId(resultSet.getInt(1));
							result.setName(resultSet.getString(2));
							result.setHighScore(resultSet.getInt(4));
							result.setEmail(resultSet.getString(5));
							result.setExperience(resultSet.getInt(6));
							result.setLevel(resultSet.getInt(7));
							result.setLosses(resultSet.getInt(8));
							result.setWins(resultSet.getInt(9));
							
							return result;
							
						} finally {
							DB.closeQuietly(stmt);
							DB.closeQuietly(resultSet);
						}
					}
				});
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return null;
	}

	@Override
	public boolean updateUserProfile(final String username, final UserProfile updatedProfile) throws SQLException {
		// TODO Auto-generated method stub
		
		return databaseRun(new ITransaction<Boolean>() {
				@Override
				public Boolean run(Connection conn) throws SQLException {
					PreparedStatement stmt = null;
					
					try {
						stmt = conn.prepareStatement(
								"UPDATE users " +
								"SET highscore=?, exp=?, level=?, losses=?, wins=? " + 
								"WHERE username = ?"
						);
						
						stmt.setInt(1, updatedProfile.getHighScore());
						stmt.setInt(2, updatedProfile.getExperience());
						stmt.setInt(3, updatedProfile.getLevel());
						stmt.setInt(4, updatedProfile.getLosses());
						stmt.setInt(5, updatedProfile.getWins());
						stmt.setString(6, username);
						stmt.executeUpdate();
						
						return true;
						
					} finally {
						DB.closeQuietly(stmt);
					}
				}
			});
	}

	@Override
	public UserProfile findUserProfileByUserId(final int id) throws SQLException  {
			return databaseRun(new ITransaction<UserProfile>() {
				@Override
				public UserProfile run(Connection conn) throws SQLException {
					PreparedStatement stmt = null;
					ResultSet resultSet = null;
					UserProfile result = new UserProfile();
					
					try {
						stmt = conn.prepareStatement("select * from users where id = ?");
						stmt.setInt(1, id);
						resultSet = stmt.executeQuery();
						
						if (!resultSet.next()) {
							// no such user
							return null;
						}
						
						result.setUserId(resultSet.getInt(1));
						result.setName(resultSet.getString(2));
						result.setHighScore(resultSet.getInt(4));
						result.setEmail(resultSet.getString(5));
						result.setExperience(resultSet.getInt(6));
						result.setLevel(resultSet.getInt(7));
						result.setLosses(resultSet.getInt(8));
						result.setWins(resultSet.getInt(9));
						
						return result;
						
					} finally {
						DB.closeQuietly(stmt);
						DB.closeQuietly(resultSet);
					}
				}
			});
	}

	@Override
	public Integer getAmountUsers() throws SQLException {
		return databaseRun(new ITransaction<Integer> () {
			@Override
			public Integer run(Connection conn) throws SQLException {
				PreparedStatement stmt = null;
				ResultSet resultSet = null;
				Integer number = 0;
				
				try {
					stmt = conn.prepareStatement("select * from users");
					resultSet = stmt.executeQuery();
					
					while(resultSet.next()) {
						number++;
					}
				} finally {
					DB.closeQuietly(stmt);
					DB.closeQuietly(resultSet);
				}
				return number;
			}
		});
	}

	@Override
	public boolean deleteUser(final String username) {
		// delete user - deletes row in SQL database
		try {
			databaseRun(new ITransaction<Boolean>() {
				@Override
				public Boolean run(Connection conn) throws SQLException {
					PreparedStatement stmt = null;
					
					try {
						stmt = conn.prepareStatement(
								"DELETE FROM users " +
								"WHERE username=?"
						);
						stmt.setString(1, username);
						
						stmt.executeUpdate();
						
						return true;
					} finally {
						DB.closeQuietly(stmt);
					}
				}
			});
		} catch (SQLException e) {
			throw new RuntimeException("SQLException deleting user", e);
		}
		
		return false;
	}

	@Override
	public UserProfile[] updateLeaderboard() throws SQLException {
		// TODO Auto-generated method stub
		return databaseRun(new ITransaction<UserProfile[]>() {
			@Override
			public UserProfile[] run(Connection conn) throws SQLException {
				PreparedStatement stmt = null;
				ResultSet resultSet = null;
				
				try {
					ArrayList<UserProfile> results = new ArrayList<UserProfile>();
					stmt = conn.prepareStatement("select * from users");
					resultSet = stmt.executeQuery();
										
					while(resultSet.next()) {
						UserProfile temp = new UserProfile();
						temp.setUserId(resultSet.getInt(1));
						temp.setName(resultSet.getString(2));
						temp.setHighScore(resultSet.getInt(4));
						temp.setEmail(resultSet.getString(5));
						temp.setExperience(resultSet.getInt(6));
						temp.setLevel(resultSet.getInt(7));
						temp.setLosses(resultSet.getInt(8));
						temp.setWins(resultSet.getInt(9));
						
						results.add(temp);
					}
					
					return results.toArray(new UserProfile[results.size()]);
				} finally {
					DB.closeQuietly(stmt);
					DB.closeQuietly(resultSet);
				}
			}
		});
	}
}
