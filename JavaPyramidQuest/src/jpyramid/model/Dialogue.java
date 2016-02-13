package jpyramid.model;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class Dialogue {

  private String description;
  private ArrayList<String> choices;
  
  public Dialogue(String description, ArrayList<String> choices) {
    this.description = description;
    this.choices = choices;
  }
  
  public int Activate() {
    System.out.println(description);
    
    for (int i=0; i < choices.size(); i++) {
      System.out.println(i+1 + ": " + choices.get(i));
    }
    
    int userInput = -1;
    
    while(true) {
      BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
      try {
        userInput = Integer.parseInt(br.readLine());
      } catch (IOException e) {
        e.printStackTrace();
      }
      if (userInput >= 0 && userInput <= choices.size()) {
        return userInput;
      }
    }
  }
}
