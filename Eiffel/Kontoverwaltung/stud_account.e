note
	description: "Summary description for {STUD_ACCOUNT}."
	author: "Andreas Egger"
	date: "$Date$"
	revision: "$Revision$"

class
	STUD_ACCOUNT

inherit
	ACCOUNT
--		rename
--			make as make_account

		export {NONE} addAccSigner

		redefine
			make,
			makeLimits,
			out,
			acc_owner,
			setAccOwner,
			addAccSigner
	end

create
	make

feature
	make(owner: STUDENT number: INTEGER balance, creditLine: INTEGER c_interest, d_interest: DOUBLE)
	do
		create stud_limits
		makeLimits(owner, number, balance, creditLine, c_interest, d_interest, stud_limits)
	end

feature {NONE}

	makeLimits(owner: STUDENT number: INTEGER balance, creditLine: INTEGER c_interest, d_interest: DOUBLE limits: STUD_ACCOUNT_LIMITS)
--	require
--		--creditLine has to be negativ
--		creditLineLessThenZero: creditLine < 0
--		--no credit under creditLine
--		keepBalance: balance >= creditLine
--		--limit not Void
--		limitNotVoid: limits /= Void
	do
		--make constructor

--		print("limits: "+ limits.minCreditLine.out + " to "  + limits.maxCreditLine.out + "%N")
		acc_limits := limits

		setAccOwner(owner)
		setAccNumber(number)
		setCreditLine(creditLine)
		setBalance(balance)
		setCreditInterest(c_interest)
		setDebitInterest(d_interest)

		create acc_signers.make
		addAccSigner(owner)

	end

--access
feature
	acc_owner: STUDENT

	stud_limits: STUD_ACCOUNT_LIMITS

--element change
feature
	setAccOwner(owner: STUDENT)
	do
		--setOwner
		acc_owner := owner
	end


feature {NONE}
	addAccSigner(signer: STUDENT)
	once
		acc_signers.extend (signer)
	end



feature
	out: STRING
	do
		Result := "STUDENT ACCOUNT [Owner: " + acc_owner.name + ", Account Number: " + acc_number.out + ", Account Balance: " + acc_balance.out + ", "
		Result := Result + "Credit Line: " + credit_line.out + ", Credit Interest: " + credit_interest.out + ", Debit Interest: " + debit_interest.out + "]"
	end

end
