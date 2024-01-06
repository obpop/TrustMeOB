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

function updatePage(data) {
    // Uppdatera sidan med Google-information
    const googleData = data.google;
    document.getElementById("name").innerText = googleData.name
    document.getElementById("google-address").innerText = "Adress: " + googleData.address;
    document.getElementById("google-address").innerText = "Recensioner: " + googleData.reviews;

    // Uppdatera sidan med Foursquare-information
    const foursquareData = data.foursquare;
    document.getElementById("foursquare-address").innerText = "Recensioner: " + foursquareData.texts;
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