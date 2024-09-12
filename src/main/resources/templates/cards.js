function fetchCards() {
    fetch('/cards')
        .then(response => response.json())
        .then(cards => {
            const tableBody = document.querySelector('#cardsTable tbody');
            tableBody.innerHTML = '';
            cards.forEach(card => {
                const row = document.createElement('tr');
                row.innerHTML = `
                    <td>${card.id}</td>
                    <td>${card.studentName}</td>
                    <td>${card.status}</td>
                    <td>${card.createdOn}</td>
                    <td>${card.updatedOn}</td>
                `;
                tableBody.appendChild(row);
            });
        });
}

fetchCards();
