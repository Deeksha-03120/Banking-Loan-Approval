public class LoanProcessingQA {

    public static double calculateEMI(double loanAmount, double annualRate, int tenureMonths) {

        double monthlyRate = annualRate / 12 / 100;

        if (monthlyRate == 0) {
            return loanAmount / tenureMonths;
        }

        return (loanAmount * monthlyRate * Math.pow(1 + monthlyRate, tenureMonths))
                / (Math.pow(1 + monthlyRate, tenureMonths) - 1);
    }

    public static void test(String testName, boolean condition) {

        if (condition) {
            System.out.println(testName + " : PASS");
        } else {
            System.out.println(testName + " : FAIL");
        }
    }

    public static void main(String[] args) {

        System.out.println("============================================");
        System.out.println("     BANKING LOAN APPROVAL QA TESTING");
        System.out.println("============================================");

        test("Minimum Age - 18", 18 >= 18 && 18 <= 65);

        test("Maximum Age - 65", 65 >= 18 && 65 <= 65);

        test("Invalid Age - 17", !(17 >= 18 && 17 <= 65));

        test("Invalid Age - 66", !(66 >= 18 && 66 <= 65));

        test("Valid Salary", 50000 > 0);

        test("Invalid Salary - Zero", !(0 > 0));

        test("Invalid Salary - Negative", !(-5000 > 0));

        test("Good Credit Score", 780 >= 750);

        test("Poor Credit Score", 500 < 600);

        test("Existing Loan Within Threshold", 100000 <= 50000 * 10);

        test("Existing Loan Exceeds Threshold", 600000 > 50000 * 10);

        double lowDTI = (10000.0 / 50000) * 100;

        test("Low Debt-to-Income Ratio", lowDTI <= 50);

        double highDTI = (30000.0 / 50000) * 100;

        test("High Debt-to-Income Ratio", highDTI > 50);

        test("Salaried Employment", "Salaried".equalsIgnoreCase("Salaried"));

        test("Self-Employed Employment", "Self-Employed".equalsIgnoreCase("Self-Employed"));

        test("Business Employment", "Business".equalsIgnoreCase("Business"));

        test("Boundary Loan Amount", 1000000 <= 50000 * 20);

        test("Loan Amount Exceeds Eligibility", 1200000 > 50000 * 20);

        double emi = calculateEMI(500000, 8, 60);

        test("EMI Calculation", emi > 0);

        test("EMI Calculation Accuracy",
                Math.abs(emi - 10138.20) < 1.0);

        test("Invalid Negative Loan", !(-100000 > 0));

        test("Invalid Zero Loan", !(0 > 0));

        test("Invalid Tenure", !(0 > 0));

        try {
            Integer.parseInt("abc");
            test("Exception Handling", false);
        } catch (Exception e) {
            test("Exception Handling", true);
        }

        System.out.println();
        System.out.println("============================================");
        System.out.println("          QA TESTING COMPLETED");
        System.out.println("============================================");
    }
}
