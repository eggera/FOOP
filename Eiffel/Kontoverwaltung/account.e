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
		make(owner: PERSON number: INTEGER balance, maxDepth: DOUBLE )

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

		acc_balance:	DOUBLE  assign setBalance
		max_depth:		DOUBLE  assign setMaxDepth

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

		setMaxDepth(max: DOUBLE)
		do
			max_depth := max
		ensure
			forceSetMaxAmount: max_depth = max
		end

		setBalance(balance: DOUBLE)
		do
			acc_balance := balance
		ensure
			forceSetBalance: acc_balance = balance
		end

		addAccSigner(signer: PERSON)
		do
			acc_signers.extend(signer)
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
end
