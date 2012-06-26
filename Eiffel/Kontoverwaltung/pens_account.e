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
			addAccSigner
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
