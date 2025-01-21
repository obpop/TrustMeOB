import React from 'react';
import { FaQuestion, FaBattleNet, FaAlignLeft } from "react-icons/fa";

import './NavAbout.css';

const NavAbout: React.FC = () => {
    return (
        <div className="about-container">
            {/* Text section container */}
            <div>
                <h1 className="page-title">VAD ÄR TRUSTME</h1>
            </div>

            <div className="paragraph-box">
                <FaQuestion className="icon"/>
                <div className="text-content">
                    <h2 className="paragraph-title">VAD ÄR TRUSTME</h2>
                    <p className="paragraph-text">TrustMe är en banbrytande plattform som hjälper företag att effektivt hantera och analysera kundrecensioner genom att samla all relevant information på ett ställe. Denna tjänst är utformad för att spara tid och ge en helhetsbild av kunders upplevelser, vilket gör det enklare att fatta välgrundade beslut baserade på verklig feedback.</p>
                </div>
            </div>

            <div className="paragraph-box" id='middle-para'>
                <FaBattleNet className="icon"/>
                <div className="text-content">
                    <h2 className="paragraph-title">HUR FUNGERAR DET</h2>
                    <p className="paragraph-text">TrustMe syftar till att underlätta för konsumenter att snabbt och enkelt hitta pålitlig och sammanhängande information om olika företag. Istället för att behöva söka igenom olika webbplatser för att samla in data, presenterar TrustMe alla nödvändiga detaljer på en enda plattform. Detta är särskilt värdefullt för konsumenter som vill göra välgrundade val baserade på andra kunders erfarenheter och åsikter. Genom att samla kundrecensioner, geografisk placering via kartbilder, och AI-genererade sammanfattningar av recensioner, skapas en tydlig och sammanhängande överblick över företag.</p>
                </div>
            </div>

            <div className="paragraph-box">
                <FaAlignLeft className="icon"/>
                <div className="text-content">
                <h2 className="paragraph-title">SAMLAR IN & PRESENTERAR INFORMATION GENOM EXTERNA API:ER</h2>
                    <ul className="paragraph-text">
                        <li className='api-list'> 
                            <span className="api-title">Google Places API:</span>    
                            <span id="google-text">Hämtar information om olika platser, inklusive användaromdömen, betyg, adresser, och öppettider.</span></li>
                        <li className='api-list'>
                            <span className="api-title">Foursquare Places API:</span>  
                            <span id="foursquare-text">Kompletterar informationen från Google Places API genom att tillhandahålla ytterligare omdömen och information.</span> </li>
                        <li className='api-list'>
                            <span className="api-title">OpenAI API:</span>          
                            <span id="openai-text">Använder AI-teknik för att sammanfatta stora mängder omdömen till koncisa sammanfattningar, vilket ger användarna en snabb insikt i andras upplevelser och åsikter.</span> </li>
                    </ul>
                    <p className="paragraph-text">Dataflödet fungerar genom att först söka på ett företag via sökrutan på hemsidan med hjälp av Google Places API. Därefter tar Foursquare API emot samma input samt koordinationer på ett begränsat område för att säkra att både API:erna hämtar recensionerna från det angivna företaget. Efter att ha mottagit datan filtreras användaromdömen och skickas till OpenAI API för sammanfattning.</p>
                </div>
            </div>

            <div>
                <h1 className="page-title">FÖRDELAR MED TRUSTME</h1>
            </div>

            <div className='text-section-container'>
                <div className="paragraph-box-about" id='top-left-para'>
                    <div className="text-content">
                        <h2 className="paragraph-title-about">CENTRALISERAD FEEDBACK</h2>
                        <p className="paragraph-text-about">Alla recensioner och kundomdömen samlas på en plattform, vilket sparar tid och ansträngning.</p>
                    </div>
                </div>

                <div className="paragraph-box-about" id='top-right-para'>
                    <div className="text-content">
                        <h2 className="paragraph-title-about">AI-DRIVNA ANALYSER</h2>
                        <p className="paragraph-text-about">Användning av AI för att analysera och sammanfatta recensioner ger värdefulla insikter och konkreta förbättringsförslag.</p>
                    </div>
                </div>

                <div className="paragraph-box-about" id='bottom-left-para'>
                    <div className="text-content">
                        <h2 className="paragraph-title-about">GEOGRAFISK KONTEXT</h2>
                        <p className="paragraph-text-about">Statiska kartbilder från Google Static Maps API visar företagets geografiska läge, vilket ger en visuell kontext.</p>
                    </div>
                </div>

                <div className="paragraph-box-about" id='bottom-right-para'>
                    <div className="text-content">
                        <h2 className="paragraph-title-about">INGEN DATABAS</h2>
                        <p className="paragraph-text-about">All data hämtas direkt från externa API, vilket förenklar underhåll och uppdateringar av systemet.</p>
                    </div>
                </div>
            </div>
        </div>
    );
};

export default NavAbout;
