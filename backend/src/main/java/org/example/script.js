function getReviewForPlace() {
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
    document.getElementById("strengths").innerHTML = "<h3>Styrkor</h3><ul>" + data.strengths.map(strength => "<li>" + strength + "</li>").join("") + "</ul>";
    document.getElementById("flaws").innerHTML = "<h3>Svagheter</h3><ul>" + data.flaws.map(flaw => "<li>" + flaw + "</li>").join("") + "</ul>";
    document.getElementById("strategy").innerHTML = "<h3>Optimeringsstrategier</h3><ul>" + data.strategy.map(strategy => "<li>" + strategy + "</li>").join("") + "</ul>";
}


