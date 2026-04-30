# VaultCLI: Accounting Ledger Application

## Project Overview: VaultCLI
VaultCLI is a Java application that records and tracks personal financial
transactions through a command-line interface. It functions by reading
from and writing to a local CSV file, which serves as a permanent data
store. This architecture ensures that all entered deposits and payments
remain available for review after the program is closed and restarted.

### The application is built around three core operational pillars:

1. Data Integrity and Persistence
   At the start of every session, the application parses the transactions.csv
   file into an ArrayList of objects. This allows the program to perform 
   calculations and searches in memory for speed while maintaining a physical
   record on the hard drive. When a user logs a new entry, the system updates
   the file and the memory list simultaneously. To maintain mathematical
   accuracy, the program automatically manages transaction signs: it
   force-assigns positive values to deposits and negative values to payments,
   preventing user error in balance calculations.

2. Search and Reporting Engine
   The reporting module uses LocalDate and LocalTime objects to analyze the
   transaction history. Users can generate fixed reports, such as
   "Month-to-Date" or "Previous Year," where the system automatically
   calculates the date boundaries and filters the list accordingly.
   The engine is designed to handle "Live" data, meaning any transaction
   added during the current session is immediately included in report
   results without requiring a reload. Beyond time-based reports, the engine
   includes dedicated logic to filter the ledger by transaction type or entity:
   * Type-Based Filtering: The system can isolate all entries to display only
     "Deposits" (positive values) or "Payments" (negative values).
   * Vendor Search: Users can query a specific vendor name to see every
     transaction associated with that business.
   * Custom Search: This feature allows for multi-criteria queries, combining
     dates, descriptions, vendors, and amounts into a single filtered view.

3. The Gatekeeper Logic
   The "Custom Search" feature uses a specific filtering pattern to handle
   multi-criteria requests. Users are prompted for five optional variables:
   Start Date, End Date, Description, Vendor, and Amount. The engine evaluates
   the transaction list through a series of conditional checks. If a search field 
   is left blank, the logic bypasses that specific check. If a field contains
   data, the transaction must match that criteria to remain in the results.
   This approach allows users to perform highly specific queries (e.g., "All
   payments to Amazon between January and March") or broad searches by filling
   in only one field.

4. Input Validation and Stability
   To prevent runtime crashes, the application uses a centralized input utility
   that handles data conversion and error trapping. 
   * Prompt Management: The system reads all input as a String first. This
     method clears the Scanner buffer and prevents the program from skipping prompts.
   * Exception Handling: When converting text into dates or numbers, the system
     uses try-catch blocks. If a user enters an invalid format (like "Oct 12"
     instead of "2026-10-12"), the program catches the error, explains the
     requirement, and re-prompts the user instead of terminating the process.

## How to Run the Project

### Prerequisites
* **Java Development Kit (JDK):** Version 17 or higher (Version 26 preferred).
* **IDE:** IntelliJ IDEA (recommended) or any Java-compatible editor.

### Steps to Run
1.  **Clone the Repository:**
    * Open your terminal and navigate to your desired directory.
    * Run: 
    ```
    git clone https://github.com/yu26s9-sky0-0glitch/vault-cli.git
    ```
2.  **Open in IntelliJ:**
    * Launch IntelliJ IDEA, select **Open**, and navigate to the `vault-cli`
      folder.
3.  **Prepare the Data Source:**
    * Ensure `transactions.csv` is located in the root directory of the project.
4.  **Launch:**
    * Locate `Main.java` in the `src` folder.
    * Right-click and select **Run 'Main.main()'**.
5.  **Navigate:**
    * Follow the on-screen menu prompts to record activity or generate reports.

### CSV Data Format Preview
```csv
date|time|description|vendor|amount
2025-08-14|08:52:40|Pizza|Uno|-15.99
2025-09-01|12:15:20|Monthly Rent|Leasing Office|-1250.00
2025-10-15|18:22:10|Dinner|Chipotle|-18.45
2025-11-28|10:00:00|Black Friday Sale|BestBuy|-89.99
```
### Technical Showcase: Robust Data Management
Chained Comparator Sorting; Originally, the application attempted to reverse
the ledger by inserting new CSV rows at index 0 of the ArrayList. I realized
this approach assumed the source file was already sorted. To ensure the 
is always accurate regardless of the CSV's state, I implemented a
programmatic sort using chained comparators.
This method sorts by date, uses the time as a tie-breaker for same-day entries,
and then reverses the entire collection to ensure the newest transactions appear first.
```
    public static ArrayList<Transaction> sortLedger(ArrayList<Transaction> ledgerLoader) {

        ledgerLoader.sort(
                Comparator.comparing(Transaction::getDate)
                        .thenComparing(Transaction::getTime)
                        .reversed()
        );
        return ledgerLoader;
    }
```