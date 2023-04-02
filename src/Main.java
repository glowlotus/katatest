import java.util.List;
import java.util.Scanner;

public class Main {
    private static boolean loopProgram = true;

    public static void main(String[] args) throws NotAllowedInputException {
        System.out.println("""
                ========== КАЛЬКУЛЯТОР ==========
                Условия работы:
                - Калькулятор умеет выполнять операции сложения, вычитания, умножения и деления с двумя числами: a + b, a - b, a * b, a / b;
                - Калькулятор умеет работать как с арабскими, так и с римскими числами;
                - Калькулятор принимает на вход числа от 1 до 10 включительно, не более. На выходе числа не ограничиваются по величине и могут быть любыми;
                - Калькулятор умеет работать только с целыми числами;
                - Обработать операцию с арабскими и римскими числами одновременно калькулятор не сможет;
                - Результатом операции деления является целое число, остаток отбрасывается;
                - Также учтите, что в римской системе счисления нет отрицательных чисел
                - Для завершения работы, введите слово "завершить\"""");
        while (loopProgram)
            calc();
    }

    private static void calc() throws NotAllowedInputException {
        System.out.println("Введите своё выражение:");
        Scanner scan = new Scanner(System.in);
        String input = scan.nextLine();
        if (input.equals("завершить"))
            loopProgram = false;
        else
            System.out.println("Ответ: " + calc(input));
    }

    private static String calc(String input) throws NotAllowedInputException {
        String[] part = input.split(" ");
        if (part.length != 3) throw new NotAllowedInputException();

        boolean arabic;
        String firstNumber = part[0];
        String theSymbol = part[1];
        String secondNumber = part[2];
        int firstNum, secondNum;

        if (isArabic(firstNumber) && isArabic(secondNumber)) {
            arabic = true;
            firstNum = Integer.parseInt(firstNumber);
            secondNum = Integer.parseInt(secondNumber);
        } else {
            arabic = false;
            firstNum = romanToArabic(firstNumber);
            secondNum = romanToArabic(secondNumber);
        }
        if (firstNum < 0 || firstNum > 10 || secondNum < 0 || secondNum > 10)
            throw new NotAllowedInputException();

        int answer = switch (theSymbol) {
            case "+" -> firstNum + secondNum;
            case "-" -> firstNum - secondNum;
            case "*" -> firstNum * secondNum;
            case "/" -> firstNum / secondNum;
            default -> throw new NotAllowedInputException();
        };
        if (arabic)
            return String.valueOf(answer);
        else {
            if (answer < 1)
                throw new NotAllowedInputException();
            return arabicToRoman(answer);
        }
    }

    public static boolean isArabic(String number) throws NotAllowedInputException {
        boolean roman = false;
        boolean arabic = false;

        for (int i = 0; i < number.length(); i++) {
            if (number.charAt(i) == 'I' || number.charAt(i) == 'V' || number.charAt(i) == 'X')
                roman = true;
            else if ((int) number.charAt(i) <= 57 && (int) number.charAt(i) >= 48)
                arabic = true;
            else
                throw new NotAllowedInputException();
        }
        if (roman && arabic)
            throw new NotAllowedInputException();

        return arabic;
    }

    public static int romanToArabic(String input) throws NotAllowedInputException {
        int result = 0;

        List<RomanNum> romanNumerals = RomanNum.getReverseSortedValues();
        int i = 0;
        while ((input.length() > 0) && (i < romanNumerals.size())) {
            RomanNum symbol = romanNumerals.get(i);
            if (input.startsWith(symbol.name())) {
                result += symbol.VALUE;
                input = input.substring(symbol.name().length());
            } else {
                i++;
            }
        }
        if (input.length() > 0)
            throw new NotAllowedInputException();

        return result;
    }

    public static String arabicToRoman(int number) {
        List<RomanNum> romanNumerals = RomanNum.getReverseSortedValues();

        int i = 0;
        StringBuilder sb = new StringBuilder();
        while (number > 0 && i < romanNumerals.size()) {
            RomanNum currentSymbol = romanNumerals.get(i);
            if (currentSymbol.VALUE <= number) {
                sb.append(currentSymbol.name());
                number -= currentSymbol.VALUE;
            } else {
                i++;
            }
        }

        return sb.toString();
    }
}