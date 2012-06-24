note
	description: "Summary description for {PENS_ACCOUNT_LIMITS}."
	author: "Andreas Egger"
	date: "$Date$"
	revision: "$Revision$"

class
	PENS_ACCOUNT_LIMITS

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
	p_min_credit_interest: DOUBLE = 1.5
	p_max_credit_interest: DOUBLE = 6.0

	p_min_debit_interest: DOUBLE = 1.5
	p_max_debit_interest: DOUBLE = 11.0

	-- credit line
	p_min_credit_line: INTEGER = -100
	p_max_credit_line: INTEGER = -1500

	p_minimum_amount: INTEGER = 1


-- retrieval methods
feature
	minCreditInterest: DOUBLE
	do
		Result := p_min_credit_interest
	end

	maxCreditInterest: DOUBLE
	do
		Result := p_max_credit_interest
	end

	minDebitInterest: DOUBLE
	do
		Result := p_min_debit_interest
	end

	maxDebitInterest: DOUBLE
	do
		Result := p_max_debit_interest
	end

	minCreditLine: INTEGER
	do
		Result := p_min_credit_line
	end

	maxCreditLine: INTEGER
	do
		Result := p_max_credit_line
	end

	minimumAmount: INTEGER
	do
		Result := p_minimum_amount
	end

end
