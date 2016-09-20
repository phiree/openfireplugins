package org.jivesoftware.openfire.domain;

public class User {
	
	private String userID;
	private String status;
	private String ipaddress;
	public User() {
		
	}
	public User(String userID, String status, String ipaddress) {
		super();
		this.userID = userID;
		this.status = status;
		this.ipaddress = ipaddress;
	}
	public String getUserID() {
		return userID;
	}
	public void setUserID(String userID) {
		this.userID = userID;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getIpaddress() {
		return ipaddress;
	}
	public void setIpaddress(String ipaddress) {
		this.ipaddress = ipaddress;
	}
	
	
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((userID == null) ? 0 : userID.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		User other = (User) obj;
		if (userID == null) {
			if (other.userID != null)
				return false;
		} else if (!userID.equals(other.userID))
			return false;
		return true;
	}
	@Override
	public String toString() {
		return "User [userID=" + userID + ", status=" + status + ", ipaddress="
				+ ipaddress + "]";
	}
	
	/*
	public static void main(String[] args) {
		User u=new User("112","12","2");
		User us=new User("112","162","2");
		User us1=new User("112","152","2");
	    HashSet<User> userLineHs = new HashSet<User>();//保存用户发送
	    userLineHs.add(u);
	    userLineHs.add(us);
	    userLineHs.add(us1);
	    for(User user:userLineHs){
	    	System.out.println(user.equals(us));
	    }
	    System.out.println(userLineHs.size());
		
	}
	*/
	


}
