// Header.tsx or Header.jsx
import React from 'react';
import { Link } from 'react-router-dom';
import { FaSearch } from 'react-icons/fa';
import './Header.css';

const Header: React.FC = () => {
    return (
        <header>
            <div className="header-container">
                <div className="nav-left">
                    <Link to="/" className="nav-link">
                        <button className="nav-button nav-button-trustme">
                            <div className="icon-text-container">
                                <FaSearch className="icon-trustme" />
                                <span className="nav-text">TrustMe</span>
                            </div>
                        </button>
                    </Link>
                </div>
                <nav className="nav">
                    <ul>
                        <Link to="/foretag" className="nav-link">
                            <button className="nav-button">
                                <span className="nav-text-right">För företag</span>
                            </button>
                        </Link>
                    
                        <Link to="/privat" className="nav-link">
                            <button className="nav-button">
                                <span className="nav-text-right">För privatpersoner</span>
                            </button>
                        </Link>
                        
                        <Link to="/om" className="nav-link">
                            <button className="nav-button">
                                <span className="nav-text-right">Om</span>
                            </button>
                        </Link>
                    </ul>
                </nav>
            </div>
        </header>
    );
};

export default Header;
