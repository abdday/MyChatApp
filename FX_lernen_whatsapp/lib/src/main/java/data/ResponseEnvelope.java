package data;

import java.util.Optional;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSeeAlso;

import appController.EMyStatus;
import appController.FriendsRequests;

@XmlRootElement(name = "responseEnvelope")
@XmlAccessorType(XmlAccessType.NONE)
//@XmlSeeAlso({ UniquePlayerIdentifier.class, GameState.class, ImpState.class, RawPlayers.class, RawGameList.class })
@XmlSeeAlso({ User.class, FriendsRequests.class, Message.class, MessagesThread.class })
public final class ResponseEnvelope<T> {

	/**
	 * Default error information, use the state data element to determine if errors
	 * have occurred or not!
	 */
	private final String defaultErrorMessage = "No error information available. Potentially, everything worked just fine.";

	/** If an exception was fired this field holds the name of the exception. */
	@XmlElement(name = "exceptionName")
	private final String exceptionName;

	/**
	 * If an exception was fired this field holds the exception message which
	 * provides additional details on the observed error.
	 */
	@XmlElement(name = "exceptionMessage")
	private final String exceptionMessage;

	/**
	 * The error state of this envelope. Basically the last command sent from the
	 * client to the server either was defined/executed fine or an error could have
	 * occurred (e.g., because a business rule was violated). This field represents
	 * one of these two states.
	 */
	@XmlElement(name = "state", required = true)
	private final EMyStatus state;

	/** The data which the server would like to send to the client. */
	@XmlElement(name = "data")
	private final T data;

	/**
	 * Instantiates a new response envelope. Mainly used for the automatic
	 * object/xml mapping. By creating ResponseEnvelope while using this ctor the
	 * data element will be set to null and the state will be set to
	 * {@link ERequestState#Okay}, i.e., the ctor assumes that no errors were
	 * observed.
	 */
	public ResponseEnvelope() {
		state = EMyStatus.OK;
		this.data = null;

		exceptionMessage = defaultErrorMessage;
		exceptionName = defaultErrorMessage;
	}

	/**
	 * Instantiates a new response envelope object to send some data to the client.
	 * This ctor assumes that no errors were observed and sets the state
	 * accordingly. Will set the state to {@link ERequestState#Okay}.
	 *
	 * @param data The data which the server wants to send to the client, must not
	 *             be null.
	 */
	public ResponseEnvelope(T data) {
		state = EMyStatus.OK;

		if (data == null)
			throw new NullPointerException("Data must not be null, use the default ctor without arguments"
					+ " if you would like to send an empty response without data.");

		this.data = data;

		exceptionMessage = defaultErrorMessage;
		exceptionName = defaultErrorMessage;
	}

	/**
	 * Instantiates a new response envelope. This ctor assumes that something went
	 * wrong and sets the state accordingly. Will set the state to
	 * {@link ERequestState#Error}.
	 *
	 * @param e An exception which is used to fill the fields in this envelope
	 *          object (e.g., the exception name). Must not be null.
	 */
	public ResponseEnvelope(Exception e) {
		this(e.getClass().getName(), e.getMessage());
		if (e == null)
			throw new NullPointerException(
					"Exception should not be null, use this ctor only if you want to report errors!");

	}

	/**
	 * Instantiates a new response envelope. This ctor assumes that something went
	 * wrong. Use it when custom error messages should be sent to the client which
	 * are not related to an exception object. Will set the state to
	 * {@link ERequestState#Error}.
	 *
	 * @param exceptionName    the exception name which should give a rough summary
	 *                         of the error/issue which should be reported by this
	 *                         envelope object. Must not be null nor empty.
	 * @param exceptionMessage the exception message which should give more details
	 *                         on the error/issue which should be reported by this
	 *                         envelope object. Must not be null nor empty.
	 */
	public ResponseEnvelope(String exceptionName, String exceptionMessage) {
		state = EMyStatus.LOGIC_FAILURE;
		state.setMessage(exceptionName + ": " + exceptionMessage);
		this.data = null;

		this.exceptionMessage = exceptionMessage;
		this.exceptionName = exceptionName;
	}

	/**
	 * Gets the exception name. Does only contain information on errors if an error
	 * has occurred when processing the most recent client command. Otherwise it can
	 * contain arbitrary information, such as, the default message or no information
	 * at all. Use the response state to check if errors have occurred.
	 *
	 * @return the exception name
	 */
	public String getExceptionName() {
		return exceptionName;
	}

	/**
	 * Gets the exception message. Does only contain information on errors if an
	 * error has occurred when processing the most recent client command. Otherwise
	 * it can contain arbitrary information, such as, the default message or no
	 * information at all. Use the response state to check if errors have occurred.
	 * 
	 * @return the exception message
	 */
	public String getExceptionMessage() {
		return exceptionMessage;
	}

	/**
	 * Gets the state. It indicates if any errors have occurred while processing the
	 * most recent client command or if everything worked just fine.
	 *
	 * @return the state of this response envelope
	 */
	public EMyStatus getState() {
		return state;
	}

	/**
	 * Gets the data, e.g., a game state object.
	 *
	 * @return the data (i.e., payload) which the server wants to send to the
	 *         client.
	 */
	public Optional<T> getData() {
		return Optional.ofNullable(data);
	}
}
