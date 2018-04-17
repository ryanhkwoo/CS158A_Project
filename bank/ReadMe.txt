--Ryan Woo --CS158A --Completed project with Customer Login option  
--Create database named "CS158A", you can run command lines below in DB2 CLP
--db2 create database CS158A /* assume successfully created so run command below */
--db2 connect to CS158A /* now you have created & connected to database "CS158A" */
--Assume that the database exists before the application is run,

The schema is created on the first run 
by running a test query selecting from the customer table. 
The schema is either there completely or not, if it is partially there 
then a manual drop is required so on first run the schema is generated.
DB2 CLP -> db2 -tvf drop.sql

The script that generates the schema is create.sql, 
I then pass through a command to remove all newlines and put in the 
bank properties so it will be run on the first application run.

Some useful command lines
==========================
// in Linux or Mac?
generate one liner query for properties:
tr -d '\n' < create.sql
// in Windows
use notepad to open a file. Notepad, a text file with multiple lines of text 
appears concatenated into a single line because of 2 char: carriage return.
PowerShell in directories >>>(gc create.sql) -join ''

// compile and run in one line (linux shell): or Mac (Terminal)
javac bank.java && java -cp :./db2jcc4.jar bank bank.properties
// compile and run in one line (Windows cmd):
javac bank.java && java bank bank.properties

drop.sql is list of commands that can be copy pasted on the interactive db2 shell 
to drop everything Starting from a fresh database either run each SQL statement.
db2 DROP TABLE Customer
db2 DROP TABLE Account
db2 DROP VIEW  CustomerAccountsSummary
(or)
db2 connect to CS158A
db2 -tvf drop.sql
(or)
db2 drop database CS158A 
// if you cannot drop (delete) database, try command lines below in order to refresh
db2 quiesce db immediate force connections
db2 connect reset
db2 terminate
db2 force application all
db2stop
db2start
db2 drop database CS158A

/////////////////////////////////////////////////////////////////////////////////

example run:
--------------------------------------------------------
--Gender, database take CAPITAL LETTER EITHER 'M' OR 'F'
--------------------------------------------------------

C:\Users\Dell\Desktop\DifferentVersionWithCustomerLoginInterface>
javac bank.java && java bank bank.properties

Initializing schema

Welcome to the Self Services Banking System! - Main Menu
--------------------------------------------------------

1. New Customer
2. Customer Login
3. Exit

Your choice: 1
Name: AA
Gender: M
Age: 20
Pin: 12
Customer succefully created:
Name: AA, Gender: M, Age: 20, Pin: 12
Customer ID: #100

Welcome to the Self Services Banking System! - Main Menu
--------------------------------------------------------

1. New Customer
2. Customer Login
3. Exit

Your choice: 1
Name: BB
Gender: F
Age: 21
Pin: 34
Customer succefully created:
Name: BB, Gender: F, Age: 21, Pin: 34
Customer ID: #101

Welcome to the Self Services Banking System! - Main Menu
--------------------------------------------------------

1. New Customer
2. Customer Login
3. Exit

Your choice: 1
Name: CC
Gender: M
Age: 22
Pin: 56
Customer succefully created:
Name: CC, Gender: M, Age: 22, Pin: 56
Customer ID: #102

Welcome to the Self Services Banking System! - Main Menu
--------------------------------------------------------

1. New Customer
2. Customer Login
3. Exit

Your choice: 1
Name: DD
Gender: F
Age: 23
Pin: 78
Customer succefully created:
Name: DD, Gender: F, Age: 23, Pin: 78
Customer ID: #103

Welcome to the Self Services Banking System! - Main Menu
--------------------------------------------------------

1. New Customer
2. Customer Login
3. Exit

Your choice: 2
ID: 100
Pin: 12

Self Services Banking System - Customer Menu
--------------------------------------------------------

