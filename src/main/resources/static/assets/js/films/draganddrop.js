var dragDropContainer = document.getElementById('drag-drop-container');
var fileInput = document.getElementById('file-input');
var previewContainer = document.getElementById('preview-container');
var previewImage = document.getElementById('preview-image');
var dragDropText = document.getElementById('drag-drop-text');

dragDropContainer.addEventListener('dragenter', function (event) {
    event.preventDefault();
    dragDropContainer.classList.add('highlight');
});

dragDropContainer.addEventListener('dragover', function (event) {
    event.preventDefault();
});

dragDropContainer.addEventListener('dragleave', function (event) {
    dragDropContainer.classList.remove('highlight');
});

dragDropContainer.addEventListener('drop', function (event) {
    event.preventDefault();
    dragDropContainer.classList.remove('highlight');

    var file = event.dataTransfer.files[0];

    if (file && file.type.startsWith('image/')) {
        var reader = new FileReader();

        reader.onload = function (event) {
            previewImage.src = event.target.result;
            dragDropText.style.display = 'none';
        };

        reader.readAsDataURL(file);
    }
});

fileInput.addEventListener('change', function (event) {
    var file = event.target.files[0];

    if (file && file.type.startsWith('image/')) {
        var reader = new FileReader();

        reader.onload = function (event) {
            previewImage.src = event.target.result;
            previewContainer.style.display = 'block';
            dragDropText.style.display = 'none';
        };

        reader.readAsDataURL(file);
    }
});