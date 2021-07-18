package appController;

public class Friend {

	private String ID, username, name;

	public String getID() {
		return ID;
	}

	public String getUsername() {
		return username;
	}

	public String getName() {
		return name;
	}

	@Override
	public String toString() {
		return "Friend [ID=" + ID + ", username=" + username + ", name=" + name + "]";
	}

	public Friend(String iD, String username, String name) {
		super();
		ID = iD;
		this.username = username;
		this.name = name;
	}

}