Welcome AA (#100)
1. Open Account
2. Close Account
3. Deposit
4. Withdraw
5. Transfer
6. Account Summary
7. Exit

Your choice: 1
Customer ID (Yours is #100): 100
Account type [C]hecking/[S]avings: C
Initial deposit: 250
Account succefully created!
Customer ID: 100, Status: A, Type: C
Account no:1000

Self Services Banking System - Customer Menu
--------------------------------------------------------

Welcome AA (#100)
1. Open Account
2. Close Account
3. Deposit
4. Withdraw
5. Transfer
6. Account Summary
7. Exit

Your choice: 3
Account no: 1000
Deposit amount: 50
Deposit successful

Self Services Banking System - Customer Menu
--------------------------------------------------------

Welcome AA (#100)
1. Open Account
2. Close Account
3. Deposit
4. Withdraw
5. Transfer
6. Account Summary
7. Exit

Your choice: 6
Account summary for Customer with ID: 100
#1000 - 300 $
Total: 300 $

Self Services Banking System - Customer Menu
--------------------------------------------------------

Welcome AA (#100)
1. Open Account
2. Close Account
3. Deposit
4. Withdraw
5. Transfer
6. Account Summary
7. Exit

Your choice: 4
Account no: 1000
Withdrawal amount: 20
Withdraw successful

Self Services Banking System - Customer Menu
--------------------------------------------------------

Welcome AA (#100)
1. Open Account
2. Close Account
3. Deposit
4. Withdraw
5. Transfer
6. Account Summary
7. Exit

Your choice: 6
Account summary for Customer with ID: 100
#1000 - 280 $
Total: 280 $

Self Services Banking System - Customer Menu
--------------------------------------------------------

Welcome AA (#100)
1. Open Account
2. Close Account
3. Deposit
4. Withdraw
5. Transfer
6. Account Summary
7. Exit

Your choice: 1
Customer ID (Yours is #100): 100
Account type [C]hecking/[S]avings: S
Initial deposit: 50
Account succefully created!
Customer ID: 100, Status: A, Type: S
Account no:1001

Self Services Banking System - Customer Menu
--------------------------------------------------------

Welcome AA (#100)
1. Open Account
2. Close Account
3. Deposit
4. Withdraw
5. Transfer
6. Account Summary
7. Exit

Your choice: 7

Welcome to the Self Services Banking System! - Main Menu
--------------------------------------------------------

1. New Customer
2. Customer Login
3. Exit

Your choice: 2
ID: 101
Pin: 34

Self Services Banking System - Customer Menu
--------------------------------------------------------

Welcome BB (#101)
1. Open Account
2. Close Account
3. Deposit
4. Withdraw
5. Transfer
6. Account Summary
7. Exit

Your choice: 1
Customer ID (Yours is #101): 101
Account type [C]hecking/[S]avings: C
Initial deposit: 150
Account succefully created!
Customer ID: 101, Status: A, Type: C
Account no:1002

Self Services Banking System - Customer Menu
--------------------------------------------------------

Welcome BB (#101)
1. Open Account
2. Close Account
3. Deposit
4. Withdraw
5. Transfer
6. Account Summary
7. Exit

Your choice: 5
Account From no: 100
Account To no: 101
Transfer amount: 80
Invalid Accounts

Self Services Banking System - Customer Menu
--------------------------------------------------------

Welcome BB (#101)
1. Open Account
2. Close Account
3. Deposit
4. Withdraw
5. Transfer
6. Account Summary
7. Exit

Your choice: 6
Account summary for Customer with ID: 101
#1002 - 150 $
Total: 150 $

Self Services Banking System - Customer Menu
--------------------------------------------------------

Welcome BB (#101)
1. Open Account
2. Close Account
3. Deposit
4. Withdraw
5. Transfer
6. Account Summary
7. Exit

Your choice: 5
Account From no: 1002
Account To no: 1000
Transfer amount: 80
Transfer successful

Self Services Banking System - Customer Menu
--------------------------------------------------------

Welcome BB (#101)
1. Open Account
2. Close Account
3. Deposit
4. Withdraw
5. Transfer
6. Account Summary
7. Exit

Your choice: 6
Account summary for Customer with ID: 101
#1002 - 70 $
Total: 70 $

Self Services Banking System - Customer Menu
--------------------------------------------------------

Welcome BB (#101)
1. Open Account
2. Close Account
3. Deposit
4. Withdraw
5. Transfer
6. Account Summary
7. Exit

Your choice: 7

Welcome to the Self Services Banking System! - Main Menu
--------------------------------------------------------

1. New Customer
2. Customer Login
3. Exit

Your choice: 2
ID: 101
Pin: 34

Self Services Banking System - Customer Menu
--------------------------------------------------------

Welcome BB (#101)
1. Open Account
2. Close Account
3. Deposit
4. Withdraw
5. Transfer
6. Account Summary
7. Exit

Your choice: 2
Account no: 1002
Account successfully closed

Self Services Banking System - Customer Menu
--------------------------------------------------------

Welcome BB (#101)
1. Open Account
2. Close Account
3. Deposit
4. Withdraw
5. Transfer
6. Account Summary
7. Exit

Your choice: 7

Welcome to the Self Services Banking System! - Main Menu
--------------------------------------------------------

1. New Customer
2. Customer Login
3. Exit

Your choice: 2
ID: 0
Pin: 0

Self Services Banking System - Administrator Main Menu
--------------------------------------------------------

1. Account Summary for a Customer
2. Report A :: Customer information with total Balance in Decreasing Order
3. Report B :: Find the Average Total Balance Between Age Groups
4. Exit

Your choice: 2
Report A :: Customer information with total Balance in Decreasing Order
-----------------------------------------------------------------------

#102: CC  M 22yrs old, Total Balance: 0 $
#103: DD  F 23yrs old, Total Balance: 0 $
#100: AA  M 20yrs old, Total Balance: 410 $
#101: BB  F 21yrs old, Total Balance: 0 $

Self Services Banking System - Administrator Main Menu
--------------------------------------------------------

1. Account Summary for a Customer
2. Report A :: Customer information with total Balance in Decreasing Order
3. Report B :: Find the Average Total Balance Between Age Groups
4. Exit

Your choice: 3
Report B :: Find the Average Total Balance Between Age Groups
-----------------------------------------------------------------------

Min age: 19
Max age: 25
Average balance: 102.5 $

Self Services Banking System - Administrator Main Menu
--------------------------------------------------------

1. Account Summary for a Customer
2. Report A :: Customer information with total Balance in Decreasing Order
3. Report B :: Find the Average Total Balance Between Age Groups
4. Exit

Your choice: 4

Welcome to the Self Services Banking System! - Main Menu
--------------------------------------------------------

1. New Customer
2. Customer Login
3. Exit

Your choice: 3
Thanks for using the Self Services Banking System!

C:\Users\Dell\Desktop\DifferentVersionWithCustomerLoginInterface>