package console.academy;

import java.io.Serializable;

public class Person implements Serializable {
	//필드
	public  String name;
	public  int age;
	public  String place;
	public  String PhoneNumber;
	
	//[기본 생성자]
	public Person() {}
	//[인자 생성자]
	public Person(String name, int age, String place, String PhoneNumber) {		
		this.name = name;
		this.age = age;
		this.place = place;
		this.PhoneNumber = PhoneNumber;
	}/////
	//[멤버 메소드]
	String get() {
		return String.format("이름:%s,나이:%s,사는 곳:%s,연락처:%s",name,age,place,PhoneNumber);
	}//////
	void print() {
		System.out.println(get());
	}////////////////////////////
	
	
	
}
