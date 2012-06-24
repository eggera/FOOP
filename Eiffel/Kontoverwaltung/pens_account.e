note
	description: "Summary description for {PENS_ACCOUNT}."
	author: "Andreas Egger"
	date: "$Date$"
	revision: "$Revision$"

class
	PENS_ACCOUNT

inherit
	ACCOUNT
		rename
			make as make_account

		export {NONE} addAccSigner

		redefine
			out
	end


create
	make


feature
	-- constructor
	make(owner: PENSIONIST number: INTEGER balance, creditLine: INTEGER c_interest, d_interest: DOUBLE)
	do
		create pens_limits
		makeLimits(owner, number, balance, creditLine, c_interest, d_interest, pens_limits)
	end

feature
	pens_limits: PENS_ACCOUNT_LIMITS

feature
	out: STRING
	do
		Result := "PENSIONIST ACCOUNT [Owner: " + acc_owner.name + ", Account Number: " + acc_number.out + ", Account Balance: " + acc_balance.out + ", "
		Result := Result + "Credit Line: " + credit_line.out + ", Credit Interest: " + credit_interest.out + ", Debit Interest: " + debit_interest.out + "]"
	end

end
