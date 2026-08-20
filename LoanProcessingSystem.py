import math

class LoanProcessingSystem:
    def __init__(self, customer_id, age, monthly_salary, existing_loan_amount, 
                 credit_score, employment_type, requested_loan_amount, loan_tenure):
        self.customer_id = customer_id
        self.age = age
        self.monthly_salary = monthly_salary
        self.existing_loan_amount = existing_loan_amount
        self.credit_score = credit_score
        self.employment_type = employment_type.strip() if employment_type else ""
        self.requested_loan_amount = requested_loan_amount
        self.loan_tenure = loan_tenure  # Tracked in months

    def validate_inputs(self):
        if not self.customer_id or self.customer_id.strip() == "":
            raise ValueError("Invalid Customer ID.")
        if not (18 <= self.age <= 65):
            raise ValueError("Age must be between 18 and 65 years.")
        if self.monthly_salary <= 0:
            raise ValueError("Monthly salary must be greater than zero.")
        if self.existing_loan_amount < 0:
            raise ValueError("Existing loan amount cannot be negative.")
        if not (300 <= self.credit_score <= 850):
            raise ValueError("Credit score must be between 300 and 850.")
        if self.employment_type.lower() not in ["salaried", "self-employed"]:
            raise ValueError("Employment type must be either 'Salaried' or 'Self-Employed'.")
        if self.requested_loan_amount <= 0:
            raise ValueError("Requested loan amount must be greater than zero.")
        if not (1 <= self.loan_tenure <= 360):
            raise ValueError("Loan tenure must be between 1 and 360 months.")

    def calculate_interest_rate(self):
        base_rate = 7.5 if self.employment_type.lower() == "salaried" else 8.5
        if self.credit_score >= 750:
            return base_rate
        if self.credit_score >= 650:
            return base_rate + 1.5
        return base_rate + 3.0

    def calculate_emi(self):
        annual_rate = self.calculate_interest_rate()
        monthly_rate = (annual_rate / 100) / 12
        emi = (self.requested_loan_amount * monthly_rate * math.pow(1 + monthly_rate, self.loan_tenure)) / \
              (math.pow(1 + monthly_rate, self.loan_tenure) - 1)
        return emi

    def calculate_dti_ratio(self):
        # Balanced baseline existing monthly liability to cleanly capture high DTI limits
        estimated_existing_monthly_obligation = self.existing_loan_amount * 0.02
        total_monthly_obligations = estimated_existing_monthly_obligation + self.calculate_emi()
        return (total_monthly_obligations / self.monthly_salary) * 100

    def calculate_eligible_loan_amount(self):
        if self.credit_score < 600:
            return 0.0
        multiplier = 24 if self.credit_score >= 750 else 12
        return self.monthly_salary * multiplier

    def evaluate_loan(self):
        try:
            self.validate_inputs()
            
            dti = self.calculate_dti_ratio()
            eligible_amount = self.calculate_eligible_loan_amount()
            
            if self.credit_score < 600:
                return "REJECTED - Poor credit score."
            
            # Explicitly capture extreme DTI variances or total overall capacity breaches
            if dti > 50.0:
                return "REJECTED - High debt-to-income ratio."
            if self.requested_loan_amount > eligible_amount:
                return "REJECTED - Requested amount exceeds eligibility limit."
            if self.existing_loan_amount > (self.monthly_salary * 36):
                return "REJECTED - Existing liabilities exceed total debt capacity."

            return f"APPROVED - EMI: {self.calculate_emi():.2f}, Interest Rate: {self.calculate_interest_rate()}%"
            
        except ValueError as e:
            return f"REJECTED - Validation Error: {str(e)}"
        except Exception:
            return "REJECTED - System Execution Exception occurred."
