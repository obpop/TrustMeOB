import React, { useEffect, useState } from 'react';
import PlacesAutocomplete from 'react-places-autocomplete';
import './Main.css';

interface ReviewData {
    google: { name: string; address: string; lat: number; lng: number; map: string };
    openAI: { strengths: string; weaknesses: string; opportunities: string; threats: string };
}

const Main: React.FC = () => {
    const [address, setAddress] = useState('');
    const [data, setData] = useState<ReviewData | null>(null);
    const [loading, setLoading] = useState(false);


    useEffect(() => {
        const searchParam = new URLSearchParams(window.location.search).get('search');
        if (searchParam) {
            fetchReviewForPlace(searchParam);
        }
    }, []);

    const fetchReviewForPlace = async (placeName: string) => {
        setLoading(true);
        try {
            const response = await fetch('https://trust-me-47e24c1a0226.herokuapp.com/', {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json',
                },
                body: JSON.stringify({ name: placeName }),
            });
            const result = await response.json();
            setData(result);
        } catch (error) {
            console.error('Error:', error);
        } finally {
            setLoading(false);
        }
    };

    const handleSelect = (address: string) => {
        setAddress(address);
        fetchReviewForPlace(address); // Fetch reviews based on searched (selected) company
    };

    const handleChange = (address: string) => {
        setAddress(address);
    };

    const renderReviewLines = (text: string) => {
        return text.split('\n').map((line, index) => {
            const cleanedLine = line.replace(/^-+\s*/, ''); // Remove leading hyphens and spaces, ex "- "
            return (
                <div key={index} className="review-line">
                    <div className="review-text">{cleanedLine}</div>
                </div>
            );
        });
    };

    return (
        <main className="main">
            <div className="trustme-info">
                <h1>TrustMe</h1>
                <h3>SAMMANSTÄLLER KUNDRECENSIONER AV FÖRETAG</h3>
            </div>
            <div className="search-bar">
                <PlacesAutocomplete
                    value={address}
                    onChange={handleChange}
                    onSelect={handleSelect}
                    searchOptions={{ types: ['establishment'], componentRestrictions: { country: 'se' } }}
                >
                    {({ getInputProps, suggestions, getSuggestionItemProps, loading }) => (
                        <div className="input-container">
                            <span className="fas fa-search search-icon" onClick={() => fetchReviewForPlace(address)}></span>
                            <input
                                {...getInputProps({
                                    placeholder: "Sök på företag och plats...",
                                    id: "search-input",
                                    className: "location-search-input",
                                    spellCheck: false,
                                    lang: 'sv',
                                    onKeyDown: (event) => {
                                        if (event.key === 'Enter') {
                                            fetchReviewForPlace(address);
                                        }
                                    }
                                })}
                            />
                            {suggestions.length > 0 && (
                            <div className="autocomplete-dropdown-container">
                                {loading}
                                {suggestions.map(suggestion => {
                                    const style = {
                                        backgroundColor: suggestion.active ? "#fafafa" : "#ffffff"
                                    };
                                    return (
                                        <div className="dropdown-content"
                                            {...getSuggestionItemProps(suggestion, { style })}
                                            key={suggestion.placeId}
                                        > <span className="fas fa-search search-icon" id="icon-auto" onClick={() => fetchReviewForPlace(address)}></span>
                                            {suggestion.description}
                                        </div>
                                    );
                                })}
                            </div>
                            )}
                        </div>
                    )}
                </PlacesAutocomplete>
            </div>

            {loading && <div className="loader">
                            <div className="loaderBar"></div>
                        </div>
            }

            {data && !loading && (
                <>
                    <div className="name-company-div">
                        <h4 id="name-company">{data.google.name}</h4>
                        <div className="data-container" id="placeinfo">
                            <div className="place" id="picture">
                        </div>
                    </div>
                        <p id="company-about-reviews">Nedan finner du en SWOT-analys som innehåller; <span id="swot-buzz">Strengths, Weaknesses, Opportunities & Threats</span></p>
                    </div>
                    <div className="data-container-color1">
                        <div className="data" id="strengths">
                            <h4 id="strengths-list">STRENGTHS</h4>
                            <p id="strengths-data" className="review-container">{renderReviewLines(data.openAI.strengths)}</p>
                        </div>
                    </div>
                    <div className="data-container-color2">
                        <div className="data" id="flaws">
                            <h4 id="flaws-list">WEAKNESSES</h4>
                            <p id="flaws-data" className="review-container">{renderReviewLines(data.openAI.weaknesses)}</p>
                        </div>
                    </div>
                    <div className="data-container-color3">
                        <div className="data" id="opportunities">
                            <h4 id="opportunities-list">OPPORTUNITIES</h4>
                            <p id="opportunities-data" className="review-container">{renderReviewLines(data.openAI.opportunities)}</p>
                        </div>
                    </div>
                    <div className="data-container-color4">
                        <div className="data" id="threats">
                            <h4 id="threats-list">THREATS</h4>
                            <p id="threats-data" className="review-container">{renderReviewLines(data.openAI.threats)}</p>
                        </div>
                    </div>
                    <div className="data-container-color5">
                        <h4 id="hitta-hit">HITTA HIT</h4>
                        <img id="mapImage" src={data.google.map} alt="Karta" />
                        <p className='address-company'>{data.google.address}</p>
                    
                    </div>
                </>
            )}
        </main>
    );
};

export default Main;
