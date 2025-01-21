import React, { useState } from 'react';
import './SearchBar.css';

const SearchBar: React.FC = () => {
    const [companyName, setCompanyName] = useState('');

    const handleSearch = () => {
        const url = `index.html?search=${encodeURIComponent(companyName)}`;
        window.location.href = url;
    };

    return (
        <div className="searchbar-startsida">
            <div className="search-container">
                <input
                    className="search-input"
                    type="text"
                    placeholder="Sök företagsnamn"
                    value={companyName}
                    onChange={(e) => setCompanyName(e.target.value)}
                />
                <button className="search-button" onClick={handleSearch}>
                    <img className="search-icon" src="images/search.png" alt="Sök" />
                </button>
            </div>
        </div>
    );
};

export default SearchBar;
