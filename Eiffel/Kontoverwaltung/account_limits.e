note
	description: "Summary description for {ACCOUNT_LIMITS}."
	author: "Andreas Egger"
	date: "$Date$"
	revision: "$Revision$"

class
	ACCOUNT_LIMITS


-- members
feature {NONE}
	-- limits

	-- interest (verzinsung)
	min_credit_interest: DOUBLE = 1.0
	max_credit_interest: DOUBLE = 7.0

	min_debit_interest: DOUBLE = 3.0
	max_debit_interest: DOUBLE = 15.0

	-- credit line
	min_credit_line: INTEGER = -500
	max_credit_line: INTEGER = -10000

	minimum_amount: INTEGER = 2


-- retrieval methods
feature
	minCreditInterest: DOUBLE
	do
		Result := min_credit_interest
	end

	maxCreditInterest: DOUBLE
	do
		Result := max_credit_interest
	end

	minDebitInterest: DOUBLE
	do
		Result := min_debit_interest
	end

	maxDebitInterest: DOUBLE
	do
		Result := max_debit_interest
	end

	minCreditLine: INTEGER
	do
		Result := min_credit_line
	end

	maxCreditLine: INTEGER
	do
		Result := max_credit_line
	end

	minimumAmount: INTEGER
	do
		Result := minimum_amount
	end

end
