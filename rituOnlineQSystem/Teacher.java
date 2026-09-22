
import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

public class Teacher
 {
    public static void main(String[] args) {
        ArrayList<Question> quiz = new ArrayList<>();
        Scanner sc = new Scanner(System.in);
                System.out.println("Enter number of Questions:");
        int n = sc.nextInt();
        sc.nextLine(); // Clear buffer

        for (int i = 1; i <= n; i++) {
            System.out.println("\n--- Question " + i + " ---");
            System.out.println("Enter Question:");
            String questionText = sc.nextLine();
            
            System.out.println("Enter Four Options:");
            String[] options = new String[4];
            for (int j = 0; j < 4; j++) {
                options[j] = (j + 1) + ". " + sc.nextLine();
            }
            
            System.out.println("Enter correct answer (1-4):");
            int correctAns = sc.nextInt() - 1; // Convert to 0-based index
            sc.nextLine(); // Clear buffer

            quiz.add(new Question(questionText, options, correctAns));
        }
        
        System.out.println("\nAll questions added successfully!\n");
try
 (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("quiz.dat"))) 
{
 oos.writeObject(quiz);
 System.out.println("All Question save in file");
}
catch(IOException e)
{
e.printStackTrace();
}
}
}
        
class Question implements Serializable
 {  private static final long serialversionUID=1L;
    private String questionText;
    private String[] options;
    private int correctAnswerIndex;

    public Question(String q, String[] opt, int ans) {
        this.questionText = q;
        this.options = opt;
        this.correctAnswerIndex = ans;
    }

    public boolean isCorrect(int choice) {
        return choice == correctAnswerIndex;
    }
    
    public String getQuestionText() { return questionText; }
    public String[] getOptions() { return options; }
    public int getCorrectAnswerIndex() { return correctAnswerIndex; }
}