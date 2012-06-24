note
	description: "Summary description for {STUD_ACCOUNT_LIMITS}."
	author: "Andreas Egger"
	date: "$Date$"
	revision: "$Revision$"

class
	STUD_ACCOUNT_LIMITS

inherit
	ACCOUNT_LIMITS
		redefine
			minCreditInterest,
			maxCreditInterest,
			minDebitInterest,
			maxDebitInterest,
			minCreditLine,
			maxCreditLine,
			minimumAmount
		end

--inherit
--	ACCOUNT_LIMITS
--		rename
--			min_credit_interest as p_min_credit_interest,
--			max_credit_interest as p_max_credit_interest,
--			min_debit_interest as p_min_debit_interest,
--			max_debit_interest as p_max_debit_interest,
--			min_credit_line as p_min_credit_line,
--			max_credit_line as p_max_credit_line,
--			minimum_amount as p_minimum_amount

--		export {NONE} all
--	end

feature {NONE}
	-- limits

	-- interest (verzinsung)
	s_min_credit_interest: DOUBLE = 1.0
	s_max_credit_interest: DOUBLE = 5.0

	s_min_debit_interest: DOUBLE = 2.0
	s_max_debit_interest: DOUBLE = 12.0

	-- credit line
	s_min_credit_line: INTEGER = -200
	s_max_credit_line: INTEGER = -2000

	s_minimum_amount: INTEGER = 1


-- retrieval methods
feature
	minCreditInterest: DOUBLE
	do
		Result := s_min_credit_interest
	end

	maxCreditInterest: DOUBLE
	do
		Result := s_max_credit_interest
	end

	minDebitInterest: DOUBLE
	do
		Result := s_min_debit_interest
	end

	maxDebitInterest: DOUBLE
	do
		Result := s_max_debit_interest
	end

	minCreditLine: INTEGER
	do
		Result := s_min_credit_line
	end

	maxCreditLine: INTEGER
	do
		Result := s_max_credit_line
	end

	minimumAmount: INTEGER
	do
		Result := s_minimum_amount
	end

end
