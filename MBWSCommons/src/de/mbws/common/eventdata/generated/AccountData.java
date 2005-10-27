/** Generated class. Do not change !!! **/
package de.mbws.common.eventdata.generated;

import java.nio.ByteBuffer;

import de.mbws.common.eventdata.AbstractEventData;

public class AccountData extends AbstractEventData { 
	private String userName;
	private String password;
	private String newPassword;
	private int age;
	private String emailAddress;


	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	} 

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	} 

	public String getNewPassword() {
		return newPassword;
	}

	public void setNewPassword(String newPassword) {
		this.newPassword = newPassword;
	} 

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	} 

	public String getEmailAddress() {
		return emailAddress;
	}

	public void setEmailAddress(String emailAddress) {
		this.emailAddress = emailAddress;
	} 


	public void deserialize(ByteBuffer payload) {
		userName = readString(payload);
		password = readString(payload);
		newPassword = readString(payload);
		age = payload.getInt();
		emailAddress = readString(payload);
	}

	public int serialize(ByteBuffer payload) {
		writeString(payload, userName);
		writeString(payload, password);
		writeString(payload, newPassword);
		payload.putInt(age);
		writeString(payload, emailAddress);
		return payload.position();
	}
}