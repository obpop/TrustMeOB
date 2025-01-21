import React from 'react';
import { FaRegSmile, FaStackOverflow, FaRobot  } from 'react-icons/fa';

import './NavCompany.css'; 

const NavCompany: React.FC = () => {
    return (
        <div className="about-container">
            <div> 
                <h1 className="page-title">ANALYSERA KUNDFEEDBACK EFFEKTIVARE MED TRUSTME</h1>
            </div>

            <div className="paragraph-box">
                <FaStackOverflow className="icon"/>
                <div className="text-content">
                    <h2 className="paragraph-title">EFFEKTIVISERA RECENSIONSHANTERINGEN</h2>
                    <p className="paragraph-text">Tänk dig att på ett ögonblick kunna se vad dina kunder tycker om ditt företag, utan att behöva söka igenom flera plattformar. Med TrustMe sammanfattas alla recensioner åt dig snabbt och enkelt, vilket ger dig en klar och tydlig överblick över kundernas upplevelser. Spara tid och få en helhetsbild av vad som fungerar och vad som kan förbättras.</p>
                </div>
            </div>

            <div className="paragraph-box" id='middle-para'>
                <FaRobot className="icon"/>
                <div className="text-content">
                    <h2 className="paragraph-title">STRATEGISK FÖRBÄTTRING MED AI-INSIKTER</h2>
                    <p className="paragraph-text">Låt kundernas feedback driva din verksamhet framåt. TrustMe använder AI för att sammanfatta recensioner och lyfta fram de viktigaste insikterna. Upptäck vanliga problem och få konkreta förslag på förbättringar. Använd denna värdefulla information för att utveckla strategier som tar din produkt eller tjänst till nästa nivå.</p>
                </div>
            </div>

            <div className="paragraph-box">
                <FaRegSmile className="icon"/>
                <div className="text-content">
                    <h2 className="paragraph-title">BYGG ETT STARKTARE RYKTE & ÖKA KUNDNÖJDHETEN</h2>
                    <p className="paragraph-text">Visa dina kunder att deras åsikter spelar roll. Med TrustMe kan du snabbt reagera på feedback och vidta åtgärder som förbättrar kundupplevelsen. Genom att aktivt arbeta med recensioner kan du stärka ditt varumärke, öka kundnöjdheten och attrahera nya kunder. TrustMe hjälper dig att vara lyhörd och anpassningsbar i en ständigt föränderlig marknad.</p>
                </div>
            </div>
        </div>
    );
};

export default NavCompany;
