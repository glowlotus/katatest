public class NotAllowedInputException extends Exception {
    @Override
    public void printStackTrace() {
        System.out.println("Введено выражение, не соответсвующее условию.");
    }
}