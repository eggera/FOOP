note
	description : "Root class of foop_3 Accounting Exercise"
	date        : "$Date$"
	revision    : "$Revision$"

class
	APPLICATION

inherit
	ARGUMENTS

create
	make

feature {NONE} -- Initialization

	make
			-- Run application.
		local
			acc1: ACCOUNT
			acc2: ACCOUNT
		do
			print ("%N")
			-- precondition violation
			-- create acc1.make( "Person1", 12345678 , 2000.00, 1000.00)
			-- create acc1.make( "Person1", 12345678 , -2000.00, -1000.00)
			create acc1.make( "Person1", 12345678 , 2000.00, -1000.00)
			print (acc1.out + "%N")
			create acc2.make( "Person2", 12456788 , 2000.00, -1000.00)
			print (acc2.out + "%N")
		end
end
