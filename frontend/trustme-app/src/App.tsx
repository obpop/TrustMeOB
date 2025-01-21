// App.tsx
import React from 'react';
import Header from './components/Header';
import NavCompany from './components/NavCompany';
import Main from "./components/Main";
import NavAbout from "./components/NavAbout";
import NavPrivate from './components/NavPrivate';
import { Route, Routes } from "react-router-dom";
import './App.css';

const App: React.FC = () => {

    return (
        <>
            <Header />
            <div className="container">
                <Routes>
                    <Route path="/" element={<Main />} />
                    <Route path="/foretag" element={<NavCompany />} />
                    <Route path="/privat" element={<NavPrivate />} />
                    <Route path="/om" element={<NavAbout />} />
                </Routes>
            </div>
        </>
    );
};

export default App;
