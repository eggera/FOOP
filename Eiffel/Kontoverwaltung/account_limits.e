note
	description: "Summary description for {ACCOUNT_LIMITS}."
	author: ""
	date: "$Date$"
	revision: "$Revision$"

class
	ACCOUNT_LIMITS
	

feature
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

end
