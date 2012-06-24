note
	description: "Summary description for {TEST}."
	author: ""
	date: "$Date$"
	revision: "$Revision$"

class
	TEST

feature

	-- test procedure
	runTest
	local
		account: ACCOUNT
		stud_account: STUD_ACCOUNT
		pens_account: PENS_ACCOUNT

		owner: PERSON
		student: STUDENT
		pensionist: PENSIONIST
	do
		print ("%N")
		-- precondition violation
		-- create stud_account.make( "Person1", 12345678 , 2000, 1000)
		-- create stud_account.make( "Person1", 12345678 , -2000, -1000)

		create owner.make ("person1")
		create student.make ("student1", "0498432")
		create pensionist.make ("pensionist1")

		create account.make (owner, 987654321, 1000, -1000, 1.4, 8.3)
		create stud_account.make( student, 12345678 , 2000, -1000, 2.0, 8.3)
		create pens_account.make( pensionist, 12456788 , 2000, -1000, 3.1, 10.2)

		account.addAccSigner (pensionist)

		print (account.out + "%N")
		print (stud_account.out + "%N")
		print (pens_account.out + "%N")

		-- precondition violation (2 euro minimum)
		-- stud_account.deposit (1)

		print ("%N stud_account deposit, 1000 %N")
		stud_account.deposit (1000)

		print ("stud_account balance = " + stud_account.acc_balance.out + "%N")
		print ("pens_account balance = " + pens_account.acc_balance.out + "%N")

		print ("%N stud_account -> pens_account transfer, 2000 %N")
		stud_account.transfer (2000, pens_account)

		print ("stud_account balance = " + stud_account.acc_balance.out + "%N")
		print ("pens_account balance = " + pens_account.acc_balance.out + "%N")

	end

end
