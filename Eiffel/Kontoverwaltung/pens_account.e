note
	description: "Summary description for {PENS_ACCOUNT}."
	author: "Andreas Egger"
	date: "$Date$"
	revision: "$Revision$"

class
	PENS_ACCOUNT

inherit
	ACCOUNT
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
		-- constructor
		make(owner: PENSIONIST number: INTEGER balance, creditLine: INTEGER c_interest, d_interest: DOUBLE)
		do
			create pens_limits
			makeLimits(owner, number, balance, creditLine, c_interest, d_interest, pens_limits)
		end


	feature {NONE}

		makeLimits(owner: PENSIONIST number: INTEGER balance, creditLine: INTEGER c_interest, d_interest: DOUBLE limits: PENS_ACCOUNT_LIMITS)

		do
			--make constructor

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
		acc_owner: PENSIONIST

		pens_limits: PENS_ACCOUNT_LIMITS


	--element change
	feature
		setAccOwner(owner: PENSIONIST)
		do
			--setOwner
			acc_owner := owner
		end

		setCreditLine(cLine: INTEGER)
		require else
			aboveMinCreditLine: cLine <= -100
			belowMaxCreditLine: cLine >= -1500
		do
			credit_line := cLine
		end

		setCreditInterest(c_interest: DOUBLE)
		require else
			aboveMinCreditInterest: c_interest >= 1.5
			belowMaxCreditInterest: c_interest <= 6.0
		do
			credit_interest := c_interest
		end

		setDebitInterest(d_interest: DOUBLE)
		require else
			aboveMinDebitInterest: d_interest >= 1.5
			belowMaxDebitInterest: d_interest <= 11.0
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
		addAccSigner(signer: PENSIONIST)
		do
			acc_signers.extend (signer)
		end


	feature
		out: STRING
		do
			Result := "PENSIONIST ACCOUNT [Owner: " + acc_owner.name + ", Account Number: " + acc_number.out + ", Account Balance: " + acc_balance.out + ", "
			Result := Result + "Credit Line: " + credit_line.out + ", Credit Interest: " + credit_interest.out + ", Debit Interest: " + debit_interest.out + "]"
		end

end
