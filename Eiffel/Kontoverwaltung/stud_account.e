note
	description: "Summary description for {STUD_ACCOUNT}."
	author: ""
	date: "$Date$"
	revision: "$Revision$"

class
	STUD_ACCOUNT

inherit
	ACCOUNT
		rename
			make as make_account
		end

create
	make

feature
	make(owner: STUDENT number: INTEGER balance, creditLine: INTEGER c_interest, d_interest: DOUBLE)
	do
		make_account(owner, number, balance, creditLine, c_interest, d_interest)
	end

end
