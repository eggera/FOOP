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

			owner: PERSON
			student: STUDENT
			pensionist: PENSIONIST
		do
			print ("%N")
			-- precondition violation
			-- create acc1.make( "Person1", 12345678 , 2000, 1000)
			-- create acc1.make( "Person1", 12345678 , -2000, -1000)

			create student.make ("student1")
			create pensionist.make ("pensionist1")
			owner := student


			create acc1.make( student, 12345678 , 2000, -1000)
			acc1.addaccsigner (pensionist)
			print (acc1.out + "%N")
			create acc2.make( pensionist, 12456788 , 2000, -1000)
			print (acc2.out + "%N")
		end
end
