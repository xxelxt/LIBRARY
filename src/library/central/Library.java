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

        for (Student S : StudentList) {
            System.out.println();
        }

        for (Books B : BooksList) {
            System.out.println();
        }

        for (PrintMedia PM : PrintMediaList) {
            System.out.println();
        }

        for (Librarian L : LibrarianList) {
            System.out.println();
        }

        for (Clerk C : ClerkList) {
            System.out.println();
        }

        for (Borrow B : BorrowList) {
            System.out.println();
        }
    }

    boolean isStudentPresent(String StudentID) {
        for (Student S : StudentList) {
            if (S.getStudentID() == StudentID) return true;
        }
        return false;
    }

    boolean isClerkPresent(String ClerkID) {
        for (Clerk C : ClerkList) {
            if (C.getStaffID() == ClerkID) return true;
        }
        return false;
    }

    boolean isLibrarianPresent(String LibrarianID) {
        for (Librarian L : LibrarianList) {
            if (L.getStaffID() == LibrarianID) return true;
        }
        return false;
    }


}
