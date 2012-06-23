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
		--preprcessor for function
		make

	feature
		make(owner: PERSON number: INTEGER balance, maxAmount: DOUBLE )
		require
			--maxAmount has to be negativ
			maxAmountLessThenZero: maxAmount < 0
			--no credit under maxAmount
			keepBalance: balance >= maxAmount
		do
			--make constructor
			setAccOwner(owner)
			setAccNumber(number)
			setMaxAmount(maxAmount)
			setBalance(balance)
		ensure
			forceSetMaxAmount: acc_balance = balance
			forceSetBalance:   max_amount  = maxAmount
		end

	feature
		--access
		-- assigner command for direct assignment of variables
		acc_owner:		PERSON 	assign setAccOwner
		acc_number:		INTEGER assign setAccNumber

		acc_balance:	DOUBLE  assign setBalance
		max_amount:		DOUBLE  assign setMaxAmount

		--TODO limit and such
	feature
		--element change
		setAccOwner(owner: PERSON)
		do
			--setName
			acc_owner := owner
		end

		setAccNumber(number: INTEGER)
		do
			--setAccNumber
			acc_number := number
		end

		setMaxAmount(max: DOUBLE)
		do
			max_amount := max
		ensure
			forceSetMaxAmount: max_amount = max
		end

		setBalance(balance: DOUBLE)
		do
			acc_balance := balance
		ensure
			forceSetBalance: acc_balance = balance
		end

	feature
		--output
		out: STRING
		do
			Result := "ACCOUNT [Name: " + acc_owner.name + ", Account Number: " + acc_number.out + ", Account Balance: " + acc_balance.out + ", Account Maximum: " + max_amount.out + "]"
		end
end
