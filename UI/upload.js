document.getElementById('uploadButton').addEventListener('click', () => {
    const fileInput = document.getElementById('fileInput');
    const file = fileInput.files[0];

    if (!file) {
        document.getElementById('status').innerText = 'Please select a file.';
        return;
    }

    const formData = new FormData();
    formData.append('taxReport', file);

    fetch('http://localhost:8090/api/calculateTax', {
        method: 'POST',
        body: formData,
    })
    .then(response => {
        if (!response.ok) {
            throw new Error('Network response was not ok ' + response.statusText);
        }
        return response.json();
    })
    .then(data => {
        document.getElementById('status').innerText = 'Upload successful: ' + JSON.stringify(data);
    })
    .catch(error => {
        document.getElementById('status').innerText = 'Upload failed: ' + error.message;
    });
});
