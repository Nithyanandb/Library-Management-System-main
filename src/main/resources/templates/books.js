function fetchBooks() {
    fetch('/books')
        .then(response => response.json())
        .then(books => {
            const tableBody = document.querySelector('#booksTable tbody');
            tableBody.innerHTML = '';
            books.forEach(book => {
                const row = document.createElement('tr');
                row.innerHTML = `
                    <td>${book.id}</td>
                    <td>${book.name}</td>
                    <td>${book.genre}</td>
                    <td>${book.authorId}</td>
                    <td>${book.available}</td>
                `;
                tableBody.appendChild(row);
            });
        });
}

document.getElementById('createBookForm').addEventListener('submit', function(event) {
    event.preventDefault();
    const formData = new FormData(this);
    fetch('/books/create', {
        method: 'POST',
        body: JSON.stringify(Object.fromEntries(formData)),
        headers: { 'Content-Type': 'application/json' }
    }).then(response => {
        if (response.ok) {
            alert('Book created successfully!');
            this.reset();
            fetchBooks();  // Refresh book table
        } else {
            alert('Error creating book.');
        }
    });
});

fetchBooks();
