import sys
from LoanProcessingSystem import LoanProcessingSystem

def run_qa_suite():
    print("======================================================")
    print("   STARTING LOAN SYSTEM PIPELINE AUTOMATION SUITE      ")
    print("======================================================\n")

    tests_passed = 0
    total_tests = 0

    # Test 1: Minimum/Maximum Age Boundary Validation
    total_tests += 1
    low_age_test = LoanProcessingSystem("C001", 17, 50000, 0, 750, "Salaried", 100000, 12)
    if "Age must be between 18 and 65" in low_age_test.evaluate_loan():
        print("[PASS] Test 1: Under-age restriction caught successfully.")
        tests_passed += 1
    else:
        print("[FAIL] Test 1: Failed to validate minimum age barrier.")

    # Test 2: Invalid Salary Evaluation Check
    total_tests += 1
    bad_salary_test = LoanProcessingSystem("C002", 30, -100, 0, 750, "Salaried", 100000, 12)
    if "Monthly salary must be greater than zero" in bad_salary_test.evaluate_loan():
        print("[PASS] Test 2: Invalid negative salary valuation intercepted.")
        tests_passed += 1
    else:
        print("[FAIL] Test 2: System accepted a negative income configuration.")

    # Test 3: Poor Credit Score Check Boundary
    total_tests += 1
    poor_credit_test = LoanProcessingSystem("C003", 35, 60000, 0, 450, "Salaried", 50000, 24)
    if "Poor credit score" in poor_credit_test.evaluate_loan():
        print("[PASS] Test 3: System correctly rejected a weak credit tier profile.")
        tests_passed += 1
    else:
        print("[FAIL] Test 3: Error evaluating high-risk credit bounds.")

    # Test 4: Existing Loan Exceeding Maximum Capacity Threshold
    total_tests += 1
    heavy_debt_test = LoanProcessingSystem("C004", 40, 40000, 2000000, 780, "Salaried", 50000, 12)
    if "Existing liabilities exceed total debt capacity" in heavy_debt_test.evaluate_loan():
        print("[PASS] Test 4: Extreme pre-existing systemic debt flag functional.")
        tests_passed += 1
    else:
        print(f"[FAIL] Test 4: Failed to suppress applicant with high debt burden. Result: {heavy_debt_test.evaluate_loan()}")

    # Test 5: High Debt-to-Income (DTI) Ratio Protection
    total_tests += 1
    high_dti_test = LoanProcessingSystem("C005", 28, 30000, 100000, 720, "Salaried", 500000, 12)
    if "High debt-to-income ratio" in high_dti_test.evaluate_loan():
        print("[PASS] Test 5: DTI safeguard calculation rejected unbalanced risk profile.")
        tests_passed += 1
    else:
        print(f"[FAIL] Test 5: System failed to prevent hyper-extended DTI ratios. Result: {high_dti_test.evaluate_loan()}")

    # Test 6: Dynamic Pricing Across Different Employment Categories
    total_tests += 1
    self_employed = LoanProcessingSystem("C006", 32, 80000, 0, 760, "Self-Employed", 100000, 24)
    if self_employed.calculate_interest_rate() == 8.5:
        print("[PASS] Test 6: Employment variance calculation adjusts dynamic variables accurately.")
        tests_passed += 1
    else:
        print("[FAIL] Test 6: Base pricing calculation index misaligned on occupational categories.")

    # Test 7: Boundary Loan Amounts Max Eligibility Limits
    total_tests += 1
    limit_test = LoanProcessingSystem("C007", 45, 50000, 0, 760, "Salaried", 2000000, 36)
    if "Requested amount exceeds eligibility limit" in limit_test.evaluate_loan():
        print("[PASS] Test 7: Capital limitation walls blocked request beyond total capacity.")
        tests_passed += 1
    else:
        print(f"[FAIL] Test 7: Threshold calculation let illegal loan parameters pass through. Result: {limit_test.evaluate_loan()}")

    # Test 8: EMI Calculation Amortization Formula Accuracy Verification
    total_tests += 1
    emi_verify = LoanProcessingSystem("C008", 30, 100000, 0, 800, "Salaried", 120000, 12)
    calculated_emi = emi_verify.calculate_emi()
    if 10000 < calculated_emi < 11000:
        print("[PASS] Test 8: Mathematical amortization execution matching control groups.")
        tests_passed += 1
    else:
        print("[FAIL] Test 8: Amortization logic processing error.")

    # Test 9 & 10: Invalid Input Formats & Exception Fault Isolation
    total_tests += 1
    try:
        exception_test = LoanProcessingSystem("", 25, 50000, 0, 700, "Unknown", 50000, 12)
        outcome = exception_test.evaluate_loan()
        if "Validation Error" in outcome:
            print("[PASS] Test 9 & 10: Graceful termination string returned during systemic boundary stress test.")
            tests_passed += 1
        else:
            print("[FAIL] Test 9 & 10: The pipeline processing was polluted with bad baseline formatting rules.")
    except Exception:
        print("[FAIL] Test 9 & 10: Runtime crash instead of catching clean exceptions safely.")

    print("\n======================================================")
    print(f" QA SUITE COMPLETED: {tests_passed} / {total_tests} SCENARIOS PASSED.")
    print("======================================================")

    if tests_passed != total_tests:
        sys.exit(1)

if __name__ == "__main__":
    run_qa_suite()
