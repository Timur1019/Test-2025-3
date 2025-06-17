document.getElementById('uploadForm').addEventListener('submit', async function(e) {
    e.preventDefault();

    const fileInput = document.getElementById('fileInput');
    const status = document.getElementById('status');
    const link = document.getElementById('link');

    if (!fileInput.files.length) {
        alert('Choose a file');
        return;
    }

    status.textContent = 'Uploading...';
    link.textContent = '';

    const formData = new FormData();
    formData.append('file', fileInput.files[0]);

    try {
        const response = await fetch('http://localhost:7070/upload', {
            method: 'POST',
            body: formData
        });

        if (!response.ok) {
            throw new Error(await response.text());
        }

        const downloadUrl = await response.text();
        status.textContent = 'Upload complete!';
        link.innerHTML = `Download: <a href="${downloadUrl}" target="_blank">${downloadUrl}</a>`;
    } catch (err) {
        status.textContent = 'Error uploading file';
        link.textContent = '';
        console.error(err);
    }
});
