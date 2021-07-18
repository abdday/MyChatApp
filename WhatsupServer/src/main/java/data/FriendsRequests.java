package data;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "FriendsRequests")
@XmlAccessorType(XmlAccessType.NONE)
public class FriendsRequests {

	@XmlElement(name = "list")
	private List<String> list;

	public FriendsRequests() {
		list = new ArrayList<>();
	}

	public FriendsRequests(List<String> list) {
		this.list = list;
	}

	public List<String> getList() {
		return list;
	}

}
