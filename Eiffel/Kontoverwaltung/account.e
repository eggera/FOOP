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
		make(owner: PERSON number: INTEGER balance, maxDepth: INTEGER )

		require
			--maxDepth has to be negativ
			maxDepthLessThenZero: maxDepth < 0
			--no credit under maxDepth
			keepBalance: balance >= maxDepth
	--	local
	--		my_array : ARRAY[INTEGER]
	--		my_list:	LINKED_LIST[PERSON]

		do
			--make constructor
			setAccOwner(owner)
			setAccNumber(number)
			setMaxDepth(maxDepth)
			setBalance(balance)

	--		create my_array.make_empty
	--		create my_list.make
			create acc_signers.make
			addAccSigner(owner)
		ensure
			--forceSetBalance: acc_balance = balance
			--forceSetMaxDepth:   max_depth  = maxDepth
		end

	feature
		--access
		-- assigner command for direct assignment of variables
		acc_owner:		PERSON 	assign setAccOwner
		acc_number:		INTEGER assign setAccNumber
		acc_signers:	LINKED_LIST[PERSON]

		acc_balance:	INTEGER  assign setBalance
		max_depth:		INTEGER  assign setMaxDepth

		--TODO limit and such

		habenzins:		DOUBLE = 1.3
		sollzins:		DOUBLE = 8.2

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

		setMaxDepth(max: INTEGER)
		do
			max_depth := max
		ensure
			forceSetMaxAmount: max_depth = max
		end

		addAccSigner(signer: PERSON)
		do
			acc_signers.extend(signer)
		end

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
			allowed: acc_balance - amount >= max_depth
		do
			acc_balance := acc_balance - amount
		ensure
			debitOK: acc_balance = old acc_balance - amount
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

			Result := Result + "Account Number: " + acc_number.out + ", Account Balance: " + acc_balance.out + ", Account Maximum: " + max_depth.out + "]"
		end

invariant
	balanceOK: acc_balance >= max_depth

end
