note
	description: "Summary description for {TEST}."
	author: "Andreas Egger"
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

		accounts: LINKED_LIST[ACCOUNT]

		owner: PERSON
		student: STUDENT
		pensionist: PENSIONIST

		student_p : STUDENT
	do
		print ("%N")
		-- precondition violation
		-- create stud_account.make( "Person1", 12345678 , 2000, 1000)
		-- create stud_account.make( "Person1", 12345678 , -2000, -1000)

		create owner.make ("person1")
		create student.make ("student1", "0498432")
		create pensionist.make ("pensionist1")

		create account.make ( owner, 987654321, 1000, -10000, 1.4, 8.3)
		create stud_account.make( student, 12345678 , 2000, -1000, 2.0, 8.3)
		create pens_account.make( pensionist, 12456788 , 2000, -1000, 3.1, 10.2)

		account.addAccSigner (pensionist)

		print (account.out + "%N%N")
		print (stud_account.out + "%N%N")
		print (pens_account.out + "%N%N")


		-- invalid assignment
		create accounts.make
		accounts.extend (account)
		accounts.extend (stud_account)
		accounts.extend (pens_account)

		create student_p.make ("student2", "0394875")
		owner := student_p

		print( accounts.at (1).acc_owner.name )
		accounts.at (2).setAccOwner (owner)

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

		testAccounts

	end


-- this method is used to subjectively test the runtime of this class
-- see the difference when turning assertions off and on (via the project properties)
	testAccounts
	local
        j, i : INTEGER
		owner: PERSON
        acc : ACCOUNT

	do
		create owner.make ("person1")

		create acc.make ( owner, 987654321, 1000, -10000, 1.4, 8.3)

		print("test accounts %N")

        from j := 1 until j > 20 loop

                from i := 0 until i > 10000 loop
                        acc.deposit (250)
                        i:=i+1
                end

                from i := 0 until i > 10000 loop
                        acc.debit (250)
                        i:=i+1
                end

                print("Iteration " + j.out + "%N")
                j := j+1
        end

        print ("%N")

    end

end
