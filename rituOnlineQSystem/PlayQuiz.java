

import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

public class PlayQuiz {
    static String name;
    static int rollno;
    static String course;
    static String branch;
    static int semester;
    
    static Scanner sc = new Scanner(System.in);
    static int score = 0;
    static ArrayList<Integer> userAnswers = new ArrayList<>();
    private static final String FILE_NAME = "./quiz.dat";

    static void Welcome() {
        System.out.println("========== Java Quiz ===========");
        System.out.print("Enter your Name : ");
        name = sc.nextLine();
        System.out.print("Enter your Roll No. : ");
        rollno = sc.nextInt();
        sc.nextLine(); // Clear buffer
        System.out.print("Enter your Course : ");
        course = sc.nextLine();
        System.out.print("Enter your Branch : ");
        branch = sc.nextLine();
        System.out.print("Enter your Semester : ");
        semester = sc.nextInt();
        sc.nextLine(); // Clear buffer

        System.out.println("\n======= Student_Details =======");
        System.out.println("Name : " + name);
        System.out.println("Roll No. : " + rollno);
        System.out.println("Course : " + course);
        System.out.println("Branch : " + branch);
        System.out.println("Semester : " + semester);
        System.out.println("==================================");
        
        System.out.println("\n Hello ! " + name);
        System.out.println("\n📝You are Instructed to read it once.");
        System.out.println("\n1. Read each question carefully before selecting your answer.");
        System.out.println("2. Enter only the option number (1, 2, 3, or 4) as your answer.");
        System.out.println("3. Once an answer is submitted, it cannot be changed, So be Careful.");
        System.out.println("4. Each question carries **1 mark**.");
        System.out.println("5. There is **no negative marking** for incorrect answers.");
        System.out.println("6. Do not press any unnecessary keys during the quiz.");
        System.out.println("7. Complete all questions before viewing the result.");
        System.out.println("8. The final score will be displayed after the last question.");
        System.out.println("9. Ensure you answer honestly without using unfair means.");
        System.out.println("10. Follow the on-screen instructions throughout the quiz.");
        System.out.println("\nPress Enter to Start..");
        sc.nextLine();
    }

    static void confirmSubmission() {
        System.out.print("\n⚠️Do you want to submit the quiz(Y/N): ");
        char ch = sc.next().charAt(0);
        sc.nextLine(); // Clear buffer
        if (ch == 'N' || ch == 'n') {
            System.out.println("Quiz Cancelled");
            System.exit(0);
        }
        System.out.println("✨Quiz Submitted Successfully");
    }

    static String grade(double p) {
        if (p >= 90) return "A+";
        else if (p >= 75) return "A";
        else if (p >= 60) return "B";
        else if (p >= 40) return "C";
        else return "Fail";
    }

    static void result(int totalQuestions) {
        double p = (score * 100.0) / totalQuestions;

        System.out.println("\n========== Score Card =========="); 
        System.out.println("Name: " + name);
        System.out.println("Roll No: " + rollno);
        System.out.println("Your obtained score: " + score + "/" + totalQuestions);
        System.out.printf("Your Percentage: %.2f%%\n", p);
        System.out.println("Your Grade: " + grade(p));
        System.out.println("==============================="); 
    }

    static void showPerformanceMessage(int totalQuestions) {
        double ratio = (double) score / totalQuestions;
        if (ratio >= 0.90) 
        	System.out.println("🏆Outstanding Performance!");
        else 
        	if (ratio >= 0.70) 
        		System.out.println("🌟Very Good!");
        else 
        	if (ratio >= 0.50) 
        		System.out.println("👍Good Job!");
        else
        	System.out.println("\n📚Need More Practice.");
    }

    static void QuizSummary(int totalQuestions) {
        System.out.println("\n======= Quiz Summary ========");
        System.out.println("Total Questions: " + totalQuestions);
        System.out.println("Attempted: " + totalQuestions);
        System.out.println("Correct Answers: " + score);
        System.out.println("Wrong Answers: " + (totalQuestions - score));
        System.out.println("===========================");
    }

    static void reviewWrongAnswers(ArrayList<Question> quiz) {
        if (score == quiz.size()) {
            System.out.println("\n🎉Perfect score! Nothing to review.");
            return;
        }
        
        System.out.println("\n======= Review Wrong Answers =======");
        for (int i = 0; i < quiz.size(); i++) {
            int uAns = userAnswers.get(i);
            int cAns = quiz.get(i).getCorrectAnswerIndex();
            if (uAns != cAns) {
                System.out.println("Q" + (i + 1) + ": " + quiz.get(i).getQuestionText());
                System.out.println("❌ Your Answer: " + (uAns + 1));
                System.out.println("✅ Correct Answer: " + (cAns + 1));
                System.out.println("------------------------------------");
            }
        }
    }
    public static void main(String[] args) {
        Welcome();
        ArrayList<Question> quiz = null;

        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(FILE_NAME))) {
            quiz = (ArrayList<Question>) ois.readObject();
            System.out.println("Quiz loaded successfully!\n");
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Unable to load the quiz. Make sure you have saved questions first.");
            return;
        }
        for (int i = 0; i < quiz.size(); i++) {
            Question q = quiz.get(i);
            System.out.println("\nQ" + (i + 1) + ". " + q.getQuestionText());
            String[] opts = q.getOptions();
            for (int j = 0; j < opts.length; j++) {
                System.out.println(opts[j]);
            }
            
            System.out.print("Your choice (1-4): ");
            int choice = sc.nextInt() - 1;
            sc.nextLine(); 
            
           
            userAnswers.add(choice);

            if (q.isCorrect(choice)) {
                score++;
            }
        }
        confirmSubmission();
        result(quiz.size());
        showPerformanceMessage(quiz.size());
        QuizSummary(quiz.size());
        reviewWrongAnswers(quiz);
        
        System.out.println("\n👋Thank you for Appearing.....!");
        sc.close();
    }
}