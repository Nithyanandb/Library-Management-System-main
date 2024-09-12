document.addEventListener('DOMContentLoaded', function() {
    const issueForm = document.getElementById('issueForm');
    const returnForm = document.getElementById('returnForm');
    const transactionsTable = document.getElementById('transactionsTable').getElementsByTagName('tbody')[0];

    issueForm.addEventListener('submit', function(event) {
        event.preventDefault();
        const cardId = document.getElementById('issueCardId').value;
        const bookId = document.getElementById('issueBookId').value;

        fetch('/transactions/issue?cardId=' + cardId + '&bookId=' + bookId, {
            method: 'POST'
        })
        .then(response => response.text())
        .then(data => {
            alert(data);
            loadTransactions(); // Reload transactions after issuing
        })
        .catch(error => console.error('Error:', error));
    });

    returnForm.addEventListener('submit', function(event) {
        event.preventDefault();
        const cardId = document.getElementById('returnCardId').value;
        const bookId = document.getElementById('returnBookId').value;

        fetch('/transactions/return?cardId=' + cardId + '&bookId=' + bookId, {
            method: 'POST'
        })
        .then(response => response.text())
        .then(data => {
            alert(data);
            loadTransactions(); // Reload transactions after returning
        })
        .catch(error => console.error('Error:', error));
    });

    function loadTransactions() {
        fetch('/transactions')
            .then(response => response.json())
            .then(transactions => {
                transactionsTable.innerHTML = ''; // Clear existing rows
                transactions.forEach(transaction => {
                    const row = transactionsTable.insertRow();
                    row.insertCell(0).textContent = transaction.id;
                    row.insertCell(1).textContent = transaction.transactionId;
                    row.insertCell(2).textContent = transaction.cardId;
                    row.insertCell(3).textContent = transaction.bookId;
                    row.insertCell(4).textContent = transaction.fineAmount;
                    row.insertCell(5).textContent = transaction.status;
                    row.insertCell(6).textContent = new Date(transaction.date).toLocaleString();
                });
            })
            .catch(error => console.error('Error loading transactions:', error));
    }

    loadTransactions(); // Initial load of transactions
});
