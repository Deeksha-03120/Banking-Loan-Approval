package main.java;
public class LoanProcessingSystem {
    private String customerId;
    private int age;
    private double monthlySalary;
    private double existingLoanAmount;
    private int creditScore;
    private String employmentType; // "Salaried" or "Self-Employed"
    private double requestedLoanAmount;
    private int loanTenure; // in months
    public LoanProcessingSystem(String customerId, int age, double monthlySalary, double existingLoanAmount, 
                                int creditScore, String employmentType, double requestedLoanAmount, int loanTenure) {
        this.customerId = customerId;
        this.age = age;
        this.monthlySalary = monthlySalary;
        this.existingLoanAmount = existingLoanAmount;
        this.creditScore = creditScore;
        this.employmentType = employmentType;
        this.requestedLoanAmount = requestedLoanAmount;
        this.loanTenure = loanTenure;
    }
    public void validateInputs() throws IllegalArgumentException {
        if (customerId == null || customerId.trim().isEmpty()) {
            throw new IllegalArgumentException("Invalid Customer ID.");
        }
        if (age < 18 || age > 65) {
            throw new IllegalArgumentException("Age must be between 18 and 65 years.");
        }
        if (monthlySalary <= 0) {
            throw new IllegalArgumentException("Monthly salary must be greater than zero.");
        }
        if (existingLoanAmount < 0) {
            throw new IllegalArgumentException("Existing loan amount cannot be negative.");
        }
        if (creditScore < 300 || creditScore > 850) {
            throw new IllegalArgumentException("Credit score must be between 300 and 850.");
        }
        if (!"Salaried".equalsIgnoreCase(employmentType) && !"Self-Employed".equalsIgnoreCase(employmentType)) {
            throw new IllegalArgumentException("Employment type must be either 'Salaried' or 'Self-Employed'.");
        }
        if (requestedLoanAmount <= 0) {
            throw new IllegalArgumentException("Requested loan amount must be greater than zero.");
        }
        if (loanTenure <= 0 || loanTenure > 360) {
            throw new IllegalArgumentException("Loan tenure must be between 1 and 360 months.");
        }
    }

    
    public double calculateInterestRate() {
        double baseRate = "Salaried".equalsIgnoreCase(employmentType) ? 7.5 : 8.5;
        
        if (creditScore >= 750) return baseRate;          // Prime rate
        if (creditScore >= 650) return baseRate + 1.5;    // Subprime profile
        return baseRate + 3.0;                            // High risk
    }

    public double calculateEMI() {
        double annualRate = calculateInterestRate();
        double monthlyRate = (annualRate / 100) / 12;
        
        return (requestedLoanAmount * monthlyRate * Math.pow(1 + monthlyRate, loanTenure)) 
                / (Math.pow(1 + monthlyRate, loanTenure) - 1);
    }

    // Calculate Estimated Debt-to-Income (DTI) Ratio
    // Assuming an estimated baseline existing monthly obligation of 10% of existing total loan pool
    public double calculateDTIRatio() {
        double estimatedExistingMonthlyObligation = existingLoanAmount * 0.05; 
        double totalMonthlyObligations = estimatedExistingMonthlyObligation + calculateEMI();
        return (totalMonthlyObligations / monthlySalary) * 100;
    }

    // Determine max eligible amount based on credit health
    public double calculateEligibleLoanAmount() {
        if (creditScore < 600) return 0.0;
        
        double multiplier = 0.0;
        if (creditScore >= 750) multiplier = 24; // Eligible for up to 24x monthly income
        else if (creditScore >= 650) multiplier = 12; // Eligible for up to 12x monthly income
        
        return monthlySalary * multiplier;
    }

    // Process Evaluation Report
    public String evaluateLoan() {
        try {
            validateInputs();
            
            double dti = calculateDTIRatio();
            double eligibleAmount = calculateEligibleLoanAmount();
            
            // Hard Rejection Triggers
            if (creditScore < 600) {
                return "REJECTED - Poor credit score.";
            }
            if (dti > 50.0) {
                return "REJECTED - High debt-to-income ratio (" + String.format("%.2f", dti) + "%).";
            }
            if (requestedLoanAmount > eligibleAmount) {
                return "REJECTED - Requested amount exceeds eligibility limit of " + eligibleAmount;
            }
            if (existingLoanAmount > (monthlySalary * 36)) {
                return "REJECTED - Existing liabilities exceed total debt capacity thresholds.";
            }

            return "APPROVED - EMI: " + String.format("%.2f", calculateEMI()) + ", Interest Rate: " + calculateInterestRate() + "%";
            
        } catch (IllegalArgumentException e) {
            return "REJECTED - Validation Error: " + e.getMessage();
        } catch (Exception e) {
            return "REJECTED - System Execution Exception occurred.";
        }
    }
}
