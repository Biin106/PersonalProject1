package console.academy;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;
import java.util.Vector;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import common.utils.CommonUtils;

public class AddressLogic {
	//[멤버변수]
	List<Person> person;
	//[생성자]
	public AddressLogic() {
		person = new Vector<>();
	}////////	
	//[멤버 메소드]
	
	 // 1]메뉴 출력용 메소드
	public void printMainMenu() {
		System.out.println("====================주소록 메뉴====================");
		System.out.println(" 1.입력 2.출력 3.수정 4.삭제 5.검색 9.종료");
		System.out.println("===============================================");
		System.out.println("메뉴 번호를 입력하세요?");
	}//////////////printMainMenu()
	
	 //2]메뉴 번호 입력용 메소드
	public int getMenuNumber() {
		Scanner sc = new Scanner(System.in);
		int menu = -1;
		while(true) {
			try {
				String menuStr=sc.nextLine().trim();
				menu=Integer.parseInt(menuStr);
				break;
			}
			catch(Exception e) {
				System.out.println("숫자만 입력하세요");			
			}
			
		}////while		
		return menu;
	}/////////////////getMenuNumber()
	
	 // 3]메뉴 번호에 따른 분기용 메소드
	public void seperateMainMenu(int mainMenu) {
		switch(mainMenu) {
			case 1://입력
				setPerson();
				break;
			case 2://출력
				printPersonByInitial();
				break;
			case 3://수정
				updatePerson();
				break;
			case 4://삭제
				deletePerson();
				break;
			case 5://검색
				searchPerson();
				break;
			case 9://종료
				System.out.println("프로그램을 종료합니다");
				System.exit(0);
			default:System.out.println("메뉴에 없는 번호입니다");
		}///switch
	}////////////////////seperateMainMenu(int mainMenu)	
	
	public void setPerson() {
		
		Scanner sc = new Scanner(System.in);
		System.out.println(">>>이름을 입력하세요?");
		String name = sc.nextLine().trim();
		System.out.println(">>>나이를 입력하세요?");
		int age= -1;
		while(true) {
			try {
				age = Integer.parseInt(sc.nextLine().trim());
				break;
			}
			catch(NumberFormatException e) {
				System.out.println("나이는 숫자로만 입력하세요");
			}
		}
		System.out.println(">>사는곳을 입력하세요");
		String place = sc.nextLine().trim();
		System.out.println(">>>연락처를 입력하세요");
		String PhoneNumber = sc.nextLine().trim();
		person.add(new Person(name, age, place, PhoneNumber));
		System.out.println("입력되었습니다\r\n");
	}/////////////setPerson()
	
	private void printPersonByInitial() {
	    Map<Character, List<Person>> personMap = new HashMap<>();
	    
	    // 이름의 초성에 따라 사람을 그룹화합니다.
	    for (Person p : person) {
	        char initialConsonant = CommonUtils.getInitialConsonant(p.name);
	        List<Person> group = personMap.getOrDefault(initialConsonant, new ArrayList<>());
	        group.add(p);
	        personMap.put(initialConsonant, group);
	    }
	    
	    // 초성별로 그룹화된 사람들을 출력합니다.
	    boolean isEmpty = true; // 명단이 비어있는지 여부를 확인하기 위한 플래그
	    for (char consonant = 'ㄱ'; consonant <= 'ㅎ'; consonant++) {
	        List<Person> group = personMap.get(consonant);
	        if (group != null && !group.isEmpty()) {
	            isEmpty = false; // 비어있지 않은 그룹이 존재하면 플래그를 false로 변경
	            System.out.println("[" + consonant + " 으로 시작하는 명단]");
	            for (Person p : group) {
	                p.print();
	            }
	        }
	    }
	    
	    // 명단이 비어있을 때 메시지 출력
	    if (isEmpty) {
	        System.out.println("출력할 명단이 없습니다");
	    }
	}
	
	//7]검색용 메소드
	private void searchPerson() {
		Person findPerson=findPersonByName("검색");
		if(findPerson !=null) {
			System.out.println(String.format("[%s로 검색한 결과]", findPerson.name));
			findPerson.print();
		}
	}/////searchPerson()
	
	public Person findPersonByName(String message) {
		
		System.out.println(message+"할 사람의 이름을 입력하세요?");
		Scanner sc = new Scanner(System.in);
		String name = sc.nextLine().trim();
		
		for(Person p:person)
			if(p.name.equals(name))
				return p;
		System.out.println(name+"로(으로) 검색된 정보가 없어요");
		return null;	
	}/////findPersonByName(String message)
	
	//9]수정용 메소드
	private void updatePerson() {
		Person findPerson=findPersonByName("수정");
		if(findPerson !=null) {
			Scanner sc = new Scanner(System.in);
			System.out.printf("(현재 나이 %s) 몇 살로 수정하시겠습니까?%n",findPerson.age);
			while(true) {
				try {
					findPerson.age=Integer.parseInt(sc.nextLine().trim());
					break;
				}
				catch(Exception e) {
					System.out.println("나이는 숫자만 입력하세요");
				}
			}///while
			System.out.printf("(현재 주소 %s) 어느 주소로 수정하시겠습니까?%n",findPerson.place);
			findPerson.place=sc.nextLine().trim();
			System.out.printf("(현재 연락처 %s) 어떤 번호로 수정하시겠습니까?%n",findPerson.PhoneNumber);
			findPerson.PhoneNumber=sc.nextLine().trim();
			
			
			System.out.printf("[%s가(이) 아래와 같이 수정되었습니다]%n",findPerson.name);
			findPerson.print();//수정 내용을 확인하기 위한 출력
		}
		
	}/////updatePerson()
	
	//10]삭제용
	private void deletePerson() {
		Person findPerson=findPersonByName("삭제");
		if(findPerson !=null) {
			for(Person p:person)
				if(findPerson.equals(p)) {
					person.remove(p);
					System.out.printf("[%s가(이) 삭제 되었습니다]%n",findPerson.name);
					break;
				}
		}
	}/////deletePerson()
	
	
}/////class
