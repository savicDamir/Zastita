package LOGIC;

public class User {

	public User() {
		//za pocetak treba da ucitam sve kljuceve koje imam to radim kad me napravi u mejnu
		System.out.println("User");
	}
	
	public void generateNewKey(String name, String email, String type, String password){
		//
		System.out.println(name+" "+email+" "+type+" "+password);
	}
}
