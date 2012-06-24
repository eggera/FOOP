note
	description: "Summary description for {PENS_ACCOUNT_LIMITS}."
	author: ""
	date: "$Date$"
	revision: "$Revision$"

class
	PENS_ACCOUNT_LIMITS

--inherit
--	ACCOUNT_LIMITS
--		redefine
--			min_credit_interest,
--			max_credit_interest,
--			min_debit_interest,
--			max_debit_interest,
--			min_credit_line,
--			max_credit_line,
--			minimum_amount
--		end

feature
	-- limits

	-- interest (verzinsung)
	min_credit_interest: DOUBLE = 1.5
	max_credit_interest: DOUBLE = 6.0

	min_debit_interest: DOUBLE = 1.5
	max_debit_interest: DOUBLE = 9.0

	-- credit line
	min_credit_line: INTEGER = -100
	max_credit_line: INTEGER = -1500

	minimum_amount: INTEGER = 1

end
