package com.TelecomProject;


import org.testng.annotations.Test;



import io.restassured.RestAssured;
import io.restassured.response.Response;

import static io.restassured.RestAssured.*;
import static io.restassured.matcher.RestAssuredMatchers.*;
import static org.hamcrest.Matchers.*;
public class TelecomAPI 
{
	 String tokenvalue;
	 String logintoken;
	 String id;
	 
  @Test(priority=1)
  public void addNewUser() 
  {
	 Response  res=given()
	  .header("Content-Type","application/json")
	  .header("Accept","application/json")
	  .body("{ \n"
	  		+ "\"firstName\": \"Lakshmana\", \n"
	  		+ "\"lastName\": \"lucky\", \n"
	  		+ "\"email\": \"lucky"+System.currentTimeMillis()+"@gmail.com\", \n"
	  		+ "\"password\": \"test123\" \n"
	  		+ "}")
	  
	 .when().post("https://thinking-tester-contact-list.herokuapp.com/users");
	  
	  res.then().log().body();
	  
	  tokenvalue=res.jsonPath().getString("token");
	  System.out.println("New User created with status code: "+res.statusCode());
	  
  }
  
  @Test(priority=2,dependsOnMethods ="addNewUser")
  public void getProfile()
  {
	  Response res=given()
	  .header("Content-Type","application/json")
	  .header("Accept","application/json")
	  .header("Authorization","Bearer "+tokenvalue)
	  
	  .when().get("https://thinking-tester-contact-list.herokuapp.com/users/me");
	  
	  res.then().log().body();
	  System.out.println("User Profile get createed with id: "+res.statusCode());
  }
  
  @Test(priority=3)
  public void updateUser()
  {
	  
	  Response res=given()
	  .header("Content-Type","application/json")
	  .header("Accept","application/json")
	  .header("Authorization","Bearer "+tokenvalue)
	  .body("{ \n"
	  		+ "\"firstN ame\": \"Lakshmana\", \n"
	  		+ "\"lastName\": \"lucky\", \n"
	  		+ "\"email\": \"lucky"+System.currentTimeMillis()+"@gmail.com.com\", \n"
	  		+ "\"password\": \"test123\" \n"
	  		+ "} ")
	  
	  .when().patch("https://thinking-tester-contact-list.herokuapp.com/users/me");
	  
	  res.then().log().body();
	  
	  System.out.println("User updated with status code : "+res.statusCode());
	  
  
  
  }
  
  @Test(priority=4)
  public void loginUser() {
      Response res = given()
          .header("Content-Type", "application/json")
          .header("Accept", "application/json")
          .body("{ \n" +
                "\"email\": \"Lucky98@gmail.com\", \n" +
                "\"password\": \"test123\" \n" +
                "}")
          .when().post("https://thinking-tester-contact-list.herokuapp.com/users/login");

      // Print raw response body for debugging
      System.out.println("Raw response body: " + res.asString());

      try {
          logintoken = res.jsonPath().getString("token");
          System.out.println("User login with status code: " + res.statusCode());
          System.out.println("Login token: " + logintoken);
      } catch (io.restassured.path.json.exception.JsonPathException e) {
          System.out.println("Failed to parse the JSON document: " + e.getMessage());
          e.printStackTrace();
      }
  }

  
  @Test(priority=5)
  public void addContact()
  {
	  Response res=given()
	  .header("Content-Type","application/json")
	  .header("Accept","application/json")
	  .header("Authorization","Bearer "+logintoken)
	  .body("{ \n"
	  		+ "\"firstName\": \"Lakshmana\", \n"
	  		+ "\"lastName\": \"Lucky\", \n"
	  		+ "\"birthdate\": \"1998-01-01\", \n"
	  		+ "\"email\": \"LuckyTest@gmail.com\", \n"
	  		+ "\"phone\": \"8005555555\", \n"
	  		+ "\"street1\": \"1 Main St.\", \n"
	  		+ "\"street2\": \"Apartment A\", \n"
	  		+ "\"city\": \"Anytown\", \n"
	  		+ "\"stateProvince\": \"KS\", \n"
	  		+ "\"postalCode\": \"12345\", \n"
	  		+ "\"country\": \"USA\" \n"
	  		+ "}")
	  
	  
	  
	  .when().post("https://thinking-tester-contact-list.herokuapp.com/contacts");
	  
	  res.then().log().body();
	  id=res.jsonPath().getString("_id");
	  System.out.println("User contact created with status code: "+res.statusCode());
  }
  
  
  
  @Test(priority=6)
  public void getContactList()
  {
	  Response res=given()
			  .header("Content-Type","application/json")
			  .header("Accept","application/json")
			  .header("Authorization","Bearer "+logintoken)
			  
			  .when().get("https://thinking-tester-contact-list.herokuapp.com/contacts");
	  
	  res.then().log().body();
	  
	  System.out.println("Contact list with code: "+res.statusCode());
	  
			  
  }
  
  
  @Test(priority=7)
  public void getContact()
  {
	  Response res=given()
  .header("Content-Type","application/json")
  .header("Accept","application/json")
  .header("Authorization","Bearer "+logintoken)
  
  .when().get("https://thinking-tester-contact-list.herokuapp.com/contacts");

res.then().log().body();

System.out.println("Contact data with code: "+res.statusCode());
	  
  }
  @Test(priority=8)
  public void updateContact()
  {
	  Response res=given()
	  .header("Content-Type","application/json")
	  .header("Accept","application/json")
	  .header("Authorization","Bearer "+logintoken)
	  
	  .body("{ \n"
	  		+ "\"firstName\": \"Lakshmana\", \n"
	  		+ "\"lastName\": \"Lucky\",\n"
	  		+ " \n"
	  		+ " \n"
	  		+ "\"birthdate\": \"1998-02-02\", \n"
	  		+ "\"email\": \"Lucky@gmail.com\", \n"
	  		+ "\"phone\": \"8005554242\", \n"
	  		+ "\"street1\": \"13 School St.\", \n"
	  		+ "\"street2\": \"Apt. 5\", \n"
	  		+ "\"city\": \"Washington\", \n"
	  		+ "\"stateProvince\": \"QC\", \n"
	  		+ "\"postalCode\": \"A1A1A1\", \n"
	  		+ "\"country\": \"Canada\" \n"
	  		+ "} ")
	  
	  
	  
	  .when().put("https://thinking-tester-contact-list.herokuapp.com/contacts/"+id);
	  
	  res.then().log().body();
	  System.out.println("User updated with code: "+res.statusCode());
  }
  
  
  @Test(priority=9)
  public void updateContactpatch()
  {
	  Response res=given()
			  .header("Content-Type","application/json")
			  .header("Accept","application/json")
			  .header("Authorization","Bearer "+logintoken)
			  .body("{ \n"
			  		+ "\"firstName\": \"Amy\", \n"
			  		+ "\"lastName\": \"Miller\"} ")
			  .when().put("https://thinking-tester-contact-list.herokuapp.com/contacts/"+id);
	  
	  res.then().log().body();
	  System.out.println("User updated with code: "+res.statusCode());
  }
  
  @Test(priority=10)
  public void logoutUser()
  {
	  Response res=given()
	  .header("Content-Type","application/json")
	  .header("Accept","application/json")
	  .header("Authorization","Bearer "+logintoken)
	  .when().post("https://thinking-tester-contact-list.herokuapp.com/users/logout");
	  
	  res.then().log().body();
	  System.out.println("User logout with code: "+res.statusCode());
  }
  
}


