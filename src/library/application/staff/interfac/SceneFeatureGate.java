package library.application.staff.interfac;

public interface SceneFeatureGate {
	public static final int LIBRARIAN = 1;
	public static final int CLERK = 2;
	public static final int STUDENT = 3;
	public void setFeatureFor(Integer user);
}
