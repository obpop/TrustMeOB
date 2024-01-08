async function getReviewForPlace() {
    var companyName = document.getElementById("companyName").value;

    if (!companyName) {
        alert("Sök på företag.");
        return;
    }

    // Gör en HTTP-begäran till backend med företagsnamnet
    fetch('/' + encodeURIComponent(companyName))
        .then(response => {
            if (!response.ok) {
                throw new Error('Fel');
            }
            return response.json();
        })
        .then(data => {
            // Uppdatera frontend med recensionsinformation
            updatePage(data);
        })
        .catch(error => {
            console.error('Error:', error);
            alert('Kunde inte hämta recension');
        });
}

function searchAndRedirect() {
    var companyName = document.getElementById("companyName").value;
    
    if (!companyName) {
        alert("Sök på företag.");
        return;
    }

    // Bygg URL med sökparametern och navigera till andra sidan
    window.location.href = "about.html?search=" + encodeURIComponent(companyName);
}

function searchAndDisplay() {
    // Hämta sökparametern från URL
    var searchParam = new URLSearchParams(window.location.search).get('search');

    if (!searchParam) {
        alert("Ingen sökparameter hittad.");
        return;
    }

    // Använd sökparametern för att utföra sökningen och uppdatera sidan
    // (Anropa din funktion för att hämta och visa sökresultatet)
    getReviewForPlace(searchParam);
}

function updatePage(data) {
    // Uppdatera sidan med Google-information
    const googleData = data.google;
    document.getElementById("name").innerText = googleData.name
    document.getElementById("google-reviews").innerText = googleData.reviews;
    
    // Uppdatera sidan med Foursquare-information
    const foursquareData = data.foursquare;
    document.getElementById("foursquare-reviews").innerText = foursquareData.texts;

    // Uppdaterar sidan med information från openAI
    const aiData = data.openAI;
    document.getElementById("strengths-data").innerText = aiData.strengths;
    document.getElementById("flaws-data").innerText = aiData.weaknesses;
    document.getElementById("strategy-data").innerText = aiData.action_points;

    // Hitta kartelementet
    const mapImage = document.getElementById("mapImage");

    // Kontrollera om det finns data för Google
    if (data && data.google && data.google.map) {
        // Visa kartan från Google
        mapImage.src = data.google.map;
    } else {
        // Om det inte finns någon kartinformation
        mapImage.src = ""; // Rensa kartan
        alert("Ingen kartinformation tillgänglig.");
    }
}

async function testReviews(){
    try {
        const response = await fetch('http://localhost:8080/places');
        const data = await response.json();

        console.log(data);
        updatePage(data);

    } catch (error) {
        console.error(error);
    }
}
