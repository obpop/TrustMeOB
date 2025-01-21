// Funktion för att hämta recensioninformation för ett företag
async function getReviewForPlace(searchParam) {
    var companyName = searchParam;

    // Kontrollerar att företaget finns
    if (!companyName) {
        alert("Sök på företag.");
        return;
    }

    const body = {
        name : companyName
    };
    showProgress();

    // Gör en HTTP-begäran till backend med företagsnamnet
    fetch('http://localhost:8080/places', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
        },
        body: JSON.stringify(body),
    })
        .then(response => response.json())
        .then(data => {
            console.log('Success:', data);
            updatePage(data);
            hideProgress()
        })
        .catch((error) => {
            console.error('Error:', error);
        });
}

// Funktion för söka med inmatat värde och omdirigerar till index.html
function searchAndRedirect(searchParam) {
    var companyName = document.getElementById("companyName").value;

    // Kontrollerar om det saknas både företagsnamn och sökparametrar *behövs???
    if (!companyName && !searchParam) {
        alert("Sök på företag.");
        return;
    }

    // Bygg URL med sökparametern och navigera till andra sidan med parametern
    var url = "index.html";
    if (searchParam) {
        url += "?search=" + encodeURIComponent(searchParam);
    } else {
        url += "?search=" + encodeURIComponent(companyName);
    }

    window.location.href = url; // Omdirigerar till den byggda URL:en
}

// Funktion för att uppdatera sida med aktuell data
function updatePage(data) {
    // Uppdatera sidan med Google-information
    const googleData = data.google;
    document.getElementById("name").innerText = googleData.name;

    // Uppdaterar sidan med information från openAI
    const aiData = data.openAI;
    document.getElementById("strengths-data").innerText = aiData.strengths;
    document.getElementById("flaws-data").innerText = aiData.weaknesses;
    document.getElementById("strategy-data").innerText = aiData.action_points;

    // Hitta kartelementet
    const mapImage = document.getElementById("mapImage");

    // Kontrollera om det finns data för Google
    if (data && data.google && data.google.map) {
        mapImage.src = data.google.map;
    } else {
        mapImage.src = ""; // Rensa kartan
        alert("Ingen kartinformation tillgänglig.");
    }
}

// Funktion för att visa laddningsindikator
function showProgress() {
    const progress = document.getElementById("progress");
    progress.style.display = "block";
}

// Funktion för att dölja laddningsindikator
function hideProgress() {
    const progress = document.getElementById("progress");
    progress.style.display = "none";
}

// Funktion för att ladda sökparameter från URL
function checkAndLoadSearchParam() {
    const searchParam = new URLSearchParams(window.location.search).get('search');
    console.log(searchParam)
    if (searchParam) {
        getReviewForPlace(searchParam);
    }
}

// Funktion för att leta efter klick på sökknapp
function checkSearchButton() {
    const searchButton = document.getElementById("searchButton");

    if (searchButton) {
        searchButton.addEventListener("click", function () {
            var companyName = document.getElementById("companyName").value;
            searchAndRedirect(companyName);
        });
    }
};

// Kör funktioner när DOM laddas
document.addEventListener("DOMContentLoaded", checkAndLoadSearchParam);
document.addEventListener("DOMContentLoaded", checkSearchButton); 