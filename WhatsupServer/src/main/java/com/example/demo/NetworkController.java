package com.example.demo;

import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import data.FriendsRequests;
import data.Message;
import data.MessagesThread;
import data.ResponseEnvelope;
import data.User;
import serverController.SController;

@RestController
@RequestMapping(value = "/whatsapp")
public class NetworkController {

	private SController controller = new SController();

	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String sayHello() {

		return "hello";
	}

	@RequestMapping(value = "/register/{username}/{name}", method = RequestMethod.POST)
	@ResponseBody
	ResponseEnvelope<User> registerNewUser(@PathVariable String username, @PathVariable String name) {

		System.out.println("aufgerufen register new user form server");
		return controller.registerNewUser(username, name);
	}

	@RequestMapping(value = "/addFriend/{userID}/{friendUserName}", method = RequestMethod.GET)
	@ResponseBody
	ResponseEnvelope<User> addFriend(@PathVariable String userID, @PathVariable String friendUserName) {
		System.out.println("add Friend in server aufgerufen parameter:+  " + userID + " " + friendUserName);
		return controller.addFriend(userID, friendUserName);
	}

	@RequestMapping(value = "/getUser/{userID}", method = RequestMethod.GET)
	@ResponseBody
	ResponseEnvelope<User> getUser(@PathVariable String userID) {
		return controller.getUserbyID(userID);
	}

	@RequestMapping(value = "/friendshipRequests/{userID}", method = RequestMethod.GET)
	@ResponseBody
	ResponseEnvelope<FriendsRequests> friendshipRequesting(@PathVariable String userID) {

		return controller.listOfFriendshipRequests(userID);

	}

	@RequestMapping(value = "/chatExsist/{userID}/{friendUserName}", method = RequestMethod.GET)
	@ResponseBody
	ResponseEnvelope chatExsist(@PathVariable String userID, @PathVariable String friendUserName) {

		return controller.CheckChatExsist(userID, friendUserName);

	}

	@RequestMapping(value = "/sendMessage/{userID}/{friendUserName}", method = RequestMethod.POST)
	@ResponseBody
	ResponseEnvelope sendMessage(@PathVariable String userID, @PathVariable String friendUserName,
			@Validated @RequestBody Message m) {

		System.out.println("new Messgae sent huuu friend: " + friendUserName + "data is :" + m.getData());

		return controller.newMessageSent(userID, friendUserName, m);

	}

	@RequestMapping(value = "/requestMessages/{userID}/{friendUserName}", method = RequestMethod.GET)
	@ResponseBody
	ResponseEnvelope<MessagesThread> requestingMessges(@PathVariable String userID,
			@PathVariable String friendUserName) {
		return controller.getMessages(userID, friendUserName);

	}

}
