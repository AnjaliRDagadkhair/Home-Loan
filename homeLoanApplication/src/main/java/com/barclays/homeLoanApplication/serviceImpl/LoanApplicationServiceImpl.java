package com.barclays.homeLoanApplication.serviceImpl;

import com.barclays.homeLoanApplication.constants.LoanApplicationConstants;
import com.barclays.homeLoanApplication.entity.LoanApplication;
import com.barclays.homeLoanApplication.repository.LoanApplicationRepository;
import com.barclays.homeLoanApplication.service.LoanApplicationService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class LoanApplicationServiceImpl implements LoanApplicationService {

    @Autowired
    private LoanApplicationRepository loanApplicationRepository;

    @Override
    public String saveLoanApplication(LoanApplication loanApplication) {
        double maximumEligibleLoaAmount = (double) (loanApplication.getApplicantMonthlySalary() * 50);

        if(loanApplication.getLoanTenure() < LoanApplicationConstants.MAXIMUM_LOAN_TENURE || loanApplication.getLoanTenure() > LoanApplicationConstants.MAXIMUM_LOAN_TENURE) {
            loanApplication.setLoanStatus("Cancelled");
            loanApplicationRepository.save(loanApplication);
            return "Loan Application is cancelled as the loan tenure must be between " + LoanApplicationConstants.MINIMUM_LOAN_TENURE + " years to " + LoanApplicationConstants.MAXIMUM_LOAN_TENURE + " years.";
        }

        if(loanApplication.getLoanAmount() <= maximumEligibleLoaAmount) {
            loanApplication.setLoanStatus("Approved");
            loanApplicationRepository.save(loanApplication);
            double EMI = ((loanApplication.getLoanAmount() * 0.12/12 * Math.pow((1 + LoanApplicationConstants.RATE_OF_INTEREST/12) ,loanApplication.getLoanTenure()*12)) / (Math.pow((1 + LoanApplicationConstants.RATE_OF_INTEREST/12), (loanApplication.getLoanTenure()*12)) -1));
            return "Your loan application has been sanctioned with the EMI : " + EMI;
        }

        loanApplication.setLoanStatus("Cancelled");
        loanApplicationRepository.save(loanApplication);
        return "Your loan can't be sanctioned, however you are eligible for a loan of maximum " +maximumEligibleLoaAmount + "/- Rs";
    }

    @Override
    public List<LoanApplication> getAllLoanApplications() {
        return loanApplicationRepository.findAll();
    }
}
