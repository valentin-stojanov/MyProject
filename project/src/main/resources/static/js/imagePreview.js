
function previewImage() {
    const oFReader = new FileReader();
    oFReader.readAsDataURL(document.getElementById("picture").files[0]);

    oFReader.onload = function (oFREvent) {
        const img = document.getElementById("uploadPreview");
        img.src = oFREvent.target.result;
        img.style.display = 'block';
    }
}