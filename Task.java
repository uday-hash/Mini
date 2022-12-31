/*
 * Todo List
 * Let’s start with the good-old trusty todo list, the “Hello, World” of full
 * programs. You’re going to write a command-line todo list program that meets
 * the following specifications:
 * 
 * Prompt the user to enter a chore or task. Store the task in a permanent
 * location so that the task persists when the program is restarted.
 * 
 * Allow the user to enter as many tasks as desired but stop entering tasks by
 * entering a blank task. Do not store the blank task.
 * 
 * Display all the tasks.
 * 
 * Allow the user to remove a task, to signify it’s been completed.
 * 
 * Constraints
 * Store the data in an external data source.
 * 
 * If you’re using a server-side language, consider persisting the data to
 * Redis.
 * 
 * Consider persisting the database to a third-party service like Parse or
 * Firebase.
 * 
 * Challenges
 * Implement this in a web browser using only front-end technologies.
 * Investigate using IndexedDB to save the items.
 * 
 * Implement the front end as an Android or iPhone app, but connect that front
 * end to your own back end that you write using a server-side language. Create
 * your own API for retrieving the list, creating a new item, and marking an
 * item as complete.
 */

import java.io.*;
import java.util.*;

public class Task {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        String task = "";
        ArrayList<String> tasks = new ArrayList<String>();
        do {
            System.out.print("Enter a task: ");
            task = sc.nextLine();
            if (task.contains(" ")) {
                break;
            }
            tasks.add(task);
        } while (!task.equals(""));
        System.out.println("Your tasks are: ");
        for (int i = 0; i < tasks.size(); i++) {
            System.out.println((i + 1) + ". " + tasks.get(i));
        }
        System.out.print("Which task have you completed? ");
        int completed = sc.nextInt();
        tasks.remove(completed - 1);
        System.out.println("Your tasks are: ");
        for (int i = 0; i < tasks.size(); i++) {
            System.out.println((i + 1) + ". " + tasks.get(i));
        }

        /*
         * Constraints
         * Store the data in an external data source.
         * 
         * If you’re using a server-side language, consider persisting the data to
         * Redis.
         */

        try {
            File file = new File("tasks.txt");
            FileWriter fw = new FileWriter(file);
            for (int i = 0; i < tasks.size(); i++) {
                fw.write(tasks.get(i) + "\n");
            }
            fw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        /*
         * consider persisting the data to
         * Redis.
         * Persist all chores so that when the command line app is restarted, all the
         * chores are still there
         */

        RedisClient client = new RedisClient("localhost", 6379);
        for (int i = 0; i < tasks.size(); i++) {
            client.set("task" + i, tasks.get(i));
        }
        client.quit();

    }
}