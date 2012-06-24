note
	description: "Summary description for {PENS_ACCOUNT}."
	author: ""
	date: "$Date$"
	revision: "$Revision$"

class
	PENS_ACCOUNT

inherit
	ACCOUNT
		rename
			make as make_account
		end

create
	make

feature
	make(owner: PENSIONIST number: INTEGER balance, creditLine: INTEGER c_interest, d_interest: DOUBLE)
	do
		make_account(owner, number, balance, creditLine, c_interest, d_interest)
	end

end
