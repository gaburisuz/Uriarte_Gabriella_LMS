package lms;

import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

/** Gabriella Uriarte
 * 	CEN3024C
 * 	24667
 *  01/23/2024
 *  Software Development 1
 *  Library Management System class manages a collection of books in the library. Users can add books, remove books, list books, and exit to the main menu.
 */

public class LibraryManagementSystem {

    private static ArrayList<Book> books = new ArrayList<>();
    private static final String FILE_PATH = "/Users/gabbyuriarte/Desktop/lms.txt";

    public static void main(String[] args) {
    	// Load books from file
        loadBooksFromFile();

        int choice;

        // Display menu and perform actions until user exits
        do {
            displayMenu();
            choice = getUserChoice();
            performAction(choice);
        } while (choice != 4);

        // Save book to file
        saveBookCollectionToFile();
        System.out.println("Thank you for using LMS.");
    }

    // Load books from file
    // FileNotFoundException
    private static void loadBooksFromFile() {
        try (Scanner scanner = new Scanner(new File(FILE_PATH))) {
            // Read book information from each line and add to collection
            while (scanner.hasNextLine()) {
                String[] bookInfo = scanner.nextLine().split(",");
                int id = Integer.parseInt(bookInfo[0]);
                books.add(new Book(id, bookInfo[1], bookInfo[2]));
            }
        } catch (FileNotFoundException e) {
            System.out.println("File not found: " + FILE_PATH);
        }
    }

    // Display menu options
    private static void displayMenu() {
        System.out.println("\nMenu:\n1. Add a new book\n2. Remove a book\n3. List all books\n4. Exit");
    }

    // Get user's choice from menu
    private static int getUserChoice() {
        System.out.print("Enter your choice: ");
        return new Scanner(System.in).nextInt();
    }

    // Perform actions based on user choice
    private static void performAction(int choice) {
        switch (choice) {
            case 1: addNewBook(); break;
            case 2: removeBook(); break;
            case 3: listAllBooks(); break;
            default: System.out.println("Try again.");
        }
    }

    // Add new book to collection
    private static void addNewBook() {
        Scanner scanner = new Scanner(System.in);

        // Get book details from user
        System.out.print("Enter book ID: ");
        int id = scanner.nextInt();
        scanner.nextLine(); // Newline character

        System.out.print("Enter book title: ");
        String title = scanner.nextLine();

        System.out.print("Enter book author: ");
        String author = scanner.nextLine();

        // Add new book to collection
        books.add(new Book(id, title, author));
        System.out.println("Book added successfully.");
    }

    // Remove book from collection
    private static void removeBook() {
        Scanner scanner = new Scanner(System.in);

        // Get ID of book to remove
        System.out.print("Enter book ID to remove: ");
        int idToRemove = scanner.nextInt();

        // Remove book with ID
        books.removeIf(book -> book.getId() == idToRemove);
        System.out.println("Book removed successfully.");
    }

    // List all books in collection
    private static void listAllBooks() {
        if (books.isEmpty()) {
            System.out.println("No books in the collection.");
        } else {
            // Display list of books
            System.out.println("Books:");
            books.forEach(System.out::println);
        }
    }

    // Save book collection to file
    private static void saveBookCollectionToFile() {
        try (PrintWriter writer = new PrintWriter(new FileWriter(FILE_PATH))) {
            // Write each book to file
            books.forEach(writer::println);
        } catch (IOException e) {
            System.out.println("Error. Book not saved to.");
        }
    }

    // Book class
    private static class Book {
        private int id;
        private String title, author;

        public Book(int id, String title, String author) {
            this.id = id;
            this.title = title;
            this.author = author;
        }

        // Override 
        @Override
        public String toString() {
            return id + "," + title + "," + author;
        }

        // Get for book ID
        public int getId() {
            return id;
        }
    }
}
