package appController;

import java.util.List;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;

import data.Message;
import data.MessagesThread;
import data.ResponseEnvelope;
import data.User;
import myExceptions.ServerException;
import reactor.core.publisher.Mono;

public class Network {

	private WebClient webbase;
	private AppController controller;
	private String myID;

	public Network(AppController controller) {
		webbase = WebClient.builder().baseUrl("http://localhost:8080/whatsapp")
				.defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_XML_VALUE)
				.defaultHeader(HttpHeaders.ACCEPT, MediaType.APPLICATION_XML_VALUE).build();
		this.controller = controller;
		this.myID = "UnknownYet";
	}

	public User registerMe(String username, String name) throws ServerException, Exception {

		Mono<ResponseEnvelope> webAccess = webbase.method(HttpMethod.POST).uri("/register/" + username + "/" + name)
				.retrieve().bodyToMono(ResponseEnvelope.class);

		ResponseEnvelope<User> res = webAccess.block();

		if (res.getState() != EMyStatus.OK) {
			throw new ServerException(res.getExceptionName() + ": " + res.getExceptionMessage());
		}

		myID = res.getData().get().getID();

		return res.getData().get();

	}

	public User addFriend(String friendUsername) throws ServerException {

		Mono<ResponseEnvelope> webAccess = webbase.method(HttpMethod.GET)
				.uri("/addFriend/" + myID + "/" + friendUsername).retrieve().bodyToMono(ResponseEnvelope.class);

		ResponseEnvelope<User> res = webAccess.block();

		if (res.getState() != EMyStatus.OK) {
			throw new ServerException(res.getExceptionName() + ": " + res.getExceptionMessage());
		}

		return res.getData().get();

	}

	public List<String> getFriendsRequests() throws ServerException {

		Mono<ResponseEnvelope> webAccess = webbase.method(HttpMethod.GET).uri("/friendshipRequests/" + myID).retrieve()
				.bodyToMono(ResponseEnvelope.class);

		System.out.println("Before block\n\n\n");

		ResponseEnvelope<FriendsRequests> resList = webAccess.block();

		System.out.println("Before block\n\n\n");

		if (resList.getState() != EMyStatus.OK) {
			throw new ServerException(resList.getExceptionName() + ": " + resList.getExceptionMessage());
		}
		return resList.getData().get().getList();
	}

	// chatExsist/{userID}/{friendUserName}

	public boolean IsChatAllowed(String firendUsername) throws ServerException {

		Mono<ResponseEnvelope> webAccess = webbase.method(HttpMethod.GET)
				.uri("/chatExsist/" + myID + "/" + firendUsername).retrieve().bodyToMono(ResponseEnvelope.class);

		ResponseEnvelope resList = webAccess.block();

		if (resList.getState() != EMyStatus.OK) {
			throw new ServerException(resList.getExceptionName() + ": " + resList.getExceptionMessage());
		}
		return true;
	}

	// /sendMessage/{userID}/{friendUserName}

	public void sendMessage(String friendUsername, Message m) throws ServerException {
		Mono<ResponseEnvelope> webAccess = webbase.method(HttpMethod.POST)
				.uri("/sendMessage/" + myID + "/" + friendUsername).body(BodyInserters.fromValue(m)).retrieve()
				.bodyToMono(ResponseEnvelope.class);

		ResponseEnvelope res = webAccess.block();
		if (res.getState() != EMyStatus.OK) {
			throw new ServerException(res.getExceptionName() + ": " + res.getExceptionMessage());
		}

	}

	// /requestMessages/{userID}/{friendUserName}

	public List<Message> requestMessage(String friendUsername) throws ServerException {

		WebClient newWebbase = WebClient.builder().baseUrl("http://localhost:8080/whatsapp")
				.defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_XML_VALUE)
				.defaultHeader(HttpHeaders.ACCEPT, MediaType.APPLICATION_XML_VALUE).build();

		Mono<ResponseEnvelope> webAccess = newWebbase.method(HttpMethod.GET)
				.uri("/requestMessages/" + myID + "/" + friendUsername).retrieve().bodyToMono(ResponseEnvelope.class);

		ResponseEnvelope<MessagesThread> res = webAccess.block();
		if (res.getState() != EMyStatus.OK) {
			throw new ServerException(res.getExceptionName() + ": " + res.getExceptionMessage());
		}

		return res.getData().get().getMessages();

	}

}

//
//Mono<ResponseEnvelope> webAccess = baseWC.method(HttpMethod.POST).uri("/" + gameId + "/moves")
//		.body(BodyInserters.fromValue(move)).retrieve().bodyToMono(ResponseEnvelope.class);
