document.addEventListener("DOMContentLoaded", () => { 
    const fetchBtn = document.getElementById("fetchBtn");
    const img = document.getElementById("displayImage");
    const errorDiv = document.getElementById("error");
 
    if (!fetchBtn) {
        return;
    }
 
    fetchBtn.addEventListener("click", async () => {
 
        img.style.display = "none";
        errorDiv.textContent = "";
 
        try {
            const response = await fetch("https://dog.ceo/api/breeds/image/random");
 
            if (!response.ok) throw new Error(`HTTP error: ${response.status}`);
 
            const data = await response.json();
 
            if (data.status !== "success") throw new Error("API returned error");
 
            img.src = data.message;
            img.onload = () => {
                img.style.display = "block";
            };
            img.onerror = () => {
                errorDiv.textContent = "Failed to load image.";
            };
        } catch (err) {
            errorDiv.textContent = "Error fetching dog image: " + err.message;
        }
    });
});