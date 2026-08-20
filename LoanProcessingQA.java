package test.java;

import main.java.LoanProcessingSystem;

public class LoanProcessingQA {

    public static void main(String[] args) {
        System.out.println("======================================================");
        System.out.println("   STARTING LOAN SYSTEM PIPELINE AUTOMATION SUITE      ");
        System.out.println("======================================================\n");

        int testsPassed = 0;
        int totalTests = 0;

        // Test 1: Minimum/Maximum Age Boundary
        totalTests++;
        LoanProcessingSystem lowAgeTest = new LoanProcessingSystem("C001", 17, 50000, 0, 750, "Salaried", 100000, 12);
        if (lowAgeTest.evaluateLoan().contains("Age must be between 18 and 65")) {
            System.out.println("[PASS] Test 1: Under-age restriction caught successfully.");
            testsPassed++;
        } else {
            System.out.println("[FAIL] Test 1: Failed to validate minimum age barrier.");
        }

        // Test 2: Invalid Salary Input
        totalTests++;
        LoanProcessingSystem badSalaryTest = new LoanProcessingSystem("C002", 30, -100, 0, 750, "Salaried", 100000, 12);
        if (badSalaryTest.evaluateLoan().contains("Monthly salary must be greater than zero")) {
            System.out.println("[PASS] Test 2: Invalid negative salary valuation intercepted.");
            testsPassed++;
        } else {
            System.out.println("[FAIL] Test 2: System accepted a negative income configuration.");
        }

        // Test 3: Poor Credit Score Check
        totalTests++;
        LoanProcessingSystem poorCreditTest = new LoanProcessingSystem("C003", 35, 60000, 0, 450, "Salaried", 50000, 24);
        if (poorCreditTest.evaluateLoan().contains("Poor credit score")) {
            System.out.println("[PASS] Test 3: System correctly rejected a weak credit tier profile.");
            testsPassed++;
        } else {
            System.out.println("[FAIL] Test 3: Error evaluating high-risk credit bounds.");
        }

        // Test 4: Existing Loan Exceeding Maximum Threshold
        totalTests++;
        LoanProcessingSystem heavyDebtTest = new LoanProcessingSystem("C004", 40, 40000, 2000000, 780, "Salaried", 50000, 12);
        if (heavyDebtTest.evaluateLoan().contains("Existing liabilities exceed total debt capacity")) {
            System.out.println("[PASS] Test 4: Extreme pre-existing systemic debt flag functional.");
            testsPassed++;
        } else {
            System.out.println("[FAIL] Test 4: Failed to suppress applicant with high debt burden.");
        }

        // Test 5: High Debt-to-Income (DTI) Ratio Protection
        totalTests++;
        LoanProcessingSystem highDTITest = new LoanProcessingSystem("C005", 28, 30000, 100000, 720, "Salaried", 500000, 12);
        if (highDTITest.evaluateLoan().contains("High debt-to-income ratio")) {
            System.out.println("[PASS] Test 5: DTI safeguard calculation rejected unbalanced risk profile.");
            testsPassed++;
        } else {
            System.out.println("[FAIL] Test 5: System failed to prevent hyper-extended DTI ratios.");
        }

        // Test 6: Different Employment Categories
        totalTests++;
        LoanProcessingSystem selfEmployed = new LoanProcessingSystem("C006", 32, 80000, 0, 760, "Self-Employed", 100000, 24);
        if (selfEmployed.calculateInterestRate() == 8.5) {
            System.out.println("[PASS] Test 6: Employment variance calculation adjusts dynamic variables accurately.");
            testsPassed++;
        } else {
            System.out.println("[FAIL] Test 6: Base pricing calculation index misaligned on occupational categories.");
        }

        // Test 7: Boundary Loan Amounts Max Eligibility Check
        totalTests++;
        LoanProcessingSystem limitTest = new LoanProcessingSystem("C007", 45, 50000, 0, 760, "Salaried", 2000000, 36);
        if (limitTest.evaluateLoan().contains("exceeds eligibility limit")) {
            System.out.println("[PASS] Test 7: Capital limitation walls blocked request beyond total capacity.");
            testsPassed++;
        } else {
            System.out.println("[FAIL] Test 7: Threshold calculation let illegal loan parameters pass through.");
        }

        // Test 8: EMI Calculation Accuracy Verification
        totalTests++;
        LoanProcessingSystem emiVerify = new LoanProcessingSystem("C008", 30, 100000, 0, 800, "Salaried", 120000, 12);
        double calculatedEmi = emiVerify.calculateEMI();
        if (calculatedEmi > 10000 && calculatedEmi < 11000) { // Standard ballpark range check for 120k over 12 months at ~7.5%
            System.out.println("[PASS] Test 8: Mathematical amortization execution matching control groups.");
            testsPassed++;
        } else {
            System.out.println("[FAIL] Test 8: Amortization logic processing error. Checked value: " + calculatedEmi);
        }

        // Test 9 & 10: Invalid Input Handling & Exception Handling
        totalTests++;
        try {
            LoanProcessingSystem exceptionTest = new LoanProcessingSystem("", 25, 50000, 0, 700, "Unknown", 50000, 12);
            String outcome = exceptionTest.evaluateLoan();
            if (outcome.contains("Validation Error")) {
                System.out.println("[PASS] Test 9 & 10: Graceful termination string returned during systemic boundary stress test.");
                testsPassed++;
            } else {
                System.out.println("[FAIL] Test 9 & 10: The pipeline processing was polluted with bad baseline formatting rules.");
            }
        } catch (Exception e) {
            System.out.println("[FAIL] Test 9 & 10: Runtime crash instead of catching clean exceptions safely.");
        }

        System.out.println("\n======================================================");
        System.out.println(" QA SUITE COMPLETED: " + testsPassed + " / " + totalTests + " SCENARIOS PASSED.");
        System.out.println("======================================================");

        // Force explicit non-zero exit code if automated checks fail to alert Jenkins build cycle
        if (testsPassed != totalTests) {
            System.exit(1);
        }
    }
}
