import React from 'react';
import { FaHandHoldingUsd, FaMoneyCheckAlt, FaFistRaised } from 'react-icons/fa';
import './NavPrivate.css'; 

const NavPrivate: React.FC = () => {
    return (
        <div className="about-container">
            <div> 
                <h1 className="page-title">GÖR SMARTARE KÖP MED TRUSTME</h1>
            </div>

            <div className="paragraph-box">
                <FaHandHoldingUsd className="icon"/>
                <div className="text-content">
                    <h2 className="paragraph-title">GÖR DET BÄSTA BESLUTEN</h2>
                    <p className="paragraph-text">Att välja rätt produkt eller tjänst kan vara svårt med så många alternativ på marknaden. TrustMe gör det enkelt för dig som privatperson att få en sammanfattning av recensioner från olika plattformar. Se vad andra tycker om produkten du funderar på att köpa och undvik onödiga misstag. Med TrustMe kan du känna dig trygg i dina köpbeslut.</p>
                </div>
            </div>

            <div className="paragraph-box" id='middle-para'>
                <FaMoneyCheckAlt className="icon"/>
                <div className="text-content">
                    <h2 className="paragraph-title">SPARA TID & PENGAR</h2>
                    <p className="paragraph-text">Att söka igenom otaliga recensioner kan vara tidskrävande. TrustMe sammanfattar alla recensioner åt dig på ett ögonblick, vilket sparar dig både tid och ansträngning. Med denna översikt kan du snabbt avgöra om ett företag eller en produkt lever upp till dina förväntningar och undvika onödiga kostnader.</p>
                </div>
            </div>

            <div className="paragraph-box">
                <FaFistRaised className="icon"/>
                <div className="text-content">
                    <h2 className="paragraph-title">ÖKA DIN KONSUMENTMAKT</h2>
                    <p className="paragraph-text">Som konsument har du rätt att få valuta för pengarna. Genom att använda TrustMe får du en klar och tydlig bild av företagets rykte och produktkvalitet baserat på tidigare kunders erfarenheter. Detta gör att du kan sätta press på företag att leverera det bästa och hålla sig ansvariga för sina produkter och tjänster.</p>
                </div>
            </div>
        </div>
    );
};

export default NavPrivate;
