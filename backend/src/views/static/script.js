async function getReviewForPlace() {
    var companyName = document.getElementById("companyName").value;

    if (!companyName) {
        alert("Sök på företag.");
        return;
    }

    const body = {
        name : companyName
    };

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
        })
        .catch((error) => {
            console.error('Error:', error);
        });


}

function updatePage(data) {
    // Uppdatera sidan med Google-information
    const googleData = data.google;
    document.getElementById("name").innerText = googleData.name
    document.getElementById("google-reviews").innerText = googleData.reviews;
    
    // Uppdatera sidan med Foursquare-information
    const foursquareData = data.foursquare;
    document.getElementById("foursquare-reviews").innerText = foursquareData.texts;

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
