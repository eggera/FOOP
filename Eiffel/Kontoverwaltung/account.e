note
	description: "Account contains information about a bank client"
	author: "Raunig Stefan, Andreas Egger"
	date: "$Date$"
	revision: "$Revision$"

class
	ACCOUNT

inherit
	ANY
		redefine
			out
		end


	create
		--preprocessor for function
		make


	feature
		make(owner: PERSON number: INTEGER balance, creditLine: INTEGER c_interest, d_interest: DOUBLE)

		do
			create acc_limits
			makeLimits (owner, number, balance, creditline, c_interest, d_interest, acc_limits)
		end


	feature {NONE}

		makeLimits(owner: PERSON number: INTEGER balance, creditLine: INTEGER c_interest, d_interest: DOUBLE limits: ACCOUNT_LIMITS)
		require
			--creditLine has to be negativ
			creditLineLessThenZero: creditLine < 0
			--no credit under creditLine
			keepBalance: balance >= creditLine
			--limit not Void
			limitNotVoid: limits /= Void
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
		-- debug: assigner command for direct assignment of variables
		-- acc_owner:		PERSON 	assign setAccOwner
		-- acc_number:		INTEGER assign setAccNumber
		acc_owner:		PERSON
		acc_number:		INTEGER
		acc_signers:	LINKED_LIST[PERSON]

		acc_balance:	INTEGER
		credit_line:	INTEGER

		credit_interest:DOUBLE
		debit_interest: DOUBLE

		--account limits
		acc_limits: 	ACCOUNT_LIMITS


	--element change
	feature
		setAccOwner(owner: PERSON)
		require
			ownerNotVoid: owner /= Void
		do
			--setOwner
			acc_owner := owner
		end

		setAccNumber(number: INTEGER)
		do
			--setAccNumber
			acc_number := number
		end

		addAccSigner(signer: PERSON)
		require
			signerNotVoid: signer /= Void
		do
			acc_signers.extend(signer)
		end

		setCreditLine(cLine: INTEGER)
		require
			aboveMinCreditLine: cLine <= acc_limits.minCreditLine
			belowMaxCreditLine: cLine >= acc_limits.maxCreditLine
		do
			credit_line := cLine
		end

		setCreditInterest(c_interest: DOUBLE)
		require
			aboveMinCreditInterest: c_interest >= acc_limits.minCreditInterest
			belowMaxCreditInterest: c_interest <= acc_limits.maxCreditInterest
		do
			credit_interest := c_interest
		end

		setDebitInterest(d_interest: DOUBLE)
		require
			aboveMinDebitInterest: d_interest >= acc_limits.minDebitInterest
			belowMaxDebitInterest: d_interest <= acc_limits.maxDebitInterest
		do
			debit_interest := d_interest
		end


	-- account movements
	feature
		deposit(amount: INTEGER)
		require
			positiveAmount: amount >= 0
			minimumAmount: 	amount >= acc_limits.minimumAmount
		do
			acc_balance := acc_balance + amount
		ensure
			depositOK: acc_balance = old acc_balance + amount
		end

		debit(amount: INTEGER)
		require
			positiveAmount: amount >= 0
			minimumAmount: 	amount >= acc_limits.minimumAmount
			debitValid:		acc_balance - amount >= credit_line
		do
			acc_balance := acc_balance - amount
		ensure
			debitOK: acc_balance = old acc_balance - amount
		end

		transfer(amount: INTEGER other_acc: ACCOUNT)
		require
			positiveAmount: amount >= 0
			minimumAmount: 	amount >= acc_limits.minimumAmount
			transferValid: 	acc_balance - amount >= credit_line
		do
			debit(amount)
			other_acc.deposit(amount)
		end


	feature {NONE}
		-- non public method to set the balance
		setBalance(balance: INTEGER)
		do
			acc_balance := balance
		ensure
			forceSetBalance: acc_balance = balance
		end


	feature
		--output
		out: STRING
		do
			Result := "ACCOUNT [Signers: "
			across acc_signers as signers

			loop
				Result := Result + signers.item.name + ", "
			end

			Result := Result + "Account Number: " + acc_number.out + ", Account Balance: " + acc_balance.out + ", "
			Result := Result + "Credit Line: " + credit_line.out + ", Credit Interest: " + credit_interest.out + ", Debit Interest: " + debit_interest.out + "]"
		end


invariant
	balanceOK: 				acc_balance >= credit_line
--	aboveMinCreditLine: 	credit_line <= acc_limits.minCreditLine
--	belowMaxCreditLine: 	credit_line >= acc_limits.maxCreditLine
	aboveMinCreditInterest: credit_interest >= acc_limits.minCreditInterest
	belowMaxCreditInterest: credit_interest <= acc_limits.maxCreditInterest
	aboveMinDebitInterest: 	debit_interest >= acc_limits.minDebitInterest
	belowMaxDebitInterest: 	debit_interest <= acc_limits.maxDebitInterest

end
