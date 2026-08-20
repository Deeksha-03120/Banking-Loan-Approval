import java.util.Scanner;

public class LoanProcessingSystem {

    public static double calculateEMI(double loanAmount, double annualRate, int tenureMonths) {
        double monthlyRate = annualRate / 12 / 100;

        if (monthlyRate == 0) {
            return loanAmount / tenureMonths;
        }

        return (loanAmount * monthlyRate * Math.pow(1 + monthlyRate, tenureMonths))
                / (Math.pow(1 + monthlyRate, tenureMonths) - 1);
    }

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        try {
            System.out.print("Enter Customer ID: ");
            String customerId = sc.nextLine();

            System.out.print("Enter Age: ");
            int age = sc.nextInt();

            System.out.print("Enter Monthly Salary: ");
            double salary = sc.nextDouble();

            System.out.print("Enter Existing Loan Amount: ");
            double existingLoan = sc.nextDouble();

            System.out.print("Enter Credit Score: ");
            int creditScore = sc.nextInt();

            sc.nextLine();

            System.out.print("Enter Employment Type (Salaried/Self-Employed/Business): ");
            String employmentType = sc.nextLine();

            System.out.print("Enter Requested Loan Amount: ");
            double requestedLoan = sc.nextDouble();

            System.out.print("Enter Loan Tenure in Months: ");
            int tenure = sc.nextInt();

            if (age < 18 || age > 65) {
                System.out.println("Invalid age. Age must be between 18 and 65.");
                return;
            }

            if (salary <= 0) {
                System.out.println("Invalid salary.");
                return;
            }

            if (existingLoan < 0) {
                System.out.println("Invalid existing loan amount.");
                return;
            }

            if (creditScore < 300 || creditScore > 900) {
                System.out.println("Invalid credit score.");
                return;
            }

            if (requestedLoan <= 0) {
                System.out.println("Invalid requested loan amount.");
                return;
            }

            if (tenure <= 0) {
                System.out.println("Invalid loan tenure.");
                return;
            }

            double dti = (existingLoan / salary) * 100;

            double multiplier;

            if (employmentType.equalsIgnoreCase("Salaried")) {
                multiplier = 20;
            } else if (employmentType.equalsIgnoreCase("Self-Employed")) {
                multiplier = 15;
            } else if (employmentType.equalsIgnoreCase("Business")) {
                multiplier = 12;
            } else {
                System.out.println("Invalid employment type.");
                return;
            }

            double eligibleLoanAmount = salary * multiplier;

            double interestRate;

            if (creditScore >= 750) {
                interestRate = 8.0;
            } else if (creditScore >= 650) {
                interestRate = 10.0;
            } else {
                interestRate = 12.0;
            }

            double emi = calculateEMI(requestedLoan, interestRate, tenure);

            boolean approved = true;

            if (creditScore < 600) {
                approved = false;
            }

            if (dti > 50) {
                approved = false;
            }

            if (existingLoan > salary * 10) {
                approved = false;
            }

            if (requestedLoan > eligibleLoanAmount) {
                approved = false;
            }

            System.out.println();
            System.out.println("========== LOAN PROCESSING RESULT ==========");
            System.out.println("Customer ID: " + customerId);
            System.out.printf("Debt-to-Income Ratio: %.2f%%\n", dti);
            System.out.printf("Eligible Loan Amount: %.2f\n", eligibleLoanAmount);
            System.out.printf("Interest Rate: %.2f%%\n", interestRate);
            System.out.printf("Monthly EMI: %.2f\n", emi);

            if (approved) {
                System.out.println("Loan Status: APPROVED");
            } else {
                System.out.println("Loan Status: REJECTED");
            }

            System.out.println("============================================");

        } catch (Exception e) {
            System.out.println("Invalid input. Please enter valid values.");
        }

        sc.close();
    }
}
