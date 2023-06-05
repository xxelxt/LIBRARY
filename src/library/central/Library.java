package library.central;

import java.util.ArrayList;

import library.mysql.Database;
import library.publication.Books;
import library.publication.PrintMedia;
import library.user.Clerk;
import library.user.Librarian;
import library.user.Student;
import library.user.User;

public class Library {

    public static ArrayList<User> UsersList;
    public static ArrayList<Student> StudentList;
    public static ArrayList<Books> BooksList;
    public static ArrayList<PrintMedia> PrintMediaList;
    public static ArrayList<Librarian> LibrarianList;
    public static ArrayList<Clerk> ClerkList;
    public static ArrayList<Borrow> BorrowList;
    public static ArrayList<Event> EventList;
    
    public Library() {
        Database db = new Database();
        
    //  UsersList = db.loadAllUsers();
        StudentList = db.loadAllStudents();
        BooksList = db.loadAllBooks();
        PrintMediaList = db.loadAllPrintMedias();

        LibrarianList = db.loadAllLibrarians();
        ClerkList = db.loadAllClerks();
        BorrowList = db.loadBorrowList();
    //  EventList = db.loadEventList();

        for (int i = 0; i < StudentList.size(); i++) {
            Student S = StudentList.get(i);

            System.out.println();
        }

        for (int i = 0; i < BooksList.size(); i++) {
            Books B = BooksList.get(i);

            System.out.println();
        }

        for (int i = 0; i < PrintMediaList.size(); i++) {
            PrintMedia PM = PrintMediaList.get(i);

            System.out.println();
        }

        for (int i = 0; i < LibrarianList.size(); i++) {
            Librarian L = LibrarianList.get(i);

            System.out.println();
        }

        for (int i = 0; i < ClerkList.size(); i++) {
            Clerk C = ClerkList.get(i);

            System.out.println();
        }

        for (int i = 0; i < BorrowList.size(); i++) {
            Borrow B = BorrowList.get(i);

            System.out.println();
        }
    }
}
