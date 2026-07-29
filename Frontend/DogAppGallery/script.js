"use strict";
document.addEventListener("DOMContentLoaded", () => {
    const fetchBtn = document.getElementById("fetchBtn");
    const img = document.querySelector(".displayImage");
    const errorDiv = document.getElementById("error");
    if (!fetchBtn || !img || !errorDiv) {
        return;
    }
    const url = "https://dog.ceo/api/breeds/image/random";
    fetchBtn.addEventListener("click", async () => {
        await fetchDogImage(url);
    });
    const fetchDogImage = async (url) => {
        try {
            const response = await fetch(url);
            if (!response.ok) {
                throw new Error(`HTTP error! status: ${response.status}`);
            }
            const data = await response.json();
            img.src = data.message;
        }
        catch (err) {
            errorDiv.textContent = "Error fetching dog image: " + err.message;
        }
    };
});
