note
	description: "Summary description for {STUD_ACCOUNT}."
	author: "Andreas Egger"
	date: "$Date$"
	revision: "$Revision$"

class
	STUD_ACCOUNT

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
	make(owner: STUDENT number: INTEGER balance, creditLine: INTEGER c_interest, d_interest: DOUBLE)
	do
		create stud_limits
		print ("stud limits: "+ stud_limits.mincreditline.out +", "+ stud_limits.maxCreditLine.out +"%N")
		makeLimits(owner, number, balance, creditLine, c_interest, d_interest, stud_limits)
	end

--access
feature
	stud_limits: STUD_ACCOUNT_LIMITS

feature
	out: STRING
	do
		Result := "STUDENT ACCOUNT [Owner: " + acc_owner.name + ", Account Number: " + acc_number.out + ", Account Balance: " + acc_balance.out + ", "
		Result := Result + "Credit Line: " + credit_line.out + ", Credit Interest: " + credit_interest.out + ", Debit Interest: " + debit_interest.out + "]"
	end

end
