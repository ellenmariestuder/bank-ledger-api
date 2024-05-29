===============================
## Overview
You are tasked with building a simple bank ledger system that utilizes the [event sourcing](https://martinfowler.com/eaaDev/EventSourcing.html) pattern to maintain a transaction history. The system should allow users to perform basic banking operations such as depositing funds, withdrawing funds, and checking balances. The ledger should maintain a complete and immutable record of all transactions, enabling auditability and reconstruction of account balances at any point in time.

## Details
The [included service.yml](service.yml) is the OpenAPI 3.0 schema to a service we would like you to create and host.

The service accepts two types of transactions:
1) Loads: Add money to a user (credit)

2) Authorizations: Conditionally remove money from a user (debit)

Every load or authorization PUT should return the updated balance following the transaction. Authorization declines should be saved, even if they do not impact balance calculation.


Implement the event sourcing pattern to record all banking transactions as immutable events. Each event should capture relevant information such as transaction type, amount, timestamp, and account identifier.
Define the structure of events and ensure they can be easily serialized and persisted to a data store of your choice. We do not expect you to use a persistent store (you can you in-memory object), but you can if you want. We should be able to bootstrap your project locally to test.

# Candidate README
## Bootstrap instructions
To run this application locally, follow these steps:
1. clone this git repository
`git clone git@github.com:ellenmariestuder/bank-ledger-api.git`
2. navigate to the project directory
`cd bank-ledger-api`
3. start the server! simply run: 
`./make.sh`
_This executes a bash script that compiles the application's source code and runs the `.jar` file._
_When you are finished running the application, hit `control` + `C` to stop the server._

## Design considerations
### API Design
My goal in designing this API was to implement a streamlined, efficient design  without sacrificing readability or ease of use. I also hoped to minimize the number of database calls so that the program wouldn't get bogged down by unnessary transactions. Below are some notes on the design choices I made at the beginning of this project. You'll also find a reflection on my choice of data store in the section following this one. I learned a great deal while working on this project and ultimately, if I were to start over I probably wouldn't have stuck with all of these design choices. Regardless, the app is functional and I had a great time working on it!

 * As the steps for handling both load and auth requests are nearly identical, I wanted to use a **single class/ method to process all requests**. (This was implemented as RestController.process()).
 * Since there are a number of distinct steps involved in processing requests end-to-end, I used a **template pattern** to declare and execute each step in an intuitive, readable way.
 * To streamline shared functionality, the classes to which each transacion type would be sent to be processed would both extend an abstract class ('abstractTransaction') containing the implementation for all shared properties and methods**. Each child class ('loadTransaction' and 'authTransaction') would implement only the functionalities that differed-- namely, approving and denying requests, and calculating the new balance. The very raw notes were: 
    * factory class to return instance of either 'load' or 'auth' transaction type
        - Both will extend abstract 'transaction' class, all method implementations shared except 'response' which will be defined in each sub-class type
        - different instance types will allow for polymorphic implementation of methods in 'TransactionOutcome' class -- namely `calculateApprovedDenied()` and `claculateNewBalance()`
    * **I learned while working on this project about Java's DTO convention, and so now know that bundling properties and methods together in this particular way might have instead been better served by separating the transaction data into its own class.
    * *Ultimately, this particular design idea was not a great choice. I was thinking in my programming language of choice --Python-- and the particular polymorphic  functionality I was aiming for was much more difficult to achieve in Java.*

### Database Design
The Event Source pattern introduces unique database needs and requirements. Understanding that the fundamental aim of this design pattern is to capture raw event data and store them separately and immutably, such that they serve as a source of truth and a means of reconstructing prevoius states, I gave a lot of consideration to the efficiency and other tradeoffs of different database design approaches.
<br>
<br>
At first, the most straightforward approach seemed to be a relational database or 
some other dual-table or dual-database configuration. Here, the 'event source' table would stored separately from a 'users' table, and the two would be joined on some key(s) or field(s). However, the idea of efficiently querying a setup like this seemed... challenging. Additionally, I wanted there to be a way to maintain _some_ stateful data while still accommodating and adhering to the Event Source pattern. I assumed we wouldn't want to 'play back' all the events in a user's event history each time we wanted to determine their current balance.
<br>
<br>
With all this in mind, I arrived at the decision of using a NoSQL document database -- in this case, MongoDB. With the flexibility that document databases offer, there was a very effective way to organize all the raw, immutable event data along with the stateful data in a way that is easily accessible (and queryable) while still facilitating a true Event Source model. As you'll see, the structure of this project's MongoDB collection is such that the collection ('Users') consists of user records with two main components: a nested collection of that user's event records, as well as a field to hold and update the user's current balance. I believe this configuration was the right choice for this project given its advantages in data accessability as well as integrity. 
<br>
<br>

## Assumptions
A few assumptions I made during the development process include:
* 'New users' may make deposits to a 'new account' by sending a load request
    - I am considering load requests containing userId's not already in the database to be new users
* Timestamps may be in UTC (don't necessarily have to be in EST)
* UserId's may be any (unique) string (no particular format required)
