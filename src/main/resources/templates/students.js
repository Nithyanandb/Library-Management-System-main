document.addEventListener('DOMContentLoaded', function() {
    // Function to show a message
    function showMessage(message, type = 'success') {
        const messageBox = document.createElement('div');
        messageBox.className = `message ${type}`;
        messageBox.innerText = message;

        // Add the message box to the body or a specific container
        document.body.appendChild(messageBox);

        // Remove the message box after 3 seconds
        setTimeout(() => {
            messageBox.remove();
        }, 3000);
    }

    // Assuming you have a way to detect a successful form submission
    const urlParams = new URLSearchParams(window.location.search);
    if (urlParams.has('success')) {
        const successMessage = urlParams.get('success');
        showMessage(successMessage);
    }
});
