note
	description: "Account contains information about a bank client"
	author: "Raunig Stefan"
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

		require
			--creditLine has to be negativ
			creditLineLessThenZero: creditLine < 0
			--no credit under creditLine
			keepBalance: balance >= creditLine
	--	local
	--		my_array : ARRAY[INTEGER]
	--		my_list:	LINKED_LIST[PERSON]

		do
			--make constructor

			create limits

			setAccOwner(owner)
			setAccNumber(number)
			setCreditLine(creditLine)
			setBalance(balance)
			setCreditInterest(c_interest)
			setDebitInterest(d_interest)

			create acc_signers.make
			addAccSigner(owner)

	--		create my_array.make_empty
	--		create my_list.make
		end


	--access
	feature
		-- assigner command for direct assignment of variables
		acc_owner:		PERSON 	assign setAccOwner
		acc_number:		INTEGER assign setAccNumber
		acc_signers:	LINKED_LIST[PERSON]

		acc_balance:	INTEGER  assign setBalance
		credit_line:	INTEGER  assign setCreditLine

		--TODO limit and such

		credit_interest:DOUBLE assign setCreditInterest
		debit_interest: DOUBLE assign setDebitInterest

		--account limits
		limits: ACCOUNT_LIMITS


	--element change
	feature
		setAccOwner(owner: PERSON)
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
		do
			acc_signers.extend(signer)
		end

		setCreditLine(cLine: INTEGER)
		require
			cLine <= limits.min_credit_line
			cLine >= limits.max_credit_line
		do
			credit_line := cLine
		end

		setCreditInterest(c_interest: DOUBLE)
		require
			c_interest >= limits.min_credit_interest
			c_interest <= limits.max_credit_interest
		do
			credit_interest := c_interest
		end

		setDebitInterest(d_interest: DOUBLE)
		require
			d_interest >= limits.min_debit_interest
			d_interest <= limits.max_debit_interest
		do
			debit_interest := d_interest
		end


	-- account movements
	feature
		deposit(amount: INTEGER)
		require
			positiveAmount: amount >= 0
		do
			acc_balance := acc_balance + amount
		ensure
			depositOK: acc_balance = old acc_balance + amount
		end

		debit(amount: INTEGER)
		require
			positiveAmount: amount >= 0
			allowed: acc_balance - amount >= credit_line
		do
			acc_balance := acc_balance - amount
		ensure
			debitOK: acc_balance = old acc_balance - amount
		end

		transfer(amount: INTEGER other_acc: ACCOUNT)
		require
			positiveAmount: amount >= 0
			allowed: acc_balance - amount >= credit_line
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
	balanceOK: acc_balance >= credit_line

end
