document.addEventListener("DOMContentLoaded", () => {
  const fetchBtn: HTMLElement | null = document.getElementById("fetchBtn");
  const img = document.querySelector<HTMLImageElement>(".displayImage");
  const errorDiv: HTMLElement | null = document.getElementById("error");

  if (!fetchBtn || !img || !errorDiv) {
    return;
  }

  type DogResponse = {
    message: string;
  };
  const url: string = "https://dog.ceo/api/breeds/image/random";
  fetchBtn.addEventListener("click", async () => {
    await fetchDogImage(url);
  });
  const fetchDogImage = async (url: string) => {
    try {
      const response: Response = await fetch(url);
      if (!response.ok) {
        throw new Error(`HTTP error! status: ${response.status}`);
      }

      const data: DogResponse = await response.json();

      img.src = data.message;
    } catch (err: any) {
      errorDiv.textContent = "Error fetching dog image: " + err.message;
    }
  };
});
