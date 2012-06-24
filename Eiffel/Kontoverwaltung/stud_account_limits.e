note
	description: "Summary description for {STUD_ACCOUNT_LIMITS}."
	author: ""
	date: "$Date$"
	revision: "$Revision$"

class
	STUD_ACCOUNT_LIMITS

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
	max_credit_interest: DOUBLE = 5.0

	min_debit_interest: DOUBLE = 2.0
	max_debit_interest: DOUBLE = 10.0

	-- credit line
	min_credit_line: INTEGER = -200
	max_credit_line: INTEGER = -2000

	minimum_amount: INTEGER = 1

end
