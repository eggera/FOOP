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
			addAccSigner,
			deposit,
			debit,
			transfer,
			setCreditLine,
			setCreditInterest,
			setDebitInterest
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

		setCreditLine(cLine: INTEGER)
		require else
			aboveMinCreditLine: cLine <= -200
			belowMaxCreditLine: cLine >= -2000
		do
			credit_line := cLine
		end

		setCreditInterest(c_interest: DOUBLE)
		require else
			aboveMinCreditInterest: c_interest >= 1.0
			belowMaxCreditInterest: c_interest <= 5.0
		do
			credit_interest := c_interest
		end

		setDebitInterest(d_interest: DOUBLE)
		require else
			aboveMinDebitInterest: d_interest >= 2.0
			belowMaxDebitInterest: d_interest <= 12.0
		do
			debit_interest := d_interest
		end



	-- account movements
	feature
		deposit(amount: INTEGER)
		require else
			minimumAmount: 	amount >= 1
		do
			acc_balance := acc_balance + amount
		end

		debit(amount: INTEGER)
		require else
			minimumAmount: 	amount >= 1
		do
			acc_balance := acc_balance - amount
		end

		transfer(amount: INTEGER other_acc: ACCOUNT)
		require else
			minimumAmount: 	amount >= 1
		do
			debit(amount)
			other_acc.deposit(amount)
		end


	feature {NONE}
		addAccSigner(signer: STUDENT)
		do
			acc_signers.extend (signer)
		end



	feature
		out: STRING
		do
			Result := "STUDENT ACCOUNT [Owner: " + acc_owner.name + ", Account Number: " + acc_number.out + ", Account Balance: " + acc_balance.out + ", "
			Result := Result + "Credit Line: " + credit_line.out + ", Credit Interest: " + credit_interest.out + ", Debit Interest: " + debit_interest.out + "]"
		end

end
